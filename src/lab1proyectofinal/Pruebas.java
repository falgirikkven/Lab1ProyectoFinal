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

        // Establecer la conexion
        Connection con = Conexion.getInstance();
        if (con == null) {
            System.out.println("[Error] No se pudo conectar a base de datos. Abortando ejecución");
            return;
        }

        //
        // ***** PRUEBAS DE CUARTELDATA *****
        //        
        // CuartelData
        CuartelData cuartelData = new CuartelData();

        // Cuarteles
        Cuartel cuartel1 = new Cuartel("cuartel1", "direccion 101", 1, 1, "2664101010", "cuartel1@gmail.com", true);     // cuarteles normales activos 
        Cuartel cuartel2 = new Cuartel("cuartel2", "direccion 202", 2, 2, "2664202020", "cuartel2@gmail.com", true);     // 
        Cuartel cuartel3 = new Cuartel("cuartel3", "direccion 303", 3, 3, "2664303030", "cuartel3@gmail.com", true);     //
        Cuartel cuartel4 = new Cuartel("cuartel4", "direccion 404", 4, 4, "2664404040", "cuartel4@gmail.com", true);     //
        Cuartel cuartel5 = new Cuartel("cuartel5", "direccion 505", 5, 5, "2664505050", "cuartel5@gmail.com", false);    // cuarteles normales inactivos
        Cuartel cuartel6 = new Cuartel("cuartel6", "direccion 606", 6, 6, "2664606060", "cuartel6@gmail.com", false);    //         
        Cuartel cuartel7 = new Cuartel("cuartel1", "direccion 707", 7, 7, "2664707070", "cuartel7@gmail.com", true);     // cuartel con nombre repetido 
        Cuartel cuartel8 = new Cuartel("cuartel8", "direccion 808", 8, 8, "2664808080", "cuartel8@gmail.com", true);     // cuartel inexistente en la BD
        Cuartel cuarteles[] = new Cuartel[]{cuartel1, cuartel2, cuartel3, cuartel4, cuartel5, cuartel6, cuartel7};

        // variables para pruebas
        Cuartel cuartelActivoConBrig = cuartel3;      // hay que darle de baja a una de sus brigadas
        Cuartel cuartelActivoSinBrig = cuartel4;
        Cuartel cuartelInactivo = cuartel5;
        Cuartel cuartelInexistente = cuartel8;
        int cuartelActivoConBrigCod = 3;
        int cuartelActivoSinBrigCod = 4;
        int cuartelInactivoCod = 5;
        int cuartelInexistenteCod = 8;
        String cuartelActivoConBrigNom = "cuartel3";
        String cuartelActivoSinBrigNom = "cuartel4";
        String cuartelInactivoNom = "cuartel5";
        String cuartelInexistenteNom = "cuartel8";

        // Guardar Cuarteles
        System.out.println("----- Guardar cuarteles -----");
        for (Cuartel cuartel : cuarteles) {
            cuartelData.guardarCuartel(cuartel);
        }

        //
        //  Buscar Cuartel
        //
        Cuartel cuartelEncontrado;
        // buscar cuartel activo, por código
        System.out.println("\n----- Buscar cuartel (codigoCuartel=" + cuartelActivoConBrigCod + " (cuartel activo)) -----");
        cuartelEncontrado = cuartelData.buscarCuartel(cuartelActivoConBrigCod);
        System.out.println("Datos del cuartel con codigoCuartel=" + cuartelActivoConBrigCod + ":");
        if (cuartelEncontrado != null) {
            System.out.println(cuartelEncontrado.toString());
        }
        // buscar cuartel inactivo, por código
        System.out.println("\n----- Buscar cuartel (codigoCuartel=" + cuartelInactivoCod + " (cuartel activo)) -----");
        cuartelEncontrado = cuartelData.buscarCuartel(cuartelInactivoCod);
        System.out.println("Datos del cuartel con codigoCuartel=" + cuartelInactivoCod + ":");
        if (cuartelEncontrado != null) {
            System.out.println(cuartelEncontrado.toString());
        }
        // buscar cuartel inexistente en la BD, por código
        System.out.println("\n----- Buscar cuartel (codigoCuartel=" + cuartelInexistenteCod + " (cuartel activo)) -----");
        cuartelEncontrado = cuartelData.buscarCuartel(cuartelInexistenteCod);
        System.out.println("Datos del cuartel con codigoCuartel=" + cuartelInexistenteCod + ":");
        if (cuartelEncontrado != null) {
            System.out.println(cuartelEncontrado.toString());
        }
