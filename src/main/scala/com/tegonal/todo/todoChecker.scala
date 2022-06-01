package com.tegonal.todo

import java.nio.file.*
import java.nio.file.attribute.BasicFileAttributes
import scala.Console.*
import scala.collection.mutable.ListBuffer
import scala.util.CommandLineParser.FromString
import scala.util.Try
import scala.util.matching.Regex
import com.tegonal.todo.analysis.{AnalysisException, FindTodoProcessor, Visitor}
import com.tegonal.todo.reporting.ExceptionReporter

@main def todoChecker(
    directory: Path,
    todoIndicator: TodoIndicator,
    issueIndicator: IssueIndicator,
): Unit = {
  try throwingTodoChecker(directory, todoIndicator, issueIndicator)
  catch
    case e: AnalysisException =>
      Console.println(e.getMessage)
      System.exit(1)
}

def throwingTodoChecker(
    directory: Path,
    todoIndicator: TodoIndicator,
    issueIndicator: IssueIndicator,
): Unit = {
  val processors = Seq(
    FindTodoProcessor(todoIndicator: TodoIndicator, issueIndicator: IssueIndicator)
  )

  val visitor = new Visitor(processors)
  Files.walkFileTree(directory, visitor)

  val results = processors.map(p => p.result())

  Seq(
    // should be last as it throws an exception if problems were found
    ExceptionReporter()
  ).foreach(r => r.report(results))
}
given FromString[Path] with
  def fromString(s: String): Path = Paths.get(s)

case class TodoIndicator(r: Regex) extends AnyVal
object TodoIndicator {
  val DEFAULT_PATTERN = "TODO|FIXME"

  given FromString[TodoIndicator] with
    def fromString(s: String): TodoIndicator = TodoIndicator((if s.isBlank then DEFAULT_PATTERN else s).r)
}

case class IssueIndicator(r: Regex) extends AnyVal
object IssueIndicator {
  val DEFAULT_PATTERN = "#\\d+"
  given FromString[IssueIndicator] with
    def fromString(s: String): IssueIndicator = IssueIndicator((if s.isBlank then DEFAULT_PATTERN else s).r)
}
