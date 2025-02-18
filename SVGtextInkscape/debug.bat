@ECHO OFF
javac -g SVGinktxt.java
if %ERRORLEVEL% NEQ 0 (
    EXIT /B 1
)
@ECHO "**============================================================**"
@ECHO "**                          COMMANDS                          **"
@ECHO "**============================================================**"
@ECHO "**  stop at SVGinktxt:<line number> - sets bkp <line number>  **"
@ECHO "**  run - runs the program and stops at bkp                   **"
@ECHO "**  locals - prints local variables                           **"
@ECHO "**  step - steps into                                         **"
@ECHO "**  next - steps out                                          **"
@ECHO "**  print SVGinktxt.<variable> - displays class <variable>    **"
@ECHO "**  list - shows source code and bkp line                     **"
@ECHO "**============================================================**"
start cmd /k "rdb"
java -agentlib:jdwp=transport=dt_socket,server=y,suspend=y,address=5005 SVGinktxt
