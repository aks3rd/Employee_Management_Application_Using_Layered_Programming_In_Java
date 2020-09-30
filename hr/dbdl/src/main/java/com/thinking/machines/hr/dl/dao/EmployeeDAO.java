package com.thinking.machines.hr.dl.dao;
import com.thinking.machines.hr.dl.exceptions.*;
import com.thinking.machines.hr.dl.interfaces.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.hr.dl.connections.*;
import java.util.*;
import java.io.*;
import java.math.*;
import java.sql.*;

public class EmployeeDAO implements EmployeeDAOInterface
{
private static final String fileName="employee.data";
private static Connection connection;
public void add(EmployeeDTOInterface employee) throws DAOException
{
connection=DAOConnection.getConnection();
int generatedCode;
String name;
String gender;
Double salary;
String vPANNumber;
vPANNumber=employee.getPANNumber().toUpperCase().trim();
try
{
PreparedStatement ps=connection.prepareStatement("select * from employee where upper(panNumber)=?");
ps.setString(1,vPANNumber);
ResultSet r=ps.executeQuery();
if(r.next())
{
ps.close();
connection.close();
throw new DAOException(employee.getPANNumber()+" PAN Number exists.");
}
else
{
ps.close();
name=employee.getName();
gender=employee.getGender();
salary=Double.parseDouble(employee.getSalary().toString());
ps=connection.prepareStatement("insert into employee(name,gender,panNumber,salary) values(?,?,?,?)",Statement.RETURN_GENERATED_KEYS);
ps.setString(1,name);
ps.setString(2,gender);
ps.setString(3,vPANNumber);
ps.setDouble(4,salary);
ps.executeUpdate();
r=ps.getGeneratedKeys();
if(r.next())
{
generatedCode=r.getInt(1);
employee.setCode(generatedCode);
ps.close();
connection.close();
System.out.println("Employee is added, code tobe assign "+generatedCode);
}
else
{
ps.close();
connection.close();
throw new DAOException("Employee is not added");
}
}
}
catch(SQLException sqle)
{
throw new DAOException(sqle.getMessage());
}
}


public void update(EmployeeDTOInterface employee) throws DAOException
{
connection=DAOConnection.getConnection();
int code;
String name;
String gender;
Double salary;
String vPANNumber;
code=employee.getCode();
vPANNumber=employee.getPANNumber().toUpperCase().trim();
try
{
PreparedStatement ps=connection.prepareStatement("select * from employee where code=?");
ps.setInt(1,code);
ResultSet r=ps.executeQuery();
if(!r.next())
{
ps.close();
connection.close();
throw new DAOException("Record not exists.");
}
ps.close();
ps=connection.prepareStatement("select * from employee where upper(panNumber)=? and not code=?");
ps.setString(1,vPANNumber);
ps.setInt(2,code);
r=ps.executeQuery();
if(r.next())
{
ps.close();
connection.close();
throw new DAOException(employee.getPANNumber()+" PAN Number exists.");
}
else
{
ps.close();
name=employee.getName();
gender=employee.getGender();
salary=Double.parseDouble(employee.getSalary().toString());
ps=connection.prepareStatement("update employee set name=?,gender=?,panNumber=?,salary=? where code=?");
ps.setString(1,name);
ps.setString(2,gender);
ps.setString(3,vPANNumber);
ps.setDouble(4,salary);
ps.setInt(5,code);
ps.executeUpdate();
ps.close();
connection.close();
System.out.println("Record Updated");

}
}catch(SQLException sqle)
{
throw new DAOException(sqle.getMessage());
}
}


public void delete(int code) throws DAOException
{
connection=DAOConnection.getConnection();
try
{
PreparedStatement ps=connection.prepareStatement("select * from employee where code=?");
ps.setInt(1,code);
ResultSet r=ps.executeQuery();
if(!r.next())
{
ps.close();
connection.close();
throw new DAOException("Record not exists.");
}
else
{
ps.close();
ps=connection.prepareStatement("delete from employee where code=?");
ps.setInt(1,code);
ps.executeUpdate();
ps.close();
connection.close();
System.out.println("Record Deleted");

}
}catch(SQLException sqle)
{
throw new DAOException(sqle.getMessage());
}
}

public EmployeeDTOInterface getByCode(int code) throws DAOException
{
EmployeeDTOInterface employee=null;
connection=DAOConnection.getConnection();
try
{
PreparedStatement ps=connection.prepareStatement("select * from employee where code=?");
ps.setInt(1,code);
ResultSet r=ps.executeQuery();
if(!r.next())
{
ps.close();
connection.close();
throw new DAOException("Record not exists.");
}
else
{
String name;
String gender;
Double salary;
String panNumber;
name=r.getString("name");
gender=r.getString("gender");
salary=r.getDouble("salary");
panNumber=r.getString("panNumber");
employee=new EmployeeDTO();
employee.setCode(code);
employee.setName(name);
employee.setGender(gender);
employee.setSalary(new BigDecimal(String.valueOf(salary)));
employee.setPANNumber(panNumber);
ps.close();
connection.close();
return employee;
}
}catch(SQLException sqle)
{
throw new DAOException(sqle.getMessage());
}
}


public EmployeeDTOInterface getByPANNumber(String panNumber) throws DAOException
{
EmployeeDTOInterface employee=null;
connection=DAOConnection.getConnection();
try
{
PreparedStatement ps=connection.prepareStatement("select * from employee where upper(panNumber)=?");
ps.setString(1,panNumber);
ResultSet r=ps.executeQuery();
if(!r.next())
{
ps.close();
connection.close();
throw new DAOException("Record not exists.");
}
else
{
int code;
String name;
String gender;
Double salary;
code=r.getInt("code");
name=r.getString("name");
gender=r.getString("gender");
salary=r.getDouble("salary");
panNumber=r.getString("panNumber");
employee=new EmployeeDTO();
employee.setCode(code);
employee.setName(name);
employee.setGender(gender);
employee.setSalary(new BigDecimal(String.valueOf(salary)));
employee.setPANNumber(panNumber);
ps.close();
connection.close();
return employee;
}
}catch(SQLException sqle)
{
throw new DAOException(sqle.getMessage());
}
}


public List<EmployeeDTOInterface> getAll() throws DAOException
{
List<EmployeeDTOInterface> employees=new LinkedList<>();
connection=DAOConnection.getConnection();
EmployeeDTOInterface employee;
int code;
String name;
String gender;
Double salary;
String panNumber;

try
{
Statement cs=connection.createStatement();
ResultSet r=cs.executeQuery("select * from employee order by code");
while(r.next())
{
code=r.getInt("code");
name=r.getString("name");
gender=r.getString("gender");
salary=r.getDouble("salary");
panNumber=r.getString("panNumber");
employee=new EmployeeDTO();
employee.setCode(code);
employee.setName(name);
employee.setGender(gender);
employee.setSalary(new BigDecimal(String.valueOf(salary)));
employee.setPANNumber(panNumber);
employees.add(employee);
}
cs.close();
connection.close();
return employees;
}catch(SQLException sqle)
{
throw new DAOException(sqle.getMessage());
}
}


public long getCount() throws DAOException
{
long noOfRecords=0;
int intCNT=0;
connection=DAOConnection.getConnection();
try
{
Statement cs=connection.createStatement();
ResultSet r=cs.executeQuery("select count(*) as \"cnt\" from employee");
if(r.next())
{
intCNT=(r.getInt("cnt"));
noOfRecords=Long.parseLong(String.valueOf(intCNT));
cs.close();
connection.close();
return noOfRecords;
}
cs.close();
connection.close();
}catch(SQLException sqle)
{
throw new DAOException(sqle.getMessage());
}
return noOfRecords;
}
}