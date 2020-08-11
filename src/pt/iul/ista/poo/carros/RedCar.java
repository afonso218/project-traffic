package pt.iul.ista.poo.carros;

import pt.iul.ista.poo.Street;


public class RedCar extends Car {

	public RedCar(Street street) {
		super(street , "RedCar", street.getMaxSpeed());
	}

}
