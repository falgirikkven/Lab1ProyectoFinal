package lab1proyectofinal.accesoADatos;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import lab1proyectofinal.entidades.Bombero;

/**
 *
 * @author Grupo-3
 */
public class BomberoData {

    /**
     * SUJETO A CAMBIOS
     */
    private Connection connection;

    public BomberoData() {
        this.connection = Conexion.getInstance();
    }

    public boolean guardarBombero(Bombero bombero) {
        // TODO: Implementar este método
        boolean resultado = false;
        return resultado;
    }

    public Bombero buscarBombero(int id_bombero) {
        // TODO: Implementar este método
        Bombero bombero = null;
        return bombero;
    }

    public Bombero buscarBomberoPorDni(int dni) {
        // TODO: Implementar este método
        Bombero bombero = null;
        return null;
    }

    public List<Bombero> listarBomberos() {
        // TODO: Implementar este método
        List<Bombero> bomberos = new ArrayList();
        return bomberos;
    }

    public boolean modificarBombero(Bombero bombero) {
        // TODO: Implementar este método
        boolean resultado = false;
        return resultado;
    }

    public boolean eliminarBombero(int id_bombero) {
        // TODO: Implementar este método
        boolean resultado = false;
        return resultado;
    }

    public boolean eliminarBomberoPorDni(int dni) {
        // TODO: Implementar este método
        boolean resultado = false;
        return resultado;
    }

}
