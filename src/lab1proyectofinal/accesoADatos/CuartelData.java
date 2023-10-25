package lab1proyectofinal.accesoADatos;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import lab1proyectofinal.entidades.Bombero;
import lab1proyectofinal.entidades.Brigada;
import lab1proyectofinal.entidades.Cuartel;

/**
 *
 * @author Grupo-3
 */
public class CuartelData {

    /**
     * SUJETO A CAMBIOS
     */
    private Connection connection;

    public CuartelData() {
        this.connection = Conexion.getInstance();
    }

    public boolean guardarCuartel(Cuartel cuartel) {
        // TODO: Implementar este método
        boolean resultado = false;
        return resultado;
    }

    public Cuartel buscarCuartel(int codigo_cuartel) {
        // TODO: Implementar este método
        Cuartel cuartel = null;
        return cuartel;
    }

    public Cuartel buscarCuartelPorNombre(String nombre_cuartel) {
        // TODO: Implementar este método
        Cuartel cuartel = null;
        return cuartel;
    }

    public List<Cuartel> listarCuarteles() {
        // TODO: Implementar este método
        List<Cuartel> cuarteles = new ArrayList();
        return cuarteles;
    }

    public List<Brigada> listarBrigadasEnCuartel(Cuartel cuartel) {
        // TODO: Implementar este método
        List<Brigada> brigadas = new ArrayList();
        return brigadas;
    }

    public List<Bombero> listarBomberosEnCuartel(Cuartel cuartel) {
        // TODO: Implementar este método
        List<Bombero> bomberos = new ArrayList();
        return bomberos;
    }

    public boolean modificarCuartel(Cuartel cuartel) {
        // TODO: Implementar este método
        boolean resultado = false;
        return resultado;
    }

    public boolean eliminarCuartel(int codigo_cuartel) {
        // TODO: Implementar este método
        boolean resultado = false;
        return resultado;
    }

}
