package lab1proyectofinal;

import java.sql.Connection;
import javax.swing.JOptionPane;
import lab1proyectofinal.accesoADatos.Conexion;
import lab1proyectofinal.vistas.MainFrame;

/**
 *
 * @author Grupo-3
 */
public class Lab1ProyectoFinal {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        // Establecer la conexion
        Connection con = Conexion.getInstance();
        if (con == null) {
            JOptionPane.showMessageDialog(null, "No se pudo conectar a la base de datos", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // TODO: implementar vistas
        MainFrame.ejecutar(args);

    }

}
