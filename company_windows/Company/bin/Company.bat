@echo off
set DIR="%~dp0"
set JAVA_EXEC="%DIR:"=%\java"



pushd %DIR% & %JAVA_EXEC% %CDS_JVM_OPTS% -cp sqlite-jdbc-3.34.0.jar -p "%~dp0/../app" -m Company/org.openjfx.Main  %* 2> nul & popd
