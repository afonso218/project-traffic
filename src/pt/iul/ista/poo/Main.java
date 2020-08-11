package pt.iul.ista.poo;

import java.awt.geom.Point2D;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import pt.iul.ista.poo.carros.Car;
import pt.iul.ista.poo.gui.UserInterfaceWindow;
import pt.iul.ista.poo.utils.Orientation;

public class Main implements Observer {

	private boolean stop;
	private List<Street> streets = new ArrayList<Street>();
	private UserInterfaceWindow gui = UserInterfaceWindow.getInstance();

	public Main() {

		lerJSON();
		for(Street s: streets){
			gui.addShape(s);
		}
		gui.addObserver(this);
	}

	private void lerJSON() {
		
		try {
			JSONParser parser = new JSONParser();
			JSONObject jsonObject = (JSONObject) parser.parse(new FileReader("streets.json"));
			JSONArray jsonstreets = (JSONArray) jsonObject.get("streets"); //vai buscar o valor do nome streets que corresponde a um vetor
			Iterator<JSONObject> iterator = jsonstreets.iterator();
			while(iterator.hasNext()){
				JSONObject obj = iterator.next();
				String name = (String)obj.get("name");
				
				JSONObject startPoint = (JSONObject)obj.get("startPoint");
				 
				int x = ((Long) startPoint.get("x")).intValue();
				int y = ((Long) startPoint.get("y")).intValue();
				
				int streetLength = ((Long) obj.get("streetLength")).intValue();
				
				Orientation orientation = Orientation.valueOf((String)obj.get("orientation"));
				
				double car_probability = (double)obj.get("car_probability");
				
				int max_speed = ((Long)obj.get("max_speed")).intValue();
				
				Street street = new Street(name, new Point2D.Double(x, y), streetLength, orientation, car_probability, max_speed);
				System.out.println(street);
				streets.add(street);
			}
			
		} catch (Exception e) {
			System.err.println("Erro a ler o ficheiro json");
			e.printStackTrace();
		}
	}

	private void start() {

		gui.go();
		while (!stop) { 
			
			int numeroCarros = 0;
			for(Street s : streets){
				s.update(getAllCars());
				numeroCarros += s.getNumeroCarros();
			}
			
			gui.setMessage("Numero de streets: " + streets.size() + " Numero de carros: " + numeroCarros);
			gui.update(); // update User Interface
			try {
				Thread.sleep(100); // regulate sleep to slow-down or speedup
									// simulation
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		gui.dispose();
	}
	
	private List<Car> getAllCars(){
		List<Car> carros = new ArrayList<Car>();
		for(Street street: streets){
			carros.addAll(street.getCars());
		}
		return carros;
	}
	
	@Override
	public void update(Observable arg0, Object arg1) {
		System.out.println("Observer notified, shutting down");
		stop = true;
	}

	public static void main(String[] args) {
		new Main().start();
	}
	

}
