import sqlite3


# the path to the sqlite database
databasePath = "./database/lmsDatabase.db"

# creates a database table
def createTable(conn, create_table_sql):
    # creates a table from the create_table_sql statement
    # conn = Connection Object
    try:
        c = conn.cursor()
        c.execute(create_table_sql)
    except Error as e:
        print(e)


def createConnection(databasePath):
    # creates a connection to the SQLite database
    # the path is specified by the "databasePath" variable
    # returns the connection object or None
    conn = None
    try:
        conn = sqlite3.connect(databasePath)
        return conn
    except Error as e:
        print (e)

    return connect

CREATE TABLE "book"
(
    BookID INTEGER not null
        primary key autoincrement,
    BookTitle TEXT not null,
    BookAuthor TEXT not null,
    BookISBN INTEGER,
    BookPubYear INTEGER,
    BookLoaned TEXT default N not null
)