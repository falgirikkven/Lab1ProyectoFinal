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
    private int id_bombero;
    private int dni;
    private String nombre_apellido;
    private LocalDate fechaNacimiento;
    private long telefono;
    private int codigo_brigada;

    public Bombero() {
    }

    public Bombero(int dni, String nombre_apellido, LocalDate fechaNacimiento, long telefono, int codigo_brigada) {
        this.id_bombero = -1;
        this.dni = dni;
        this.nombre_apellido = nombre_apellido;
        this.fechaNacimiento = fechaNacimiento;
        this.telefono = telefono;
        this.codigo_brigada = codigo_brigada;
    }

    public Bombero(int id_bombero, int dni, String nombre_apellido, LocalDate fechaNacimiento, long telefono, int codigo_brigada) {
        this.id_bombero = id_bombero;
        this.dni = dni;
        this.nombre_apellido = nombre_apellido;
        this.fechaNacimiento = fechaNacimiento;
        this.telefono = telefono;
        this.codigo_brigada = codigo_brigada;
    }

    public int getId_bombero() {
        return id_bombero;
    }

    public void setId_bombero(int id_bombero) {
        this.id_bombero = id_bombero;
    }

    public int getDni() {
        return dni;
    }

    public void setDni(int dni) {
        this.dni = dni;
    }

    public String getNombre_apellido() {
        return nombre_apellido;
    }

    public void setNombre_apellido(String nombre_apellido) {
        this.nombre_apellido = nombre_apellido;
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

    @Override
    public String toString() {
        return "Bombero{" + "id_bombero=" + id_bombero + ", dni=" + dni + ", nombre_apellido=" + nombre_apellido + ", fechaNacimiento=" + fechaNacimiento + ", telefono=" + telefono + ", codigo_brigada=" + codigo_brigada + '}';
    }

}
