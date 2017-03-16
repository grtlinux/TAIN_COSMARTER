@echo off
@setlocal

::----------------------------------------------
:label_env1
set CURRENT_DIR=%CD%
set ENV_CMD=%CURRENT_DIR%\_env.cmd

call %ENV_CMD% _env.cmd
if errorlevel 1 goto label_end

::----------------------------------------------
:label_start

set JVM_OPTIONS=
set JVM_OPTIONS=%JVM_OPTIONS% -Dtain.co.kr=TAIN
set JVM_OPTIONS=%JVM_OPTIONS% -DrsrcMainClass=tain.kr.com.proj.cosmarter.v04.main.client.MainCoSmarterClient
set JVM_OPTIONS=%JVM_OPTIONS% -Dtain.cosmarter.v04.client.log.flag=false
set JVM_OPTIONS=%JVM_OPTIONS% -Dtain.cosmarter.v04.client.charset=euc-kr
set JVM_OPTIONS=%JVM_OPTIONS% -Dfile.encoding=euc-kr

set JAR_FILE=%CURRENT_DIR%\tain-cosmarter-1.04.jar

java %JVM_OPTIONS% -jar %JAR_FILE%

::----------------------------------------------
:label_end
echo The end of the command

@endlocal




