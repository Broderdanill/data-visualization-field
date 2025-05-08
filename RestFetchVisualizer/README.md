# 1. Make zip-archive - important to zip folder and content not just content

    zip -r RestFetchVisualizer.arviz RestFetchDef/


# 2. Verify that virtualizer.jar is in Modules

    Module Name: Visualizer
    Module Type: Visualizer
    Entry Class: com.remedy.arsys.visualizer.plugin.BasePlugin
    Status: Enabled
    Module Code: Visualizer.jar

# 3. Submit to form "Data Visualization Definition"

    Definition Name: RestFetchDef
    Sub Type: VARIABLE
    Module Name: Visualizer
    Status: Active
    Simple Definition:
        <?xml version="1.0" encoding="UTF-8"?>
        <DV-Variables>
        <DV name="RestFetchDef" module="Visualizer" operation="update" expression="536870924"/>
        </DV-Variables>
    Complex Definition: RestFetchVisualizer.arviz

# 4. Create DVF in Developer Studio
    Module Type: Visualizer
    Definition Name: RestFetchDef
    Custom Properties: <Can be set to field values like: urlField=$URL$>

# 5. Midtier Deployment
    Midtier will extract content of "RestFetchVisualizer.arviz" to folder: 
        /opt/apache-tomcat/webapps/arsys/PluginDefsCache/Visualizer/

    Verify that XXXXXXXXXXXX files are created here