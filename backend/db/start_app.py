import mysql.connector

mydb = mysql.connector.connect(host="db", user="root", password="admin", port="3306")

cursor = mydb.cursor()

cursor.execute("create database habits")
cursor.execute("create user 'admin' identified by 'admin'")
cursor.execute("grant all ON *.* TO 'admin'@'%';")
cursor.execute('flush privileges;')