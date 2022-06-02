#!/usr/bin/env bash
set -e

if [[ -z "$1" ]]; then
  echo >&2 "no command(s) provided"
  exit 1
fi

eval $1