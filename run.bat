@echo off
echo Compiling Curse of Zed...
javac -encoding UTF-8 src\CurseOfZed.java -d out
if %errorlevel% neq 0 (
    echo Compilation failed. Make sure Java JDK is installed.
    pause
    exit /b 1
)
echo Launching...
java -cp out CurseOfZed
pause
