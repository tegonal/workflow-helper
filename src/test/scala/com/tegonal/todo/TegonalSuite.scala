package com.tegonal.todo

import java.nio.charset.{Charset, StandardCharsets}
import java.nio.file.{Files, Path}

trait TegonalSuite extends munit.FunSuite {

  def writeStringToFileFixture(content: String, charset: Charset = StandardCharsets.UTF_8): FunFixture[Path] =
    fileFixture(file => Files.writeString(file, content, charset))

  def fileFixture(action: Path => Unit): FunFixture[Path] =
    FunFixture[Path](
      setup = { test =>
        val path = Files.createTempFile("workflow-helper_" + test.name, ".txt")
        action(path)
        path
      },
      teardown = { file =>
        Files.deleteIfExists(file)
      }
    )
}
