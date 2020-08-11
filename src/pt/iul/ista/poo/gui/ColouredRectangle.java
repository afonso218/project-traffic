package pt.iul.ista.poo.gui;

import java.awt.Color;
import java.awt.geom.Rectangle2D;

/**
 * @author POO2016
 *
 *
 * Esta classe é apenas um exemplo
 *
 */
public class ColouredRectangle extends ColouredShape {
	@Override
	public String toString() {
		return "ColouredRectangle [toString()=" + super.toString() + "]";
	}

	public ColouredRectangle(int i, int j, int k, int l, Color color) {
		super(new Rectangle2D.Double(i, j, k, l), color); 
	}

	public ColouredRectangle(int i, int j, int k, int l, Color color, int layer) {
		super(new Rectangle2D.Double(i, j, k, l), color, layer);
	}

}