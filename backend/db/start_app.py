import mysql.connector

print('Hello world!')

mydb = mysql.connector.connect(host="localhost", user="root", password="admin", port="3306")

cursor = mydb.cursor()

cursor.execute("create database habits")
cursor.execute("create user 'admin' identified by 'admin'")
cursor.execute("grant all ON *.* TO 'admin'@'%';")
cursor.execute('flush privileges;')