package com.thinking.machines.hr.pl.ui;
import com.thinking.machines.hr.pl.ui.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
public class EmployeeFrame extends JFrame
{
private EmployeePanel employeePanel;
private Container container;
public EmployeeFrame()
{
initComponents();
setApperrances();
addListeners();
}
private void initComponents()
{
employeePanel=new EmployeePanel();
container=getContentPane();
}
private void setApperrances()
{
setLayout(new BorderLayout());
add(employeePanel,BorderLayout.CENTER);
setSize(700,500);
Dimension d=Toolkit.getDefaultToolkit().getScreenSize();
setLocation(d.width/2-getWidth()/2,d.height/2-getHeight()/2);
setDefaultCloseOperation(EXIT_ON_CLOSE);
}
private void addListeners()
{

}
}