package by.bsuir.iit.abramov.ppvis.findinthetable.server.view;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class ContentPane extends JPanel {
	private static final String	ABOUT_AUTHOR	= "about_author";
	private static final String	ABOUT_TITLE		= "about_title";
	private Desktop				desktop;
	private final JFrame		parent;

	public ContentPane(final JFrame parent) {

		this.parent = parent;
		initialize();
	}

	public void about() {

		JOptionPane.showMessageDialog(null, ContentPane.ABOUT_AUTHOR,
				ContentPane.ABOUT_TITLE, JOptionPane.INFORMATION_MESSAGE);
	}

	public void exit() {

		((Window) parent).exit();
	}

	private void initialize() {

		setLayout(new BorderLayout(0, 0));
		desktop = new Desktop(this);
		desktop.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		add(desktop, BorderLayout.CENTER);
	}
}
