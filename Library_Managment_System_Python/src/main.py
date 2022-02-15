
from os import system, name, path
import time
from platform import system
from connection import *

class Main:
    def preReq():
        # this section of code checks various things about the system and code
        osType = platform.system()
        databasePath = connection.getDatabasePath()
        # checks if the database exists
        if os.path.exists(databasePath):
            return True
        else:
            createDatabase(databasePath)


        print("System Specs:")
        print("Platform: ", osType)
        print("Database Path: ", databasePath)



    def clear():
        #windows clear terminal
        if os.name == 'nt':
            _ = os.system('cls')
        elif os.name == 'posix':
            _ = os.system('clear')


#UNUSED!!
    #def viewer():
        #view_chooser_input = input()
        #view_chooser = view_chooser_input.lower()
        #view_chooser = view_chooser.strip()
        #if view_chooser == 'terminal':
        #    #test code
        #    print("loading program through terminal...")
        #    time.sleep(3)
        #    print("Boop Loaded")
        #    return("terminal")
            
        #elif view_chooser == 'gui':
        #    #will be removed once i implement GUI
        #    print("Will be added once GUI is implemented")
        #    return("gui")
        #else:
        #     print("Please enter the correct value")
      #       clear()
     #        viewer()

    def login_term():
        clear()
        print("Please Enter your username and password")
        username = input("Enter your username: ")
        password = input("Enter your password: ")
        #testing
        print(username, password)


    # prints welcome
    preReq()
    print("Welcome to the Library Management System")
    # launches the login screen
    login_term()

