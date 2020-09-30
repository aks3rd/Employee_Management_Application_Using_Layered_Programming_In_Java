package com.thinking.machines.hr.dl.dto;
import com.thinking.machines.hr.dl.interfaces.*;
import java.math.*;
public class EmployeeDTO implements EmployeeDTOInterface
{
int code;
String name;
String gender;
BigDecimal salary;
String panNumber;
public EmployeeDTO()
{
code=0;
name=" ";
gender=" ";
salary=new BigDecimal("0.00");
panNumber=" ";
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
public int hashCode()
{
return this.code;
}
public boolean equals(Object other)
{
if(!(other instanceof EmployeeDTOInterface))return false;
EmployeeDTOInterface e=(EmployeeDTOInterface)other;
return this.code==e.getCode();
}
public int compareTo(EmployeeDTOInterface other)
{
return this.code-other.getCode();
}
}