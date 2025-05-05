# Change Calendar Viewer (DVF)

This Data Visualization Field renders AR form entries in a responsive calendar view using FullCalendar.js.

## Parameters (input to DVF)
- `formName`: Name of the AR form to pull entries from
- `qualification`: AR qualification to filter records (ex: 'Status' = "Scheduled")
- `startField`: Field containing start datetime
- `endField`: Field containing end datetime
- `titleField`: Field to use for event titles

## How to Use

1. Upload this ARVIZ file in `AR System Resource Definitions` with:
   - Name: `calendar.change.viewer`
   - Type: `Data Visualization Definition`

2. In Developer Studio:
   - Add a Data Visualization Field to your form/view
   - Set Visualization: `calendar.change.viewer`
   - Set Input Parameters (formName, qualification, etc.)
   - Map each parameter to a character field or literal

3. Trigger the view:
   - On load, it will query entries from the form and render a calendar.

## Notes
- Uses FullCalendar from CDN
- Works with Progressive View only
- Events are clickable and support day/week/month view