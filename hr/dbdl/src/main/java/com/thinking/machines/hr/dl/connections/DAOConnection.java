package com.thinking.machines.hr.dl.connections;
import java.sql.*;
import com.thinking.machines.hr.dl.exceptions.*;
public class DAOConnection
{
private DAOConnection()
{

}
public static Connection c=null;
public static Connection getConnection() throws DAOException
{
try
{
//Class.forName("com.mysql.jdbc.Driver");
c=DriverManager.getConnection("jdbc:mysql://localhost:3306/EmployeeDB","employeemanager","EmployeeManager_2020");
return c;
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
}

}
