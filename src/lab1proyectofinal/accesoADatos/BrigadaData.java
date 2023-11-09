package lab1proyectofinal.accesoADatos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import lab1proyectofinal.entidades.*;
import org.mariadb.jdbc.Statement;

/**
 *
 * @author Grupo-3
 */
public class BrigadaData {

    /**
     * SUJETO A CAMBIOS
     */
    private final Connection connection;

    public BrigadaData() {
        this.connection = Conexion.getInstance();
    }

    public boolean crearFalsaBrigada() {
        boolean resultado = false;
        try {
            String sql = "INSERT INTO brigada(nombreBrigada, especialidad, tratandoSiniestro, codigoCuartel, codigoBrigada, estado) VALUES ('brigada inexistente', '---', false, -1, -1, false);";
            PreparedStatement ps = connection.prepareStatement(sql);
            if (ps.executeUpdate() > 0) {
                resultado = true;
                System.out.println("[BrigadaData] Brigada falsa agregada");
            } else {
                System.out.println("[BrigadaData] No se pudo agregar a la brigada falsa");
            }
            ps.close();
        } catch (SQLException e) {
            int errorCode = e.getErrorCode();
            System.out.println("[BrigadaData Error " + errorCode + "] " + e.getMessage());
            if (errorCode != 1062) { // Ignorar datos repetidos
                e.printStackTrace();
            }
        }
        return resultado;
    }

