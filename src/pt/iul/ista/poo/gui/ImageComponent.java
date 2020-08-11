package pt.iul.ista.poo.gui;

import javax.swing.ImageIcon;



/**
 * @author POO2016
 * 
 *         ImageComponent is the interface required to all "images" used by
 *         ImageMatrixGUI.
 * 
 *         ImageComponent is only required to provide the name of the image (e.g.
 *         "XxxX") and its position (in tile coordinates, with 0,0 in the top
 *         left corner and increasing to the right in the horizontal axis and
 *         downwards in the vertical axis). The ImageMatrixGUI will look for an
 *         image with that name in the "images" folder (e.g. "XxxX.png") and
 *         draw that image in the window.
 *
 */
public interface ImageComponent extends GraphicsComponent {

	/**
	 * The name of the image. Must correspond to the name of an image file in
	 * the "images" folder otherwise i will trigger one of the following
	 * exceptions: FileNotFoundException, IllegalArgumentException
	 * 
	 * @return The name of the image file containing the desired image (without
	 *         extention)
	 * 
	 */
	String getName();

	ImageIcon getImage();

	int getHeight();

	int getWidth();
}
