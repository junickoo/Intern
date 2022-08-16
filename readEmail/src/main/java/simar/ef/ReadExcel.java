package simar.ef;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import kong.unirest.json.JSONObject;

public class ReadExcel {
	private static String URL_SERVICE_VEN_CONFIRM="http://10.20.214.170:53073/simar-api/inquiry-pengajuan/vendor-konfirmation";
	private static String URL_SERVICE_ASSIGN_OPR="http://10.20.214.170:53073/simar-api/inquiry-pengajuan/vendor-assign-opr";
	private static String URL_SERVICE_DAILY_REPPORT="http://10.20.214.170:53073/simar-api/inquiry-pengajuan/vendor-daily-report";

	private static String URL_SERVICE_PERMANENT_OUT="http://localhost:8080/simar/permOut";
	private static String PATH_TEMP_DIR="C:\\\\Users\\\\U546591\\\\Rayhan\\\\Email\\\\";
	
	static Logger log = Logger.getLogger("rootLogger"); 
	static Logger logEmail =Logger.getLogger("com.ews.email");
	static Logger logExcel =Logger.getLogger("com.ews.excel");
	
	ReadExcel(){
		
	}
	
	public static void readAllFile()  {
	     List msgFileError= new ArrayList<Object>();
	     List msgFileNotProcess= new ArrayList<Object>();
	     List msgFileList= new ArrayList<Object>();
    	 File directoryPath = new File(PATH_TEMP_DIR);
         //List of all files and directories
         String[] contents = directoryPath.list();
         System.out.println("Processing Read File report");
         System.out.println("total file read :"+contents.length);
         for(int i=0; i<contents.length; i++) {
//        	System.out.println("----------------file ke "+ i+" :----------------------" );
//        	check dulu file belakangnya .xls atau bukan kalau bukan di skip
        	String fileName = contents[i];
        	logExcel.info(": " +i+" ["+fileName+"]");
        	String[] extensionFile=fileName.split(Pattern.quote("."));
        	String[] sender=fileName.split(Pattern.quote("#_"));
//        	System.out.println("sender :"+sender[1]);
        	if(extensionFile[extensionFile.length-1].equals("xlsx")) {
        		 try {
					FileInputStream file = new FileInputStream(new File(PATH_TEMP_DIR+contents[i]));
					XSSFWorkbook wb = new XSSFWorkbook(file);  
					if(extensionFile[extensionFile.length-2].toLowerCase().contains("konfirmasi pengajuan")) {
//			        	 processSheetCoba(workbook, "FirstSheet");	
						logExcel.info("file read : konfirmasi pengajuan");							
			        	 String typeProses="konfirmasi";
			        	 processSheet(wb,sender[1],typeProses);
			        	 msgFileList.add(fileName);
			         }else if(extensionFile[extensionFile.length-2].toLowerCase().contains("assign petugas")) {
			        	 logExcel.info("file read : assign petugas");
			        	 String typeProses="assign";
			        	 processSheet(wb,sender[1],typeProses);
			        	 msgFileList.add(fileName);
			         }else if(extensionFile[extensionFile.length-2].toLowerCase().contains("laporan harian")) {
			        	 logExcel.info("file read : laporan harian");
			        	 String typeProses="laporan";
			        	 processSheet(wb,sender[1],typeProses);
			        	 msgFileList.add(fileName);
			         }else if(extensionFile[extensionFile.length-2].toLowerCase().contains("permanent out")) {
						logExcel.info("file read : permanent out");
						String typeProses="permanent";
						processSheet(wb,sender[1],typeProses);
						msgFileList.add(fileName);
					}else {
			        	 System.out.println("not file read");
			        	 msgFileNotProcess.add(fileName);
			         }
        		 } catch (IOException  e) {
					// TODO Auto-generated catch block
        			 msgFileError.add(fileName +"-"+e.getMessage());
        			 logExcel.error(e.getMessage());
					e.printStackTrace();
				}
        	}else{
//        		System.out.println("no format");
        		logExcel.info("not allowed format");
        		msgFileNotProcess.add(fileName.length());
        	}
        	
//        	 try {
//				Files.deleteIfExists(Paths.get(PATH_TEMP_DIR+contents[i]));
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
                        
         }
         System.out.println("total process file :"+msgFileList.size());
         System.out.println("total not file procces: "+msgFileNotProcess.size());
         System.out.println("total Error file procces: "+msgFileError.size());
         if(msgFileError.size()>0) {
        	 System.out.println(msgFileError);
         }
         
    }
    
