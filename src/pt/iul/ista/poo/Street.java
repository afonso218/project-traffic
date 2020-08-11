package pt.iul.ista.poo;

import java.awt.Color;
import java.awt.Shape;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import pt.iul.ista.poo.carros.BlueCar;
import pt.iul.ista.poo.carros.Car;
import pt.iul.ista.poo.carros.GreenCar;
import pt.iul.ista.poo.carros.RedCar;
import pt.iul.ista.poo.gui.ShapeComponent;
import pt.iul.ista.poo.gui.UserInterfaceWindow;
import pt.iul.ista.poo.utils.Orientation;

public class Street implements ShapeComponent {

	private List<Car> carros = new ArrayList<Car>();

	private final int layer = 0;
	private final Color color = Color.DARK_GRAY;
	private final Shape shape;

	private final int STREET_SIZE = 20;

	private String name;
	private Point2D startPoint;
	private Point2D carStartPoint;
	private int streetLenght;
	private Orientation orientation;
	private double car_probabability;
	private int max_speed;

	public Street(String name, Point2D startPoint, int streetLenght, Orientation orientation, double car_probabability,
			int max_speed) {
		this.name = name;
		this.startPoint = startPoint;
		this.streetLenght = streetLenght;
		this.orientation = orientation;
		this.car_probabability = car_probabability;
		this.max_speed = max_speed;
		carros = new ArrayList<Car>();

		if (startPoint.getX() == 0) {
			shape = new Rectangle2D.Double(startPoint.getX(), startPoint.getY(), streetLenght, STREET_SIZE);
		} else {
			shape = new Rectangle2D.Double(startPoint.getX(), startPoint.getY(), STREET_SIZE, streetLenght);
		}

		if (orientation == Orientation.NORTH) {
			carStartPoint = new Point2D.Double(startPoint.getX(), startPoint.getY() + streetLenght - Car.COMPRIMENTO);
		}
		if (orientation == Orientation.WEST) {
			carStartPoint = new Point2D.Double(startPoint.getX() + streetLenght - Car.COMPRIMENTO, startPoint.getY());
		}
		if (orientation == Orientation.SOUTH) {
			carStartPoint = new Point2D.Double(startPoint.getX(), startPoint.getY());
		}
		if (orientation == Orientation.EAST) {
			carStartPoint = new Point2D.Double(startPoint.getX(), startPoint.getY());
		}
	}

	public Point2D getCarStartPoint() {
		return carStartPoint;
	}

	public String getName() {
		return name;
	}

	public Point2D getStartingPoint() {
		return startPoint;
	}

	public int getStreetLenght() {
		return streetLenght;
	}

	public Orientation getOrientation() {
		return orientation;
	}

	public double getCar_probabability() {
		return car_probabability;
	}

	public int getMaxSpeed() {
		return max_speed;
	}

	@Override
	public Point2D getPosition() {
		return startPoint;
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

	public void update(List<Car> allCars) {
		Iterator<Car> it = carros.iterator();
		while (it.hasNext()) {
			Car c = it.next();
			c.move(allCars);
			if (c.isFinished()) {
				it.remove();
				UserInterfaceWindow.getInstance().removeImage(c);
			}
		}
		gerarCarros(allCars);
	}

	private void gerarCarros(List<Car> allCars) {
		Random random = new Random();
		if (car_probabability >= random.nextDouble()) {
			Car car;
			int x = random.nextInt(3);
			if (x == 0) {
				car = new RedCar(this);
			} else if (x == 1) {
				car = new BlueCar(this);
			} else {
				car = new GreenCar(this);
			}

			if (car.estaLivre(allCars)) {
				addCar(car);
			}
		}
	}

	public void addCar(Car c) {
		if (c != null) {
			carros.add(c);
			UserInterfaceWindow.getInstance().addImage(c);
		}

	}

	public List<Car> getCars() {
		return carros;
	}

	public int getNumeroCarros() {
		return carros.size();
	}

	@Override
	public String toString() {
		return "Street [name=" + name + ", startingPoint=" + startPoint + ", streetLenght=" + streetLenght
				+ ", orientation=" + orientation + ", car_probabability=" + car_probabability + ", max_speed="
				+ max_speed + "]";
	}

}