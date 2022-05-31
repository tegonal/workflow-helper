package com.tegonal.todo.analysis

import java.nio.file.Path

enum Problem:
  case Todos(file: Path, lines: Seq[Todo])

case class Todo(lineNumber: Int, todo: String, description: String)
