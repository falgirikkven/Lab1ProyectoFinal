package lab1proyectofinal.accesoADatos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import lab1proyectofinal.entidades.Bombero;
import lab1proyectofinal.entidades.Brigada;
import lab1proyectofinal.entidades.Cuartel;

/**
 * Un objeto de la clase CuartelData puede realizar las operaciones de inserción, búsqueda, listado,
 * modificación y eliminación lógica sobre los registros de la tabla "cuartel" de la base de datos
 * "proyecto_final".
 *
 * @author Grupo-3
 */
public class CuartelData {

    private Connection connection;      // conexión con la base de datos "proyecto_final"

    /**
     * Constructor: instancia con un Connection asignado por {@link Conexion#getInstance()}.
     */
    public CuartelData() {
        this.connection = Conexion.getInstance();
    }

    /**
     * Retornar true si alguna de las propiedades {@code nombreCuartel}, {@code direccion},
     * {@code correo} y {@code telefono} de {@code cuar} tiene un valor semánticamente incorrecto.
     *
     * Nota: no se toman en cuenta las demás propiedades de {@code cuar}, dado que las mismas no
     * tienen un valor que pueda considerarse semánticamente incorrecto en términos generales (es
     * decir, sin tomar en cuenta la operación que se está llevando a cabo con {@code cuar}) o,
     * directamente, no pueden tener un valor semánticamente incorrecto.
     */
    public boolean hayDatoInvalido(Cuartel cuar) {
        // Si cuar tiene un nombreCuartel con una longitud mayor a 30 caracteres, retornar false
        if (cuar.getNombreCuartel().length() > 30) {
            System.out.println("[CuartelData.hayDatoInvalido] Error: cuartel con nombreCuartel "
                    + "mayor a 30 caracteres: " + cuar.debugToString());
            return false;
        }

        // Si cuar tiene una direccion con una longitud mayor a 50 caracteres, retornar false
        if (cuar.getDireccion().length() > 50) {
            System.out.println("[CuartelData.hayDatoInvalido] Error: cuartel con direccion mayor a "
                    + "50 caracteres: " + cuar.debugToString());
            return false;
        }

        // Si cuar tiene un telefono inválido, retornar false
        if (!Utils.esTelefonoValido(cuar.getTelefono())) {
            System.out.println("[CuartelData.hayDatoInvalido] Error: cuartel con telefono "
                    + "inválido: " + cuar.debugToString());
            return false;
        }

        // Si cuar tiene un correo inválido, retornar false
        if (!Utils.esCorreoValido(cuar.getCorreo())) {
            System.out.println("[CuartelData.hayDatoInvalido] Error: cuartel con correo "
                    + "inválido: " + cuar.debugToString());
            return false;
        }       
        
        // { las propiedades de cuar nombreCuartel, direccion, telefono y correo tienen valores
        // válidos }
        return true;
    }

