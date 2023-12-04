Make sure Java is installed on your device before proceeding

Open the command prompt and enter the "xampp" directory, then enter "mysql_start"

Open a new command prompt and enter the directory "xampp/mysql/bin"

Enter the command "mysql -h localhost -u root"

Follow with the command "grant all privileges on * to user@localhost identified by 'password' with grant option;"
Change user to the username you want to use and password should also be changed to the password you want (Keep the single quotes around password)

Enter "commit;"

Next enter "create database checkers_space;"

Enter "grant all on checkers_space.* to user identified by 'password';"
Again user and and password should be the username and password you entered before

Now you can exit by entering "exit"

Next log into the account you made by entering "mysql -h localhost -u user -p"
user is your username

It will ask for your password so enter it and hit enter

Then enter "use checkers_space;"

Next run the command "source c:path"
path should be the path to the project file downloaded at the start and then to the sql file inside that folder
path should end with "/project.sql"

For the computer being used to run the server, make sure you have the ocsf.jar and mysql-connector-java-5.1.40-bin.jar files inside the project folder and add them to the build path of the project

Now we are ready to run the bat files to play the game

Make sure that Firewalls are turned off for this portion, otherwise you cannot connect two computers

Enter into the project file and run CkeckersServer.bat

When the GUI pops up, hit Listen

Then run two different CheckersClient.bat files (can be one pc or two different ones)

Enter the IP address shown on the server gui from before

Log in or create an account on both client GUIs

One client will choose host game and the other will choose join game after the first one has selected

Once both have connected, play the game until one client wins

View the leaderboard then exit or logout