# 1. Kompilera
javac --release 17 -cp "../api/*" com/example/RestFetcherPlugin.java
jar cf RestFetcherPlugin.jar com/example/*.class

# 2. Registrera plugin
I formuläret Data Visualization Module:
    Module Name: RestFetcher
    Module Type: Visualizer
    Entry Class: com.example.RestFetcher
    Status: Enabled
    Module Code: Ladda upp RestFetcher.jar

# 3. Importera Demoformulär
    Importera formulär i Developer Studio

# 4. Testa
    Test görs genom att....
