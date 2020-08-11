package pt.iul.ista.poo.gui;

import java.awt.geom.Point2D;

public interface GraphicsComponent {

	/**
	 * Getter for the position (in grid coordinates) were the image should be placed.
	 * 
	 * @return A position.
	 */
	Point2D getPosition();

	int getLayer();
	
//	boolean isImage();
//
//	boolean isShape();

}
