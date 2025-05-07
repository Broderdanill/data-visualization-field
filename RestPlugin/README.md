# Compile
javac --release 17 -cp "../arapi/arapi251_build001.jar:../arapi/arpluginsvr251_build001.jar:../arapi/MidTier-25.1.01-SNAPSHOT-MidTier.jar:../arapi/jakarta.servlet-api-6.0.0.jar" com/example/RestPlugin.java

# Create jar
jar cfv RestPlugin.jar -C com/example/ .

# Verify jar-file
jar tf RestPlugin.jar | grep RestPlugin.class

    Shoud respond with:
    com/example/RestPlugin.class