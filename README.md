<!-- for main -->

[![Download](https://img.shields.io/badge/Download-v0.1.0-%23007ec6)](https://hub.docker.com/r/tegonal/workflow-helper/tags?&name=v0.1.0)
[![EUPL](https://img.shields.io/badge/%E2%9A%96-EUPL%201.2-%230b45a6)](https://joinup.ec.europa.eu/collection/eupl/eupl-text-11-12 "License")
[![Build Status Ubuntu](https://github.com/tegonal/workflow-helper/workflows/Ubuntu/badge.svg?event=push&branch=main)](https://github.com/tegonal/workflow-helper/actions?query=workflow%3AUbuntu+branch%3Amain)
[![Newcomers Welcome](https://img.shields.io/badge/%F0%9F%91%8B-Newcomers%20Welcome-blueviolet)](https://github.com/robstoll/atrium/issues?q=is%3Aissue+is%3Aopen+label%3A%22good+first+issue%22 "Ask in slack for help")

<!-- for a specific release -->
<!--
[![Download](https://img.shields.io/badge/Download-v0.1.0-%23007ec6)](https://hub.docker.com/r/tegonal/workflow-helper/tags?&name=v0.1.0)
[![EUPL](https://img.shields.io/badge/%E2%9A%96-EUPL%201.2-%230b45a6)](https://joinup.ec.europa.eu/collection/eupl/eupl-text-11-12 "License")
[![Newcomers Welcome](https://img.shields.io/badge/%F0%9F%91%8B-Newcomers%20Welcome-blueviolet)](https://github.com/robstoll/atrium/issues?q=is%3Aissue+is%3Aopen+label%3A%22good+first+issue%22 "Ask in slack for help")
-->

# Workflow Helper

Utility which helps in managing your workflows.

---
❗ You are taking a *sneak peek* at the next version.
Please have a look at the README of the git tag in case you are looking for the documentation of the corresponding version.
For instance, the [README of v0.1.0](https://github.com/tegonal/workflow-helper/tree/v0.1.0/README.md).

---

Workflow helper is currently featuring the following scripts: 

## TODO Checker
Provides ways to detect unwanted TODOs in files.

Following the output of running `todo-checker --help`:

<todo-checker-help>

<!-- auto-generated, do not modify here but in ./scripts/todo-checker.sh -->
```text
Parameters:
directory            -d|--directory
todo                 -t|--todo-regex
issue                -i|--issue-regex

Examples:
# searches for todos in current directory
# uses default TodoIndicator TODO|FIXME
# uses default IssueIndicator #d+
todo-checker

# searches for todos in directory ./foo with default indicators
todo-checker -d ./foo
todo-checker --directory ./foo

# uses a custom TodoIndicator
todo-checker -t "TODOs?"
todo-checker --todo-regex "TODOs?"

# searches todos for a specific issue by using a custom IssueIndicator
todo-checker -i "#123"
todo-checker --issue-regex "#123"

# searches todos for specific issues by using a custom IssueIndicator
todo-checker -i "#123|op#478"
todo-checker --issue-regex "#123|op#478"
```

</todo-checker-help>

# License
Workflow helper is licensed under [EUPL 1.2](https://joinup.ec.europa.eu/collection/eupl/eupl-text-11-12).
