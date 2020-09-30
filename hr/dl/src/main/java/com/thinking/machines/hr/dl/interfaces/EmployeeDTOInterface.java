package com.thinking.machines.hr.dl.interfaces;
import java.math.*;
public interface EmployeeDTOInterface extends java.io.Serializable,Comparable<EmployeeDTOInterface>
{
public void setCode(int code);
public int getCode();
public void setName(String name);
public String getName();
public void setGender(String gender);
public String getGender();
public void setSalary(BigDecimal salary);
public BigDecimal getSalary();
public void setPANNumber(String panNumber);
public String getPANNumber();
public int hashCode();
public boolean equals(Object other);
}