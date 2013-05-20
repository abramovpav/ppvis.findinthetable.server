package by.bsuir.iit.abramov.ppvis.findinthetable.server.server;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import by.bsuir.iit.abramov.ppvis.findinthetable.model.Files;
import by.bsuir.iit.abramov.ppvis.findinthetable.model.Model;
import by.bsuir.iit.abramov.ppvis.findinthetable.model.Student;
import by.bsuir.iit.abramov.ppvis.findinthetable.utiilNetClasses.Mode;
import by.bsuir.iit.abramov.ppvis.findinthetable.utiilNetClasses.Package;

public class Server {
	public static final String	DRIVE_C	= "d:" + File.separator + "WORK";
	private ServerSocketChannel	server;
	private final Model			model;
	private static Logger		LOG		= Logger.getLogger(Server.class.getName());

	public Server() {

		model = new Model();
	}

	public Server(final Model model) {

		this.model = model;
	}

	private final Object getFirstElementIfClassEqualent(final Class inputClass,
			final List<Object> objects) {

		if (objects.size() != 0) {
			final Object object = objects.get(0);
			if (isObjectClassEqualent(object, inputClass)) {
				return object;
			}
		}
		return null;
	}

	private String getString(final List<Object> objects, final int num, String name) {

		if (num >= 0 && num < objects.size()) {
			final Object object = objects.get(num);
			if (isObjectClassEqualent(objects, String.class)) {
				name = (String) object;
			}
		}
		return name;
	}

	private boolean isEqualentClasses(final Class class1, final Class class2) {

		return class1 == class2;
	}

	private boolean isObjectClassEqualent(final Object object, final Class inputClass) {

		return object != null && isEqualentClasses(object.getClass(), inputClass);
	}

	public void open() {

		try {
			server = ServerSocketChannel.open();
			print("Server: start");
			server.configureBlocking(true);
			final int port = 12345;
			server.socket().bind(new InetSocketAddress(port));
			print("Server: port " + port);
		} catch (final IOException e) {
			print("Server: Unable to open port");

		}
	}

	private void print(final String text) {

		Server.LOG.info(text);
		System.out.println(text);
	}

