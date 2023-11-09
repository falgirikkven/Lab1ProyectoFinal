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
    private boolean tratandoSiniestro;
    private Cuartel cuartel;
    private boolean estado;

    public Brigada() {
    }

    // el usuario inicializa todo menos 'codigoBrigada' y 'estado'
    public Brigada(String nombreBrigada, String especialidad, boolean tratandoSiniestro, Cuartel cuartel) {
        this.codigoBrigada = -1;
        this.nombreBrigada = nombreBrigada;
        this.especialidad = especialidad;
        this.tratandoSiniestro = tratandoSiniestro;
        this.cuartel = cuartel;
        this.estado = true;
    }
    
    // el usuario inicializa todo     
    public Brigada(String nombreBrigada, String especialidad, boolean tratandoSiniestro, Cuartel cuartel, int codigoBrigada, boolean estado) {
        this.codigoBrigada = codigoBrigada;
        this.nombreBrigada = nombreBrigada;
        this.especialidad = especialidad;
        this.tratandoSiniestro = tratandoSiniestro;
        this.cuartel = cuartel;
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

    public boolean isTratandoSiniestro() {
        return tratandoSiniestro;
    }

    public void setTratandoSiniestro(boolean tratandoSiniestro) {
        this.tratandoSiniestro = tratandoSiniestro;
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

    @Override
    public String toString() {
        return "Brigada{" + "codigoBrigada=" + codigoBrigada + ", nombreBrigada=" + nombreBrigada + ", especialidad=" + especialidad + ", tratandoSiniestro=" + tratandoSiniestro + ", cuartel=" + cuartel + ", estado=" + estado + '}';
    }
}
