#!/bin/bash

var_path=$(dirname -- $(readlink -fn -- "$0"))

cp bhaga-challProject/src/project/*.java $var_path

javac BhagaChallServer.java 

javac BhagaChallClient.java 

rm *.class
