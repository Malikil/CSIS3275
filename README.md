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
  
  
Client(User):
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
  
Client(Admin):
Help:
  no need, they're admins :P
Program:
  Allows admins to access server terminal from remote locations
  shows admins errors performed, and which user performed them
  admins my process user requests to modify databases or users
  
Server:
Help:
  
Program:
  Admins may use the server the exact same way as the remote admin terminal with added serverside error logging
  


Extra:
clients: Request table delete
Admin: Process requests remotely
