package lab1proyectofinal;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import lab1proyectofinal.accesoADatos.BomberoData;
import lab1proyectofinal.accesoADatos.Conexion;
import lab1proyectofinal.entidades.Bombero;

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
        } catch (SQLException ex) {
            Logger.getLogger(Pruebas.class.getName()).log(Level.SEVERE, null, ex);
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

        // Bombero Data
        BomberoData bomberoData = new BomberoData();

        // Bomberos
        Bombero bombero1 = new Bombero(1, 11000111, "Nahuel Lucero", "A+", LocalDate.of(1998, Month.AUGUST, 1), 11000111, 1, true);
        Bombero bombero2 = new Bombero(2, 37666666, "Leonel Nievas", "A+", LocalDate.of(1993, Month.AUGUST, 7), 37666666, 1, true);
        Bombero bombero3 = new Bombero(3, 40000444, "Nahuel Ochoa", "B+", LocalDate.of(1999, Month.OCTOBER, 18), 40000444, 1, true);
        Bombero bomberos[] = new Bombero[]{bombero1, bombero2, bombero3};

        // Guardar Bomberos
        System.out.println("----- Guardar Bomberos -----");
        for (Bombero bombero : bomberos) {
            bomberoData.guardarBombero(bombero);
        }

        if (true) {
            return;
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
        cualBomberoBuscar = 2;
        bomberoEncontrado = bomberoData.buscarBombero(cualBomberoBuscar);
        System.out.println("Datos del bombero " + cualBomberoBuscar + ":");
        if (bomberoEncontrado != null) { // Deberia funcionar
            System.out.println(bomberoEncontrado.toString());
        } else {
            System.out.println("No encontrado");
        }
        //
        cualBomberoBuscar = 666;
        bomberoEncontrado = bomberoData.buscarBombero(cualBomberoBuscar);
        System.out.println("Datos del bombero " + cualBomberoBuscar + ":");
        if (bomberoEncontrado != null) { // No deberia funcionar
            System.out.println(bomberoEncontrado.toString());
        } else {
            System.out.println("No encontrado");
        }

        // Editar Bombero
        int cualBomberoEditar = 2;
        Bombero bomberoModificado;
        System.out.println("\n----- Modificar Bombero (con id " + cualBomberoEditar + ")-----");
        bomberoModificado = new Bombero(cualBomberoEditar, 42897241, "Ramiro Moran", "O-", LocalDate.of(2000, Month.NOVEMBER, 13), 42897241, 1, true);
        bomberoData.modificarBombero(bomberoModificado);

        // Listar Bomberos para ver los efectos de la edición
        System.out.println("\n----- Listar Bomberos -----");
        listaBomberos = listaBomberos = bomberoData.listarBomberos();
        for (Bombero bombero : listaBomberos) {
            System.out.println(bombero.toString());
        }

        // Eliminar Bombero
        int cualBomberoEliminar = 2;
        System.out.println("\n----- Eliminar Bombero (con id " + cualBomberoEliminar + ") -----");
        bomberoData.eliminarBombero(cualBomberoEliminar);

    }
}
