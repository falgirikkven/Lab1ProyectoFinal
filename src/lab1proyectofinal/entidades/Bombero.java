package lab1proyectofinal.entidades;

import java.time.LocalDate;

/**
 *
 * @author Grupo-3
 */
public class Bombero {

    /**
     * SUJETO A CAMBIOS
     */
    private int idBombero;
    private int dni;
    private String nombreApellido;
    private LocalDate fechaNacimiento;
    private long telefono;
    private int codigo_brigada;
    private boolean estado;

    public Bombero() {
    }

    public Bombero(int dni, String nombreApellido, LocalDate fechaNacimiento, long telefono, int codigo_brigada, boolean estado) {
        this.idBombero = -1;
        this.dni = dni;
        this.nombreApellido = nombreApellido;
        this.fechaNacimiento = fechaNacimiento;
        this.telefono = telefono;
        this.codigo_brigada = codigo_brigada;
        this.estado = estado;
    }

    public Bombero(int idBombero, int dni, String nombreApellido, LocalDate fechaNacimiento, long telefono, int codigo_brigada, boolean estado) {
        this.idBombero = idBombero;
        this.dni = dni;
        this.nombreApellido = nombreApellido;
        this.fechaNacimiento = fechaNacimiento;
        this.telefono = telefono;
        this.codigo_brigada = codigo_brigada;
        this.estado = estado;
    }

    public int getIdBombero() {
        return idBombero;
    }

    public void setIdBombero(int idBombero) {
        this.idBombero = idBombero;
    }

    public int getDni() {
        return dni;
    }

    public void setDni(int dni) {
        this.dni = dni;
    }

    public String getNombreApellido() {
        return nombreApellido;
    }

    public void setNombreApellido(String nombreApellido) {
        this.nombreApellido = nombreApellido;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public long getTelefono() {
        return telefono;
    }

    public void setTelefono(long telefono) {
        this.telefono = telefono;
    }

    public int getCodigo_brigada() {
        return codigo_brigada;
    }

    public void setCodigo_brigada(int codigo_brigada) {
        this.codigo_brigada = codigo_brigada;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Bombero{" + "idBombero=" + idBombero + ", dni=" + dni + ", nombreApellido=" + nombreApellido + ", fechaNacimiento=" + fechaNacimiento + ", telefono=" + telefono + ", codigo_brigada=" + codigo_brigada + '}';
    }

}
