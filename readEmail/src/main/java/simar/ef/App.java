package simar.ef;



/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	ReadEmail msees = new ReadEmail();
        msees.readEmails();
        ReadExcel readExcel=new ReadExcel();
        readExcel.readAllFile();
    }
}
