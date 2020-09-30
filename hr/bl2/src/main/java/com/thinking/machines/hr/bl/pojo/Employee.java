package com.thinking.machines.hr.bl.pojo;
import com.thinking.machines.hr.bl.interfaces.*;
import java.math.*;
public class Employee implements EmployeeInterface
{
int code;
String name;
String gender;
BigDecimal salary;
String panNumber;
public Employee()
{
code=0;
name=" ";
gender=" ";
salary=new BigDecimal("0.00");
panNumber=" ";
}
public Employee(Employee employee)
{
this.code=employee.code;
this.name=employee.name;
this.gender=employee.gender;
this.salary=employee.salary;
this.panNumber=employee.panNumber;
}
public void setCode(int code)
{
this.code=code;
}
public int getCode()
{
return this.code;
}
public void setName(String name)
{
this.name=name;
}
public String getName()
{
return this.name;
}
public void setGender(String gender)
{
this.gender=gender;
}
public String getGender()
{
return this.gender;
}
public void setSalary(BigDecimal salary)
{
this.salary=salary;
}
public BigDecimal getSalary()
{
return this.salary;
}
public void setPANNumber(String panNumber)
{
this.panNumber=panNumber;
}
public String getPANNumber()
{
return this.panNumber;
}
public boolean isMale()
{
return this.gender.equals("M");
}
public boolean isFemale()
{
return this.gender.equals("F");
}

public int hashCode()
{
return this.code;
}
public boolean equals(Object other)
{
System.out.println("Equals chali employee wali");
if(!(other instanceof EmployeeInterface))return false;
EmployeeInterface e=(EmployeeInterface)other;
return this.code==e.getCode();
}
public int compareTo(EmployeeInterface other)
{
return this.code-other.getCode();
}
}