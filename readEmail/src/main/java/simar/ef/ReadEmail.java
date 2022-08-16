package simar.ef;

import java.net.URI;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import microsoft.exchange.webservices.data.core.ExchangeService;
import microsoft.exchange.webservices.data.core.PropertySet;
import microsoft.exchange.webservices.data.core.enumeration.misc.ExchangeVersion;
import microsoft.exchange.webservices.data.core.enumeration.property.WellKnownFolderName;
import microsoft.exchange.webservices.data.core.enumeration.service.ConflictResolutionMode;
import microsoft.exchange.webservices.data.core.service.folder.Folder;
import microsoft.exchange.webservices.data.core.service.item.EmailMessage;
import microsoft.exchange.webservices.data.core.service.item.Item;
import microsoft.exchange.webservices.data.credential.ExchangeCredentials;
import microsoft.exchange.webservices.data.credential.WebCredentials;
import microsoft.exchange.webservices.data.property.complex.Attachment;
import microsoft.exchange.webservices.data.property.complex.FileAttachment;
import microsoft.exchange.webservices.data.search.FindItemsResults;
import microsoft.exchange.webservices.data.search.ItemView;

public class ReadEmail {

//	Logger log = LoggerFactory.getLogger(ReadEmailCoba.class);
 	private static ExchangeService service;
    private static Integer NUMBER_EMAILS_FETCH = 100; // only latest 5 emails/appointments are fetched.

    private static String URI_SERVICE = "https://em.d-appspecto.com/ews/exchange.asmx";
    private static String USERNAME = "u071779";
    private static String PASSWORD = "bcabca";
    private static String DOMAIN = "dtidomain";
    
    private static String URL_SERVICE_VEN_CONFIRM="http://10.20.214.170:53073/simar-api/inquiry-pengajuan/vendor-konfirmation";
    private static String URL_SERVICE_ASSIGN_OPR="http://10.20.214.170:53073/simar-api/inquiry-pengajuan/vendor-assign-opr";
    private static String URL_SERVICE_DAILY_REPPORT="http://10.20.214.170:53073/simar-api/inquiry-pengajuan/vendor-daily-report";
    private static String PATH_TEMP_DIR="C:\\\\Users\\\\U546591\\\\Rayhan\\\\Email\\\\";
    
