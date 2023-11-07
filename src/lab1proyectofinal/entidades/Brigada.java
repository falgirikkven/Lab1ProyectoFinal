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
    private boolean enCuartel;
    private int cantBomberos;
    private Cuartel cuartel;
    private boolean estado;

    public Brigada() {
    }

    // el usuario inicializa todo menos 'codigoBrigada', 'cantBomberos' y 'estado'
    public Brigada(String nombreBrigada, String especialidad, boolean enCuartel, Cuartel cuartel) {
        this.codigoBrigada = -1;
        this.nombreBrigada = nombreBrigada;
        this.especialidad = especialidad;
        this.enCuartel = enCuartel;
        this.cantBomberos = 0;
        this.cuartel = cuartel;
        this.estado = true;
    }
    
    // el usuario inicializa todo menos 'codigoBrigada'
    public Brigada(String nombreBrigada, String especialidad, boolean enCuartel, int cantBomberos, Cuartel cuartel, boolean estado) {
        this.nombreBrigada = nombreBrigada;
        this.especialidad = especialidad;
        this.enCuartel = enCuartel;
        this.cantBomberos = cantBomberos;
        this.cuartel = cuartel;
        this.estado = estado;
    }
    

    // el usuario inicializa todo 
    public Brigada(int codigoBrigada, String nombreBrigada, String especialidad, boolean enCuartel, int cantBomberos, Cuartel cuartel, boolean estado) {
        this.codigoBrigada = codigoBrigada;
        this.nombreBrigada = nombreBrigada;
        this.especialidad = especialidad;
        this.enCuartel = enCuartel;
        this.cantBomberos = cantBomberos;
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

    public boolean isEnCuartel() {
        return enCuartel;
    }

    public void setEnCuartel(boolean enCuartel) {
        this.enCuartel = enCuartel;
    }

    public int getCantBomberos() {
        return cantBomberos;
    }

    public void setCantBomberos(int cantBomberos) {
        this.cantBomberos = cantBomberos;
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
        return "Brigada{" + "codigoBrigada=" + codigoBrigada + ", nombreBrigada=" + nombreBrigada + ", especialidad=" + especialidad + ", enCuartel=" + enCuartel + ", cantBomberos=" + cantBomberos + ", cuartel=" + cuartel + ", estado=" + estado + '}';
    }

}
