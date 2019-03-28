#!/usr/bin/bash
java -classpath "$(dirname "$(pwd)")/lib/*" org.prolobjectlink.db.prolog.jtrolog.JTrologDatabaseConsole ${1+"$@"}