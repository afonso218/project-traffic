package pt.iul.ista.poo.carros;

import pt.iul.ista.poo.Street;


public class BlueCar extends Car {

	public BlueCar(Street street) {
		super(street , "BlueCar", (street.getMaxSpeed()+1)/2);
	}

}
