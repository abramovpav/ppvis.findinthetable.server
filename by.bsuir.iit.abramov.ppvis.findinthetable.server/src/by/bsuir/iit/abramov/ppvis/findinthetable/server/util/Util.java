package by.bsuir.iit.abramov.ppvis.findinthetable.server.util;

import java.text.NumberFormat;
import java.text.ParsePosition;

public class Util {

	public static boolean isNumeric(final String str) {

		if (str.length() == 0) {
			return false;
		}
		final NumberFormat formatter = NumberFormat.getInstance();
		final ParsePosition pos = new ParsePosition(0);
		formatter.parse(str, pos);
		return str.length() == pos.getIndex();
	}
}
