# CSIS3475-Project1

Database Project
client / server
admins can access higher level commands on clientside
High Level Commands = Add & change user/ add database/ add table
clients to access DB and Tables
clients to add / delete / edit Entries and fields/ sort enries/ search entries
database == folder
tables == document

Login:
Help:
  Enter Server IP Address
  Enter Username with corresponding Password 
  press "Login" to continue
Program:
  connects client with server
  matches username and password with serverside user list
  informs client as to the role of the client (User/Admin)
  
  
ClientTab(home):
Help:
  DataBase Dropdown Menu:
    Choose A DataBase to modify and press go to recieve data
  Table DropDown Menu:
    Choose a Table to Modify and press select to recieve data
  Add:
    Shows GUI for adding new Entry
  Edit:
    Shows GUI for editing selected Entry
  Delete:
    Popup box will confirm the deletion of entry
  Sort DropDown Menu:
    Choose which column to Sort By
  Search Field:
    Enter Query to search by, Hit Search to Proceed
    
Program:
  Allows Users with Client status to modify tables and entries
  shows users the active table graphically
  Maybe: Shows users errors
  
ClientHeaderTable(AddTable):
  Help:
    Input Table Name
    Input Header Name and field Datatypes
    "Add" To add textFields for more headers
  Program:
    Allows users to create tables with as many columns/headers as needed

ClientHeader(DeleteField):
  Help:
    Deletes Current Active Table
  Program:
    Allows users to delete active table
    Confirms user indeed wants to delete table
      
Client(AddField):
  Help:
    Input Header Name and field Datatypes
    Finish to proceed
  program:
    Allows users to add columns to table
    
Client(DeleteField):
  Help:
    Select COlumn You Wish To delete
  Program:
    Allows user to delete colum form table
    Confirms Deletion
    
Client(Add):
  Help:
    enter Values in TextField
  Program:
    Allows users to add entries to table
    Data Validation Occures
  
Client(Edit):
  Help:
    Replace Current Data FIeld with new Data, Complete to proceed.
  Program:
    Allows users to edit selected entry
    Dynamically Generates Fields according to number of Headers and their Datatypes.
    
Client(Delete):
  Help:
    Removes Selected Entry In table
  Program:
    Reminds user to select entry in table if one is not selected
    confirms deletion of selected entry
    
ClientTab(Search):
Help:
  Query:
    Enter Value to find, Search Button to proceed.
  Filter:
    Enter field name, enter operation to implement from dropdown, enter value to base operation on, Search to proceed
  Sort:
    Select Column to sort by in dropdown, Search to proceed.
    
Program:
  Allows users to search for values within selected Table
  Allowes users to filter based on any operation for results
  Allows users to sort items based on column
  
  
Server:
Help:
  
Program:
  Admins may use the server the exact same way as the remote admin terminal with added serverside error logging
  