    static void processSheet(XSSFWorkbook workbook,String sender,String typeProses) {
    	XSSFSheet sheet = workbook.getSheetAt(0); 	    	
//    	System.out.println("sheet :"+sheet.getLastRowNum());
    	Iterator<Row> itr = sheet.iterator();
 
    	while (itr.hasNext())                 
    	{  
    		
	    	Row row = itr.next();  
	    	ArrayList<Object> listObj=new ArrayList<>();
	    	Iterator<Cell> cellIterator = row.cellIterator();   //iterating over each column  
//	    	kalau row tdk sama dengan header
	    	if(row.getRowNum()!=0) {
	    		while (cellIterator.hasNext())   
		    	{  
			    	Cell cell = cellIterator.next();  
			    	
			    	switch (cell.getCellType())               
			    	{  
				    	case STRING:    //field that represents string cell type  
				    		HashMap<Object, Object > reqBody=new HashMap<Object, Object>();
					    	reqBody.put("data", cell.getStringCellValue());
					    	listObj.add(reqBody);						    
					    	break;  
				    	case NUMERIC:    //field that represents number cell type  
//					    	System.out.println(cell.getNumericCellValue() ); 
//					    	data.add(cell.getNumericCellValue());
				    		HashMap<Object, Object > reqBodyNum=new HashMap<Object, Object>();
//					    	System.out.println("cell empty :"+cell.getNumericCellValue()); 
					    	String str = NumberToTextConverter.toText(cell.getNumericCellValue());
					    	reqBodyNum.put("data", str);					    	
					    	listObj.add(reqBodyNum);
					    	break;
				    	case BLANK:
				    		break;
				    	default:  
			    	} 

			    	
		    	} 
	    					    	
		    	if(listObj.size()>0) {
//		    		System.out.println("data :"+listObj);
//					System.out.println(typeProses);
		    		if(typeProses.contains("konfirmasi")) {
		    			logExcel.info("[post] request konfirmasi : "+listObj);
		    			JSONObject json=new JSONObject();
		    			String[] reqBody= {"no_box","pickup_date"};
		    			json.put("email_vendor",sender);
		    			for (int j2 = 0; j2 < listObj.size(); j2++) {
		    				HashMap<Object, Object> param=(HashMap<Object, Object>) (listObj.get(j2));
//		    				System.out.println(param.get("data"));
		    				json.put(reqBody[j2], param.get("data"));
		    				
						}
		    			
		    			logExcel.info("Body : "+json.toString());
		    			try {
							HttpResponse<JsonNode> response =Unirest.post(URL_SERVICE_VEN_CONFIRM)
							        .header("Content-Type", "application/json")
							        .body(json.toString()).asJson();
							logExcel.info("status : "+response.getStatus());
							logExcel.info(response.getBody());
						} catch (UnirestException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							logExcel.error(e.getMessage());
						}
//			    		System.out.println("hit api 1");
			    	}else if(typeProses.contains("assign")) {
			    		logExcel.info("[post] request assign opr : "+listObj);
			    		JSONObject json=new JSONObject();
		    			String[] reqBody= {"no_box","name_opr","nip_opr"};
		    			json.put("email_vendor",sender);
		    			for (int j2 = 0; j2 < listObj.size(); j2++) {
		    				HashMap<Object, Object> param=(HashMap<Object, Object>) (listObj.get(j2));
//		    				System.out.println(param.get("data"));
		    				json.put(reqBody[j2], param.get("data"));
		    				
						}
		    			
		    			logExcel.info("Body : "+json.toString());
		    			try {
							HttpResponse<JsonNode> response =Unirest.post(URL_SERVICE_ASSIGN_OPR)
							        .header("Content-Type", "application/json")
							        .body(json.toString()).asJson();
							logExcel.info(response.getStatus());
							logExcel.info(response.getBody());
							
						} catch (UnirestException e) {
							e.printStackTrace();
							logExcel.error(e.getMessage());
						}
			    	}else if(typeProses.contains("laporan")) {
			    		logExcel.info("[post] request Daily report : "+listObj);
			    		JSONObject json=new JSONObject();
		    			String[] reqBody= {"no_box","flow_status","realisasion_date","handover_no","archive_status"};
		    			json.put("email_vendor",sender);
		    			for (int j2 = 0; j2 < listObj.size(); j2++) {
		    				HashMap<Object, Object> param=(HashMap<Object, Object>) (listObj.get(j2));
		    				if(reqBody[j2].contains("archive_status")) {
		    					
		    					if(((String) param.get("data")).contains("1")) {
		    						json.put(reqBody[j2], "1");
		    					}else if(((String) param.get("data")).contains("2")) {
		    						json.put(reqBody[j2], "2");
		    					}else if(((String) param.get("data")).contains("3")) {
		    						json.put(reqBody[j2], "3");
		    					}
		    					
		    				}else {
//			    				System.out.println(param.get("data"));
			    				json.put(reqBody[j2], param.get("data"));
		    				}
		    				
						}
		    			logExcel.info("Body : "+json.toString());
		    			try {
							HttpResponse<JsonNode> response =Unirest.post(URL_SERVICE_DAILY_REPPORT)
							        .header("Content-Type", "application/json")
							        .body(json.toString()).asJson();
							logExcel.info(response.getStatus());
							logExcel.info(response.getBody());
						} catch (UnirestException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							logExcel.error(e.getMessage());
						}
			    	}
					else if(typeProses.contains("permanent")) {
						System.out.println(listObj);
						logExcel.info("[put] permanent out : "+listObj);
						JSONObject json=new JSONObject();
						String[] reqBody= {"boxNum"};
						HashMap<Object, Object> param=(HashMap<Object, Object>) (listObj.get(0));
						json.put(reqBody[0], param.get("data"));
						logExcel.info("Body : "+json.toString());
						System.out.println(json.toString());
						try {
							HttpResponse<JsonNode> response =Unirest.put(URL_SERVICE_PERMANENT_OUT)
									.header("Content-Type", "application/json")
									.body(json.toString()).asJson();
							logExcel.info(response.getStatus());
							logExcel.info(response.getBody());
						} catch (UnirestException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							logExcel.error(e.getMessage());
						}
					}
		    	}else {
		    		System.out.println("not hit api");
		    		logExcel.error("not hit api data not found");
		    	}
		    	
	    	}
    	 
    	}  
    	
    	
    }
}
