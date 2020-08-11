package pt.iul.ista.poo.utils;

public enum Orientation {
    NORTH(new Vector2D(0,-1)), WEST(new Vector2D(-1,0)), SOUTH(new Vector2D(0,1)), EAST(new Vector2D(1,0));

    private Vector2D vector;
   
    Orientation(Vector2D vector) {
    	this.vector = vector;
    }
	public Vector2D asVector() {
		return vector;
	}
	public char getFirstLetter() {
		return this.name().charAt(0);
	}
	public boolean isHorizontal() {
		return this == WEST || this == EAST;
	}
}
