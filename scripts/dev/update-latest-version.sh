#!/usr/bin/env bash
set -e

if [[ -z "$1" ]]; then
  echo >&2 "no version provided"
  exit 1
fi

version=$1
echo "set version $version as latest download"

current_dir="$( cd "$(dirname "$0")" >/dev/null 2>&1 ; pwd -P )"
cd "$current_dir/../../"

perl -0777 -i \
  -pe "s@(<!-- for main -->[\S\s]+?https://img.shields.io/badge/Download-)[^-]+(-.*?https://hub.docker.com/r/tegonal/workflow-helper/tags\?&name=)[^\)]+\)@\$1$version\$2$version\)@;" \
  -pe "s@(For instance, the \[README of )v[^\]]+(\].*tree/)v[^/]+/@\$1$version\$2$version/@;" \
  ./README.md
