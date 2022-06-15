#!/usr/bin/env bash
set -e

current_dir="$( cd -- "$( dirname -- "${BASH_SOURCE[0]:-$0}"; )" &> /dev/null && pwd 2> /dev/null; )";
cd "$current_dir/../../"

rm ./target/scala*/workflow-helper-assembly*.jar || true

sbt "set assembly / test := {}; assembly"
cp ./target/scala*/workflow-helper-assembly*.jar ./scripts/workflow-helper.jar
