package com.thinking.machines.hr.pl.ui;
import com.thinking.machines.hr.pl.model.*;
import com.thinking.machines.hr.pl.exceptions.*;
import com.thinking.machines.hr.bl.interfaces.*;
import com.thinking.machines.hr.bl.exceptions.*;
import com.thinking.machines.hr.bl.pojo.*;
import com.thinking.machines.hr.bl.manager.*;
import java.util.*;
import java.math.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;
import javax.swing.filechooser.*;
import java.io.*;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.itextpdf.text.pdf.draw.DottedLineSeparator;

public class EmployeePanel extends JPanel
{
private JTable table;
private JTableHeader tableHeader;
private JScrollPane jsp;
private EmployeeModel employeeModel;
private JLabel searchLabel,searchErrorLabel;
private JTextField searchTextField;
private java.awt.Image img,newimg;
private ImageIcon crossIcon;
private JButton crossButton;
private EmployeeCRUDPanel employeeCRUDPanel;
private enum Mode{VIEW,ADD,EDIT,DELETE,CANCEL,EXPORT_TO_PDF};
private Mode mode=Mode.VIEW;
public EmployeePanel()
{
initComponents();
setApperrances();
addListeners();
setViewMode();
}
private void initComponents()
{
employeeCRUDPanel=new EmployeeCRUDPanel();
employeeModel=new EmployeeModel();
table=new JTable(employeeModel);
tableHeader=table.getTableHeader();

searchErrorLabel=new JLabel(" ");

searchLabel=new JLabel("Employee Search : ");
searchTextField=new JTextField(100);

//crossIcon=new ImageIcon("images/crossIcon.png");

//   src/main/resources/ ise bhi likh shakte hai
crossIcon=new ImageIcon(getClass().getClassLoader().getResource("images/crossIcon.png"));
crossButton=new JButton(crossIcon);
//crossButton=new JButton("C");

jsp=new JScrollPane(table,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

}
private void setApperrances()
{
java.awt.Font f=new java.awt.Font("Verdana",java.awt.Font.PLAIN,16);
table.setFont(f);
table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
table.getColumnModel().getColumn(0).setPreferredWidth(120);
table.getColumnModel().getColumn(1).setPreferredWidth(120);
table.getColumnModel().getColumn(2).setPreferredWidth(410);
table.setRowHeight(30);

table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

java.awt.Font f2=new java.awt.Font("Verdana",java.awt.Font.BOLD,16);
tableHeader.setFont(f2);
tableHeader.setReorderingAllowed(false);
tableHeader.setResizingAllowed(false);

setLayout(null);

searchErrorLabel.setBounds(250,0,100,20);
add(searchErrorLabel);

searchLabel.setBounds(10,20,160,30);
searchLabel.setFont(f);
add(searchLabel);

searchTextField.setBounds(170,20,160,30);
add(searchTextField);

crossButton.setBounds(340,20,50,30);
img=crossIcon.getImage() ;  
newimg=img.getScaledInstance(50,30,java.awt.Image.SCALE_SMOOTH) ;  
crossIcon=new ImageIcon(newimg);
crossButton.setIcon(crossIcon);
//crossButton.setBorderPainted(false);
//crossButton.setFocusPainted(false);
//crossButton.setContentAreaFilled(false);
//crossButton.setFont(f);
add(crossButton);

jsp.setBounds(10,60,690,200);
jsp.setSize(670,200);
add(jsp);

employeeCRUDPanel.setBounds(10,270,670,190);
add(employeeCRUDPanel);
setSize(1000,1000);
setLocation(10,10);
setVisible(true);

}
private void addListeners()
{
searchTextField.getDocument().addDocumentListener(new DocumentListener()
{
public void changedUpdate(DocumentEvent de)
{
String str=searchTextField.getText().trim();
search(str);
}
public void insertUpdate(DocumentEvent de)
{
changedUpdate(de);
}
public void removeUpdate(DocumentEvent de)
{
changedUpdate(de);
}
});

ListSelectionModel cellSelectionModel = table.getSelectionModel();
//cellSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
cellSelectionModel.addListSelectionListener(new ListSelectionListener() 
{
public void valueChanged(ListSelectionEvent e)
{
try
{
int rowIndex=table.getSelectedRow();
if(!(rowIndex<0 || rowIndex>=table.getRowCount()))
{
employeeCRUDPanel.setEmployee(employeeModel.getEmployeeAt(rowIndex));
}
}catch(ModelException me)
{
//do nothing
}

}
});

crossButton.addActionListener(new ActionListener()
{
public void actionPerformed(ActionEvent ae)
{
searchTextField.setText(null);
crossButton.setVisible(false);
}
});
}
public void search(String str)
{
searchErrorLabel.setText(" ");
if(str==null)return;
if(str.length()==0)
{
//System.out.println("Selection Clear Huaa");
//table.clearSelection();
crossButton.setVisible(false);
return;
}
crossButton.setVisible(true);
try
{

int rowIndex=employeeModel.search(str);
table.setRowSelectionInterval(rowIndex,rowIndex);
table.getSelectionModel().setSelectionInterval(rowIndex,rowIndex);
table.scrollRectToVisible(new java.awt.Rectangle(table.getCellRect(rowIndex,2,true)));

}catch(ModelException me)
{
//do nothing
searchErrorLabel.setVisible(true);
searchErrorLabel.setText(me.getMessage());
java.awt.Font errorF=new java.awt.Font("Verdana",java.awt.Font.PLAIN,16);
searchErrorLabel.setForeground(Color.red);
}
}
private void setViewMode()
{
searchLabel.setVisible(true);
searchTextField.setVisible(true);
searchErrorLabel.setVisible(false);
crossButton.setVisible(false);
if(EmployeePanel.this.mode==Mode.ADD ||EmployeePanel.this.mode==Mode.EDIT)
{
searchLabel.setEnabled(true);
searchTextField.setEnabled(true);
searchErrorLabel.setVisible(true);
searchErrorLabel.setEnabled(true);
jsp.getViewport().getView().setEnabled(true);

//crossButton.setVisible(true);
if(searchTextField.getText().equals(""))
{
crossButton.setEnabled(true);
crossButton.setVisible(false);
}
else
{
crossButton.setEnabled(true);
crossButton.setVisible(true);
}
}
}
private void setAddMode()
{
searchLabel.setEnabled(false);
searchErrorLabel.setEnabled(false);
searchTextField.setEnabled(false);
crossButton.setEnabled(false);
jsp.getViewport().getView().setEnabled(false);
}
private void setEditMode()
{
searchLabel.setEnabled(false);
searchErrorLabel.setEnabled(false);
searchTextField.setEnabled(false);
crossButton.setEnabled(false);
jsp.getViewport().getView().setEnabled(false);
}
private void setDeleteMode()
{

}
private void setExport_to_pdfMode()
{

}

private class EmployeeCRUDPanel extends JPanel
{
private int lm,rm;
private JLabel codeLabel,nameLabel,genderLabel,salaryLabel,panNumberLabel;
private JLabel code,name,gender,salary,panNumber;
private JLabel tmpCode;
private JTextField nameText,salaryText,panNumberText;
private JRadioButton male,female;
private ButtonGroup genderButton;
private java.awt.Image img,newimg;
private ImageIcon crossIcon;
private JButton crossButton1,crossButton2,crossButton3;
private ImageIcon addIcon,saveIcon,editIcon,cancelIcon,deleteIcon,savePdfIcon;
private JButton addButton,editButton,cancelButton,deleteButton,savePdfButton;
EmployeeCRUDPanel()
{
initComponents();
setApperrances();
addListeners();
setViewMode();
}
private void initComponents()
{
lm=10;
rm=10;
codeLabel=new JLabel("Code : ");
nameLabel=new JLabel("Name : ");
genderLabel=new JLabel("Gender : ");
salaryLabel=new JLabel("Salary : ");
panNumberLabel=new JLabel("PAN Number : ");
code=new JLabel("_______");
tmpCode=new JLabel("0");
name=new JLabel("_______");
gender=new JLabel("_______");
salary=new JLabel("_______");
panNumber=new JLabel("_______");
//codeText=new JTextField(150);
nameText=new JTextField("",150);
//genderText=new JTextField("",150);
salaryText=new JTextField("",150);
panNumberText=new JTextField("",150);

male=new JRadioButton("Male");
female=new JRadioButton("Female");
//male.setMnemonic(KeyEvent.VK_M);
//female.setMnemonic(KeyEvent.VK_F);
genderButton=new ButtonGroup();
genderButton.add(male);
genderButton.add(female);
male.setSelected(true);

//crossIcon=new ImageIcon("images/crossIcon.png");
crossIcon=new ImageIcon(getClass().getClassLoader().getResource("src/main/resources/images/crossIcon.png"));
crossButton1=new JButton(crossIcon);
crossButton2=new JButton(crossIcon);
crossButton3=new JButton(crossIcon);

addIcon=new ImageIcon(getClass().getClassLoader().getResource("src/main/resources/images/addIcon.png"));
saveIcon=new ImageIcon(getClass().getClassLoader().getResource("src/main/resources/images/saveIcon.png"));
addButton=new JButton(addIcon);
editIcon=new ImageIcon(getClass().getClassLoader().getResource("src/main/resources/images/editIcon.png"));
editButton=new JButton(editIcon);
cancelIcon=new ImageIcon(getClass().getClassLoader().getResource("src/main/resources/images/backIcon.png"));
cancelButton=new JButton(cancelIcon);
deleteIcon=new ImageIcon(getClass().getClassLoader().getResource("src/main/resources/images/deleteIcon.png"));
deleteButton=new JButton(deleteIcon);
savePdfIcon=new ImageIcon(getClass().getClassLoader().getResource("src/main/resources/images/savePdfIcon.png"));
savePdfButton=new JButton(savePdfIcon);

}
private void setApperrances()
{
setLayout(null);
codeLabel.setBounds(lm+0,rm+0,60,20);
add(codeLabel);
code.setBounds(lm+70,rm+0,200,20);
add(code);
tmpCode.setBounds(lm+70,rm+0,200,20);
add(tmpCode);

//codeText.setBounds(lm+70,rm+0,150,20);
//add(codeText);

nameLabel.setBounds(lm+300,rm+0,90,20);
add(nameLabel);
name.setBounds(lm+400,rm+0,200,20);
add(name);
nameText.setBounds(lm+400,rm+0,200,20);
add(nameText);

genderLabel.setBounds(lm+0,rm+30,60,20);
add(genderLabel);
gender.setBounds(lm+70,rm+30,200,20);
add(gender);
//genderText.setBounds(lm+70,rm+30,150,20);
//add(genderText);
male.setBounds(lm+70,rm+30,60,20);
add(male);
female.setBounds(lm+140,rm+30,70,20);
add(female);

panNumberLabel.setBounds(lm+300,rm+30,90,20);
add(panNumberLabel);
panNumber.setBounds(lm+400,rm+30,200,20);
add(panNumber);
panNumberText.setBounds(lm+400,rm+30,200,20);
add(panNumberText);

salaryLabel.setBounds(lm+0,rm+60,60,20);
add(salaryLabel);
salary.setBounds(lm+70,rm+60,200,20);
add(salary);
salaryText.setBounds(lm+70,rm+60,150,20);
add(salaryText);

crossButton1.setBounds(lm+610,rm+0,40,20);
img=crossIcon.getImage() ;  
newimg=img.getScaledInstance(40,20,java.awt.Image.SCALE_SMOOTH) ;  
crossIcon=new ImageIcon(newimg);
crossButton1.setIcon(crossIcon);
add(crossButton1);

crossButton2.setBounds(lm+610,rm+30,40,20);
img=crossIcon.getImage() ;  
newimg=img.getScaledInstance(40,20,java.awt.Image.SCALE_SMOOTH) ;  
crossIcon=new ImageIcon(newimg);
crossButton2.setIcon(crossIcon);
add(crossButton2);

crossButton3.setBounds(lm+230,rm+60,40,20);
img=crossIcon.getImage() ;  
newimg=img.getScaledInstance(40,20,java.awt.Image.SCALE_SMOOTH) ;  
crossIcon=new ImageIcon(newimg);
crossButton3.setIcon(crossIcon);
add(crossButton3);

addButton.setBounds(lm+70,rm+120,50,50);
img=addIcon.getImage() ;  
newimg=img.getScaledInstance(50,50,java.awt.Image.SCALE_SMOOTH) ;  
addIcon=new ImageIcon(newimg);
addButton.setIcon(addIcon);
add(addButton);

editButton.setBounds(lm+170,rm+120,50,50);
img=editIcon.getImage() ;  
newimg=img.getScaledInstance(50,50,java.awt.Image.SCALE_SMOOTH) ;  
editIcon=new ImageIcon(newimg);
editButton.setIcon(editIcon);
add(editButton);

cancelButton.setBounds(lm+270,rm+120,50,50);
img=cancelIcon.getImage() ;  
newimg=img.getScaledInstance(50,50,java.awt.Image.SCALE_SMOOTH) ;  
cancelIcon=new ImageIcon(newimg);
cancelButton.setIcon(cancelIcon);
add(cancelButton);

deleteButton.setBounds(lm+370,rm+120,50,50);
img=deleteIcon.getImage() ;  
newimg=img.getScaledInstance(50,50,java.awt.Image.SCALE_SMOOTH) ;  
deleteIcon=new ImageIcon(newimg);
deleteButton.setIcon(deleteIcon);
add(deleteButton);

savePdfButton.setBounds(lm+470,rm+120,50,50);
img=savePdfIcon.getImage() ;  
newimg=img.getScaledInstance(50,50,java.awt.Image.SCALE_SMOOTH) ;  
savePdfIcon=new ImageIcon(newimg);
savePdfButton.setIcon(savePdfIcon);
add(savePdfButton);

setBorder(BorderFactory.createLineBorder(new Color(22,22,22)));
setSize(670,190);
setVisible(true);
setLocation(10,10);
}
private void addListeners()
{
addButton.addActionListener(new ActionListener()
{
public void actionPerformed(ActionEvent ae)
{
if(!(EmployeePanel.this.mode==Mode.ADD))
{
EmployeePanel.this.setAddMode();
EmployeeCRUDPanel.this.setAddMode();
EmployeePanel.this.mode=Mode.ADD;
return;
}
else
{
try
{
int vCode=Integer.parseInt(tmpCode.getText());
String vName=nameText.getText().trim();
String vPanNumber=panNumberText.getText().trim();
BigDecimal vSalary=new BigDecimal(salaryText.getText().trim());
String vGender="M";

if(genderButton.getSelection()==male.getModel())
{
vGender="M";
//System.out.println("Male selected");
}
if(genderButton.getSelection()==female.getModel())
{
vGender="F";
//System.out.println("Male selected");
}
ModelValidationException validationException=new ModelValidationException();
if(vCode!=0)
{
validationException.addException("code","Should be zero");
}
if(vName==null || vName.trim().length()==0)
{
validationException.addException("name","Required");
}
if(vName!=null && vName.trim().length()>30)
{
validationException.addException("name","Should not exceed 30 characters");
}
if(vGender==null || vGender.trim().length()==0)
{
validationException.addException("gender","Required");
}
if(!vGender.equals("M") && !vGender.equals("F"))
{
validationException.addException("gender","Invalid");
}
if(vSalary==null)
{
validationException.addException("salary","Required");
}
else
{
try
{
if(vSalary.signum()==-1)
{
validationException.addException("salary","Invalid");
}
}catch(NumberFormatException numberFormatException)
{
validationException.addException("salary","Invalid");
}
}//else end
if(vPanNumber==null || vPanNumber.trim().length()==0)
{
validationException.addException("panNumber","Required");
}
if(vPanNumber!=null && vPanNumber.length()>15)
{
validationException.addException("panNumber","Cannot exceed 15 characters");
}
/*
if(vPanNumber!=null && panNumberWiseEmployeesMap.containsKey(employee.getPANNumber().toUpperCase()))
{
validationException.addException("panNumber","Exists");
}
*/
if(validationException.hasExceptions())throw validationException;
EmployeeInterface plEmployee=new Employee();
plEmployee.setCode(vCode);
plEmployee.setName(vName);
plEmployee.setGender(vGender);
plEmployee.setSalary(vSalary);
plEmployee.setPANNumber(vPanNumber);
employeeModel.add(plEmployee);
int rowIndex=employeeModel.getIndexOf(plEmployee.getCode());
table.setRowSelectionInterval(rowIndex,rowIndex);
table.getSelectionModel().setSelectionInterval(rowIndex,rowIndex);
table.scrollRectToVisible(new java.awt.Rectangle(table.getCellRect(rowIndex,2,true)));
employeeCRUDPanel.setEmployee(plEmployee);
EmployeePanel.this.setViewMode();
EmployeeCRUDPanel.this.setViewMode();
EmployeePanel.this.mode=Mode.VIEW;
/*
System.out.println(vCode);
System.out.println(vName);
System.out.println(vPanNumber);
System.out.println(vSalary);
System.out.println(vGender);
*/
}catch(NumberFormatException nfe)
{
if(nfe.getMessage()==null)
{
JOptionPane.showMessageDialog(EmployeeCRUDPanel.this,"Salary should not be null");
return;
}
JOptionPane.showMessageDialog(EmployeeCRUDPanel.this,nfe.getMessage());
}
catch(ModelException me)
{
JOptionPane.showMessageDialog(EmployeeCRUDPanel.this,me.getMessage());
}
catch(ModelValidationException mve)
{
java.util.List<String> list=mve.getAllException();
list.forEach(e->{
System.out.print(e+" : ");
System.out.println(mve.getException(e));}); 
}
}
}
});

editButton.addActionListener(new ActionListener()
{
public void actionPerformed(ActionEvent ae)
{
if(table.getSelectionModel().isSelectionEmpty())
{
JOptionPane.showMessageDialog(EmployeeCRUDPanel.this,"Employee is not selected");
return;
}
if(EmployeePanel.this.mode==Mode.VIEW)
{
EmployeePanel.this.setEditMode();
EmployeeCRUDPanel.this.setEditMode();
EmployeePanel.this.mode=Mode.EDIT;
try
{
int rowIndex=table.getSelectedRow();
if(!(rowIndex<0 || rowIndex>=table.getRowCount()))
{
employeeCRUDPanel.setEmployee(employeeModel.getEmployeeAt(rowIndex));
}
}catch(ModelException me)
{
//do nothing
}
return;
}
else
{
try
{
int vCode=Integer.parseInt(tmpCode.getText());
String vName=nameText.getText().trim();
String vPanNumber=panNumberText.getText().trim();
BigDecimal vSalary=new BigDecimal(salaryText.getText().trim());
String vGender="M";

if(genderButton.getSelection()==male.getModel())
{
vGender="M";
//System.out.println("Male selected");
}
if(genderButton.getSelection()==female.getModel())
{
vGender="F";
//System.out.println("FeMale selected");
}
ModelValidationException validationException=new ModelValidationException();

if(vName==null || vName.trim().length()==0)
{
validationException.addException("name","Required");
}
if(vName!=null && vName.trim().length()>30)
{
validationException.addException("name","Should not exceed 30 characters");
}
if(vGender==null || vGender.trim().length()==0)
{
validationException.addException("gender","Required");
}
if(!vGender.equals("M") && !vGender.equals("F"))
{
validationException.addException("gender","Invalid");
}
if(vSalary==null)
{
validationException.addException("salary","Required");
}
else
{
try
{
if(vSalary.signum()==-1)
{
validationException.addException("salary","Invalid");
}
}catch(NumberFormatException numberFormatException)
{
validationException.addException("salary","Invalid");
}
}//else end
if(vPanNumber==null || vPanNumber.trim().length()==0)
{
validationException.addException("panNumber","Required");
}
if(vPanNumber!=null && vPanNumber.length()>15)
{
validationException.addException("panNumber","Cannot exceed 15 characters");
}
/*
if(vPanNumber!=null && panNumberWiseEmployeesMap.containsKey(employee.getPANNumber().toUpperCase()))
{
validationException.addException("panNumber","Exists");
}
*/
if(validationException.hasExceptions())throw validationException;
EmployeeInterface plEmployee=new Employee();
plEmployee.setCode(vCode);
plEmployee.setName(vName);
plEmployee.setGender(vGender);
plEmployee.setSalary(vSalary);
plEmployee.setPANNumber(vPanNumber);

employeeModel.edit(plEmployee);
int rowIndex=employeeModel.getIndexOf(plEmployee.getCode());
table.setRowSelectionInterval(rowIndex,rowIndex);
table.getSelectionModel().setSelectionInterval(rowIndex,rowIndex);
table.scrollRectToVisible(new java.awt.Rectangle(table.getCellRect(rowIndex,2,true)));

EmployeePanel.this.setViewMode();
EmployeeCRUDPanel.this.setViewMode();
EmployeePanel.this.mode=Mode.VIEW;

employeeCRUDPanel.setEmployee(employeeModel.getEmployeeAt(rowIndex));

/*
System.out.println(vCode);
System.out.println(vName);
System.out.println(vPanNumber);
System.out.println(vSalary);
System.out.println(vGender);
*/
}catch(NumberFormatException nfe)
{
if(nfe.getMessage()==null)
{
JOptionPane.showMessageDialog(EmployeeCRUDPanel.this,"Salary should not be null");
return;
}
JOptionPane.showMessageDialog(EmployeeCRUDPanel.this,nfe.getMessage());
}
catch(ModelException me)
{
JOptionPane.showMessageDialog(EmployeeCRUDPanel.this,me.getMessage());
}
catch(ModelValidationException mve)
{
java.util.List<String> list=mve.getAllException();
list.forEach(e->{
System.out.print(e+" : ");
System.out.println(mve.getException(e));}); 
}
}
}
});
deleteButton.addActionListener(new ActionListener()
{
public void actionPerformed(ActionEvent ae)
{
if(table.getSelectionModel().isSelectionEmpty())
{
JOptionPane.showMessageDialog(EmployeeCRUDPanel.this,"Employee is not selected");
return;
}
if(EmployeePanel.this.mode==Mode.VIEW)
{
EmployeePanel.this.setDeleteMode();
EmployeeCRUDPanel.this.setDeleteMode();
EmployeePanel.this.mode=Mode.DELETE;
}
int dialogButton=JOptionPane.YES_NO_OPTION;
int dialogResult=JOptionPane.showConfirmDialog (EmployeeCRUDPanel.this, "Do You Want To Delete This Employee Record ?","Warning",dialogButton);	
if(dialogResult==JOptionPane.YES_OPTION)
{
int rowIndex=table.getSelectedRow();
if(!(rowIndex<0 || rowIndex>=table.getRowCount()))
{
try
{
employeeModel.delete(rowIndex);
employeeCRUDPanel.setEmployee(null);
EmployeePanel.this.setViewMode();
EmployeeCRUDPanel.this.setViewMode();
EmployeePanel.this.mode=Mode.VIEW;
}catch(ModelException me)
{
JOptionPane.showMessageDialog(EmployeeCRUDPanel.this,me.getMessage());
}
catch(ModelValidationException mve)
{
java.util.List<String> list=mve.getAllException();
list.forEach(e->{
System.out.print(e+" : ");
System.out.println(mve.getException(e));}); 
}
}
System.out.println("Delete huaa");

}
else
{
System.out.println("Delete nhi huaa");
JOptionPane.showMessageDialog(EmployeeCRUDPanel.this,"Reord is Not Deleted");
}

}
});

savePdfButton.addActionListener(new ActionListener()
{
public void actionPerformed(ActionEvent ae)
{
System.out.println("Save Chala");
EmployeePanel.this.setExport_to_pdfMode();
EmployeeCRUDPanel.this.setExport_to_pdfMode();
JFileChooser jfc=new JFileChooser();
jfc.setAcceptAllFileFilterUsed(false);
FileNameExtensionFilter fileFilter=new FileNameExtensionFilter("PDF Files","pdf");
jfc.addChoosableFileFilter(fileFilter);
jfc.setCurrentDirectory(new File("."));
//chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
//chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
//chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
int selectedOption=jfc.showSaveDialog(EmployeePanel.this);
if(selectedOption==JFileChooser.APPROVE_OPTION)
{
File file=jfc.getSelectedFile();
String fullPath=file.getAbsolutePath();
File parent=new File(file.getParent());
if(parent.exists()==false)
{
JOptionPane.showMessageDialog(EmployeePanel.this,"Invalid path : "+fullPath);
EmployeePanel.this.setViewMode();
EmployeeCRUDPanel.this.setViewMode();
EmployeePanel.this.mode=Mode.VIEW;
return;
}
if(parent.isDirectory()==false)
{
JOptionPane.showMessageDialog(EmployeePanel.this,parent.getAbsolutePath()+" is not a folder");
EmployeePanel.this.setViewMode();
EmployeeCRUDPanel.this.setViewMode();
EmployeePanel.this.mode=Mode.VIEW;
return;
}
if(fullPath.endsWith(".pdf")==false)
{
if(fullPath.endsWith(".")) fullPath+="pdf";
else fullPath+=".pdf";
}
// code to export data to pdf starts here
try
{
com.itextpdf.text.Document document=new com.itextpdf.text.Document();
com.itextpdf.text.pdf.PdfWriter pdfWriter=com.itextpdf.text.pdf.PdfWriter.getInstance(document,new FileOutputStream(fullPath));
document.open();
com.itextpdf.text.Paragraph p=new com.itextpdf.text.Paragraph();

com.itextpdf.text.Image logo;
com.itextpdf.text.pdf.PdfPTable pdfTable=new PdfPTable(6);;
com.itextpdf.text.Paragraph paragraph;
com.itextpdf.text.Font firmNameFont=new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.TIMES_ROMAN,16,com.itextpdf.text.Font.BOLD);
com.itextpdf.text.Font titleFont=new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.TIMES_ROMAN,14,com.itextpdf.text.Font.BOLD);
com.itextpdf.text.Font columnTitleFont=new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.TIMES_ROMAN,12,com.itextpdf.text.Font.BOLD);
com.itextpdf.text.Font dataFont=new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.TIMES_ROMAN,12,com.itextpdf.text.Font.NORMAL);
String firmName="ABC Retailers";
//ItemDTO item;
int k=employeeModel.getRowCount();
int pageNumber=0;
int pageSize=40;
boolean newPage=true;
int x=0;
while(x<k)
{
if(newPage)
{
logo=com.itextpdf.text.Image.getInstance("src/main/resources/images/logo.png");
logo.setAbsolutePosition(50f,750.0f);
document.add(logo);
paragraph=new com.itextpdf.text.Paragraph(firmName,firmNameFont);
paragraph.setAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
document.add(paragraph);

paragraph=new com.itextpdf.text.Paragraph("Employees",titleFont);
paragraph.setAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
document.add(paragraph);
// add page number
paragraph=new com.itextpdf.text.Paragraph("Page No. "+(x+1));
paragraph.setAlignment(com.itextpdf.text.Element.ALIGN_RIGHT);
document.add(paragraph);

document.add(new Chunk(new DottedLineSeparator()));
// create table

//pdfTable=new PdfPTable(6);
pdfTable.setWidthPercentage(100); //Width 100%
pdfTable.setSpacingBefore(20f); //Space before table
pdfTable.setSpacingAfter(10f); //Space after table
//for set Alignment of all roes content
pdfTable.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
pdfTable.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
//for setRowHeight 
//pdfTable.getDefaultCell().setFixedHeight(30);

//Set Column widths
//float[] columnWidths = {1f, 1f, 1f};
float[] columnWidths = {1f,1f,3f,1f,2f,2f};
pdfTable.setWidths(columnWidths);

// add columns/titles
PdfPCell sno=new PdfPCell(new Paragraph("S.No.",columnTitleFont));
//sno.setBorderColor(BaseColor.BLUE);
//sno.setPaddingLeft(10);
sno.setHorizontalAlignment(Element.ALIGN_CENTER);
sno.setVerticalAlignment(Element.ALIGN_MIDDLE);

PdfPCell code=new PdfPCell(new Paragraph("Code",columnTitleFont));
code.setHorizontalAlignment(Element.ALIGN_CENTER);
code.setVerticalAlignment(Element.ALIGN_MIDDLE);

PdfPCell name=new PdfPCell(new Paragraph("Name",columnTitleFont));
name.setHorizontalAlignment(Element.ALIGN_CENTER);
name.setVerticalAlignment(Element.ALIGN_MIDDLE);

PdfPCell gender=new PdfPCell(new Paragraph("Gender",columnTitleFont));
gender.setHorizontalAlignment(Element.ALIGN_CENTER);
gender.setVerticalAlignment(Element.ALIGN_MIDDLE);

PdfPCell salary=new PdfPCell(new Paragraph("Salary",columnTitleFont));
salary.setHorizontalAlignment(Element.ALIGN_CENTER);
salary.setVerticalAlignment(Element.ALIGN_MIDDLE);

PdfPCell panNumber=new PdfPCell(new Paragraph("PAN Number",columnTitleFont));
panNumber.setHorizontalAlignment(Element.ALIGN_CENTER);
panNumber.setVerticalAlignment(Element.ALIGN_MIDDLE);

pdfTable.addCell(sno);
pdfTable.addCell(code);
pdfTable.addCell(name);
pdfTable.addCell(gender);
pdfTable.addCell(panNumber);
pdfTable.addCell(salary);

pageNumber++;
newPage=false;
}
try
{
//item=itemModel.getItemAt(x);
EmployeeInterface employee=employeeModel.getEmployeeAt(x);
// add row to table with data from item and serial number as(x+1)
pdfTable.addCell(String.valueOf(x+1));
pdfTable.addCell(String.valueOf(employee.getCode()));
pdfTable.addCell(employee.getName());
pdfTable.addCell(employee.getGender());
pdfTable.addCell(employee.getPANNumber());
pdfTable.addCell(employee.getSalary().toString());

}catch(ModelException modelException)
{
// do nothing
}
// create and add row to table
x++;
if(x%pageSize==0 || x==k)
{
// create footer
if(x!=k)
{
document.newPage();
newPage=true;
}
}
}




document.add(pdfTable);
document.close();
JOptionPane.showMessageDialog(EmployeeCRUDPanel.this,fullPath+"  created");
}catch(Throwable t)
{
System.out.println(t.getMessage());
JOptionPane.showMessageDialog(EmployeeCRUDPanel.this,"Cannot create pdf, contact administrator");
}
// code to export data to pdf ends here
}
EmployeePanel.this.setViewMode();
EmployeeCRUDPanel.this.setViewMode();






}
});


