package display;

import java.io.IOException;

import DAO.FileIO;
import business_logic.ZipCode;
import business_logic.District;
import business_logic.Selection;
import business_logic.ValidZipCode;
import business_logic.Phaser;

public class ResultDisplay {

	public static void main(String[] args) throws IOException, CloneNotSupportedException {

		//input path
		FileIO.ZipCodeInfo_InputPath="zipCode_info.xlsx";
		FileIO.PersonalAddress_InputPath="input_sample3.txt";

		//Phaser for valid and invalid group
		ZipCode DZ=new ZipCode();
		Phaser valid=DZ.GetPhaser("valid");
		valid.getSize();
		//valid.InfoPrinter();   //print detail for valid group
		Phaser invalid=DZ.GetPhaser("invalid");	
		invalid.getSize();
		//invalid.InfoPrinter();   //print detail for invalid group

		//Phaser for Selection		
		System.out.println("-----Selection-----");
		Selection selection =new Selection();
		selection.selectionBluePhaser();
		System.out.println("Blue selection group= "+selection.getSelectionBlueGroupSize());
		selection.selectionRedPhaser();
		System.out.println("Red selection group= "+ selection.getSelectionRedGroupSize());
		System.out.println("Total valid people at 2 Selection Group: "+(selection.getSelectionBlueGroupSize()+selection.getSelectionRedGroupSize()));
		System.out.println("-----end-----");


		//Phaser for Region
		System.out.println("-----Region-----");
		District district=new District();
		district.region_NortheastPhaser();
		//district.InfoPrinter(district.Region_NortheastPhaser());
		System.out.println("Northeast group= "+district.getNortheastGroupSize());	
		district.region_MidwestPhaser();
		System.out.println("Midwest group= "+ district.getMidwestGroupSize());
		district.region_SouthPhaser();
		System.out.println("South group= "+ district.getSouthGroupSize());
		district.region_WestPhaser();
		System.out.println("West group= " + district.getWestGroupSize());
		System.out.println("Total valid people at 4 region: "+ (district.getNortheastGroupSize()+district.getMidwestGroupSize()+district.getSouthGroupSize()+district.getWestGroupSize()));
		System.out.println("-----end-----");


		//Phaser for District information	
		//District Level 1: region only
		//District Level 2: region + division
		//District Level 3: region + division + state
		System.out.println("-----Name and District information-----");
		district.DistrictPhaser("District");
		for(String i:District.AllDistrictResultList) {
			System.out.println(i);
		}
		System.out.println("-----District Summary-----");
		System.out.println(District.DistrictSummary);
		System.out.println("-----end-----");
		
		
		System.out.println("-----UpdatedZipCode-----");
		FileIO f = FileIO.getInstance();	
		f.updateZipCode("Louise Sanders", "73018-6555");
		f.createUpdatedInputFile();
		System.out.println("-----end-----");
		
		System.out.println("-----add new person-----");
		f.addNewPerson("name: new person ,address: new address, new city, OK, 71214, 3");	
		System.out.println("-----end-----");

	}

}
