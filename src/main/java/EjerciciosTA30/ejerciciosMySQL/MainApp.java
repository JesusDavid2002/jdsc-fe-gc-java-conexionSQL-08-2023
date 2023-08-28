package EjerciciosTA30.ejerciciosMySQL;

public class MainApp {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MySQL conexion = new MySQL();
		conexion.openConnection();
		
		String bd = conexion.crearDB("TA31Ejercicio4");
		String tabla;
		String tabla2;
		String nombre = "Bob Esponja";
		String edad = "11";
		String pelicula = "1";
		
		tabla = conexion.crearTabla(bd, "peliculas");
		tabla2 = conexion.crearTabla(bd, "salas");
		conexion.insertarDatosPeliculas(bd, tabla, nombre, edad);
		conexion.insertarDatosSalas(bd, tabla2, nombre, pelicula);
		conexion.deleteDatos(tabla2, "2");
		conexion.getValues(bd, tabla);
		System.out.println("---------------------");
		conexion.getValues(bd, tabla2);
	}

}
