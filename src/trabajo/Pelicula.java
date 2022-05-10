package trabajo;

import java.sql.*;
import java.util.Scanner;

public class Pelicula {

	private Scanner teclado = new Scanner(System.in);
	
	public Pelicula() throws Exception {
		this.conectar();
	}
	
	private Connection conectar() throws SQLException {
		String url = "jdbc:mysql://localhost:3306/dam1" + 
				"?useUnicode=true&characterEncoding=UTF-8"+ 
				"&autoReconnect=true&useSSL=false";
		String user = "root";
		String password = "";		
		
		return DriverManager.getConnection(url, user, password);
	}
	
	public boolean estaVacia() {
		String cons = "SELECT count(*) AS 'total' FROM peliculas";
		boolean sw = true;
		
		try (
			Connection cn = this.conectar();
			Statement st = cn.createStatement();
			ResultSet rs = st.executeQuery(cons);
		) {
			int total = 0;
			if (rs.next()) {
				total = rs.getInt("total");
				sw = (total == 0);
			}
		} catch (SQLException e) {
			System.out.println("[BD-SQL]: " + e.getMessage());
		} catch (Exception e) {
			System.out.println("[OTRO]: " + e.getMessage());
			e.printStackTrace();
		}
		return sw;
	}
	
	public int verDatos(ResultSet rs) {
		int cont = 0;
		try {
			while (rs.next()) {
				// Accceso a los campos
				if (cont == 0) {
					encabezado();
				}
				cont++;
				String titulo = rs.getString("titulo");
				int a�o = rs.getInt("a�o");
				String streaming = rs.getString("streaming");
				boolean en4k = rs.getBoolean("4k");
				int duracion = rs.getInt("duracion");
				String genero = rs.getString("genero");
				int proyecciones = rs.getInt("proyecciones");
				
				// Salida en consola
				System.out.printf("%-15s %5d %-10s %-2s %8d %-8s %12d\n", titulo, a�o, streaming, en4k ? "Si":"No", duracion, genero, proyecciones );
			}			
		} catch (SQLException e) {
			System.out.println("[BD-SQL]: " + e.getMessage());
		} catch (Exception e) {
			System.out.println("[OTRO]: " + e.getMessage());
			e.printStackTrace();
		}
		return cont;
	}
	
	private void encabezado () {
		System.out.printf("%-15s %-5s %-10s %-2s %-8s %-8s %-12s\n", "T�tulo", " A�o", "Streaming", "4K", "Duraci�n", "G�nero", "Proyecciones");
		System.out.printf("%-15s %-5s %-10s %-2s %-8s %-8s %-12s\n", "======", "=====", "=========", "==", "========", "======", "============");
	}
	
	private void cabecera(String titulo) {
		System.out.printf("\n[%s]\n", titulo);
		hr(titulo.length());
	}
	
