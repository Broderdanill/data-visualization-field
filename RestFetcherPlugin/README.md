# A. Kompilera
javac --release 17 -cp "../api/*" com/example/RestFetcherPlugin.java
jar cf RestFetcherPlugin.jar com/example/*.class

# B. Registrera plugin
I formuläret Data Visualization Module:
    Module Name: RestFetcher
    Module Type: Visualizer
    Entry Class: com.example.RestFetcher
    Status: Enabled
    Module Code: Ladda upp RestFetcher.jar

# C. Skapa ingen Data Visualization Definition – eftersom vi använder View Field, inte DVF.


# Test
Lägg View Field i ditt Progressive View-formulär.

Tryck på knappen.

Bekräfta att du får svaret från url med aktuell användares JWT-token i AR-JWT.