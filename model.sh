#!/usr/bin/bash
java -classpath "$(pwd)/lib/*" io.github.prolobjectlink.db.prolog.jtrolog.JTrologDatabaseConsole -m
java -classpath "$(pwd)/lib/*" io.github.prolobjectlink.db.prolog.jtrolog.JTrologDatabaseConsole -j  "$(pwd)/lib/prolobjectlink-jpx-model.jar"