    public boolean guardarBrigada(Brigada brigada) {
        boolean resultado = false;
        try {
            String sql;
            if (brigada.getCodigoBrigada() != -1) {
                sql = "INSERT INTO brigada(nombreBrigada, especialidad, tratandoSiniestro, codigoCuartel, estado, codigoBrigada) VALUES (?, ?, ?, ?, ?, ?);";
            } else {
                sql = "INSERT INTO brigada(nombreBrigada, especialidad, tratandoSiniestro, codigoCuartel, estado) VALUES (?, ?, ?, ?, ?);";
            }
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, brigada.getNombreBrigada());
            ps.setString(2, brigada.getEspecialidad());
            ps.setBoolean(3, brigada.isTratandoSiniestro());
            ps.setInt(4, brigada.getCuartel().getCodigoCuartel());
            ps.setBoolean(5, brigada.isEstado());
            if (brigada.getCodigoBrigada() != -1) {
                ps.setInt(6, brigada.getCodigoBrigada());
            }
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                brigada.setCodigoBrigada(rs.getInt(1));
                resultado = true;
                System.out.println("[BrigadaData] Brigada agregada");
            } else {
                System.out.println("[BrigadaData] No se pudo agregar a la brigada");
            }
            ps.close();
        } catch (SQLException e) {
            int errorCode = e.getErrorCode();
            System.out.println("[BrigadaData Error " + errorCode + "] " + e.getMessage());
            if (errorCode != 1062) { // Ignorar datos repetidos
                e.printStackTrace();
            }
        }
        return resultado;
    }

    public Brigada buscarBrigada(int codigoBrigada) {
        Brigada brigada = null;
        try {
            String sql = "SELECT * FROM brigada, cuartel "
                    + "WHERE codigoBrigada=? AND brigada.estado=true AND brigada.codigoCuartel = cuartel.codigoCuartel;";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, codigoBrigada);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                brigada = Utils.obtenerDeResultSetBrigada(rs);
                System.out.println("[BrigadaData] Brigada con codigo=" + codigoBrigada + " encontrada");
            } else {
                System.out.println("[BrigadaData] No se ha encontrado a la brigada con codigo=" + codigoBrigada);
            }
            ps.close();
        } catch (SQLException e) {
            System.out.println("[BrigadaData Error " + e.getErrorCode() + "] " + e.getMessage());
            e.printStackTrace();
        }
        return brigada;
    }

    public Brigada buscarBrigadaPorNombre(String nombreBrigada) {
        Brigada brigada = null;
        try {
            String sql = "SELECT * FROM brigada, cuartel "
                    + "WHERE nombreBrigada = ? AND brigada.estado = true AND brigada.codigoCuartel = cuartel.codigoCuartel;";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, nombreBrigada);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                brigada = Utils.obtenerDeResultSetBrigada(rs);
                System.out.println("[BrigadaData] Brigada '" + nombreBrigada + "' encontrada");
            } else {
                System.out.println("[BrigadaData] No se ha encontrado a la brigada '" + nombreBrigada + "'");
            }
            ps.close();
        } catch (SQLException e) {
            System.out.println("[BrigadaData Error " + e.getErrorCode() + "] " + e.getMessage());
            e.printStackTrace();
        }
        return brigada;
    }

    public List<Brigada> listarBrigadas() {
        List<Brigada> brigadas = new ArrayList();
        try {
            String sql = "SELECT * FROM brigada, cuartel "
                    + "WHERE brigada.estado = true AND brigada.codigoCuartel = cuartel.codigoCuartel;";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            Brigada brigada;
            while (rs.next()) {
                brigada = Utils.obtenerDeResultSetBrigada(rs);
                brigadas.add(brigada);
            }
            ps.close();
        } catch (SQLException e) {
            System.out.println("[BrigadaData Error " + e.getErrorCode() + "] " + e.getMessage());
            e.printStackTrace();
        }
        return brigadas;
    }

    // brigada disponible: tratandoSiniestro=true & cantBomberos=5 (la brigada se encuentra en el cuartel y tiene cantidad de integrantes requerida para actuar)
    public List<Brigada> listarBrigadasDisponibles() {
        List<Brigada> brigadas = new ArrayList();
        try {
            String sql = "SELECT * FROM brigada, cuartel "
                    + "WHERE brigada.estado = true AND brigada.tratandoSiniestro = true AND brigada.cantBomberos=5 AND brigada.codigoCuartel = cuartel.codigoCuartel;";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            Brigada brigada;
            while (rs.next()) {
                brigada = Utils.obtenerDeResultSetBrigada(rs);
                brigadas.add(brigada);
            }
            ps.close();
        } catch (SQLException e) {
            System.out.println("[BrigadaData Error " + e.getErrorCode() + "] " + e.getMessage());
            e.printStackTrace();
        }
        return brigadas;
    }

    // brigada ocupada: tratandoSiniestro=false (se la envió a tratar una emergencia)
    public List<Brigada> listarBrigadasNoDisponibles() {
        List<Brigada> brigadas = new ArrayList();
        try {
            String sql = "SELECT * FROM brigada, cuartel "
                    + "WHERE brigada.estado = true AND brigada.tratandoSiniestro = false AND brigada.codigoCuartel = cuartel.codigoCuartel;";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            Brigada brigada;
            while (rs.next()) {
                brigada = Utils.obtenerDeResultSetBrigada(rs);
                brigadas.add(brigada);
            }
            ps.close();
        } catch (SQLException e) {
            System.out.println("[BrigadaData Error " + e.getErrorCode() + "] " + e.getMessage());
            e.printStackTrace();
        }
        return brigadas;
    }

    // brigada incompleta: cantBomberos!=5 (la brigada NO tiene la cantidad de integrantes requerida para actuar)
    public List<Brigada> listarBrigadasIncompletas() {
        List<Brigada> brigadas = new ArrayList();
        try {
            String sql = "SELECT * FROM brigada, cuartel "
                    + "WHERE brigada.estado = true AND brigada.cantBomberos!=5 AND brigada.codigoCuartel = cuartel.codigoCuartel;";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            Brigada brigada;
            while (rs.next()) {
                brigada = Utils.obtenerDeResultSetBrigada(rs);
                brigadas.add(brigada);
            }
            ps.close();
        } catch (SQLException e) {
            System.out.println("[BrigadaData Error " + e.getErrorCode() + "] " + e.getMessage());
            e.printStackTrace();
        }
        return brigadas;
    }

    public List<Bombero> listarBomberosDeBrigada(Brigada brigada) {
        List<Bombero> bomberos = new ArrayList();
        try {
            String sql = "SELECT * FROM bombero WHERE codigoBrigada = ? AND estado = true;";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, brigada.getCodigoBrigada());
            ResultSet rs = ps.executeQuery();
            Bombero bombero;
            while (rs.next()) {
                bombero = Utils.obtenerDeResultSetBombero(rs, brigada);
                bomberos.add(bombero);
            }
            ps.close();
        } catch (SQLException e) {
            System.out.println("[BrigadaData Error " + e.getErrorCode() + "] " + e.getMessage());
            e.printStackTrace();
        }
        return bomberos;
    }

    public boolean modificarBrigada(Brigada brigada) {
        boolean resultado = false;
        try {
            String sql = "UPDATE brigada SET nombreBrigada=?, especialidad=?, tratandoSiniestro=?, codigoCuartel=? WHERE codigoBrigada=? AND estado=true";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, brigada.getNombreBrigada());
            ps.setString(2, brigada.getEspecialidad());
            ps.setBoolean(3, brigada.isTratandoSiniestro());
            ps.setInt(4, brigada.getCuartel().getCodigoCuartel());
            ps.setInt(5, brigada.getCodigoBrigada());
            if (ps.executeUpdate() > 0) {
                resultado = true;
                System.out.println("[BrigadaData] Brigada modificada");
            } else {
                System.out.println("[BrigadaData] No se pudo modificar a la brigada");
            }
            ps.close();
        } catch (SQLException e) {
            System.out.println("[BrigadaData Error " + e.getErrorCode() + "] " + e.getMessage());
            e.printStackTrace();
        }
        return resultado;
    }

    public boolean eliminarBrigada(int codigoBrigada) {
        boolean resultado = false;
        try {
            String sql = "UPDATE brigada SET estado=false WHERE codigoBrigada=? AND estado=true";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, codigoBrigada);
            if (ps.executeUpdate() > 0) {
                resultado = true;
                System.out.println("[BrigadaData] Brigada eliminada");
            } else {
                System.out.println("[BrigadaData] No se pudo eliminar a la brigada");
            }
            ps.close();
        } catch (SQLException e) {
            System.out.println("[BrigadaData Error " + e.getErrorCode() + "] " + e.getMessage());
            e.printStackTrace();
        }
        return resultado;
    }

    public boolean eliminarBrigadaPorNombre(String nombreBrigada) {
        boolean resultado = false;
        try {
            String sql = "UPDATE brigada SET estado=false WHERE nombreBrigada=? AND estado=true";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, nombreBrigada);
            if (ps.executeUpdate() > 0) {
                resultado = true;
                System.out.println("[BrigadaData] Brigada eliminada");
            } else {
                System.out.println("[BrigadaData] No se pudo eliminar a la brigada");
            }
            ps.close();
        } catch (SQLException e) {
            System.out.println("[BrigadaData Error " + e.getErrorCode() + "] " + e.getMessage());
            e.printStackTrace();
        }
        return resultado;
    }
}
