@echo off

::----------------------------------------------
:label_check
if not ""%1"" == ""_env.cmd"" goto label_exit

::----------------------------------------------
:label_env
if not "%TAIN_HOME%" == "" goto label_end

set TAIN_HOME=N:\PROG
set JAVA_HOME=%TAIN_HOME%\jdk1.7.0_79
set CATALINA_HOME=%TAIN_HOME%\apache-tomcat-7.0.75

set PATH=%JAVA_HOME%\bin;%CATALINA_HOME%\bin;%PATH%

::----------------------------------------------
:label_start
echo TAIN_HOME=%TAIN_HOME%
echo JAVA_HOME=%JAVA_HOME%
echo CATALINA_HOME=%CATALINA_HOME%
echo The environment is OK!!!
goto label_end

::----------------------------------------------
:label_exit
echo ERROR: don't make the enviroment
exit /b 1

::----------------------------------------------
:label_end
exit /b 0

