javac -d bin @sources.txt
del utf8.jar
cd bin
jar cfe utf8.jar com.UTF8FileReader com/UTF8FileReader.class *
mv utf8.jar ..
cd ..
