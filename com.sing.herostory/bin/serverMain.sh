#!/bin/bash
java_main_clazz=com.sing.herostory.ServerMain

java_cmd="java -server -cp .../lib/* -xmx2048 ${java_main_clazz}"
nohup $java_cmd > /dev/null &