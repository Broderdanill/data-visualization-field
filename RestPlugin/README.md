# Build jar-file from script
I have attached a script (build.sh) to build jar-file from variables
    
    ./build.sh

# Details

Compile

    javac --release 17 -cp "../arapi/arapi251_build001.jar:../arapi/arpluginsvr251_build001.jar:../arapi/MidTier-25.1.01-SNAPSHOT-MidTier.jar:../arapi/jakarta.servlet-api-6.0.0.jar" com/example/RestPlugin.java

Create jar-file

    jar cf RestPlugin.jar com


# Verify jar-file
jar tf RestPlugin.jar | grep RestPlugin.class

    Shoud respond with:
    com/example/RestPlugin.class


Om du istället får:

    404: plugin inte laddad.

    500: kodfel i processRequest.

# Test the DVF in browser console
EventDispatcher.sendEventToDVF(536870924, "TriggerFetch", "https://jsonplaceholder.typicode.com/todos/1");

# Custom Properties
{ "urlField": "536870913", "targetField": "536870914"}



http://localhost:8080/arsys/plugins/Visualizer/params?server=arserver&flashboard=RestFetchDef&name=RestFetchDef&height=305&width=832&rtl=ltr&fieldid=536870924&windowID=0&schema=DVF_PV&view=pv&isProgressiveView=true