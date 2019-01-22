@echo off
set APP_HOME=%~f0
echo %APP_HOME%
set APP_HOME=%APP_HOME:\bin\service.bat=%
echo %APP_HOME%
:::::::::: set classpath
set CLASSPATH=%CLASSPATH%;%APP_HOME%\config
set CLASSPATH=%CLASSPATH%;%APP_HOME%\lib\*
echo %CLASSPATH%
java -classpath %CLASSPATH% com.chinacscs.platform.search.StartUp --app.home=%APP_HOME%
pause