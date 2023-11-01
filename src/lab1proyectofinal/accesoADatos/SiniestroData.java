package lab1proyectofinal.accesoADatos;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lab1proyectofinal.entidades.Brigada;
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
        try {
            String sql;
            if (siniestro.getCodigoSiniestro() != -1) {
                sql = "INSERT INTO siniestro(tipo, fechaSiniestro, coordenadaX, coordenadaY, detalles, fechaResolucion, puntuacion, codigoBrigada) VALUES (?,?,?, ?, ?, ?, ?, ?)";
            } else {
                sql = "INSERT INTO siniestro(tipo, fechaSiniestro, coordenadaX, coordenadaY, detalles, fechaResolucion, puntuacion, codigoBrigada, codigoSiniestro) VALUES (?,?,?,?, ?, ?, ?, ?, ?)";
            }
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, siniestro.getTipo());
            ps.setDate(2, Date.valueOf(siniestro.getFechaSiniestro()));
            ps.setInt(3, siniestro.getCoordenadaX());
            ps.setInt(4, siniestro.getCoordenadaY());
            ps.setString(5, siniestro.getDetalles());
            if (siniestro.getCodigoBrigada() != -1) {
                if (siniestro.getFechaResolucion() == null && siniestro.getPuntuacion() == -1) {
                    ps.setNull(6, Types.DATE);
                    ps.setNull(7, Types.INTEGER);
                } else if (siniestro.getFechaResolucion() != null && siniestro.getPuntuacion() != -1) {
                    ps.setDate(6, Date.valueOf(siniestro.getFechaResolucion()));
                    ps.setInt(7, siniestro.getPuntuacion());
                } else {
                    return false;
                }
                ps.setInt(8, siniestro.getCodigoBrigada());
            } else {
                ps.setNull(6, Types.DATE);
                ps.setNull(7, Types.INTEGER);
                ps.setNull(8, Types.INTEGER);
            }
            ps.setInt(9, siniestro.getCodigoSiniestro());
            if (ps.executeUpdate() > 0) {
                resultado = true;
                System.out.println("[SiniestroData] Siniestro agregado");
            } else {
                System.out.println("[SiniestroData] No se pudo agregar al siniestro");
            }
            ps.close();
        } catch (SQLException e) {
            int errorCode = e.getErrorCode();
            System.out.println("[SiniestroData Error " + errorCode + "] " + e.getMessage());
            if (errorCode != 1062) { // Ignorar datos repetidos
                e.printStackTrace();
            }
        }
        return resultado;
    }

    public Siniestro buscarSiniestro(int codigoSiniestro) {
        Siniestro siniestro = null;
        try {
            String sql = "SELECT * FROM siniestro WHERE codigoSiniestro=?;";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, codigoSiniestro);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                siniestro = new Siniestro();
                siniestro.setTipo(rs.getString("tipo"));
                siniestro.setFechaSiniestro(rs.getDate("fechaSiniestro").toLocalDate());
                siniestro.setCoordenadaX(rs.getInt("coordenadaX"));
                siniestro.setCoordenadaY(rs.getInt("coordenadaY"));
                siniestro.setDetalles(rs.getString("detalles"));
                if (siniestro.getCodigoBrigada() == -1) {
                    siniestro.setFechaResolucion(null);
                    siniestro.setPuntuacion(-1);
                } else {
                    siniestro.setFechaResolucion(rs.getDate("fechaResolucion").toLocalDate());
                    siniestro.setPuntuacion(rs.getInt("puntuacion"));
                }
                siniestro.setCodigoBrigada(rs.getInt("codigoSiniestro"));
                System.out.println("[SiniestroData] Siniestro con codigo '" + codigoSiniestro + "' encontrada");
            } else {
                System.out.println("[SiniestroData] No se ha encontrado a la siniestro '" + codigoSiniestro + "'");
            }
            ps.close();
        } catch (SQLException e) {
            int errorCode = e.getErrorCode();
            System.out.println("[SiniestroData Error " + errorCode + "] " + e.getMessage());
            e.printStackTrace();
        }
        return siniestro;
    }

    public List<Siniestro> listarSiniestros() {
        List<Siniestro> siniestros = new ArrayList();
        try {
            String sql = "SELECT * FROM siniestro";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            Siniestro siniestro;
            while (rs.next()) {
                siniestro = new Siniestro();
                siniestro.setTipo(rs.getString("tipo"));
                siniestro.setFechaSiniestro(rs.getDate("fechaSiniestro").toLocalDate());
                siniestro.setCoordenadaX(rs.getInt("coordenadaX"));
                siniestro.setCoordenadaY(rs.getInt("coordenadaY"));
                siniestro.setDetalles(rs.getString("detalles"));
                if (siniestro.getCodigoBrigada() == -1) {
                    siniestro.setFechaResolucion(null);
                    siniestro.setPuntuacion(-1);
                } else {
                    siniestro.setFechaResolucion(rs.getDate("fechaResolucion").toLocalDate());
                    siniestro.setPuntuacion(rs.getInt("puntuacion"));
                }
                siniestro.setCodigoBrigada(rs.getInt("codigoSiniestro"));
            }
            ps.close();
        } catch (SQLException e) {
            int errorCode = e.getErrorCode();
            System.out.println("[SiniestroData Error " + errorCode + "] " + e.getMessage());
            e.printStackTrace();
        }
        return siniestros;
    }

    public boolean modificarSiniestro(Siniestro siniestro) {
        boolean resultado = false;
        try {
            String sql;
            sql = "UPDATE siniestro SET tipo=?, fechaSiniestro=?, coordenadaX=?, coordenadaY=?, detalles=?, fechaResolucion=?, puntuacion=?, codigoBrigada=? WHERE codigoSiniestro=?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, siniestro.getTipo());
            ps.setDate(2, Date.valueOf(siniestro.getFechaSiniestro()));
            ps.setInt(3, siniestro.getCoordenadaX());
            ps.setInt(4, siniestro.getCoordenadaY());
            ps.setString(5, siniestro.getDetalles());
            if (siniestro.getCodigoBrigada() == -1) {
                ps.setNull(6, Types.DATE);
                ps.setNull(7, Types.INTEGER);
                ps.setNull(8, Types.INTEGER);
            } else {
                if (siniestro.getFechaResolucion() == null && siniestro.getPuntuacion() == -1) {
                    ps.setNull(6, Types.DATE);
                    ps.setNull(7, Types.INTEGER);
                } else if (siniestro.getFechaResolucion() != null && siniestro.getPuntuacion() != -1) {
                    ps.setDate(6, Date.valueOf(siniestro.getFechaResolucion()));
                    ps.setInt(7, siniestro.getPuntuacion());
                } else {
                    return false;
                }
                ps.setInt(8, siniestro.getCodigoBrigada());
            }
            ps.setInt(9, siniestro.getCodigoSiniestro());
            if (ps.executeUpdate() > 0) {
                resultado = true;
                System.out.println("[SiniestroData] Siniestro agregado");
            } else {
                System.out.println("[SiniestroData] No se pudo agregar al siniestro");
            }
            ps.close();
        } catch (SQLException e) {
            int errorCode = e.getErrorCode();
            System.out.println("[SiniestroData Error " + errorCode + "] " + e.getMessage());
            e.printStackTrace();
        }
        return resultado;
    }

    public boolean asignarBrigada(Siniestro siniestro, Brigada brigada) {
        if (!brigada.isDisponible()) {
            return false;
        }
        boolean resultado = false;
        try {
            String sql;
            sql = "UPDATE siniestro SET codigoBrigada=? WHERE codigoSiniestro=?; UPDATE brigada SET disponible=? WHERE codigoBrigada=?;";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, brigada.getCodigoBrigada());
            ps.setInt(2, siniestro.getCodigoSiniestro());
            ps.setBoolean(3, false);
            ps.setInt(4, brigada.getCodigoBrigada());
            if (ps.executeUpdate() > 0) {
                resultado = true;
                System.out.println("[SiniestroData] Brigada asignada");
            } else {
                System.out.println("[SiniestroData] No se pudo asignar brigada");
            }
            ps.close();
        } catch (SQLException e) {
            int errorCode = e.getErrorCode();
            System.out.println("[SiniestroData Error " + errorCode + "] " + e.getMessage());
            e.printStackTrace();
        }
        return resultado;
        /*
        Siniestro aux = new Siniestro();
        aux.setTipo(siniestro.getTipo());
        aux.setFechaSiniestro(siniestro.getFechaSiniestro());
        aux.setCoordenadaX(siniestro.getCoordenadaX());
        aux.setCoordenadaY(siniestro.getCoordenadaY());
        aux.setDetalles(siniestro.getDetalles());
        aux.setFechaResolucion(siniestro.getFechaResolucion());
        aux.setPuntuacion(siniestro.getPuntuacion());
        //aux.setCodigoBrigada(siniestro.getCodigoBrigada());
        aux.setCodigoBrigada(brigada.getCodigoBrigada());
        aux.setCodigoSiniestro(siniestro.getCodigoSiniestro());
        return modificarSiniestro(aux);
         */

    }

    public boolean asignarResolucion(Siniestro siniestro, LocalDate fechaResolucion, int puntaje) {
        if (!(fechaResolucion == null && puntaje == -1) || (fechaResolucion != null && puntaje != -1)) {
            return false;
        }
        boolean resultado = false;
        try {
            String sql;
            sql = "UPDATE siniestro SET fechaResolucion=?, puntuacion=? WHERE codigoSiniestro=?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setDate(1, Date.valueOf(fechaResolucion));
            ps.setInt(2, puntaje);
            ps.setInt(3, siniestro.getCodigoSiniestro());
            if (ps.executeUpdate() > 0) {
                resultado = true;
                System.out.println("[SiniestroData] Resolucion asignada");
            } else {
                System.out.println("[SiniestroData] No se pudo asignar resolucion");
            }
            ps.close();
        } catch (SQLException e) {
            int errorCode = e.getErrorCode();
            System.out.println("[SiniestroData Error " + errorCode + "] " + e.getMessage());
            e.printStackTrace();
        }
        return resultado;
        /*
        Siniestro aux = new Siniestro();
        aux.setTipo(siniestro.getTipo());
        aux.setFechaSiniestro(siniestro.getFechaSiniestro());
        aux.setCoordenadaX(siniestro.getCoordenadaX());
        aux.setCoordenadaY(siniestro.getCoordenadaY());
        aux.setDetalles(siniestro.getDetalles());
        // aux.setFechaResolucion(siniestro.getFechaResolucion());
        aux.setFechaResolucion(fechaResolucion);
        // aux.setPuntuacion(siniestro.getPuntuacion());
        aux.setPuntuacion(puntaje);
        aux.setCodigoBrigada(siniestro.getCodigoBrigada());
        aux.setCodigoSiniestro(siniestro.getCodigoSiniestro());
        return modificarSiniestro(aux);
         */
    }

    public boolean eliminarSiniestro(int codigoSiniestro) {
        boolean resultado = false;
        try {
            String sql = "DELETE FROM siniestro WHERE codigoSiniestro=?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, codigoSiniestro);
            if (ps.executeUpdate() > 0) {
                resultado = true;
                System.out.println("[SiniestroData] Siniestro eliminado");
            } else {
                System.out.println("[SiniestroData] No se pudo eliminar al siniestro");
            }
            ps.close();
        } catch (SQLException e) {
            System.out.println("[SiniestroData Error " + e.getErrorCode() + "] " + e.getMessage());
            e.printStackTrace();
        }
        return resultado;
    }

}
