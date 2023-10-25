package lab1proyectofinal.entidades;

/**
 *
 * @author Grupo-3
 */
public class Cuartel {

    /**
     * SUJETO A CAMBIOS
     */
    private int codigo_cuartel;
    private String nombre_cuartel;
    private String direccion;
    private int coordenada_x;
    private int coordenada_y;
    private long telefono;
    private String correo;

    public Cuartel() {
    }

    public Cuartel(String nombre_cuartel, String direccion, int coordenada_x, int coordenada_y, long telefono, String correo) {
        this.codigo_cuartel = -1;
        this.nombre_cuartel = nombre_cuartel;
        this.direccion = direccion;
        this.coordenada_x = coordenada_x;
        this.coordenada_y = coordenada_y;
        this.telefono = telefono;
        this.correo = correo;
    }

    public Cuartel(int codigo_cuartel, String nombre_cuartel, String direccion, int coordenada_x, int coordenada_y, long telefono, String correo) {
        this.codigo_cuartel = codigo_cuartel;
        this.nombre_cuartel = nombre_cuartel;
        this.direccion = direccion;
        this.coordenada_x = coordenada_x;
        this.coordenada_y = coordenada_y;
        this.telefono = telefono;
        this.correo = correo;
    }

    public int getCodigo_cuartel() {
        return codigo_cuartel;
    }

    public void setCodigo_cuartel(int codigo_cuartel) {
        this.codigo_cuartel = codigo_cuartel;
    }

    public String getNombre_cuartel() {
        return nombre_cuartel;
    }

    public void setNombre_cuartel(String nombre_cuartel) {
        this.nombre_cuartel = nombre_cuartel;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public int getCoordenada_x() {
        return coordenada_x;
    }

    public void setCoordenada_x(int coordenada_x) {
        this.coordenada_x = coordenada_x;
    }

    public int getCoordenada_y() {
        return coordenada_y;
    }

    public void setCoordenada_y(int coordenada_y) {
        this.coordenada_y = coordenada_y;
    }

    public long getTelefono() {
        return telefono;
    }

    public void setTelefono(long telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    @Override
    public String toString() {
        return "Cuartel{" + "codigo_cuartel=" + codigo_cuartel + ", nombre_cuartel=" + nombre_cuartel + ", direccion=" + direccion + ", coordenada_x=" + coordenada_x + ", coordenada_y=" + coordenada_y + ", telefono=" + telefono + ", correo=" + correo + '}';
    }

}
