package business_logic;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import DAO.FileIO;

public class ValidZipCode implements Phaser,Cloneable {

	public static Map<Integer, String> ValidZipCodeMap= new HashMap<Integer, String>();

	public ValidZipCode() throws IOException {
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

				// Converting to Map.Entry so that we can get key and value separately
				Map.Entry entry = (Map.Entry) itr.next();
				String[] address = entry.getValue().toString().split(",");
				String zipCode = entry.getValue().toString().split(",")[address.length - 2].trim();
				String stCode = entry.getValue().toString().split(",")[address.length - 3].trim();
				int tempMin=Integer.parseInt(mapListMatchList.get(stCode).toString().split("_")[0]);
				int tempMax=Integer.parseInt(mapListMatchList.get(stCode).toString().split("_")[1]);
				int currentCode=Integer.parseInt(zipCode);

				if(mapListMatchList.get(stCode)!=null) {
					if(currentCode>=tempMin && currentCode<=tempMax) {
						ValidZipCodeMap.put((Integer) entry.getKey(),entry.getValue().toString());
					}
				}
			}
		}catch(Exception e) {
			System.out.println("ERROR INPUT, input file not match correct form");
		}

		return ValidZipCodeMap;
	}


	@Override
	public ValidZipCode clone(){
		ValidZipCode validZipCode=null;
		try {
			validZipCode=(ValidZipCode)super.clone();
			validZipCode.ValidZipCodeMap=(Map<Integer, String>)this.ValidZipCodeMap;
		}catch(CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return validZipCode;
	}


	//return map size
	@Override
	public int getSize() {
		System.out.println("-----------Valid Zip Code Match size: "+ ValidZipCodeMap.size());
		return ValidZipCodeMap.size();

	}

	//print map information
	public void infoPrinter() {
		for(String value:ValidZipCodeMap.values()) {
			System.out.println(value);
		}
	}
}
