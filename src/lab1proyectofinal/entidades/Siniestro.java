package lab1proyectofinal.entidades;

import java.time.LocalDate;

/**
 *
 * @author Grupo-3
 */
public class Siniestro {

    /**
     * SUJETO A CAMBIOS
     */
    private int codigo;
    private String tipo;
    private LocalDate fecha_siniestro;
    private int coordenada_x;
    private int coordenada_y;
    private String detalles;
    private LocalDate fecha_resolucion;
    private int puntuacion;
    private int codigo_brigada;

    public Siniestro() {
    }

    public Siniestro(String tipo, LocalDate fecha_siniestro, int coordenada_x, int coordenada_y, String detalles) {
        this.codigo = -1;
        this.tipo = tipo;
        this.fecha_siniestro = fecha_siniestro;
        this.coordenada_x = coordenada_x;
        this.coordenada_y = coordenada_y;
        this.detalles = detalles;
        this.fecha_resolucion = null;
        this.puntuacion = -1;
        this.codigo_brigada = -1;
    }

    public Siniestro(int codigo, String tipo, LocalDate fecha_siniestro, int coordenada_x, int coordenada_y, String detalles) {
        this.codigo = codigo;
        this.tipo = tipo;
        this.fecha_siniestro = fecha_siniestro;
        this.coordenada_x = coordenada_x;
        this.coordenada_y = coordenada_y;
        this.detalles = detalles;
        this.fecha_resolucion = null;
        this.puntuacion = -1;
        this.codigo_brigada = -1;
    }

    public Siniestro(int codigo, String tipo, LocalDate fecha_siniestro, int coordenada_x, int coordenada_y, String detalles, int codigo_brigada) {
        this.codigo = codigo;
        this.tipo = tipo;
        this.fecha_siniestro = fecha_siniestro;
        this.coordenada_x = coordenada_x;
        this.coordenada_y = coordenada_y;
        this.detalles = detalles;
        this.fecha_resolucion = null;
        this.puntuacion = -1;
        this.codigo_brigada = codigo_brigada;
    }

    public Siniestro(int codigo, String tipo, LocalDate fecha_siniestro, int coordenada_x, int coordenada_y, String detalles, LocalDate fecha_resolucion, int puntuacion, int codigo_brigada) {
        this.codigo = codigo;
        this.tipo = tipo;
        this.fecha_siniestro = fecha_siniestro;
        this.coordenada_x = coordenada_x;
        this.coordenada_y = coordenada_y;
        this.detalles = detalles;
        this.fecha_resolucion = fecha_resolucion;
        this.puntuacion = puntuacion;
        this.codigo_brigada = codigo_brigada;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public LocalDate getFecha_siniestro() {
        return fecha_siniestro;
    }

    public void setFecha_siniestro(LocalDate fecha_siniestro) {
        this.fecha_siniestro = fecha_siniestro;
    }

    public int getCoordenada_x() {
        return coordenada_x;
    }

    public void setCoordenada_x(int coordenada_x) {
        this.coordenada_x = coordenada_x;
    }

    public int getCoordenada_y() {
        return coordenada_y;
    }

    public void setCoordenada_y(int coordenada_y) {
        this.coordenada_y = coordenada_y;
    }

    public String getDetalles() {
        return detalles;
    }

    public void setDetalles(String detalles) {
        this.detalles = detalles;
    }

    public LocalDate getFecha_resolucion() {
        return fecha_resolucion;
    }

    public void setFecha_resolucion(LocalDate fecha_resolucion) {
        this.fecha_resolucion = fecha_resolucion;
    }

    public int getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(int puntuacion) {
        this.puntuacion = puntuacion;
    }

    public int getCodigo_brigada() {
        return codigo_brigada;
    }

    public void setCodigo_brigada(int codigo_brigada) {
        this.codigo_brigada = codigo_brigada;
    }

    @Override
    public String toString() {
        return "Siniestro{" + "codigo=" + codigo + ", tipo=" + tipo + ", fecha_siniestro=" + fecha_siniestro + ", coordenada_x=" + coordenada_x + ", coordenada_y=" + coordenada_y + ", detalles=" + detalles + ", fecha_resolucion=" + fecha_resolucion + ", puntuacion=" + puntuacion + ", codigo_brigada=" + codigo_brigada + '}';
    }

}
