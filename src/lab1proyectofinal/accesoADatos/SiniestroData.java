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
import lab1proyectofinal.entidades.*;

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
                sql = "INSERT INTO siniestro(tipo, fechaHoraInicio, coordenadaX, coordenadaY, detalles, codigoBrigada, fechaHoraResolucion, puntuacion, codigoSiniestro) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            } else {
                sql = "INSERT INTO siniestro(tipo, fechaHoraInicio, coordenadaX, coordenadaY, detalles, codigoBrigada, fechaHoraResolucion, puntuacion) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            }
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, siniestro.getTipo());
            ps.setTimestamp(2, Timestamp.valueOf(siniestro.getFechaHoraInicio()));
            ps.setInt(3, siniestro.getCoordenadaX());
            ps.setInt(4, siniestro.getCoordenadaY());
            ps.setString(5, siniestro.getDetalles());
            ps.setInt(6, siniestro.getBrigada().getCodigoBrigada());
            int puntuacion = siniestro.getPuntuacion();
            if (siniestro.getFechaHoraResolucion() == null && puntuacion == Siniestro.PUNTUACION_NIL) {
                ps.setNull(7, Types.TIMESTAMP);
                ps.setInt(8, puntuacion);
            } else if (siniestro.getFechaHoraResolucion() != null && puntuacion != Siniestro.PUNTUACION_NIL) {
                if (puntuacion < Siniestro.PUNTUACION_MIN || puntuacion > Siniestro.PUNTUACION_MAX) {
                    ps.close();
                    System.out.println("[SiniestroData] Error al agregar. Puntuacion fuera de rango");
                    return false;
                }
                ps.setTimestamp(7, Timestamp.valueOf(siniestro.getFechaHoraResolucion()));
                ps.setInt(8, puntuacion);
            } else {
                ps.close();
                System.out.println("[SiniestroData] Error al agregar. Los datos de la resolucion son inconsistentes");
                return false;
            }
            if (siniestro.getCodigoSiniestro() != -1) {
                ps.setInt(9, siniestro.getCodigoSiniestro());
            }
            if (ps.executeUpdate() > 0) {
                resultado = true;
                System.out.println("[SiniestroData] Siniestro agregado");
            } else {
                System.out.println("[SiniestroData] No se pudo agregado el siniestro");
            }
            ps.close();
        } catch (SQLException e) {
            // System.out.println("[SiniestroData Error " + e.getErrorCode() + "] " + e.getMessage());
            // e.printStackTrace();
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
            String sql = "SELECT * FROM siniestro si, brigada bri, cuartel cuar "
                    + "WHERE codigoSiniestro=? "
                    + "AND si.codigoBrigada=bri.codigoBrigada "
                    + "AND bri.codigoCuartel=cuar.codigoCuartel;";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, codigoSiniestro);
            ResultSet rs = ps.executeQuery();
            Cuartel cuartel;
            Brigada brigada;
            if (rs.next()) {
                cuartel = new Cuartel();
                cuartel.setCodigoCuartel(rs.getInt("cuar.codigoCuartel"));
                cuartel.setNombreCuartel(rs.getString("nombreCuartel"));
                cuartel.setDireccion(rs.getString("direccion"));
                cuartel.setCoordenadaX(rs.getInt("coordenadaX"));
                cuartel.setCoordenadaY(rs.getInt("coordenadaY"));
                cuartel.setTelefono(rs.getString("telefono"));
                cuartel.setCorreo(rs.getString("correo"));
                cuartel.setEstado(rs.getBoolean("cuar.estado"));

                brigada = new Brigada();
                brigada.setCodigoBrigada(rs.getInt("bri.codigoBrigada"));
                brigada.setNombreBrigada(rs.getString("nombreBrigada"));
                brigada.setEspecialidad(rs.getString("especialidad"));
                brigada.setEnCuartel(rs.getBoolean("enCuartel"));
                brigada.setCuartel(cuartel);
                brigada.setEstado(rs.getBoolean("bri.estado"));

                siniestro = new Siniestro();
                siniestro.setCodigoSiniestro(rs.getInt("codigoSiniestro"));
                siniestro.setTipo(rs.getString("tipo"));
                siniestro.setFechaHoraInicio(rs.getTimestamp("fechaHoraInicio").toLocalDateTime());
                siniestro.setCoordenadaX(rs.getInt("coordenadaX"));
                siniestro.setCoordenadaY(rs.getInt("coordenadaY"));
                siniestro.setDetalles(rs.getString("detalles"));
                siniestro.setBrigada(brigada);
                // siniestro.setFechaHoraResolucion(rs.getTimestamp("fechaHoraResolucion").toLocalDateTime());
                // siniestro.setPuntuacion(rs.getInt("puntuacion"));
                int puntuacion = rs.getInt("puntuacion");
                if (puntuacion != -1) {
                    siniestro.setFechaHoraResolucion(rs.getTimestamp("fechaHoraResolucion").toLocalDateTime());
                } else {
                    siniestro.setFechaHoraResolucion(null);
                }
                siniestro.setPuntuacion(puntuacion);
                System.out.println("[SiniestroData] Siniestro con codigo '" + codigoSiniestro + "' encontrado");
            } else {
                System.out.println("[SiniestroData] No se ha encontrado el siniestro con código '" + codigoSiniestro + "'");
            }
            ps.close();
        } catch (SQLException e) {
            System.out.println("[SiniestroData Error " + e.getErrorCode() + "] " + e.getMessage());
            e.printStackTrace();
        }
        return siniestro;
    }

    public List<Siniestro> listarSiniestros() {
        List<Siniestro> siniestros = new ArrayList();
        try {
            String sql = "SELECT * FROM siniestro si, brigada bri, cuartel cuar "
                    + "WHERE si.codigoBrigada=bri.codigoBrigada "
                    + "AND bri.codigoCuartel=cuar.codigoCuartel;";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            Cuartel cuartel;
            Brigada brigada;
            Siniestro siniestro;
            if (rs.next()) {
                cuartel = new Cuartel();
                cuartel.setCodigoCuartel(rs.getInt("cuar.codigoCuartel"));
                cuartel.setNombreCuartel(rs.getString("nombreCuartel"));
                cuartel.setDireccion(rs.getString("direccion"));
                cuartel.setCoordenadaX(rs.getInt("coordenadaX"));
                cuartel.setCoordenadaY(rs.getInt("coordenadaY"));
                cuartel.setTelefono(rs.getString("telefono"));
                cuartel.setCorreo(rs.getString("correo"));
                cuartel.setEstado(rs.getBoolean("cuar.estado"));

                brigada = new Brigada();
                brigada.setCodigoBrigada(rs.getInt("codigoBrigada"));
                brigada.setNombreBrigada(rs.getString("nombreBrigada"));
                brigada.setEspecialidad(rs.getString("especialidad"));
                brigada.setEnCuartel(rs.getBoolean("enCuartel"));
                brigada.setCantBomberos(rs.getInt("cantBomberos"));
                brigada.setCuartel(cuartel);
                brigada.setEstado(rs.getBoolean("bri.estado"));

                siniestro = new Siniestro();
                siniestro.setCodigoSiniestro(rs.getInt("codigoSiniestro"));
                siniestro.setTipo(rs.getString("tipo"));
                siniestro.setFechaHoraInicio(rs.getTimestamp("fechaHoraInicio").toLocalDateTime());
                siniestro.setCoordenadaX(rs.getInt("coordenadaX"));
                siniestro.setCoordenadaY(rs.getInt("coordenadaY"));
                siniestro.setDetalles(rs.getString("detalles"));
                siniestro.setBrigada(brigada);
                // siniestro.setFechaHoraResolucion(rs.getTimestamp("fechaHoraResolucion").toLocalDateTime());
                // siniestro.setPuntuacion(rs.getInt("puntuacion"));
                int puntuacion = rs.getInt("puntuacion");
                if (puntuacion != -1) {
                    siniestro.setFechaHoraResolucion(rs.getTimestamp("fechaHoraResolucion").toLocalDateTime());
                } else {
                    siniestro.setFechaHoraResolucion(null);
                }
                siniestro.setPuntuacion(puntuacion);
                siniestros.add(siniestro);
            }
            ps.close();
        } catch (SQLException e) {
            System.out.println("[SiniestroData Error " + e.getErrorCode() + "] " + e.getMessage());
            e.printStackTrace();
        }
        return siniestros;
    }

    public List<Siniestro> listarSiniestrosResueltos() {
        List<Siniestro> siniestros = new ArrayList();
        try {
            String sql = "SELECT * FROM siniestro si, brigada bri, cuartel cuar "
                    + "WHERE si.puntuacion!=-1 AND si.codigoBrigada=bri.codigoBrigada "
                    + "AND bri.codigoCuartel=cuar.codigoCuartel;";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            Cuartel cuartel;
            Brigada brigada;
            Siniestro siniestro;
            if (rs.next()) {
                cuartel = new Cuartel();
                cuartel.setCodigoCuartel(rs.getInt("cuar.codigoCuartel"));
                cuartel.setNombreCuartel(rs.getString("nombreCuartel"));
                cuartel.setDireccion(rs.getString("direccion"));
                cuartel.setCoordenadaX(rs.getInt("coordenadaX"));
                cuartel.setCoordenadaY(rs.getInt("coordenadaY"));
                cuartel.setTelefono(rs.getString("telefono"));
                cuartel.setCorreo(rs.getString("correo"));
                cuartel.setEstado(rs.getBoolean("cuar.estado"));

                brigada = new Brigada();
                brigada.setCodigoBrigada(rs.getInt("codigoBrigada"));
                brigada.setNombreBrigada(rs.getString("nombreBrigada"));
                brigada.setEspecialidad(rs.getString("especialidad"));
                brigada.setEnCuartel(rs.getBoolean("enCuartel"));
                brigada.setCantBomberos(rs.getInt("cantBomberos"));
                brigada.setCuartel(cuartel);
                brigada.setEstado(rs.getBoolean("bri.estado"));

                siniestro = new Siniestro();
                siniestro.setCodigoSiniestro(rs.getInt("codigoSiniestro"));
                siniestro.setTipo(rs.getString("tipo"));
                siniestro.setFechaHoraInicio(rs.getTimestamp("fechaHoraInicio").toLocalDateTime());
                siniestro.setCoordenadaX(rs.getInt("coordenadaX"));
                siniestro.setCoordenadaY(rs.getInt("coordenadaY"));
                siniestro.setDetalles(rs.getString("detalles"));
                siniestro.setBrigada(brigada);
                // siniestro.setFechaHoraResolucion(rs.getTimestamp("fechaHoraResolucion").toLocalDateTime());
                // siniestro.setPuntuacion(rs.getInt("puntuacion"));
                int puntuacion = rs.getInt("puntuacion");
                if (puntuacion != -1) {
                    siniestro.setFechaHoraResolucion(rs.getTimestamp("fechaHoraResolucion").toLocalDateTime());
                } else {
                    siniestro.setFechaHoraResolucion(null);
                }
                siniestro.setPuntuacion(puntuacion);
                siniestros.add(siniestro);
            }
            ps.close();
        } catch (SQLException e) {
            System.out.println("[SiniestroData Error " + e.getErrorCode() + "] " + e.getMessage());
            e.printStackTrace();
        }
        return siniestros;
    }

    public List<Siniestro> listarSiniestrosSinResolucion() {
        List<Siniestro> siniestros = new ArrayList();
        try {
            String sql = "SELECT * FROM siniestro si, brigada bri, cuartel cuar "
                    + "WHERE si.puntuacion=-1 AND si.codigoBrigada=bri.codigoBrigada "
                    + "AND bri.codigoCuartel=cuar.codigoCuartel;";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            Cuartel cuartel;
            Brigada brigada;
            Siniestro siniestro;
            if (rs.next()) {
                cuartel = new Cuartel();
                cuartel.setCodigoCuartel(rs.getInt("cuar.codigoCuartel"));
                cuartel.setNombreCuartel(rs.getString("nombreCuartel"));
                cuartel.setDireccion(rs.getString("direccion"));
                cuartel.setCoordenadaX(rs.getInt("coordenadaX"));
                cuartel.setCoordenadaY(rs.getInt("coordenadaY"));
                cuartel.setTelefono(rs.getString("telefono"));
                cuartel.setCorreo(rs.getString("correo"));
                cuartel.setEstado(rs.getBoolean("cuar.estado"));

                brigada = new Brigada();
                brigada.setCodigoBrigada(rs.getInt("codigoBrigada"));
                brigada.setNombreBrigada(rs.getString("nombreBrigada"));
                brigada.setEspecialidad(rs.getString("especialidad"));
                brigada.setEnCuartel(rs.getBoolean("enCuartel"));
                brigada.setCantBomberos(rs.getInt("cantBomberos"));
                brigada.setCuartel(cuartel);
                brigada.setEstado(rs.getBoolean("bri.estado"));

                siniestro = new Siniestro();
                siniestro.setCodigoSiniestro(rs.getInt("codigoSiniestro"));
                siniestro.setTipo(rs.getString("tipo"));
                siniestro.setFechaHoraInicio(rs.getTimestamp("fechaHoraInicio").toLocalDateTime());
                siniestro.setCoordenadaX(rs.getInt("coordenadaX"));
                siniestro.setCoordenadaY(rs.getInt("coordenadaY"));
                siniestro.setDetalles(rs.getString("detalles"));
                siniestro.setBrigada(brigada);
                // siniestro.setFechaHoraResolucion(rs.getTimestamp("fechaHoraResolucion").toLocalDateTime());
                // siniestro.setPuntuacion(rs.getInt("puntuacion"));
                int puntuacion = rs.getInt("puntuacion");
                if (puntuacion != -1) {
                    siniestro.setFechaHoraResolucion(rs.getTimestamp("fechaHoraResolucion").toLocalDateTime());
                } else {
                    siniestro.setFechaHoraResolucion(null);
                }
                siniestro.setPuntuacion(puntuacion);
                siniestros.add(siniestro);
            }
            ps.close();
        } catch (SQLException e) {
            System.out.println("[SiniestroData Error " + e.getErrorCode() + "] " + e.getMessage());
            e.printStackTrace();
        }
        return siniestros;
    }

    public List<Siniestro> listarSiniestrosEntreFechas(LocalDateTime fecha1, LocalDateTime fecha2) {
        List<Siniestro> siniestros = new ArrayList();
        try {
            String sql = "SELECT * FROM siniestro si, brigada bri, cuartel cuar "
                    + "WHERE (fechaHoraInicio BETWEEN ? AND ?) AND si.codigoBrigada=bri.codigoBrigada "
                    + "AND bri.codigoCuartel=cuar.codigoCuartel;";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setTimestamp(1, Timestamp.valueOf(fecha1));
            ps.setTimestamp(2, Timestamp.valueOf(fecha2));
            ResultSet rs = ps.executeQuery();
            Cuartel cuartel;
            Brigada brigada;
            Siniestro siniestro;
            if (rs.next()) {
                cuartel = new Cuartel();
                cuartel.setCodigoCuartel(rs.getInt("cuar.codigoCuartel"));
                cuartel.setNombreCuartel(rs.getString("nombreCuartel"));
                cuartel.setDireccion(rs.getString("direccion"));
                cuartel.setCoordenadaX(rs.getInt("coordenadaX"));
                cuartel.setCoordenadaY(rs.getInt("coordenadaY"));
                cuartel.setTelefono(rs.getString("telefono"));
                cuartel.setCorreo(rs.getString("correo"));
                cuartel.setEstado(rs.getBoolean("cuar.estado"));

                brigada = new Brigada();
                brigada.setCodigoBrigada(rs.getInt("codigoBrigada"));
                brigada.setNombreBrigada(rs.getString("nombreBrigada"));
                brigada.setEspecialidad(rs.getString("especialidad"));
                brigada.setEnCuartel(rs.getBoolean("enCuartel"));
                brigada.setCantBomberos(rs.getInt("cantBomberos"));
                brigada.setCuartel(cuartel);
                brigada.setEstado(rs.getBoolean("bri.estado"));

                siniestro = new Siniestro();
                siniestro.setCodigoSiniestro(rs.getInt("codigoSiniestro"));
                siniestro.setTipo(rs.getString("tipo"));
                siniestro.setFechaHoraInicio(rs.getTimestamp("fechaHoraInicio").toLocalDateTime());
                siniestro.setCoordenadaX(rs.getInt("coordenadaX"));
                siniestro.setCoordenadaY(rs.getInt("coordenadaY"));
                siniestro.setDetalles(rs.getString("detalles"));
                siniestro.setBrigada(brigada);
                // siniestro.setFechaHoraResolucion(rs.getTimestamp("fechaHoraResolucion").toLocalDateTime());
                // siniestro.setPuntuacion(rs.getInt("puntuacion"));
                int puntuacion = rs.getInt("puntuacion");
                if (puntuacion != -1) {
                    siniestro.setFechaHoraResolucion(rs.getTimestamp("fechaHoraResolucion").toLocalDateTime());
                } else {
                    siniestro.setFechaHoraResolucion(null);
                }
                siniestro.setPuntuacion(puntuacion);
                siniestros.add(siniestro);
            }
            ps.close();
        } catch (SQLException e) {
            System.out.println("[SiniestroData Error " + e.getErrorCode() + "] " + e.getMessage());
            e.printStackTrace();
        }
        return siniestros;
    }

    public boolean modificarSiniestro(Siniestro siniestro) {
        boolean resultado = false;
        try {
            String sql;
            sql = "UPDATE siniestro SET tipo=?, fechaHoraInicio=?, coordenadaX=?, coordenadaY=?, detalles=?, "
                    + "codigoBrigada=?, fechaHoraResolucion=?, puntuacion=? "
                    + "WHERE codigoSiniestro=?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, siniestro.getTipo());
            ps.setTimestamp(2, Timestamp.valueOf(siniestro.getFechaHoraInicio()));
            ps.setInt(3, siniestro.getCoordenadaX());
            ps.setInt(4, siniestro.getCoordenadaY());
            ps.setString(5, siniestro.getDetalles());
            ps.setInt(6, siniestro.getBrigada().getCodigoBrigada());
            int puntuacion = siniestro.getPuntuacion();
            if (siniestro.getFechaHoraResolucion() == null && puntuacion == Siniestro.PUNTUACION_NIL) {
                ps.setNull(7, Types.TIMESTAMP);
                ps.setInt(8, puntuacion);
            } else if (siniestro.getFechaHoraResolucion() != null && siniestro.getPuntuacion() != -1) {
                if (puntuacion < Siniestro.PUNTUACION_MIN || puntuacion > Siniestro.PUNTUACION_MAX) {
                    ps.close();
                    System.out.println("[SiniestroData] Error al modificar. Puntuacion fuera de rango");
                    return false;
                }
                ps.setTimestamp(7, Timestamp.valueOf(siniestro.getFechaHoraResolucion()));
                ps.setInt(8, siniestro.getPuntuacion());
            } else {
                ps.close();
                System.out.println("[SiniestroData] Error al modificar. Los datos de la resolucion son inconsistentes");
                return false;
            }
            ps.setInt(9, siniestro.getCodigoSiniestro());
            if (ps.executeUpdate() > 0) {
                resultado = true;
                System.out.println("[SiniestroData] Siniestro modificado");
            } else {
                System.out.println("[SiniestroData] No se pudo modificar el siniestro");
            }
            ps.close();
        } catch (SQLException e) {
            System.out.println("[SiniestroData Error " + e.getErrorCode() + "] " + e.getMessage());
            e.printStackTrace();
        }
        return resultado;
    }

    public boolean asignarResolucion(Siniestro siniestro, LocalDateTime fechaHoraResolucion, int puntuacion) {
        if (!(fechaHoraResolucion != null && puntuacion != Siniestro.PUNTUACION_NIL)) {
            System.out.println("[SiniestroData] Error al asignar resolucion. Tanto la fecha de resolución como la puntuación deben ser establecidos");
            return false;
        } else if (puntuacion < Siniestro.PUNTUACION_MIN || puntuacion > Siniestro.PUNTUACION_MAX) {
            System.out.println("[SiniestroData] Error al asignar resolucion. Puntuacion fuera de rango");
            return false;
        } else if (siniestro.getFechaHoraInicio().isAfter(fechaHoraResolucion)) {
            System.out.println("[SiniestroData] Error al asignar resolucion. La fecha de resolución es anterior a la fecha de inicio del siniestro");
            return false;
        }
        boolean resultado = false;
        try {
            String sql;
            sql = "UPDATE siniestro SET fechaHoraResolucion=?, puntuacion=? WHERE codigoSiniestro=?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setTimestamp(1, Timestamp.valueOf(fechaHoraResolucion));
            ps.setInt(2, puntuacion);
            ps.setInt(3, siniestro.getCodigoSiniestro());
            if (ps.executeUpdate() > 0) {
                System.out.println("[SiniestroData] Resolucion asignada");

                // actualizar la brigada que trató la emergencia
                sql = "UPDATE brigada SET enCuartel=true WHERE codigoBrigada=?;";
                ps = connection.prepareStatement(sql);
                ps.setInt(1, siniestro.getBrigada().getCodigoBrigada());
                if (ps.executeUpdate() > 0) {
                    resultado = true;
                    System.out.println("[SiniestroData] Brigada actualizada");
                } else {
                    System.out.println("[SiniestroData] No se pudo actualizar la brigada");
                }

            } else {
                System.out.println("[SiniestroData] No se pudo asignar la resolucion");
            }
            ps.close();
        } catch (SQLException e) {
            System.out.println("[SiniestroData Error " + e.getErrorCode() + "] " + e.getMessage());
            e.printStackTrace();
        }
        return resultado;
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
                System.out.println("[SiniestroData] No se pudo eliminar el siniestro");
            }
            ps.close();
        } catch (SQLException e) {
            System.out.println("[SiniestroData Error " + e.getErrorCode() + "] " + e.getMessage());
            e.printStackTrace();
        }
        return resultado;
    }

}
