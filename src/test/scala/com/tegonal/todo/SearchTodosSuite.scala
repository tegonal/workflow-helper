package com.tegonal.todo

import com.tegonal.todo.analysis.AnalysisException

import java.io.ByteArrayOutputStream
import java.nio.file.{Files, Path}
import java.nio.charset.{Charset, StandardCharsets}
import java.nio.file.attribute.{DosFileAttributes, PosixFilePermissions}
import scala.jdk.CollectionConverters.*

class TodoFoundSuite extends TegonalSuite {
  List(
    (
      "default - finds TODO start of line",
      TodoIndicator(TodoIndicator.DEFAULT_PATTERN.r),
      IssueIndicator(IssueIndicator.DEFAULT_PATTERN.r),
      "TODO #123"
    ),
    (
      "default - finds TODO start of line after comment",
      TodoIndicator(TodoIndicator.DEFAULT_PATTERN.r),
      IssueIndicator(IssueIndicator.DEFAULT_PATTERN.r),
      "// TODO #123"
    ),
    (
      "default - finds FIXME in middle of line",
      TodoIndicator(TodoIndicator.DEFAULT_PATTERN.r),
      IssueIndicator(IssueIndicator.DEFAULT_PATTERN.r),
      "if x < 10 then // FIXME #123"
    ),
    (
      "default - finds TODO with multiple spaces before issue",
      TodoIndicator(TodoIndicator.DEFAULT_PATTERN.r),
      IssueIndicator(IssueIndicator.DEFAULT_PATTERN.r),
      "// TODO   #123"
    ),
    (
      "default - finds TODO with multiple tabs before issue",
      TodoIndicator(TodoIndicator.DEFAULT_PATTERN.r),
      IssueIndicator(IssueIndicator.DEFAULT_PATTERN.r),
      "// TODO		#123"
    ),
    (
      "with issue indicator",
      TodoIndicator(TodoIndicator.DEFAULT_PATTERN.r),
      IssueIndicator("PROJ#".r),
      "if x < 10 then // TODO PROJ#123"
    ),
    (
      "with complex issue indicator 1",
      TodoIndicator(TodoIndicator.DEFAULT_PATTERN.r),
      IssueIndicator("PROJ|ABC?#".r),
      "if x < 10 then // TODO AB#123"
    ),
    (
      "with complex issue indicator 2",
      TodoIndicator(TodoIndicator.DEFAULT_PATTERN.r),
      IssueIndicator("PROJ|ABC?#".r),
      "if x < 10 then // TODO ABC#123"
    ),
    (
      "with complex issue indicator 3",
      TodoIndicator(TodoIndicator.DEFAULT_PATTERN.r),
      IssueIndicator("PROJ|ABC?#".r),
      "if x < 10 then // TODO PROJ#123"
    ),
  ).foreach((description, todoIndicator, issueIndicator, content) => {
    writeStringToFileFixture(content).test(description) { file =>
      intercept[AnalysisException] {
        throwingTodoChecker(file, todoIndicator, issueIndicator)
      }
    }
  })

  writeStringToFileFixture("TODO #123", StandardCharsets.ISO_8859_1)
    .test("write file in ISO-8859-1") { file =>
      intercept[AnalysisException] {
        throwingTodoChecker(
          file,
          TodoIndicator(TodoIndicator.DEFAULT_PATTERN.r),
          IssueIndicator(IssueIndicator.DEFAULT_PATTERN.r)
        )
      }
    }

  fileFixture(file => Files.write(file, BigInt(255).toByteArray))
    .test("write byte file, illegal utf-8 sequence is ignored") { file =>
      val errCapture = new ByteArrayOutputStream()
      Console.withErr(errCapture) {
        throwingTodoChecker(
          file,
          TodoIndicator(TodoIndicator.DEFAULT_PATTERN.r),
          IssueIndicator(IssueIndicator.DEFAULT_PATTERN.r)
        )
      }
      val array = errCapture.toByteArray

      assert(array.isEmpty, s"error reported: ${new String(array, StandardCharsets.UTF_8)}")
    }
}

class NoTodoFoundSuite extends TegonalSuite {
  List(
    (
      "default - does not find with suffix",
      TodoIndicator(TodoIndicator.DEFAULT_PATTERN.r),
      IssueIndicator(IssueIndicator.DEFAULT_PATTERN.r),
      "// TODO PROJ#123"
    ),
    (
      "default - does not find with suffix",
      TodoIndicator(TodoIndicator.DEFAULT_PATTERN.r),
      IssueIndicator(IssueIndicator.DEFAULT_PATTERN.r),
      "// TODO PROJ#123"
    ),
  ).foreach((description, todoIndicator, issueIndicator, content) => {
    writeStringToFileFixture(content, StandardCharsets.UTF_8).test(description) { file =>
      todoChecker(file, todoIndicator, issueIndicator)
    }
  })
}
