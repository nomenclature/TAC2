#!/bin/sh

# Clear current class files
rm *.class &> /dev/null

# Compile all Java files in the directory above
javac ../*.java -d .

# Compile all Java files in the current directory with junit jar
javac -cp .:jars/junit-4.12.jar *.java

# Run tests with junit
java -cp .:./jars/junit-4.12.jar:./jars/hamcrest-core-1.3.jar org.junit.runner.JUnitCore ItemTests
java -cp .:./jars/junit-4.12.jar:./jars/hamcrest-core-1.3.jar org.junit.runner.JUnitCore ViewTests
java -cp .:./jars/junit-4.12.jar:./jars/hamcrest-core-1.3.jar org.junit.runner.JUnitCore PlayerTests
java -cp .:./jars/junit-4.12.jar:./jars/hamcrest-core-1.3.jar org.junit.runner.JUnitCore InventoryTests
java -cp .:./jars/junit-4.12.jar:./jars/hamcrest-core-1.3.jar org.junit.runner.JUnitCore FractionTests


