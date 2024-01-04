package lab1proyectofinal.accesoADatos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lab1proyectofinal.entidades.Brigada;
import lab1proyectofinal.entidades.Cuartel;
import lab1proyectofinal.entidades.Emergencia;

/**
 *
 * @author Grupo-3
 */
public class EmergenciaData {
    
    private static Cuartel cuartelNulo = null;
    private static Brigada brigadaNula = null;    

    private final Connection connection;

    public EmergenciaData() {
        this.connection = Conexion.getInstance();        
    }

    // revisado
    public static void inicializar() {
        CuartelData cuartelData = new CuartelData();
        BrigadaData brigadaData = new BrigadaData();
        Cuartel cuartel = cuartelData.buscarCuartelPorNombreActivosInactivos(Utils.nombreEntidadNula);
        if (cuartel == null) {
            cuartel = new Cuartel(Utils.nombreEntidadNula, "", 0, 0, "0", "");
            if (!cuartelData.guardarCuartel(cuartel) || !cuartelData.eliminarCuartelPorNombre(Utils.nombreEntidadNula)) {
                System.out.println("[EmergenciaData.inicializar] "
                        + "Error al inicializar");
                return;
            }
        }

        Brigada brigada = brigadaData.buscarBrigadaPorNombreActivosInactivos(Utils.nombreEntidadNula);
        if (brigada == null) {
            brigada = new Brigada(Utils.nombreEntidadNula, "", false, cuartel);
            if (!brigadaData.guardarBrigada(brigada) || !brigadaData.eliminarBrigadaPorNombre(Utils.nombreEntidadNula)) {
                System.out.println("[EmergenciaData.inicializar] "
                        + "Error al inicializar");
                return;
            }
        }

        cuartel.setEstado(false);
        brigada.setEstado(false);
        
        cuartelNulo = cuartel;
        brigadaNula = brigada;
    }

    // revisado, no se me ocurre para qué fue creado
    public static Cuartel obtenerCuartelNulo() {
        if (cuartelNulo == null) {
            inicializar();
        }
        return cuartelNulo;
    }

    // revisado
    public static Brigada obtenerBrigadaNula() {
        if (brigadaNula == null) {
            inicializar();
        }
        return brigadaNula;
    }

