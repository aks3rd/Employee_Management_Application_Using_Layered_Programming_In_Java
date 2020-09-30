package com.thinking.machines.hr.pl.exceptions;
import java.util.*;
public class ModelValidationException extends Exception
{
Map<String,String> exceptions=new HashMap<String,String>();
public ModelValidationException(){}
public ModelValidationException(String properties,String exception)
{
addException(properties,exception);
}
public void addException(String properties,String exception)
{
exceptions.put(properties,exception);
}
public void removeException(String properties)
{
exceptions.remove(properties);
}
public void removeAll()
{
exceptions.clear();
}
public int getSize()
{
return exceptions.size();
}
public String getException(String properties)
{
return exceptions.get(properties);
}
public List<String> getAllException()
{
return new ArrayList<String>(exceptions.keySet());
}
public boolean hasException(String properties)
{
return exceptions.containsKey(properties);
}
public boolean hasExceptions()
{
return exceptions.size()>0;
}
}