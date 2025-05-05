# Kanban Board Viewer (DVF) - v3 (Modern UI)

This version uses Bootstrap 5 and custom styling for a modern, responsive Kanban board.

## Parameters

| Name           | Required | Description                                       |
|----------------|----------|---------------------------------------------------|
| formName       | Yes      | AR form name                                      |
| lanes          | Yes      | JSON array defining swimlanes                     |
| titleField     | Yes      | Field used for card title                         |
| updateField    | Yes      | Field to update when card is moved                |
| cardFields     | No       | Comma-separated fields to show on card            |
| thumbnailField | No       | Field with image URL                              |
| colorField     | No       | Field used to set card background color (hex/RGB) |

## Features

- Responsive layout with Bootstrap
- Drag-and-drop card movement
- Optional thumbnails and color styling
- Dark mode via OS setting (limited to browser theme)

## How to Use

1. Upload this `.arviz` to `AR System Resource Definitions`
2. Add DVF field in Developer Studio
3. Set `Data Visualization Definition` to `kanban.board.viewer`
4. Set parameters:
   - `formName`: e.g., "HPD:Help Desk"
   - `lanes`: JSON array
   - `titleField`: "Summary"
   - `updateField`: "Status"
   - `cardFields`: "Priority,Assignee"
   - `thumbnailField`: "ThumbnailURL"
   - `colorField`: "ColorCode"