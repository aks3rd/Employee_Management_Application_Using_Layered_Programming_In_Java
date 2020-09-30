import com.thinking.machines.hr.bl.exceptions.*;
import com.thinking.machines.hr.bl.interfaces.*;
import com.thinking.machines.hr.bl.pojo.*;
import com.thinking.machines.hr.bl.manager.*;
import com.thinking.machines.utils.*;
import java.math.*;
import java.util.*;
public class TestCase
{
public void add()
{
try
{
EmployeeInterface employee=new Employee();
employee.setName(Keyboard.getString("Name : "));
employee.setGender(Keyboard.getString("Gender : "));
employee.setSalary(new BigDecimal(Keyboard.getString("Salary : ")));
employee.setPANNumber(Keyboard.getString("PanNumber : "));
EmployeeManagerInterface employeeManager=new EmployeeManager();
employeeManager.add(employee);
System.out.println("Employee is added and code tobe assign "+employee.getCode());
}catch(ValidationException validationException)
{
List<String> list=validationException.getAllException();
list.forEach(e->{
System.out.print(e+" : ");
System.out.println(validationException.getException(e));}); 
}
catch(ProcessException exception)
{
System.out.println(exception);
}
}
public void update()
{
try
{
System.out.println("Update By :\n1.Code\n2.PanNumber");
int ch=Keyboard.getInt("Enter your choice : ");
if(ch!=1 && ch!=2)
{
System.out.println("Invalid Choice.");
return;
}
EmployeeManagerInterface employeeManager=new EmployeeManager();;
EmployeeInterface employee=null;
if(ch==1)
{
int code=Keyboard.getInt("Code = ");
employee=employeeManager.getByCode(code);
}
if(ch==2)
{
String panNumber=Keyboard.getString("PanNumber = ");
employee=employeeManager.getByPANNumber(panNumber);
}
System.out.println("Code : "+employee.getCode());
System.out.println("Name : "+employee.getName());
System.out.println("Gender : "+employee.getGender());
System.out.println("Salary : "+employee.getSalary().toPlainString());
System.out.println("PANNumber : "+employee.getPANNumber());
char m=Keyboard.getCharacter("Do you want to update this(Y/N) : ");
if(m!='y' && m!='Y')
{
System.out.println("Record not updated.");
return;
}
EmployeeInterface blEmployee=new Employee();
blEmployee.setCode(employee.getCode());
System.out.println("Code :"+blEmployee.getCode());
blEmployee.setName(Keyboard.getString("Name : "));
blEmployee.setGender(Keyboard.getString("Gender : "));
blEmployee.setSalary(new BigDecimal(Keyboard.getInt("Salary : ")));
blEmployee.setPANNumber(employee.getPANNumber());
System.out.println("PanNumber :"+blEmployee.getPANNumber());
employeeManager.update(blEmployee);
System.out.println("Record Updated.");
}catch(ValidationException validationException)
{
List<String> list=validationException.getAllException();
list.forEach(e->{
System.out.print(e+" : ");
System.out.println(validationException.getException(e));}); 
}
catch(ProcessException exception)
{
System.out.println(exception);
}
}
public void delete()
{
try
{
System.out.println("Delete By :\n1.Code\n2.PanNumber");
int ch=Keyboard.getInt("Enter your choice : ");
if(ch!=1 && ch!=2)
{
System.out.println("Invalid Choice.");
return;
}
EmployeeManagerInterface employeeManager=new EmployeeManager();;
EmployeeInterface employee=null;
if(ch==1)
{
int code=Keyboard.getInt("Code = ");
employee=employeeManager.getByCode(code);
}
if(ch==2)
{
String panNumber=Keyboard.getString("PanNumber = ");
employee=employeeManager.getByPANNumber(panNumber);
}
System.out.println("Code : "+employee.getCode());
System.out.println("Name : "+employee.getName());
System.out.println("Gender : "+employee.getGender());
System.out.println("Salary : "+employee.getSalary().toPlainString());
System.out.println("PANNumber : "+employee.getPANNumber());
char m=Keyboard.getCharacter("Do you want to delete this(Y/N) : ");
if(m!='y' && m!='Y')
{
System.out.println("Record not deleted.");
return;
}
employeeManager.delete(employee.getCode());
System.out.println("Record Deleted.");
}catch(ValidationException validationException)
{
List<String> list=validationException.getAllException();
list.forEach(e->{
System.out.print(e+" : ");
System.out.println(validationException.getException(e));}); 
}
catch(ProcessException exception)
{
System.out.println(exception);
}
}
public void search()
{
try
{
System.out.println("Search By :\n1.Code\n2.PanNumber");
int ch=Keyboard.getInt("Enter your choice : ");
if(ch!=1 && ch!=2)
{
System.out.println("Invalid Choice.");
return;
}
EmployeeManagerInterface employeeManager=new EmployeeManager();;
EmployeeInterface employee=null;
if(ch==1)
{
int code=Keyboard.getInt("Code = ");
employee=employeeManager.getByCode(code);
}
if(ch==2)
{
String panNumber=Keyboard.getString("PanNumber = ");
employee=employeeManager.getByPANNumber(panNumber);
}
System.out.println("Code : "+employee.getCode());
System.out.println("Name : "+employee.getName());
System.out.println("Gender : "+employee.getGender());
System.out.println("Salary : "+employee.getSalary().toPlainString());
System.out.println("PANNumber : "+employee.getPANNumber());
}catch(ValidationException validationException)
{
List<String> list=validationException.getAllException();
list.forEach(e->{
System.out.print(e+" : ");
System.out.println(validationException.getException(e));}); 
}
}
public void getCount()
{
System.out.println(new EmployeeManager().getCount());
}
public void hasEmployees()
{
System.out.println(new EmployeeManager().hasEmployees());
}
public void deleteAll()
{
char m=Keyboard.getCharacter("Do you want to delete all records(Y/N) : ");
if(m!='y' && m!='Y')
{
System.out.println("Record not deleted.");
return;
}
try
{
new EmployeeManager().deleteAll();
getOrderedBy();
getCount();
}catch(Exception e)
{}
}
public void getOrderedBy()
{
try
{
EmployeeManagerInterface employeeManager=new EmployeeManager();
List<EmployeeInterface> list=employeeManager.getOrderedBy(EmployeeManager.OrderedBy.CODE);
System.out.println("***********Code Wise List**********");
list.forEach(e->System.out.println(e.getCode()+" "+e.getName()+" "+e.getGender()+" "+e.getSalary().toPlainString()+" "+e.getPANNumber()));
System.out.println("***********Name Wise List**********");
list=employeeManager.getOrderedBy(EmployeeManager.OrderedBy.NAME);
list.forEach(e->System.out.println(e.getCode()+" "+e.getName()+" "+e.getGender()+" "+e.getSalary().toPlainString()+" "+e.getPANNumber()));
System.out.println("***********PAN_NUMBER Wise List**********");
list=employeeManager.getOrderedBy(EmployeeManager.OrderedBy.PAN_NUMBER);
list.forEach(e->System.out.println(e.getCode()+" "+e.getName()+" "+e.getGender()+" "+e.getSalary().toPlainString()+" "+e.getPANNumber()));
System.out.println("***********SALARY Wise List**********");
list=employeeManager.getOrderedBy(EmployeeManager.OrderedBy.SALARY);
list.forEach(e->System.out.println(e.getCode()+" "+e.getName()+" "+e.getGender()+" "+e.getSalary().toPlainString()+" "+e.getPANNumber()));
System.out.println("***********GENDER Wise List**********");
list=employeeManager.getOrderedBy(EmployeeManager.OrderedBy.GENDER);
list.forEach(e->System.out.println(e.getCode()+" "+e.getName()+" "+e.getGender()+" "+e.getSalary().toPlainString()+" "+e.getPANNumber()));

}catch(ValidationException validationException)
{
List<String> list=validationException.getAllException();
list.forEach(e->{
System.out.print(e+" : ");
System.out.println(validationException.getException(e));}); 
}
}
public static void main(String gg[])
{
TestCase t=new TestCase();
while(true)
{
System.out.println("1.Add\n2.Update\n3.Delete\n4.Search\n5.getCount()\n6.hasEmployees()\n7.deleteAll()\n8.getOrderedBy()\n9.Exit");
int ch=Keyboard.getInt("Enter your choice : ");
if(ch==1)t.add();
if(ch==2)t.update();
if(ch==3)t.delete();
if(ch==4)t.search();
if(ch==5)t.getCount();
if(ch==6)t.hasEmployees();
if(ch==7)t.deleteAll();
if(ch==8)t.getOrderedBy();
if(ch==9)break;
}
}
}