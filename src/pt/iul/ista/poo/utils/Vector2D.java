package pt.iul.ista.poo.utils;

public class Vector2D {

	private int x;
	private int y;
	
	public Vector2D(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public Vector2D multiply(int k) {
		return new Vector2D(k * x, k * y);
	}
	
	public Vector2D plus(Vector2D vector2d) {
		return new Vector2D(vector2d.getX() + x, vector2d.getY() + y);
	}

	public Vector2D minus(Vector2D vector2d) {
		return new Vector2D(x - vector2d.getX(), y - vector2d.getY());
	}

	

}
