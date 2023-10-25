package lab1proyectofinal.accesoADatos;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import lab1proyectofinal.entidades.Bombero;
import lab1proyectofinal.entidades.Brigada;

/**
 *
 * @author Grupo-3
 */
public class BrigadaData {

    /**
     * SUJETO A CAMBIOS
     */
    private Connection connection;

    public BrigadaData() {
        this.connection = Conexion.getInstance();
    }

    public boolean guardarBrigada(Brigada brigada) {
        // TODO: Implementar este método
        boolean resultado = false;
        return resultado;
    }

    public Brigada buscarBrigada(int codigo_brigada) {
        // TODO: Implementar este método
        Brigada brigada = null;
        return brigada;
    }

    public Brigada buscarBrigadaPorNombre(String nombre_brigada) {
        // TODO: Implementar este método
        Brigada brigada = null;
        return brigada;
    }

    public List<Brigada> listarBrigadas() {
        // TODO: Implementar este método
        List<Brigada> brigadas = new ArrayList();
        return brigadas;
    }

    public List<Bombero> listarBomberosEnBrigada(Brigada brigada) {
        // TODO: Implementar este método
        List<Bombero> bombero = new ArrayList();
        return bombero;
    }

    public boolean modificarBrigada(Brigada brigada) {
        // TODO: Implementar este método
        boolean resultado = false;
        return resultado;
    }

    public boolean eliminarBrigada(int codigo_brigada) {
        // TODO: Implementar este método
        boolean resultado = false;
        return resultado;
    }

}
