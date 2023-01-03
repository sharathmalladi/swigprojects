# How to use swig to generate an interface with c++ code and call that from a java class.

## Step 0: What environment we are using
```bash
## `ls $JAVA_HOME/include` shows the following files:
## classfile_constants.h  jawt.h  jdwpTransport.h  jni.h  jvmticmlr.h  jvmti.h  linux  sizecalc.h
## `ls $JAVA_HOME/include/linux` shows the following files:
## jawt_md.h  jni_md.h
## `cat /etc/issue` shows the following output:
## Ubuntu 22.04.1 LTS
## `java -version` => openjdk version "17.0.5" 2022-10-18
## `javac -version` => javac 17.0.5
export JAVA_HOME='/usr/lib/jvm/java-11-openjdk-amd64'
```

## Step 1: Install packages
```bash
sudo apt-get update  
sudo apt-get install g++  
sudo apt-get install swig  
```

## Step 2: Clean up generated and temp files
```bash
export ROOTFOLDER=`pwd`
rm -rf $ROOTFOLDER/swigoutput  
rm -f $ROOTFOLDER/cpp/*.o  
rm -f $ROOTFOLDER/cpp/*.so  
```

## Step 3: Compile the sample c++ project
```bash
cd $ROOTFOLDER/cpp
# Compile cpp code
g++ -c -o sample.o sample.cpp
# Create the shared object file
g++ -shared -o sample.so sample.o
```

## Step 4: Create the java classes from the SWIG interface
```bash
cd $ROOTFOLDER
mkdir -p swigoutput
# create java classes: look in 'cpp' folder for source files, place java files in 'swigoutput' folder and cpp wrapper file in a file named 'swigoutput/sample_wrapper.cpp'.
swig -java -c++ -Icpp -outdir swigoutput -o swigoutput/sample_wrapper.cpp sample.i
```

## Step 5: Copy files to one common location

```bash
# Copy everything to swigoutput folder before compiling the shared library from the swig generated wrapper c++ file
cd $ROOTFOLDER/swigoutput
cp $ROOTFOLDER/cpp/sample.so .
cp $ROOTFOLDER/cpp/sample.h .
cp $ROOTFOLDER/java/Program.java .
```

## Step 6: Build new shared library that references wrapper code and the c++ library

```bash
# PIC = position independent code
# The shared library should be prefixed with "lib" for things to work.
# Include the c++ source code binary (sample.so) as well as the swig generated c++ file and create a new shared library.
g++ -fPIC -I"$JAVA_HOME/include" -I"$JAVA_HOME/include/linux" -shared -o libsamplewrapper.so sample_wrapper.cpp sample.so
```

## Step 7: Compile the java code to a jar file
```bash
# The java file references the library by calling System.loadLibrary("samplewrapper") since the library is called libsamplewrapper.so in the earlier step (by removing the "lib" prefix).
javac *.java
jar cvf Program.jar *.class
```

## Step 8: Run the java program
```bash
# LD_LIBRARY_PATH must contain the folder that has sample.so so it can be found by libsamplewrapper.so.
export LD_LIBRARY_PATH=$LD_LIBRARY_PATH:`pwd`
# java.library.path is current folder since that is where the libsamplewrapper.so file is present.
java -Djava.library.path=. Program
```
