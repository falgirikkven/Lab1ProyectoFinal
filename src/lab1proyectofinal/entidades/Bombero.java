package lab1proyectofinal.entidades;

import java.time.LocalDate;
import lab1proyectofinal.accesoADatos.Utils;

/**
 *
 * @author Grupo-3
 */
public class Bombero {

    private int idBombero = Utils.codigoNoEstablecido;
    private int dni;
    private String nombreCompleto;
    private String grupoSanguineo;
    private LocalDate fechaNacimiento;
    private String celular;
    private Brigada brigada;
    private boolean estado = true;

    public Bombero() {
    }

    public Bombero(int dni, String nombreCompleto, String grupoSanguineo, LocalDate fechaNacimiento, String celular, Brigada brigada) {
        this.dni = dni;
        this.nombreCompleto = nombreCompleto;
        this.grupoSanguineo = grupoSanguineo;
        this.fechaNacimiento = fechaNacimiento;
        this.celular = celular;
        this.brigada = brigada;
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

    public String debugToString() {
        return "Bombero{" + "idBombero=" + idBombero + ", dni=" + dni + ", nombreCompleto=" + nombreCompleto + ", grupoSanguineo=" + grupoSanguineo + ", fechaNacimiento=" + fechaNacimiento + ", celular=" + celular + ", codigoBrigada=" + Integer.toString(brigada.getCodigoBrigada()) + ", estado=" + estado + '}';
    }

    @Override
    public String toString() {
        return "DNI: " + dni + ", Nombre:" + nombreCompleto;
    }
}
