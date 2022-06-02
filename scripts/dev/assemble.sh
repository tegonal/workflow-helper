#!/usr/bin/env bash
set -e

current_dir="$( cd "$(dirname "$0")" >/dev/null 2>&1 ; pwd -P )"
cd "$current_dir/../../"

rm ./target/scala*/workflow-helper-assembly*.jar || true

sbt "set assembly / test := {}; assembly"
cp ./target/scala*/workflow-helper-assembly*.jar ./scripts/workflow-helper.jar
