package by.bsuir.iit.abramov.ppvis.findinthetable.server.view;

import java.awt.BorderLayout;
import java.io.File;
import java.util.List;
import java.util.Vector;
import java.util.logging.Logger;

import javax.swing.JPanel;

import by.bsuir.iit.abramov.ppvis.findinthetable.model.Model;
import by.bsuir.iit.abramov.ppvis.findinthetable.model.Student;
import by.bsuir.iit.abramov.ppvis.findinthetable.server.server.Server;

public class Desktop extends JPanel {
	public static final String	MAX					= "max";

	private static final int	EXAM_COLUMNS_COUNT	= 2;
	private static final int	INFO_COLUMNS_COUNT	= 2;
	public static final int		EXAMS_COUNT			= 5;
	public static final int		COLUMNS_COUNT		= Desktop.INFO_COLUMNS_COUNT
															+ Desktop.EXAMS_COUNT
															* Desktop.EXAM_COLUMNS_COUNT;
	private static Logger		LOG					= Logger.getLogger(Desktop.class
															.getName());
	public static final String	DECREMENT			= "-";
	public static final String	INCREMENT			= "+";
	public static final String	BUTTON_NEXT			= "next";
	public static final String	BUTTON_PREV			= "prev";
	private final ContentPane	contentPane;
	private Model				model;

	public Desktop(final ContentPane contentPane) {

		this.contentPane = contentPane;
		initialize();
	}

	public void addStudent(final Student student) {

		if (student != null) {
			model.addStudent(student);
			final List<Student> pageOfStudents = model.getCurrPageOfStudent();
		}
	}

	public void deleteStudents(final List<Student> students) {

		model.deleteStudents(students);
	}

	public void initialize() {

		setLayout(new BorderLayout(0, 0));

		model = new Model();
		openXML(new File("c:\\students.xml"));
		final Server server = new Server(model);
		server.open();
		server.read();

	}

	public void openXML(final File file) {

		model.openXML(file);
	}

	public void saveXML(final File file) {

		model.saveXML(file);
	}

	/*
	 * public List<Student> search(final List<CoupleExt<String, JTextField>>
	 * list, final int num) {
	 * 
	 * List<Student> studentsVector = new Vector<Student>(); String name,
	 * groupStr, topStr, botStr, examStr; switch (num) { case 1: topStr = "";
	 * botStr = ""; name = ""; for (int i = 0; i < list.size(); ++i) { final
	 * CoupleExt<String, JTextField> item = list.get(i); if (item.getField1() ==
	 * Window.geti18nString(FindDialog.NAME)) { name =
	 * item.getField2().getText(); } else if (item.getField1() ==
	 * Window.geti18nString(FindDialog.FROM)) { botStr =
	 * item.getField2().getText(); } else if (item.getField1() ==
	 * Window.geti18nString(FindDialog.TO)) { topStr =
	 * item.getField2().getText(); } } studentsVector = search(name, botStr,
	 * topStr);
	 * 
	 * break; case 2: name = ""; groupStr = ""; for (int i = 0; i < list.size();
	 * ++i) { final CoupleExt<String, JTextField> item = list.get(i); if
	 * (item.getField1() == Window.geti18nString(FindDialog.NAME)) { name =
	 * item.getField2().getText(); } else if (item.getField1() ==
	 * Window.geti18nString(FindDialog.GROUP)) { groupStr =
	 * item.getField2().getText(); } } final Integer group =
	 * Util.isNumeric(groupStr) ? Integer .parseInt(groupStr) : null;
	 * studentsVector = search(name, group); break; case 3: name = ""; examStr =
	 * ""; topStr = ""; botStr = ""; for (int i = 0; i < list.size(); ++i) {
	 * final CoupleExt<String, JTextField> item = list.get(i); if
	 * (item.getField1() == Window.geti18nString(FindDialog.NAME)) { name =
	 * item.getField2().getText(); } else if (item.getField1() ==
	 * Window.geti18nString(FindDialog.EXAM)) { examStr =
	 * item.getField2().getText(); } else if (item.getField1() ==
	 * Window.geti18nString(FindDialog.FROM)) { botStr =
	 * item.getField2().getText(); } else if (item.getField1() ==
	 * Window.geti18nString(FindDialog.TO)) { topStr =
	 * item.getField2().getText(); } }
	 * 
	 * studentsVector = search(name, examStr, botStr, topStr); break; } return
	 * studentsVector; }
	 */

	public Vector<Student> search(final String name, final Integer group) {

		return model.search(name, group);
	}

	public Vector<Student> search(final String name, final String botStr,
			final String topStr) {

		return model.search(name, botStr, topStr);
	}

	public Vector<Student> search(final String name, final String examStr,
			final String botStr, final String topStr) {

		return model.search(name, examStr, botStr, topStr);
	}
}