    static Logger log = Logger.getLogger("rootLogger"); 
    static Logger logEmail =Logger.getLogger("com.ews.email");
    static Logger logExcel =Logger.getLogger("com.ews.excel");
    /**
     * Firstly check, whether "https://webmail.xxxx.com/ews/Services.wsdl" and
     * "https://webmail.xxxx.com/ews/Exchange.asmx" is accessible, if yes that
     * means the Exchange Webservice is enabled on your MS Exchange.
     */
    static {
        try {
            service = new ExchangeService(ExchangeVersion.Exchange2010_SP2);
            //service = new ExchangeService(ExchangeVersion.Exchange2007_SP1); //depending on the version of your Exchange. 
            service.setUrl(new URI(URI_SERVICE));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Initialize the Exchange Credentials. Don't forget to replace the
     * "USRNAME","PWD","DOMAIN_NAME" variables.
     */
    public ReadEmail() {
        ExchangeCredentials credentials = new WebCredentials(USERNAME, PASSWORD, DOMAIN);
        service.setCredentials(credentials);
    }
    
    public List readEmails() {
        List msgDataList = new ArrayList();
        List msgDataError= new ArrayList<Object>();
        List msgDataNotProcess= new ArrayList<Object>();
        try {
            Folder folder = Folder.bind(service, WellKnownFolderName.Inbox);
            FindItemsResults<Item> results = service.findItems(folder.getId(), new ItemView(NUMBER_EMAILS_FETCH));
            int i = 1;
            for (Item item : results) {
                Item itm = Item.bind(service, item.getId(), PropertySet.FirstClassProperties);
                EmailMessage emailMessage = EmailMessage.bind(service, itm.getId());
//            List<Attachment> lstAttach = emailMessage.getAttachments().getItems();
                String senderEmail=emailMessage.getFrom().getAddress();
                String subject = emailMessage.getSubject();
                boolean isRead = emailMessage.getIsRead();
                Date date=emailMessage.getDateTimeReceived();
//                System.out.println("Read Email Ke : " + i++);
//                System.out.println("subject : " + subject);	              
//                System.out.println("isRead: " + isRead);
                String reportLog=":ke-"+ i++ +"[" +date+"-"+senderEmail+"-"+subject+"]";
                logEmail.info(reportLog);               
                if (subject.toLowerCase().contains("konfirmasi pengajuan arsip") && !isRead) {
//                	 System.out.println("proccessing : " + subject);
                	  
                	  if (emailMessage.getHasAttachments()) {
                        for (Attachment attach : emailMessage.getAttachments()) {
                            FileAttachment fleAttach = (FileAttachment) attach;
                            String fileName = fleAttach.getName();
//                            System.out.println("fileName: " + fileName);
//                            System.out.println("conten type: " + fleAttach.getContentType());
                            logEmail.info("fileName: " + fileName);
                            if(fileName.toLowerCase().contains("konfirmasi")) {
                            	String newFileName="1#_"+senderEmail+"#_"+fileName;
                            	logEmail.info(newFileName);
                            	fleAttach.load(PATH_TEMP_DIR + newFileName);
                            	msgDataList.add("succes"+reportLog);
                            }else {
                            	logEmail.info("attachment is not proccess");
//                            	 System.out.println("attachment is not proccess: ");
                            }
//                            make read and deleted email	                           
	                        emailMessage.setIsRead(true);
	                        emailMessage.update(ConflictResolutionMode.AutoResolve);
//	                        System.out.println("set isRead: " + emailMessage.getIsRead().toString());
//	                        System.out.println("Move to Deleted Folder..");
//	                        emailMessage.delete(DeleteMode.MoveToDeletedItems);
//	                        System.out.println("Email Deleted");
                            
                        }
                    }else {
                    	 logEmail.info("email not attachment");
                    }
                }else if(subject.toLowerCase().contains("penugasan pengajuan arsip") && !isRead) {
//                	System.out.println("subject : " + subject);
                	  if (emailMessage.getHasAttachments()) {
                        for (Attachment attach : emailMessage.getAttachments()) {
                            FileAttachment fleAttach = (FileAttachment) attach;
                            String fileName = fleAttach.getName();
//                            System.out.println("fileName: " + fileName);
//                            System.out.println("conten type: " + fleAttach.getContentType());
                            if(fileName.toLowerCase().contains("assign")) {
                            	String newFileName="2#_"+senderEmail+"#_"+fileName;
                            	logEmail.info("fileName: " + fileName);
                            	fleAttach.load(PATH_TEMP_DIR+ newFileName);
                            	msgDataList.add("succes"+reportLog);
                            }else {
//                            	 System.out.println("attachment is not proccess: ");
                            	logEmail.info("attachment is not proccess");
                            }
//                            make read and deleted email
	                        emailMessage.setIsRead(true);
	                        emailMessage.update(ConflictResolutionMode.AutoResolve);
//	                        System.out.println("isRead: " + emailMessage.getIsRead().toString());
//	                        System.out.println("Move to Deleted Folder..");
//	                        emailMessage.delete(DeleteMode.MoveToDeletedItems);
//	                        System.out.println("Email Deleted");
                            
                        }
                    }
                }else if(subject.toLowerCase().contains("laporan harian") && !isRead) {
                	System.out.println("subject : " + subject);
                	  if (emailMessage.getHasAttachments()) {
                        for (Attachment attach : emailMessage.getAttachments()) {
                            FileAttachment fleAttach = (FileAttachment) attach;
                            String fileName = fleAttach.getName();
                            System.out.println("fileName: " + fileName);
                            System.out.println("conten type: " + fleAttach.getContentType());
                            if(fileName.toLowerCase().contains("laporan")) {
                            	String newFileName="3#_"+senderEmail+"#_"+fileName;
                            	logEmail.info("fileName: " + fileName);
                            	fleAttach.load(PATH_TEMP_DIR + newFileName);
                            	msgDataList.add("succes"+reportLog);
                            }else {
//                            	 System.out.println("attachment is not proccess: ");
                            	logEmail.info("attachment is not proccess");
                            }
//                            make read and deleted email
	                        emailMessage.setIsRead(true);
	                        emailMessage.update(ConflictResolutionMode.AutoResolve);
//	                        System.out.println("isRead: " + emailMessage.getIsRead().toString());
//	                        System.out.println("Move to Deleted Folder..");
//	                        emailMessage.delete(DeleteMode.MoveToDeletedItems);
//	                        System.out.println("Email Deleted");
                            
                            
                        }
                    }
                }
                else if(subject.toLowerCase().contains("pengajuan permanent out") && !isRead) {
                    System.out.println("subject : " + subject);
                    if (emailMessage.getHasAttachments()) {
                        for (Attachment attach : emailMessage.getAttachments()) {
                            FileAttachment fleAttach = (FileAttachment) attach;
                            String fileName = fleAttach.getName();
                            System.out.println("fileName: " + fileName);
                            System.out.println("conten type: " + fleAttach.getContentType());
                            if(fileName.toLowerCase().contains("permanent out")) {
                                String newFileName="4#_"+senderEmail+"#_"+fileName;
                                logEmail.info("fileName: " + fileName);
                                fleAttach.load(PATH_TEMP_DIR + newFileName);
                                msgDataList.add("succes"+reportLog);
                            }else {
//                            	 System.out.println("attachment is not proccess: ");
                                logEmail.info("attachment is not proccess");
                            }
//                            make read and deleted email
                            emailMessage.setIsRead(true);
                            emailMessage.update(ConflictResolutionMode.AutoResolve);
//	                        System.out.println("isRead: " + emailMessage.getIsRead().toString());
//	                        System.out.println("Move to Deleted Folder..");
//	                        emailMessage.delete(DeleteMode.MoveToDeletedItems);
//	                        System.out.println("Email Deleted");


                        }
                    }
                }
                else {
                	 logEmail.info("not processed email");	  
                	 msgDataNotProcess.add("not processed not contain email subject"+reportLog);
                	  emailMessage.setIsRead(true);
                      emailMessage.update(ConflictResolutionMode.AutoResolve);
//                      System.out.println("isRead: " + emailMessage.getIsRead().toString());
                }
            }
        } catch (Exception e) {
        	msgDataError.add("failed:"+e.getMessage());
        	logEmail.error(e.getMessage());
            e.printStackTrace();
        }
        System.out.println("Read email report");
        System.out.println("succes process email: "+msgDataList.size());
        System.out.println("not process read email: "+msgDataNotProcess.size()); 
        System.out.println("Fail read email: "+msgDataError.size());
        if(msgDataError.size()>0) {
        	System.out.println("error read email: "+msgDataError);
        }
        return msgDataList;
    }
}
