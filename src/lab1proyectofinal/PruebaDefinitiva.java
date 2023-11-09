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
public class PruebaDefinitiva {

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

        // Clases Data
        CuartelData cuartelData = new CuartelData();
        BrigadaData brigadaData = new BrigadaData();
        BomberoData bomberoData = new BomberoData();
        SiniestroData siniestroData = new SiniestroData();

        // Cuarteles
        Cuartel cuartel1 = new Cuartel("cuartel1", "direccion 101", 1, 1, "2664101010", "cuartel1@gmail.com");
        Cuartel cuartel2 = new Cuartel("cuartel2", "direccion 202", 2, 2, "2664202020", "cuartel2@gmail.com");
        Cuartel cuartel3 = new Cuartel("cuartel3", "direccion 303", 3, 3, "2664303030", "cuartel3@gmail.com");
        Cuartel cuartelRep = new Cuartel("cuartel1", "direccion 404", 4, 4, "2664404040", "cuartel4@gmail.com");  // cuartel con nombre repetido
        Cuartel cuarteles[] = new Cuartel[]{cuartel1, cuartel2, cuartel3, cuartelRep};

        // Siniestros
        Siniestro siniestro1 = new Siniestro("Incendio", LocalDateTime.of(2023, Month.NOVEMBER, 1, 12, 0), 10, 10, "Mucho humo");
        Siniestro siniestro2 = new Siniestro("Derrumbe", LocalDateTime.of(2023, Month.NOVEMBER, 2, 14, 0), 20, 20, "Mucho escombro");
        Siniestro siniestro3 = new Siniestro("Accidente", LocalDateTime.of(2023, Month.NOVEMBER, 3, 16, 0), 30, 30, "Al desarmadero");
        Siniestro siniestro4 = new Siniestro("Accidente", LocalDateTime.of(2023, Month.NOVEMBER, 15, 12, 0), 40, 40, "Al desarmadero");      // siniestro con fechaI posterior a la actual
        Siniestro siniestro5 = new Siniestro("Accidente", LocalDateTime.of(2023, Month.NOVEMBER, 5, 14, 0), 50, 50, "Al desarmadero", LocalDateTime.of(2023, Month.NOVEMBER, 5, 14, 0), -1);    // siniestro con fechaR no "nula", pero puntuación "nula" 
        Siniestro siniestro6 = new Siniestro("Accidente", LocalDateTime.of(2023, Month.NOVEMBER, 6, 16, 0), 60, 60, "Al desarmadero", null, 5);       // siniestro con puntuación no nula, pero fechaR nula
        Siniestro siniestro7 = new Siniestro("Accidente", LocalDateTime.of(2023, Month.NOVEMBER, 7, 16, 0), 70, 70, "Al desarmadero", LocalDateTime.of(2023, Month.NOVEMBER, 5, 14, 0), 10);    // siniestro con fechaI posterior a la fechaR
        Siniestro siniestro8 = new Siniestro("Accidente", LocalDateTime.of(2023, Month.NOVEMBER, 8, 16, 0), 80, 80, "Al desarmadero", LocalDateTime.of(2023, Month.NOVEMBER, 30, 14, 0), 11);    // siniestro con puntuación fuera del rango válido
        Siniestro siniestros[] = new Siniestro[]{siniestro1, siniestro2, siniestro3, siniestro4, siniestro5, siniestro6, siniestro7, siniestro8};

        // Brigadas
        Brigada brigada1 = new Brigada("Grupo 1", "Quien sabe", true, cuartel1);
        Brigada brigada2 = new Brigada("Grupo 2", "Quien sabe", true, cuartel2);
        Brigada brigada3 = new Brigada("Grupo 3", "Quien sabe", true, cuartel1);
        Brigada brigada4 = new Brigada("Grupo 4", "Quien sabe", true, cuartel3);
        Brigada brigadaRep = new Brigada("Grupo 1", "Quien sabe", true, cuartel1);      // brigada con nombre repetirdo 
        Brigada brigadas[] = new Brigada[]{brigada1, brigada2, brigada3, brigada4, brigadaRep};

