REST Fetch DVM (Data Visualization Module)
-------------------------------------------------

Denna modul låter dig göra ett REST-anrop från ett DVM-fält i ett formulär i BMC Helix AR System.

INSTRUKTIONER:

1. Packa denna mapp till en ZIP-fil (eller använd bifogad ZIP).
2. Ladda upp ZIP-filen till formuläret "Data Visualization Module" som "Module Code".
   - Sätt 'Entry Class' till: com.bmc.arsys.visualizer.HtmlBasedVisualizer
3. Skapa en post i "Data Visualization Definition" som pekar på denna modul.
4. I Developer Studio:
   - Lägg till ett Data Visualization-fält.
   - Sätt "Component Name" till: rest-fetch
   - Sätt "Data Source" till: Data Visualization Module
   - I "Custom Properties", skriv:
     { "url": "https://api.example.com/data", "returnFieldId": "536870912" }
     (ersätt med faktiska värden)

5. Lägg till ett character-fält med Field ID 536870912 för att ta emot resultatet.

Vid laddning kommer DVM-modulen hämta JSON från angiven URL och skriva resultatet till det angivna fältet.

Utvecklad för BMC Helix AR 25.1 / 25.2.
