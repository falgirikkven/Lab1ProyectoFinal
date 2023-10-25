package lab1proyectofinal.entidades;

/**
 *
 * @author Grupo-3
 */
public class Brigada {

    /**
     * SUJETO A CAMBIOS
     */
    private int codigo_brigada;
    private String nombre_brigada;
    private String especialidad;
    private boolean disponible;
    private int codigo_cuartel;

    public Brigada() {
    }

    public Brigada(String nombre_brigada, String especialidad, boolean disponible, int codigo_cuartel) {
        this.codigo_brigada = -1;
        this.nombre_brigada = nombre_brigada;
        this.especialidad = especialidad;
        this.disponible = disponible;
        this.codigo_cuartel = codigo_cuartel;
    }

    public Brigada(int codigo_brigada, String nombre_brigada, String especialidad, boolean disponible, int codigo_cuartel) {
        this.codigo_brigada = codigo_brigada;
        this.nombre_brigada = nombre_brigada;
        this.especialidad = especialidad;
        this.disponible = disponible;
        this.codigo_cuartel = codigo_cuartel;
    }

    public int getCodigo_brigada() {
        return codigo_brigada;
    }

    public void setCodigo_brigada(int codigo_brigada) {
        this.codigo_brigada = codigo_brigada;
    }

    public String getNombre_brigada() {
        return nombre_brigada;
    }

    public void setNombre_brigada(String nombre_brigada) {
        this.nombre_brigada = nombre_brigada;
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
        return codigo_cuartel;
    }

    public void setCodigo_cuartel(int codigo_cuartel) {
        this.codigo_cuartel = codigo_cuartel;
    }

    @Override
    public String toString() {
        return "Brigada{" + "codigo_brigada=" + codigo_brigada + ", nombre_brigada=" + nombre_brigada + ", especialidad=" + especialidad + ", disponible=" + disponible + ", codigo_cuartel=" + codigo_cuartel + '}';
    }

}
