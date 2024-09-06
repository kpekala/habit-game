import mysql.connector
import subprocess

def init_db():
    db = mysql.connector.connect(host="db", user="root", password="admin", port="3306")

    cursor = db.cursor()

    cursor.execute("create database habits")
    db.close()
    # cursor.execute("create user 'admin' identified by 'admin'")
    # cursor.execute("grant all ON *.* TO 'admin'@'%';")
    # cursor.execute('flush privileges;')

def run_app():
    subprocess.run(["java", "-jar", "-Dspring.profiles.active=production", "/app.jar"])

def main():
    init_db()
    run_app()

if __name__ == "__main__":
    main()
