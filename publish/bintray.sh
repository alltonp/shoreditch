#!/bin/bash
mkdir -p ~/.bintray
eval "echo \"$(< ./publish/bintray.template)\"" > ~/.bintray/.credentials
eval "echo \"$(< ./publish/sonatype.template)\"" > ../sonatype.sbt
#openssl des3 -d -salt -in ./publish/sonatype.asc.enc -out ./publish/sonatype.asc -k "$SECRET"