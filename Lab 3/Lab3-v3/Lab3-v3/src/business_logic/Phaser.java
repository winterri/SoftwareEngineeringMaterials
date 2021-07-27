package business_logic;

import java.io.IOException;
import java.util.Map;

public interface Phaser extends Cloneable {

	Map<Integer, String> zipCodePhaser() throws IOException;
	int getSize();
	public void infoPrinter();
}
