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

    public Brigada() {
    }

    public Brigada(String nombreBrigada, String especialidad, boolean disponible, int codigoCuartel) {
        this.codigoBrigada = -1;
        this.nombreBrigada = nombreBrigada;
        this.especialidad = especialidad;
        this.disponible = disponible;
        this.codigoCuartel = codigoCuartel;
    }

    public Brigada(int codigoBrigada, String nombreBrigada, String especialidad, boolean disponible, int codigoCuartel) {
        this.codigoBrigada = codigoBrigada;
        this.nombreBrigada = nombreBrigada;
        this.especialidad = especialidad;
        this.disponible = disponible;
        this.codigoCuartel = codigoCuartel;
    }

    public int getCodigoBrigada() {
        return codigoBrigada;
    }

    public void setCodigoBrigada(int codigoBrigada) {
        this.codigoBrigada = codigoBrigada;
    }

    public String getNombre_brigada() {
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

    public int getCodigo_cuartel() {
        return codigoCuartel;
    }

    public void setCodigoCuartel(int codigoCuartel) {
        this.codigoCuartel = codigoCuartel;
    }

    @Override
    public String toString() {
        return "Brigada{" + "codigo_brigada=" + codigoBrigada + ", nombre_brigada=" + nombreBrigada + ", especialidad=" + especialidad + ", disponible=" + disponible + ", codigo_cuartel=" + codigoCuartel + '}';
    }

}
