package lab1proyectofinal;

import java.sql.Connection;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import lab1proyectofinal.accesoADatos.BomberoData;
import lab1proyectofinal.accesoADatos.BrigadaData;
import lab1proyectofinal.accesoADatos.Conexion;
import lab1proyectofinal.accesoADatos.CuartelData;
import lab1proyectofinal.accesoADatos.SiniestroData;
import lab1proyectofinal.entidades.Bombero;
import lab1proyectofinal.entidades.Brigada;
import lab1proyectofinal.entidades.Cuartel;
import lab1proyectofinal.entidades.Siniestro;

/**
 *
 * @author Grupo-3
 */
public class Pruebas {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        /* RECORDATORIO: Limpiar base de datos antes de ejecutar las pruebas */

        // Establecer la conexion
        Connection con = Conexion.getInstance();
        if (con == null) {
            System.out.println("[Error] No se pudo conectar a base de datos. Abortando ejecución");
            return;
        }

        // ***** CUARTEL PRUEBAS *****
        System.out.println("\n***** CUARTEL PRUEBAS *****");

        // CuartelData
        CuartelData cuartelData = new CuartelData();

        // Cuarteles
        Cuartel cuartel1 = new Cuartel("central1", "Bolivar 123", 2, 3, "266498271", "cuartel1@gmail.com");
        Cuartel cuartel2 = new Cuartel("central2", "Maipu 345", 4, 1, "266421345", "cuartel2@gmail.com");
        Cuartel cuartel3 = new Cuartel("central3", "Pringles 678", 5, 7, "266456437", "cuartel3@gmail.com");
        Cuartel cuarteles[] = new Cuartel[]{cuartel1, cuartel2, cuartel3};

        // Guardar Cuartel
        System.out.println("\n----- Guardar Cuartel -----");
        for (Cuartel cuartel : cuarteles) {
            cuartelData.guardarCuartel(cuartel);
        }

        // Buscar Cuartel
        System.out.println("\n----- Buscar Cuartel -----");
        cuartelData.buscarCuartel(cuartel1.getCodigoCuartel()); // No deberia fallar
        cuartelData.buscarCuartel(-2); // Deberia fallar

        // Modificar Cuartel
        Cuartel cuartelModificado = new Cuartel("central2Nueva", "Bolivar 341", 3, 1, "26641323", "cuartelnue@gmail.com");
        cuartelModificado.setCodigoCuartel(cuartel2.getCodigoCuartel());
        System.out.println("\n----- Modificar cuartel (con codigo " + Integer.toString(cuartelModificado.getCodigoCuartel()) + ")-----");
        cuartelData.modificarCuartel(cuartelModificado);

        //  Eliminar Cuartel
        int cualCuartelEliminar = cuartel3.getCodigoCuartel();
        System.out.println("\n----- Eliminar Cuartel (con codigo " + cualCuartelEliminar + ") -----");
        cuartelData.eliminarCuartel(cualCuartelEliminar);

        // Listar Cuartel
        System.out.println("\n----- Listar Cuartel -----");
        List<Cuartel> listaCuartel = cuartelData.listarCuarteles();
        for (Cuartel cuartel : listaCuartel) {
            System.out.println(cuartel.DebugToString());
        }
        // ***** FIN CUARTEL PRUEBAS *****

        // ***** BRIGADA PRUEBAS *****
        System.out.println("\n***** BRIGADA PRUEBAS *****");

        // Brigada Data
        BrigadaData brigadaData = new BrigadaData();

        // Brigadas
        Brigada brigada1 = new Brigada("Grupo 1", "Quien sabe", true, cuartel1);
        Brigada brigada2 = new Brigada("Grupo 2", "Quien sabe", true, cuartel1);
        Brigada brigada3 = new Brigada("Grupo 3", "Sabotear ejercitos", false, cuartel1);
        Brigada brigada4 = new Brigada("Grupo 4", "Quien sabe", true, cuartel1);
        Brigada brigada5 = new Brigada("Grupo 5", "Quien sabe", true, cuartel1);
        Brigada brigada6 = new Brigada("Grupo 6", "Quien sabe", true, cuartel1);
        Brigada brigada7 = new Brigada("Grupo 7", "Quien sabe", true, cuartel1);
        Brigada brigadas[] = new Brigada[]{brigada1, brigada2, brigada3, brigada4, brigada5, brigada6, brigada7};

        // Guardar Brigadas
        System.out.println("\n----- Guardar Brigadas -----");
        for (Brigada brigada : brigadas) {
            brigadaData.guardarBrigada(brigada);
        }

        // Buscar Brigadas
        System.out.println("\n----- Buscar Brigada -----");
        brigadaData.buscarBrigada(brigada1.getCodigoBrigada()); // No deberia fallar
        brigadaData.buscarBrigada(-2); // Deberia fallar

        // Modificar Brigada
        Brigada brigadaModificada = new Brigada("Infernal", "Traer caos y corrupcion", true, cuartel1);
        brigadaModificada.setCodigoBrigada(brigada2.getCodigoBrigada());
        System.out.println("\n----- Modificar Brigada (con codigo=" + Integer.toString(brigadaModificada.getCodigoBrigada()) + ")-----");
        brigadaData.modificarBrigada(brigadaModificada);

        // Eliminar Brigada
        int cualBrigadaEliminar = brigada3.getCodigoBrigada();
        System.out.println("\n----- Eliminar brigada (con codigo=" + cualBrigadaEliminar + ") -----");
        brigadaData.eliminarBrigada(cualBrigadaEliminar);

