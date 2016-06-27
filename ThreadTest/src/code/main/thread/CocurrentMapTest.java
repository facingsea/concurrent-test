package code.main.thread;

import java.util.Collections;
import java.util.HashMap;

public class CocurrentMapTest {
	
	public static void main(String[] args) {
		Collections.synchronizedMap(new HashMap<String, String>());
	}

}
