#!/usr/bin/env bash
set -e

if [[ -z "$1" ]]; then
  echo >&2 "no command(s) provided"
  exit 1
fi

# for gitlab which passes sh -c, in this case we just exec a bash
if [[ "$1" =~ ^sh|bash ]]; then
 /usr/bin/env bash
else
  eval "$@"
fi

