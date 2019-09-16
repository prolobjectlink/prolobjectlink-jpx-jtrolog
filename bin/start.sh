#!/usr/bin/bash
java -classpath "$(dirname "$(pwd)")/lib/*" org.prolobjectlink.db.prolog.jtrolog.JTrologDatabaseConsole -m
java -classpath "$(dirname "$(pwd)")/lib/*" org.prolobjectlink.db.prolog.jtrolog.JTrologDatabaseConsole -z 9110