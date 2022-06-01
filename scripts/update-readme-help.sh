#!/usr/bin/env bash
set -e

current_dir="$( cd "$(dirname "$0")" >/dev/null 2>&1 ; pwd -P )"
cd "$current_dir/.."/

sbt "set assembly / test := {}; assembly"
cp ./target/scala*/workflow-helper-assembly*.jar ./scripts/workflow-helper.jar

function replaceHelpForCommand(){
  local command=$1
  local help=$(./scripts/${command}.sh --help)
  perl -0777 -i \
     -pe "s@<${command}-help>[\S\s]+</${command}-help>@<${command}-help>\n\n<!-- auto-generated, do not modify here but in ./scripts/${command}.sh -->\n\`\`\`text$help\n\`\`\`\n\n</${command}-help>@g;" \
     -pe "s/\033\[(1;\d{2}|0)m//g" \
     README.md
}
replaceHelpForCommand todo-checker