cancelButton.addActionListener(new ActionListener()
{
public void actionPerformed(ActionEvent ae)
{
if(EmployeePanel.this.mode==Mode.ADD || EmployeePanel.this.mode==Mode.EDIT)
{
EmployeePanel.this.setViewMode();
EmployeeCRUDPanel.this.setViewMode();
EmployeePanel.this.mode=Mode.VIEW;
return;
}
}
});

nameText.getDocument().addDocumentListener(new DocumentListener()
{
public void changedUpdate(DocumentEvent de)
{
String str=nameText.getText().trim();
if(str.length()==0)
{
crossButton1.setVisible(false);
return;
}
crossButton1.setVisible(true);
}

public void insertUpdate(DocumentEvent de)
{
changedUpdate(de);
}
public void removeUpdate(DocumentEvent de)
{
changedUpdate(de);
}
});
crossButton1.addActionListener(new ActionListener()
{
public void actionPerformed(ActionEvent ae)
{
nameText.setText(null);
crossButton1.setVisible(false);
}
});
panNumberText.getDocument().addDocumentListener(new DocumentListener()
{
public void changedUpdate(DocumentEvent de)
{
String str=panNumberText.getText().trim();
if(str.length()==0)
{
crossButton2.setVisible(false);
return;
}
crossButton2.setVisible(true);
}

public void insertUpdate(DocumentEvent de)
{
changedUpdate(de);
}
public void removeUpdate(DocumentEvent de)
{
changedUpdate(de);
}
});
crossButton2.addActionListener(new ActionListener()
{
public void actionPerformed(ActionEvent ae)
{
panNumberText.setText(null);
crossButton2.setVisible(false);
}
});
salaryText.getDocument().addDocumentListener(new DocumentListener()
{
public void changedUpdate(DocumentEvent de)
{
String str=salaryText.getText().trim();
if(str.length()==0)
{
crossButton3.setVisible(false);
return;
}
crossButton3.setVisible(true);
}

public void insertUpdate(DocumentEvent de)
{
changedUpdate(de);
}
public void removeUpdate(DocumentEvent de)
{
changedUpdate(de);
}
});
crossButton3.addActionListener(new ActionListener()
{
public void actionPerformed(ActionEvent ae)
{
salaryText.setText(null);
crossButton3.setVisible(false);
}
});
}
private void setEmployee(EmployeeInterface employee)
{
if(employee==null)
{
code.setText(" ");
name.setText(" ");
gender.setText(" ");
salary.setText(" ");
panNumber.setText(" ");
return;
}
if(EmployeePanel.this.mode==Mode.EDIT)
{
tmpCode.setText(String.valueOf(employee.getCode()));
nameText.setText(employee.getName());
salaryText.setText(employee.getSalary().toString());
panNumberText.setText(employee.getPANNumber());
if(employee.getGender().equals("M"))male.setSelected(true);
if(employee.getGender().equals("F"))female.setSelected(true);
return;
}
code.setText(String.valueOf(employee.getCode()));
name.setText(employee.getName());
gender.setText(employee.getGender());
salary.setText(employee.getSalary().toString());
panNumber.setText(employee.getPANNumber());
}
private void setViewMode()
{
codeLabel.setVisible(true);
nameLabel.setVisible(true);
genderLabel.setVisible(true);
salaryLabel.setVisible(true);
panNumberLabel.setVisible(true);
code.setVisible(true);
tmpCode.setVisible(false);
name.setVisible(true);
gender.setVisible(true);
salary.setVisible(true);
panNumber.setVisible(true);
nameText.setVisible(false);
salaryText.setVisible(false);
panNumberText.setVisible(false);
male.setVisible(false);
female.setVisible(false);
crossButton1.setVisible(false);
crossButton2.setVisible(false);
crossButton3.setVisible(false);
addButton.setVisible(true);
editButton.setVisible(true);
cancelButton.setVisible(false);
deleteButton.setVisible(true);
savePdfButton.setVisible(true);
if(EmployeePanel.this.mode==Mode.ADD)
{
img=addIcon.getImage();  
newimg=img.getScaledInstance(50,50,java.awt.Image.SCALE_SMOOTH) ;  
addIcon=new ImageIcon(newimg);
addButton.setIcon(addIcon);
}
if(EmployeePanel.this.mode==Mode.EDIT)
{
img=editIcon.getImage();  
newimg=img.getScaledInstance(50,50,java.awt.Image.SCALE_SMOOTH) ;  
editIcon=new ImageIcon(newimg);
editButton.setIcon(editIcon);
}
}
private void setAddMode()
{
codeLabel.setVisible(true);
nameLabel.setVisible(true);
genderLabel.setVisible(true);
salaryLabel.setVisible(true);
panNumberLabel.setVisible(true);
code.setVisible(false);
tmpCode.setVisible(true);
name.setVisible(false);
gender.setVisible(false);
salary.setVisible(false);
panNumber.setVisible(false);
nameText.setText("");
salaryText.setText("");
panNumberText.setText("");
nameText.setVisible(true);
salaryText.setVisible(true);
panNumberText.setVisible(true);
male.setVisible(true);
female.setVisible(true);
crossButton1.setVisible(false);
crossButton2.setVisible(false);
crossButton3.setVisible(false);

img=saveIcon.getImage() ;  
newimg=img.getScaledInstance(50,50,java.awt.Image.SCALE_SMOOTH) ;  
saveIcon=new ImageIcon(newimg);
addButton.setIcon(saveIcon);


addButton.setVisible(true);
editButton.setVisible(false);
cancelButton.setVisible(true);
deleteButton.setVisible(false);
savePdfButton.setVisible(false);

}
private void setEditMode()
{
codeLabel.setVisible(true);
nameLabel.setVisible(true);
genderLabel.setVisible(true);
salaryLabel.setVisible(true);
panNumberLabel.setVisible(true);
code.setVisible(false);
tmpCode.setVisible(true);
name.setVisible(false);
gender.setVisible(false);
salary.setVisible(false);
panNumber.setVisible(false);
/*
nameText.setText("");
salaryText.setText("");
panNumberText.setText("");
*/
nameText.setVisible(true);
salaryText.setVisible(true);
panNumberText.setVisible(true);
male.setVisible(true);
female.setVisible(true);
crossButton1.setVisible(false);
crossButton2.setVisible(false);
crossButton3.setVisible(false);

img=saveIcon.getImage() ;  
newimg=img.getScaledInstance(50,50,java.awt.Image.SCALE_SMOOTH) ;  
saveIcon=new ImageIcon(newimg);
editButton.setIcon(saveIcon);


addButton.setVisible(false);
editButton.setVisible(true);
cancelButton.setVisible(true);
deleteButton.setVisible(false);
savePdfButton.setVisible(false);


}
private void setDeleteMode()
{

}
private void setExport_to_pdfMode()
{

}

}
}
