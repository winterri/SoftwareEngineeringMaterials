package business_logic;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import DAO.FileIO;

public class District{

	//Region
	public static List<String> officialRegionList = Arrays.asList("Northeast","Midwest","South","West");

	//Northeast,Midwest,South,West Abbreviation
	public static List<String> NortheastAbbreviationList = Arrays.asList("CT","ME","MA","NH","RI","VT","NJ","NY","PA");
	public static List<String> MidwestAbbreviationList = Arrays.asList("ND","SD","NE","KS","MN","IA","MO","WI","IL","IN","MI","OH");
	public static List<String> SouthAbbreviationList = Arrays.asList("TX","OK","AR","LA","MS","AL","TN","KY","GA","FL","DE","SC","NC","VA","MD","DC","WV");
	public static List<String> WestAbbreviationList = Arrays.asList("WA","OR","CA","HI","AK","NV","ID","UT","AZ","MT","WY","CO","NM");


	//Division

	public static List<String> Division1_List = Arrays.asList("CT","ME","MA","NH","RI","VT");
	public static List<String> Division2_List = Arrays.asList("NJ","NY","PA");
	public static List<String> Division3_List = Arrays.asList("WI","IL","IN","MI","OH");
	public static List<String> Division4_List = Arrays.asList("ND","SD","NE","KS","MN","IA","MO");
	public static List<String> Division5_List = Arrays.asList("GA","FL","DE","SC","NC","VA","MD","DC","WV");
	public static List<String> Division6_List = Arrays.asList("MS","AL","TN","KY");
	public static List<String> Division7_List = Arrays.asList("TX","OK","AR","LA");
	public static List<String> Division8_List = Arrays.asList("NV","ID","UT","AZ","MT","WY","CO","NM");
	public static List<String> Division9_List = Arrays.asList("WA","OR","CA","HI","AK");
	public static Map<Integer, List<String>> Division = new HashMap<Integer, List<String>>();


	//Output Result Map(Northeast,Midwest,South,West)
	public static List<String> NortheastGroup = new ArrayList<String>();
	public static List<String> MidwestGroup = new ArrayList<String>();
	public static List<String> SouthGroup = new ArrayList<String>();
	public static List<String> WestGroup = new ArrayList<String>();
	public static List<String> AllDistrictResultList = new ArrayList<String>();

	public static String DistrictSummary="";
	public District() throws IOException{

	}

