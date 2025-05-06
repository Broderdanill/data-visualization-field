#!/bin/bash

# Set paths
SRC_DIR="src"
OUT_DIR="out"
JAR_NAME="pongplugin.jar"
SERVLET_JAR="servlet-api.jar"

# Check for servlet-api.jar
if [ ! -f "$SERVLET_JAR" ]; then
    echo "Missing $SERVLET_JAR. Please place it in the same directory as this script."
    exit 1
fi

# Clean and recreate output directory
rm -rf $OUT_DIR
mkdir -p $OUT_DIR

# Compile Java source
javac --release 17 -cp $SERVLET_JAR -d $OUT_DIR $(find $SRC_DIR -name "*.java")

# Check if compilation succeeded
if [ $? -ne 0 ]; then
    echo "Compilation failed."
    exit 1
fi

# Create JAR file
jar cf $JAR_NAME -C $OUT_DIR .

echo "Build complete: $JAR_NAME"
