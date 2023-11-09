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
    private String nombreCompleto;
    private String grupoSanguineo;
    private LocalDate fechaNacimiento;
    private String celular;
    private Brigada brigada;
    private boolean estado;

    public Bombero() {
    }

    // usuario inicializa todo menos 'idBombero' y 'estado'
    public Bombero(int dni, String nombreCompleto, String grupoSanguineo, LocalDate fechaNacimiento, String celular, Brigada brigada) {
        this.idBombero = -1;
        this.dni = dni;
        this.nombreCompleto = nombreCompleto;
        this.grupoSanguineo = grupoSanguineo;
        this.fechaNacimiento = fechaNacimiento;
        this.celular = celular;
        this.brigada = brigada;
        this.estado = true;
    }    
    
    // usuario inicializa todo 
    public Bombero(int dni, String nombreCompleto, String grupoSanguineo, LocalDate fechaNacimiento, String celular, Brigada brigada, int idBombero, boolean estado) {
        this.idBombero = idBombero;
        this.dni = dni;
        this.nombreCompleto = nombreCompleto;
        this.grupoSanguineo = grupoSanguineo;
        this.fechaNacimiento = fechaNacimiento;
        this.celular = celular;
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

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
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

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
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
    public String toString() {
        return "Bombero{" + "idBombero=" + idBombero + ", dni=" + dni + ", nombreCompleto=" + nombreCompleto + ", grupoSanguineo=" + grupoSanguineo + ", fechaNacimiento=" + fechaNacimiento + ", celular=" + celular + ", brigada=" + brigada + ", estado=" + estado + '}';
    }

}
