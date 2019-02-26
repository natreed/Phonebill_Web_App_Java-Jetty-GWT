# phonebill-gwt 

A restful web application built with Jetty and GWT.

This web application is designed to manage phonebills using a database for customers.

## Requirements:
- Java  > 10.0.1
- Maven > 3.6

## Getting Started: LINUX
- ***Clone the repository.*** git clone https://github.com/natreed/Phonebill_Web_App_Java-Jetty-GWT
- ***Build the project:*** mvn package
- ***Start the server:*** mvn jetty:run-war
- ***Go o URL:*** http://localhost:8080/phonebill in you browser
- Instructions for each of the features are listed in the 'Instructions' below. Please read before using features for the first time.

## Features include:

- Creating a phonebill for a customer and adding it to the company database.
- Adding a phonecall to an existing customers phonebil.
- Retrieving and displaying a phonebill by customer name.
- Displaying calls made by a customer with a time range

## Instructions:


***Add a phone call:*** Select 'Add Phonecall' from the dropdown menu, fill in the required fields (name, 
caller number, callee number, start time and end time) time in the boxes

- Start times and end times must include the date formatted as MM/dd/yyyy followed by the time formatted as hh:mm aa
- For example, Start Time = 08/20/2018 08:20 am and End Time =  08/20/2018 08:20 am would be acceptable inputs.
  - Phone numbers for the caller and callee must be formatted as 10 digits (i.e. 123-345-6789).

***Print a phone call entry:*** Select Print Call from the options, add the customer's name and click submit.
  - If a phone bill does not exist for that customer you may create one by selecting Add Phonecall and following the steps
described above.

***Search for calls within a specified date range:*** Select search from the drop down menu.
  - Fill in the name, and date range (start date and end date fields). 
  - Format dates as described in the 'Add Phonecalls' section.
