import java.io.*;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
class eg1
{
private static String FILE="eg1.pdf";
private static Font headerFont=new Font(Font.FontFamily.TIMES_ROMAN, 18,Font.BOLD);
private static Font subFont=new Font(Font.FontFamily.TIMES_ROMAN,16,Font.BOLD);

public static void main(String gg[])
{
try
{
//Document document=new Document();
Document document=new Document(PageSize.A4);
//Document document=new Document(PageSize.A4,50,50,50,50);
File file=new File(FILE);
if(file.exists()==false)
{
file.createNewFile();
}
PdfWriter writer=PdfWriter.getInstance(document,new FileOutputStream(file));
document.open();
document.add(new Paragraph("Hello Dear,"));
document.close();
writer.close();
}catch(DocumentException de)
{

}
catch(IOException ioException)
{

}
}

}