import com.thinking.machines.hr.dl.exceptions.*;
import com.thinking.machines.hr.dl.interfaces.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.hr.dl.dao.*;
import com.thinking.machines.utils.*;//c:\javatools\classes;
import java.io.*;
import java.math.*;
import java.util.*;
public class TestCase
{
public void add()
{
try
{
EmployeeDTOInterface employee=new EmployeeDTO();
employee.setName(Keyboard.getString("Name : "));
employee.setGender(Keyboard.getString("Gender : "));
employee.setSalary(new BigDecimal(Keyboard.getString("Salary : ")));
employee.setPANNumber(Keyboard.getString("PAN Number : "));
EmployeeDAOInterface employeeDAO=new EmployeeDAO();
employeeDAO.add(employee);
}catch(DAOException daoException)
{
System.out.println(daoException.getMessage());
}
}
public void update()
{
try
{
EmployeeDTOInterface employee=null;
EmployeeDAOInterface employeeDAO=new EmployeeDAO();
int choice=Keyboard.getInt("Updated By :\n1.By Code\n2.By PANNumber\nEnter choice : ");
if(choice==1)
{
employee=employeeDAO.getByCode(Keyboard.getInt("Enter Code : "));
}
if(choice==2)
{
employee=employeeDAO.getByPANNumber(Keyboard.getString("Enter PANNumber : "));
}
if(choice!=1 && choice!=2)return;
System.out.println(employee.getCode()+" "+employee.getName()+" "+employee.getGender()+" "+employee.getSalary().toPlainString()+" "+employee.getPANNumber());
char m=Keyboard.getCharacter("Do you want to edit this employee(Y/N) : ");
if(m!='Y' && m!='y')
{
System.out.println("Employee is not update.");
return;
}
employee.setName(Keyboard.getString("Name : "));
employee.setGender(Keyboard.getString("Gender : "));
employee.setSalary(new BigDecimal(Keyboard.getString("Salary : ")));
employee.setPANNumber(Keyboard.getString("PANNumber : "));
m=Keyboard.getCharacter("Do you want to edit this record(Y/N) : ");
if(m!='Y' && m!='y')
{
System.out.println("Employee is not update.");
return;
}
employeeDAO.update(employee);
}catch(DAOException daoException)
{
System.out.println(daoException);
}
}
public void delete()
{
try
{
EmployeeDTOInterface employee=null;
EmployeeDAOInterface employeeDAO=new EmployeeDAO();
int choice=Keyboard.getInt("Deleted By :\n1.By Code\n2.By PANNumber\nEnter choice : ");
if(choice==1)
{
employee=employeeDAO.getByCode(Keyboard.getInt("Enter Code : "));
}
if(choice==2)
{
employee=employeeDAO.getByPANNumber(Keyboard.getString("Enter PANNumber : "));
}
if(choice!=1 && choice!=2)return;
System.out.println(employee.getCode()+" "+employee.getName()+" "+employee.getGender()+" "+employee.getSalary().toPlainString()+" "+employee.getPANNumber());
char m=Keyboard.getCharacter("Do you want to deleted this record(Y/N) : ");
if(m!='Y' && m!='y')
{
System.out.println("Employee is not deleted.");
return;
}
employeeDAO.delete(employee.getCode());
}catch(DAOException daoException)
{
System.out.println(daoException);
}
}
public void list()
{
try
{
EmployeeDAOInterface employeeDAO=new EmployeeDAO();
List<EmployeeDTOInterface> employees=employeeDAO.getAll();
EmployeeDTOInterface employee;
Iterator list=employees.iterator();
while(list.hasNext())
{
employee=(EmployeeDTOInterface)list.next();
System.out.println(employee.getCode()+" "+employee.getName()+" "+employee.getGender()+" "+employee.getSalary().toPlainString()+" "+employee.getPANNumber());
}
}catch(DAOException daoException)
{
System.out.println(daoException.getMessage());
}
}
public void getbycode()
{
try
{
EmployeeDAOInterface employeeDAO=new EmployeeDAO();
EmployeeDTOInterface employee;
employee=employeeDAO.getByCode(Keyboard.getInt("Enter code : "));
System.out.println(employee.getCode()+" "+employee.getName()+" "+employee.getGender()+" "+employee.getSalary().toPlainString()+" "+employee.getPANNumber());
}catch(DAOException daoException)
{
System.out.println(daoException.getMessage());
}
}
public void getbypannumber()
{
try
{
EmployeeDAOInterface employeeDAO=new EmployeeDAO();
EmployeeDTOInterface employee;
employee=employeeDAO.getByPANNumber(Keyboard.getString("Enter PAN Number : "));
System.out.println(employee.getCode()+" "+employee.getName()+" "+employee.getGender()+" "+employee.getSalary().toPlainString()+" "+employee.getPANNumber());
}catch(DAOException daoException)
{
System.out.println(daoException.getMessage());
}
}
public void getCount()
{
try
{
EmployeeDAOInterface employee=new EmployeeDAO();
System.out.println("Total records are : "+employee.getCount());
}
catch(DAOException daoException)
{
System.out.println(daoException.getMessage());
}
}
public static void main(String gg[])
{
TestCase t1=new TestCase();
int choice;
while(true)
{
System.out.println("1.Add\n2.Update\n3.Delete\n4.List\n5.Get by Code\n6.Get by PAN number\n7.Get Count\n8.Exit");
choice=Keyboard.getInt("Enter your choice : ");
if(choice==1)t1.add();
if(choice==2)t1.update();
if(choice==3)t1.delete();
if(choice==4)t1.list();
if(choice==5)t1.getbycode();
if(choice==6)t1.getbypannumber();
if(choice==7)t1.getCount();
if(choice==8)break;
}
}
}