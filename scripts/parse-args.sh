#!/usr/bin/env bash
set -e

function checkAllArgumentsSet {
  local -n parameterNames=$1
  local good=1
  for paramName in "${!parameterNames[@]}"; do
    if ! [ -v "$paramName" ]; then
      printf >&2 "\033[1;31mERROR: %s not set\n\033[0m" "$paramName"
      good=0
    fi
  done
  if [ "$good" -eq 0 ]; then
    echo >&2 ""
    echo >&2 "following the help documentation:"
    echo >&2 ""
    printHelp >&2 parameterNames
    echo >&2 ""
    echo >&2 "use --help to see this list"
    exit 1
  fi
}

function printHelp {
  local -n names=$1
  printf "\n\033[1;33mParameters:\033[0m\n"
  for paramName in "${!names[@]}"; do
    printf "%-20s %s\n" "$paramName" "${parameterNames[$paramName]}"
  done
  if [ -v examples ]; then
    printf "\n\033[1;33mExamples:\033[0m\n"
    echo "$examples"
  fi
}

function parseArguments {
  local -n parameterNames=$1
  shift

  while [[ $# -gt 0 ]]; do
    argName="$1"
    expectedName=0
    for paramName in "${!parameterNames[@]}"; do
      regex="^(${parameterNames[$paramName]})$"
      if [[ "$argName" =~ $regex ]]; then
        # that's where the black magic happens, we are assigning to global variables here
        printf -v "${paramName}" "%s" "$2"
        expectedName=1
        shift
      fi
    done
    if [[ "$argName" == "--help" ]]; then
      printHelp parameterNames
      exit 0
    fi
    if [ "$expectedName" -eq 0 ]; then
      if [[ "$argName" =~ ^- ]]; then
        printf "\033[1;33mignored argument %s (and its value)\033[0m\n" "$argName"
      fi
    fi
    shift
  done
}