    /**
     * Retornar true si alguna de las propiedades {@code codigoCuartel}, {@code estado},
     * {@code nombreCuartel}, {@code direccion}, {@code correo} o {@code telefono} de {@code cuar}
     * tiene un valor semánticamente incorrecto en un contexto de inserción, es decir, al insertar
     * un registro con los valores de las propiedades de {@code cuar} (excepto los de
     * {@code codigoCuartel} y {@code estado}) en la tabla "cuartel" de la base de datos
     * "proyecto_final").
     *
     * La razón por la que se toman en cuenta la validez de los valores de {@code cuar} en
     * {@code codigoCuartel} y {@code estado} aunque estos no se incluyan en el registro que se
     * busca insertar se debe a que, por un lado, {@code codigoCuartel} no debe ser establecido por
     * el usuario y, por tanto, no se permite insertar un registro basado en los valores de
     * {@code cuar} cuando este último tiene un {@code codigoCuartel} distinto del indicado por
     * {@link Utils.codigoNoEstablecido} (valor que viene por defecto al crear un {@code Cuartel}).
     * Por otro lado, en el caso de {@code estado} se prefiere que este sea true para evitar
     * posibles confusiones al momento de manipular el registro (y, si se quisiera ingresar en la
     * base de datos un registro de un {@code Cuartel} que está inactivo (estado = false), se
     * debería insertarlo con estado = true y luego darle de baja).
     *
     * Nota: no se toman en cuenta las demás propiedades de {@code cuar}, dado que las mismas no
     * pueden tener un valor semánticamente incorrecto.
     */
    public boolean hayDatoInvalidoInsercion(Cuartel cuar) {
        // Si cuar tiene en codigoCuartel un valor distinto del indicado por 
        // Utils.codigoNoEstablecido, retornar false
        if (cuar.getCodigoCuartel() != Utils.codigoNoEstablecido) {
            System.out.println("[CuartelData.insertarCuartel] Error: cuartel con codigoCuartel "
                    + "ya definido: " + cuar.debugToString());
            return false;
        }

        // Si cuar tiene estado igual a false, retornar false
        if (!cuar.isEstado()) {
            System.out.println("[CuartelData.insertarCuartel] Error: cuartel dado de baja: "
                    + cuar.debugToString());
            return false;
        }

        // Si cuar tiene en alguna de sus propiedades nombreCuartel, direccion, correo o telefono
        // un dato inválido, retornar false. Sino, retornar true
        return (hayDatoInvalido(cuar));
    }

    /**
     * Retornar true si alguna de las propiedades {@code codigoCuartel}, {@code estado},
     * {@code nombreCuartel}, {@code direccion}, {@code correo} o {@code telefono} de {@code cuar}
     * tiene un valor semánticamente incorrecto en un contexto de modificación, es decir, al
     * reemplazar los valores del registro de la tabla "cuartel" de la base de datos
     * "proyecto_final" que tiene el mismo valor en la columna "codigoCuartel" que la propiedad
     * {@code codigoCuartel} de {@code cuar} (si dicho registro existe) con los valores de las
     * propiedades de {@code cuar} (excepto los de {@code codigoCuartel} y {@code estado}).
     *
     * La razón por la que se toman en cuenta la validez de los valores de {@code cuar} en
     * {@code codigoCuartel} y {@code estado} aunque estos no reemplazen a los valores del registro
     * en las columnas "codigoCuartel" y "estado" se debe a que, por un lado, {@code codigoCuartel}
     * debe tener un valor distinto del indicado por {@link Utils.codigoNoEstablecido} si
     * {@code cuar} realmente representa un registro insertado en la base de datos (y así debe ser,
     * si se intenta modificar dicho registro). Entonces, si el valor de {@code codigoCuartel} en
     * {@code cuar} es igual a {@link Utils.codigoNoEstablecido} eso significaría que se está
     * modificando un registro que aun no existe en la base de datos. Por otra parte {@code estado}
     * debe ser true, dado que no se permite la modificación de registros con estado igual a false.
     *
     * Nota: no se toman en cuenta las demás propiedades de {@code cuar}, dado que las mismas no
     * pueden tener un valor semánticamente incorrecto.
     */
    public boolean hayDatoInvalidoModificacion(Cuartel cuar) {
        // Si cuar tiene en codigoCuartel un valor igual al indicado por 
        // Utils.codigoNoEstablecido, retornar false
        if (cuar.getCodigoCuartel() == Utils.codigoNoEstablecido) {
            System.out.println("[CuartelData.hayDatoInvalidoModificacion] Error: cuartel con codigoCuartel "
                    + "ya definido: " + cuar.debugToString());
            return false;
        }

        // Si cuar tiene estado igual a false, retornar false
        if (!cuar.isEstado()) {
            System.out.println("[CuartelData.hayDatoInvalidoModificacion] Error: cuartel dado de baja: "
                    + cuar.debugToString());
            return false;
        }

        // Si cuar tiene en alguna de sus propiedades nombreCuartel, direccion, correo y telefono
        // un valor inválido, retornar false. Caso contrario, retornar true
        return (hayDatoInvalido(cuar));
    }

