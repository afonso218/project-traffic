package pt.iul.ista.poo.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * @author POO 2016
 * 
 *         UserInterfaceWindow is an interface window that allows the user to
 *         display shapes and images (provided they respect the ImageComponent
 *         and ShapeComponent interfaces). Displayed images must be on the
 *         "images" folder in the project and will addressed by their name
 *         without extension (so images with the same name and different
 *         extensions are not allowed). Example, if you want to use the image
 *         saved in "images/RedCar.jpg" you should create an ImageComponent
 *         implementation that returns the name: "RedCar"
 * 
 *         A text message can be displayed on the top window if necessary using
 *         setMessage().
 * 
 *         The UserInterfaceWindow is Observable and the Exit button in the
 *         bottom row triggers an update on the registered observers.
 * 
 *         UserInterfaceWindow implements the Singleton design pattern.
 *
 */

public class UserInterfaceWindow extends Observable {

	private static final UserInterfaceWindow INSTANCE = new UserInterfaceWindow();

	private static final int INFO_PANEL_HEIGHT = 20;
	private static final int BUTTON_PANEL_HEIGHT = 20;

	public static final int MAX_LAYER = 10;

	public final String IMAGE_DIR = "images";

	public final int WIDTH;
	public final int HEIGHT;

	private JFrame frame;
	private JPanel panel;
	private JLabel info;
	private JPanel buttons;

	private Map<String, ImageIcon> imageDB = new HashMap<String, ImageIcon>();

	private List<ImageComponent> images = new ArrayList<ImageComponent>();
	private List<ShapeComponent> shapes = new ArrayList<ShapeComponent>();

	private boolean buttonPressed;
	private String buttonName;

	private UserInterfaceWindow() {
		WIDTH = 690;
		HEIGHT = 460;
		init();
	}

	/**
	 * @return Access to the Singleton instance of ImageMatrixGUI
	 */
	public static UserInterfaceWindow getInstance() {
		return INSTANCE;
	}

	/**
	 * 
	 * Setter for the name of the frame
	 * 
	 * @param name
	 *            Name of application (will be displayed as a frame title in the
	 *            top left corner)
	 * 
	 */

	public void setName(final String name) {
		frame.setTitle(name);
	}

	/**
	 * Initializer for the main window components (Info panel, main display
	 * window and exit button
	 */
	private void init() {
		frame = new JFrame();
		panel = new MainWindow();
		info = new JLabel("Messages ... ");
		buttons = new ButtonPanel();
		buttons.setBorder(BorderFactory.createLineBorder(Color.black));

		panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		info.setPreferredSize(new Dimension(WIDTH, INFO_PANEL_HEIGHT));
		buttons.setPreferredSize(new Dimension(WIDTH, BUTTON_PANEL_HEIGHT));

		frame.add(panel, BorderLayout.CENTER);
		frame.add(info, BorderLayout.NORTH);
		frame.add(buttons, BorderLayout.SOUTH);

		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		initImages();

		new ButtonWatcher();

	}

	/**
	 * @param newMessage
	 *            Message to be displayed
	 * 
	 *            This method replaces the message shown on the top row of the
	 *            display window.
	 * 
	 */
	public void setMessage(String newMessage) {
		info.setText(newMessage);
	}

	synchronized private void releaseObserver() {
		notify();
	}

	synchronized private void waitForButton() throws InterruptedException {
		while (!buttonPressed) {
			wait();
		}
		setChanged();
		notifyObservers(buttonName);
	}

	/**
	 * Reads all images on IMAGE_DIR and saves a map of their names (without
	 * extention) to their full paths (with extentions)
	 */
	private void initImages() {
		File dir = new File(IMAGE_DIR);
		for (File f : dir.listFiles()) {
			assert (f.getName().lastIndexOf('.') != -1);
			imageDB.put(f.getName().substring(0, f.getName().lastIndexOf('.')),
					new ImageIcon(IMAGE_DIR + "/" + f.getName()));
		}
	}

