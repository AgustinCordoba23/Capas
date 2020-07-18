package ui;

import java.util.LinkedList;
import java.util.Scanner;

import entities.*;
import logic.Login;

public class Menu {
	Scanner s=null;
	Login ctrlLogin = new Login();

	public void start() {
		s = new Scanner(System.in);
		Persona p=login();
		System.out.println("Bienvenido "+p.getNombre()+" "+p.getApellido());
		System.out.println();
		
		String command;
		do {
			command=getCommand();
			executeCommand(command);
			System.out.println();
			
		}while(!command.equalsIgnoreCase("exit"));
		
		s.close();
	}

	private void executeCommand(String command) {
		switch (command) {
		case "list":
			System.out.println(ctrlLogin.getAll());
			break;
		case "find":
			System.out.println(find());
			break;
		case "search":
			System.out.println(search());
			break;
		case "new":
			new_persona();
			System.out.println("Persona añadida");
			break;
		case "edit":
			edit();
			break;
		case "delete":
			delete();
			System.out.println("Persona borrada");
			break;
		default:
			break;
		}
	}

	private String getCommand() {
		System.out.println("Ingrese el comando segun la opcion que desee realizar");
		System.out.println("list\t\tlistar todos");
		System.out.println("find\t\tbuscar por tipo y nro de documento"); //solo debe devolver 1
		System.out.println("search\t\tlistar por apellido"); //puede devolver varios
		System.out.println("new\t\tcrea una nueva persona y asigna un rol existente");
		System.out.println("edit\t\tbusca por tipo y nro de documento y actualiza todos los datos");
		System.out.println("delete\t\tborra por tipo y nro de documento");
		System.out.println();
		System.out.print("comando: ");
		return s.nextLine();
	}
	
	public Persona login() {
		Persona p=new Persona();		
		System.out.print("Email: ");
		p.setEmail(s.nextLine());
		System.out.print("password: ");
		p.setPassword(s.nextLine());		
		p=ctrlLogin.validate(p);	
		return p;		
	}
	
	private Persona find() {
		System.out.println();
		Persona p=new Persona();
		Documento d=new Documento();
		p.setDocumento(d);
		System.out.print("Tipo doc: ");
		d.setTipo(s.nextLine());
		System.out.print("Nro doc: ");
		d.setNro(s.nextLine());		
		return ctrlLogin.getByDocumento(p);
	}
	
	private LinkedList<Persona> search(){
		System.out.println();
		Persona p=new Persona();
		System.out.println("Apellido: ");
		p.setApellido(s.nextLine());
		return ctrlLogin.getByApellido(p);
	}
	
	private void new_persona() {
		System.out.println();
		Persona p = new Persona();
		Documento d = new Documento();
		Rol r = new Rol();
		System.out.println("Nombre: ");
		p.setNombre(s.nextLine());
		System.out.println("Apellido: ");
		p.setApellido(s.nextLine());
		System.out.println("Tipo doc: ");
		d.setTipo(s.nextLine());
		System.out.println("Nro doc: ");
		d.setNro(s.nextLine());
		p.setDocumento(d);
		System.out.println("Email: ");
		p.setEmail(s.nextLine());
		System.out.println("Telefono: ");
		p.setTel(s.nextLine());
		p.setHabilitado(true);
		System.out.println("Password: ");
		p.setPassword(s.nextLine());
		System.out.println("Rol? 1. Admin, 2. User"); //se podria ir a buscar a la db los roles que hay
		if(Integer.parseInt(s.nextLine()) == 1) {
			r.setDescripcion("Admin");
			r.setId(1);
		} else {
			r.setDescripcion("User");
			r.setId(2);
		}
		p.addRol(r);
		ctrlLogin.add_persona(p);
		System.out.println(p.hasRol(r));
	}
	
	private void edit() {
		System.out.println();
		Persona p=new Persona();
		Documento d=new Documento();
		p.setDocumento(d);
		System.out.print("Tipo doc: ");
		d.setTipo(s.nextLine());
		System.out.print("Nro doc: ");
		d.setNro(s.nextLine());		
		Persona persona_encontrada = ctrlLogin.getByDocumento(p);
		if (persona_encontrada != null) {
			Rol r = new Rol();
			System.out.println("Nombre: ");
			persona_encontrada.setNombre(s.nextLine());
			System.out.println("Apellido: ");
			persona_encontrada.setApellido(s.nextLine());
			System.out.println("Email: ");
			persona_encontrada.setEmail(s.nextLine());
			System.out.println("Telefono: ");
			persona_encontrada.setTel(s.nextLine());
			persona_encontrada.setHabilitado(true);
			System.out.println("Password: ");
			persona_encontrada.setPassword(s.nextLine());
			System.out.println("Rol? 1. Admin, 2. User");
			if(Integer.parseInt(s.nextLine()) == 1) {
				r.setDescripcion("Admin");
				r.setId(1);
			} else {
				r.setDescripcion("User");
				r.setId(2);
			}
			Rol rol_remover = new Rol();
			rol_remover.setId(1);
			persona_encontrada.removeRol(rol_remover);
			rol_remover.setId(2);
			persona_encontrada.removeRol(rol_remover);
			persona_encontrada.addRol(r);
			ctrlLogin.update(persona_encontrada);
		} else {
			System.out.println("Persona no encontrada en la base de datos");
		}
	}
	
	private void delete() {
		System.out.println();
		Persona p=new Persona();
		Documento d=new Documento();
		p.setDocumento(d);
		System.out.print("Tipo doc: ");
		d.setTipo(s.nextLine());
		System.out.print("Nro doc: ");
		d.setNro(s.nextLine());
		ctrlLogin.delete(p);
	}

}
