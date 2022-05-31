package com.tegonal.todo

import java.nio.file.{Files, Path}
import java.nio.charset.StandardCharsets
import java.nio.file.attribute.{DosFileAttributes, PosixFilePermissions}

class TodoFoundSuite extends munit.FunSuite {
  List(
    (
      "default - finds TODO start of line",
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
      IssueIndicator("PROJ".r),
      "if x < 10 then // TODO PROJ#123"
    ),
    (
      "with complex issue indicator 1",
      TodoIndicator(TodoIndicator.DEFAULT_PATTERN.r),
      IssueIndicator("PROJ|ABC?".r),
      "if x < 10 then // TODO AB#123"
    ),
    (
      "with complex issue indicator 2",
      TodoIndicator(TodoIndicator.DEFAULT_PATTERN.r),
      IssueIndicator("PROJ|ABC?".r),
      "if x < 10 then // TODO ABC#123"
    ),
    (
      "with complex issue indicator 3",
      TodoIndicator(TodoIndicator.DEFAULT_PATTERN.r),
      IssueIndicator("PROJ|ABC?".r),
      "if x < 10 then // TODO PROJ#123"
    ),
  ).foreach((description, todoIndicator, issueIndicator, content) => {
    FunFixture[Path](
      setup = { test =>
        val path = Files.createTempFile("workflow-helper_" + test.name, ".txt")
        Files.writeString(path, content, StandardCharsets.UTF_8)
      },
      teardown = { file =>
        Files.deleteIfExists(file)
      }
    ).test(description) { file =>
      intercept[IllegalStateException] {
        todoChecker(file, todoIndicator, issueIndicator)
      }
    }
  })
}

class NoTodoFoundSuite extends munit.FunSuite {
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
    FunFixture[Path](
      setup = { test =>
        val path = Files.createTempFile("workflow-helper_" + test.name, ".txt")
        Files.writeString(path, content, StandardCharsets.UTF_8)
      },
      teardown = { file =>
        Files.deleteIfExists(file)
      }
    ).test(description) { file =>
      todoChecker(file, todoIndicator, issueIndicator)
    }
  })
}
