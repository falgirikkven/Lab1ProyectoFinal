package lab1proyectofinal.accesoADatos;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lab1proyectofinal.entidades.Bombero;

/**
 * Un objeto de la clase BomberoData puede realizar las operaciones de inserción, búsqueda, listado,
 * modificación y borrado lógico sobre una tabla en una base de datos cuyos registros se compongan 
 * de datos de instancias de la clase {@link Bombero}
 *
 * @author Grupo-3
 */
public class BomberoData {

    private final Connection connection;    // conexión a una BD

    /**
     * Constructor: instancia con un objeto de la clase {@link Connection} asignada por
     * {@link Conexion#getInstance()}
     */
    public BomberoData() {
        this.connection = Conexion.getInstance();
    }

    /**
     * Retorna "el dni de bombero pertenece a un rango de valores válido y no está presente en ningún registro de
     * la BD espeficicada por {@link #connection}".
     */
    public boolean esDNIValido(Bombero bombero) throws SQLException {
        // Si bombero tiene un dni fuera del rango válido [10000000, 99999999], retornar false
        if (bombero.getDni() < 10000000 || bombero.getDni() > 99999999) {
            return false;
        }

        // Intentar obtener de la BD la cantidad de bomberos con el mismo dni que bombero
        String sql = "SELECT COUNT(dni) FROM bombero "
                + "WHERE dni = ?;";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, bombero.getDni());
        ResultSet rs = ps.executeQuery();
        ps.close();

