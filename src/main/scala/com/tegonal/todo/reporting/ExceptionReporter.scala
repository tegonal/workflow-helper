package com.tegonal.todo.reporting

import scala.Console.*
import com.tegonal.todo.analysis.Processor
import com.tegonal.todo.analysis.Todo
import com.tegonal.todo.analysis.Problem
import com.tegonal.todo.analysis.Result

import scala.collection.mutable

class ExceptionReporter extends Reporter {
  def report(results: Seq[Result]): Unit = {

    var foundProblems = false

    val sb = mutable.StringBuilder()

    results.foreach(result =>
      sb.append(RESET).append(BLUE).append(result.description).append(RESET).append("\n")

      if (result.problems.isEmpty) {
        sb.append(RESET).append(GREEN).append("SUCCESS").append(RESET).append(" no violation found")
      } else {
        foundProblems = true
        result.problems.foreach(p => reportProblem(p, sb))
      }
    )
    if (foundProblems) {
      throw IllegalStateException("Found violations:\n" + sb.toString)
    }
  }

  private def reportProblem(problem: Problem, sb: mutable.StringBuilder): Unit =
    problem match {
      case Problem.Todos(file, todos) =>
        sb.append(s"file: ").append(file.getFileName.toString).append("\n")
        todos.foreach { case Todo(index, todo, text) =>
          sb.append(s" %4s: $RESET$RED%s$RESET %s".format(index.toString, todo, text)).append("\n")
        }

    }
}
