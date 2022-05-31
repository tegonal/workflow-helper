package com.tegonal.todo.analysis

import java.nio.file.Path

trait Processor {

  type IntermediateResult

  val description: String

  def transformAndFilter(lineNumber: Int, line: String): Option[IntermediateResult]

  def foreach(file: Path, lines: List[IntermediateResult]): Unit

  def result(): Result
}
