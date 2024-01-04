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
import lab1proyectofinal.accesoADatos.EmergenciaData;
import lab1proyectofinal.entidades.Bombero;
import lab1proyectofinal.entidades.Brigada;
import lab1proyectofinal.entidades.Cuartel;
import lab1proyectofinal.entidades.Emergencia;

/**
 *
 * @author Grupo-3
 */
public class PruebasN8A {

    /* Utilitarios */
    public static void imprimirListaBombero(List<Bombero> lista) {
        if (lista.isEmpty()) {
            System.out.println("No se ha encontrado ningún elemento");
        }
        for (Bombero bom : lista) {
            System.out.println(bom.debugToString());
        }
    }

    public static void imprimirListaSiniestro(List<Emergencia> lista) {
        if (lista.isEmpty()) {
            System.out.println("No se ha encontrado ningún elemento");
        }
        for (Emergencia sin : lista) {
            System.out.println(sin.debugToString());
        }
    }

    public static void imprimirListaBrigada(List<Brigada> lista) {
        if (lista.isEmpty()) {
            System.out.println("No se ha encontrado ningún elemento");
        }
        for (Brigada bri : lista) {
            System.out.println(bri.debugToString());
        }
    }

    public static void imprimirListaCuartel(List<Cuartel> lista) {
        if (lista.isEmpty()) {
            System.out.println("No se ha encontrado ningún elemento");
        }
        for (Cuartel cru : lista) {
            System.out.println(cru.debugToString());
        }
    }

    public static void imprimirResultadoBombero(Bombero bombero) {
        if (bombero != null) {
            System.out.println("Mostrando:\n" + bombero.debugToString());
        }
    }

    public static void imprimirResultadoSiniestro(Emergencia siniestro) {
        if (siniestro != null) {
            System.out.println("Mostrando:\n" + siniestro.debugToString());
        }
    }

    public static void imprimirResultadoBrigada(Brigada brigada) {
        if (brigada != null) {
            System.out.println("Mostrando:\n" + brigada.debugToString());
        }
    }