        // Bomberos
        Bombero bombero1 = new Bombero(11000000, "nombre1", "A+", LocalDate.of(1998, Month.AUGUST, 1), "2679110001", brigada1);
        Bombero bombero2 = new Bombero(22000000, "nombre2", "A+", LocalDate.of(1998, Month.AUGUST, 2), "2679110002", brigada1);
        Bombero bombero3 = new Bombero(33000000, "nombre3", "A+", LocalDate.of(1998, Month.AUGUST, 3), "2679110003", brigada1);
        Bombero bombero4 = new Bombero(44000000, "nombre4", "A+", LocalDate.of(1998, Month.AUGUST, 4), "2679110004", brigada1);
        Bombero bombero5 = new Bombero(55000000, "nombre5", "A+", LocalDate.of(1998, Month.AUGUST, 5), "2679110005", brigada1);
        Bombero bombero6 = new Bombero(66000000, "nombre6", "A+", LocalDate.of(1998, Month.AUGUST, 6), "2679110006", brigada1);     // 6to bombero que se intenta ingresar a la misma brigada, sin que en esta última se haya dado de baja a ningún bombero anterior
        Bombero bombero7 = new Bombero(77000000, "nombre7", "A+", LocalDate.of(1998, Month.AUGUST, 7), "2679110007", brigada2);
        Bombero bombero8 = new Bombero(88000000, "nombre8", "A+", LocalDate.of(1998, Month.AUGUST, 8), "2679110008", brigada2);
        Bombero bombero9 = new Bombero(99000000, "nombre9", "A+", LocalDate.of(1998, Month.AUGUST, 9), "2679110009", brigada2);
        Bombero bombero10 = new Bombero(10100000, "nombre10", "A+", LocalDate.of(1998, Month.AUGUST, 10), "2679110010", brigada2);
        Bombero bombero11 = new Bombero(11110000, "nombre11", "A+", LocalDate.of(1998, Month.AUGUST, 11), "2679110011", brigada2);
        Bombero bombero12 = new Bombero(12120000, "nombre12", "A+", LocalDate.of(1998, Month.AUGUST, 12), "2679110012", brigada3);
        Bombero bombero13 = new Bombero(13130000, "nombre13", "A+", LocalDate.of(1998, Month.AUGUST, 13), "2679110013", brigada3);      // bombero a dar de baja
        Bombero bomberoRep = new Bombero(11000000, "nombre12", "A+", LocalDate.of(1998, Month.AUGUST, 12), "2679110012", brigada3);    // bombero con dni repetido        
        Bombero bomberos[] = new Bombero[]{bombero1, bombero2, bombero3, bombero4, bombero5, bombero6, bombero7, bombero8, bombero9, bombero10, bombero11, bombero12, bombero13, bomberoRep};

        // para cubrir casos en los que no hay brigadas disponibles para atender emergencias
        cuartelData.crearFalsoCuartel();
        brigadaData.crearFalsaBrigada();
        
        // ************ GUARDAR DATOS *************
        
        System.out.println("\n--------------- GUARDADO DE DATOS ---------------");

        System.out.println("\n----- Guardar cuarteles -----");
        for (Cuartel cuartel : cuarteles) {
            cuartelData.guardarCuartel(cuartel);
        }
        
        System.out.println("\n----- Guardar siniestros -----");
        for (Siniestro siniestro : siniestros) {
            siniestroData.guardarSiniestro(siniestro);
        }

        System.out.println("\n----- Guardar brigadas -----");
        for (Brigada brigada : brigadas) {
            brigadaData.guardarBrigada(brigada);
        }

        System.out.println("\n----- Guardar Bomberos -----");
        for (Bombero bombero : bomberos) {
            bomberoData.guardarBombero(bombero);
        }
        
        // ************ FIN GUARDAR DATOS *************
        
        
        // ************ LISTAR DATOS *************
        
        System.out.println("\n--------------- LISTAR DATOS ---------------");
        
        System.out.println("\n----- Listar todos los bomberos -----");
        bomberoData.listarBomberos();
        
        System.out.println("\n----- Listar todos los bomberos del cuartel 'cuartel1' -----");
        cuartelData.listarBomberosDelCuartel(cuartel1);
        
        System.out.println("\n----- Listar todos los bomberos de la brigada 'brigada3' -----");
        brigadaData.listarBomberosDeBrigada(brigada3);
        
        System.out.println("\n----- Listar todos los siniestros -----");
        siniestroData.listarSiniestros();
        
