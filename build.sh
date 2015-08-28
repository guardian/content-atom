#!/bin/bash

LANGUAGES=("js:node")

THRIFT_VERBOSE="-v"

while getopts "q" OPT
do
  case $OPT in
    "q") THRIFT_VERBOSE=""
      ;;
    "v") THRIFT_VERBOSE="-v"
      ;;
  esac
done

function fatal {
    echo >&2 $@
    exit 1
}

shopt -s globstar

THRIFT_DIR="thrift"
OUT_DIR="generated"
JS_LIB_PATH="$OUT_DIR/gen-nodejs"

function make_dir_or_fail () {
    local DIR=$1
    if [[ ! -d $DIR ]]
    then
        echo "Directory \"$OUT_DIR\" does not exist, creating"
        if ! mkdir $DIR
        then
            fatal "Could not create output directory \"$DIR\""
        fi
    fi
}

function thrift {
    make_dir_or_fail $OUT_DIR

    : ${THRIFT_CMD:=$(which thrift)}

    THRIFT_FILES=($THRIFT_DIR/**/*.thrift)

    if [[ ! -x "${THRIFT_CMD}" ]]
    then
        fatal "ERROR: Cannot find thrift. Either install it in the path or set variable" \
              "THRIFT_CMD to its location. (See https://thrift.apache.org/download)"
    fi


    LANG_OPTS=${LANGUAGES[@]/#/--gen }

    for file in "${THRIFT_FILES[@]}"
    do
        $THRIFT_CMD $THRIFT_VERBOSE --recurse -o ${OUT_DIR} $LANG_OPTS "$file"
    done
}

thrift