//        // buscar cuartel activo, por nombre
//        System.out.println("\n----- Buscar cuartel (nombreCuartel=" + cuartelActivoConBrigNom + " (cuartel activo)) -----");
//        cuartelEncontrado = cuartelData.buscarCuartelPorNombre(cuartelActivoConBrigNom);
//        System.out.println("Datos del cuartel con nombreCuartel=" + cuartelActivoConBrigNom + ":");
//        if (cuartelEncontrado != null) {
//            System.out.println(cuartelEncontrado.toString());
//        }
//        // buscar cuartel inactivo, por nombre    
//        System.out.println("\n----- Buscar cuartel (nombreCuartel=" + cuartelInactivoNom + " (cuartel activo)) -----");
//        cuartelEncontrado = cuartelData.buscarCuartelPorNombre(cuartelInactivoNom);
//        System.out.println("Datos del cuartel con nombreCuartel=" + cuartelInactivoNom + ":");
//        if (cuartelEncontrado != null) {
//            System.out.println(cuartelEncontrado.toString());
//        }
//        // buscar cuartel inexistente en la BD, por nombre
//        System.out.println("\n----- Buscar cuartel (nombreCuartel=" + cuartelInexistenteNom + " (cuartel activo)) -----");
//        cuartelEncontrado = cuartelData.buscarCuartelPorNombre(cuartelInexistenteNom);
//        System.out.println("Datos del cuartel con nombreCuartel=" + cuartelInexistenteNom + ":");
//        if (cuartelEncontrado != null) {
//            System.out.println(cuartelEncontrado.toString());
//        }
        //
        // Fin Buscar Cuartel
        //

        //
        // Listar Cuarteles
        //
        List<Cuartel> listaCuartel;
        List<Brigada> listaBrigada;
        List<Bombero> listaBombero;
        // listar todos los cuarteles activos
        System.out.println("\n----- Listar cuarteles -----");
        listaCuartel = cuartelData.listarCuarteles();
        for (Cuartel cuartel : listaCuartel) {
            System.out.println(cuartel.toString());
        }
        // listar todas las brigadas activas de un cuartel activo
        System.out.println("\n----- Listar brigadas en cuartel (codigoCuartel=" + cuartelActivoConBrigCod + " (cuartel activo)) -----");
        listaBrigada = cuartelData.listarBrigadasDelCuartel(cuartelActivoConBrig);
        for (Brigada brigada : listaBrigada) {
            System.out.println(brigada.toString());
        }
        // todas las brigadas activas de un cuartel inactivo
        System.out.println("\n----- Listar brigadas en cuartel (codigoCuartel=" + cuartelInactivoCod + " (cuartel inactivo)) -----");
        listaBrigada = cuartelData.listarBrigadasDelCuartel(cuartelInactivo);
        for (Brigada brigada : listaBrigada) {
            System.out.println(brigada.toString());
        }
        // todas las brigadas activas de un cuartel inexistente en la BD
        System.out.println("\n----- Listar brigadas en cuartel(codigoCuartel=" + cuartelInexistenteCod + " (cuartel inexistente en la BD)) -----");
        listaBrigada = cuartelData.listarBrigadasDelCuartel(cuartelInexistente);
        for (Brigada brigada : listaBrigada) {
            System.out.println(brigada.toString());
        }
        // todos los bomberos activos de un cuartel activo
        System.out.println("\n----- Listar bomberos en cuartel (codigoCuartel=" + cuartelActivoConBrigCod + " (cuartel activo)) -----");
        listaBombero = cuartelData.listarBomberosDelCuartel(cuartelActivoConBrig);
        for (Bombero bombero : listaBombero) {
            System.out.println(bombero.toString());
        }
        // todos los bomberos activos de un cuartel inactivo
        System.out.println("\n----- Listar bomberos en cuartel (codigoCuartel=" + cuartelInactivoCod + " (cuartel inactivo)) -----");
        listaBombero = cuartelData.listarBomberosDelCuartel(cuartelInactivo);
        for (Bombero bombero : listaBombero) {
            System.out.println(bombero.toString());
        }
        // todos los bomberos activos de un cuartel inexistente en la BD
        System.out.println("\n----- Listar bomberos en cuartel (codigoCuartel=" + cuartelInexistenteCod + " (cuartel inexistente en la BD)) -----");
        listaBombero = cuartelData.listarBomberosDelCuartel(cuartelInexistente);
        for (Bombero bombero : listaBombero) {
            System.out.println(bombero.toString());
        }
        //
        // Fin Listar Cuarteles
        //

        //
        // Editar Cuarteles
        //
        // modificar cuartel activo
        System.out.println("\n----- Modificar cuartel (codigoCuartel=" + cuartelActivoConBrigCod + ") -----");
        cuartelActivoConBrig = new Cuartel(cuartelActivoConBrigCod, cuartelActivoConBrig.getNombreCuartel() + " mod",
                cuartelActivoConBrig.getDireccion() + " mod", cuartelActivoConBrig.getCoordenadaX() * 10,
                cuartelActivoConBrig.getCoordenadaY() * 10, cuartelActivoConBrig.getTelefono() + " mod",
                cuartelActivoConBrig.getCorreo() + ".mod");
        cuartelData.modificarCuartel(cuartelActivoConBrig);
        // modificar cuartel inactivo
        System.out.println("\n----- Modificar cuartel (codigoCuartel=" + cuartelInactivoCod + ") -----");
        cuartelInactivo = new Cuartel(cuartelInactivoCod, cuartelInactivo.getNombreCuartel() + " mod",
                cuartelInactivo.getDireccion() + " mod", cuartelInactivo.getCoordenadaX() * 10,
                cuartelInactivo.getCoordenadaY() * 10, cuartelInactivo.getTelefono() + " mod",
                cuartelInactivo.getCorreo() + ".mod");
        cuartelData.modificarCuartel(cuartelInactivo);
        // listar Cuarteles luego de editar
        System.out.println("\n----- Listar cuarteles luego de modificación -----");
        listaCuartel = cuartelData.listarCuarteles();
        for (Cuartel cuartel : listaCuartel) {
            System.out.println(cuartel.toString());
        }
        //
        // Fin Editar Cuarteles
        //

        //
        // Eliminar Cuarteles
        //
        // eliminar cuartel activo con una o más brigadas activas, por código
        System.out.println("\n----- Eliminar cuartel (codigoCuartel=" + cuartelActivoConBrigCod + " (cuartel sin brigadas)) -----");
        cuartelData.eliminarCuartel(cuartelActivoConBrigCod);
        // eliminar cuartel activo sin brigadas activas, por código
        System.out.println("\n----- Eliminar cuartel (codigoCuartel=" + cuartelActivoSinBrigCod + " (cuartel sin brigadas)) -----");
        cuartelData.eliminarCuartel(cuartelActivoSinBrigCod);
        // eliminar cuartel inactivo, por código 
        System.out.println("\n----- Eliminar cuartel (codigoCuartel=" + cuartelInactivoCod + " (cuartel sin brigadas)) -----");
        cuartelData.eliminarCuartel(cuartelInactivoCod);
        // eliminar cuartel activo con una o más brigadas activas, por nombre
        System.out.println("\n----- Eliminar cuartel (codigoCuartel=" + cuartelActivoConBrigNom + " (cuartel sin brigadas)) -----");
        cuartelData.eliminarCuartelPorNombre(cuartelActivoConBrigNom);
        // eliminar cuartel activo sin brigadas activas, por nombre
        System.out.println("\n----- Eliminar cuartel (codigoCuartel=" + cuartelActivoSinBrigNom + " (cuartel sin brigadas)) -----");
        cuartelData.eliminarCuartelPorNombre(cuartelActivoSinBrigNom);
        // eliminar cuartel inactivo, por nombre 
        System.out.println("\n----- Eliminar cuartel (codigoCuartel=" + cuartelInactivoNom + " (cuartel sin brigadas)) -----");
        cuartelData.eliminarCuartelPorNombre(cuartelInactivoNom);
        //
        // Fin Eliminar Cuarteles
        //
        //
        // ***** FIN PRUEBAS DE CUARTELDATA *****
        // 

        //
        // ***** BRIGADA PRUEBAS *****
        //
        // Brigada Data
        BrigadaData brigadaData = new BrigadaData();

        // Brigadas
        Brigada brigada1 = new Brigada("Grupo 1", "Quien sabe", true, 0, cuartel3, true);       // brigada activa, eventualmente completa (con, al menos, una baja) y estacionada en el cuartel (apta para tratar una emergencia)
        Brigada brigada2 = new Brigada("Grupo 2", "Quien sabe", false, 0, cuartel1, true);      // brigada activa, eventualmente completa (con, al menos, una baja) y fuera del cuartel (tratando una emergencia)
        Brigada brigada3 = new Brigada("Grupo 3", "Quien sabe", true, 0, cuartel2, true);       // brigadas activas, incompletas
        Brigada brigada4 = new Brigada("Grupo 4", "Quien sabe", true, 0, cuartel3, false);      // brigadas inactivas
        Brigada brigada5 = new Brigada("Grupo 1", "Quien sabe", true, 0, cuartel1, true);       // brigada con nombre repetido
        Brigada brigada6 = new Brigada("Grupo 6", "Quien sabe", true, 0, cuartel3, true);       // brigada inexistente en la BD    
        Brigada brigadas[] = new Brigada[]{brigada1, brigada2, brigada3, brigada4, brigada5};

        // variables para pruebas
        Brigada brigadaActivaConBom = brigada1;     // hay que darle de baja a uno de sus bomberos
        Brigada brigadaActivaSinBom = brigada3;      
        Brigada brigadaInactiva = brigada4;
        Brigada brigadaInexistente = brigada6;
        int brigadaActivaConBomCod = 1;
        int brigadaActivaSinBomCod = 3;
        int brigadaInactivaCod = 4;
        int brigadaInexistenteCod = 6;
        String brigadaActivaConBomNom = "brigada1";
        String brigadaActivaSinBomNom = "brigada3";
        String brigadaInactivaNom = "brigada4";
        String brigadaInexistenteNom = "brigada6";

        // Guardar Brigadas
        System.out.println("\n----- Guardar brigadas -----");
        for (Brigada brigada : brigadas) {
            brigadaData.guardarBrigada(brigada);
        }

        //
        // Buscar Brigadas
        // 
        Brigada brigadaEncontrada;
        // buscar brigada activa, por código        
        System.out.println("\n----- Buscar brigada (codigoBrigada=" + brigadaActivaConBomCod + " (brigada activa)) -----");
        brigadaEncontrada = brigadaData.buscarBrigada(brigadaActivaConBomCod);
        System.out.println("Datos de la brigada con codigoBrigada=" + brigadaActivaConBomCod + ":");
        if (brigadaEncontrada != null) { // Deberia funcionar
            System.out.println(brigadaEncontrada.toString());
        }
        // buscar brigada inactiva, por código
        System.out.println("\n----- Buscar brigada (codigoBrigada=" + brigadaInactivaCod + " (brigada inactiva)) -----");
        brigadaEncontrada = brigadaData.buscarBrigada(brigadaInactivaCod);
        System.out.println("Datos de la brigada con codigoBrigada=" + brigadaInactivaCod + ":");
        if (brigadaEncontrada != null) { // Deberia funcionar
            System.out.println(brigadaEncontrada.toString());
        }
        // buscar brigada inexistente en la BD, por código
        System.out.println("\n----- Buscar brigada (codigoBrigada=" + brigadaInexistenteCod + " (brigada inexistente en la BD)) -----");
        brigadaEncontrada = brigadaData.buscarBrigada(brigadaInexistenteCod);
        System.out.println("Datos de la brigada con codigoBrigada=" + brigadaInexistenteCod + ":");
        if (brigadaEncontrada != null) { // Deberia funcionar
            System.out.println(brigadaEncontrada.toString());
        }
        //
        // buscar brigada activa, por nombre        
        System.out.println("\n----- Buscar brigada (nombreBrigada=" + brigadaActivaConBomNom + " (brigada activa)) -----");
        brigadaEncontrada = brigadaData.buscarBrigadaPorNombre(brigadaActivaConBomNom);
        System.out.println("Datos de la brigada con nombreBrigada=" + brigadaActivaConBomNom + ":");
        if (brigadaEncontrada != null) { // Deberia funcionar
            System.out.println(brigadaEncontrada.toString());
        }
        // buscar brigada inactiva, por nombre
        System.out.println("\n----- Buscar brigada (nombreBrigada=" + brigadaInactivaNom + " (brigada inactiva)) -----");
        brigadaEncontrada = brigadaData.buscarBrigadaPorNombre(brigadaInactivaNom);
        System.out.println("Datos de la brigada con nombreBrigada=" + brigadaInactivaNom + ":");
        if (brigadaEncontrada != null) { // Deberia funcionar
            System.out.println(brigadaEncontrada.toString());
        }
        // buscar brigada inexistente en la BD, por código
        System.out.println("\n----- Buscar brigada (nombreBrigada=" + brigadaInexistenteNom + " (brigada inexistente en la BD)) -----");
        brigadaEncontrada = brigadaData.buscarBrigadaPorNombre(brigadaInexistenteNom);
        System.out.println("Datos de la brigada con nombreBrigada=" + brigadaInexistenteNom + ":");
        if (brigadaEncontrada != null) { // Deberia funcionar
            System.out.println(brigadaEncontrada.toString());
        }
        //
        // Fin Buscar Brigadas
        // 
        
        //
        // Listar Brigadas
        //          
        // listar todas las brigadas activas
        System.out.println("\n----- Listar brigadas -----");
        List<Brigada> listaBrigadas = brigadaData.listarBrigadas();
        for (Brigada brigada : listaBrigadas) {
            System.out.println(brigada.toString());
        }
        // listar todas las brigadas activas, completas y estacionadas
        System.out.println("\n----- Listar brigadas disponibles (capaces de atender una emergencia) -----");
        listaBrigadas = brigadaData.listarBrigadasDisponibles();
        for (Brigada brigada : listaBrigadas) {
            System.out.println(brigada.toString());
        }
        // listar todas las brigadas activas, completas y NO estacionadas
        System.out.println("\n----- Listar brigadas NO disponibles (están atendiendo una emergencia) -----");
        listaBrigadas = brigadaData.listarBrigadasOcupadas();
        for (Brigada brigada : listaBrigadas) {
            System.out.println(brigada.toString());
        }
        // listar todas las brigadas activas e incompletas 
        System.out.println("\n----- Listar brigadas incompletas (activas pero sin la cantidad suficiente de bomberos) -----");
        listaBrigadas = brigadaData.listarBrigadasIncompletas();
        for (Brigada brigada : listaBrigadas) {
            System.out.println(brigada.toString());
        }
        // listar todos los bomberos activos en una brigada activa
        System.out.println("\n----- Listar bomberos en brigada (codigoBrigada="+brigadaActivaConBom+" (brigada activa)) -----");
        listaBombero = brigadaData.listarBomberos(brigada1);
        for (Bombero bombero : listaBombero) {
            System.out.println(bombero.toString());
        }
        // listar todos los bomberos activos en una brigada inactiva
        System.out.println("\n----- Listar bomberos en brigada (codigoBrigada="+brigadaInactivaCod+" (brigada inactiva)) -----");
        listaBombero = brigadaData.listarBomberos(brigadaInactiva);
        for (Bombero bombero : listaBombero) {
            System.out.println(bombero.toString());
        }
        // listar todos los bomberos activos en una brigada inexistente en la BD
        System.out.println("\n----- Listar bomberos en brigada (codigoBrigada="+brigadaInexistenteCod+" (brigada inactiva)) -----");
        listaBombero = brigadaData.listarBomberos(brigadaInexistente);
        for (Bombero bombero : listaBombero) {
            System.out.println(bombero.toString());
        }
        //
        // Fin Listar Brigadas
        // 

        //
        // Modificar Brigadas
        // 
        // modificar brigada activa
        System.out.println("\n----- Modificar brigada (codigoBrigada=" + brigadaActivaConBomCod + " (brigada activa)) -----");
        brigadaActivaConBom = new Brigada(brigadaActivaConBom.getNombreBrigada()+" mod", 
                brigadaActivaConBom.getEspecialidad()+" mod", brigadaActivaConBom.isTratandoSiniestro(), brigadaActivaConBom.getCuartel());
        brigadaData.modificarBrigada(brigadaActivaConBom);
        // modificar brigada inactiva
        System.out.println("\n----- Modificar brigada (codigoBrigada=" + brigadaInactivaCod + " (brigada inactiva)) -----");
        brigadaInactiva = new Brigada(brigadaInactiva.getNombreBrigada()+" mod", 
                brigadaInactiva.getEspecialidad()+" mod", brigadaInactiva.isTratandoSiniestro(), brigadaInactiva.getCuartel());
        brigadaData.modificarBrigada(brigadaInactiva);
        //
        // Fin Modificar Brigadas
        //
        
        //
        // Eliminar Brigadas
        //
        // eliminar brigada activa con bomberos activos, por código       
        System.out.println("\n----- Eliminar brigada (codigoBrigada=" + brigadaActivaConBomCod + " (brigada activa con bomberos activos)) -----");
        brigadaData.eliminarBrigada(brigadaActivaConBomCod);
        // eliminar brigada activa sin bomberos activos, por código       
        System.out.println("\n----- Eliminar brigada (codigoBrigada=" + brigadaActivaSinBomCod + " (brigada activa sin bomberos activos)) -----");
        brigadaData.eliminarBrigada(brigadaActivaSinBomCod);
        // eliminar brigada inactiva, por código       
        System.out.println("\n----- Eliminar brigada (codigoBrigada=" + brigadaInactivaCod + " (brigada activa sin bomberos activos)) -----");
        brigadaData.eliminarBrigada(brigadaInactivaCod);
        // eliminar brigada activa con bomberos activos, por nombre       
        System.out.println("\n----- Eliminar brigada (nombreBrigada=" + brigadaActivaConBomNom + " (brigada activa con bomberos activos)) -----");
        brigadaData.eliminarBrigadaPorNombre(brigadaActivaConBomNom);
        // eliminar brigada activa sin bomberos activos, por nombre       
        System.out.println("\n----- Eliminar brigada (nombreBrigada=" + brigadaActivaSinBomNom + " (brigada activa sin bomberos activos)) -----");
        brigadaData.eliminarBrigadaPorNombre(brigadaActivaSinBomNom);
        // eliminar brigada inactiva, por nombre       
        System.out.println("\n----- Eliminar brigada (nombreBrigada=" + brigadaInactivaNom + " (brigada inactiva)) -----");
        brigadaData.eliminarBrigadaPorNombre(brigadaInactivaNom);
        //
        // Eliminar Brigadas
        //        
        // ***** FIN BRIGADA PRUEBAS *****
        //

        //
        // ***** BOMBERO PRUEBAS *****
        //
        // Bombero Data
        BomberoData bomberoData = new BomberoData();

        // Bomberos
        Bombero bombero1 = new Bombero(11000000, "nombre1", "A+", LocalDate.of(1998, Month.AUGUST, 1), "2679110001", brigada1, true);
        Bombero bombero2 = new Bombero(22000000, "nombre2", "A+", LocalDate.of(1998, Month.AUGUST, 2), "2679110002", brigada1, true);
        Bombero bombero3 = new Bombero(33000000, "nombre3", "A+", LocalDate.of(1998, Month.AUGUST, 3), "2679110003", brigada1, true);
        Bombero bombero4 = new Bombero(44000000, "nombre4", "A+", LocalDate.of(1998, Month.AUGUST, 4), "2679110004", brigada1, true);
        Bombero bombero5 = new Bombero(55000000, "nombre5", "A+", LocalDate.of(1998, Month.AUGUST, 5), "2679110005", brigada1, true);
        Bombero bombero6 = new Bombero(66000000, "nombre6", "A+", LocalDate.of(1998, Month.AUGUST, 6), "2679110006", brigada2, true);
        Bombero bombero7 = new Bombero(77000000, "nombre7", "A+", LocalDate.of(1998, Month.AUGUST, 7), "2679110007", brigada2, true);
        Bombero bombero8 = new Bombero(88000000, "nombre8", "A+", LocalDate.of(1998, Month.AUGUST, 8), "2679110008", brigada2, true);
        Bombero bombero9 = new Bombero(99000000, "nombre9", "A+", LocalDate.of(1998, Month.AUGUST, 9), "2679110009", brigada2, true);
        Bombero bombero10 = new Bombero(10100000, "nombre10", "A+", LocalDate.of(1998, Month.AUGUST, 10), "2679110010", brigada2, true);
        Bombero bombero11 = new Bombero(11110000, "nombre11", "A+", LocalDate.of(1998, Month.AUGUST, 11), "2679110011", brigada3, true);
        Bombero bombero12 = new Bombero(12120000, "nombre12", "A+", LocalDate.of(1998, Month.AUGUST, 12), "2679110012", brigada1, false);
        Bombero bomberoRep = new Bombero(11000000, "nombre12", "A+", LocalDate.of(1998, Month.AUGUST, 12), "2679110012", brigada3, true);    // bombero con dni repetido
