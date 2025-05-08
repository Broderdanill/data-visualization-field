#!/bin/bash

# ======= Konfigurationsvariabler =======
JAVA_VERSION=17
CLASSPATH="../arapi/arapi251_build001.jar:../arapi/arpluginsvr251_build001.jar:../arapi/MidTier-25.1.01-SNAPSHOT-MidTier.jar:../arapi/jakarta.servlet-api-6.0.0.jar"
SOURCE_FILE="com/example/RestPlugin.java"
JAR_NAME="RestPlugin.jar"
PACKAGE_DIR="com"
# =======================================

echo "Kompilerar med Java version $JAVA_VERSION..."
javac --release "$JAVA_VERSION" -cp "$CLASSPATH" "$SOURCE_FILE"

if [ $? -ne 0 ]; then
    echo "Kompilering misslyckades."
    exit 1
fi

echo "Skapar JAR-fil: $JAR_NAME..."
jar cf "$JAR_NAME" "$PACKAGE_DIR"

if [ $? -ne 0 ]; then
    echo "JAR-skapande misslyckades."
    exit 1
fi

echo "Klart! Skapade $JAR_NAME"
