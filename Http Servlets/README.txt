
SER422 - Web Application Development
----------------------------------------------------------------------------
Author: Amanjot Singh
email: asing225@asu.edu
----------------------------------------------------------------------------
****************************************************************************
Instructions to search on the landing page:
1. The days and languages should be comma seperated.
2. Firstname, lastname and dreamjob will be stored as strings.
****************************************************************************
Instructions for the GET query:
1. Example of get query to display all programmers:
http://localhost:8080/match1/programmers
2. Example query to use filtering:
http://localhost:8080/match1/programmers?firstname=aman&lastname=si&
days=monday,tues&languages=py,java&dreamjob=app
3. firstname, lastname and dreamjob should be normal text.

4. days and languages can be any string and should be comma seperated:
e.g.
	1. days: the options in query should be comma seperated 
 		e.g. days: M,W,T
 		e.g. days: mon,tu,thursday
 		e.g. days: monday,tues,thur		
	2. languages: the options in query should be comma seperated 
 		e.g. languages: Ja,C,Pyth
 		e.g. languages: Java, C, P
 		e.g. languages: va,Python,C 
****************************************************************************
