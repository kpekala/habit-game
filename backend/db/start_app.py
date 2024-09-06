import mysql.connector
import subprocess

def init_db():
    mydb = mysql.connector.connect(host="db", user="root", password="admin", port="3306")

    cursor = mydb.cursor()

    cursor.execute("create database habits")
    # cursor.execute("create user 'admin' identified by 'admin'")
    # cursor.execute("grant all ON *.* TO 'admin'@'%';")
    # cursor.execute('flush privileges;')

def run_app():
    subprocess.run(["java", "-jar", "-Dspring.profiles.active=production", "/app.jar"])

def main():
    try:
        init_db()
    except:
        print('Something went wrong with database initialization')
    finally:
        run_app()

if __name__ == "__main__":
    main()
