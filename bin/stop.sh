#!/usr/bin/bash
kill $(jps -l | grep io.github.prolobjectlink.db.prolog.jpl7.swi7.SwiPrologDatabaseConsole | awk '{print $1}')