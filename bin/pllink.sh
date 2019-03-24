#!/usr/bin/bash
java -classpath "$(dirname "$(pwd)")/lib/*" org.prolobjectlink.prolog.jtrolog.JTrologDatabaseConsole ${1+"$@"}