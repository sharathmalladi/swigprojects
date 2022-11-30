!#/bin/bash

export ROOTFOLDER=`pwd`

# Get packages
sudo apt-get update
sudo apt-get install g++
sudo apt-get install swig

# clean up generated and temp files
rm -rf $ROOTFOLDER/swigoutput
rm -f $ROOTFOLDER/cpp/*.o
rm -f $ROOTFOLDER/cpp/*.so

# compile cpp code, first create the object file, then the shared library (.so) file.
cd $ROOTFOLDER/cpp
g++ -c -o sample.o sample.cpp
g++ -shared -o sample.so sample.o

cd $ROOTFOLDER
mkdir -p swigoutput
# create java interface files: look in cpp folder for source files, place java files in swigoutput folder and c++ wrapper in swigoutfolder
swig -java -c++ -Icpp -outdir swigoutput -o swigoutput/sample_wrapper.cpp sample.i 

# Get everything to swigoutput folder before compiling the shared library from the swig generated wrapper c++ file
cd $ROOTFOLDER/swigoutput
cp $ROOTFOLDER/cpp/sample.so .
cp $ROOTFOLDER/cpp/sample.h .
cp $ROOTFOLDER/java/Program.java .

# PIC = position independent code
# The shared library should be prefixed with "lib" for things to work.
g++ -fPIC -I"$JAVA_HOME/include" -I"$JAVA_HOME/include/linux" -shared -o libsamplewrapper.so sample_wrapper.cpp

# Compile the java code
javac Program.java
## jar cvfM Program -C classes .
java -Djava.library.path=. Program
