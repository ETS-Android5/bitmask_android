#!/bin/bash

GO_VERSION=go1.12.7.linux-amd64

if [[ $(ls -A ${GO_VERSION}.tar.gz) ]]
then
    echo "reusing downloaded golang bundle"
else
    echo "installing go lang bundle ${GO_VERSION}.tar.gz"
    curl -o $GO_VERSION.tar.gz https://dl.google.com/go/$GO_VERSION.tar.gz
fi

if [[ -d ./golang ]]
then
    if [[ $(ls -A ./golang/*) ]]
    then
        rm -r ./golang/*
    fi
else
    mkdir ./golang
fi
tar -C ./golang -xzf $GO_VERSION.tar.gz


export GOPATH=`pwd`
export GO_LANG=`pwd`/golang/go/bin
export GO_COMPILED=`pwd`/bin
PATH="${GO_LANG}:${GO_COMPILED}:${PATH}"

./golang/go/bin/go get golang.org/x/mobile/cmd/gomobile
./golang/go/bin/go env
echo "getting gomobile..."
./golang/go/bin/go get golang.org/x/mobile/cmd/gomobile
echo "initiating gomobile..."
./bin/gomobile init

