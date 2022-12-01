# Swig interfaces for rlclientlib and sample call from a java class.

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
```

## Step 1: Install packages
```bash
sudo apt-get update -y
sudo apt-get install -y g++
sudo apt-get install -y swig
sudo apt-get install -y nuget
```

## Step 2: Clean up generated and temp files
```bash
export ROOTFOLDER=`pwd`
rm -rf $ROOTFOLDER/swigoutput
rm -f $ROOTFOLDER/*.so
```

## Step 3: Download rlclientlib github project and nuget package
```bash
cd $ROOTFOLDER
# git submodule add https://github.com/VowpalWabbit/reinforcement_learning.git
# Fetch the reinforcement_learning github project.
git submodule update --init
# Checkout 0.2.0 tag since the nuget project was built on that tag.
cd $ROOTFOLDER/rlclientlib/reinforcement_learning
git checkout 0.2.0

# nuget command does not work. Needs to be fixed. Using curl instead.
# nuget install RL.Net -Prerelease -Version 0.2.0
curl -o rl.net.nupkg https://globalcdn.nuget.org/packages/rl.net.0.2.0.nupkg
# unzip the .so file for linux from the nuget package.
unzip -j rl.net.nupkg runtimes/linux-x64/native/librlnetnative.so librlnetnative.so
rm -f rl.net.nupkg
```

## Step 4: Create the java classes from the SWIG interface
```bash
cd $ROOTFOLDER
mkdir -p swigoutput
# create java classes: look in 'cpp' folder for source files, place java files in 'swigoutput' folder and cpp wrapper file in a file named 'swigoutput/sample_wrapper.cpp'.
swig -java -c++ -I$ROOTFOLDER/reinforcement_learning/include -outdir swigoutput -o swigoutput/rlclient_wrapper.cpp rlclientlib.i
```

## Step 5: Copy files to one common location

```bash
# Copy everything to swigoutput folder before compiling the shared library from the swig generated wrapper c++ file
cd $ROOTFOLDER/swigoutput
cp $ROOTFOLDER/librlnetnative.so .
cp $ROOTFOLDER/reinforcement_learning/include/*.h .
cp $ROOTFOLDER/java/Program.java .
```

## Step 6: Build new shared library that references wrapper code and the c++ library

```bash
# PIC = position independent code
# The shared library should be prefixed with "lib" for things to work.
# Include the c++ source code binary (sample.so) as well as the swig generated c++ file and create a new shared library.
g++ -fPIC -I"$JAVA_HOME/include" -I"$JAVA_HOME/include/linux" -I"$ROOTFOLDER/reinforcement_learning/ext_libs/string-view-lite" -I"$ROOTFOLDER/reinforcement_learning/include" -shared -o librlclientlib.so rlclient_wrapper.cpp librlnetnative.so > out.txt 2>&1
```

## Step 7: Compile the java code to a jar file
```bash
# The java file references the library by calling System.loadLibrary("rlclientlib") since the library is called librlclientlib.so in the earlier step (by removing the "lib" prefix).
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
