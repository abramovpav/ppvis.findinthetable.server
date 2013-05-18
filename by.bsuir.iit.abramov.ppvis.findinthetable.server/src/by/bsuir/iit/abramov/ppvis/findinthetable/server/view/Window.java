package by.bsuir.iit.abramov.ppvis.findinthetable.server.view;

import java.io.File;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JFrame;

import by.bsuir.iit.abramov.ppvis.findinthetable.server.server.Server;

public class Window extends JFrame {
	public static final int			defaultX		= 200;
	public static final int			defaultY		= 100;
	public static final int			defaultWidth	= 995;
	public static final int			defaultHeight	= 600;
	public static final String		TITLE			= "title";


	private ContentPane	contentPane;
	public Window() {

		super(Window.TITLE);
		initialize();
		
	}

	public Window(final String title) {

		super(title);
		initialize();
	}

	public void exit() {

		setVisible(false);
		dispose();
	}

	public final ContentPane getContPane() {

		return contentPane;
	}

	private void initialize() {

		contentPane = new ContentPane(this);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(contentPane);
		setBounds(Window.defaultX, Window.defaultY, Window.defaultWidth,
				Window.defaultHeight);
	}
}
