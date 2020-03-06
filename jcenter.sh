#!/bin/bash
./gradlew clean build bintrayUpload -PdryRun=false
