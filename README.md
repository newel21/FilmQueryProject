## FilmQuery Project

### Week 6 Skill Distillery Weekend Homework

### How the program was created:
1. Created a DatabaseAccessor interface with film, List of Actors, and List of films.
2. Connected to the database and created a DatabaseAccessorObject class to create objects as we pull data from the database.
3. Created the film and actor class
4. Created the FilmQueryApp class with the main method so we can test and run the program

### How the program works:
1. The user is prompted to search films by either Film ID or a keyword.
2. If the user selects the first option, then the user is asked to enter ID (which is an integer). We have a catch statement in case the user enter a non-integer input.
3. If the user selects the second options, then the user is asked to enter a keyword (in a form of string). We also have a catch statement in case the input is not found in the database or isn't matching.
4. If the user selects the third options, then the program ends.

### Additional Notes:
We just started learning how Java interacts with the database using the JDBC statement and preparedstatement interface.

### Tools/Techniques used:
1. JDBC and preparedstatement to send SQL commands and
receive data from the Database
2. List/ArrayList to store and retrieve multiple data
3. switch statement to prompt user to select from options
4. try and catch to deal with exceptions
5. foreach loop to iterate through the ArrayList and print
items nicely instead of using the Array toString.
