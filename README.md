SER_422_Lab4
============


Sessions and MVC

This news site was made for SER422 Lab4 at taught by Dr. Kevin Gary at
	Polytechnic ASU. The repository for where the code is located as well
	as list of requirements and specifications it was created from is:
	https://github.com/hibooboo2/SER_422_Lab4/ The build.xml has 5 targets,
	they are clean, prepare, compile, war, and deploy. This site follows
	MVC. Where the model is represented with a BizLogic class, as well as a
	DAO and MODEL package that was provided By Dr. Gary. The controller is
	the Controller.java and it does not access the DAO, merely uses the
	beans created from the data, from with in the request,to choose where
	to place beans from the Model and in which views to place said beans.
	The views are all .jsp files. Which do not access the DAO, and get
	their beans from the request. This site was created by Adam Lay and
	James Harris via pair programming over skype and with teamviewer.