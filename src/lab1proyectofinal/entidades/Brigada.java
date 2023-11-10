package lab1proyectofinal.entidades;

import lab1proyectofinal.accesoADatos.Utils;

/**
 *
 * @author Grupo-3
 */
public class Brigada {

    /**
     * SUJETO A CAMBIOS
     */
    private int codigoBrigada = Utils.NIL;
    private String nombreBrigada;
    private String especialidad;
    private boolean disponible;
    private Cuartel cuartel;
    private boolean estado = true;

    public Brigada() {
    }

    public Brigada(String nombreBrigada, String especialidad, boolean disponible, Cuartel cuartel) {
        this.nombreBrigada = nombreBrigada;
        this.especialidad = especialidad;
        this.disponible = disponible;
        this.cuartel = cuartel;
    }

    public int getCodigoBrigada() {
        return codigoBrigada;
    }

    public void setCodigoBrigada(int codigoBrigada) {
        this.codigoBrigada = codigoBrigada;
    }

    public String getNombreBrigada() {
        return nombreBrigada;
    }

    public void setNombreBrigada(String nombreBrigada) {
        this.nombreBrigada = nombreBrigada;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public Cuartel getCuartel() {
        return cuartel;
    }

    public void setCuartel(Cuartel cuartel) {
        this.cuartel = cuartel;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public String DebugToString() {
        return "Brigada{" + "codigoBrigada=" + codigoBrigada + ", nombreBrigada=" + nombreBrigada + ", especialidad=" + especialidad + ", disponible=" + disponible + ", codigoCuartel=" + Integer.toString(cuartel.getCodigoCuartel()) + ", estado=" + estado + '}';
    }

}