        // Si la cantidad es distinta de 0, retornar constante DNI
        if (rs.next() && rs.getInt(1) != 0) {
            return false;
        }
        return true;
    }

    /** 
     * Retorna "el codigoBrigada de bombero corresponde a una brigada en la BD que tiene menos de 5
     * miembros"
    
    
    /**
     * Dato de bombero que no permite insertarlo en la BD (si dicho dato existe). Lanza SQLException
     * si se producen problemas al crear una instancia de {@link PreparedStatement}
     */
    public Utils.Dato datoBomberoInvalidandoInsercion(Bombero bombero) throws SQLException {
        // Si bombero tiene idBombero establecido, retornar constante CODIGO 
        if (bombero.getIdBombero() != Utils.codigoNoEstablecido) {
            return Utils.Dato.ID_BOMBERO;
        }
        
        // Si bombero tiene un dni inválido, retornar constante DNI
        if (!esDNIValido(bombero)) {
            return Utils.Dato.DNI;
        }        

        // Si el nombre del bombero supera los 40 caracteres o está compuesto solo por espacios, 
        // retornar constante NOMBRE_COMPLETO
        String nomCom = bombero.getNombreCompleto();
        if (nomCom.isBlank() || nomCom.length() > 40) {
            return Utils.Dato.NOMBRE_COMPLETO;
        }

        // Si el grupo sanguíneo del bombero no es ninguno válido, retornar constante 
        // GRUPO_SANGUINEO 
        boolean gsValido = false;
        for (String gs : Utils.obtenerGrupoSanguineo()) {
            if (gs.equalsIgnoreCase(bombero.getGrupoSanguineo())) {
                gsValido = true;
                break;
            }
        }
        if (!gsValido) {
            return Utils.Dato.GRUPO_SANGUINEO;
        }

        // Si la fecha de nacimiento del bombero indica que el bombero tiene menos de 18 años o más
        // de 65, retornar constante FECHA_NACIMIENTO
        if (bombero.getFechaNacimiento().isAfter(LocalDate.now().minusYears(18))
                || bombero.getFechaNacimiento().isBefore(LocalDate.now().minusYears(65))) {
            return Utils.Dato.FECHA_NACIMIENTO;
        }

        // Si el número de celular del bombero no es válido, retornar constante CELULAR
        if (!Utils.esTelefonoValido(bombero.getCelular())) {
            return Utils.Dato.CELULAR;
        }

        // Intentar obtener de la BD la cantidad de bomberos con estado = true y que pertenezcan a 
        // la brigada a la que se quiere asignar a bombero
        sql = "SELECT COUNT(codigoBrigada) FROM bombero "
                + "WHERE codigoBrigada = ? "
                + "AND estado = true;";
        ps = connection.prepareStatement(sql);
        ps.setInt(1, bombero.getBrigada().getCodigoBrigada());
        rs = ps.executeQuery();
        ps.close();

        // Si la cantidad es igual a 5, retornar constante BRIGADA
        if (rs.next() && rs.getInt(1) == 5) {
            return Utils.Dato.BRIGADA;
        }

        // Si bombero tiene estado = false, retornar constante ESTADO 
        if (!bombero.isEstado()) {
            return Utils.Dato.ESTADO;
        }

        // { todos los datos son válidos para la inserción del bombero en la BD }        
        return null;
    }

    /**
     * Retornar "bombero ha sido insertado en la BD"
     */
    public boolean esGuardarBombero(Bombero bombero) {
        try {
            // Determinar si bombero tiene algún dato que invalide su inserción en la BD
            switch (datoBomberoInvalidandoInsercion(bombero)) {
                case BRIGADA:
                    System.out.println("[BomberoData.guardarBombero] No se agregó: no hay más "
                            + "espacio en brigada con código: "
                            + bombero.getBrigada().getCodigoBrigada() + ". Bombero que se intentó "
                            + "agregar: " + bombero.debugToString());
                    return false;
                case CELULAR:
                    System.out.println("[BomberoData.guardarBombero] No se agregó: nro de celular "
                            + "del bombero inválido. Bombero que se intentó agregar: "
                            + bombero.debugToString());
                case ID_BOMBERO:
                    System.out.println("[BomberoData.guardarBombero] No se agregó: idBombero "
                            + bombero.getIdBombero() + " ya establecido. Bombero que se intentó "
                            + "agregar: " + bombero.debugToString());
                    return false;
                case DNI:
                    System.out.println("[BomberoData.guardarBombero] No se agregó: DNI "
                            + bombero.getDni() + " ya registrado en la BD. Bombero que se intentó "
                            + "agregar: " + bombero.debugToString());
                    return false;
                case ESTADO:
                    System.out.println("[BomberoData.guardarBombero] No se agregó: estado del "
                            + "bombero igual a false. Bombero que se intentó agregar: "
                            + bombero.debugToString());
                    return false;
                case FECHA_NACIMIENTO:
                    System.out.println("[BomberoData.guardarBombero] No se agregó: fecha de "
                            + "nacimiento del bombero implica edad fuera del rango [18, 65] años. "
                            + "Bombero que se intentó agregar: " + bombero.debugToString());
                    return false;
                case GRUPO_SANGUINEO:
                    System.out.println("[BomberoData.guardarBombero] No se agregó: grupo sanguíneo "
                            + "del bombero inválido. Bombero que se intentó agregar: "
                            + bombero.debugToString());
                    return false;
                case NOMBRE_COMPLETO:
                    System.out.println("[BomberoData.guardarBombero] No se agregó: nombre del "
                            + "bombero inválido (compuesto solo por espacios o por más de 40 "
                            + "caracteres). Bombero que se intentó agregar: "
                            + bombero.debugToString());
                    return false;
            }

            // Intentar insertar en la BD un registro con los datos de bombero
            String sql = "INSERT INTO bombero(dni, nombreCompleto, grupoSanguineo, "
                    + "fechaNacimiento, celular, codigoBrigada, estado) "
                    + "VALUES (?, ?, ?, ?, ?, ?, true);";
            PreparedStatement ps = connection.prepareStatement(sql,
                    PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setInt(1, bombero.getDni());
            ps.setString(2, bombero.getNombreCompleto());
            ps.setString(3, bombero.getGrupoSanguineo());
            ps.setDate(4, java.sql.Date.valueOf(bombero.getFechaNacimiento()));
            ps.setString(5, bombero.getCelular());
            ps.setInt(6, bombero.getBrigada().getCodigoBrigada());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            ps.close();
            if (rs.next()) {
                // Establecer el idBombero de bombero según lo asignado por la BD 
                bombero.setIdBombero(rs.getInt(1));

                System.out.println("[BomberoData.guardarBombero] "
                        + "Agregado: " + bombero.debugToString());
                return true;
            } else {
                System.out.println("[BomberoData.guardarBombero] "
                        + "No se pudo agregar: " + bombero.debugToString());
                return false;
            }

        } catch (SQLException e) {
            // informar sobre error y retornar false
            if (e.getErrorCode() == 1062) {
                System.out.println("[BomberoData.guardarBombero] "
                        + "Error: entrada duplicada para " + bombero.debugToString());
            } else {
                e.printStackTrace();
            }
            return false;
        }
    }

    /**
     * Retornar bombero de la BD cuyo dni sea igual a dni y su estado sea true
     */
    public Bombero bomberoSegunDNI(int dni) {
        Bombero bombero = null;

        try {
            // Intentar buscar bombero en la BD cuyo dni sea igual a dni y su estado sea true
            String sql = "SELECT * FROM bombero "
                    + "JOIN brigada ON (bombero.codigoBrigada = brigada.codigoBrigada "
                    + "AND bombero.dni = ? "
                    + "AND bombero.estado = true "
                    + "AND brigada.estado = true) "
                    + "JOIN cuartel ON (brigada.codigoCuartel = cuartel.codigoCuartel "
                    + "AND cuartel.estado = true);";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, dni);
            ResultSet rs = ps.executeQuery();
            ps.close();

            // Si se obtuvo el bombero buscado, retornarlo. Informar. 
            if (rs.next()) {
                bombero = Utils.obtenerDeResultSetBombero(rs);
                System.out.println("[BomberoData.buscarBomberoPorDni] "
                        + "Encontrado: " + bombero.debugToString());
            } else {
                System.out.println("[BomberoData.buscarBomberoPorDni] "
                        + "No se pudo encontrar con dni=" + dni);
            }

        } catch (SQLException e) {
            // informar sobre error 
            System.out.println("[BomberoData.buscarBombero] "
                    + "Error " + e.getErrorCode() + ": " + e.getMessage());
            e.printStackTrace();
        }
        return bombero;
    }

    public boolean estaDNIentreInactivos(int dni) {
        boolean resultado = false;
        try {
            String sql = "SELECT estado FROM bombero "
                    + "WHERE dni = ? "
                    + "AND estado = false";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, dni);
            ResultSet rs = ps.executeQuery();

            ps.close();
            if (rs.next()) {
                resultado = true;
                System.out.println("[BomberoData.estaDNIentreInactivos] "
                        + "Encontrado el DNI: " + dni);
            } else {
                System.out.println("[BomberoData.estaDNIentreInactivos] "
                        + "No encontrado el DNI: " + dni);
            }
        } catch (SQLException e) {
            System.out.println("[BomberoData.estaDNIentreInactivos] "
                    + "Error " + e.getErrorCode() + ": " + e.getMessage());
            e.printStackTrace();
        }
        return resultado;
    }

    // revisado
    public List<Bombero> listarBomberos() {
        List<Bombero> bomberos = null;
        try {
            String sql = "SELECT * FROM bombero "
                    + "JOIN brigada ON (bombero.codigoBrigada = brigada.codigoBrigada "
                    + "AND bombero.estado = true "
                    + "AND brigada.estado = true) "
                    + "JOIN cuartel ON (brigada.codigoCuartel = cuartel.codigoCuartel "
                    + "AND cuartel.estado = true); ";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            ps.close();
            bomberos = new ArrayList();
            while (rs.next()) {
                Bombero bombero = Utils.obtenerDeResultSetBombero(rs);
                bomberos.add(bombero);
            }
            System.out.println("[BomberoData.listarBomberos] "
                    + "Cantidad de bomberos: " + bomberos.size());
        } catch (SQLException e) {
            System.out.println("[BomberoData.buscarBombero] "
                    + "Error " + e.getErrorCode() + ": " + e.getMessage());
            e.printStackTrace();
        }
        return bomberos;
    }

    // revisado
    public boolean modificarBombero(Bombero bombero, boolean deLaMismaBrigada) {
        if (bombero.getIdBombero() == Utils.codigoNoEstablecido || !bombero.isEstado()) {
            System.out.println("[BomberoData.modificarBombero] "
                    + "Error: no se modificó. "
                    + "Bombero dado de baja o sin idBombero definido: "
                    + bombero.debugToString());
            return false;
        }

        boolean resultado = false;
        try {
            String sql;
            if (deLaMismaBrigada) {
                sql = "UPDATE bombero SET dni = ?, nombreCompleto = ?, grupoSanguineo = ?, "
                        + "fechaNacimiento = ?, celular = ?, codigoBrigada = ? "
                        + "WHERE idBombero = ? "
                        + "AND estado = true "
                        + "AND (SELECT COUNT(codigoBrigada) FROM bombero "
                        + "WHERE codigoBrigada = ? "
                        + "AND estado = true) < 6";
            } else {
                sql = "UPDATE bombero SET dni = ?, nombreCompleto = ?, grupoSanguineo = ?, "
                        + "fechaNacimiento = ?, celular = ?, codigoBrigada = ? "
                        + "WHERE idBombero = ? "
                        + "AND estado = true "
                        + "AND (SELECT COUNT(codigoBrigada) FROM bombero "
                        + "WHERE codigoBrigada = ? "
                        + "AND estado = true) < 5";
            }
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, bombero.getDni());
            ps.setString(2, bombero.getNombreCompleto());
            ps.setString(3, bombero.getGrupoSanguineo());
            ps.setDate(4, Date.valueOf(bombero.getFechaNacimiento()));
            ps.setString(5, bombero.getCelular());
            ps.setInt(6, bombero.getBrigada().getCodigoBrigada());
            ps.setInt(7, bombero.getIdBombero());
            ps.setInt(8, bombero.getBrigada().getCodigoBrigada());
            if (ps.executeUpdate() > 0) {
                resultado = true;
                System.out.println("[BomberoData.modificarBombero] "
                        + "Modificado: " + bombero.debugToString());
            } else {
                System.out.println("[BomberoData.modificarBombero] "
                        + "No se pudo modificar: " + bombero.debugToString());
            }
            ps.close();
        } catch (SQLException e) {
            System.out.println("[BomberoData.modificarBombero] "
                    + "Error " + e.getErrorCode() + ": " + e.getMessage());
            e.printStackTrace();
        }
        return resultado;
    }

    // con el fin de asegurar la mayor similitud posible entre objeto java y registro en la BD, se crea este método que posibilita cambiar el estado del objeto java
    public boolean eliminarBombero(Bombero bombero) {
        boolean resultado = false;
        try {
            String sql = "UPDATE bombero SET estado = false "
                    + "WHERE idBombero = ? "
                    + "AND estado = true "
                    + "AND codigoBrigada NOT IN (SELECT codigoBrigada FROM emergencia "
                    + "WHERE desempenio = -1);";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, bombero.getIdBombero());
            if (ps.executeUpdate() > 0) {
                bombero.setEstado(false);
                resultado = true;
                System.out.println("[BomberoData.eliminarBombero] "
                        + "Eliminado: idBombero=" + bombero.getIdBombero());
            } else {
                System.out.println("[BomberoData.eliminarBombero] "
                        + "No se pudo eliminar: idBombero=" + bombero.getIdBombero());
            }
            ps.close();
        } catch (SQLException e) {
            System.out.println("[BomberoData.eliminarBombero] "
                    + "Error " + e.getErrorCode() + ": " + e.getMessage());
            e.printStackTrace();
        }
        return resultado;
    }

    // revisado, potencialmente innecesario
    public boolean eliminarBomberoPorID(int idBombero) {
        boolean resultado = false;
        try {
//            String sql = "UPDATE bombero SET estado=false WHERE idBombero=? AND estado=true";
            String sql = "UPDATE bombero SET estado = false "
                    + "WHERE idBombero = ? "
                    + "AND estado = true "
                    + "AND codigoBrigada NOT IN (SELECT codigoBrigada FROM emergencia "
                    + "WHERE desempenio = -1);";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, idBombero);
            if (ps.executeUpdate() > 0) {
                resultado = true;
                System.out.println("[BomberoData.eliminarBombero] "
                        + "Eliminado: idBombero=" + idBombero);
            } else {
                System.out.println("[BomberoData.eliminarBombero] "
                        + "No se pudo eliminar: idBombero=" + idBombero);
            }
            ps.close();
        } catch (SQLException e) {
            System.out.println("[BomberoData.eliminarBombero] "
                    + "Error " + e.getErrorCode() + ": " + e.getMessage());
            e.printStackTrace();
        }
        return resultado;
    }

    // revisado, potencialmente innecesario
    public boolean eliminarBomberoPorDNI(int dni) {
        boolean resultado = false;
        try {
            String sql = "UPDATE bombero SET estado = false "
                    + "WHERE dni = ? "
                    + "AND estado = true "
                    + "AND codigoBrigada NOT IN (SELECT codigoBrigada FROM emergencia "
                    + "WHERE desempenio = -1);";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, dni);
            if (ps.executeUpdate() > 0) {
                resultado = true;
                System.out.println("[BomberoData.eliminarBombero] "
                        + "Eliminado: dni=" + dni);
            } else {
                System.out.println("[BomberoData.eliminarBombero] "
                        + "No se pudo eliminar: dni=" + dni);
            }
            ps.close();
        } catch (SQLException e) {
            System.out.println("[BomberoData.eliminarBomberoPorDni] "
                    + "Error " + e.getErrorCode() + ": " + e.getMessage());
            e.printStackTrace();
        }
        return resultado;
    }

}
