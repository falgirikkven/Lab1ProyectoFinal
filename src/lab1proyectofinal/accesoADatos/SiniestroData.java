package lab1proyectofinal.accesoADatos;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
        boolean resultado = false;
        String sql;
        if (siniestro.getCodigoBrigada() == -1) {       // según mi idea inicial sobre la implementación del proyecto, solo se debería poder crear un siniestro con o sin una brigada asignada, pero en ningún caso con codigoSiniestro ni la fecha de finalización del siniestro y la puntuación del manejo del mismo
            sql = "INSERT INTO siniestro(tipo, fechaSiniestro, coordenadaX, coordenadaY, detalles) VALUES (?,?,?,?,?)";
        } else {
            sql = "INSERT INTO siniestro(tipo, fechaSiniestro, coordenadaX, coordenadaY, detalles, codigoBrigada) VALUES (?,?,?,?,?,?)";
        }
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, siniestro.getTipo());
            ps.setDate(2, Date.valueOf(siniestro.getFechaSiniestro()));
            ps.setInt(3, siniestro.getCoordenadaX());
            ps.setInt(4, siniestro.getCoordenadaY());
            ps.setString(5, siniestro.getDetalles());
            if (siniestro.getCodigoBrigada() != -1) {
                ps.setInt(6, siniestro.getCodigoBrigada());
            }
            if (ps.executeUpdate() > 0) {
                resultado = true;
                System.out.println("[SiniestroData] Siniestro guardado");
            } else {
                System.out.println("[SiniestroData] No se pudo guardar el siniestro");
            }
            ps.close();
        } catch (SQLException e) {
            System.out.println("[SiniestroData Error " + e.getErrorCode() + "] " + e.getMessage());
            e.printStackTrace();
        }

        return resultado;
    }

    public List<Siniestro> listarSiniesNoResuel(boolean hayBrigada) {
        List<Siniestro> listaSiniestros = new ArrayList();
        try {
            String sql;
            if (hayBrigada) {
                sql = "SELECT * FROM siniestro WHERE ISNULL(fechaResolucion) AND puntuacion=0 AND codigoBrigada<>-1";
            } else {
                sql = "SELECT * FROM siniestro WHERE ISNULL(fechaResolucion) AND puntuacion=0 AND codigoBrigada=-1";
            }
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            Siniestro siniestro;
            while (rs.next()) {
                siniestro = new Siniestro();        // fechaResolucion debería tener null y puntuacion debería tener 0. Lo mismo aplica para el registro obtenido 
                siniestro.setCodigoSiniestro(rs.getInt("codigoSiniestro"));
                siniestro.setTipo(rs.getString("tipo"));
                siniestro.setFechaSiniestro(rs.getDate("fechaSiniestro").toLocalDate());
                siniestro.setCoordenadaX(rs.getInt("coordenadaX"));
                siniestro.setCoordenadaY(rs.getInt("coordenadaY"));
                siniestro.setDetalles(rs.getString("detalles"));
                siniestro.setCodigoBrigada(rs.getInt("codigoBrigada"));
                listaSiniestros.add(siniestro);
            }
            ps.close();
        } catch (SQLException e) {
            System.out.println("[SiniestroData Error " + e.getErrorCode() + "] " + e.getMessage());
            e.printStackTrace();
        }
        return listaSiniestros;
    }

    public List<Siniestro> listarSiniesResuel() {
        List<Siniestro> listaSiniestros = new ArrayList();
        String sql = "SELECT * FROM siniestro WHERE NOT(ISNULL(fechaResolucion))";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            Siniestro siniestro;
            while (rs.next()) {
                siniestro = new Siniestro();
                siniestro.setCodigoSiniestro(rs.getInt("codigoSiniestro"));
                siniestro.setTipo(rs.getString("tipo"));
                siniestro.setFechaSiniestro(rs.getDate("fechaSiniestro").toLocalDate());
                siniestro.setCoordenadaX(rs.getInt("coordenadaX"));
                siniestro.setCoordenadaY(rs.getInt("coordenadaY"));
                siniestro.setDetalles(rs.getString("detalles"));
                siniestro.setCodigoBrigada(rs.getInt("codigoBrigada"));
                siniestro.setFechaResolucion(rs.getDate("fechaResolucion").toLocalDate());
                siniestro.setPuntuacion(rs.getInt("puntuacion"));
                listaSiniestros.add(siniestro);
            }
            ps.close();
        } catch (SQLException e) {
            System.out.println("[SiniestroData Error " + e.getErrorCode() + "] " + e.getMessage());
            e.printStackTrace();
        }
        return listaSiniestros;
    }

    public List<Siniestro> listarSiniesFecha(Date fecha1, Date fecha2) {
        List<Siniestro> listaSiniestros = new ArrayList();
        String sql = "SELECT * FROM siniestro WHERE NOT(ISNULL(fechaResolucion)) AND fechaResolucion BETWEEN ? AND ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setDate(1, fecha1);
            ps.setDate(2, fecha2);
            ResultSet rs = ps.executeQuery();
            Siniestro siniestro;
            while (rs.next()) {
                siniestro = new Siniestro();
                siniestro.setCodigoSiniestro(rs.getInt("codigoSiniestro"));
                siniestro.setTipo(rs.getString("tipo"));
                siniestro.setFechaSiniestro(rs.getDate("fechaSiniestro").toLocalDate());
                siniestro.setCoordenadaX(rs.getInt("coordenadaX"));
                siniestro.setCoordenadaY(rs.getInt("coordenadaY"));
                siniestro.setDetalles(rs.getString("detalles"));
                siniestro.setCodigoBrigada(rs.getInt("codigoBrigada"));
                siniestro.setFechaResolucion(rs.getDate("fechaResolucion").toLocalDate());
                siniestro.setPuntuacion(rs.getInt("puntuacion"));
                listaSiniestros.add(siniestro);
            }
            ps.close();
        } catch (SQLException e) {
            System.out.println("[SiniestroData Error " + e.getErrorCode() + "] " + e.getMessage());
            e.printStackTrace();
        }
        return listaSiniestros;
    }

    
}
