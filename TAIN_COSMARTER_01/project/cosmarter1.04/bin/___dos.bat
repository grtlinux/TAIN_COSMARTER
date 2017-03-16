@echo off
@setlocal

::----------------------------------------------
:label_env1
set CURRENT_DIR=%CD%
set ENV_CMD=%CURRENT_DIR%\_env.cmd

call %ENV_CMD% _env.cmd
if errorlevel 1 goto label_end

::----------------------------------------------
:label_env2




goto label_start

::----------------------------------------------
:: administrator
::net user administrator /active:yes

::----------------------------------------------
:label_start
start

::----------------------------------------------
:label_end
echo The end of the command

@endlocal
