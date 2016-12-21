#!/usr/bin/env bash

input=$1
output=$2

if [[ -n "$input" ]]; then
    java -jar library.jar "$input" "$output"
else
    java -jar library.jar
fi