        // Listar Brigadas
        System.out.println("\n----- Listar Brigadas -----");
        List<Brigada> listaBrigadas = brigadaData.listarBrigadas();
        for (Brigada brigada : listaBrigadas) {
            System.out.println(brigada.DebugToString());
        }
        // ***** FIN BRIGADA PRUEBAS *****

        // ***** BOMBERO PRUEBAS *****
        System.out.println("\n***** BOMBERO PRUEBAS *****");

        // Bombero Data
        BomberoData bomberoData = new BomberoData();

        // Bomberos
        Bombero bombero1 = new Bombero(11000111, "Nahuel Lucero", "A+", LocalDate.of(1998, Month.AUGUST, 1), "11000111", brigada3);
        Bombero bombero2 = new Bombero(37666666, "Leonel Nievas", "A+", LocalDate.of(1993, Month.AUGUST, 7), "37666666", brigada3);
        Bombero bombero3 = new Bombero(40000444, "Nahuel Ochoa", "B+", LocalDate.of(1999, Month.OCTOBER, 18), "40000444", brigada3);
        Bombero bomberos[] = new Bombero[]{bombero1, bombero2, bombero3};

        // Guardar Bomberos
        System.out.println("\n----- Guardar Bomberos -----");
        for (Bombero bombero : bomberos) {
            bomberoData.guardarBombero(bombero);
        }

        // Buscar Bombero
        System.out.println("\n----- Buscar Bombero -----");
        bomberoData.buscarBombero(bombero1.getIdBombero()); // No deberia fallar
        bomberoData.buscarBombero(-2); // Deberia fallar

        // Modificar Bombero
        Bombero bomberoModificado = new Bombero(42897241, "Ramiro Moran", "O-", LocalDate.of(2000, Month.NOVEMBER, 13), "42897241", brigada3);
        bomberoModificado.setIdBombero(bombero2.getIdBombero());
        System.out.println("\n----- Modificar Bombero (con idBombero=" + Integer.toString(bomberoModificado.getIdBombero()) + ")-----");
        bomberoData.modificarBombero(bomberoModificado);

        // Eliminar Bombero
        int cualBomberoEliminar = bombero3.getIdBombero();
        System.out.println("\n----- Eliminar Bombero (con idBombero=" + cualBomberoEliminar + ") -----");
        bomberoData.eliminarBombero(cualBomberoEliminar);

        // Listar Bomberos
        System.out.println("\n----- Listar Bomberos -----");
        List<Bombero> listaBomberos = bomberoData.listarBomberos();
        for (Bombero bombero : listaBomberos) {
            System.out.println(bombero.DebugToString());
        }
        // ***** FIN BOMBERO PRUEBAS *****

        // ***** SINIESTRO PRUEBAS *****
        System.out.println("\n***** SINIESTRO PRUEBAS *****");

        // Siniestro data
        SiniestroData siniestroData = new SiniestroData();

        // Siniestros
        Siniestro siniestro1 = new Siniestro("Incendio", LocalDateTime.of(2023, Month.NOVEMBER, 1, 12, 0), 10, 10, "Mucho humo", brigada3);
        Siniestro siniestro2 = new Siniestro("Derrumbe", LocalDateTime.of(2023, Month.NOVEMBER, 2, 14, 0), 10, 10, "Mucho escombro", brigada3);
        Siniestro siniestro3 = new Siniestro("Accidente", LocalDateTime.of(2023, Month.NOVEMBER, 3, 16, 0), 10, 10, "Al desarmadero", brigada3);
        Siniestro siniestro4 = new Siniestro("NADA", LocalDateTime.of(2023, Month.NOVEMBER, 4, 16, 0), 10, 10, "NADA", brigada3, LocalDateTime.of(2023, Month.NOVEMBER, 5, 17, 0), 10);
        Siniestro siniestros[] = new Siniestro[]{siniestro1, siniestro2, siniestro3, siniestro4};

        // Guardar Siniestros
        System.out.println("\n----- Guardar Siniestros -----");
        for (Siniestro siniestro : siniestros) {
            siniestroData.guardarSiniestro(siniestro);
        }

        // Buscar Siniestros
        System.out.println("\n----- Buscar Siniestro -----");
        siniestroData.buscarSiniestro(siniestro1.getCodigoSiniestro()); // No deberia fallar
        siniestroData.buscarSiniestro(-2); // Deberia fallar

        // Modificar Siniestro
        Siniestro siniestroModificado = new Siniestro("Accidente FATAL", LocalDateTime.of(2023, Month.NOVEMBER, 3, 16, 30), -666, -666, "Se ve al diablo", brigada3);
        siniestroModificado.setCodigoSiniestro(siniestro3.getCodigoSiniestro());
        System.out.println("\n----- Modificar Siniestro (con codigoSiniestro=" + Integer.toString(siniestroModificado.getCodigoSiniestro()) + " ) -----");
        siniestroData.modificarSiniestro(siniestroModificado);

        // Eliminar Siniestro
        int cualSiniestroEliminar = siniestro3.getCodigoSiniestro();
        System.out.println("\n----- Eliminar Siniestro (con codigoSiniestro=" + cualSiniestroEliminar + ") -----");
        siniestroData.eliminarSiniestro(cualSiniestroEliminar);

        // Listar Siniestros
        System.out.println("\n----- Listar Siniestros -----");
        List<Siniestro> listaSiniestros = siniestroData.listarSiniestros();
        for (Siniestro siniestro : listaSiniestros) {
            System.out.println(siniestro.DebugToString());
        }
        // ***** FIN SINIESTRO PRUEBAS *****

    }
}
