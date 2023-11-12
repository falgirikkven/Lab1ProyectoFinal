package lab1proyectofinal.entidades;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lab1proyectofinal.accesoADatos.BrigadaData;


/**
 *
 * @author Grupo-3
 */
public class Siniestro {

    /**
     * SUJETO A CAMBIOS
     */
    public static final int PUNTUACION_NIL = -1;
    public static final int PUNTUACION_MIN = 1;
    public static final int PUNTUACION_MAX = 10;    

    private int codigoSiniestro=-1;
    private String tipo;
    private LocalDateTime fechaHoraInicio;
    private int coordenadaX;
    private int coordenadaY;
    private String detalles;
    private LocalDateTime fechaHoraResolucion;
    private int puntuacion;
    private Brigada brigada;

    public Siniestro() {
    }

    // usuario inicializa todo menos 'codigoSiniestro', 'fechaHoraResolucion', 'puntuacion' y 'brigada'
    public Siniestro(String tipo, LocalDateTime fechaHoraInicio, int coordenadaX, int coordenadaY, String detalles) {
        this.tipo = tipo;
        this.fechaHoraInicio = fechaHoraInicio;
        this.coordenadaX = coordenadaX;
        this.coordenadaY = coordenadaY;
        this.detalles = detalles;
        this.fechaHoraResolucion = null;
        this.puntuacion = PUNTUACION_NIL;
        this.brigada = BrigadaData.brigadaNull;
    }

    // usuario inicializa todo menos 'codigoSiniestro', 'fechaHoraResolucion' y 'puntuacion'
    public Siniestro(String tipo, LocalDateTime fechaHoraInicio, int coordenadaX, int coordenadaY, String detalles, Brigada brigada) {
        this.tipo = tipo;
        this.fechaHoraInicio = fechaHoraInicio;
        this.coordenadaX = coordenadaX;
        this.coordenadaY = coordenadaY;
        this.detalles = detalles;
        this.fechaHoraResolucion = null;
        this.puntuacion = PUNTUACION_NIL;
        this.brigada = brigada;
    }
    
    // usuario inicializa todo menos 'codigoSiniestro' y 'brigada' (en principio, para registrar emergencias que no se registraron anteriormente por cualquier motivo y que fueron tratados por una brigada que actualmente se encuentra tratando otra emergencia)
    public Siniestro(String tipo, LocalDateTime fechaHoraInicio, int coordenadaX, int coordenadaY, String detalles, LocalDateTime fechaHoraResolucion, int puntuacion) {
        this.tipo = tipo;
        this.fechaHoraInicio = fechaHoraInicio;
        this.coordenadaX = coordenadaX;
        this.coordenadaY = coordenadaY;
        this.detalles = detalles;
        this.fechaHoraResolucion = fechaHoraResolucion;
        this.puntuacion = puntuacion;
        this.brigada = BrigadaData.brigadaNull;
    }

    // usuario inicializa todo menos 'codigoSiniestro'
    public Siniestro(String tipo, LocalDateTime fechaHoraInicio, int coordenadaX, int coordenadaY, String detalles, LocalDateTime fechaHoraResolucion, int puntuacion, Brigada brigada) {
        this.tipo = tipo;
        this.fechaHoraInicio = fechaHoraInicio;
        this.coordenadaX = coordenadaX;
        this.coordenadaY = coordenadaY;
        this.detalles = detalles;
        this.fechaHoraResolucion = fechaHoraResolucion;
        this.puntuacion = puntuacion;
        this.brigada = brigada;
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

    public LocalDateTime getFechaHoraInicio() {
        return fechaHoraInicio;
    }

    public void setFechaHoraInicio(LocalDateTime fechaHoraInicio) {
        this.fechaHoraInicio = fechaHoraInicio;
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

    public LocalDateTime getFechaHoraResolucion() {
        return fechaHoraResolucion;
    }

    public void setFechaHoraResolucion(LocalDateTime fechaHoraResolucion) {
        this.fechaHoraResolucion = fechaHoraResolucion;
    }

    public int getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(int puntuacion) {
        this.puntuacion = puntuacion;
    }

    public Brigada getBrigada() {
        return brigada;
    }

    public void setBrigada(Brigada brigada) {
        this.brigada = brigada;
    }

    @Override
    public String toString() {
        return "Siniestro{" + "codigoSiniestro=" + codigoSiniestro + ", tipo=" + tipo + ", fechaHoraInicio=" + fechaHoraInicio + ", coordenadaX=" + coordenadaX + ", coordenadaY=" + coordenadaY + ", detalles=" + detalles + ", fechaHoraResolucion=" + fechaHoraResolucion + ", puntuacion=" + puntuacion + ", brigada=" + brigada + '}';
    }

    public String debugToString() {
        return "Siniestro{" + "codigoSiniestro=" + codigoSiniestro + ", tipo=" + tipo + ", fechaHoraInicio=" + fechaHoraInicio + ", coordenadaX=" + coordenadaX + ", coordenadaY=" + coordenadaY + ", detalles=" + detalles + ", fechaHoraResolucion=" + fechaHoraResolucion + ", puntuacion=" + puntuacion + ", brigada=" + brigada + '}';
    }
}
