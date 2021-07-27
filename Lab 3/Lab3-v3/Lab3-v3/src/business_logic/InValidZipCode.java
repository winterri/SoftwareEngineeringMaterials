package business_logic;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import DAO.FileIO;

public class InValidZipCode implements Phaser{
	
	
	public static int unknowID=1;
	
	
	
	
	
	
	
	public static Map<Integer, String> InValidZipCodeMap= new HashMap<Integer, String>();
	public InValidZipCode() throws IOException{
		this.zipCodePhaser();
	}


	@SuppressWarnings("rawtypes")
	@Override
	public Map<Integer, String> zipCodePhaser() throws IOException {
		FileIO f = FileIO.getInstance();
		Map<Integer, String> mapListAddress=f.getPeopleInfo();
		Map<String, String> mapListMatchList=f.getOfficialZipcodeRangeInfo();
		Set set = mapListAddress.entrySet();// Converting to Set so that we can traverse
		Iterator itr = set.iterator();
		try {
			while (itr.hasNext()) {
				Map.Entry entry = (Map.Entry) itr.next();
				String[] address = entry.getValue().toString().split(",");
				String zipCode = entry.getValue().toString().split(",")[address.length - 2].trim();
				String stCode = entry.getValue().toString().split(",")[address.length - 3].trim();
				int tempMin=Integer.parseInt(mapListMatchList.get(stCode).toString().split("_")[0]);
				int tempMax=Integer.parseInt(mapListMatchList.get(stCode).toString().split("_")[1]);
				int currentCode=Integer.parseInt(zipCode);
				if(mapListMatchList.get(stCode)!=null) {		
					if(!(currentCode>=tempMin && currentCode<=tempMax)) {
						InValidZipCodeMap.put(unknowID,entry.getValue().toString());
						unknowID++;
					}
				}
			}
		}catch(Exception e) {
			System.out.println("ERROR INPUT, input file not match correct form");
			InValidZipCodeMap.put(unknowID,"unknow");
			unknowID++;
		}
		return InValidZipCodeMap;
	}

	@Override
	public int getSize() {
		System.out.println("-----------Invalid Zip Code Match size: "+ InValidZipCodeMap.size());
		return InValidZipCodeMap.size();
	}
	public void infoPrinter() {
		for(String value:InValidZipCodeMap.values()) {
			System.out.println(value);
		}
	}


}