//        Bombero bombero13 = new Bombero(13130000, "nombre13", "A+", LocalDate.of(1998, Month.AUGUST, 13), "2679110013", brigada1, true);
//        Bombero bombero14 = new Bombero(14140000, "nombre14", "A+", LocalDate.of(1998, Month.AUGUST, 14), "2679110014", brigada1, true);
        Bombero bomberos[] = new Bombero[]{bombero1, bombero2, bombero3, bombero4, bombero5, bombero6, bombero7, bombero8, bombero9, bombero10, bombero11, bomberoRep};

        // variables para pruebas
        Bombero bomberoActivo=bombero1;
        Bombero bomberoInactivo=bombero12;
        int bomberoActivoId=1;
        int bomberoInactivoId=12;  
        int bomberoInexistenteId=-10;
        int bomberoActivoDni=11000000;
        int bomberoInactivoDni=12120000;
        int bomberoInexistenteDni=-12345678;                
        
        // Guardar Bomberos
        System.out.println("\n----- Guardar Bomberos -----");
        for (Bombero bombero : bomberos) {
            bomberoData.guardarBombero(bombero);
        }
        
        //
        // Buscar Bombero
        //        
        Bombero bomberoEncontrado;
        // buscar bombero activo, por id
        System.out.println("\n----- Buscar bombero (codigoBombero=" + bomberoActivoId + " (bombero activo)) -----");
        bomberoEncontrado = bomberoData.buscarBombero(bomberoActivoId);
        System.out.println("Datos del bombero con idBombero=" + bomberoActivoId + ":");
        if (bomberoEncontrado != null) { 
            System.out.println(bomberoEncontrado.toString());
        }
        // buscar bombero inactivo, por id
        System.out.println("\n----- Buscar bombero (codigoBombero=" + bomberoInactivoId + " (bombero inactivo)) -----");
        bomberoEncontrado = bomberoData.buscarBombero(bomberoInactivoId);
        System.out.println("Datos del bombero con idBombero=" + bomberoInactivoId + ":");
        if (bomberoEncontrado != null) { 
            System.out.println(bomberoEncontrado.toString());
        }
        // buscar bombero inexistente en la BD, por id
        System.out.println("\n----- Buscar bombero (codigoBombero=" + bomberoInexistenteId + " (bombero inexistente en la BD)) -----");
        bomberoEncontrado = bomberoData.buscarBombero(bomberoInexistenteId);
        System.out.println("Datos del bombero con idBombero=" + bomberoInexistenteId + ":");
        if (bomberoEncontrado != null) { 
            System.out.println(bomberoEncontrado.toString());
        }
        // buscar bombero activo, por dni
        System.out.println("\n----- Buscar bombero (dni=" + bomberoActivoDni + " (bombero activo)) -----");
        bomberoEncontrado = bomberoData.buscarBomberoPorDni(bomberoActivoDni);
        System.out.println("Datos del bombero con dni=" + bomberoActivoDni + ":");
        if (bomberoEncontrado != null) { 
            System.out.println(bomberoEncontrado.toString());
        }
        // buscar bombero inactivo, por dni
        System.out.println("\n----- Buscar bombero (dni=" + bomberoInactivoDni + " (bombero inactivo)) -----");
        bomberoEncontrado = bomberoData.buscarBomberoPorDni(bomberoInactivoDni);
        System.out.println("Datos del bombero con dni=" + bomberoInactivoDni + ":");
        if (bomberoEncontrado != null) { 
            System.out.println(bomberoEncontrado.toString());
        }
        // buscar bombero inexistente en la BD, por dni
        System.out.println("\n----- Buscar bombero (dni=" + bomberoInexistenteDni + " (bombero inexistente en la BD)) -----");
        bomberoEncontrado = bomberoData.buscarBombero(bomberoInexistenteDni);
        System.out.println("Datos del bombero con idBombero=" + bomberoInexistenteDni + ":");
        if (bomberoEncontrado != null) { 
            System.out.println(bomberoEncontrado.toString());
        }
        //
        // Fin Buscar Bombero
        //
               
        // listar todos los bomberos activos
        System.out.println("\n----- Listar bomberos  -----");
        List<Bombero> listaBomberos = bomberoData.listarBomberos();
        for (Bombero bombero : listaBomberos) {
            System.out.println(bombero.toString());
        }

        //
        // Editar Bombero
        //
        // modificar bombero activo
        System.out.println("\n----- Modificar Bombero (idBombero=" + bomberoActivoId + ")-----");
        bomberoActivo = new Bombero(bomberoActivo.getDni()+10000000, bomberoActivo.getNombreCompleto()+" mod", "O-", LocalDate.of(2000, Month.NOVEMBER, 13), "42897241", bomberoActivo.getBrigada());
        bomberoData.modificarBombero(bomberoActivo);
        // modificar bombero inactivo
        System.out.println("\n----- Modificar Bombero (idBombero=" + bomberoInactivoId + ")-----");
        bomberoInactivo = new Bombero(bomberoInactivo.getDni()+10000000, bomberoInactivo.getNombreCompleto()+" mod", "O-", LocalDate.of(2000, Month.NOVEMBER, 13), "42897241", bomberoInactivo.getBrigada());
        bomberoData.modificarBombero(bomberoInactivo);
        //
        // Fin Editar Bombero
        //

        //
        // Eliminar Bombero
        //        
        // eliminar bombero activo, por id
        System.out.println("\n----- Eliminar bombero (idBombero=" + bomberoActivoId + " (activo)) -----");
        bomberoData.eliminarBombero(bomberoActivoId);
        // eliminar bombero inactivo, por id
        System.out.println("\n----- Eliminar bombero (idBombero=" + bomberoInactivoId + " (inactivo)) -----");
        bomberoData.eliminarBombero(bomberoInactivoId);
        // eliminar bombero activo, por dni
        System.out.println("\n----- Eliminar bombero (dni=" + bomberoActivoDni + " (activo)) -----");
        bomberoData.eliminarBombero(bomberoActivoDni);
        // eliminar bombero inactivo, por dni
        System.out.println("\n----- Eliminar bombero (dni=" + bomberoInactivoDni + " (inactivo)) -----");
        bomberoData.eliminarBombero(bomberoInactivoDni);
        //
        // Fin Eliminar Bombero
        //
        //
        // ***** FIN BOMBERO PRUEBAS *****
        //

        //
        // ***** SINIESTRO PRUEBAS *****
        // Siniestro data
        SiniestroData siniestroData = new SiniestroData();

        // Siniestros
        Siniestro siniestro1 = new Siniestro("Incendio", LocalDateTime.of(2023, Month.NOVEMBER, 1, 12, 0), 10, 10, "Mucho humo", brigada2);
        Siniestro siniestro2 = new Siniestro("Derrumbe", LocalDateTime.of(2023, Month.NOVEMBER, 2, 14, 0), 10, 10, "Mucho escombro");
        Siniestro siniestro3 = new Siniestro("Accidente", LocalDateTime.of(2023, Month.NOVEMBER, 3, 16, 0), 10, 10, "Al desarmadero");
        Siniestro siniestro4 = new Siniestro("NADA", LocalDateTime.of(2023, Month.NOVEMBER, 4, 16, 0), 10, 10, "NADA", LocalDateTime.of(2023, Month.NOVEMBER, 3, 17, 0), 10, brigada1);
        Siniestro siniestros[] = new Siniestro[]{siniestro1, siniestro2, siniestro3, siniestro4};        

        // Guardar Siniestros
        System.out.println("\n----- Guardar siniestros -----");
        for (Siniestro siniestro : siniestros) {
            siniestroData.guardarSiniestro(siniestro);
        }

        // Buscar Siniestros
        Siniestro siniestroEncontrado;
        System.out.println("\n----- Busco siniestro (codigoSiniestro=" + 1 + ") -----");
        siniestroEncontrado = siniestroData.buscarSiniestro(1);
        if (siniestroEncontrado != null) { 
            System.out.println(siniestroEncontrado.toString());
        } 

        // Listar Siniestros
        System.out.println("\n----- Listar siniestros -----");
        List<Siniestro> listaSiniestros = siniestroData.listarSiniestros();
        for (Siniestro siniestro : listaSiniestros) {
            System.out.println(siniestro.toString());
        }
        
        // Listar siniestros con resolución
        System.out.println("\n----- Listar siniestros resueltos -----");
        listaSiniestros = siniestroData.listarSiniestrosResueltos();
        for (Siniestro siniestro : listaSiniestros) {
            System.out.println(siniestro.toString());
        }
        // Listar Siniestros Sin resolucion
        System.out.println("\n----- Listar siniestros sin resolucion -----");
        listaSiniestros = siniestroData.listarSiniestrosSinResolucion();
        for (Siniestro siniestro : listaSiniestros) {
            System.out.println(siniestro.toString());
        }
        // Listar Siniestros Entre dos fechas
        System.out.println("\n----- Listar siniestros entre 2023-11-3 y 2023-11-5 -----");
        listaSiniestros = siniestroData.listarSiniestrosEntreFechas(LocalDateTime.of(2023, Month.NOVEMBER, 3, 0, 0), LocalDateTime.of(2023, Month.NOVEMBER, 5, 23, 59));
        for (Siniestro siniestro : listaSiniestros) {
            System.out.println(siniestro.toString());
        }

        // Modificar Siniestro
        System.out.println("\n----- Modificar siniestro (codigoSiniestro=1) -----");
        siniestro1.setDetalles("Mucho fuego");
        siniestroData.modificarSiniestro(siniestro1);
        System.out.println(siniestro1.toString());

//        System.out.println("\n----- Modificar Siniestro (Asigno resolucion a siniestro 2) -----");
//        // Esta forma comentada tambien funciona
//        // siniestro2.setFechaResolucion(LocalDateTime.now());
//        // siniestro2.setPuntuacion(4);
//        // siniestroData.modificarSiniestro(siniestro2);
//        siniestroData.asignarResolucion(siniestro2, LocalDateTime.now(), 4);
//        System.out.println(siniestro2.toString());

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
