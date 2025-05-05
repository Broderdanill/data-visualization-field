# Dynamic REST Fetch DVF

This Data Visualization Field allows you to fetch any AR REST API (or external) URL defined in a field and place the result into another field.

## How it works

1. This DVF takes 2 input parameters:
   - `urlField`: The name of a field in the form that contains a REST URL
   - `targetField`: The Field ID (number) of the field where the result will be stored

2. The DVF reads the `urlField` value using `ARFieldGetValue(...)`

3. It performs a REST GET request to that URL

4. The returned JSON is stringified and inserted into the field with ID `targetField` via `ARFieldSetValue(...)`

## Example use case

- You create two character fields:
  - Field A: "RestUrl" (contains: `/api/arsys/v1/entry/User?fields=Full Name`)
  - Field B: "ResultText" (Field ID: 536870999)

- Configure the DVF with:
  - urlField = RestUrl
  - targetField = 536870999

- When the DVF loads, it performs the fetch and places the output into "ResultText"

## Setup steps

1. Import the `.arviz` file into AR System Resource Definitions
2. Add a DVF field to your PV form layout
3. Map the input parameters:
   - urlField = the name of your input field
   - targetField = the ID of your result field
4. Ensure both fields exist in the PV
5. Open the form in Mid Tier and test





{ "urlField": "536870913", "targetField": "536870914" }