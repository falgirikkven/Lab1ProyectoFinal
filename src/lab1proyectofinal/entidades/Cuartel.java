package lab1proyectofinal.entidades;

/**
 *
 * @author Grupo-3
 */
public class Cuartel {

    /**
     * SUJETO A CAMBIOS
     */
    private int codigoCuartel;
    private String nombreCuartel;
    private String direccion;
    private int coordenadaX;
    private int coordenadaY;
    private long telefono;
    private String correo;
    private boolean estado;

    public Cuartel() {
    }

    //public Cuartel(String nombreCuartel, String direccion, int coordenadaX, int coordenadaY, long telefono, String correo) {
        //this.codigoCuartel = -1;
        //this.nombreCuartel = nombreCuartel;
        //this.direccion = direccion;
        //this.coordenadaX = coordenadaX;
        //this.coordenadaY = coordenadaY;
        //this.telefono = telefono;
        //this.correo = correo;
    //}

    public Cuartel(String nombreCuartel, String direccion, int coordenadaX, int coordenadaY, long telefono, String correo, boolean estado) {
        this.codigoCuartel = -1;
        this.nombreCuartel = nombreCuartel;
        this.direccion = direccion;
        this.coordenadaX = coordenadaX;
        this.coordenadaY = coordenadaY;
        this.telefono = telefono;
        this.correo = correo;
        this.estado = estado;
    }
    
    //public Cuartel(int codigoCuartel, String nombreCuartel, String direccion, int coordenadaX, int coordenadaY, long telefono, String correo) {
        //this.codigoCuartel = codigoCuartel;
        //this.nombreCuartel = nombreCuartel;
        //this.direccion = direccion;
        //this.coordenadaX = coordenadaX;
        //this.coordenadaY = coordenadaY;
        //this.telefono = telefono;
        //this.correo = correo;
    //}

    public Cuartel(int codigoCuartel, String nombreCuartel, String direccion, int coordenadaX, int coordenadaY, long telefono, String correo, boolean estado) {
        this.codigoCuartel = codigoCuartel;
        this.nombreCuartel = nombreCuartel;
        this.direccion = direccion;
        this.coordenadaX = coordenadaX;
        this.coordenadaY = coordenadaY;
        this.telefono = telefono;
        this.correo = correo;
        this.estado = estado;
    }

    public int getCodigoCuartel() {
        return codigoCuartel;
    }

    public void setCodigoCuartel(int codigoCuartel) {
        this.codigoCuartel = codigoCuartel;
    }

    public String getNombreCuartel() {
        return nombreCuartel;
    }

    public void setNombreCuartel(String nombreCuartel) {
        this.nombreCuartel = nombreCuartel;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public int getCoordenadaX() {
        return coordenadaX;
    }

    public void setCoordenadaX(int coordenadaX) {
        this.coordenadaX = coordenadaX;
    }

    public int getCoordenadaY() {
        return coordenadaY;
    }

    public void setCoordenadaY(int coordenadaY) {
        this.coordenadaY = coordenadaY;
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

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    
    @Override
    public String toString() {
        return "Cuartel{" + "codigoCuartel=" + codigoCuartel + ", nombreCuartel=" + nombreCuartel + ", direccion=" + direccion + ", coordenadaX=" + coordenadaX + ", coordenadaY=" + coordenadaY + ", telefono=" + telefono + ", correo=" + correo + ", estado=" + estado + '}';
    }

}
