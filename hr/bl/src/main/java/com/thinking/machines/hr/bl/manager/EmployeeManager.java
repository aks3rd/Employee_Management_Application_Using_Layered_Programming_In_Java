package com.thinking.machines.hr.bl.manager;
import com.thinking.machines.hr.bl.exceptions.*;
import com.thinking.machines.hr.bl.interfaces.*;
import com.thinking.machines.hr.bl.pojo.*;
import java.util.*;
import java.util.stream.*;
import com.thinking.machines.hr.dl.exceptions.*;
import com.thinking.machines.hr.dl.interfaces.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.hr.dl.dao.*;
import com.thinking.machines.tmutils.*;
public class EmployeeManager implements EmployeeManagerInterface
{
private Map<Integer,EmployeeInterface> codeWiseEmployeesMap=new HashMap<>();
private Map<String,EmployeeInterface> panNumberWiseEmployeesMap=new HashMap<>();
private List<EmployeeInterface> codeWiseEmployeesList=new LinkedList<>();
private List<EmployeeInterface> nameWiseEmployeesList=new LinkedList<>();
EmployeeDAOInterface employeeDAO=new EmployeeDAO();
public EmployeeManager()
{
populatedDataStructure();
}
private void populatedDataStructure()
{
try
{
List<EmployeeDTOInterface> list=employeeDAO.getAll();
list.forEach(e->{
EmployeeInterface employee=new Employee();
employee.setCode(e.getCode());
employee.setName(e.getName());
employee.setGender(e.getGender());
employee.setSalary(e.getSalary());
employee.setPANNumber(e.getPANNumber());
codeWiseEmployeesMap.put(e.getCode(),employee);
panNumberWiseEmployeesMap.put(e.getPANNumber().toUpperCase(),employee);
codeWiseEmployeesList.add(employee);
nameWiseEmployeesList.add(employee);
});
codeWiseEmployeesList=codeWiseEmployeesList.stream().sorted().collect(Collectors.toList());
nameWiseEmployeesList=nameWiseEmployeesList.stream().sorted(Comparator.comparing(EmployeeInterface::getName,String.CASE_INSENSITIVE_ORDER).thenComparing(EmployeeInterface::getCode)).collect(Collectors.toList());

}catch(DAOException daoException)
{
return;
}
}
public void add(EmployeeInterface employee) throws ValidationException,ProcessException
{
ValidationException validationException=new ValidationException();
if(employee==null)
{
validationException.addException("Employee","Required");
throw validationException;
}
if(employee.getCode()!=0)
{
validationException.addException("code","Should be zero");
}
if(employee.getName()==null || employee.getName().trim().length()==0)
{
validationException.addException("name","Required");
}
if(employee.getName()!=null && employee.getName().trim().length()>30)
{
validationException.addException("name","Should not exceed 30 characters");
}
if(employee.getGender()==null || employee.getGender().trim().length()==0)
{
validationException.addException("gender","Required");
}
if(!employee.isMale() && !employee.isFemale())
{
validationException.addException("gender","Invalid");
}
if(employee.getSalary()==null)
{
validationException.addException("salary","Required");
}
else
{
try
{
if(employee.getSalary().signum()==-1)
{
validationException.addException("salary","Invalid");
}
}catch(NumberFormatException numberFormatException)
{
validationException.addException("salary","Invalid");
}
}//else end
if(employee.getPANNumber()==null || employee.getPANNumber().trim().length()==0)
{
validationException.addException("panNumber","Required");
}
if(employee.getPANNumber()!=null && employee.getPANNumber().length()>15)
{
validationException.addException("panNumber","Cannot exceed 15 characters");
}
if(employee.getPANNumber()!=null && panNumberWiseEmployeesMap.containsKey(employee.getPANNumber().toUpperCase()))
{
validationException.addException("panNumber","Exists");
}
if(validationException.hasExceptions())throw validationException;

try
{
EmployeeDTOInterface dlEmployee=new EmployeeDTO();
dlEmployee.setName(employee.getName());
dlEmployee.setGender(employee.getGender());
dlEmployee.setSalary(employee.getSalary());
dlEmployee.setPANNumber(employee.getPANNumber());
EmployeeDAOInterface employeeDAO=new EmployeeDAO();
employeeDAO.add(dlEmployee);
employee.setCode(dlEmployee.getCode());
EmployeeInterface dsEmployee=new Employee();
dsEmployee.setCode(employee.getCode());
dsEmployee.setName(employee.getName());
dsEmployee.setGender(employee.getGender());
dsEmployee.setSalary(employee.getSalary());
dsEmployee.setPANNumber(employee.getPANNumber());
codeWiseEmployeesMap.put( employee.getCode(),dsEmployee);
panNumberWiseEmployeesMap.put(employee.getPANNumber().toUpperCase(),dsEmployee);
codeWiseEmployeesList.add(dsEmployee);
if(nameWiseEmployeesList.isEmpty())
{
nameWiseEmployeesList.add(dsEmployee);
return;
}
String vName=employee.getName().toUpperCase();
int insertAt=Collections.binarySearch(nameWiseEmployeesList,employee,Comparator.comparing(EmployeeInterface::getName,String.CASE_INSENSITIVE_ORDER).thenComparing(EmployeeInterface::getCode));
if(insertAt<0)insertAt=((insertAt*(-1))-1);
/*
//this code not sufficient
String vName=employee.getName().toUpperCase();
if(vName.compareToIgnoreCase(nameWiseEmployeesList.get(0).getName())<=0)
{
nameWiseEmployeesList.add(0,dsEmployee);
return;
}
if(vName.compareToIgnoreCase(nameWiseEmployeesList.get(nameWiseEmployeesList.size()-1).getName())>=0)
{
nameWiseEmployeesList.add(dsEmployee);
return;
}
int start=1;
int end=nameWiseEmployeesList.size()-1;
int mid;
int insertAt=0;
EmployeeInterface tmp;
String tmpName;
String newName=employee.getName().toUpperCase();
while(start<=end)
{
mid=(start+end)/2;
tmp=nameWiseEmployeesList.get(mid);
tmpName=tmp.getName().toUpperCase();
if(tmpName.equals(newName))
{
insertAt=mid;
break;
}
if(tmpName.compareTo(newName)>0)
{
if(nameWiseEmployeesList.get(mid-1).getName().toUpperCase().compareTo(newName)<=0)
{
insertAt=mid;
break;
}
else
{
end=mid-1;
continue;
}
}
if(tmpName.compareTo(newName)<0)
{
start=mid+1;
}
}
//this code not sufficient
*/
nameWiseEmployeesList.add(insertAt,dsEmployee);

}catch(DAOException daoException)
{
throw new ProcessException(daoException.getMessage());
}

}
public void delete(int code) throws ValidationException,ProcessException
{
ValidationException validationException=new ValidationException();
if(code==0)
{
validationException.addException("code","Should not be zero");
}
if(code!=0 && codeWiseEmployeesMap.containsKey(code)==false)
{
validationException.addException("code","Invalid");
}
if(validationException.hasExceptions()) throw validationException;
try
{
new EmployeeDAO().delete(code);
EmployeeInterface employee=codeWiseEmployeesMap.get(code);
codeWiseEmployeesMap.remove(code);
panNumberWiseEmployeesMap.remove(employee.getPANNumber().toUpperCase());

int index=Collections.binarySearch(codeWiseEmployeesList,employee,Comparator.comparing(EmployeeInterface::getCode));
if(index >-1) codeWiseEmployeesList.remove(index);

// serious bug as discussed in classroom session
// remove it

index=Collections.binarySearch(nameWiseEmployeesList,employee,Comparator.comparing(EmployeeInterface::getName,String.CASE_INSENSITIVE_ORDER).thenComparing(EmployeeInterface::getCode));
if(index > -1) nameWiseEmployeesList.remove(index);
}catch(DAOException daoException)
{
throw new ProcessException(daoException.getMessage());
}
}

public void update(EmployeeInterface employee) throws ValidationException,ProcessException
{
ValidationException validationException=new ValidationException();
if(employee==null)
{
validationException.addException("Employee","Required");
throw validationException;
}
if(employee.getCode()==0)
{
validationException.addException("code","Should not be zero");
}
if(employee.getCode()!=0 && codeWiseEmployeesMap.containsKey(employee.getCode())==false)
{
validationException.addException("code","Invalid");
//throw validationException;
}
if(employee.getName()==null || employee.getName().trim().length()==0)
{
validationException.addException("name","Required");
}
if(employee.getName()!=null && employee.getName().trim().length()>30)
{
validationException.addException("name","Should not exceed 30 characters");
}
if(employee.getGender()==null || employee.getGender().trim().length()==0)
{
validationException.addException("gender","Required");
}
if(!employee.isMale() && !employee.isFemale())
{
validationException.addException("gender","Invalid");
}
if(employee.getSalary()==null)
{
validationException.addException("salary","Required");
}
else
{
try
{
if(employee.getSalary().signum()==-1)
{
validationException.addException("salary","Invalid");
}
}catch(NumberFormatException numberFormatException)
{
validationException.addException("salary","Invalid");
}
}//else end
if(employee.getPANNumber()==null || employee.getPANNumber().trim().length()==0)
{
validationException.addException("panNumber","Required");
}
if(employee.getPANNumber()!=null && employee.getPANNumber().length()>15)
{
validationException.addException("panNumber","Cannot exceed 15 characters");
}
EmployeeInterface tmpEmployeePAN=codeWiseEmployeesMap.get(employee.getCode());
if(employee.getPANNumber()!=null && !(tmpEmployeePAN.getPANNumber().equalsIgnoreCase(employee.getPANNumber())) && panNumberWiseEmployeesMap.containsKey(employee.getPANNumber().toUpperCase()) )
{
validationException.addException("panNumber","Exists");
}
if(validationException.hasExceptions())throw validationException;
try
{
EmployeeDTOInterface dlEmployee=new EmployeeDTO();
EmployeeInterface tmpEmployee=codeWiseEmployeesMap.get(employee.getCode());
POJOUtility.copy(dlEmployee,employee);
new EmployeeDAO().update(dlEmployee);
EmployeeInterface dsEmployee=new Employee();
POJOUtility.copy(dsEmployee,employee);
codeWiseEmployeesMap.replace(dsEmployee.getCode(),dsEmployee);
panNumberWiseEmployeesMap.replace(dsEmployee.getPANNumber().toUpperCase(),dsEmployee);
int index=Collections.binarySearch(codeWiseEmployeesList,tmpEmployee,Comparator.comparing(EmployeeInterface::getCode));
codeWiseEmployeesList.set(index,dsEmployee);

index=Collections.binarySearch(nameWiseEmployeesList,tmpEmployee,Comparator.comparing(EmployeeInterface::getName,String.CASE_INSENSITIVE_ORDER).thenComparing(EmployeeInterface::getCode));
nameWiseEmployeesList.set(index,dsEmployee);
Collections.sort(nameWiseEmployeesList,Comparator.comparing(EmployeeInterface::getName,String.CASE_INSENSITIVE_ORDER).thenComparing(EmployeeInterface::getCode));

}catch(DAOException daoException)
{
throw new ProcessException(daoException.getMessage());
}
}

public void deleteAll() throws ProcessException
{
EmployeeDAOInterface employeeDAO=new EmployeeDAO();
List<EmployeeInterface> cannotDeleteList=new LinkedList<>();
codeWiseEmployeesList.forEach((e)->{
// code to delete starts here
try
{ 
int code=e.getCode();
employeeDAO.delete(code);
codeWiseEmployeesMap.remove(code);
panNumberWiseEmployeesMap.remove(e.getPANNumber().toUpperCase());
int index=Collections.binarySearch(nameWiseEmployeesList,e,Comparator.comparing(EmployeeInterface::getName,String.CASE_INSENSITIVE_ORDER).thenComparing(EmployeeInterface::getCode));
if(index >=0) nameWiseEmployeesList.remove(index);

}catch(DAOException daoException)
{ 
cannotDeleteList.add(e);
// do nothing
}
// code to delete ends here
});
codeWiseEmployeesList.clear();
codeWiseEmployeesList=cannotDeleteList;
}
public int getCount()
{
return codeWiseEmployeesList.size();
}
public boolean hasEmployees()
{
return codeWiseEmployeesList.size()>0;
}
public EmployeeInterface getByPANNumber(String panNumber) throws ValidationException
{
if(panNumber==null || panNumber.trim().length()==0) throw new ValidationException("panNumber","Invalid");
EmployeeInterface employee=panNumberWiseEmployeesMap.get(panNumber.toUpperCase());
if(employee==null) throw new ValidationException("panNumber","Does not exist.");
return employee;
}
public EmployeeInterface getByCode(int code) throws ValidationException
{
if(code<=0) throw new ValidationException("code","Invalid");
EmployeeInterface employee=codeWiseEmployeesMap.get(code);
if(employee==null) throw new ValidationException("code","Does not exist");
return employee;
}
public List<EmployeeInterface> getOrderedBy(OrderedBy orderedBy) throws ValidationException
{
List<EmployeeInterface> list=new LinkedList<>();
if(orderedBy==OrderedBy.CODE)
{
codeWiseEmployeesList.forEach((e)->{
EmployeeInterface ei= new Employee();
POJOUtility.copy(ei,e);
list.add(ei);
});
return list;
}
if(orderedBy==OrderedBy.NAME)
{
nameWiseEmployeesList.forEach((e)->{
EmployeeInterface ei= new Employee();
POJOUtility.copy(ei,e);
list.add(ei);
});
return list;
}
if(orderedBy==OrderedBy.PAN_NUMBER)
{
List<EmployeeInterface> panNumberWiseEmployeesList;
panNumberWiseEmployeesList=codeWiseEmployeesList.stream().sorted(Comparator.comparing(EmployeeInterface::getPANNumber)).collect(Collectors.toList());
panNumberWiseEmployeesList.forEach((e)->{
EmployeeInterface ei=new Employee();
POJOUtility.copy(ei,e);
list.add(ei);
});
return list;
}
if(orderedBy==OrderedBy.GENDER)
{
List<EmployeeInterface> genderWiseEmployeesList;
genderWiseEmployeesList=codeWiseEmployeesList.stream().sorted(Comparator.comparing(EmployeeInterface::getGender)).collect(Collectors.toList());
genderWiseEmployeesList.forEach((e)->{
EmployeeInterface ei=new Employee();
POJOUtility.copy(ei,e);
list.add(ei);
});
return list;
}
if(orderedBy==OrderedBy.SALARY)
{
List<EmployeeInterface> salaryWiseEmployeesList;
salaryWiseEmployeesList=codeWiseEmployeesList.stream().sorted(Comparator.comparing(EmployeeInterface::getSalary)).collect(Collectors.toList());
salaryWiseEmployeesList.forEach((e)->{
EmployeeInterface ei=new Employee();
POJOUtility.copy(ei,e);
list.add(ei);
});
return list;
}
return list;
}
}