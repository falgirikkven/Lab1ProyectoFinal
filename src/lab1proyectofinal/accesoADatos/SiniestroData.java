package lab1proyectofinal.accesoADatos;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import lab1proyectofinal.entidades.Siniestro;

/**
 *
 * @author Grupo-3
 */
public class SiniestroData {

    /**
     * SUJETO A CAMBIOS
     */
    private Connection connection;

    public SiniestroData() {
        this.connection = Conexion.getInstance();
    }

    public boolean guardarSiniestro(Siniestro siniestro) {
        // TODO: Implementar este método
        boolean resultado = false;
        return resultado;
    }

    public Siniestro buscarSiniestro(int codigo) {
        // TODO: Implementar este método
        Siniestro siniestro = null;
        return siniestro;
    }

    public List<Siniestro> listarSiniestros() {
        // TODO: Implementar este método
        List<Siniestro> siniestros = new ArrayList();
        return siniestros;
    }

}
