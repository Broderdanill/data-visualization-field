# Chart Data Viewer (DVF)

This Data Visualization Field renders a chart based on AR form data using Chart.js.

## Parameters

| Name          | Required | Description                                   |
|---------------|----------|-----------------------------------------------|
| formName      | Yes      | AR form name to pull data from                |
| qualification | No       | AR qualification string (e.g., 'Status'="New")|
| groupField    | Yes      | Field to group data by (e.g., "Status")       |
| chartType     | No       | Type of chart: "bar", "pie", "line"           |
| title         | No       | Title of the chart                            |

## How to Use

1. Upload the `.arviz` file in `AR System Resource Definitions`:
   - Type: `Data Visualization Definition`
   - Name: `chart.data.viewer`

2. In Developer Studio:
   - Add a Data Visualization Field
   - Set Data Visualization Definition: `chart.data.viewer`
   - Configure Input Parameters:
     - `formName`: e.g., "CHG:Change Request"
     - `qualification`: e.g., "'Status' = "Scheduled""
     - `groupField`: e.g., "Status"
     - `chartType`: "bar", "pie", or "line"
     - `title`: "My Change Overview"

3. Load the form to see the chart render dynamically.