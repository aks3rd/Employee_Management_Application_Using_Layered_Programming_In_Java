package com.thinking.machines.hr.bl.interfaces;
import com.thinking.machines.hr.bl.exceptions.*;
import java.util.*;
public interface EmployeeManagerInterface
{
public enum OrderedBy{CODE,NAME,GENDER,SALARY,PAN_NUMBER};
public static final OrderedBy CODE = OrderedBy.CODE;
public static final OrderedBy NAME = OrderedBy.NAME;
public static final OrderedBy GENDER = OrderedBy.GENDER;
public static final OrderedBy SALARY = OrderedBy.SALARY;
public static final OrderedBy PAN_NUMBER = OrderedBy.PAN_NUMBER;
public void add(EmployeeInterface employee) throws ValidationException,ProcessException;
public void delete(int code) throws ValidationException,ProcessException;
public void update(EmployeeInterface employee) throws ValidationException,ProcessException;
public EmployeeInterface getByCode(int code) throws ValidationException;
public EmployeeInterface getByPANNumber(String panNumber) throws ValidationException;
public int getCount();
public boolean hasEmployees();
public List<EmployeeInterface> getOrderedBy(OrderedBy orderedBy) throws ValidationException;
public void deleteAll() throws ProcessException;
}