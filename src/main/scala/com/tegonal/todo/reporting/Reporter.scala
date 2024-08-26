package com.tegonal.todo.reporting

import com.tegonal.todo.analysis.Processor
import com.tegonal.todo.analysis.Result

trait Reporter {
  def report(results: Seq[Result]): Unit
}