    /**
     * Retornar true si alguna de las propiedades {@code codigoCuartel}, {@code estado},
     * {@code nombreCuartel}, {@code direccion}, {@code correo} o {@code telefono} de {@code cuar}
     * tiene un valor semánticamente incorrecto en un contexto de eliminación lógica, es decir, al
     * establecer en false el valor de la columna "estado" del registro de la tabla "cuartel" de la
     * base de datos "proyecto_final" que tiene el mismo valor en la columna "codigoCuartel" que la
     * propiedad {@code codigoCuartel} de {@code cuar} (si dicho registro existe).
     *
     * Notese que las propiedades {@code codigoCuartel} y {@code estado} pueden tener valores
     * inválidos para esta operación dado qe, por un lado, {@code codigoCuartel} debe tener un valor
     * distinto del indicado por {@link Utils.codigoNoEstablecido} si {@code cuar} realmente
     * representa un registro insertado en la base de datos (y así debe ser, si se intenta eliminar
     * lógicamente dicho registro). Entonces, si el valor de {@code codigoCuartel} en {@code cuar}
     * es igual a {@link Utils.codigoNoEstablecido} eso significaría que se está eliminando
     * lógicamente un registro que aun no existe en la base de datos. Por otra parte {@code estado}
     * debe ser true, dado que no se permite la eliminación de registros con estado igual a false.
     *
     * Nota: no se toman en cuenta las demás propiedades de {@code cuar}, dado que las mismas no
     * pueden tener un valor semánticamente incorrecto.
     */
    public boolean hayDatoInvalidoElimLog(Cuartel cuar) {
        // Si cuar tiene en codigoCuartel un valor igual al indicado por 
        // Utils.codigoNoEstablecido, retornar false
        if (cuar.getCodigoCuartel() == Utils.codigoNoEstablecido) {
            System.out.println("[CuartelData.hayDatoInvalidoElimLog] Error: cuartel con "
                    + "codigoCuartel ya definido: " + cuar.debugToString());
            return false;
        }

        // Si cuar tiene estado igual a true, retornar false
        if (cuar.isEstado()) {
            System.out.println("[CuartelData.hayDatoInvalidoElimLog] Error: cuartel con estado igual a "
                    + "true: " + cuar.debugToString());
            return false;
        }

        // Si cuar tiene en alguna de sus propiedades nombreCuartel, direccion, correo y telefono
        // un valor inválido, retornar false. Caso contrario, retornar true
        return (hayDatoInvalido(cuar));
    }

    /**
     * Si {@code cuar} tiene en sus propiedades valores válidos para llevar a cabo una inserción,
     * insertar un registro con los valores de las propiedades de {@code cuar} (excepto los de
     * {@code codigoCuartel} y {@code estado}) en la tabla "cuartel" de la base de datos
     * "proyecto_final". Retornar true si se insertó el registro.
     */
    public boolean insertarCuartel(Cuartel cuar) {
        // Si existe algún dato invalido en las propiedades de cuar, retornar false
        if (hayDatoInvalidoInsercion(cuar)) {
            System.out.println("[CuartelData.insertarCuartel] "
                    + "Error: cuartel con datos inválidos para una inserción: "
                    + cuar.debugToString());
            return false;
        }

        try {
            // Crear y ejecutar un PreparedStatement (ps) para llevar a cabo la operación indicada
            // en la especificación del método. Almacenar la clave generada en un ResultSet (rs) y 
            // cerrar ps
            String sql = "INSERT INTO cuartel(nombreCuartel, direccion, coordenadaX, coordenadaY, "
                    + "telefono, correo, estado) "
                    + "VALUES (?, ?, ?, ?, ?, ?, true);";
            PreparedStatement ps = connection.prepareStatement(sql,
                    PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, cuar.getNombreCuartel());
            ps.setString(2, cuar.getDireccion());
            ps.setDouble(3, cuar.getCoordenadaX());
            ps.setDouble(4, cuar.getCoordenadaY());
            ps.setString(5, cuar.getTelefono());
            ps.setString(6, cuar.getCorreo());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            ps.close();

            if (rs.next()) {
                // {registro insertado}
                // Asignar a la propiedad codigoCuartel de cuar el valor almacenado por rs                
                cuar.setCodigoCuartel(rs.getInt(1));

                // Informar sobre registro insertado por consola y retornar true
                System.out.println("[CuartelData.insertarCuartel] Agregado: "
                        + cuar.debugToString());
                return true;
            } else {
                // {registro no insertado}
                // Informar sobre registro no insertado por consola
                System.out.println("[CuartelData.insertarCuartel] No se pudo agregar: "
                        + cuar.debugToString());
            }
        } catch (SQLException e) {
            // {registro no insertado}
            // Informar sobre excepcion por consola
            System.out.println("[CuartelData.insertarCuartel] "
                    + "Error " + e.getErrorCode() + ": " + e.getMessage());
            e.printStackTrace();
        }
        // {registro no insertado}
        return false;
    }

