package logic;

import java.util.LinkedList;

import data.*;
import entities.*;

public class Login {
	private DataPersona dp;
	
	public Login() {
		dp=new DataPersona();
	}
	
	public Persona validate(Persona p) {
		/* para hacer mas seguro el manejo de passwords este sera un lugar 
		 * adecuado para generar un hash de la password utilizando un cifrado
		 * asimetrico como sha256 y utilizar el hash en lugar de la password en plano 
		 */
		return dp.getByUser(p);
	}

	public LinkedList<Persona> getAll(){
		return dp.getAll();
	}

	public Persona getByDocumento(Persona per) {
		return dp.getByDocumento(per);
	}
	
	public LinkedList<Persona> getByApellido(Persona per){
		return dp.getByApellido(per);
	}
	
	public void add_persona(Persona per) {
		dp.add(per);
	}
	
	public void update(Persona per) {
		dp.update(per);
	}
	
	public void delete(Persona per) {
		dp.delete(per);
	}
}
