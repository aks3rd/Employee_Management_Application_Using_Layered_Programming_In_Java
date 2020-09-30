import java.io.*;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.itextpdf.text.pdf.PdfWriter;
class eg10
{
private static String FILE="eg10.pdf";
private static Font headerFont=new Font(Font.FontFamily.TIMES_ROMAN, 18,Font.BOLD,BaseColor.RED);
private static Font subFont=new Font(Font.FontFamily.TIMES_ROMAN,16,Font.BOLD,BaseColor.BLUE);
private static Font redFont=new Font(Font.FontFamily.TIMES_ROMAN,11,Font.BOLD,BaseColor.RED);
private static String USER_PASSWORD = "password";
private static String OWNER_PASSWORD = "akash";
public static void main(String gg[])
{
try
{
//Document document=new Document();
//Document document=new Document(PageSize.A4);
Document document=new Document(PageSize.A4,50,50,50,50);
File file=new File(FILE);
if(file.exists()==false)
{
file.createNewFile();
}
PdfWriter writer=PdfWriter.getInstance(document,new FileOutputStream(file));

writer.setEncryption(USER_PASSWORD.getBytes(),OWNER_PASSWORD.getBytes(), PdfWriter.ALLOW_PRINTING,PdfWriter.ENCRYPTION_AES_128);

document.open();
document.add(new Paragraph("Password Protected PDF",headerFont));
document.add(new Paragraph("Password Protected pdf example !!"));
document.close();
writer.close();
}catch(DocumentException de)
{

}
catch(IOException ioException)
{

}
catch(Exception e)
{
e.printStackTrace();
}
}

}