    /**
     * Retornar el {@code Cuartel} correspondiente al registro cuyo valor en la columna
     * "nombreCuartel" es {@code nomCuar} y cuyo valor en la columna "estado" es true en la tabla
     * "cuartel" de la base de datos "proyecto_final", si dicho registro existe (caso contrario,
     * retorna null).
     */
    public Cuartel buscarCuartelPorNombre(String nomCuar) {
        try {
            // Crear y ejecutar un PreparedStatement (ps) para llevar a cabo la operación indicada
            // en la especificación del método. Almacenar resultado en un ResultSet (rs) y cerrar ps                                    
            String sql = "SELECT * FROM cuartel "
                    + "WHERE cuartel.nomCuar = ? "
                    + "AND cuartel.estado = true;";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, nomCuar);
            ResultSet rs = ps.executeQuery();
            ps.close();

            if (rs.next()) {
                // {registro encontrado}                
                // Obtener el Cuartel (c) correspondiente al registro encontrado
                Cuartel c = Utils.obtenerDeResultSetCuartel(rs);

                // Informar sobre registro encontrado por consola y retornar c
                System.out.println("[CuartelData.buscarCuartelPorNombre] "
                        + "Encontrado: " + c.debugToString());
                return c;
            } else {
                // {registro no encontrado}
                // Informar sobre registro no encontrado por consola
                System.out.println("[CuartelData.buscarCuartelPorNombre] "
                        + "No se encontró cuartel con nomCuar='" + nomCuar + "'");
            }
        } catch (SQLException e) {
            // {registro no encontrado}
            // Informar sobre excepción por consola
            System.out.println("[CuartelData.buscarCuartelPorNombre] "
                    + "Error " + e.getErrorCode() + ": " + e.getMessage());
            e.printStackTrace();
        }
        // {registro no encontrado}        
        return null;
    }

    /**
     * Retornar true si existe un registro en la tabla "cuartel" de la base de datos
     * "proyecto_final" cuyo valor en la columna "nombreCuartel" sea igual a {@code nomCuar} y cuyo
     * valor en la columna "estado" sea igual a false.
     */
    public boolean estaNombreEntreInactivos(String nomCuar) {
        try {
            // Crear y ejecutar un PreparedStatement (ps) para llevar a cabo la operación indicada
            // en la especificación del método. Almacenar resultado en un ResultSet (rs) y cerrar ps 
            String sql = "SELECT nomCuar FROM cuartel "
                    + "WHERE cuartel.nomCuar = ? "
                    + "AND cuartel.estado = false;";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, nomCuar);
            ResultSet rs = ps.executeQuery();
            ps.close();

            if (rs.next()) {
                // {registro encontrado}
                // Informar por consola sobre registro encontrado y retorna true
                System.out.println("[CuartelData.buscarNombreEntreInactivos] "
                        + "Se encontró con nombre='" + nomCuar + "'");
                return true;
            } else {
                // {registro no encontrado}
                System.out.println("[CuartelData.buscarNombreEntreInactivos] "
                        + "No se encontró con nombre='" + nomCuar + "'");
            }
        } catch (SQLException e) {
            // {registro no encontrado}
            // Informar por consola sobre excepción
            System.out.println("[CuartelData.buscarNombreEntreInactivos] "
                    + "Error " + e.getErrorCode() + ": " + e.getMessage());
            e.printStackTrace();
        }
        // {registro no encontrado}        
        return false;
    }

    /**
     * Retornar un {@code List<Cuartel>} con los {@code Cuartel} correspondientes a todos los
     * registros de la tabla "cuartel" de la base de datos "proyecto_final" cuyo valor en la columna
     * "estado" sea igual a true.
     */
    public List<Cuartel> listarCuarteles() {
        List<Cuartel> cuarteles = null;     // List<Cuartel> a retornar  

        try {
            // Crear y ejecutar un PreparedStatement (ps) para llevar a cabo la operación indicada
            // en la especificación del método. Almacenar resultado en un ResultSet (rs) y cerrar ps
            String sql = "SELECT * FROM cuartel "
                    + "WHERE cuartel.estado = true;";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            ps.close();

            // Añadir a cuarteles el Cuartel correspondiente a cada registro en rs 
            cuarteles = new ArrayList();
            while (rs.next()) {
                cuarteles.add(Utils.obtenerDeResultSetCuartel(rs));
            }

            // Informar por consola la cantidad de instancias de Cuartel en cuarteles
            System.out.println("[CuartelData.listarCuarteles] "
                    + "Cantidad de cuarteles: " + cuarteles.size());
        } catch (SQLException e) {
            // Informar por consola sobre excepción
            System.out.println("[CuartelData.listarCuarteles] "
                    + "Error " + e.getErrorCode() + ": " + e.getMessage());
            e.printStackTrace();
        }
        // {cuarteles tiene null o una o más instancias de Cuartel}
        return cuarteles;
    }

    /**
     * Retorna un {@code List<Brigada>} con los {@code Brigada} correspondientes a todos los
     * registros de la tabla "brigada" de la base de datos "proyecto_final" cuyo valor en la columna
     * "codigoCuartel" sea igual al valor de la propiedad {@code codigoCuartel} de {@code cuar} y
     * cuyo valor en la columna "estado" sea igual a true.
     */
    public List<Brigada> listarBrigadasDelCuartel(Cuartel cuar) {
        List<Brigada> brigadas = null; // List<Brigada> a retornar

        try {
            // Crear y ejecutar un PreparedStatement (ps) para llevar a cabo la operación indicada
            // en la especificación del método. Almacenar resultado en un ResultSet (rs) y cerrar ps
            String sql = "SELECT * FROM brigada "
                    + "JOIN cuartel ON (brigada.codigoCuartel = cuartel.codigoCuartel "
                    + "AND cuartel.codigoCuartel = ? "
                    + "AND cuartel.estado = true "
                    + "AND brigada.estado = true); ";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, cuar.getCodigoCuartel());
            ResultSet rs = ps.executeQuery();
            ps.close();

            // Añadir a brigadas el Brigada correspondiente a cada registro en rs 
            brigadas = new ArrayList();
            while (rs.next()) {
                Brigada brigada = Utils.obtenerDeResultSetBrigada(rs);
                brigadas.add(brigada);
            }

            // Informar por consola la cantidad de instancias de Brigada en brigadas
            System.out.println("[CuartelData.listarBrigadasEnCuartel] "
                    + "Cantidad de brigadas: " + brigadas.size());
        } catch (SQLException e) {
            // Informar por consola sobre excepción
            System.out.println("[CuartelData.listarBrigadasEnCuartel] "
                    + "Error " + e.getErrorCode() + ": " + e.getMessage());
            e.printStackTrace();
        }
        // {brigadas tiene null o una o más instancias de Brigada}
        return brigadas;
    }

    /**
     * Retornar un {@code List<Bombero>} con los {@code Bombero} correspondientes a todos los
     * registros de la tabla "bombero" de la base de datos "proyecto_final" tales que: 1) su valor
     * en la columna "codigoBrigada" sea igual al valor de la columa "codigoBrigada" de algún
     * registro X de la tabla "brigada" en la misma base de datos y, a la vez, X tenga un valor en
     * la columna "codigoCuartel" igual al de la propiedad {@code codigoCuartel} de {@code cuar}. 2)
     * su valor en la columna "estado" sea igual a true.
     */
    public List<Bombero> listarBomberosDelCuartel(Cuartel cuar) {
        List<Bombero> bomberos = null;      // List<Bombero> a retornar

        try {
            // Crear y ejecutar un PreparedStatement (ps) para llevar a cabo la operación indicada
            // en la especificación del método. Almacenar resultado en un ResultSet (rs) y cerrar ps
            String sql = "SELECT * FROM bombero "
                    + "JOIN brigada ON (bombero.codigoBrigada = brigada.codigoBrigada "
                    + "AND bombero.estado = true "
                    + "AND brigada.estado = true) "
                    + "JOIN cuartel ON (brigada.codigoCuartel = cuartel.codigoCuartel "
                    + "AND cuartel.codigoCuartel = ? "
                    + "AND cuartel.estado = true); ";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, cuar.getCodigoCuartel());
            ResultSet rs = ps.executeQuery();
            ps.close();

            // Añadir a bomberos el Bombero correspondiente a cada registro en rs 
            bomberos = new ArrayList();
            while (rs.next()) {
                bomberos.add(Utils.obtenerDeResultSetBombero(rs));
            }

            // Informar por consola la cantidad de instancias de Bombero en bomberos
            System.out.println("[CuartelData.listarBomberosDelCuartel] "
                    + "Cantidad de bomberos: " + bomberos.size());
        } catch (SQLException e) {
            // Informar por consola sobre excepción
            System.out.println("[CuartelData.listarBomberosDelCuartel] "
                    + "Error " + e.getErrorCode() + ": " + e.getMessage());
            e.printStackTrace();
        }
        // {bomberos tiene null o una o más instancias de Bombero}
        return bomberos;
    }

    /**
     * Si {@code cuar} tiene en sus propiedades valores válidos para llevar a cabo una modificación,
     * reemplazar los valores del registro de la tabla "cuartel" de la base de datos
     * "proyecto_final" cuyo valor en la columna "codigoCuartel" sea igual al valor de la propiedad
     * {@code codigoCuartel} de {@code cuar} con los valores de las propiedades de {@code cuar}
     * (excepto los de {@code codigoCuartel} y {@code estado}), si dicho registro existe. Retornar
     * true si modificó el registro.
     */
    public boolean modificarCuartel(Cuartel cuar) {
        // Si existe algún dato invalido en las propiedades de cuar, retornar false
        if (hayDatoInvalidoModificacion(cuar)) {
            System.out.println("[CuartelData.modificarCuartel] "
                    + "Error: cuartel con datos inválidos para una modificación: "
                    + cuar.debugToString());
            return false;
        }

        // Crear y ejecutar un PreparedStatement (ps) para llevar a cabo la operación indicada
        // en la especificación del método.             
        try {
            String sql = "UPDATE cuartel SET nombreCuartel = ?, direccion = ?, coordenadaX = ?, "
                    + "coordenadaY = ?, telefono = ?, correo = ? "
                    + "WHERE cuartel.codigoCuartel = ? "
                    + "AND cuartel.estado = true;";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, cuar.getNombreCuartel());
            ps.setString(2, cuar.getDireccion());
            ps.setDouble(3, cuar.getCoordenadaX());
            ps.setDouble(4, cuar.getCoordenadaY());
            ps.setString(5, cuar.getTelefono());
            ps.setString(6, cuar.getCorreo());
            ps.setInt(7, cuar.getCodigoCuartel());
            if (ps.executeUpdate() > 0) {
                // {registro modificado}
                // Informar sobre registro modificado y retornar true                
                System.out.println("[CuartelData.modificarCuartel] "
                        + "Modificado: " + cuar.debugToString());
                return true;
            } else {
                // {registro no modificado}
                // Informar sobre registro no modificado 
                System.out.println("[CuartelData.modificarCuartel] "
                        + "No se pudo modificar: " + cuar.debugToString());
            }

            // Cerrar ps
            ps.close();
        } catch (SQLException e) {
            // {registro no modificado}
            // Informar sobre excepción
            System.out.println("[CuartelData.modificarCuartel] "
                    + "Error " + e.getErrorCode() + ": " + e.getMessage());
            e.printStackTrace();
        }
        // {registro no modificado}
        return false;
    }

    /**
     * Si {@code cuar} tiene en sus propiedades valores válidos para llevar a cabo una eliminación
     * lógica, establecer en false el valor de la columna "estado" en el registro de la tabla
     * "cuartel" de la base de datos "proyecto_final" tal que: 1) su valor en el campo
     * "codigoCuartel" sea igual al de la propiedad {@code codigoCuartel} de {@code cuar}. 2) su
     * valor en el campo "estado" sea igual a true. 3) no existan registros de la tabla "brigada" de
     * la misma base de datos tales que: 3.1) su valor en la columna "codigoCuartel" sea igual al
     * valor de la propiedad {@code codigoCuartel} de {@code cuar} y 3.2) su valor en la columna
     * "estado" sea igual a true. Retornar true si se eliminó lógicamente el registro.
     */
    public boolean eliminarLogicamenteCuartel(Cuartel cuar) {
        // Si existe algún dato invalido en las propiedades de cuar, retornar false
        if (hayDatoInvalidoElimLog(cuar)) {
            System.out.println("[CuartelData.eliminarLogicamenteCuartel] "
                    + "Error: cuartel con datos inválidos para una eliminación: "
                    + cuar.debugToString());
            return false;
        }

        try {
            // Crear y ejecutar un PreparedStatement (ps) para llevar a cabo la operación indicada
            // en la especificación del método. 
            String sql = "UPDATE cuar SET estado = false "
                    + "WHERE codigoCuartel = ? "
                    + "AND estado = true "
                    + "AND (SELECT COUNT(*) FROM brigada "
                    + "WHERE codigoCuartel = ? "
                    + "AND estado = true) = 0;";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, cuar.getCodigoCuartel());
            ps.setInt(2, cuar.getCodigoCuartel());
            if (ps.executeUpdate() > 0) {
                // {registro eliminado lógicamente}
                // Establecer en false la propiedad estado de cuar
                cuar.setEstado(false);

                // Informar por consola sobre registro eliminado lógicamente y retornar true
                System.out.println("[CuartelData.eliminarCuartel] "
                        + "Eliminado: codigoCuartel=" + cuar.getCodigoCuartel());
                return true;
            } else {
                // {registro no eliminado lógicamente}
                // Informar por consola sobre registro no eliminado lógicamente
                System.out.println("[CuartelData.eliminarCuartel] "
                        + "No se pudo eliminar: codigoCuartel=" + cuar.getCodigoCuartel());
            }

            // Cerrar ps
            ps.close();
        } catch (SQLException e) {
            // {registro no eliminado lógicamente}
            System.out.println("[CuartelData.eliminarCuartel] "
                    + "Error " + e.getErrorCode() + ": " + e.getMessage());
            e.printStackTrace();
        }
        // {registro no eliminado lógicamente}
        return false;
    }
}
