package by.bsuir.iit.abramov.ppvis.findinthetable.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Files {
	private static Map<String, String>	files	= new HashMap<String, String>();

	static {
		Files.files.put("students.xml", "c:\\students.xml");
		Files.files.put("123.xml", "c:\\123.xml");
		Files.files.put("text.xml", "c:\\text.xml");
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

	public static List<Object> getObjectKeys() {

		final List<Object> strings = new ArrayList<Object>();
		strings.addAll(Files.files.keySet());
		return strings;
	}

	public static Set<String> getObjects() {

		return Files.files.keySet();
	}

	public static int size() {

		return Files.files.size();
	}

}
