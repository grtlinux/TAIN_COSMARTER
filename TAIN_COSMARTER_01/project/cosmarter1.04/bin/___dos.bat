@echo on
@setlocal

::----------------------------------------------
:: ENV

set TAIN_HOME=N:\PROG
if not exist %TAIN_HOME% set TAIN_HOME=..\..

set JAVA_HOME=%TAIN_HOME%\jdk1.7.0_79


set PATH=%JAVA_HOME%\bin;%PATH%

::----------------------------------------------
:: administrator
::net user administrator /active:yes

::----------------------------------------------
:: START

start



@endlocal

