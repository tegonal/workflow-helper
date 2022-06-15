#!/usr/bin/env bash
set -e

declare -A params
declare -A help

declare directory todo issue
params[directory]='-d|--directory'
help[directory]='(optional) specifies which directory (including sub directories) shall be analysed -- default: .'

params[todo]='-t|--todo-regex'
help[todo]='(optional) regex used to match todos -- default: TODO|FIXME'

params[issue]='-i|--issue-regex'
help[issue]='(optional) regex used to match an issue number -- default: #\d+'

declare examples
examples=$(cat << EOM
# searches for todos in current directory
# uses default TodoIndicator TODO|FIXME
# uses default IssueIndicator #\d+
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
EOM
)

current_dir="$( cd -- "$( dirname -- "${BASH_SOURCE[0]:-$0}"; )" &> /dev/null && pwd 2> /dev/null; )";
. "$current_dir/parse-args.sh"

parseArguments params "$@"
if ! [ -v directory ]; then directory="."; fi
if ! [ -v todo ]; then todo=""; fi
if ! [ -v issue ]; then issue=""; fi
checkAllArgumentsSet params


java -cp "$current_dir/workflow-helper.jar" com.tegonal.todo.todoChecker "$directory" "$todo" "$issue"