    public static void imprimirResultadoCuartel(Cuartel cuartel) {
        if (cuartel != null) {
            System.out.println("Mostrando:\n" + cuartel.debugToString());
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
        
        EmergenciaData.inicializar();

        // Clases Data
        CuartelData cuartelData = new CuartelData();
        BrigadaData brigadaData = new BrigadaData();
        BomberoData bomberoData = new BomberoData();
        EmergenciaData siniestroData = new EmergenciaData();

        // List de cada entidad
        List<Cuartel> listaCuartel;
        List<Brigada> listaBrigada;
        List<Bombero> listaBombero;
        List<Emergencia> listaSiniestro;

        // Objetos "vacíos" de cada entidad
        Cuartel cuartelAux;
        Brigada brigadaAux;
        Bombero bomberoAux;
        Emergencia siniestroAux;

        // Cuarteles
        Cuartel cuartel2 = new Cuartel("cuartel2", "direccion 202", 2, 2, "2664202020", "cuartel2@gmail.com");
        Cuartel cuartel3 = new Cuartel("cuartel3", "direccion 303", 3, 3, "2664303030", "cuartel3@gmail.com");
        Cuartel cuartel4 = new Cuartel("cuartel4", "direccion 404", 4, 4, "2664404040", "cuartel4@gmail.com");
        Cuartel cuartelRep = new Cuartel("cuartel2", "direccion 404", 4, 4, "2664404040", "cuartel4@gmail.com");  // cuartel con nombre repetido
        Cuartel cuarteles[] = new Cuartel[]{cuartel2, cuartel3, cuartel4, cuartelRep};

        // Siniestros
        Emergencia siniestro1 = new Emergencia("Incendio", LocalDateTime.of(2023, Month.NOVEMBER, 1, 12, 0), 10, 10, "Mucho humo");
        Emergencia siniestro2 = new Emergencia("Derrumbe", LocalDateTime.of(2023, Month.NOVEMBER, 2, 14, 0), 20, 20, "Mucho escombro");
        Emergencia siniestro3 = new Emergencia("Accidente", LocalDateTime.of(2023, Month.NOVEMBER, 3, 16, 0), 30, 30, "Al desarmadero");
        Emergencia siniestro4 = new Emergencia("Accidente", LocalDateTime.of(2023, Month.NOVEMBER, 20, 12, 0), 40, 40, "Al desarmadero");      // siniestro con fechaI posterior a la actual
        //Siniestro siniestro5 = new Emergencia("Accidente", LocalDateTime.of(2023, Month.NOVEMBER, 5, 14, 0), 50, 50, "Al desarmadero", LocalDateTime.of(2023, Month.NOVEMBER, 5, 14, 0), -1);    // siniestro con fechaR no "nula", pero puntuación "nula" 
        //Siniestro siniestro6 = new Emergencia("Accidente", LocalDateTime.of(2023, Month.NOVEMBER, 6, 16, 0), 60, 60, "Al desarmadero", null, 5);       // siniestro con puntuación no nula, pero fechaR nula
        //Siniestro siniestro7 = new Emergencia("Accidente", LocalDateTime.of(2023, Month.NOVEMBER, 7, 16, 0), 70, 70, "Al desarmadero", LocalDateTime.of(2023, Month.NOVEMBER, 5, 14, 0), 10);    // siniestro con fechaI posterior a la fechaR
        //Siniestro siniestro8 = new Emergencia("Accidente", LocalDateTime.of(2023, Month.NOVEMBER, 8, 16, 0), 80, 80, "Al desarmadero", LocalDateTime.of(2023, Month.NOVEMBER, 9, 14, 0), 11);    // siniestro con puntuación fuera del rango válido
        //Siniestro siniestro9 = new Emergencia("Accidente", LocalDateTime.of(2023, Month.NOVEMBER, 4, 16, 0), 70, 70, "Al desarmadero", LocalDateTime.of(2023, Month.NOVEMBER, 30, 14, 0), 10);    // siniestro con fechaR posterior a la fecha actual
        //Siniestro siniestros[] = new Emergencia[]{siniestro1, siniestro2, siniestro3, siniestro4, siniestro5, siniestro6, siniestro7, siniestro8, siniestro9};
        Emergencia siniestros[] = new Emergencia[]{siniestro1, siniestro2, siniestro3, siniestro4};

        // Brigadas
        Brigada brigada2 = new Brigada("Grupo 2", "Quien sabe", true, cuartel2);
        Brigada brigada3 = new Brigada("Grupo 3", "Quien sabe", false, cuartel3);       // brigada no disponible
        Brigada brigada4 = new Brigada("Grupo 4", "Quien sabe", true, cuartel2);
        Brigada brigada5 = new Brigada("Grupo 5", "Quien sabe", true, cuartel4);
        Brigada brigadaRep = new Brigada("Grupo 2", "Quien sabe", true, cuartel3);      // brigada con nombre repetirdo 
        Brigada brigadas[] = new Brigada[]{brigada2, brigada3, brigada4, brigada5, brigadaRep};

        // Bomberos
        Bombero bombero1 = new Bombero(11000000, "nombre1", "A+", LocalDate.of(1998, Month.AUGUST, 1), "2679110001", brigada2);
        Bombero bombero2 = new Bombero(22000000, "nombre2", "A+", LocalDate.of(1998, Month.AUGUST, 2), "2679110002", brigada2);
        Bombero bombero3 = new Bombero(33000000, "nombre3", "A+", LocalDate.of(1998, Month.AUGUST, 3), "2679110003", brigada2);
        Bombero bombero4 = new Bombero(44000000, "nombre4", "A+", LocalDate.of(1998, Month.AUGUST, 4), "2679110004", brigada2);
        Bombero bombero5 = new Bombero(55000000, "nombre5", "A+", LocalDate.of(1998, Month.AUGUST, 5), "2679110005", brigada2);
        Bombero bombero6 = new Bombero(66000000, "nombre6", "A+", LocalDate.of(1998, Month.AUGUST, 6), "2679110006", brigada2);     // 6to bombero que se intenta ingresar a la misma brigada, sin que en esta última se haya dado de baja a ningún bombero anterior
        Bombero bombero7 = new Bombero(77000000, "nombre7", "A+", LocalDate.of(1998, Month.AUGUST, 7), "2679110007", brigada3);
        Bombero bombero8 = new Bombero(88000000, "nombre8", "A+", LocalDate.of(1998, Month.AUGUST, 8), "2679110008", brigada3);
        Bombero bombero9 = new Bombero(99000000, "nombre9", "A+", LocalDate.of(1998, Month.AUGUST, 9), "2679110009", brigada3);
        Bombero bombero10 = new Bombero(10100000, "nombre10", "A+", LocalDate.of(1998, Month.AUGUST, 10), "2679110010", brigada3);
        Bombero bombero11 = new Bombero(11110000, "nombre11", "A+", LocalDate.of(1998, Month.AUGUST, 11), "2679110011", brigada3);
        Bombero bombero12 = new Bombero(12120000, "nombre12", "A+", LocalDate.of(1998, Month.AUGUST, 12), "2679110012", brigada4);
        Bombero bombero13 = new Bombero(13130000, "nombre13", "A+", LocalDate.of(1998, Month.AUGUST, 13), "2679110013", brigada4);      // bombero a dar de baja
        Bombero bomberoRep = new Bombero(11000000, "nombre12", "A+", LocalDate.of(1998, Month.AUGUST, 12), "2679110012", brigada4);    // bombero con dni repetido        
        Bombero bomberos[] = new Bombero[]{bombero1, bombero2, bombero3, bombero4, bombero5, bombero6, bombero7, bombero8, bombero9, bombero10, bombero11, bombero12, bombero13, bomberoRep};

        // otras variables auxiliares
        int codigoCuartelBuscar;
        int codigoBrigadaBuscar;
        int idBomberoBuscar;
        int dniBomberoBuscar;
        int codigoSiniestroBuscar;
        String nombreCuartelBuscar;
        String nombreBrigadaBuscar;
        LocalDateTime fecha1 = LocalDateTime.of(2023, Month.NOVEMBER, 2, 14, 0);
        LocalDateTime fecha2 = LocalDateTime.of(2023, Month.NOVEMBER, 10, 14, 0);

        // para cubrir casos en los que no hay brigadas disponibles para atender emergencias
        System.out.println("\n--------------- CARGADO DE CUARTEL Y BRIGADA NULL ---------------\n");
        //cuartelData.insertarCuartelNull();
        //brigadaData.insertarBrigadaNull();

        //
        // ************ GUARDAR DATOS *************
        //
        System.out.println("\n\n\n--------------- GUARDADO DE DATOS ---------------");

        System.out.println("\n----- Guardar cuarteles -----");
        for (Cuartel cuartel : cuarteles) {
            cuartelData.guardarCuartel(cuartel);
        }

        System.out.println("\n----- Guardar siniestros -----");
        for (Emergencia siniestro : siniestros) {
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

        System.out.println("\n--------------- FIN GUARDADO DE DATOS ---------------\n\n");
        //
        // ************ FIN GUARDAR DATOS *************
        //

        //
        // ************ LISTAR DATOS *************
        //
        System.out.println("\n--------------- LISTAR DATOS (PRIMERA PARTE) ---------------");

        System.out.println("\n----- Listar todos los bomberos -----");
        listaBombero = bomberoData.listarBomberos();
        imprimirListaBombero(listaBombero);

        System.out.println("\n----- Listar todos los bomberos del cuartel '" + cuartel2.getNombreCuartel() + "' -----");
        listaBombero = cuartelData.listarBomberosDelCuartel(cuartel2);
        imprimirListaBombero(listaBombero);

        System.out.println("\n----- Listar todos los bomberos de la brigada '" + brigada4.getNombreBrigada() + "' -----");
        listaBombero = brigadaData.listarBomberosDeBrigada(brigada4);
        imprimirListaBombero(listaBombero);

        System.out.println("\n----- Listar todos los siniestros -----");
        listaSiniestro = siniestroData.listarSiniestros();
        imprimirListaSiniestro(listaSiniestro);

        System.out.println("\n----- Listar todos los siniestros resueltos -----");
        listaSiniestro = siniestroData.listarSiniestrosResueltos();
        imprimirListaSiniestro(listaSiniestro);

        System.out.println("\n----- Listar todos los siniestros NO resueltos -----");
        listaSiniestro = siniestroData.listarSiniestrosSinResolucion();
        imprimirListaSiniestro(listaSiniestro);

        System.out.println("\n----- Listar todos los siniestros que se iniciaron y terminaron en el siguiente intervalo (" + fecha1 + " hasta " + fecha2 + ")-----");
        listaSiniestro = siniestroData.listarSiniestrosInicioFinEntreFechas(fecha1, fecha2);
        imprimirListaSiniestro(listaSiniestro);

        System.out.println("\n----- Listar todos los siniestros que se iniciaron en el siguiente intervalo (" + fecha1 + " hasta " + fecha2 + ")-----");
        listaSiniestro = siniestroData.listarSiniestrosEntreFechas(fecha1, fecha2);
        imprimirListaSiniestro(listaSiniestro);

        System.out.println("\n----- Listar todas las brigadas -----");
        listaBrigada = brigadaData.listarBrigadas();
        imprimirListaBrigada(listaBrigada);

        System.out.println("\n----- Listar todas las brigadas del cuartel '" + cuartel2.getNombreCuartel() + "' -----");
        listaBrigada = cuartelData.listarBrigadasDelCuartel(cuartel2.getCodigoCuartel());
        imprimirListaBrigada(listaBrigada);

        System.out.println("\n----- Listar todas las brigadas asignables (disponible, con cantidad de bomberos igual a 5 y la brigada no está atendiendo una emergencia) -----");
        listaBrigada = brigadaData.listarBrigadasAsignables();
        imprimirListaBrigada(listaBrigada);

        System.out.println("\n----- Listar todas las brigadas NO asignables (brigada activa que no está disponible o tiene menos de 5 bomberos o está atendiendo una emergencia) -----");
        listaBrigada = brigadaData.listarBrigadasNoAsignables();
        imprimirListaBrigada(listaBrigada);

        System.out.println("\n----- Listar todas las brigadas que están atendiendo una emergencia -----");
        listaBrigada = brigadaData.listarBrigadasAtendiendoEmergencia();
        imprimirListaBrigada(listaBrigada);

        System.out.println("\n----- Listar todas las brigadas incompletas (que tienen menos de 5 miembros activos) -----");
        listaBrigada = brigadaData.listarBrigadasIncompletas();
        imprimirListaBrigada(listaBrigada);

        System.out.println("\n----- Listar todos los cuarteles -----");
        listaCuartel = cuartelData.listarCuarteles();
        imprimirListaCuartel(listaCuartel);

        System.out.println("\n--------------- FIN LISTAR DATOS (PRIMERA PARTE) ---------------\n\n");
        //
        // ************ FIN LISTAR DATOS *************
        //

        //
        // ************ BUSCAR DATOS *************
        //
        codigoCuartelBuscar = cuartel4.getCodigoCuartel();
        codigoBrigadaBuscar = brigada3.getCodigoBrigada();
        idBomberoBuscar = bombero13.getIdBombero();
        dniBomberoBuscar = bombero13.getDni();
        codigoSiniestroBuscar = siniestro3.getCodigoSiniestro();
        nombreCuartelBuscar = cuartel4.getNombreCuartel();
        nombreBrigadaBuscar = brigada3.getNombreBrigada();

        System.out.println("\n--------------- BUSCAR DATOS (PRIMERA PARTE) ---------------");

        System.out.println("\n----- Buscar cuartel (activo) por código (" + codigoCuartelBuscar + ") -----");
        cuartelAux = cuartelData.buscarCuartel(codigoCuartelBuscar);
        imprimirResultadoCuartel(cuartelAux);

        System.out.println("\n----- Buscar cuartel (activo) por nombre (" + nombreCuartelBuscar + ") -----");
        cuartelAux = cuartelData.buscarCuartelPorNombre(nombreCuartelBuscar);
        imprimirResultadoCuartel(cuartelAux);

        System.out.println("\n----- Buscar brigada (activa) por código (" + codigoBrigadaBuscar + ") -----");
        brigadaAux = brigadaData.buscarBrigada(codigoBrigadaBuscar);
        imprimirResultadoBrigada(brigadaAux);

        System.out.println("\n----- Buscar brigada (activa) por nombre (" + nombreBrigadaBuscar + ") -----");
        brigadaAux = brigadaData.buscarBrigadaPorNombre(nombreBrigadaBuscar);
        imprimirResultadoBrigada(brigadaAux);

        System.out.println("\n----- Buscar bombero (activo) por id (" + idBomberoBuscar + ")-----");
        bomberoAux = bomberoData.buscarBombero(idBomberoBuscar);
        imprimirResultadoBombero(bomberoAux);

        System.out.println("\n----- Buscar bombero (activo) por dni (" + dniBomberoBuscar + ") -----");
        bomberoAux = bomberoData.buscarBomberoPorDni(dniBomberoBuscar);
        imprimirResultadoBombero(bomberoAux);

        System.out.println("\n----- Buscar siniestro por código (" + codigoSiniestroBuscar + ") -----");
        siniestroAux = siniestroData.buscarSiniestro(codigoSiniestroBuscar);
        imprimirResultadoSiniestro(siniestroAux);

        System.out.println("\n--------------- FIN BUSCAR DATOS (PRIMERA PARTE) ---------------\n\n");
        //
        // ************ FIN BUSCAR DATOS *************
        //        

        //
        // ************ MODIFICAR DATOS *************
        //
        System.out.println("\n--------------- MODIFICAR DATOS ---------------");

        System.out.println("\n----- Modificar cuartel (activo) (codigo: " + cuartel4.getCodigoCuartel() + ") -----");
        cuartel4.setNombreCuartel("cuartel44");
        cuartelData.modificarCuartel(cuartel4);

        System.out.println("\n----- Modificar brigada (activo) (codigo: " + brigada3.getCodigoBrigada() + ") -----");
        brigada3.setDisponible(true);
        brigadaData.modificarBrigada(brigada3);

        System.out.println("\n----- Modificar bombero (activo) (id: " + bombero13.getIdBombero() + ") -----");
        bombero13.setGrupoSanguineo("B-");
        bomberoData.modificarBombero(bombero13);

        System.out.println("\n----- Modificar siniestro (no resuelto) (código: " + siniestro3.getCodigoSiniestro() + ")");
        System.out.println("\nCambiando la fecha de inicio a una posterior a la actual ");
        siniestro3.setFechaHoraInicio(LocalDateTime.of(2023, Month.NOVEMBER, 30, 12, 0));
        siniestroData.modificarSiniestro(siniestro3);
        System.out.println("\nCambiando la fecha de inicio a una válida, agregando puntuación válida (10) y fecha de resolución como previa a la de inicio ");
        siniestro3.setFechaHoraInicio(LocalDateTime.of(2023, Month.NOVEMBER, 3, 16, 0));     // volver fechaR a como estaba antes   
        siniestro3.setPuntuacion(10);
        siniestro3.setFechaHoraResolucion(LocalDateTime.of(2023, Month.SEPTEMBER, 30, 12, 0));
        siniestroData.modificarSiniestro(siniestro3);
        System.out.println("\nCambiando la fecha de resolución como posterior a la actual ");
        siniestro3.setFechaHoraResolucion(LocalDateTime.of(2023, Month.NOVEMBER, 30, 12, 0));
        siniestroData.modificarSiniestro(siniestro3);
        System.out.println("\nCambiando la fecha de resolución como anterior a la actual y posterior a la de inicio y cambiando la puntuación a un valor inválido (20) ");
        siniestro3.setFechaHoraResolucion(LocalDateTime.of(2023, Month.NOVEMBER, 9, 12, 0)); // establecer fechaR como posterior a fechaI y anterior a la actual
        siniestro3.setPuntuacion(20);
        siniestroData.modificarSiniestro(siniestro3);
        System.out.println("\nCambiando la puntuación a un valor válido (10) y asignando a codigoBrigada el código de " + brigada2.getNombreBrigada() + " (ahora todo debería ser correcto)");
        siniestro3.setPuntuacion(10);
        siniestro3.setBrigada(brigada2);
        siniestroData.modificarSiniestro(siniestro3);

        System.out.println("\n----- Modificar siniestro (no resuelto) (código: " + siniestro2.getCodigoSiniestro() + ")");
        System.out.println("\nAsignando a codigoBrigada el código de " + brigada3.getNombreBrigada());
        siniestro2.setBrigada(brigada3);
        siniestroData.modificarSiniestro(siniestro2);

        System.out.println("\n--------------- FIN MODIFICAR DATOS ---------------\n\n");
        //
        // ************ FIN MODIFICAR DATOS *************
        //

        //
        // ************ LISTAR DATOS *************
        //
        System.out.println("\n--------------- LISTAR DATOS (SEGUNDA PARTE) ---------------");

        System.out.println("\n----- Listar todos los siniestros -----");
        listaSiniestro = siniestroData.listarSiniestros();
        imprimirListaSiniestro(listaSiniestro);

        System.out.println("\n----- Listar todos los siniestros resueltos -----");
        listaSiniestro = siniestroData.listarSiniestrosResueltos();
        imprimirListaSiniestro(listaSiniestro);

        System.out.println("\n----- Listar todos los siniestros NO resueltos -----");
        listaSiniestro = siniestroData.listarSiniestrosSinResolucion();
        imprimirListaSiniestro(listaSiniestro);

        System.out.println("\n----- Listar todos los siniestros que se iniciaron y terminaron en el siguiente intervalo (" + fecha1 + " hasta " + fecha2 + ")-----");
        listaSiniestro = siniestroData.listarSiniestrosInicioFinEntreFechas(fecha1, fecha2);
        imprimirListaSiniestro(listaSiniestro);

        System.out.println("\n----- Listar todos los siniestros que se iniciaron en el siguiente intervalo (" + fecha1 + " hasta " + fecha2 + ")-----");
        listaSiniestro = siniestroData.listarSiniestrosEntreFechas(fecha1, fecha2);
        imprimirListaSiniestro(listaSiniestro);

        System.out.println("\n----- Listar todas las brigadas asignables (disponible, con cantidad de bomberos igual a 5 y la brigada no está atendiendo una emergencia) -----");
        listaBrigada = brigadaData.listarBrigadasAsignables();
        imprimirListaBrigada(listaBrigada);

        System.out.println("\n----- Listar todas las brigadas NO asignables (brigada activa que no está disponible o tiene menos de 5 bomberos o está atendiendo una emergencia) -----");
        listaBrigada = brigadaData.listarBrigadasNoAsignables();
        imprimirListaBrigada(listaBrigada);

        System.out.println("\n----- Listar todas las brigadas que están atendiendo una emergencia -----");
        listaBrigada = brigadaData.listarBrigadasAtendiendoEmergencia();
        imprimirListaBrigada(listaBrigada);

        System.out.println("\n--------------- FIN LISTAR DATOS (SEGUNDA PARTE) ---------------\n\n");
        //
        // ************ FIN LISTAR DATOS *************
        //

        //
        // ************ BUSCAR DATOS *************
        //
        nombreCuartelBuscar = cuartel4.getNombreCuartel();
        nombreBrigadaBuscar = brigada3.getNombreBrigada();

        System.out.println("\n--------------- BUSCAR DATOS (SEGUNDA PARTE) ---------------");

        System.out.println("\n----- Buscar cuartel (activo) por código (" + codigoCuartelBuscar + ") -----");
        cuartelAux = cuartelData.buscarCuartel(codigoCuartelBuscar);
        imprimirResultadoCuartel(cuartelAux);

        System.out.println("\n----- Buscar cuartel (activo) por nombre (" + nombreCuartelBuscar + ") -----");
        cuartelAux = cuartelData.buscarCuartelPorNombre(nombreCuartelBuscar);
        imprimirResultadoCuartel(cuartelAux);

        System.out.println("\n----- Buscar brigada (activa) por código (" + codigoBrigadaBuscar + ") -----");
        brigadaAux = brigadaData.buscarBrigada(codigoBrigadaBuscar);
        imprimirResultadoBrigada(brigadaAux);

        System.out.println("\n----- Buscar brigada (activa) por nombre (" + nombreBrigadaBuscar + ") -----");
        brigadaAux = brigadaData.buscarBrigadaPorNombre(nombreBrigadaBuscar);
        imprimirResultadoBrigada(brigadaAux);

        System.out.println("\n----- Buscar bombero (activo) por id (" + idBomberoBuscar + ")-----");
        bomberoAux = bomberoData.buscarBombero(idBomberoBuscar);
        imprimirResultadoBombero(bomberoAux);

        System.out.println("\n----- Buscar bombero (activo) por dni (" + dniBomberoBuscar + ") -----");
        bomberoAux = bomberoData.buscarBomberoPorDni(dniBomberoBuscar);
        imprimirResultadoBombero(bomberoAux);

        System.out.println("\n----- Buscar siniestro por código (" + codigoSiniestroBuscar + ") -----");
        siniestroAux = siniestroData.buscarSiniestro(codigoSiniestroBuscar);
        imprimirResultadoSiniestro(siniestroAux);

        System.out.println("\n--------------- FIN BUSCAR DATOS (SEGUNDA PARTE) ---------------\n\n");
        //
        // ************ FIN BUSCAR DATOS *************
        //

        //
        // ************ ELIMINAR DATOS *************
        //
        System.out.println("\n--------------- ELIMINAR DATOS (PRIMERA PARTE) ---------------");

        System.out.println("\n----- Eliminar cuartel (con brigadas activas en él) por código (" + cuartel3.getCodigoCuartel() + ")");
        cuartelData.eliminarCuartel(cuartel3.getCodigoCuartel());

        System.out.println("\n----- Eliminar cuartel (con brigadas activas en él) por nombre (" + cuartel3.getNombreCuartel() + ")");
        cuartelData.eliminarCuartelPorNombre(cuartel3.getNombreCuartel());

        System.out.println("\n----- Eliminar brigada (con bomberos activos en ella) por código (" + brigada3.getCodigoBrigada() + ")");
        brigadaData.eliminarBrigada(brigada3.getCodigoBrigada());

        System.out.println("\n----- Eliminar brigada (con bomberos activos en ella) por nombre (" + brigada3.getNombreBrigada() + ")");
        brigadaData.eliminarBrigadaPorNombre(brigada3.getNombreBrigada());

        System.out.println("\n----- Eliminar bombero (que forma parte de una brigada que está atendiendo una emergencia) por id (" + bombero7.getIdBombero() + ")");
        bomberoData.eliminarBombero(bombero7.getIdBombero());

        System.out.println("\n----- Eliminar bombero (que forma parte de una brigada que está atendiendo una emergencia) por dni (" + bombero7.getDni() + ")");
        bomberoData.eliminarBomberoPorDni(bombero7.getDni());

        // TODO: cambiar por modificar
        //System.out.println("\n----- Asignando resolución a un siniestro (código de siniestro: " + siniestro2.getCodigoSiniestro() + ") -----");
        //siniestroData.asignarResolucion(siniestro2, LocalDateTime.of(2023, Month.NOVEMBER, 3, 12, 0), 8);
        System.out.println("\n--------------- FIN ELIMINAR DATOS (PRIMERA PARTE) ---------------\n\n");
        //
        // ************ FIN ELIMINAR DATOS *************
        //        

        //
        // ************ LISTAR DATOS *************
        // 
        System.out.println("\n--------------- LISTAR DATOS (TERCERA PARTE) ---------------");

        System.out.println("\n----- Listar todos los siniestros -----");
        listaSiniestro = siniestroData.listarSiniestros();
        imprimirListaSiniestro(listaSiniestro);

        System.out.println("\n----- Listar todos los siniestros resueltos -----");
        listaSiniestro = siniestroData.listarSiniestrosResueltos();
        imprimirListaSiniestro(listaSiniestro);

        System.out.println("\n----- Listar todos los siniestros NO resueltos -----");
        listaSiniestro = siniestroData.listarSiniestrosSinResolucion();
        imprimirListaSiniestro(listaSiniestro);

        System.out.println("\n----- Listar todos los siniestros que se iniciaron y terminaron en el siguiente intervalo (" + fecha1 + " hasta " + fecha2 + ")-----");
        listaSiniestro = siniestroData.listarSiniestrosInicioFinEntreFechas(fecha1, fecha2);
        imprimirListaSiniestro(listaSiniestro);

        System.out.println("\n----- Listar todos los siniestros que se iniciaron en el siguiente intervalo (" + fecha1 + " hasta " + fecha2 + ")-----");
        listaSiniestro = siniestroData.listarSiniestrosEntreFechas(fecha1, fecha2);
        imprimirListaSiniestro(listaSiniestro);

        System.out.println("\n----- Listar todas las brigadas asignables (disponible, con cantidad de bomberos igual a 5 y la brigada no está atendiendo una emergencia) -----");
        listaBrigada = brigadaData.listarBrigadasAsignables();
        imprimirListaBrigada(listaBrigada);

        System.out.println("\n----- Listar todas las brigadas NO asignables (brigada activa que no está disponible o tiene menos de 5 bomberos o está atendiendo una emergencia) -----");
        listaBrigada = brigadaData.listarBrigadasNoAsignables();
        imprimirListaBrigada(listaBrigada);

        System.out.println("\n----- Listar todas las brigadas que están atendiendo una emergencia -----");
        listaBrigada = brigadaData.listarBrigadasAtendiendoEmergencia();
        imprimirListaBrigada(listaBrigada);

        System.out.println("\n--------------- FIN LISTAR DATOS (TERCERA PARTE) ---------------\n\n");
        //
        // ************ FIN LISTAR DATOS *************
        //

        //
        // ************ ELIMINAR DATOS *************
        // 
        System.out.println("\n--------------- ELIMINAR DATOS (SEGUNDA PARTE) ---------------");

        System.out.println("\n----- Eliminar bombero (activo) por id (" + bombero7.getIdBombero() + ")");
        bomberoData.eliminarBombero(bombero7.getIdBombero());
        System.out.println("\n----- Eliminar bombero (inactivo) por id (" + bombero7.getIdBombero() + ")");
        bomberoData.eliminarBombero(bombero7.getIdBombero());

        System.out.println("\n----- Eliminar brigada (activa) por código (" + brigada3.getCodigoBrigada() + ")");
        brigadaData.eliminarBrigada(brigada3.getCodigoBrigada());
        System.out.println("\n----- Eliminar brigada (inactivo) por nombre (" + brigada3.getNombreBrigada() + ")");
        brigadaData.eliminarBrigadaPorNombre(brigada3.getNombreBrigada());

        System.out.println("\n----- Eliminar bombero (activo) por dni (" + bombero8.getDni() + ")");
        bomberoData.eliminarBomberoPorDni(bombero8.getDni());
        System.out.println("\n----- Eliminar bombero (inactivo) por dni (" + bombero8.getDni() + ")");
        bomberoData.eliminarBomberoPorDni(bombero8.getDni());

        System.out.println("\n----- Eliminar bombero (activo) por id (" + bombero9.getIdBombero() + ")");
        bomberoData.eliminarBombero(bombero9.getIdBombero());
        System.out.println("\n----- Eliminar bombero (activo) por id (" + bombero10.getIdBombero() + ")");
        bomberoData.eliminarBombero(bombero10.getIdBombero());
        System.out.println("\n----- Eliminar bombero (activo) por id (" + bombero11.getIdBombero() + ")");
        bomberoData.eliminarBombero(bombero11.getIdBombero());

        System.out.println("\n----- Eliminar brigada (activa) por código (" + brigada3.getCodigoBrigada() + ")");
        brigadaData.eliminarBrigada(brigada3.getCodigoBrigada());
        System.out.println("\n----- Eliminar brigada (inactiva) por código (" + brigada3.getCodigoBrigada() + ")");
        brigadaData.eliminarBrigada(brigada3.getCodigoBrigada());
        System.out.println("\n----- Eliminar brigada (activa) por nombre (" + brigada5.getNombreBrigada() + ")");
        brigadaData.eliminarBrigadaPorNombre(brigada5.getNombreBrigada());
        System.out.println("\n----- Eliminar brigada (inactiva) por nombre (" + brigada5.getNombreBrigada() + ")");
        brigadaData.eliminarBrigadaPorNombre(brigada5.getNombreBrigada());

        System.out.println("\n----- Eliminar cuartel (activo) por código (" + cuartel3.getCodigoCuartel() + ")");
        cuartelData.eliminarCuartel(cuartel3.getCodigoCuartel());
        System.out.println("\n----- Eliminar cuartel (inactivo) por código (" + cuartel3.getCodigoCuartel() + ")");
        cuartelData.eliminarCuartel(cuartel3.getCodigoCuartel());
        System.out.println("\n----- Eliminar cuartel (activo) por nombre (" + cuartel4.getNombreCuartel() + ")");
        cuartelData.eliminarCuartelPorNombre(cuartel4.getNombreCuartel());
        System.out.println("\n----- Eliminar cuartel (inactivo) por nombre (" + cuartel4.getNombreCuartel() + ")");
        cuartelData.eliminarCuartelPorNombre(cuartel4.getNombreCuartel());

        System.out.println("\n--------------- FIN ELIMINAR DATOS (SEGUNDA PARTE) ---------------\n\n");
        //
        // ************ FIN ELIMINAR DATOS *************
        //

        //
        // ************ LISTAR DATOS *************
        //
        System.out.println("\n--------------- LISTAR DATOS (CUARTA PARTE) ---------------");

        System.out.println("\n----- Listar todos los bomberos -----");
        listaBombero = bomberoData.listarBomberos();
        imprimirListaBombero(listaBombero);

        System.out.println("\n----- Listar todos los bomberos del cuartel '" + cuartel3.getNombreCuartel() + "' (inactivo) -----");   // CAMBIAR CUARTEL Y LUEGO CAMBIAR BRIGADA ABAJO!!!!
        listaBombero = cuartelData.listarBomberosDelCuartel(cuartel3);
        imprimirListaBombero(listaBombero);

        System.out.println("\n----- Listar todos los bomberos del cuartel '" + cuartel4.getNombreCuartel() + "' (inactivo) -----");   // CAMBIAR CUARTEL Y LUEGO CAMBIAR BRIGADA ABAJO!!!!
        listaBombero = cuartelData.listarBomberosDelCuartel(cuartel4);
        imprimirListaBombero(listaBombero);

        System.out.println("\n----- Listar todos los bomberos de la brigada '" + brigada3.getNombreBrigada() + "' (inactivo) -----");
        listaBombero = brigadaData.listarBomberosDeBrigada(brigada3);
        imprimirListaBombero(listaBombero);
        System.out.println("\n----- Listar todos los bomberos de la brigada '" + brigada5.getNombreBrigada() + "' (inactivo) -----");
        listaBombero = brigadaData.listarBomberosDeBrigada(brigada5);
        imprimirListaBombero(listaBombero);

        System.out.println("\n----- Listar todas las brigadas -----");
        listaBrigada = brigadaData.listarBrigadas();
        imprimirListaBrigada(listaBrigada);

        System.out.println("\n----- Listar todas las brigadas del cuartel '" + cuartel3.getNombreCuartel() + "' (inactivo) -----");
        listaBrigada = cuartelData.listarBrigadasDelCuartel(cuartel3.getCodigoCuartel());
        imprimirListaBrigada(listaBrigada);
        System.out.println("\n----- Listar todas las brigadas del cuartel '" + cuartel4.getNombreCuartel() + "' (inactivo) -----");
        listaBrigada = cuartelData.listarBrigadasDelCuartel(cuartel4.getCodigoCuartel());
        imprimirListaBrigada(listaBrigada);

        System.out.println("\n----- Listar todas las brigadas incompletas (que tienen menos de 5 miembros activos) -----");
        listaBrigada = brigadaData.listarBrigadasIncompletas();
        imprimirListaBrigada(listaBrigada);

        System.out.println("\n----- Listar todos los cuarteles -----");
        listaCuartel = cuartelData.listarCuarteles();
        imprimirListaCuartel(listaCuartel);

        System.out.println("\n--------------- FIN LISTAR DATOS ---------------\n\n");
        //
        // ************ FIN LISTAR DATOS *************
        //

        //
        // ************ BUSCAR DATOS *************
        //
        codigoCuartelBuscar = cuartel3.getCodigoCuartel();
        codigoBrigadaBuscar = brigada3.getCodigoBrigada();
        idBomberoBuscar = bombero7.getIdBombero();
        dniBomberoBuscar = bombero8.getDni();
        nombreCuartelBuscar = cuartel4.getNombreCuartel();
        nombreBrigadaBuscar = brigada5.getNombreBrigada();

        System.out.println("\n--------------- BUSCAR DATOS (TERCERA PARTE) ---------------");

        System.out.println("\n----- Buscar cuartel (inactivo) por código (" + codigoCuartelBuscar + ") -----");
        cuartelAux = cuartelData.buscarCuartel(codigoCuartelBuscar);
        imprimirResultadoCuartel(cuartelAux);

        System.out.println("\n----- Buscar cuartel (inactivo) por nombre (" + nombreCuartelBuscar + ") -----");
        cuartelAux = cuartelData.buscarCuartelPorNombre(nombreCuartelBuscar);
        imprimirResultadoCuartel(cuartelAux);

        System.out.println("\n----- Buscar brigada (inactiva) por código (" + codigoBrigadaBuscar + ") -----");
        brigadaAux = brigadaData.buscarBrigada(codigoBrigadaBuscar);
        imprimirResultadoBrigada(brigadaAux);

        System.out.println("\n----- Buscar brigada (inactiva) por nombre (" + nombreBrigadaBuscar + ") -----");
        brigadaAux = brigadaData.buscarBrigadaPorNombre(nombreBrigadaBuscar);
        imprimirResultadoBrigada(brigadaAux);

        System.out.println("\n----- Buscar bombero (inactivo) por id (" + idBomberoBuscar + ")-----");
        bomberoAux = bomberoData.buscarBombero(idBomberoBuscar);
        imprimirResultadoBombero(bomberoAux);

        System.out.println("\n----- Buscar bombero (inactivo) por dni (" + dniBomberoBuscar + ") -----");
        bomberoAux = bomberoData.buscarBomberoPorDni(dniBomberoBuscar);
        imprimirResultadoBombero(bomberoAux);

        System.out.println("\n--------------- FIN BUSCAR DATOS ---------------\n\n");
        //
        // ************ FIN BUSCAR DATOS *************
        //

        //        // verificar la modificación de los objetos 
        //        for (Bombero bombero : bomberos) {
        //            System.out.println(bombero);
        //        }
        //        for (Brigada brigada : brigadas) {
        //            System.out.println(brigada);
        //        }
        //        for (Emergencia siniestro : siniestros) {
        //            System.out.println(siniestro);
        //        }
        //        for (Cuartel cuartel : cuarteles) {
        //            System.out.println(cuartel);
        //        }
    }
}
