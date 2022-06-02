#!/usr/bin/env bash
set -e

if [[ -z "$1" ]]; then
  echo >&2 "no version provided"
  exit 1
fi

version=$1
echo "prepare release for version $version"

current_dir="$( cd "$(dirname "$0")" >/dev/null 2>&1 ; pwd -P )"
cd "$current_dir/../../"

"$current_dir/cleanup.sh"

perl -0777 -i \
  -pe 's/(<!-- for main -->\n)\n([\S\s]*?)(\n<!-- for a specific release -->\n)<!--\n([\S\s]*?)-->\n(\n# Workflow)/$1<!--\n$2-->$3\n$4\n$5/;' \
  -pe "s@(<!-- for a specific release -->[\S\s]+?https://img.shields.io/badge/Download-)[^-]+(-.*?https://hub.docker.com/r/tegonal/workflow-helper/tags\?&name=)[^\)]+\)@\${1}$version\${2}$version\)@;" \
  -pe 's/(---\n❗ You are taking[\S\s]+?---)/<!$1>/;' \
  ./README.md

perl -0777 -i \
  -pe "s@(ThisBuild / version := \")[^\"]+\"@\${1}$version\"@;" \
  ./build.sbt

perl -0777 -i \
  -pe "s@(image: 'docker://tegonal/workflow-helper:)[^']+'@\${1}$version'@;" \
  ./action.yml

find ./.github/workflows -name "*.yml" | xargs perl -0777 -i \
  -pe "s#(uses: tegonal/workflow-helper@)#\${1}$version#;"

git commit -a -m "$version"