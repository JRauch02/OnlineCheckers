Download the project file or zip file (extract zip if downloaded that way)

Make sure Java is installed on your device before proceeding

Open the command prompt and enter the "xampp" directory, then enter "mysql_start"

Open a new command prompt and enter the directory "xampp/mysql/bin"

Enter the command "mysql -h localhost -u root"

Follow with the command "grant all privileges on * to student@localhost identified by 'hello' with grant option;"

Enter "commit;"

Next enter "create database checkers_space;"

Enter "grant all on checkers_space.* to student identified by 'hello';"

Now you can exit by entering "exit"

Next log into the account you made by entering "mysql -h localhost -u student -p"

It will ask for your password so enter "hello" and hit enter

Then enter "use checkers_space;"

Next run the command "source c:path"
path should be the path to the project file downloaded at the start and then to the sql file inside that folder
path should end with "/project.sql"

Now we are ready to run the bat files to play the game

Make sure that Firewalls are turned off for this portion, otherwise you cannot connect two computers

RUNNING THE PROJECT (Once above is completed): 
Enter into the project folder

Open the folder "batFiles" and move both "CheckersServer.bat" and "CheckersClient.bat" into the root directory of the project (up one level in the directory).

Ensure that the following two files: "mysql-connector-java-5.1.40-bin.jar", "ocsf.jar" are in the same directory
as the CheckersClient.bat and CheckersServer.bat files.

Run CheckersServer.bat, there should be no error printed in the command prompt that opens with the GUI.
Press the Listen Button, take note that the IP of the Checkers Server is printed in the Server Log Area once listen is pressed.
(Note down the numbers following the slash - should be in the format "192.xxx.x.xxx")

Next, Run CheckersClient.bat and enter the IP from the Checkers Server instance and press submit.

Once this is completed you can either create an account or log in to an existing account by following prompts on screen.

After logging in, the first client MUST click the "Host" button.

Launch a second client and complete the same steps mentioned above, but click the "Join" button
once the game menu is reached.

Now you should be able to play a game of Checkers!

Once it is your turn (visible via the left-most side of the screen as "Your Turn"), click a piece and the server will
communicate which cells are available for moves.

Enjoy!





















