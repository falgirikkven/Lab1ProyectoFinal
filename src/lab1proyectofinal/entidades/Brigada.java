package lab1proyectofinal.entidades;

import lab1proyectofinal.accesoADatos.Utils;

/**
 *
 * @author Grupo-3
 */
public class Brigada {

    private int codigoBrigada = Utils.codigoNoEstablecido;
    private String nombreBrigada;
    private String especialidad;
    private boolean enServicio;
    private Cuartel cuartel;
    private boolean estado = true;

    public Brigada() {
    }

    public Brigada(String nombreBrigada, String especialidad, boolean enServicio, Cuartel cuartel) {
        this.nombreBrigada = nombreBrigada;
        this.especialidad = especialidad;
        this.enServicio = enServicio;
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

    public boolean isEnServicio() {
        return enServicio;
    }

    public void setEnServicio(boolean enServicio) {
        this.enServicio = enServicio;
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

    public String debugToString() {
        return "Brigada{" + "codigoBrigada=" + codigoBrigada + ", nombreBrigada=" + nombreBrigada + ", especialidad=" + especialidad + ", enServicio=" + enServicio + ", codigoCuartel=" + Integer.toString(cuartel.getCodigoCuartel()) + ", estado=" + estado + '}';
    }
        
    @Override
    public String toString() {
        return "Nombre: " + nombreBrigada;
    }
}
