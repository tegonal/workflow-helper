#!/usr/bin/env bash
set -e

if [[ -z "$1" ]]; then
  echo >&2 "no version provided"
  exit 1
fi

version=$1
echo "prepare next dev cycle for version $version"

current_dir="$( cd "$(dirname "$0")" >/dev/null 2>&1 ; pwd -P )"
cd "$current_dir/.."/

perl -0777 -i \
  -pe 's/(<!-- for main -->\n)<!--\n([\S\s]*?)-->(\n<!-- for a specific release -->)\n([\S\s]*?)\n(\n# Workflow)/$1\n$2$3\n<!--$4-->\n$5/;' \
  -pe 's/<!(---\n❗ You are taking[\S\s]+?---)>/$1/;' \
  ./README.md

perl -0777 -i \
  -pe "s@(ThisBuild / version := \")[^\"]+\"@\$1$version\"@;" \
  ./build.sbt

git commit -a -m "prepare next dev cycle for $version"