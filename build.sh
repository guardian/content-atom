#!/bin/bash

LANGUAGES=("js:node" "java")

THRIFT_VERBOSE="-v"
TAR=$(which tar)

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

THRIFT_DIR="thrift"
OUT_DIR="generated"
PUBLISHED_DIR="published"

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

    THRIFT_FILE="$THRIFT_DIR/contentatom.thrift"

    if [[ ! -x "${THRIFT_CMD}" ]]
    then
        fatal "ERROR: Cannot find thrift. Either install it in the path or set variable" \
              "THRIFT_CMD to its location. (See https://thrift.apache.org/download)"
    fi


    LANG_OPTS=${LANGUAGES[@]/#/--gen }

    $THRIFT_CMD $THRIFT_VERBOSE --recurse -o ${OUT_DIR} $LANG_OPTS "$THRIFT_FILE"
}

function jslib() {
    make_dir_or_fail $PUBLISHED_DIR
    local OUT_FILE=$(realpath $PUBLISHED_DIR)/content-atom-js.tar
    cp package.json $JS_LIB_PATH
    (cd $JS_LIB_PATH; \
     $TAR -cvf $OUT_FILE ./)
    echo "Created $OUT_FILE"
}

thrift
jslib

