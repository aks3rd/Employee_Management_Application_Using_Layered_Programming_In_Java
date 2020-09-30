package com.thinking.machines.hr.pl.model;
import com.thinking.machines.hr.pl.exceptions.*;
import com.thinking.machines.hr.bl.exceptions.*;
import com.thinking.machines.hr.bl.interfaces.*;
import com.thinking.machines.hr.bl.pojo.*;
import com.thinking.machines.hr.bl.manager.*;
import com.thinking.machines.tmutils.*;
import java.util.*;
import java.awt.*;

import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;
import javax.swing.event.*;
public class EmployeeModel extends AbstractTableModel
{
private java.util.List<EmployeeInterface> employees;
private EmployeeManagerInterface employeeManager;
private EmployeeInterface employee;
public EmployeeModel()
{
papulatedDataStructure();
}
private void papulatedDataStructure()
{
try
{
employee=new Employee();
employeeManager=new EmployeeManager();
employees=employeeManager.getOrderedBy(EmployeeManagerInterface.OrderedBy.NAME);
}catch(Exception e)
{
//do nothing;
}
}
public int getColumnCount()
{
return 3;
}
public int getRowCount()
{
return employees.size();
}
public String getColumnName(int columnIndex)
{
if(columnIndex==0)return "S.No.";
if(columnIndex==1)return "Code";
if(columnIndex==2)return "Employee";
return " ";
}
public Class getColumnClass(int columnIndex)
{
if(columnIndex==0 || columnIndex==1)return Integer.class;
return String.class;
}
public Object getValueAt(int rowIndex,int columnIndex)
{
employee=employees.get(rowIndex);
if(columnIndex==0)return (rowIndex+1);
if(columnIndex==1)return employee.getCode();
return employee.getName();
}
public boolean isCellEditable(int rowIndex,int columnIndex)
{
return false;
}
public void setValueAt(int rowIndex,int columnIndex)
{
//do nothing
}
public void add(EmployeeInterface employee) throws ModelException,ModelValidationException
{
try
{
employeeManager.add(employee);
int insertAt=Collections.binarySearch(employees,employee,Comparator.comparing(EmployeeInterface::getName,String.CASE_INSENSITIVE_ORDER).thenComparing(EmployeeInterface::getCode));
if(insertAt<0)insertAt=((insertAt*(-1))-1);
employees.add(insertAt,employee);
System.out.println(employees.get(insertAt).getName());
System.out.println(employee.getCode());
System.out.println(employee.getName());
System.out.println(employee.getGender());
System.out.println(employee.getSalary().toString());
System.out.println(employee.getPANNumber());
fireTableDataChanged();
}catch(ProcessException pe)
{
System.out.println("EmployeeModel ki add ProcessException aaya");
throw new ModelException(pe.getMessage());
}
catch(ValidationException ve)
{
ModelValidationException mve=new ModelValidationException();
java.util.List<String> list=ve.getAllException();
list.forEach(e->{
mve.addException(e,ve.getException(e));
});
System.out.println("EmployeeModel ki add ValidationException aaya");
throw mve;
}
}
public void edit(EmployeeInterface employee) throws ModelException,ModelValidationException
{
try
{
employeeManager.update(employee);
int insertAt=getIndexOf(employee.getCode());
employees.set(insertAt,employee);
Collections.sort(employees,Comparator.comparing(EmployeeInterface::getName,String.CASE_INSENSITIVE_ORDER).thenComparing(EmployeeInterface::getCode));
fireTableDataChanged();
}catch(ProcessException pe)
{
System.out.println("EmployeeModel ki edit ProcessException aaya");
throw new ModelException(pe.getMessage());
}
catch(ValidationException ve)
{
ModelValidationException mve=new ModelValidationException();
java.util.List<String> list=ve.getAllException();
list.forEach(e->{
mve.addException(e,ve.getException(e));
});
System.out.println("EmployeeModel ki edit, ValidationException aaya");
throw mve;
}
}
public void delete(int rowIndex) throws ModelException,ModelValidationException
{
try
{
employee=getEmployeeAt(rowIndex);
employeeManager.delete(employee.getCode());
int index=getIndexOf(employee.getCode());
employees.remove(index);
fireTableDataChanged();
}catch(ProcessException pe)
{
throw new ModelException(pe.getMessage());
}
catch(ValidationException ve)
{
ModelValidationException mve=new ModelValidationException();
java.util.List<String> list=ve.getAllException();
list.forEach(e->{
mve.addException(e,ve.getException(e));
});
System.out.println("EmployeeModel ki delete, ValidationException aaya");
throw mve;
}
}
public int search(String str) throws ModelException
{
ListIterator<EmployeeInterface> itr=employees.listIterator();
for(int i=0;itr.hasNext();i++)
{
if(itr.next().getName().toUpperCase().startsWith(str.toUpperCase()))return i;
}
throw new ModelException("Not Exist");
}
public EmployeeInterface getEmployeeAt(int index) throws ModelException
{
if(index<0 || index >=employees.size())
{
//throw new ModelException(" ");
//System.out.println("Model ki getEmployeeAt ka if chala");
return null;
}
employee=new Employee();
EmployeeInterface plEmployee=employees.get(index);
POJOUtility.copy(employee,plEmployee);
return employee;
}
public int getIndexOf(int code) throws ModelException
{

ListIterator<EmployeeInterface> itr=employees.listIterator();
for(int i=0;itr.hasNext();i++)
{
if(itr.next().getCode()==code)return i;
}
throw new ModelException("Not Exist");
}
}