	@SuppressWarnings("rawtypes")
	public void DistrictPhaser(String district) throws CloneNotSupportedException, IOException {

		//Add all division information into Map
		Division.put(1, Division1_List);
		Division.put(2, Division2_List);
		Division.put(3, Division3_List);
		Division.put(4, Division4_List);
		Division.put(5, Division5_List);
		Division.put(6, Division6_List);
		Division.put(7, Division7_List);
		Division.put(8, Division8_List);
		Division.put(9, Division9_List);

		ZipCode DZ=new ZipCode();
		Phaser validList=DZ.GetPhaser("valid");
		ValidZipCode valList_copy=((ValidZipCode) validList).clone();
		Map<Integer, String> tempMap= valList_copy.zipCodePhaser();
		Set set = tempMap.entrySet();// Converting to Set so that we can traverse
		Iterator itr = set.iterator();
		try {
			while (itr.hasNext()) {
				// Converting to Map.Entry so that we can get key and value separately
				Map.Entry entry = (Map.Entry) itr.next();
				String name= entry.getValue().toString().split(",")[0].trim();
				String[] address = entry.getValue().toString().split(",");
				String districtInfo = entry.getValue().toString().split(",")[address.length - 1].trim();
				//System.out.println(districtInfo);
				String stCode = entry.getValue().toString().split(",")[address.length - 3].trim();
				
				switch(district) {
				case "Northeast":
					if(NortheastAbbreviationList.contains(stCode)) {
						NortheastGroup.add(entry.getValue().toString() + "Region: Northeast");
					}
					break;
				case "Midwest":
					if(MidwestAbbreviationList.contains(stCode)) {
						MidwestGroup.add(entry.getValue().toString() + "Region: Midwest");
					}
					break;
				case "South":
					if(SouthAbbreviationList.contains(stCode)) {
						SouthGroup.add(entry.getValue().toString() + "Region: South");
					}
					break;
				case "West":
					if(WestAbbreviationList.contains(stCode)) {
						WestGroup.add(entry.getValue().toString() + "Region: West");
					}
					break;
				case "District":
					String temp="";
					//condition: district information type is 1
					if(districtInfo.equals("1")) {
						if(NortheastAbbreviationList.contains(stCode)) {
							temp+= "Northeast ";
						}else if(MidwestAbbreviationList.contains(stCode)) {
							temp+= "Midwest ";
						}else if(SouthAbbreviationList.contains(stCode)) {
							temp+= "South ";
						}else if(WestAbbreviationList.contains(stCode)) {
							temp+= "West ";
						}
						AllDistrictResultList.add(name + "; District information: " + temp);
						if(temp=="") {
							System.out.println(temp+"---"+name);
						}
					}

					//condition: district information is 2
					if(districtInfo.equals("2")) {
						if(NortheastAbbreviationList.contains(stCode)) {
							temp+= "Northeast ";
						}else if(MidwestAbbreviationList.contains(stCode)) {
							temp+= "Midwest ";
						}else if(SouthAbbreviationList.contains(stCode)) {
							temp+= "South ";
						}else if(WestAbbreviationList.contains(stCode)) {
							temp+= "West ";
						}

						for(int i=1;i<10;i++) {
							for(String a: Division.get(i)) {
								if(stCode.equals(a)) {
									temp+="--> Division "+i;
								}
							}

						}
						AllDistrictResultList.add(name + "; District information: " + temp);
					}


					//condition: district information is 3
					if(districtInfo.equals("3")) {
						if(NortheastAbbreviationList.contains(stCode)) {
							temp+= "Northeast ";
						}else if(MidwestAbbreviationList.contains(stCode)) {
							temp+= "Midwest ";
						}else if(SouthAbbreviationList.contains(stCode)) {
							temp+= "South ";
						}else if(WestAbbreviationList.contains(stCode)) {
							temp+= "West ";
						}

						for(int i=1;i<10;i++) {
							for(String a: Division.get(i)) {
								if(stCode.equals(a)) {
									temp+="--> Division "+i;
								}
							}
						}
						temp+= "--> "+getFullStateName(stCode);
						AllDistrictResultList.add(name + "; District information: " + temp);
					}
						if(temp=="") {
					System.out.println("#######Invalid District information or format######, Check error input: " + name);
				}
						
						
				}
			
			}
		}catch(Exception e) {
			e.printStackTrace();
			Map.Entry entry = (Map.Entry) itr.next();
			System.out.println("Invalid information: "+ entry.getValue().toString());

		}
		DistrictSummary="Total size of valid input = "+tempMap.size()+"; \r\n"+ "Effective output = " +AllDistrictResultList.size()+"\r\n"; 

	}


	//when input is State Abbreviation, return State full name
	public String getFullStateName(String stCode) throws IOException {
		FileIO f = FileIO.getInstance();
		String stFullName=f.getOfficialZipcodeRangeInfo().get(stCode).toString().split("_")[2];
		return stFullName;
	}

	public List<String> region_NortheastPhaser() throws CloneNotSupportedException, IOException {
		DistrictPhaser("Northeast");
		return NortheastGroup;		
	}
	public int getNortheastGroupSize() {
		return NortheastGroup.size();
	}

	public List<String> region_MidwestPhaser() throws CloneNotSupportedException, IOException {
		DistrictPhaser("Midwest");
		return MidwestGroup;	
	}
	public int getMidwestGroupSize() {
		return MidwestGroup.size();
	}

	public List<String> region_SouthPhaser() throws CloneNotSupportedException, IOException {
		DistrictPhaser("South");
		return SouthGroup;
	}
	public int getSouthGroupSize() {
		return SouthGroup.size();
	}

	public List<String> region_WestPhaser() throws CloneNotSupportedException, IOException {
		DistrictPhaser("West");
		return WestGroup;
	}
	public int getWestGroupSize() {
		return WestGroup.size();
	}
	public void infoPrinter(List<String> l) {
		for(String a:l) {
			System.out.println(a);
		}
	}
	public List<String> district_Phaser() throws CloneNotSupportedException, IOException {
		DistrictPhaser("District");
		return AllDistrictResultList;
	}
	public int getDistrictGroupSize() {
		return AllDistrictResultList.size();
	}
	
	public void districtInfoReport(List<String> l) {
		for(String a:l) {
			System.out.println(a);
		}
	}
}