        System.out.println("\n----- Listar todos los siniestros resueltos -----");
        siniestroData.listarSiniestrosResueltos();
        
        System.out.println("\n----- Listar todos los siniestros NO resueltos -----");
        siniestroData.listarSiniestrosSinResolucion();
        
        System.out.println("\n----- Listar todos los siniestros que se produjeron y terminaron entre 2 momentos en el tiempo -----");
        siniestroData.listarSiniestrosEntreFechas(LocalDateTime.of(2023, Month.NOVEMBER, 1, 12, 0), LocalDateTime.of(2023, Month.NOVEMBER, 2, 14, 0));
        
        System.out.println("\n----- Listar todos los siniestros que se produjeron entre 2 momentos en el tiempo -----");
        siniestroData.listarSiniestrosEntreFechas(LocalDateTime.of(2023, Month.NOVEMBER, 1, 12, 0), LocalDateTime.of(2023, Month.NOVEMBER, 2, 14, 0));

        System.out.println("\n----- Listar todas las brigadas -----");
        brigadaData.listarBrigadas();
        
        System.out.println("\n----- Listar todas las brigadas del cuartel 'cuartel1' -----");
        cuartelData.listarBrigadasDelCuartel(cuartel1);
        
        System.out.println("\n----- Listar todas las brigadas disponibles (que tienen 5 miembros activos y no están tratando un siniestro) -----");
        brigadaData.listarBrigadasDisponibles();
        
        System.out.println("\n----- Listar todas las brigadas NO disponibles (que tienen menos de 5 miembros activos o están tratando un siniestro) -----");
        brigadaData.listarBrigadasNoDisponibles();
        
        System.out.println("\n----- Listar todas las brigadas incompletas (que tienen menos de 5 miembros activos) -----");
        brigadaData.listarBrigadasIncompletas();
        
        System.out.println("\n----- Listar todos los cuarteles -----");
        cuartelData.listarCuarteles();
        
        // ************ FIN LISTAR DATOS *************
        
        
        // ************ BUSCAR DATOS *************
        
        int codigoCuartelBuscar = cuartel3.getCodigoCuartel();
        int codigoBrigadaBuscar = brigada4.getCodigoBrigada();
        int idBomberoBuscar = bombero13.getIdBombero();
        int dniBomberoBuscar = bombero13.getDni();
        int codigoSiniestroBuscar = siniestro3.getCodigoSiniestro();
        String nombreCuartelBuscar = cuartel3.getNombreCuartel();
        String nombreBrigadaBuscar = brigada4.getNombreBrigada();        
        
        System.out.println("\n----- Buscar cuartel por código ("+codigoCuartelBuscar+")-----");
        cuartelData.buscarCuartel(codigoCuartelBuscar);
        
        System.out.println("\n----- Buscar cuartel por nombre ("+nombreCuartelBuscar+")-----");
        cuartelData.buscarCuartelPorNombre(nombreCuartelBuscar);
        
        System.out.println("\n----- Buscar brigada por código ("+codigoBrigadaBuscar+")-----");
        brigadaData.buscarBrigada(codigoBrigadaBuscar);
        
        System.out.println("\n----- Buscar brigada por nombre ("+nombreBrigadaBuscar+")-----");
        brigadaData.buscarBrigadaPorNombre(nombreBrigadaBuscar);
        
        System.out.println("\n----- Buscar bombero por id ("+idBomberoBuscar+")-----");
        bomberoData.buscarBombero(idBomberoBuscar);
        
        System.out.println("\n----- Buscar bombero por dni ("+dniBomberoBuscar+")-----");
        bomberoData.buscarBomberoPorDni(dniBomberoBuscar);
        
        System.out.println("\n----- Buscar siniestro ("+codigoSiniestroBuscar+")-----");
        siniestroData.buscarSiniestro(codigoSiniestroBuscar);
        
        
        
//        // verificar la modificación de los objetos 
//        for (Bombero bombero : bomberos) {
//            System.out.println(bombero);
//        }
//        for (Brigada brigada : brigadas) {
//            System.out.println(brigada);
//        }
//        for (Siniestro siniestro : siniestros) {
//            System.out.println(siniestro);
//        }
//        for (Cuartel cuartel : cuarteles) {
//            System.out.println(cuartel);
//        }
    }
}
