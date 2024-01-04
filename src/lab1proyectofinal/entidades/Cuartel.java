package lab1proyectofinal.entidades;

import lab1proyectofinal.accesoADatos.Utils;

/**
 *
 * @author Grupo-3
 */
public class Cuartel {

    private int codigoCuartel = Utils.codigoNoEstablecido;
    private String nombreCuartel;
    private String direccion;
    private double coordenadaX;
    private double coordenadaY;
    private String telefono;
    private String correo;
    private boolean estado = true;

    public Cuartel() {
    }

    public Cuartel(String nombreCuartel, String direccion, double coordenadaX, double coordenadaY, String telefono, String correo) {
        this.nombreCuartel = nombreCuartel;
        this.direccion = direccion;
        this.coordenadaX = coordenadaX;
        this.coordenadaY = coordenadaY;
        this.telefono = telefono;
        this.correo = correo;
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

    public double getCoordenadaX() {
        return coordenadaX;
    }

    public void setCoordenadaX(double coordenadaX) {
        this.coordenadaX = coordenadaX;
    }

    public double getCoordenadaY() {
        return coordenadaY;
    }

    public void setCoordenadaY(double coordenadaY) {
        this.coordenadaY = coordenadaY;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
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

    public String debugToString() {
        return "Cuartel{" + "codigoCuartel=" + codigoCuartel + ", nombreCuartel=" + nombreCuartel + ", direccion=" + direccion + ", coordenadaX=" + coordenadaX + ", coordenadaY=" + coordenadaY + ", telefono=" + telefono + ", correo=" + correo + ", estado=" + estado + '}';
    }

    @Override
    public String toString() {
        return "Nombre: " + nombreCuartel;
    }
}
