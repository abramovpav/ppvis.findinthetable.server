package by.bsuir.iit.abramov.ppvis.findinthetable.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Files {
	private static Map<String, String>	files	= new HashMap<String, String>();

	static {
		Files.files.put("students.xml", "c:\\students.xml");
		Files.files.put("123.xml", "c:\\123.xml");
	}

	public static void addFile(final String key, final String value) {

		Files.files.put(key, value);
	}

	public static String getAddress(final String key) {

		if (Files.files.containsKey(key)) {
			return Files.files.get(key);
		} else {
			return null;
		}
	}

	public static Set<String> getObjects() {

		return Files.files.keySet();
	}

}
