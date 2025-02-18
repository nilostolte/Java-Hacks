@ECHO OFF
javac SVGinktxt.java
if %ERRORLEVEL% NEQ 0 (
    EXIT /B 1
)
jar cfe SVGinktxt.jar SVGinktxt SVGinktxt.class
