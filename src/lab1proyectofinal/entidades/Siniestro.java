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
    private int codigoSiniestro;
    private String tipo;
    private LocalDate fechaSiniestro;
    private int coordenadaX;
    private int coordenadaY;
    private String detalles;
    private LocalDate fechaResolucion;
    private int puntuacion;
    private int codigoBrigada;

    public Siniestro() {
    }

    public Siniestro(String tipo, LocalDate fechaSiniestro, int coordenadaX, int coordenadaY, String detalles) {
        this.codigoSiniestro = -1;
        this.tipo = tipo;
        this.fechaSiniestro = fechaSiniestro;
        this.coordenadaX = coordenadaX;
        this.coordenadaY = coordenadaY;
        this.detalles = detalles;
        this.fechaResolucion = null;
        this.puntuacion = -1;
        this.codigoBrigada = -1;
    }

    public Siniestro(int codigoSiniestro, String tipo, LocalDate fechaSiniestro, int coordenadaX, int coordenadaY, String detalles) {
        this.codigoSiniestro = codigoSiniestro;
        this.tipo = tipo;
        this.fechaSiniestro = fechaSiniestro;
        this.coordenadaX = coordenadaX;
        this.coordenadaY = coordenadaY;
        this.detalles = detalles;
        this.fechaResolucion = null;
        this.puntuacion = -1;
        this.codigoBrigada = -1;
    }

    public Siniestro(int codigoSiniestro, String tipo, LocalDate fechaSiniestro, int coordenadaX, int coordenadaY, String detalles, int codigoBrigada) {
        this.codigoSiniestro = codigoSiniestro;
        this.tipo = tipo;
        this.fechaSiniestro = fechaSiniestro;
        this.coordenadaX = coordenadaX;
        this.coordenadaY = coordenadaY;
        this.detalles = detalles;
        this.fechaResolucion = null;
        this.puntuacion = -1;
        this.codigoBrigada = codigoBrigada;
    }

    public Siniestro(int codigoSiniestro, String tipo, LocalDate fechaSiniestro, int coordenadaX, int coordenadaY, String detalles, LocalDate fechaResolucion, int puntuacion, int codigoBrigada) {
        this.codigoSiniestro = codigoSiniestro;
        this.tipo = tipo;
        this.fechaSiniestro = fechaSiniestro;
        this.coordenadaX = coordenadaX;
        this.coordenadaY = coordenadaY;
        this.detalles = detalles;
        this.fechaResolucion = fechaResolucion;
        this.puntuacion = puntuacion;
        this.codigoBrigada = codigoBrigada;
    }

    public int getCodigoSiniestro() {
        return codigoSiniestro;
    }

    public void setCodigoSiniestro(int codigoSiniestro) {
        this.codigoSiniestro = codigoSiniestro;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public LocalDate getFechaSiniestro() {
        return fechaSiniestro;
    }

    public void setFechaSiniestro(LocalDate fechaSiniestro) {
        this.fechaSiniestro = fechaSiniestro;
    }

    public int getCoordenadaX() {
        return coordenadaX;
    }

    public void setCoordenadaX(int coordenadaX) {
        this.coordenadaX = coordenadaX;
    }

    public int getCoordenadaY() {
        return coordenadaY;
    }

    public void setCoordenadaY(int coordenadaY) {
        this.coordenadaY = coordenadaY;
    }

    public String getDetalles() {
        return detalles;
    }

    public void setDetalles(String detalles) {
        this.detalles = detalles;
    }

    public LocalDate getFechaResolucion() {
        return fechaResolucion;
    }

    public void setFechaResolucion(LocalDate fechaResolucion) {
        this.fechaResolucion = fechaResolucion;
    }

    public int getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(int puntuacion) {
        this.puntuacion = puntuacion;
    }

    public int getCodigoBrigada() {
        return codigoBrigada;
    }

    public void setCodigoBrigada(int codigoBrigada) {
        this.codigoBrigada = codigoBrigada;
    }

    @Override
    public String toString() {
        return "Siniestro{" + "codigoSiniestro=" + codigoSiniestro + ", tipo=" + tipo + ", fechaSiniestro=" + fechaSiniestro + ", coordenadaX=" + coordenadaX + ", coordenadaY=" + coordenadaY + ", detalles=" + detalles + ", fechaResolucion=" + fechaResolucion + ", puntuacion=" + puntuacion + ", codigoBrigada=" + codigoBrigada + '}';
    }

}
