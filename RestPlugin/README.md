# Compile
javac --release 17 -cp "../arapi/arapi251_build001.jar:../arapi/arpluginsvr251_build001.jar:../arapi/MidTier-25.1.01-SNAPSHOT-MidTier.jar:../arapi/jakarta.servlet-api-6.0.0.jar" com/example/RestPlugin.java

javac --release 17 -cp "../arapi/arapi251_build001.jar:../arapi/arpluginsvr251_build001.jar:../arapi/MidTier-25.1.01-SNAPSHOT-MidTier.jar:../arapi/jakarta.servlet-api-6.0.0.jar" com/example/*.java

# Create jar
jar cfv restplugin.jar com/example/*.class
