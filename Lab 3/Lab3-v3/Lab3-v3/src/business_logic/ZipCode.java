package business_logic;

import java.io.IOException;

public class ZipCode {

	public Phaser GetPhaser(String type) throws IOException {
		if(type==null) {
			return null;
		}
		if(type.equalsIgnoreCase("valid")) {
			return new ValidZipCode();
		}else if(type.equalsIgnoreCase("invalid")) {
			return new InValidZipCode();
		}
		return null;
	}
}

