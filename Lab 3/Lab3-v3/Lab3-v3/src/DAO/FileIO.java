package DAO;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class FileIO {

	public static Map<Integer, String> InputPeopleInfo = new HashMap<Integer, String>();
	public static Map<String, String> InputZipCodeRangeInfo = new HashMap<String, String>();
	public static int PersonUniqueID=1;
	public static FileIO instance = new FileIO();
	public static String ZipCodeInfo_InputPath="";
	public static String PersonalAddress_InputPath="";

	private FileIO() {

	}

	public static FileIO getInstance() {
		return instance;
	}

	//read .Txt file
	private static void readTxt() throws IOException {
		String path=PersonalAddress_InputPath;
		try {
			BufferedReader br = new BufferedReader(new FileReader(path));

			String st;
			List<String> tempList = new ArrayList<String>();
			while ((st = br.readLine()) != null) {
				tempList.add(st.trim().toString());
			}
			for (int i = 0; i < tempList.size(); i++) {
				if (tempList.get(i).toString().contains("name:")) {
					String tempAddress=tempList.get(i).trim()+","+tempList.get(i+1).trim();		
					InputPeopleInfo.put(PersonUniqueID, tempAddress);
					PersonUniqueID++;				
				}
			}
			br.close();
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("Input file fail or can;t read, check FileIO class");
		}
	}

	//read Excel file
	private static void readExcelSheet() throws IOException {

		try  
		{  
			String path2=ZipCodeInfo_InputPath;
			File f1 = new File(path2);   //creating a new file instance  
			FileInputStream fis = new FileInputStream(f1);   //obtaining bytes from the file  
			XSSFWorkbook wb = new XSSFWorkbook(fis);   
			XSSFSheet sheet = wb.getSheetAt(0);     //creating a Sheet object to retrieve object  
			for(int rowIndex=1;rowIndex<= sheet.getLastRowNum();rowIndex++) {
				XSSFRow row =sheet.getRow(rowIndex);
				if(row!=null) {
					Cell cellStFullName=row.getCell(1);
					Cell cellSt=row.getCell(2);
					Cell cellMin = row.getCell(3);
					Cell cellMax = row.getCell(4);
					String temp=null;		
					try {
						temp=(int)cellMin.getNumericCellValue()+"_"+(int)cellMax.getNumericCellValue()+"_"+cellStFullName.getStringCellValue().toString();
					}catch(Exception e){
						temp=cellMin+"_"+cellMax+"_"+cellStFullName.getStringCellValue().toString();
					}
					InputZipCodeRangeInfo.put(cellSt.toString(),temp);
				}
			}
			wb.close();
		}  
		catch(Exception e)  
		{  
			e.printStackTrace(); 
			System.out.println("Input file fail or can;t read, check FileIO class");

		}  	
	}

	//return Map for official zip code range info
	public Map<String, String> getOfficialZipcodeRangeInfo() throws IOException {
		if(InputZipCodeRangeInfo.size()==0) {
			readExcelSheet();
		}
		return InputZipCodeRangeInfo;
	}

	//return Map for personal information
	public Map<Integer, String> getPeopleInfo() throws IOException {
		if(InputPeopleInfo.size()==0) {
			readTxt();
		}		
		return InputPeopleInfo;
	}
	
	public void updateZipCode(String name, String zipCode) throws IOException {
		
		//check input value is correct refinement
		if(zipCode.charAt(5)=='-'&&zipCode.length()==10) {
			for(int i=0;i<zipCode.length();i++) {
				if(i!=5) {
					if(!isInteger(Character.toString(zipCode.charAt(i)))) {
						System.out.println("Incorrect input format!");
						return;
					}
				}
			}
		}else {
			System.out.println("Incorrect input format!");
			return;
		}
		
		if(InputPeopleInfo.size()==0) {
			readTxt();
		}		
		Set set = InputPeopleInfo.entrySet();
		Iterator itr = set.iterator();
		String newAddress = "";
		while (itr.hasNext()) {
			Map.Entry entry = (Map.Entry) itr.next();
			if (entry.getValue().toString().contains(name)) {
				String oldAddress=entry.getValue().toString();
				String[] add = entry.getValue().toString().split(",");
				String OldzipCode = entry.getValue().toString().split(",")[add.length - 2].trim();
				if (OldzipCode.substring(0, 5).equals(zipCode.substring(0, 5))) {
					newAddress=oldAddress.replace(OldzipCode, zipCode);
					entry.setValue(newAddress.toString());
					System.out.println("Update Success! right click project folder and refrech to check new file");
				
				} else {
					System.out.println("Update fail! Name Found! user: " + entry.getKey().toString()
							+ ", but you can't modify first 5 number of Zip Code!");
				}
			}
		}

	}	
	
	public static boolean isInteger(String s) {
	      boolean isValidInteger = false;
	      try
	      {
	         Integer.parseInt(s);
	         // s is a valid integer
	         isValidInteger = true;
	      }
	      catch (NumberFormatException ex)
	      {
	         // s is not an integer
	      }
	      return isValidInteger;
	   }

	
	public void createUpdatedInputFile() throws IOException {

		File fout = new File("UpdatedInputFile.txt");
		FileOutputStream fos = new FileOutputStream(fout);
		OutputStreamWriter osw = new OutputStreamWriter(fos);
		Set<Entry<Integer,String>> set = InputPeopleInfo.entrySet();// Converting to Set so that we can traverse
		Iterator<Entry<Integer, String>> itr = set.iterator();
		while (itr.hasNext()) {
			Map.Entry entry = (Map.Entry) itr.next();
			String temp= entry.getValue().toString();
			String[]tempName=temp.split(",address:");		
			osw.write(tempName[0]+ "\n");
			osw.write("address:"+tempName[1]+ "\n");
			osw.write("\n");
		}
		osw.close();
	}

	public void addNewPerson(String info) throws IOException {	
		InputPeopleInfo.put(PersonUniqueID, info);
		PersonUniqueID++;

	}
	
}
