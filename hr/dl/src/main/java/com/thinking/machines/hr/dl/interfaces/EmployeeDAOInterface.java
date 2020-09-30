package com.thinking.machines.hr.dl.interfaces;
import com.thinking.machines.hr.dl.exceptions.*;
import java.util.*;
public interface EmployeeDAOInterface
{
public void add(EmployeeDTOInterface employee) throws DAOException;
public void update(EmployeeDTOInterface employee) throws DAOException;
public void delete(int code) throws DAOException;
public EmployeeDTOInterface getByCode(int code) throws DAOException;
public EmployeeDTOInterface getByPANNumber(String panNumber) throws DAOException;
public List<EmployeeDTOInterface> getAll() throws DAOException;
public long getCount() throws DAOException;
}