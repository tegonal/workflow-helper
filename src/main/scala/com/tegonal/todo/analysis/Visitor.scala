package com.tegonal.todo.analysis

import com.tegonal.todo.analysis.Processor

import java.io.IOException
import java.nio.charset.{MalformedInputException, StandardCharsets}
import java.nio.file.attribute.BasicFileAttributes
import java.nio.file.{FileVisitResult, Files, Path, SimpleFileVisitor}
import scala.Console.{RED, RESET, YELLOW}
import scala.collection.SeqView
import scala.util.control.Breaks.break
import scala.util.{Failure, Try}

class Visitor(processors: Seq[Processor]) extends SimpleFileVisitor[Path] {
  private val ignorePatterns: Set[String] = Set(
    ".git",
    ".bsp",
    ".bloop",
    ".history",
    "target",
    ".idea",
    "out",
    ".vscode",
    ".metals",
  )

  override def preVisitDirectory(dir: Path, attrs: BasicFileAttributes): FileVisitResult =
    if ignorePatterns.contains(dir.getFileName.toString) then FileVisitResult.SKIP_SUBTREE;
    else FileVisitResult.CONTINUE

  override def visitFile(file: Path, attrs: BasicFileAttributes): FileVisitResult = {
    try {
      import scala.jdk.CollectionConverters.*
      val lines = Files
        .readAllLines(file)
        .asScala
        .view
        .zipWithIndex
        .map((line, index) => (index + 1, line))
        .toList

      processors.foreach { p =>
        val transformed = lines.flatMap(p.transformAndFilter)
        if (transformed.nonEmpty) p.foreach(file, transformed.toList)
      }
      FileVisitResult.CONTINUE
    } catch {
      case _: MalformedInputException =>
        // ignore (most likely a binary file) and continue
        FileVisitResult.CONTINUE
      case t: Throwable =>
        Console.err.println(
          s"$RESET${YELLOW}WARNING$RESET analysing file $file -- ${t.getClass.getName}: ${t.getMessage}"
        )
        FileVisitResult.CONTINUE
    }
  }

  override def visitFileFailed(file: Path, exc: IOException): FileVisitResult = {
    Console.err.println(
      s"$RESET${YELLOW}WARNING$RESET could not visit $file -- ${exc.getClass.getName}: ${exc.getMessage}"
    )
    FileVisitResult.CONTINUE
  }
}
