@ECHO OFF
javac SVGfix.java
if %ERRORLEVEL% NEQ 0 (
    EXIT /B 1
)
jar cfe SVGfix.jar SVGfix SVGfix.class
