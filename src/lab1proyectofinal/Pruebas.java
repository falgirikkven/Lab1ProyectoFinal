package lab1proyectofinal;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import lab1proyectofinal.accesoADatos.BomberoData;
import lab1proyectofinal.accesoADatos.BrigadaData;
import lab1proyectofinal.accesoADatos.Conexion;
import lab1proyectofinal.entidades.Bombero;
import lab1proyectofinal.entidades.Brigada;

/**
 *
 * @author Grupo-3
 */
public class Pruebas {

    // Este bloque de codigo es temporal
    private static void insertarCuartelyBrigada(Connection connection) {
        try {
            Statement statementCuartel = connection.createStatement();
            String sqlCuartel = "INSERT INTO `cuartel`(`codigoCuartel`, `nombreCuartel`, `direccion`, `coordenadaX`, `coordenadaY`, `telefono`, `correo`, `estado`) VALUES (1,'Manso  cuartel', 'Calle falsa 123', 0, 0, 2664666666, 'mansocuartel@fromhell.666', true);";
            statementCuartel.executeQuery(sqlCuartel);

            Statement statementBrigada = connection.createStatement();
            String sqlBrigada = "INSERT INTO `brigada`(`codigoBrigada`, `nombreBrigada`, `especialidad`, `disponible`, `codigoCuartel`, `estado`) VALUES (1,'Brigada SOS','Quien sabe', 1, 1, 1)";
            statementBrigada.executeQuery(sqlBrigada);
        } catch (SQLException e) {
            if (e.getErrorCode() != 1062) { // Ignorar datos repetidos
                e.printStackTrace();
            }
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        // Establecer la conexion
        Connection con = Conexion.getInstance();
        if (con == null) {
            System.out.println("[Error] No se pudo conectar a base de datos. Abortando ejecución");
            return;
        }

        /* NOTA: Deberia crearse cuartel y brigada antes de intentar guardar los bomberos.
           Caso contrario Ocurren Errores */
        insertarCuartelyBrigada(con); // Metodo static temporal

        //
        // ***** BRIGADA PRUEBAS *****
        // Brigada Data
        BrigadaData brigadaData = new BrigadaData();

        // Brigadas
        Brigada brigada1 = new Brigada(1, "Grupo 1", "Quien sabe", true, 1, true);
        Brigada brigada2 = new Brigada(2, "Grupo 2", "Quien sabe", true, 1, true);
        Brigada brigada3 = new Brigada(3, "Grupo 3", "Sabotear ejercitos", false, 1, true);
        Brigada brigada4 = new Brigada(4, "Grupo 4", "Quien sabe", true, 1, true);
        Brigada brigada5 = new Brigada(5, "Grupo 5", "Quien sabe", true, 1, false);
        Brigada brigada6 = new Brigada(6, "Grupo 6", "Quien sabe", true, 1, true);
        Brigada brigada7 = new Brigada(7, "Grupo 7", "Quien sabe", true, 1, true);
        Brigada brigadas[] = new Brigada[]{brigada1, brigada2, brigada3, brigada4, brigada5, brigada6, brigada7};

        // Guardar Brigadas
        System.out.println("\n----- Guardar Brigadas -----");
        for (Brigada brigada : brigadas) {
            brigadaData.guardarBrigada(brigada);
        }

        // Listar Brigadas
        System.out.println("\n----- Listar Brigadas -----");
        List<Brigada> listaBrigadas = brigadaData.listarBrigadas();
        for (Brigada brigada : listaBrigadas) {
            System.out.println(brigada.toString());
        }

        // Buscar Brigadas
        int cualBrigadaBuscar;
        Brigada brigadaEncontrada;
        System.out.println("\n----- Buscar Brigada -----");
        //
        cualBrigadaBuscar = brigada2.getCodigoBrigada();
        brigadaEncontrada = brigadaData.buscarBrigada(cualBrigadaBuscar);
        System.out.println("Datos de la brigada " + cualBrigadaBuscar + ":");
        if (brigadaEncontrada != null) { // Deberia funcionar
            System.out.println(brigadaEncontrada.toString());
        } else {
            System.out.println("No encontrada");
        }
        cualBrigadaBuscar = -666;
        brigadaEncontrada = brigadaData.buscarBrigada(cualBrigadaBuscar);
        System.out.println("Datos de la brigada " + cualBrigadaBuscar + ":");
        if (brigadaEncontrada != null) { // No deberia funcionar
            System.out.println(brigadaEncontrada.toString());
        } else {
            System.out.println("No encontrada");
        }

        // Editar Brigada
        int cualBrigadaEditar = brigada5.getCodigoBrigada();
        Brigada brigadaModificada;
        System.out.println("\n----- Modificar Brigada (con codigo=" + cualBrigadaEditar + ")-----");
        brigadaModificada = new Brigada(cualBrigadaEditar, "Grupo 5", "Abandonar", false, 1, false);
        brigadaData.modificarBrigada(brigadaModificada);
        // Eliminar Brigada
        int cualBrigadaEliminar = brigada3.getCodigoBrigada();
        System.out.println("\n----- Eliminar brigada (con codigo=" + cualBrigadaEliminar + ") -----");
        brigadaData.eliminarBrigada(cualBrigadaEliminar);

        // Listar Brigadas (post ediciones)
        System.out.println("\n----- Listar Brigadas (post ediciones) -----");
        listaBrigadas = brigadaData.listarBrigadas();
        for (Brigada brigada : listaBrigadas) {
            System.out.println(brigada.toString());
        }
        // ***** FIN BRIGADA PRUEBAS *****
        //

        //
        // ***** BOMBERO PRUEBAS *****
        // Bombero Data
        BomberoData bomberoData = new BomberoData();

        // Bomberos
        Bombero bombero1 = new Bombero(1, 11000111, "Nahuel Lucero", "A+", LocalDate.of(1998, Month.AUGUST, 1), 11000111, 1, true);
        Bombero bombero2 = new Bombero(2, 37666666, "Leonel Nievas", "A+", LocalDate.of(1993, Month.AUGUST, 7), 37666666, 1, true);
        Bombero bombero3 = new Bombero(3, 40000444, "Nahuel Ochoa", "B+", LocalDate.of(1999, Month.OCTOBER, 18), 40000444, 1, true);
        Bombero bomberos[] = new Bombero[]{bombero1, bombero2, bombero3};

        // Guardar Bomberos
        System.out.println("\n----- Guardar Bomberos -----");
        for (Bombero bombero : bomberos) {
            bomberoData.guardarBombero(bombero);
        }

        // Listar Bomberos
        System.out.println("\n----- Listar Bomberos -----");
        List<Bombero> listaBomberos = bomberoData.listarBomberos();
        for (Bombero bombero : listaBomberos) {
            System.out.println(bombero.toString());
        }

        // Buscar Bombero
        int cualBomberoBuscar;
        Bombero bomberoEncontrado;
        System.out.println("\n----- Buscar Bombero -----");
        //
        cualBomberoBuscar = bombero2.getIdBombero();
        bomberoEncontrado = bomberoData.buscarBombero(cualBomberoBuscar);
        System.out.println("Datos del bombero " + cualBomberoBuscar + ":");
        if (bomberoEncontrado != null) { // Deberia funcionar
            System.out.println(bomberoEncontrado.toString());
        } else {
            System.out.println("No encontrado");
        }
        //
        cualBomberoBuscar = -666;
        bomberoEncontrado = bomberoData.buscarBombero(cualBomberoBuscar);
        System.out.println("Datos del bombero " + cualBomberoBuscar + ":");
        if (bomberoEncontrado != null) { // No deberia funcionar
            System.out.println(bomberoEncontrado.toString());
        } else {
            System.out.println("No encontrado");
        }

        // Editar Bombero
        int cualBomberoEditar = bombero2.getIdBombero();
        Bombero bomberoModificado;
        System.out.println("\n----- Modificar Bombero (con idBombero=" + cualBomberoEditar + ")-----");
        bomberoModificado = new Bombero(cualBomberoEditar, 42897241, "Ramiro Moran", "O-", LocalDate.of(2000, Month.NOVEMBER, 13), 42897241, 1, true);
        bomberoData.modificarBombero(bomberoModificado);

        // Eliminar Bombero
        int cualBomberoEliminar = bombero2.getIdBombero();
        System.out.println("\n----- Eliminar Bombero (con idBombero=" + cualBomberoEliminar + ") -----");
        bomberoData.eliminarBombero(cualBomberoEliminar);

        // Listar Bomberos para ver los efectos de la edición
        System.out.println("\n----- Listar Bomberos -----");
        listaBomberos = bomberoData.listarBomberos();
        for (Bombero bombero : listaBomberos) {
            System.out.println(bombero.toString());
        }
        // ***** FIN BOMBERO PRUEBAS *****
        //

    }
}
