#!/usr/bin/env bash
set -e

echo "Formatting sources..."
sbt scalafmtAll scalafmtSbt

echo "updating help snippets in readme..."
current_dir="$( cd -- "$( dirname -- "${BASH_SOURCE[0]:-$0}"; )" &> /dev/null && pwd 2> /dev/null; )";
"$current_dir/update-readme-help.sh"