	/**
	 * 
	 * Make the window visible.
	 * 
	 */
	public void go() {
		frame.setVisible(true);
	}

	// /**
	// *
	// * Add a new set of images to the main window.
	// *
	// * @param newImages
	// * images to be added to main window
	// *
	// * @throws IllegalArgumentException
	// * if no image with that name (and a suitable extension) is
	// * found the images folder
	// *
	// */
	//
	// public void newImages(final List<ImageComponent> newImages) {
	// synchronized (images) { // Added 16-Mar-2016
	// if (newImages == null)
	// return;
	// if (newImages.size() == 0)
	// return;
	// for (ImageComponent i : newImages) {
	// if (!imageDB.containsKey(i.getName())) {
	// throw new IllegalArgumentException("No such image in DB " + i.getName());
	// }
	// }
	// images.addAll(newImages);
	// }
	// }

	/**
	 * Removes the image given as a parameter.
	 * 
	 * Does nothing if there is no match.
	 * 
	 * @param image
	 *            to be removed (must be the exact same Object and not a copy)
	 * 
	 */

	public void removeImage(final ImageComponent image) {
		synchronized (images) { // Added 16-Mar-2016
			images.remove(image);
		}
	}

	/**
	 * Adds image to main window
	 * 
	 * @param image
	 *            to be added
	 */
	public void addImage(final ImageComponent image) {
		if (image == null)
			throw new IllegalArgumentException("Tentativa de inserção de imagem nula");
		if (imageDB.get(image.getName()) == null)
			throw new IllegalArgumentException("Não há nenhuma imagem chamada: " + image.getName());
		synchronized (images) { // Added 16-Mar-2016
			images.add(image);
		}
	}

	/**
	 * Clear all images displayed in main window.
	 */
	public void clearImages() {
		synchronized (images) {
			images.clear();
		}
	}

	@SuppressWarnings("serial")
	private class MainWindow extends JPanel {
		@Override
		public void paintComponent(Graphics g) {
			Graphics2D g2 = (Graphics2D) g;
			for (int j = 0; j != MAX_LAYER; j++) {
				synchronized (shapes) {
					for (ShapeComponent i : shapes) {
						if (i.getLayer() == j) {
							g2.setColor(i.getColor());
							g2.fill(i.getShape());
						}
					}
				}
				synchronized (images) {
					for (ImageComponent i : images) {
						if (i.getLayer() == j) {
							g.drawImage(imageDB.get(i.getName()).getImage(), (int) i.getPosition().getX(),
									(int) i.getPosition().getY(), i.getWidth(), i.getHeight(), frame);
						}
					}
				}
			}
		}
	}

	private class ButtonWatcher extends Thread {
		public void run() {
			try {
				while (true)
					waitForButton();
			} catch (InterruptedException e) {
			}
		}
	}

	@SuppressWarnings("serial")
	private class ButtonPanel extends JPanel {
		private JButton exit = new JButton("Exit");

		public ButtonPanel() {
			setLayout(new BorderLayout());
			add(exit, BorderLayout.EAST);
			exit.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					buttonPressed = true;
					buttonName = exit.getActionCommand();
					setChanged();
					notifyObservers();
				}
			});
		}
	}

	/**
	 * Force scheduling of a new window paint (this may take a while, it does
	 * not necessarily happen immediately after this instruction is issued)
	 */
	public void update() {
		frame.repaint();
	}

	/**
	 * Terminate window GUI
	 */
	public void dispose() {
		images.clear();
		// statusImages.clear();
		imageDB.clear();
		frame.dispose();
	}

	public void addShape(ShapeComponent s) {
		if (s == null)
			throw new IllegalArgumentException();
		synchronized (shapes) {
			shapes.add(s);
		}
	}

	synchronized public void removeShape(ShapeComponent s) {
		synchronized (shapes) {
			shapes.remove(s);
		}
	}

	public boolean isButtonPressed() {
		return buttonPressed;
	}

	// public String getButtonName() {
	// return buttonName;
	// }

}
