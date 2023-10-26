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
 *
 * @author Grupo-3
 */
public class CuartelData {

    /**
     * SUJETO A CAMBIOS
     */
    private Connection connection;
    
    public CuartelData() {
        this.connection = Conexion.getInstance();
        
    }

    public boolean guardarCuartel(Cuartel cuartel) {
        // TODO: Implementar este método
        boolean resultado = false;
        try{
            String sql;
            if(cuartel.getCodigoCuartel() == -1){
                sql="INSERT INTO cuartel(nombre_cuartel, direccion, coordenada_x, coordenada_y, telefono, correo) VALUES (?, ?, ?, ?, ?, ?)";
            }else{
                sql="INSERT INTO cuartel(nombre_cuartel, direccion, coordenada_x, coordenada_y, telefono, correo, codigo_cuartel) VALUES (?, ?, ?, ?, ?, ?, ?)";
            }
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1,cuartel.getNombreCuartel() );
            ps.setString(2,cuartel.getDireccion() );
            ps.setInt(3,cuartel.getCoordenadaX() );
            ps.setInt(4, cuartel.getCoordenadaY());
            ps.setInt(5, (int) cuartel.getTelefono());
            ps.setString(6, cuartel.getCorreo());
            if(cuartel.getCodigoCuartel() != -1){
                ps.setInt(7, cuartel.getCodigoCuartel());
            }
            if(ps.executeUpdate()>0){
                resultado=true;
                System.out.println("[CuartelData] cuartel agregado");
            }else{
                System.out.println("[CuartelData] cuartel no agregado");
            }
            ps.close();
        }catch(SQLException e){
            int errorCode = e.getErrorCode();
            System.out.println("[CuartelData Error " + errorCode + "] " + e.getMessage());
            if (errorCode != 1062) { // Ignorar datos repetidos
                e.printStackTrace();
            }
        }
        return resultado;
    }

    public Cuartel buscarCuartel(int codigo_cuartel) {
        // TODO: Implementar este método
        Cuartel cuartel = null;
        try{
            String sql="SELECT * FROM cuartel WHERE codigo_cuartel";
            PreparedStatement ps=connection.prepareStatement(sql);
            ps.setInt(1, codigo_cuartel);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                cuartel = new Cuartel();
                cuartel.setCodigoCuartel(rs.getInt("codigo_cuartel"));
                cuartel.setNombreCuartel(rs.getString("nombre_cuartel"));
                cuartel.setDireccion(rs.getString("direccion"));
                cuartel.setCoordenadaX(rs.getInt("coordenada_x"));
                cuartel.setCoordenadaY(rs.getInt("coordenada_y"));
                cuartel.setTelefono(rs.getInt("telefono"));
                cuartel.setCorreo(rs.getString("correo"));
                System.out.println("[CuartelData] cuartel con id=" + codigo_cuartel + " encontrado");
            }else{
                System.out.println("[CuartelData] No se ha encontrado al cuartel con id=" + codigo_cuartel);
            }
            ps.close();
        }catch(SQLException e){
            System.out.println("[CuartelData Error " + e.getErrorCode() + "] " + e.getMessage());
            e.printStackTrace();
        }
        return cuartel;
    }

    public Cuartel buscarCuartelPorNombre(String nombre_cuartel) {
        // TODO: Implementar este método
        Cuartel cuartel = null;
        try{
            String sql="SELECT * FROM cuartel WHERE nombre_cuartel";
            PreparedStatement ps=connection.prepareStatement(sql);
            ps.setString(1, nombre_cuartel);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                cuartel = new Cuartel();
                cuartel.setCodigoCuartel(rs.getInt("codigo_cuartel"));
                cuartel.setNombreCuartel(rs.getString("nombre_cuartel"));
                cuartel.setDireccion(rs.getString("direccion"));
                cuartel.setCoordenadaX(rs.getInt("coordenada_x"));
                cuartel.setCoordenadaY(rs.getInt("coordenada_y"));
                cuartel.setTelefono(rs.getInt("telefono"));
                cuartel.setCorreo(rs.getString("correo"));
                System.out.println("[CuartelData] cuartel con id=" + nombre_cuartel + " encontrado");
            }else{
                System.out.println("[CuartelData] No se ha encontrado al cuartel con id=" + nombre_cuartel);
            }
            ps.close();
        }catch(SQLException e){
            System.out.println("[CuartelData Error " + e.getErrorCode() + "] " + e.getMessage());
            e.printStackTrace();
        }
        return cuartel;
    }

    public List<Cuartel> listarCuarteles() {
        // TODO: Implementar este método
        List<Cuartel> cuarteles = new ArrayList();
        try{
            String sql="SELECT * FROM cuartel";
            PreparedStatement ps=connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            Cuartel cuartel;
            while(rs.next()){
                cuartel=new Cuartel();
                cuartel.setCodigoCuartel(rs.getInt("codigo_cuartel"));
                cuartel.setNombreCuartel(rs.getString("nombre_cuartel"));
                cuartel.setDireccion(rs.getString("direccion"));
                cuartel.setCoordenadaX(rs.getInt("coordenada_x"));
                cuartel.setCoordenadaY(rs.getInt("coordenada_y"));
                cuartel.setTelefono(rs.getInt("telefono"));
                cuartel.setCorreo(rs.getString("correo"));
                cuarteles.add(cuartel);
            }
            ps.close();
        }catch(SQLException e){
            System.out.println("[CuartelData Error " + e.getErrorCode() + "] " + e.getMessage());
            e.printStackTrace();
        }
        return cuarteles;
    }

    public List<Brigada> listarBrigadasEnCuartel(int codigoCuartel) {
        // TODO: Implementar este método
        List<Brigada> brigadas = new ArrayList();
        try{
            //SELECT*FROM brigada WHERE codigoBrigda IN(SELECT codigoBrigada FROM cuartel WHERE codigoBrigada=?)AND estado=true;
            String sql="SELECT*FROM brigada WHERE codigo_cuartel IN(SELECT codigo_cuartel FROM cuartel WHERE codigo_cuartal=?)AND estado=true";
            PreparedStatement ps=connection.prepareStatement(sql);
                ps.setInt(1,codigoCuartel);
            ResultSet rs= ps.executeQuery();
            
            Brigada brigada;
            while(rs.next()){
                brigada=new Brigada();
                brigada.setCodigoBrigada(rs.getInt("codigo_brigada"));
                brigada.setCodigoCuartel(rs.getInt("codigo_cuartel"));
                brigada.setEspecialidad(rs.getString("especialidad"));
                brigada.setNombreBrigada(rs.getString("nombreBrigada"));
                brigada.setDisponible(rs.getBoolean("disponible"));
                brigadas.add(brigada);
                
            }
        }catch(SQLException e){
            System.out.println("[CuartelData Error " + e.getErrorCode() + "] " + e.getMessage());
            e.printStackTrace();
        }
        return brigadas;
    }

    public List<Bombero> listarBomberosEnCuartel(Cuartel cuartel) {
        // TODO: Implementar este método
        List<Bombero> bomberos = new ArrayList();
        try{
            String sql="";
            PreparedStatement ps=connection.prepareStatement(sql);
            ResultSet rs= ps.executeQuery();
            Bombero bombero;
            while(rs.next()){
                //inserta cuartel
                
            }
        }catch(SQLException e){
            System.out.println("[CuartelData Error " + e.getErrorCode() + "] " + e.getMessage());
            e.printStackTrace();
        }
        return bomberos;
    }

    public boolean modificarCuartel(Cuartel cuartel) {
        // TODO: Implementar este método
        boolean resultado = false;
        try{
            String sql="UPDATE cuartel SET nombre_cuartel= ? , direccion = ? , coordenada_x = ? , coordenada_y = ? , telefono = ? , correo = ? WHERE codigo_cuartel=?";
            PreparedStatement ps=connection.prepareStatement(sql);
            ps.setString(1,cuartel.getNombreCuartel() );
            ps.setString(2,cuartel.getDireccion() );
            ps.setInt(3,cuartel.getCoordenadaX() );
            ps.setInt(4, cuartel.getCoordenadaY());
            ps.setInt(5, (int) cuartel.getTelefono());
            ps.setString(6, cuartel.getCorreo());
            ps.setInt(7, cuartel.getCodigoCuartel());
            if(ps.executeUpdate()>0) {
                resultado=true;
                System.out.println("[CuartelData] Cuartel modificado");
            }else{
                System.out.println("[CuartelData] No se pudo modificar el Cuartel");
            }
        }catch(SQLException e){
            System.out.println("[CuartelData Error " + e.getErrorCode() + "] " + e.getMessage());
            e.printStackTrace();
        }
        return resultado;
    }

    public boolean eliminarCuartel(int codigo_cuartel) {
        // TODO: Implementar este método
        boolean resultado = false;
        try{
            String sql="UPDATE cuartel SET estado=false WHERE codigo_cuartel=?";
            PreparedStatement ps=connection.prepareStatement(sql);
            ps.setInt(1, codigo_cuartel);
            if(ps.executeUpdate() > 0){
                resultado=true;
                System.out.println("[CuartelData] Cuartel modificado");
            }else{
                System.out.println("[CuartelData] No se pudo modificar el Cuartel");
            }
        }catch(SQLException e){
            System.out.println("[CuartelData Error " + e.getErrorCode() + "] " + e.getMessage());
            e.printStackTrace();
        }
        return resultado;
    }
    
    public boolean eliminarNombre(String nombre_cuartel){
        boolean resultado = false;
        try{
            String sql="UPDATE cuartel SET estado=false WHERE nombre_cuartel=?";
            PreparedStatement ps=connection.prepareStatement(sql);
            ps.setString(1, nombre_cuartel);
            if(ps.executeUpdate() > 0){
                resultado=true;
                System.out.println("[CuartelData] Cuartel modificado");
            }else{
                System.out.println("[CuartelData] No se pudo modificar el Cuartel");
            }
        }catch(SQLException e){
            System.out.println("[CuartelData Error " + e.getErrorCode() + "] " + e.getMessage());
            e.printStackTrace();
        }
        return resultado;
    }

}
