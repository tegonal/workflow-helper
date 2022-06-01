package com.tegonal.todo.analysis

import com.tegonal.todo.*

import java.nio.file.Path
import scala.Console.{RED, RESET}
import scala.collection.mutable.ListBuffer
import scala.util.matching.Regex

class FindTodoProcessor(
      todoIndicator: TodoIndicator,
    issueIndicator: IssueIndicator
) extends Processor {
  override type IntermediateResult = Todo

  override val description =
    s"Find todos in code with $todoIndicator and $issueIndicator"

  private val regex = Regex(s"((?:${todoIndicator.r.regex})\\s*(?:${issueIndicator.r.regex}))(.*)")
  private val buffer = ListBuffer[Problem.Todos]()

  override def transformAndFilter(lineNumber: Int, line: String): Option[Todo] =
    regex.findFirstMatchIn(line).map(m => Todo(lineNumber, m.group(1), m.group(2)))

  override def foreach(file: Path, lines: List[Todo]): Unit =
    buffer.addOne(Problem.Todos(file, lines))

  override def result(): Result = Result(description, buffer.toList)
}
