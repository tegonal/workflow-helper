#!/usr/bin/env bash
set -e

echo "Formatting sources..."
sbt scalafmtAll scalafmtSbt

echo "updating help snippets in readme..."
current_dir="$( cd "$(dirname "$0")" >/dev/null 2>&1 ; pwd -P )"
"$current_dir/update-readme-help.sh"
