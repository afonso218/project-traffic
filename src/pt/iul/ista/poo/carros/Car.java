package pt.iul.ista.poo.carros;

import java.awt.geom.Point2D;
import java.util.List;

import javax.swing.ImageIcon;

import pt.iul.ista.poo.Street;
import pt.iul.ista.poo.gui.ImageComponent;
import pt.iul.ista.poo.utils.Orientation;
import pt.iul.ista.poo.utils.Rectangle;

public abstract class Car implements ImageComponent {

	public static final int ALTURA = 20;
	public static final int COMPRIMENTO = 40;

	private Street street;
	private String imageName;
	private int position;
	private int layer = 1;
	private int speed;

	public Car(Street street, String imageName, int speed) {
		this.street = street;
		this.imageName = imageName;
		position = 0;
		this.speed = speed;
	}

	public void move(List<Car> allCars) {
		if (estaLivre(allCars)) {
			position += speed;
		}
	}

	public boolean estaLivre(List<Car> allCars) {
		Rectangle rectanguloCarro1 = this.getRectangulo(this.getPositionFutura());
		for (Car car : allCars) {
			if (car != this) {
				Rectangle r2 = car.getRectangulo(car.getPosition());
				if (rectanguloCarro1.estaContido(r2)) {
					return false;
				}
			}
		}
		return true;
	}

	public Rectangle getRectangulo(Point2D p) {
		double x1 = p.getX();
		double y1 = p.getY();
		double x2 = p.getX() + getWidth();
		double y2 = p.getY() + getHeight();
		return new Rectangle(x1, x2, y1, y2);
	}

	@Override
	public String toString() {
		return "Car [position=" + getPosition() + ", Name=" + getName() + "]";
	}

	public boolean isFinished() {
		return position >= street.getStreetLenght();
	}

	@Override
	public Point2D getPosition() {
		if (street.getOrientation() == Orientation.NORTH) {
			return new Point2D.Double(street.getCarStartPoint().getX(), street.getCarStartPoint().getY() - position);
		}
		if (street.getOrientation() == Orientation.WEST) {
			return new Point2D.Double(street.getCarStartPoint().getX() - position, street.getCarStartPoint().getY());
		}
		if (street.getOrientation() == Orientation.SOUTH) {
			return new Point2D.Double(street.getCarStartPoint().getX(), street.getCarStartPoint().getY() + position);
		}
		if (street.getOrientation() == Orientation.EAST) {
			return new Point2D.Double(street.getCarStartPoint().getX() + position, street.getCarStartPoint().getY());
		}
		return null;
	}

	public Point2D getPositionFutura() {
		if (street.getOrientation() == Orientation.NORTH) {
			return new Point2D.Double(street.getCarStartPoint().getX(),
					street.getCarStartPoint().getY() - (position + speed));
		}
		if (street.getOrientation() == Orientation.WEST) {
			return new Point2D.Double(street.getCarStartPoint().getX() - (position + speed),
					street.getCarStartPoint().getY());
		}
		if (street.getOrientation() == Orientation.SOUTH) {
			return new Point2D.Double(street.getCarStartPoint().getX(),
					street.getCarStartPoint().getY() + (position + speed));
		}
		if (street.getOrientation() == Orientation.EAST) {
			return new Point2D.Double(street.getCarStartPoint().getX() + (position + speed),
					street.getCarStartPoint().getY());
		}
		return null;
	}

	@Override
	public String getName() {
		return imageName + "_" + street.getOrientation().getFirstLetter();
	}

	@Override
	public ImageIcon getImage() {
		return new ImageIcon();
	}

	@Override
	public int getLayer() {
		return layer;
	}

	@Override
	public int getHeight() {
		if (street.getOrientation().isHorizontal()) {
			return ALTURA;
		} else {
			return COMPRIMENTO;
		}
	}

	@Override
	public int getWidth() {
		if (street.getOrientation().isHorizontal()) {
			return COMPRIMENTO;
		} else {
			return ALTURA;
		}
	}
}
