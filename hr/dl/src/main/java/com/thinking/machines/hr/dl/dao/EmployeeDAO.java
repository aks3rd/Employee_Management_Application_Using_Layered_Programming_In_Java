package com.thinking.machines.hr.dl.dao;
import com.thinking.machines.hr.dl.exceptions.*;
import com.thinking.machines.hr.dl.interfaces.*;
import com.thinking.machines.hr.dl.dto.*;
import java.util.*;
import java.io.*;
import java.math.*;
public class EmployeeDAO implements EmployeeDAOInterface
{
private static final String fileName="employee.data";
public void add(EmployeeDTOInterface employee) throws DAOException
{
int code;
String vPANNumber;
String header;
int lastGeneratedCode=0;
int noOfRecords=0;
try
{
File file=new File(fileName);
RandomAccessFile randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length()>0)
{
header=randomAccessFile.readLine();
lastGeneratedCode=Integer.parseInt(header.substring(0,10).trim());
noOfRecords=Integer.parseInt(header.substring(10).trim());
}
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
randomAccessFile.readLine();
randomAccessFile.readLine();
randomAccessFile.readLine();
randomAccessFile.readLine();
vPANNumber=randomAccessFile.readLine();
if(vPANNumber.equalsIgnoreCase(employee.getPANNumber()))
{
randomAccessFile.close();
throw new DAOException(employee.getPANNumber()+" PAN Number exists.");
}
}
lastGeneratedCode++;
noOfRecords++;
code=lastGeneratedCode;
header=String.format("%10s%10s",code,noOfRecords);
if(randomAccessFile.length()==0)
{
randomAccessFile.writeBytes(header+"\n");
randomAccessFile.writeBytes(code+"\n");
randomAccessFile.writeBytes(employee.getName()+"\n");
randomAccessFile.writeBytes(employee.getGender()+"\n");
randomAccessFile.writeBytes(employee.getSalary().toPlainString()+"\n");
randomAccessFile.writeBytes(employee.getPANNumber()+"\n");
}
else
{
randomAccessFile.writeBytes(code+"\n");
randomAccessFile.writeBytes(employee.getName()+"\n");
randomAccessFile.writeBytes(employee.getGender()+"\n");
randomAccessFile.writeBytes(employee.getSalary().toPlainString()+"\n");
randomAccessFile.writeBytes(employee.getPANNumber()+"\n");
randomAccessFile.seek(0);
randomAccessFile.writeBytes(header);
}
employee.setCode(code);
randomAccessFile.close();
System.out.println("Employee is added, code tobe assign "+code);
}catch(IOException ioException)
{
System.out.println(ioException);
}
}
public void update(EmployeeDTOInterface employee) throws DAOException
{
int vCode;
String vPANNumber;
try
{
File file=new File(fileName);
if(!file.exists()) throw new DAOException("Record not exists.");
RandomAccessFile randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0)
{
randomAccessFile.close();
throw new DAOException("Record not exists.");
}
int codeFound=0;
randomAccessFile.readLine();
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
vCode=Integer.parseInt(randomAccessFile.readLine());
randomAccessFile.readLine();
randomAccessFile.readLine();
randomAccessFile.readLine();
vPANNumber=randomAccessFile.readLine();
if(vPANNumber.equalsIgnoreCase(employee.getPANNumber()) && vCode!=employee.getCode())
{
randomAccessFile.close();
throw new DAOException(employee.getPANNumber()+" PAN Number exists.");
}
if(vCode==employee.getCode())
{
codeFound=1;
}
}
if(codeFound==0)throw new DAOException("Record not exists.");
randomAccessFile.seek(0);
File tmpfile=new File("tmp.tmp");
if(tmpfile.exists())tmpfile.delete();
RandomAccessFile tmpRandomAccessFile=new RandomAccessFile(tmpfile,"rw");
tmpRandomAccessFile.writeBytes(randomAccessFile.readLine()+"\n");
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
vCode=Integer.parseInt(randomAccessFile.readLine());
if(vCode!=employee.getCode())
{
tmpRandomAccessFile.writeBytes(vCode+"\n");
tmpRandomAccessFile.writeBytes(randomAccessFile.readLine()+"\n");
tmpRandomAccessFile.writeBytes(randomAccessFile.readLine()+"\n");
tmpRandomAccessFile.writeBytes(randomAccessFile.readLine()+"\n");
tmpRandomAccessFile.writeBytes(randomAccessFile.readLine()+"\n");
}
else
{
tmpRandomAccessFile.writeBytes(vCode+"\n");
tmpRandomAccessFile.writeBytes(employee.getName()+"\n");
tmpRandomAccessFile.writeBytes(employee.getGender()+"\n");
tmpRandomAccessFile.writeBytes(employee.getSalary().toPlainString()+"\n");
tmpRandomAccessFile.writeBytes(employee.getPANNumber()+"\n");
randomAccessFile.readLine();
randomAccessFile.readLine();
randomAccessFile.readLine();
randomAccessFile.readLine();
}
}
randomAccessFile.seek(0);
tmpRandomAccessFile.seek(0);
while(tmpRandomAccessFile.getFilePointer()<tmpRandomAccessFile.length())
{
randomAccessFile.writeBytes(tmpRandomAccessFile.readLine()+"\n");
}
randomAccessFile.setLength(tmpRandomAccessFile.length());
tmpRandomAccessFile.setLength(0);
tmpRandomAccessFile.close();
randomAccessFile.close();
}catch(IOException ioException)
{
System.out.println(ioException);
}
}
public void delete(int code) throws DAOException
{
int vCode;
String header;
int lastGeneratedCode=0;
int noOfRecords=0;
try
{
File file=new File(fileName);
if(!file.exists()) throw new DAOException("Record not exists.");
RandomAccessFile randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0)
{
randomAccessFile.close();
throw new DAOException("Record not exists.");
}
File tmpfile=new File("tmp.tmp");
if(tmpfile.exists())tmpfile.delete();
RandomAccessFile tmpRandomAccessFile=new RandomAccessFile(tmpfile,"rw");
tmpRandomAccessFile.writeBytes(randomAccessFile.readLine()+"\n");
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
vCode=Integer.parseInt(randomAccessFile.readLine());
if(vCode!=code)
{
tmpRandomAccessFile.writeBytes(vCode+"\n");
tmpRandomAccessFile.writeBytes(randomAccessFile.readLine()+"\n");
tmpRandomAccessFile.writeBytes(randomAccessFile.readLine()+"\n");
tmpRandomAccessFile.writeBytes(randomAccessFile.readLine()+"\n");
tmpRandomAccessFile.writeBytes(randomAccessFile.readLine()+"\n");
}
else
{
randomAccessFile.readLine();
randomAccessFile.readLine();
randomAccessFile.readLine();
randomAccessFile.readLine();
}
}
randomAccessFile.seek(0);
tmpRandomAccessFile.seek(0);
if(tmpRandomAccessFile.length()>0)
{
header=tmpRandomAccessFile.readLine();
lastGeneratedCode=Integer.parseInt(header.substring(0,10).trim());
noOfRecords=Integer.parseInt(header.substring(10).trim());
}
noOfRecords--;
header=String.format("%10s%10s",lastGeneratedCode,noOfRecords);
randomAccessFile.writeBytes(header+"\n");
while(tmpRandomAccessFile.getFilePointer()<tmpRandomAccessFile.length())
{
randomAccessFile.writeBytes(tmpRandomAccessFile.readLine()+"\n");
}
randomAccessFile.setLength(tmpRandomAccessFile.length());
tmpRandomAccessFile.setLength(0);
tmpRandomAccessFile.close();
randomAccessFile.close();
}catch(IOException ioException)
{
System.out.println(ioException);
}
}
public EmployeeDTOInterface getByCode(int code) throws DAOException
{
EmployeeDTOInterface employee=null;
try
{
File file=new File(fileName);
if(!file.exists())throw new DAOException("Records not exists.");
RandomAccessFile randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0)
{
randomAccessFile.close();
throw new DAOException("Records not exists.");
}
int vCode;
String vName;
String vGender;
BigDecimal vSalary;
String vPanNumber;
randomAccessFile.readLine();
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
vCode=Integer.parseInt(randomAccessFile.readLine());
vName=randomAccessFile.readLine();
vGender=randomAccessFile.readLine();
vSalary=new BigDecimal(randomAccessFile.readLine());
vPanNumber=randomAccessFile.readLine();
if(vCode==code)
{
employee=new EmployeeDTO();
employee.setCode(vCode);
employee.setName(vName);
employee.setGender(vGender);
employee.setSalary(vSalary);
employee.setPANNumber(vPanNumber);
return employee;
}
}
randomAccessFile.close();
if(employee==null)throw new DAOException("Records not exists.");
}catch(IOException ioException)
{
System.out.println(ioException);
}
return employee;
}
public EmployeeDTOInterface getByPANNumber(String panNumber) throws DAOException
{
EmployeeDTOInterface employee=null;
try
{
File file=new File(fileName);
if(!file.exists())throw new DAOException("Records not exists.");
RandomAccessFile randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0)
{
randomAccessFile.close();
throw new DAOException("Records not exists.");
}
int vCode;
String vName;
String vGender;
BigDecimal vSalary;
String vPanNumber;
randomAccessFile.readLine();
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
vCode=Integer.parseInt(randomAccessFile.readLine());
vName=randomAccessFile.readLine();
vGender=randomAccessFile.readLine();
vSalary=new BigDecimal(randomAccessFile.readLine());
vPanNumber=randomAccessFile.readLine();
if(vPanNumber.equalsIgnoreCase(panNumber))
{
employee=new EmployeeDTO();
employee.setCode(vCode);
employee.setName(vName);
employee.setGender(vGender);
employee.setSalary(vSalary);
employee.setPANNumber(vPanNumber);
return employee;
}
}
randomAccessFile.close();
if(employee==null)throw new DAOException("Records not exists.");
}catch(IOException ioException)
{
System.out.println(ioException);
}
return employee;
}
public List<EmployeeDTOInterface> getAll() throws DAOException
{
List<EmployeeDTOInterface> employees=new LinkedList<>();
try
{
File file=new File(fileName);
if(!file.exists())return employees;
RandomAccessFile randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0)
{
randomAccessFile.close();
return employees;
}
EmployeeDTOInterface employee;
int code;
String name;
String gender;
BigDecimal salary;
String panNumber;
randomAccessFile.readLine();
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
employee=new EmployeeDTO();
code=Integer.parseInt(randomAccessFile.readLine());
name=randomAccessFile.readLine();
gender=randomAccessFile.readLine();
salary=new BigDecimal(randomAccessFile.readLine());
panNumber=randomAccessFile.readLine();
employee.setCode(code);
employee.setName(name);
employee.setGender(gender);
employee.setSalary(salary);
employee.setPANNumber(panNumber);
employees.add(employee);
}
randomAccessFile.close();
}catch(IOException ioException)
{
System.out.println(ioException);
}
return employees;
}
public long getCount() throws DAOException
{
String header;
long noOfRecords=0;
try
{
File file=new File(fileName);
if(!file.exists())throw new DAOException("Record not exists.");
RandomAccessFile randomAccessFile=new RandomAccessFile(file,"rw");
header=randomAccessFile.readLine();
noOfRecords=Long.parseLong(header.substring(10).trim());
	
}catch(IOException ioException)
{
System.out.println(ioException);
}
return noOfRecords;
}
}