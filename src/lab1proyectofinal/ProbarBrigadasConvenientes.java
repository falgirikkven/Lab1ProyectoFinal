package lab1proyectofinal;

import java.sql.Connection;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import lab1proyectofinal.accesoADatos.BomberoData;
import lab1proyectofinal.accesoADatos.BrigadaData;
import lab1proyectofinal.accesoADatos.Conexion;
import lab1proyectofinal.accesoADatos.CuartelData;
import lab1proyectofinal.accesoADatos.SiniestroData;
import lab1proyectofinal.accesoADatos.Utils;
import lab1proyectofinal.entidades.Bombero;
import lab1proyectofinal.entidades.Brigada;
import lab1proyectofinal.entidades.BrigadaDistancia;
import lab1proyectofinal.entidades.Cuartel;
import lab1proyectofinal.entidades.Siniestro;

/**
 *
 * @author Grupo-3
 */
public class ProbarBrigadasConvenientes {

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

        SiniestroData.inicializar();

        /* RECORDATORIO: Limpiar base de datos antes de ejecutar las pruebas */
        System.out.println("[RECORDATORIO] Limpiar base de datos antes de ejecutar las pruebas");

        // CuartelData
        CuartelData cuartelData = new CuartelData();

        // Cuarteles
        Cuartel cuartel1 = new Cuartel("centro", "centro 0  0", 0, 0, "1", "centro@gmail.com");
        Cuartel cuartel2 = new Cuartel("este", "derecha 10 0", 10, 0, "2", "derecha@gmail.com");
        Cuartel cuartel3 = new Cuartel("oeste", "izquierda -10 0", -10, 0, "3", "izquierda@gmail.com");
        Cuartel cuartel4 = new Cuartel("norte", "arriba 0 -10", 0, -10, "4", "arriba@gmail.com");
        Cuartel cuartel5 = new Cuartel("sur", "abajo 0 10", 0, 10, "5", "sur@gmail.com");
        Cuartel cuarteles[] = new Cuartel[]{cuartel1, cuartel2, cuartel3, cuartel4, cuartel5};

        // Guardar Cuartel
        System.out.println("\n----- Guardar Cuartel -----");
        for (Cuartel cuartel : cuarteles) {
            cuartelData.guardarCuartel(cuartel);
        }

        // Especialidades
        String[] especialidades = Utils.obtenerEspecialidades();

        // Brigada Data
        BrigadaData brigadaData = new BrigadaData();

        // Brigadas
        Brigada brigada1 = new Brigada("Centro 1", especialidades[0], true, cuartel1);
        Brigada brigada2 = new Brigada("Centro 2", especialidades[1], true, cuartel1);
        Brigada brigada3 = new Brigada("Este 1", especialidades[2], true, cuartel2);
        Brigada brigada4 = new Brigada("Este 2", especialidades[3], true, cuartel2);
        Brigada brigada5 = new Brigada("Oeste 1", especialidades[3], true, cuartel3);
        Brigada brigada6 = new Brigada("Oeste 2", especialidades[3], true, cuartel3);
        Brigada brigada7 = new Brigada("Norte 1", especialidades[2], true, cuartel4);
        Brigada brigada8 = new Brigada("Norte 2", especialidades[0], true, cuartel4);
        Brigada brigada9 = new Brigada("Sur 1", especialidades[1], true, cuartel5);
        Brigada brigada10 = new Brigada("Sur 2", especialidades[0], true, cuartel5);
        Brigada brigadas[] = new Brigada[]{brigada1, brigada2, brigada3, brigada4, brigada5, brigada6, brigada7, brigada8, brigada9, brigada10};

        // Guardar Brigadas
        System.out.println("\n----- Guardar Brigadas -----");
        for (Brigada brigada : brigadas) {
            brigadaData.guardarBrigada(brigada);
        }

        // Grupo sanguineo
        String[] grupoSanguineo = Utils.obtenerGrupoSanguineo();

        // Bombero Data
        BomberoData bomberoData = new BomberoData();

        // Guardar Bomberos
        System.out.println("\n----- Guardar Brigadas -----");
        for (int i = 0; i < brigadas.length; i++) {
            for (int j = 0; j < 5; j++) {
                int dni = (i * 5) + j;
                Bombero bombero = new Bombero(dni, "Soldado Imperial " + dni, grupoSanguineo[dni % 8],
                        LocalDate.of(2000, 1, 1), Integer.toString(dni * dni), brigadas[i]);
                bomberoData.guardarBombero(bombero);
            }
        }

        // Siniestro data
        SiniestroData siniestroData = new SiniestroData();

        // Siniestros
        Siniestro siniestro1 = new Siniestro(especialidades[3], LocalDateTime.MIN, 2, 2, "");

        System.out.println("\n----- Brigadas Convenientes -----");
        List<BrigadaDistancia> brigadasConvenientes = siniestroData.listarBrigadasConvenientes(siniestro1);
        for (BrigadaDistancia bd : brigadasConvenientes) {
            System.out.println(bd.toString());
        }
    }
}
