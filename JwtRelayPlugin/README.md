# A. Kompilera
javac -cp midtier.jar:servlet-api.jar com/example/JwtRelayPlugin.java
jar cf JwtRelayPlugin.jar com/example/JwtRelayPlugin.class


# B. Registrera plugin
I formuläret Data Visualization Module:
    Module Name: JwtRelayPlugin
    Module Type: Visualizer
    Entry Class: com.example.JwtRelayPlugin
    Status: Enabled
    Module Code: Ladda upp JwtRelayPlugin.jar

# C. Skapa ingen Data Visualization Definition – eftersom vi använder View Field, inte DVF.


# Test
Lägg View Field i ditt Progressive View-formulär.

Tryck på knappen.

Bekräfta att du får svaret från url med aktuell användares JWT-token i AR-JWT.