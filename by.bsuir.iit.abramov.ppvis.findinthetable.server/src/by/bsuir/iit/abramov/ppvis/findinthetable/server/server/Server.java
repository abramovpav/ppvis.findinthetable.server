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
import java.util.Vector;

import by.bsuir.iit.abramov.ppvis.findinthetable.model.Files;
import by.bsuir.iit.abramov.ppvis.findinthetable.model.Model;
import by.bsuir.iit.abramov.ppvis.findinthetable.model.Student;
import by.bsuir.iit.abramov.ppvis.findinthetable.utiilNetClasses.Mode;
import by.bsuir.iit.abramov.ppvis.findinthetable.utiilNetClasses.Package;

public class Server {
	private ServerSocketChannel	server;
	private final Model			model;

	public Server() {

		model = new Model();
	}

	public Server(final Model model) {

		this.model = model;
	}

	public void open() {

		System.out.println("Sender Start");
		try {
			server = ServerSocketChannel.open();
			server.configureBlocking(true);
			final int port = 12345;
			server.socket().bind(new InetSocketAddress(port));
		} catch (final IOException e) {
			System.out.println("class Server. Unable to open port");
		}
	}

	public void read() {

		SocketChannel client;
		try {
			client = server.accept();
		} catch (final IOException e) {
			System.out.println("class Server. read. can't accept");
			return;
		}

		ObjectOutputStream oos = null;
		ObjectInputStream ois = null;
		try {
			oos = new ObjectOutputStream(client.socket().getOutputStream());
			ois = new ObjectInputStream(client.socket().getInputStream());
		} catch (final IOException e) {
			System.out.println("class Server.read. can't get streams");
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
					if (obj.getClass() == Package.class) {
						final Package pack = (Package) obj;
						final Mode mode = pack.getMode();
						switch (mode) {

							case ADD_STUDENT:
								System.out.println("addStudent");
								objects = pack.getObjects();
								if (objects.size() != 0) {
									object = objects.get(0);
									if (object != null
											&& object.getClass() == Student.class) {
										final Student student = (Student) object;
										model.addStudent(student);
									}
								}
							break;
							case DELETE_STUDENTS:
								System.out.println("currPage");
								objects = pack.getObjects();
								students = new Vector<Student>();
								for (final Object studObject : objects) {
									final Student student = (Student) studObject;
									students.add(student);
								}
								model.deleteStudents(students);
							break;
							case GET_CURR_PAGE:
								System.out.println("currPage");
								students = model.getCurrPageOfStudent();
								objects = new Vector<Object>();
								objects.addAll(students);
								oos.writeObject(new Package(Mode.GET_CURR_PAGE, objects));
							break;
							case GET_NEXT_PAGE:
								System.out.println("nextPage");
								students = model.getNextPageOfStudents();
								objects = new Vector<Object>();
								objects.addAll(students);
								oos.writeObject(new Package(Mode.GET_NEXT_PAGE, objects));
							break;
							case GET_PREV_PAGE:
								System.out.println("prevPage");
								students = model.getPrevPageOfStudents();
								objects = new Vector<Object>();
								objects.addAll(students);
								oos.writeObject(new Package(Mode.GET_PREV_PAGE, objects));
							break;
							case GET_STUDENTS_COUNT:
								System.out.println("getStudentsCount");
								final Integer studentsCount = model.getStudentsCount();
								objects = new Vector<Object>();
								objects.add(studentsCount);
								oos.writeObject(new Package(Mode.GET_STUDENTS_COUNT,
										objects));
							break;
							case GET_VIEWSIZE:
								System.out.println("getViewSize");
								viewSize = model.getViewSize();
								objects = new Vector<Object>();
								objects.add(viewSize);
								oos.writeObject(new Package(Mode.GET_VIEWSIZE, objects));
							break;
							case GET_FILES_LIST:
								System.out.println("getFileList");
								objects = new Vector<Object>();
								objects.addAll(Files.getObjects());
								oos.writeObject(new Package(Mode.GET_FILES_LIST, objects));
							break;
							case LEAF_NEXT_PAGE:
								System.out.println("leafNext");
								model.leafNext();
							break;
							case LEAF_PREV_PAGE:
								System.out.println("leafPrev");
								model.leafPrev();
							break;
							case OPEN_FILE:
								System.out.println("openFile");
								object = pack.getObjects().get(0);
								if (object != null && object.getClass() == String.class) {
									String fileName = (String) object;
									System.out.println("File == " + fileName);
									fileName = Files.getAddress(fileName);
									if (fileName != null) {
										model.openXML(new File(fileName));
									}
								}
							break;
							case SAVE_FILE:
								System.out.println("saveFile");
								object = pack.getObjects().get(0);
								if (object != null && object.getClass() == String.class) {
									final String fileName = (String) object;
									final String path = "c:" + File.separator + fileName;
									System.out.println("File == " + path);
									model.saveXML(new File(path));
									Files.addFile(fileName, path);
								}
							break;
							case SEARCH1:
								name = "";
								botStr = "";
								topStr = "";
								object = pack.getObjects().get(0);
								if (object != null && object.getClass() == String.class) {
									name = (String) object;
								}
								object = pack.getObjects().get(1);
								if (object != null && object.getClass() == String.class) {
									botStr = (String) object;
								}
								object = pack.getObjects().get(2);
								if (object != null && object.getClass() == String.class) {
									topStr = (String) object;
								}
								students = model.search(name, botStr, topStr);
								objects = new ArrayList<Object>();
								objects.addAll(students);
								oos.writeObject(new Package(Mode.SEARCH1, objects));
							break;
							case SEARCH2:
								name = "";
								group = null;
								object = pack.getObjects().get(0);
								if (object != null && object.getClass() == String.class) {
									name = (String) object;
								}
								object = pack.getObjects().get(1);
								if (object != null && object.getClass() == Integer.class) {
									group = (Integer) object;
								}
								students = model.search(name, group);
								objects = new ArrayList<Object>();
								objects.addAll(students);
								oos.writeObject(new Package(Mode.SEARCH2, objects));
							break;
							case SEARCH3:
								name = "";
								botStr = "";
								topStr = "";
								String examStr = "";
								object = pack.getObjects().get(0);
								if (object != null && object.getClass() == String.class) {
									name = (String) object;
								}
								object = pack.getObjects().get(1);
								if (object != null && object.getClass() == String.class) {
									examStr = (String) object;
								}
								object = pack.getObjects().get(2);
								if (object != null && object.getClass() == String.class) {
									botStr = (String) object;
								}
								object = pack.getObjects().get(3);
								if (object != null && object.getClass() == String.class) {
									topStr = (String) object;
								}
								students = model.search(name, examStr, botStr, topStr);
								objects = new ArrayList<Object>();
								objects.addAll(students);
								oos.writeObject(new Package(Mode.SEARCH3, objects));
							break;
							case SET_VIEWSIZE:
								System.out.println("setViewSize");
								objects = pack.getObjects();
								if (objects.size() != 0) {
									object = objects.get(0);
									if (object != null
											&& object.getClass() == Integer.class) {
										viewSize = (Integer) object;
										model.setViewSize(viewSize);
									}
								}
							break;
							default:
							break;
						}
					}

				}
			} catch (final IOException e) {
				System.out.println("class Server. read. can't readObject");
				break;
			} catch (final ClassNotFoundException e) {
				e.printStackTrace();
				System.out.println("class Server. read. can't readObject");
				break;
			}
		}

		try {
			oos.close();
			ois.close();
		} catch (final IOException e) {
			System.out.println("class Server.read. can't close stream");
		}
	}
}
