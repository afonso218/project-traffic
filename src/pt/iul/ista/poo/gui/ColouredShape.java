package pt.iul.ista.poo.gui;

import java.awt.Color;
import java.awt.Shape;
import java.awt.geom.Point2D;

/**
 * @author POO 2016
 *
 *
 * Esta classe é apenas um exemplo
 *
 */
public class ColouredShape implements ShapeComponent {

	@Override
	public String toString() {
		return "ColouredShape [shape=" + shape + ", color=" + color + ", layer=" + layer + "]";
	}

	private Shape shape;
	private Color color;
	private int layer = 0;
	
	public ColouredShape(Shape shape, Color color) {
		this.shape = shape;
		this.color = color; 
	}

	public ColouredShape(Shape shape, Color color, int layer) {
		this.shape = shape;
		this.color = color; 
		this.layer = layer;
	}

	@Override
	public Point2D getPosition() {
		return new Point2D.Double((int)shape.getBounds().x, (int)shape.getBounds().y);
	}

	@Override
	public int getLayer() {
		return layer;
	}

	@Override
	public Color getColor() {
		return color;
	}

	@Override
	public Shape getShape() {
		return shape;
	}

}