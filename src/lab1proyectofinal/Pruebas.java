package lab1proyectofinal;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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

        // Establecer la conexion
        Connection con = Conexion.getInstance();
        if (con == null) {
            System.out.println("[Error] No se pudo conectar a base de datos. Abortando ejecución");
            return;
        }

        //------------------cuartel-------------------
        //CuartelData
        CuartelData cuartelData = new CuartelData();

        //Cuarteles
        Cuartel cuartel1 = new Cuartel(1, "central1", "Bolivar 123", 2, 3, 266498271, "cuartel1@gmail.com", true);
        Cuartel cuartel2 = new Cuartel(2, "central2", "Maipu 345", 4, 1, 266421345, "cuartel2@gmail.com", true);
        Cuartel cuartel3 = new Cuartel(3, "central3", "Pringles 678", 5, 7, 266456437, "cuartel3@gmail.com", true);
        Cuartel cuarteles[] = new Cuartel[]{cuartel1, cuartel2, cuartel3};

        //Guardar Cuartel
        System.out.println("----- Guardar Cuartel -----");
        for (Cuartel cuartel : cuarteles) {
            cuartelData.guardarCuartel(cuartel);
        }

        //Listar Cuartel
        System.out.println("\n----- Listar Cuartel -----");
        List<Cuartel> listaCuartel = cuartelData.listarCuarteles();
        for (Cuartel cuartel : listaCuartel) {
            System.out.println(cuartel.toString());
        }

        //Buscar Cuartel
        int cualcuartel;
        Cuartel cuartelEncontrado;
        System.out.println("\n----- Buscar Cuartel -----");
        //
        cualcuartel = 1;
        cuartelEncontrado = cuartelData.buscarCuartel(cualcuartel);
        System.out.println("Datos del cuartel " + cualcuartel + ":");
        if (cuartelEncontrado != null) {
            System.out.println(cuartelEncontrado.toString());
        } else {
            System.out.println("No encontrado");
        }
        //
        cualcuartel = 2;
        cuartelEncontrado = cuartelData.buscarCuartel(cualcuartel);
        System.out.println("Datos del cuartel " + cualcuartel + ":");
        if (cuartelEncontrado != null) {
            System.out.println(cuartelEncontrado.toString());
        } else {
            System.out.println("No encontrado");
        }
        //Editar Cuartel
        int cualcuartelEdi = 1;
        Cuartel cuartelModificado;
        System.out.println("\n----- Modificar cuartel (con id " + cualcuartelEdi + ")-----");
        cuartelModificado = new Cuartel(cualcuartelEdi, "central1Nueva", "Bolivar 341", 3, 1, 26641323, "cuartelnue@gmail.com", true);
        cuartelData.modificarCuartel(cuartelModificado);
        //Listar cuarteles
        System.out.println("\n----- Listar Cuarteles -----");
        listaCuartel = cuartelData.listarCuarteles();
        for (Cuartel cuartel : listaCuartel) {
            System.out.println(cuartel.toString());
        }
        //Listar Brigadas en cuartel
        System.out.println("\n----- Listar Brigadas en Cuarteles -----");
        List<Brigada> listaBrigada = cuartelData.listarBrigadasEnCuartel(1);
        for (Brigada brigada : listaBrigada) {
            System.out.println(brigada.toString());
        }
        //Listar Bomberos en el cuartel
        System.out.println("\n----- Listar Bomberos en Cuarteles -----");
        List<Bombero> listaBombero = cuartelData.listarBomberosEnCuartel(cuartel1);
        for (Bombero bombero : listaBombero) {
            System.out.println(bombero.toString());
        }
        //Eliminar Cuartel
        int cualEliminar = 3;
        System.out.println("\n----- Eliminar Cuartel (con id " + cualEliminar + ") -----");
        cuartelData.eliminarCuartel(cualEliminar);
        //Eliminar por Nombre
        //int cualEliminarNombre=2;
        //System.out.println("\n----- Eliminar Bombero (con id " + cualEliminarNombre + ") -----");
        //cuartelData.eliminarNombre(cualEliminarNombre);
        //no se si esta bien

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

        //
        // ***** SINIESTRO PRUEBAS *****
        // Siniestro data
        SiniestroData siniestroData = new SiniestroData();

        // Siniestros
        Siniestro siniestro1 = new Siniestro("Incendio", LocalDateTime.of(2023, Month.NOVEMBER, 1, 12, 0), 10, 10, "Mucho humo", 3);
        Siniestro siniestro2 = new Siniestro("Derrumbe", LocalDateTime.of(2023, Month.NOVEMBER, 2, 14, 0), 10, 10, "Mucho escombro", 3);
        Siniestro siniestro3 = new Siniestro("Accidente", LocalDateTime.of(2023, Month.NOVEMBER, 3, 16, 0), 10, 10, "Al desarmadero", 3);
        Siniestro siniestro4 = new Siniestro("NADA", LocalDateTime.of(2023, Month.NOVEMBER, 4, 16, 0), 10, 10, "NADA", 3, LocalDateTime.of(2023, Month.NOVEMBER, 3, 17, 0), 10, 4);
        Siniestro siniestros[] = new Siniestro[]{siniestro1, siniestro2, siniestro3, siniestro4};
        siniestro1.setCodigoSiniestro(1);
        siniestro2.setCodigoSiniestro(2);
        siniestro3.setCodigoSiniestro(3);

        // Guardar Siniestros
        System.out.println("\n----- Guardar Siniestros -----");
        for (Siniestro siniestro : siniestros) {
            siniestroData.guardarSiniestro(siniestro);
        }

        // Buscar Siniestros
        int cualBuscar = 1;
        Siniestro siniestroEncontrado;
        System.out.println("\n----- Busco Siniestro (con id=" + cualBuscar + ") -----");
        siniestroEncontrado = siniestroData.buscarSiniestro(cualEliminar);
        if (siniestroEncontrado != null) { // No deberia funcionar
            System.out.println(siniestroEncontrado.toString());
        } else {
            System.out.println("No encontrado");
        }

        // Listar Siniestros
        System.out.println("\n----- Listar Siniestros -----");
        List<Siniestro> listaSiniestros = siniestroData.listarSiniestros();
        for (Siniestro siniestro : listaSiniestros) {
            System.out.println(siniestro.toString());
        }

        // Listar Siniestros Sin resolucion
        System.out.println("\n----- Listar Siniestros Sin Resolucion -----");
        listaSiniestros = siniestroData.listarSiniestrosSinResolucion();
        for (Siniestro siniestro : listaSiniestros) {
            System.out.println(siniestro.toString());
        }

        // Listar Siniestros Entre dos fechas
        System.out.println("\n----- Listar Siniestros Entre 2023-11-3 y 2023-11-5 -----");
        listaSiniestros = siniestroData.listarSiniestrosEntreFechas(LocalDateTime.of(2023, Month.NOVEMBER, 3, 0, 0), LocalDateTime.of(2023, Month.NOVEMBER, 5, 23, 59));
        for (Siniestro siniestro : listaSiniestros) {
            System.out.println(siniestro.toString());
        }

        // Modificar Siniestro
        System.out.println("\n----- Modificar Siniestro (asigno brigada 2 a siniestro 1) -----");
        siniestro1.setCodigoBrigada(2);
        siniestroData.modificarSiniestro(siniestro1);
        System.out.println(siniestro1.toString());

        System.out.println("\n----- Modificar Siniestro (Asigno resolucion a siniestro 2) -----");
        // Esta forma comentada tambien funciona
        // siniestro2.setFechaResolucion(LocalDateTime.now());
        // siniestro2.setPuntuacion(4);
        // siniestroData.modificarSiniestro(siniestro2);
        siniestroData.asignarResolucion(siniestro2, LocalDateTime.now(), 4);
        System.out.println(siniestro2.toString());

        // Eliminar Bombero
        int cualSiniestroEliminar = siniestro3.getCodigoSiniestro();
        System.out.println("\n----- Eliminar Siniestro (con codigoSiniestro=" + cualSiniestroEliminar + ") -----");
        siniestroData.eliminarSiniestro(cualSiniestroEliminar);

        // Listar Siniestros (una vez mas)
        System.out.println("\n----- Listar Siniestros (una vez mas) -----");
        listaSiniestros = siniestroData.listarSiniestros();
        for (Siniestro siniestro : listaSiniestros) {
            System.out.println(siniestro.toString());
        }

        // ***** FIN SINIESTRO PRUEBAS *****
        //
    }
}
