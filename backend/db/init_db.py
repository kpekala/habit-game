import mysql.connector

mydb = mysql.connector.connect(host="localhost", user="root", password="admin", port="3306")

cursor = mydb.cursor()

cursor.execute("create database habits")
cursor.execute("create user 'admin' identified by 'admin'")
cursor.execute("grant all on habits.* to 'admin'")