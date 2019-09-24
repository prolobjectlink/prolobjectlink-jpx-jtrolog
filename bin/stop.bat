@echo off
FOR /F "tokens=1,2 delims= " %%G IN ('jps -l') DO IF %%H==org.prolobjectlink.db.prolog.jpl7.swi7.SwiPrologDatabaseConsole taskkill /F /PID %%G