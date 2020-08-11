package pt.iul.ista.poo.utils;

public class Rectangle {

	private double left;
	private double right;
	private double top;
	private double bottom;

	public Rectangle(double left, double right, double top, double bottom) {
		this.left = left;
		this.right = right;
		this.top = top;
		this.bottom = bottom;
	}

	public boolean estaContido(Rectangle other) {
		return (this.left <= other.right && other.left <= this.right && this.top <= other.bottom
				&& other.top <= this.bottom);
	}
}
