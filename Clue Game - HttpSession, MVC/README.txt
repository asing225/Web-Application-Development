****************************************************************************************************************
Amanjot Singh
SER422 Lab2 Part1 Instructions
System requirements: Eclipse JEE, Tomcat 8.5

****************************************************************************************************************
Instructions to run this project on Tomcat using terminal:
1. This is a dynamic web project, so open Eclipse JEE perspetive and import the project from
	file system.
2. Right click on the project, go to the properties -> java build path.
3. Under libraries -> add external jars.
4. Add the servlet-api.jar from the project directory under lib folder.
5. To run the project, right click on project and export as war file.
6. Select the war file location as the web-apps directory of your tomcat installation directory.
7. Start tomcat.
8. You can see the application running under the tomcat applications with the same name as the 
	project name.
****************************************************************************************************************
Instructions to run this project on Tomcat embedded in eclipse:
1. This is a dynamic web project, so open Eclipse JEE perspetive and import the project from
	file system.
2. Right click on the project, go to the properties -> java build path.
3. Under libraries -> add external jars.
4. Add the servlet-api.jar from the project directory under lib folder.
5. Create a server in eclipse using tomcat directory. Use this link to setup tomcat in your 
	eclipse:
	https://help.eclipse.org/neon/index.jsp?topic=%2Forg.eclipse.stardust.docs.wst%2Fhtml%2Fwst-integration%2Fconfiguration.html
6. Once server is setup, right click on your dynamic project and run the project on server.
****************************************************************************************************************