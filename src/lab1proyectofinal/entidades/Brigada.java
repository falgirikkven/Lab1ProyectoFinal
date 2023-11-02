package lab1proyectofinal.entidades;

/**
 *
 * @author Grupo-3
 */
public class Brigada {

    /**
     * SUJETO A CAMBIOS
     */
    private int codigoBrigada;
    private String nombreBrigada;
    private String especialidad;
    private boolean disponible;
    private int codigoCuartel;
    private boolean estado;

    public Brigada() {
    }

    public Brigada(String nombreBrigada, String especialidad, boolean disponible, int codigoCuartel) {
        this.codigoBrigada = -1;
        this.nombreBrigada = nombreBrigada;
        this.especialidad = especialidad;
        this.disponible = disponible;
        this.codigoCuartel = codigoCuartel;
        this.estado = true;
    }

    public Brigada(int codigoBrigada, String nombreBrigada, String especialidad, boolean disponible, int codigoCuartel, boolean estado) {
        this.codigoBrigada = codigoBrigada;
        this.nombreBrigada = nombreBrigada;
        this.especialidad = especialidad;
        this.disponible = disponible;
        this.codigoCuartel = codigoCuartel;
        this.estado = estado;
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

    public int getCodigoCuartel() {
        return codigoCuartel;
    }

    public void setCodigoCuartel(int codigoCuartel) {
        this.codigoCuartel = codigoCuartel;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Brigada{" + "codigoBrigada=" + codigoBrigada + ", nombreBrigada=" + nombreBrigada + ", especialidad=" + especialidad + ", disponible=" + disponible + ", codigoCuartel=" + codigoCuartel + ", estado=" + estado + '}';
    }
}
