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
    private String grupoSanguineo;
    private LocalDate fechaNacimiento;
    private long telefono;
    private Brigada brigada;
    private boolean estado;

    public Bombero() {
    }

    public Bombero(int dni, String nombreApellido, String grupoSanguineo, LocalDate fechaNacimiento, long telefono, Brigada brigada) {
        this.idBombero = -1;
        this.dni = dni;
        this.nombreApellido = nombreApellido;
        this.grupoSanguineo = grupoSanguineo;
        this.fechaNacimiento = fechaNacimiento;
        this.telefono = telefono;
        this.brigada = brigada;
        this.estado = true;
    }

    public Bombero(int idBombero, int dni, String nombreApellido, String grupoSanguineo, LocalDate fechaNacimiento, long telefono, Brigada brigada, boolean estado) {
        this.idBombero = idBombero;
        this.dni = dni;
        this.nombreApellido = nombreApellido;
        this.grupoSanguineo = grupoSanguineo;
        this.fechaNacimiento = fechaNacimiento;
        this.telefono = telefono;
        this.brigada = brigada;
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

    public String getGrupoSanguineo() {
        return grupoSanguineo;
    }

    public void setGrupoSanguineo(String grupoSanguineo) {
        this.grupoSanguineo = grupoSanguineo;
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

    public Brigada getBrigada() {
        return brigada;
    }

    public void setBrigada(Brigada brigada) {
        this.brigada = brigada;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {  // potencialmente prescindible
        return "Bombero{" + "idBombero=" + idBombero + ", dni=" + dni + ", nombreApellido=" + nombreApellido + ", grupoSanguineo=" + grupoSanguineo + ", fechaNacimiento=" + fechaNacimiento + ", telefono=" + telefono + ", brigada=" + brigada + ", estado=" + estado + '}';
    }

    public String mostrarDatos() {
        return "Bombero{" + "idBombero=" + idBombero + ", dni=" + dni + ", nombreApellido=" + nombreApellido + ", grupoSanguineo=" + grupoSanguineo + ", fechaNacimiento=" + fechaNacimiento + ", telefono=" + telefono + ", brigada=" + brigada + ", estado=" + estado + '}';
    }

}