    // revisado
    public boolean guardarEmergencia(Emergencia emergencia) {
        if (emergencia.getCodigoEmergencia() != Utils.codigoNoEstablecido) {
            System.out.println("[EmergenciaData.guardarEmergencia] "
                    + "Error: no se puede guardar. "
                    + "Tiene codigoEmergencia definido: "
                    + emergencia.debugToString());
            return false;
        }

        // corroborando la validez de los datos ingresados
        float desempenio = emergencia.getDesempenio();
        LocalDateTime fecHorIni = emergencia.getFechaHoraInicio();
        LocalDateTime fecHorRes = emergencia.getFechaHoraResolucion();
        LocalDateTime fecActual = LocalDateTime.now();
        if (fecHorIni.isAfter(fecActual)) {
            System.out.println("[EmergenciaData.guardarEmergencia] Error: el inicio de la emergencia "
                    + "(" + fecHorIni + ") no puede ser posterior a la actualidad");
            return false;
        }
        if (fecHorRes != null && desempenio != Utils.desempenioNoEstablecida) {
            if (fecHorIni.isAfter(fecHorRes)) {
                System.out.println("[EmergenciaData.guardarEmergencia] Error: el inicio de la "
                        + "emergencia (" + fecHorIni + ") no puede ser posterior a la "
                        + "resolución de la misma");
                return false;
            } else if (fecHorRes.isAfter(fecActual)) {
                System.out.println("[EmergenciaData.guardarEmergencia] Error: la resolución de la "
                        + "emergencia (" + fecHorRes + ") no puede ser posterior a la actualidad");
                return false;
            } else if (desempenio < Emergencia.PUNTUACION_MIN || desempenio
                    > Emergencia.PUNTUACION_MAX) {
                System.out.println("[EmergenciaData.guardarEmergencia] Error: desempenio "
                        + "(" + desempenio + ") fuera de rango");
                return false;
            }
        } else if (fecHorRes != null || desempenio != Utils.desempenioNoEstablecida) {
            System.out.println("[EmergenciaData.guardarEmergencia] Error: no se puede establecer una "
                    + "fecha de resolución sin establecer una puntuación, ni viceversa");
            return false;
        }

        boolean resultado = false;
        try {
            String sql = "INSERT INTO emergencia(tipo, fechaHoraInicio, coordenadaX, coordenadaY, "
                    + "detalles, codigoBrigada, fechaHoraResolucion, desempenio) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, emergencia.getTipo());
            ps.setTimestamp(2, Timestamp.valueOf(fecHorIni));
            ps.setDouble(3, emergencia.getCoordenadaX());
            ps.setDouble(4, emergencia.getCoordenadaY());
            ps.setString(5, emergencia.getDetalles());
            if (emergencia.getBrigada() != null) {
                ps.setInt(6, emergencia.getBrigada().getCodigoBrigada());
            } else {
                ps.setInt(6, EmergenciaData.obtenerBrigadaNula().getCodigoBrigada());
            }
            if (fecHorRes == null && desempenio == Utils.desempenioNoEstablecida) {
                ps.setNull(7, Types.TIMESTAMP);
            } else {
                ps.setTimestamp(7, Timestamp.valueOf(fecHorRes));
            }
            ps.setFloat(8, desempenio);
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                emergencia.setCodigoEmergencia(rs.getInt(1));
                resultado = true;
                System.out.println("[EmergenciaData.guardarEmergencia] "
                        + "Agregado: " + emergencia.debugToString());
            } else {
                System.out.println("[EmergenciaData.guardarEmergencia] "
                        + "No se pudo agregar: " + emergencia.debugToString());
            }
            ps.close();
        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) { // Informar datos repetidos
                System.out.println("[EmergenciaData.guardarEmergencia] "
                        + "Error: entrada duplicada para " + emergencia.debugToString());
            } else {
                e.printStackTrace();
            }
        }
        return resultado;
    }

    // revisado
    public Emergencia buscarEmergencia(int codigoEmergencia) {
        Emergencia emergencia = null;
        try {
            String sql = "SELECT * FROM emergencia "
                    + "JOIN brigada ON (emergencia.codigoBrigada = brigada.codigoBrigada "
                    + "AND codigoEmergencia = ?) "
                    + "JOIN cuartel ON (brigada.codigoCuartel = cuartel.codigoCuartel);";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, codigoEmergencia);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                emergencia = Utils.obtenerDeResultSetEmergencia(rs);
                System.out.println("[EmergenciaData.buscarEmergencia] "
                        + "Encontrado: " + emergencia.debugToString());
            } else {
                System.out.println("[EmergenciaData.buscarEmergencia] "
                        + "No se ha encontrado con codigoEmergencia=" + codigoEmergencia);
            }
            ps.close();
        } catch (SQLException e) {
            System.out.println("[EmergenciaData.buscarEmergencia] "
                    + "Error " + e.getErrorCode() + " " + e.getMessage());
            e.printStackTrace();
        }
        return emergencia;
    }

    // revisado
    public List<Emergencia> listarEmergencias() {
        List<Emergencia> emergencias = null;
        try {
            String sql = "SELECT * FROM emergencia "
                    + "JOIN brigada ON (emergencia.codigoBrigada = brigada.codigoBrigada) "
                    + "JOIN cuartel ON (brigada.codigoCuartel = cuartel.codigoCuartel);";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            emergencias = new ArrayList();
            while (rs.next()) {
                Emergencia emergencia = Utils.obtenerDeResultSetEmergencia(rs);
                emergencias.add(emergencia);
            }
            System.out.println("[EmergenciaData.listarEmergencias] "
                    + "Cantidad de emergencias: " + emergencias.size());
            ps.close();
        } catch (SQLException e) {
            System.out.println("[EmergenciaData.listarEmergencias] "
                    + "Error " + e.getErrorCode() + " " + e.getMessage());
            e.printStackTrace();
        }
        return emergencias;
    }

    // revisado
    public List<Emergencia> listarEmergenciasResueltos() {
        List<Emergencia> emergencias = null;
        try {
            String sql = "SELECT * FROM emergencia "
                    + "JOIN brigada ON (emergencia.codigoBrigada = brigada.codigoBrigada "
                    + "AND emergencia.desempenio != -1) "
                    + "JOIN cuartel ON (brigada.codigoCuartel = cuartel.codigoCuartel);";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            emergencias = new ArrayList();
            while (rs.next()) {
                Emergencia emergencia = Utils.obtenerDeResultSetEmergencia(rs);
                emergencias.add(emergencia);
            }
            System.out.println("[EmergenciaData.listarEmergenciasResueltos] "
                    + "Cantidad de emergencias: " + emergencias.size());
            ps.close();
        } catch (SQLException e) {
            System.out.println("[EmergenciaData.listarEmergenciasResueltos] "
                    + "Error " + e.getErrorCode() + " " + e.getMessage());
            e.printStackTrace();
        }
        return emergencias;
    }

    // revisado
    public List<Emergencia> listarEmergenciasSinResolucion() {
        List<Emergencia> emergencias = null;
        try {
            String sql = "SELECT * FROM emergencia "
                    + "JOIN brigada ON (emergencia.codigoBrigada = brigada.codigoBrigada "
                    + "AND emergencia.desempenio = -1) "
                    + "JOIN cuartel ON (brigada.codigoCuartel = cuartel.codigoCuartel);";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            emergencias = new ArrayList();
            while (rs.next()) {
                Emergencia emergencia = Utils.obtenerDeResultSetEmergencia(rs);
                emergencias.add(emergencia);
            }
            System.out.println("[EmergenciaData.listarEmergenciasSinResolucion] "
                    + "Cantidad de emergencias: " + emergencias.size());
            ps.close();
        } catch (SQLException e) {
            System.out.println("[EmergenciaData.listarEmergenciasSinResolucion] "
                    + "Error " + e.getErrorCode() + " " + e.getMessage());
            e.printStackTrace();
        }
        return emergencias;
    }

    // revisado
    public List<Emergencia> listarEmergenciasInicioEntreFechas(LocalDateTime fecha1, LocalDateTime fecha2) {
        List<Emergencia> emergencias = null;
        try {
            String sql = "SELECT * FROM emergencia "
                    + "JOIN brigada ON (emergencia.codigoBrigada = brigada.codigoBrigada "
                    + "AND fechaHoraInicio BETWEEN (? AND ?)) "
                    + "JOIN cuartel ON (brigada.codigoCuartel = cuartel.codigoCuartel);";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setTimestamp(1, Timestamp.valueOf(fecha1));
            ps.setTimestamp(2, Timestamp.valueOf(fecha2));
            ResultSet rs = ps.executeQuery();
            emergencias = new ArrayList();
            while (rs.next()) {
                Emergencia emergencia = Utils.obtenerDeResultSetEmergencia(rs);
                emergencias.add(emergencia);
            }
            System.out.println("[EmergenciaData.listarEmergenciasEntreFechas] "
                    + "Cantidad de emergencias: " + emergencias.size());
            ps.close();
        } catch (SQLException e) {
            System.out.println("[EmergenciaData.listarEmergenciasEntreFechas] "
                    + "Error " + e.getErrorCode() + " " + e.getMessage());
            e.printStackTrace();
        }
        return emergencias;
    }

    // revisado
    public List<Emergencia> listarEmergenciasInicioFinEntreFechas(LocalDateTime fecha1, LocalDateTime fecha2) {
        List<Emergencia> emergencias = null;
        try {
            String sql = "SELECT * FROM emergencia "
                    + "JOIN brigada ON (emergencia.codigoBrigada = brigada.codigoBrigada "
                    + "AND fechaHoraInicio >= ? AND fechaHoraResolucion <= ?) "
                    + "JOIN cuartel ON (brigada.codigoCuartel = cuartel.codigoCuartel);";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setTimestamp(1, Timestamp.valueOf(fecha1));
            ps.setTimestamp(2, Timestamp.valueOf(fecha2));
            ResultSet rs = ps.executeQuery();
            emergencias = new ArrayList();
            while (rs.next()) {
                Emergencia emergencia = Utils.obtenerDeResultSetEmergencia(rs);
                emergencias.add(emergencia);
            }
            System.out.println("[EmergenciaData.listarEmergenciasInicioFinEntreFechas] "
                    + "Cantidad de emergencias: " + emergencias.size());
            ps.close();
        } catch (SQLException e) {
            System.out.println("[EmergenciaData.listarEmergenciasInicioFinEntreFechas] "
                    + "Error " + e.getErrorCode() + " " + e.getMessage());
            e.printStackTrace();
        }
        return emergencias;
    }

    // revisado
    public List<Brigada> listarBrigadasAsignablesConLaEspecialidad(Emergencia emergencia) {
        List<Brigada> brigadas = null;
        try {
            String sql = "SELECT * FROM brigada "
                    + "JOIN cuartel ON (brigada.codigoCuartel = cuartel.codigoCuartel "
                    + "AND cuartel.estado = true "
                    + "AND brigada.estado = true "
                    + "AND brigada.enServicio = true "
                    + "AND (SELECT COUNT(codigoBrigada) FROM bombero "
                    + "WHERE bombero.codigoBrigada = brigada.codigoBrigada "
                    + "AND bombero.estado = true) = 5 "
                    + "AND (SELECT COUNT(codigoBrigada) FROM emergencia "
                    + "WHERE emergencia.codigoBrigada = brigada.codigoBrigada AND emergencia.desempenio = -1) = 0)"
                    + "AND brigada.especialidad = ?;";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, emergencia.getTipo());
            ResultSet rs = ps.executeQuery();
            brigadas = new ArrayList();
            while (rs.next()) {
                Brigada brigada = Utils.obtenerDeResultSetBrigada(rs);
                brigadas.add(brigada);
            }
            System.out.println("[BrigadaData.listarBrigadasAsignables] "
                    + "Cantidad de brigadas: " + brigadas.size());
            ps.close();
        } catch (SQLException e) {
            System.out.println("[BrigadaData.listarBrigadasAsignables] "
                    + "Error" + e.getErrorCode() + ": " + e.getMessage());
            e.printStackTrace();
        }
        return brigadas;
    }

    // revisado
    public List<Brigada> listarBrigadasAsignablesSinLaEspecialidad(Emergencia emergencia) {
        List<Brigada> brigadas = null;
        try {
            String sql = "SELECT * FROM brigada "
                    + "JOIN cuartel ON (brigada.codigoCuartel = cuartel.codigoCuartel "
                    + "AND cuartel.estado = true "
                    + "AND brigada.estado = true "
                    + "AND brigada.enServicio = true "
                    + "AND (SELECT COUNT(codigoBrigada) FROM bombero "
                    + "WHERE bombero.codigoBrigada = brigada.codigoBrigada "
                    + "AND bombero.estado = true) = 5 "
                    + "AND (SELECT COUNT(codigoBrigada) FROM emergencia "
                    + "WHERE emergencia.codigoBrigada = brigada.codigoBrigada AND emergencia.desempenio = -1) = 0)"
                    + "AND brigada.especialidad != ?;";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, emergencia.getTipo());
            ResultSet rs = ps.executeQuery();
            brigadas = new ArrayList();
            while (rs.next()) {
                Brigada brigada = Utils.obtenerDeResultSetBrigada(rs);
                brigadas.add(brigada);
            }
            System.out.println("[BrigadaData.listarBrigadasAsignables] "
                    + "Cantidad de brigadas: " + brigadas.size());
            ps.close();
        } catch (SQLException e) {
            System.out.println("[BrigadaData.listarBrigadasAsignables] "
                    + "Error" + e.getErrorCode() + ": " + e.getMessage());
            e.printStackTrace();
        }
        return brigadas;
    }   

    // revisado
    public boolean modificarEmergencia(Emergencia emergencia) {
        if (emergencia.getCodigoEmergencia() == Utils.codigoNoEstablecido) {
            System.out.println("[EmergenciaData.modificarEmergencia] "
                    + "Error: no se pudo modificar. "
                    + "Emergencia dado de baja o sin codigoEmergencia definido: "
                    + emergencia.debugToString());
            return false;
        }

        // corroborando la validez de los datos ingresados
        float desempenio = emergencia.getDesempenio();
        LocalDateTime fecHorIni = emergencia.getFechaHoraInicio();
        LocalDateTime fecHorRes = emergencia.getFechaHoraResolucion();
        LocalDateTime fecActual = LocalDateTime.now();
        if (fecHorIni.isAfter(fecActual)) {
            System.out.println("[EmergenciaData.guardarEmergencia] Error: el inicio de la emergencia "
                    + "(" + fecHorIni + ") no puede ser posterior a la actualidad");
            return false;
        }
        if (fecHorRes != null && desempenio != Utils.desempenioNoEstablecida) {
            if (fecHorIni.isAfter(fecHorRes)) {
                System.out.println("[EmergenciaData.guardarEmergencia] Error: el inicio de la "
                        + "emergencia (" + fecHorIni + ") no puede ser posterior a la "
                        + "resolución de la misma");
                return false;
            } else if (fecHorRes.isAfter(fecActual)) {
                System.out.println("[EmergenciaData.guardarEmergencia] Error: la resolución de la emergencia "
                        + "(" + fecHorRes + ") no puede ser posterior a la actualidad");
                return false;
            } else if (desempenio < Emergencia.PUNTUACION_MIN || desempenio > Emergencia.PUNTUACION_MAX) {
                System.out.println("[EmergenciaData.guardarEmergencia] Error: desempenio (" + desempenio + ") fuera "
                        + "de rango");
                return false;
            }
        } else if (fecHorRes != null || desempenio != Utils.desempenioNoEstablecida) {
            System.out.println("[EmergenciaData.guardarEmergencia] Error: no se puede establecer una fecha "
                    + "de resolución sin establecer una puntuación, ni viceversa");
            return false;
        }

        boolean resultado = false;
        try {
            String sql;
            sql = "UPDATE emergencia SET tipo=?, fechaHoraInicio=?, coordenadaX=?, coordenadaY=?, detalles=?, "
                    + "codigoBrigada=?, fechaHoraResolucion=?, desempenio=? "
                    + "WHERE codigoEmergencia = ?;";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, emergencia.getTipo());
            ps.setTimestamp(2, Timestamp.valueOf(emergencia.getFechaHoraInicio()));
            ps.setDouble(3, emergencia.getCoordenadaX());
            ps.setDouble(4, emergencia.getCoordenadaY());
            ps.setString(5, emergencia.getDetalles());
            if (emergencia.getBrigada() != null) {
                ps.setInt(6, emergencia.getBrigada().getCodigoBrigada());
            } else {
                ps.setInt(6, EmergenciaData.obtenerBrigadaNula().getCodigoBrigada());
            }
            if (fecHorRes == null && desempenio == Utils.desempenioNoEstablecida) {
                ps.setNull(7, Types.TIMESTAMP);
                ps.setFloat(8, desempenio);
            } else {
                ps.setTimestamp(7, Timestamp.valueOf(fecHorRes));
                ps.setFloat(8, desempenio);
            }
            ps.setInt(9, emergencia.getCodigoEmergencia());
            if (ps.executeUpdate() > 0) {
                resultado = true;
                System.out.println("[EmergenciaData.modificarEmergencia] "
                        + "Modificado: " + emergencia.debugToString());
            } else {
                System.out.println("[EmergenciaData.modificarEmergencia] "
                        + "No se pudo modificar: " + emergencia.debugToString());
            }
            ps.close();
        } catch (SQLException e) {
            System.out.println("[EmergenciaData.modificarEmergencia] "
                    + "Error " + e.getErrorCode() + " " + e.getMessage());
            e.printStackTrace();
        }
        return resultado;
    }

    // revisado, potencialmente innecesario
    public boolean eliminarEmergencia(int codigoEmergencia) {
        boolean resultado = false;
        try {
            String sql = "DELETE FROM emergencia "
                    + "WHERE codigoEmergencia = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, codigoEmergencia);
            if (ps.executeUpdate() > 0) {
                resultado = true;
                System.out.println("[EmergenciaData.eliminarEmergencia] "
                        + "Eliminado: codigoEmergencia=" + codigoEmergencia);
            } else {
                System.out.println("[EmergenciaData.eliminarEmergencia] "
                        + "No se pudo eliminar: codigoCuartel=" + codigoEmergencia);
            }
            ps.close();
        } catch (SQLException e) {
            System.out.println("[EmergenciaData.eliminarEmergencia] "
                    + "Error " + e.getErrorCode() + " " + e.getMessage());
            e.printStackTrace();
        }
        return resultado;
    }

}
