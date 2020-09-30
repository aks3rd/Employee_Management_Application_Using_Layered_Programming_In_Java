package com.thinking.machines.hr.bl.interfaces;
import java.math.*;
public interface EmployeeInterface extends java.io.Serializable,Comparable<EmployeeInterface>
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
public boolean isMale();
public boolean isFemale();
public int hashCode();
public boolean equals(Object other);
}