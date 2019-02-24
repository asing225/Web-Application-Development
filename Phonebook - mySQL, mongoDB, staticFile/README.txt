**********************************************************************************************************
Amanjot Singh
SER422 Lab2 part2 EC2
**********************************************************************************************************
System Requirements:
Following softwares should be installed on the system.
1. Eclipse JEE 2018,
2. Tomcat Server 8.5
3. mySQL database
**********************************************************************************************************
MySQL Database setup:
1. Open file mysqlSetup.txt and read through the instructions to setup the database for this app.
**********************************************************************************************************
Instructions to run this project:
1. Open the project in eclipse in Java perspective.
2. Go to build.properties and provide the tomcat local directory in tomcat.home property. Make sure your 
	tomcat folder has the port number appended to its name e.g. tomcat-8.5.37-8080
3. Open the rdbm.properties file and change the username and password for your mySQL.
4. Open WEB-INF/classes/resources/phonebook.properties and choose the data store for file system or mySQL.
	Uncomment the line that you want to use as data store.
5. Run the sql server on your system.
6. Now run the build.xml file to deploy the project as war on tomcat webapps.
7. To change the data store after deployment, go to the tomcat webapps directory and change the data
	store in phonebook.properties and save the file.
8. Go to http://localhost:8080/ in your browser and reload the application.
**********************************************************************************************************