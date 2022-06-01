package com.tegonal.todo.reporting

import com.tegonal.todo.analysis.*

import scala.Console.*
import scala.collection.mutable

class ExceptionReporter extends Reporter {
  def report(results: Seq[Result]): Unit = {

    var foundProblems = false

    val sb = mutable.StringBuilder()

    results.foreach { result =>
      sb.append("- ").append(RESET).append(CYAN).append(result.description).append(RESET).append("\n")
      if (result.problems.nonEmpty) {
        foundProblems = true
        result.problems.foreach(p => reportProblem(p, sb))
      }
    }
    if (foundProblems) {
      throw AnalysisException(s"$RESET${RED}ERROR$RESET Found violations:\n" + sb.toString)
    } else {
      Console.println(s"$RESET${GREEN}SUCCESS$RESET No violations found for:\n" + sb.toString())
    }
  }

  private def reportProblem(problem: Problem, sb: mutable.StringBuilder): Unit =
    problem match {
      case Problem.Todos(file, todos) =>
        sb.append(s"  file: ").append(file.getFileName.toString).append("\n")
        todos.foreach { case Todo(index, todo, text) =>
          sb.append(s"   %4s: $RESET$RED%s$RESET %s".format(index.toString, todo, text)).append("\n")
        }

    }
}
