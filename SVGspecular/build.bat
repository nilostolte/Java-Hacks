@ECHO OFF
javac SVGspecular.java
if %ERRORLEVEL% NEQ 0 (
    EXIT /B 1
)
jar cfe SVGspecular.jar SVGspecular SVGspecular.class
