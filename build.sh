#!/bin/bash

LANGUAGES=("js:node" "java")

# if this is empty then thrift won't run in verbose mode
VERBOSE="-v"

THRIFT_DIR="thrift"
: ${THRIFT_CMD:=$(which thrift)}

shopt -s globstar
THRIFT_FILES=($THRIFT_DIR/**/*.thrift)

function fatal {
    echo >&2 $@
    exit 1
}

if [[ ! -x "${THRIFT_CMD}" ]]
then
    fatal "ERROR: Cannot find thrift. Either install it in the path or set variable" \
          "THRIFT_CMD to its location. (See https://thrift.apache.org/download)"
fi


LANG_OPTS=${LANGUAGES[@]/#/--gen }

$THRIFT_CMD $VERBOSE -o ${THRIFT_DIR} $LANG_OPTS ${THRIFT_FILES[@]}