	public void listado() {
		String cons = "SELECT * FROM peliculas ORDER BY titulo";
		
		try (
			Connection cn = this.conectar();
			Statement st = cn.createStatement();
			ResultSet rs = st.executeQuery(cons);
		){
			cabecera("Listado");
			int cont = verDatos(rs);
			System.out.printf("\nEn lista: %2d pel�culas\n", cont);
		} catch (SQLException e) {
			System.out.println("[BD-SQL]: " + e.getMessage());
		} catch (Exception e) {
			System.out.println("[OTRO]: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	public void alta() {
		// Consulta SQL parametrizada
					String cons = "INSERT INTO peliculas VALUES (default, ?, ?, ?, ?, ?, ?, ?)";
		
		try (
			Connection cn = this.conectar();
			PreparedStatement ps = cn.prepareStatement(cons);
		) {		
			cabecera("Nueva pel�cula");
			
			System.out.println("�T�tulo?");
			String titulo = teclado.nextLine().trim();
					
			System.out.println("�A�o?");
			int a�o = Integer.parseInt(teclado.nextLine().trim());
			
			MiMenu menuS = new MiMenu();
			menuS.add("Amazon");
			menuS.add("Apple TV");
			menuS.add("Disney+");
			menuS.add("HBO Max");
			menuS.add("Hulu");
			menuS.add("Netflix");
			menuS.add("Paramount+");
			menuS.add("Peacock");
			menuS.add(" > Salir");
			
			String streaming = "";
			
			switch(menuS.ver("�Plataforma de streaming? (1-9):")) {
				case 1:
					streaming = "Amazon";
					break;
				case 2:
					streaming = "Apple TV";
					break;
				case 3:
					streaming = "Disney+";
					break;
				case 4:
					streaming = "HBO Max";
					break;
				case 5:
					streaming = "Hulu";
					break;
				case 6:
					streaming = "Netflix";
					break;
				case 7:
					streaming = "Paramount+";
					break;
				case 8:
					streaming = "Peacock";
					break;
				case 9:
					break;
			}		
			
			System.out.println("�4K? (s/N):");		
			String resp = teclado.nextLine().trim().toLowerCase();
			boolean en4k = false;
			if (resp.length() > 0) {
				if (resp.charAt(0) == 's') {
					en4k = true;
				}
			}
			
			System.out.println("�Duraci�n?");
			int duracion = Integer.parseInt(teclado.nextLine().trim());
			
			MiMenu menuG = new MiMenu();
			menuG.add("Acci�n");
			menuG.add("B�lico");
			menuG.add("Drama");
			menuG.add("Fantas�a");
			menuG.add("Humor");
			menuG.add("SciFi");
			menuG.add("Terror");
			menuG.add("Thriller");
			menuG.add(" > Salir");
			String genero = "";
			switch(menuG.ver("�G�nero? (1-9):")) {
				case 1:
					genero = "Acci�n";
					break;
				case 2:
					genero = "B�lico";
					break;
				case 3:
					genero = "Drama";
					break;
				case 4:
					genero = "Fantas�a";
					break;
				case 5:
					genero = "Humor";
					break;
				case 6:
					genero = "SciFi";
					break;
				case 7:
					genero = "Terror";
					break;
				case 8:
					genero = "Thriller";
					break;
				case 9:
					break;
			}
			
			System.out.println("�Proyecciones?");
			int proyecciones = Integer.parseInt(teclado.nextLine().trim());
			
			// Carga de par�metros con datos
			ps.setString(1, titulo);
			ps.setInt(2, a�o);
			ps.setString(3, streaming);
			ps.setBoolean(4, en4k);
			ps.setInt(5, duracion);
			ps.setString(6, genero);
			ps.setInt(7, proyecciones);
			
			// Lanzamos la consulta
			int cont = ps.executeUpdate();
			System.out.println("Se ha a�adido [" + cont + "] pel�cula a la tabla.");
			
		} catch (SQLException e) {
			System.out.println("[BD-SQL]: " + e.getMessage());
		} catch (Exception e) {
			System.out.println("[OTRO]: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	public void modificarUnoAUno() {
		
		int cont = 0, modificados = 0;
		
		cabecera("Modificar (uno a uno) / (Empieza por)");
		
		System.out.println("�T�tulo? (empieza por)");
		String buscar = teclado.nextLine().trim().toLowerCase();
		
		String cons = "SELECT * FROM peliculas " + 
					"WHERE titulo LIKE '" + buscar + "%' " +
					"ORDER BY titulo";
		
		try (
			Connection cn = this.conectar();
			Statement st = cn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			ResultSet rs = st.executeQuery(cons);
		) {
			while (rs.next()) {
				if (cont == 0) {
					cabecera("Modificar");					
				}
				cont++;
				
				// Acceso a los campos
				String titulo = rs.getString("titulo");
				int a�o = rs.getInt("a�o");
				String streaming = rs.getString("streaming");
				boolean en4k = rs.getBoolean("4k");
				int duracion = rs.getInt("duracion");
				String genero = rs.getString("genero");
				int proyecciones = rs.getInt("proyecciones");
				
				// Vista detalle
				System.out.printf("%-12s: %s \n%-12s: %d \n%-12s: %s \n%-12s: %s \n%-12s: %d \n%-12s: %s \n%-12s: %d\n\n", 
								"T�tulo", titulo, "A�o", a�o, "Streaming", streaming, "4K", en4k ? "Si":"No", "Duraci�n", 
								duracion, "G�nero", genero, "Proyecciones", proyecciones );
				
				//Tratamiento uno a uno
				System.out.println("�Modificar? (s/N)");
				String res = teclado.nextLine().toLowerCase().trim();
				if (res.length() > 0) {
					if(res.charAt(0) == 's') {
						
						String dato = "";
						
						System.out.printf("�T�tulo? [%s]", titulo);
						dato = teclado.nextLine().trim();
						if (dato.length() > 0) {
							rs.updateString("titulo", dato);
						}
						
						System.out.printf("�A�o? [%s]", a�o);
						dato = teclado.nextLine().trim();
						if (dato.length() > 0) {
							rs.updateInt("a�o", Integer.parseInt(dato));
						}
						
						MiMenu menuS = new MiMenu();
						menuS.add("Amazon");
						menuS.add("Apple TV");
						menuS.add("Disney+");
						menuS.add("HBO Max");
						menuS.add("Hulu");
						menuS.add("Netflix");
						menuS.add("Paramount+");
						menuS.add("Peacock");
						menuS.add(" > Salir");
						
						switch(menuS.ver("�Plataforma de streaming? (1-9):")) {
							case 1:
								rs.updateString("streaming", "Amazon");
								break;
							case 2:
								rs.updateString("streaming", "Apple TV");
								break;
							case 3:
								rs.updateString("streaming", "Disney+");
								break;
							case 4:
								rs.updateString("streaming", "HBO Max");
								break;
							case 5:
								rs.updateString("streaming", "Hulu");
								break;
							case 6:
								rs.updateString("streaming", "Netflix");
								break;
							case 7:
								rs.updateString("streaming", "Paramount+");
								break;
							case 8:
								rs.updateString("streaming", "Peacock");
								break;
							case 9:
								break;
						}
						
						System.out.printf("�Disponible en 4K [%s]? (s/N)", en4k ? "Si":"No");
						dato = teclado.nextLine().trim().toLowerCase();
						boolean resp = false;
						if (dato.length() > 0) {
							if(dato.charAt(0) == 's') {
								resp = true;
							}
						}
						rs.updateBoolean("4k", resp);
						
						System.out.printf("�Duraci�n [%s]?", duracion);
						dato = teclado.nextLine().trim();
						if (dato.length() > 0) {
							rs.updateInt("duracion", Integer.parseInt(dato));
						}
						
						MiMenu menuG = new MiMenu();
						menuG.add("Acci�n");
						menuG.add("B�lico");
						menuG.add("Drama");
						menuG.add("Fantas�a");
						menuG.add("Humor");
						menuG.add("SciFi");
						menuG.add("Terror");
						menuG.add("Thriller");
						menuG.add(" > Salir");
						
						switch(menuG.ver("�G�nero? (1-9):")) {
							case 1:
								rs.updateString("genero", "Acci�n");
								break;
							case 2:
								rs.updateString("genero", "B�lico");
								break;
							case 3:
								rs.updateString("genero", "Drama");
								break;
							case 4:
								rs.updateString("genero", "Fantas�a");
								break;
							case 5:
								rs.updateString("genero", "Humor");
								break;
							case 6:
								rs.updateString("genero", "SciFi");
								break;
							case 7:
								rs.updateString("genero", "Terror");
								break;
							case 8:
								rs.updateString("genero", "Thriller");
								break;
							case 9:
								break;
						}
						
						System.out.printf("�Proyecciones? [%s]", proyecciones);
						dato = teclado.nextLine().trim();
						if (dato.length() > 0) {
							rs.updateInt("proyecciones", Integer.parseInt(dato));
						}						
						
						// Guardamos los cambios en el registro actual
						rs.updateRow();
						System.out.println("OK! Modificado");
						modificados++;						
					}
				}
				hr(10);
			}			
		} catch (SQLException e) {
			System.out.println("[BD-SQL]: " + e.getMessage());
		} catch (Exception e) {
			System.out.println("[OTRO]: " + e.getMessage());
			e.printStackTrace();
		}
		
		// Resumen de resultados
		System.out.printf("Encontradas: %d pel�culas | Modificadas: %d pel�culas.\n", cont, modificados);					
	}
	
	public void modificarEnGrupo() {
		int cont = 0, modificadas = 0;
		
		cabecera("Modificar (en grupo)");	
		
		String cons = "SELECT * FROM peliculas " + 
				"WHERE duracion > ?" +
				" ORDER BY titulo";
		
		String sql = "UPDATE peliculas " +
				"SET proyecciones = 0 " +
				"WHERE duracion > ?";
		
		try (
			Connection cn = this.conectar();
			PreparedStatement ps1 = cn.prepareStatement(cons);	
			PreparedStatement ps2 = cn.prepareStatement(sql);
		) {
			System.out.println("�Duraci�n? (superior a)");
			int buscar = Integer.parseInt(teclado.nextLine());
			ps1.setInt(1, buscar);			
			try (ResultSet rs = ps1.executeQuery()) {					
				cont = verDatos(rs);	
				System.out.printf("En lista %d pel�culas.\n", cont);
				if (cont > 0) {
					System.out.printf("�Modificar? (s/N)\n");
					String resp = teclado.nextLine().toLowerCase().trim();						
					if (resp.length() > 0) {
						if (resp.charAt(0) == 's') {
							ps2.setInt(1, buscar);
							modificadas = ps2.executeUpdate();
							System.out.println("> OK! Se han modificado " + modificadas + " pel�culas.");
						} 
					}
				}
				else {
					System.out.println("> No se han encontrado resultados.");
				}
			} catch (SQLException e) {
				System.out.println("[BD-SQL]: " + e.getMessage());
			} catch (Exception e) {
				System.out.println("[OTRO]: " + e.getMessage());
				e.printStackTrace();
			}	
			if (modificadas == 0) System.out.println("> No se ha modificado ninguna pel�cula.");
		} catch (SQLException e) {
			System.out.println("[BD-SQL]: " + e.getMessage());
		} catch (Exception e) {
			System.out.println("[OTRO]: " + e.getMessage());
			e.printStackTrace();
		}		
	}
	
	public void eliminarUnoAUno() {
		int cont = 0, borradas = 0;
		
		cabecera("Eliminar (uno a uno)");
		
		System.out.println("�T�tulo? (incluye)");
		String buscar = teclado.nextLine().trim().toLowerCase();
		
		String cons = "SELECT * FROM peliculas " + 
					"WHERE titulo LIKE " + "'%" + buscar + "%' " +
					"ORDER BY titulo";
		
		try (
				Connection cn = this.conectar();
				Statement st = cn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
				ResultSet rs = st.executeQuery(cons);
			) {
				while (rs.next()) {
					if (cont == 0) {
						cabecera("Eliminar");					
					}
					cont++;
					
					// Acceso a los campos
					String titulo = rs.getString("titulo");
					int a�o = rs.getInt("a�o");
					String streaming = rs.getString("streaming");
					boolean en4k = rs.getBoolean("4k");
					int duracion = rs.getInt("duracion");
					String genero = rs.getString("genero");
					int proyecciones = rs.getInt("proyecciones");
					
					// Vista detalle
					System.out.printf("%-12s: %s \n%-12s: %d \n%-12s: %s \n%-12s: %s \n%-12s: %d \n%-12s: "
							+ "%s \n%-12s: %d\n", "T�tulo", titulo, "A�o", a�o, "Streaming", streaming, "4K", 
							en4k ? "Si":"No", "Duraci�n", duracion, "G�nero", genero, "Proyecciones", proyecciones );
					
					//Tratamiento uno a uno
					System.out.println("�Eliminar? (s/N)");
					String res = teclado.nextLine().toLowerCase().trim();
					if (res.length() > 0) {
						if(res.charAt(0) == 's') {
							rs.deleteRow();	
							System.out.println("> OK! Borrada.");
							borradas++;						
						}
					}
				}	
				System.out.printf("Encontradas: %d pel�culas | Borradas: %d pel�culas.\n", cont, borradas);		
			} catch (SQLException e) {
				System.out.println("[BD-SQL]: " + e.getMessage());
			} catch (Exception e) {
				System.out.println("[OTRO]: " + e.getMessage());
				e.printStackTrace();
			}
			
	}
	
	public void eliminarEnGrupo() {
		int cont = 0, borradas = 0;
		
		cabecera("Eliminar (en grupo)");
				
		String cons = "SELECT * FROM peliculas " + 
					"WHERE streaming = ?" +
					" ORDER BY titulo";
		
		String sql = "DELETE FROM peliculas " +
					"WHERE streaming = ?";
		
		try (
				Connection cn = this.conectar();				
				PreparedStatement ps1 = cn.prepareStatement(cons);
				PreparedStatement ps2 = cn.prepareStatement(sql);				
			) {
				MiMenu menuS = new MiMenu();
				menuS.add("Amazon");
				menuS.add("Apple TV");
				menuS.add("Disney+");
				menuS.add("HBO Max");
				menuS.add("Hulu");
				menuS.add("Netflix");
				menuS.add("Paramount+");
				menuS.add("Peacock");
				menuS.add(" > Salir");
				
				String buscar = "";	
							
				switch(menuS.ver("�Plataforma de streaming? (1-9):")) {
				case 1:
					buscar = "Amazon";
					break;
				case 2:
					buscar = "Apple TV";
					break;
				case 3:
					buscar = "Disney+";
					break;
				case 4:
					buscar = "HBO Max";
					break;
				case 5:
					buscar = "Hulu";
					break;
				case 6:
					buscar = "Netflix";
					break;
				case 7:
					buscar = "Paramount+";
					break;
				case 8:
					buscar = "Peacock";
					break;
				case 9:
					break;
			}		
				ps1.setString(1, buscar);
				try (ResultSet rs = ps1.executeQuery()) {		
					cont = verDatos(rs);	
					System.out.printf("En lista %d pel�culas.\n", cont);
					if (cont > 0) {
						System.out.printf("�Eliminar? (s/N)\n");
						String resp = teclado.nextLine().toLowerCase().trim();						
						if (resp.length() > 0) {
							if (resp.charAt(0) == 's') {
								ps2.setString(1, buscar);
								borradas = ps2.executeUpdate();
								System.out.println("> OK! Se han borrado " + borradas + " pel�culas.");
							} 
						}
					}
					else {
						System.out.println("> No se han encontrado resultados.");
					}
				} catch (SQLException e) {
					System.out.println("[BD-SQL]: " + e.getMessage());
				} catch (Exception e) {
					System.out.println("[OTRO]: " + e.getMessage());
					e.printStackTrace();
				}
			if (borradas == 0) System.out.println("> No se ha borrado ninguna pel�cula.");
		} catch (SQLException e) {
			System.out.println("[BD-SQL]: " + e.getMessage());
		} catch (Exception e) {
			System.out.println("[OTRO]: " + e.getMessage());
			e.printStackTrace();
		}		
	}
		
	public void calculo() {
		try (
				Connection cn = this.conectar();
				CallableStatement cs = cn.prepareCall("{call media()}");
				ResultSet rs = cs.executeQuery();
			){
				int cont = 0;				
				cabecera("Duraci�n media de las pel�culas de cada g�nero:");	
				
				while (rs.next()) {
					if (cont == 0) {
						System.out.printf("%10s %5s \n", "G�nero", "Duraci�n media");	
						System.out.printf("%10s %5s \n", "======", "===============");
					}
					System.out.printf("%10s %,5d \n", rs.getString("genero"), rs.getInt("media"));
					cont++;
				}
				
				// Resumen de resultados
				System.out.printf("\nEn lista: %2d g�neros\n", cont);
			} catch (SQLException e) {
				System.out.println("[BD-SQL]: " + e.getMessage());
			} catch (Exception e) {
				System.out.println("[OTRO]: " + e.getMessage());
				e.printStackTrace();
			}
	}
	
	public void hr(int n) {
		for (int i = 0; i < n; i++) {
			System.out.print("=");
		}
		System.out.println();
		System.out.println();
	}
}
