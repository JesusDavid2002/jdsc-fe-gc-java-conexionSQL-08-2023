package EjerciciosTA30.ejerciciosMySQL;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

public class MySQL {
	
	private static Connection conexion = null;
	private String usuario = "root";
	private String password = "root";
	private String bd = "jdbc:mysql://localhost:3306/";
	private String url = "?useUnicode=true&use" + 
				"JDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";

    public void openConnection() {
        try {
        	Class.forName("com.mysql.cj.jdbc.Driver");
        	conexion = DriverManager.getConnection(bd+url, usuario, password);
        	System.out.println("Server Connected");
        	
        } catch(SQLException | ClassNotFoundException ex) {
        	System.out.println("No se ha podido encontrar la base de datos");
        	System.out.println(ex);
        }
    }
    
    public void closeConnection() {
    	try {
    		conexion.close();
    		System.out.println("Se ha finalizado la conexión con el servidor");
    	} catch(SQLException ex) {
    		Logger.getLogger(MySQL.class.getName()).log(Level.SEVERE, null, ex);
    	}
    }
    
    public String crearDB(String nombre) {
    	try {
    		String Query = "CREATE DATABASE " + nombre;
    		Statement st = conexion.createStatement();
    		st.execute(Query);
    		
    		closeConnection();
        	conexion = DriverManager.getConnection(bd + nombre + url , usuario, password);
    		
    		System.out.println("Se ha creado la base de datos " + nombre + " de forma exitosa.");
    	} catch(SQLException ex) {
    		Logger.getLogger(MySQL.class.getName()).log(Level.SEVERE, null, ex);
    	}
		return nombre;
    }

    public String crearTabla(String bd, String nombre) {
    	try {
    		String QueryBD = "USE " + bd + ";";
    		Statement stBD = conexion.createStatement();
    		stBD.execute(QueryBD);
    		
    		if(nombre.equalsIgnoreCase("peliculas")) {
    			String Query = "CREATE TABLE " + nombre + "" +
    						"(CODIGO INT PRIMARY KEY AUTO_INCREMENT, Nombre VARCHAR(100), CalificacionEdad INT)";
	    		Statement st = conexion.createStatement();
	    		st.executeUpdate(Query);
    	
    		}else if(nombre.equalsIgnoreCase("salas")) {
    			String Query = "CREATE TABLE " + nombre + "" +
						"(CODIGO INT PRIMARY KEY AUTO_INCREMENT, Nombre VARCHAR(100), Pelicula INT,"
						+ " FOREIGN KEY (Pelicula) REFERENCES peliculas(CODIGO) ON DELETE CASCADE ON UPDATE CASCADE)";
	    		Statement st = conexion.createStatement();
	    		st.executeUpdate(Query);
    		}
    		
    		System.out.println("Tabla " + nombre + " creada correctamente.");
    	} catch(SQLException ex) {
    		System.out.println(ex.getMessage());
    		System.out.println("Error creando la tabla.");
    	}
		return nombre;
    }
    
    
    public void insertarDatosPeliculas(String bd, String nombre_tabla, String nombre, String edad) {
    	try {
    		String QueryBD = "USE " + bd + ";";
    		Statement stBD = conexion.createStatement();
    		stBD.execute(QueryBD);
    		
    		String Query = "INSERT INTO " + nombre_tabla + "(Nombre, CalificacionEdad) VALUE("
    				+ "\"" + nombre + "\", "
    				+ "\"" + edad + "\"); ";
    		Statement st = conexion.createStatement();
    		st.executeUpdate(Query);
    		
    		System.out.println("Datos almacenados correctamente");
    	} catch(SQLException ex) {
    		System.out.println(ex.getMessage());
    		System.out.println("Error en el almacenamiento.");
    	}
    }
    
    public void insertarDatosSalas(String bd, String nombre_tabla, String nombre, String pelicula) {
    	try {
    		String QueryBD = "USE " + bd + ";";
    		Statement stBD = conexion.createStatement();
    		stBD.execute(QueryBD);
    		
    		String Query = "INSERT INTO " + nombre_tabla + "(Nombre, Pelicula) VALUE("
    				+ "\"" + nombre + "\", "
    				+ "\"" + pelicula + "\"); ";
    		Statement st = conexion.createStatement();
    		st.executeUpdate(Query);
    		
    		System.out.println("Datos almacenados correctamente");
    	} catch(SQLException ex) {
    		System.out.println(ex.getMessage());
    		System.out.println("Error en el almacenamiento.");
    	}
    }
    
    public void getValues(String bd, String nombre_tabla) {
    	try {
    		String QueryBD = "USE " + bd + ";";
    		Statement stBD = conexion.createStatement();
    		stBD.execute(QueryBD);
    		
    		String Query = "SELECT * FROM " + nombre_tabla;
    		Statement st = conexion.createStatement();
    		java.sql.ResultSet resultados;
    		resultados = st.executeQuery(Query);
    		
    		if(nombre_tabla.equals("peliculas")) {
    			while(resultados.next()) {
    				System.out.println("CODIGO: " + resultados.getString("CODIGO") + ", "
    								+ "Nombre: " + resultados.getString("Nombre") + ", "
    								+ "Calificacion de edad: " + resultados.getString("CalificacionEdad") + ". ");
    			}
    		} else if(nombre_tabla.equals("salas")) {
    			while(resultados.next()) {
        			System.out.println("CODIGO: " + resultados.getString("CODIGO") + ", "
        								+ "Nombre: " + resultados.getString("Nombre") + ", "
        								+ "Pelicula: " + resultados.getString("Pelicula") + ". ");
    			}
    		}
    		
    	} catch(SQLException ex) {
    		System.out.println(ex.getMessage());
    		System.out.println("Error en la adquisición de datos.");
    	}
    }
        
    public void deleteDatos(String nombre_tabla, String ID) {
    	try {
    		String Query = "DELETE FROM " + nombre_tabla + " WHERE CODIGO = \"" + ID + "\"";
    		Statement st = conexion.createStatement();
    		st.executeUpdate(Query);
    	} catch(SQLException ex) {
    		System.out.println(ex.getMessage());
    		System.out.println("Error borrando el registro especificado.");
    	}
    }
    
}
