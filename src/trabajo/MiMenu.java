package trabajo;

import java.util.*;

public class MiMenu {
	
	// Versión 2018
	private ArrayList <Entrada> menu = new ArrayList<Entrada>();	
	private int opcion = 0;
	private Scanner teclado = new Scanner(System.in);
	
	public class Entrada {
		private String entrada = "";
		private int codigo = 0;

		public Entrada(String entrada, int codigo) {
			this.entrada = entrada;
			this.codigo = codigo;
		}

		public String getEntrada() {
			return entrada;
		}

		public int getCodigo() {
			return codigo;
		}

		@Override
		public String toString() {
			return String.format("Entrada [%-30s] (%4d)", this.entrada, this.codigo);
		}
		
	}
	
	public int getOpcion() {
		return opcion;
	}
	
	public int getCodigo() {
		if (menu.isEmpty()==false)
			return menu.get(opcion-1).getCodigo();
		else
			return 0;
	}
	
	public String getEntrada() {
		if (menu.isEmpty()==false)
			return menu.get(opcion-1).getEntrada();
		else
			return "N/A";
	}
	
	
	public void add(String entrada)
	{
		this.add(entrada, 0);
	}
	
	public void add(String entrada, int codigo)
	{
		menu.add(new Entrada(entrada,codigo));
	}
	
	private int leerOpcion()
	{
		
		boolean sw = true;
		do {
			try {
				System.out.print("¿Opción? (1-"+menu.size()+")? ");
				opcion = Integer.parseInt(teclado.nextLine());
				if (!(opcion>=1 && opcion<=menu.size()))
					throw new Exception("Introduzca un valor entre 1 y "+menu.size());
				sw = false;
			} catch (NumberFormatException e) {
				System.out.println("Error: introduzca un valor numérico");
			} catch (Exception e) {
				System.out.println("Error: "+e.getMessage());
			}
		} while (sw);
		System.out.println();
		return opcion;
	}
	
	public int ver()
	{
		return this.ver("Menú");
	}
	
	public int ver(String encabezado)
	{
		this.opcion = 0;
		if (menu.size()==0) return this.opcion;
		
		// Encabezado 
		System.out.println("\n"+encabezado);
		for(int i=1;i<=encabezado.length();i++)
			System.out.print("=");
		
		// Menú de opciones
		for(int i=0;i<menu.size();i++)
		{
			System.out.printf("\n%3d. %s",i+1,menu.get(i).getEntrada());
		}
		System.out.println();
		return leerOpcion();
	}

	public boolean salirMenu() {
		return this.opcion == menu.size();
	}

	@Override
	public String toString() {
		return "Clase MiMenu (2018)";
	}
	
	
}