	public void read() {

		SocketChannel client;
		try {
			print("Server: waiting for connection...");
			client = server.accept();
		} catch (final IOException e) {
			print("Server: can't accept");

			return;
		}

		ObjectOutputStream oos = null;
		ObjectInputStream ois = null;
		try {
			oos = new ObjectOutputStream(client.socket().getOutputStream());
			ois = new ObjectInputStream(client.socket().getInputStream());
		} catch (final IOException e) {
			print("Server: can't get streams");
			return;
		}

		Object obj, object;
		List<Object> objects;
		List<Student> students;
		Integer viewSize;
		String name, topStr, botStr;
		Integer group;

		while (true) {
			try {
				obj = ois.readObject();
				if (obj != null) {
					if (isEqualentClasses(obj.getClass(), Package.class)) {
						final Package pack = (Package) obj;
						final Mode mode = pack.getMode();
						switch (mode) {
							case ADD_STUDENT:
								print("Client: addStudent");
								objects = pack.getObjects();
								if ((object = getFirstElementIfClassEqualent(
										Student.class, objects)) != null) {
									final Student student = (Student) object;
									model.addStudent(student);
									print("Server: add  student " + student.getName());
								}
							break;
							case DELETE_STUDENTS:
								print("Client: deleteStudents");
								objects = pack.getObjects();
								students = new ArrayList<Student>();
								for (final Object studObject : objects) {
									if (isEqualentClasses(studObject.getClass(),
											Student.class)) {
										final Student student = (Student) studObject;
										students.add(student);
									}
								}
								model.deleteStudents(students);
								print("Server: delete " + students.size() + " students");
							break;
							case GET_CURR_PAGE:
								print("Client: getCurrPage");
								students = model.getCurrPageOfStudent();
								sendPackage(oos, Mode.GET_CURR_PAGE, students);
								print("Server: send " + students.size() + " students");
							break;
							case GET_NEXT_PAGE:
								print("Client: getNextPage");
								students = model.getNextPageOfStudents();
								sendPackage(oos, Mode.GET_NEXT_PAGE, students);
								print("Server: send " + students.size() + " students");

							break;
							case GET_PREV_PAGE:
								print("Client: getPrevPage");
								students = model.getPrevPageOfStudents();
								sendPackage(oos, Mode.GET_PREV_PAGE, students);
								print("Server: send " + students.size() + " students");

							break;
							case GET_STUDENTS_COUNT:
								print("Client: getStudentsCount");
								final Integer studentsCount = model.getStudentsCount();
								sendPackage(oos, Mode.GET_STUDENTS_COUNT, studentsCount);
								print("Server: studentsCount = " + studentsCount);
							break;
							case GET_VIEWSIZE:
								print("Client: getViewSize");
								viewSize = model.getViewSize();
								sendPackage(oos, Mode.GET_VIEWSIZE, viewSize);
								print("Server: viewSize = " + viewSize);
							break;
							case GET_FILES_LIST:
								print("Client: getFileList");
								sendPackage(oos, Mode.GET_FILES_LIST,
										Files.getObjectKeys());
								print("Server: send " + Files.size() + " files");
							break;
							case LEAF_NEXT_PAGE:
								print("Client: leafNext");
								model.leafNext();
								print("Server: leafNext");
							break;
							case LEAF_PREV_PAGE:
								print("Client: leafPrev");
								model.leafPrev();
								print("Server: leafPrev");
							break;
							case OPEN_FILE:
								print("Client: openFile");
								objects = pack.getObjects();
								if ((object = getFirstElementIfClassEqualent(
										String.class, objects)) != null) {
									String fileName = (String) object;
									print("Server: open path " + fileName);
									fileName = Files.getAddress(fileName);
									if (fileName != null) {
										model.openXML(new File(fileName));
									}
								}
							break;
							case SAVE_FILE:
								print("Client: saveFile");
								objects = pack.getObjects();
								if ((object = getFirstElementIfClassEqualent(
										String.class, objects)) != null) {
									final String fileName = (String) object;
									final String path = Server.DRIVE_C + File.separator
											+ fileName;
									model.saveXML(new File(path));
									print("Server: save to " + path);
									Files.addFile(fileName, path);
								}
							break;
							case SEARCH1:
								print("Client: search1");
								name = botStr = topStr = "";
								objects = pack.getObjects();
								if (objects.size() != 0) {
									name = getString(objects, 0, name);
									botStr = getString(objects, 1, botStr);
									topStr = getString(objects, 2, topStr);
									students = model.search(name, botStr, topStr);
									objects = new ArrayList<Object>();
									objects.addAll(students);
								} else {
									objects = new ArrayList<Object>();
								}
								oos.writeObject(new Package(Mode.SEARCH1, objects));

							break;
							case SEARCH2:
								print("Client: search2");
								name = "";
								group = null;
								objects = pack.getObjects();
								if (objects.size() != 0) {
									name = getString(objects, 0, name);
									object = pack.getObjects().get(1);
									if (isObjectClassEqualent(object, Integer.class)) {
										group = (Integer) object;
									}
									students = model.search(name, group);
									objects = new ArrayList<Object>();
									objects.addAll(students);
								} else {
									objects = new ArrayList<Object>();
								}
								oos.writeObject(new Package(Mode.SEARCH2, objects));
							break;
							case SEARCH3:
								print("Client: search3");
								name = botStr = topStr = "";
								String examStr = "";
								objects = pack.getObjects();
								if (objects.size() != 0) {
									name = getString(objects, 0, name);
									examStr = getString(objects, 1, examStr);
									botStr = getString(objects, 2, botStr);
									topStr = getString(objects, 3, topStr);
									students = model
											.search(name, examStr, botStr, topStr);
									objects = new ArrayList<Object>();
									objects.addAll(students);
								} else {
									objects = new ArrayList<Object>();
								}
								oos.writeObject(new Package(Mode.SEARCH3, objects));
							break;
							case SET_VIEWSIZE:
								print("Client: setViewSize");
								objects = pack.getObjects();
								if ((object = getFirstElementIfClassEqualent(
										Integer.class, objects)) != null) {
									viewSize = (Integer) object;
									model.setViewSize(viewSize);
									print("Server: new viewSize = " + viewSize);
								}
							break;
							default:
								print("Client: unknown command");
							break;
						}
					}

				}
			} catch (final IOException e) {
				print("Server: Connection lost");
				break;
			} catch (final ClassNotFoundException e) {
				print("ClassnotFoundException*****");
				e.printStackTrace();
				print("***************************");
				break;
			}
		}

		try {
			oos.close();
			ois.close();
		} catch (final IOException e) {
			print("Server: can't close streams");
		}
		read();
	}

	private void sendPackage(final ObjectOutputStream oos, final Mode inputMode,
			final Integer num) throws IOException {

		List<Object> objects;
		objects = new ArrayList<Object>();
		objects.add(num);
		oos.writeObject(new Package(inputMode, objects));
	}

	private void sendPackage(final ObjectOutputStream oos, final Mode inputMode,
			final List inputObjects) throws IOException {

		final List<Object> objects = new ArrayList<Object>();
		objects.addAll(inputObjects);
		oos.writeObject(new Package(inputMode, objects));
	}
}
