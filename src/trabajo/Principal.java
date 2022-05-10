package trabajo;

import java.util.ArrayList;
import java.util.Scanner;

import trabajo.MiMenu.Entrada;

public class Principal {

	public static void main(String[] args) {
		
		Pelicula peli = null;
		
		try {
			peli = new Pelicula();
			System.out.println("Conexión con la base de datos > OK");
		}catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
			// Fin del programa.
			return;
		}
		
		// Menú completo
		MiMenu menu = new MiMenu();
		menu.add("Listado");
		menu.add("Nueva película");
		menu.add("Modificar (uno a uno)");
		menu.add("Modificar (en grupo)");
		menu.add("Eliminar (uno a uno)");
		menu.add("Eliminar (en grupo)");
		menu.add("Cálculos");
		menu.add("Salida");
		
		// Menú reducido
		MiMenu reducido = new MiMenu();
		reducido.add("Nuevo");
		reducido.add("Salida");

		// Bucle de gestión del menú
		boolean salir = false;
		do {
			if (peli.estaVacia() == true) {
				System.out.println("BD vacía.");
				switch(reducido.ver("Festival de cine:")) {
					case 1: 
						peli.alta(); 
						break;
					default:
						salir = true;
						break;
				}
			}
			else {
				switch(menu.ver("Opciones:")) {
					case 1: 
						peli.listado();
						break;
					case 2:
						peli.alta();
						break;
					case 3: 
						peli.modificarUnoAUno();
						break;
					case 4:
						peli.modificarEnGrupo();
						break;
					case 5: 
						peli.eliminarUnoAUno();
						break;
					case 6:
						peli.eliminarEnGrupo();
						break;
					case 7: 
						peli.calculo();
						break; 
					default:
						salir = true;
						break;
				}
			}
		} while(!salir);			
		System.out.println("Fin de la aplicación.");
	}		
}
