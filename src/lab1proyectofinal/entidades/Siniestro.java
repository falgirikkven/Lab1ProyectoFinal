package lab1proyectofinal.entidades;

import java.time.LocalDateTime;
import lab1proyectofinal.accesoADatos.Utils;

/**
 *
 * @author Grupo-3
 */
public class Siniestro {

    /**
     * SUJETO A CAMBIOS
     */
    public static final int PUNTUACION_MIN = 1;
    public static final int PUNTUACION_MAX = 10;

    private int codigoSiniestro = Utils.NIL;
    private String tipo;
    private LocalDateTime fechaSiniestro;
    private int coordenadaX;
    private int coordenadaY;
    private String detalles;
    private Brigada brigada;
    private LocalDateTime fechaResolucion;
    private int puntuacion;

    public Siniestro() {
    }

    public Siniestro(String tipo, LocalDateTime fechaSiniestro, int coordenadaX, int coordenadaY, String detalles) {
        this.tipo = tipo;
        this.fechaSiniestro = fechaSiniestro;
        this.coordenadaX = coordenadaX;
        this.coordenadaY = coordenadaY;
        this.detalles = detalles;
    }

    public Siniestro(String tipo, LocalDateTime fechaSiniestro, int coordenadaX, int coordenadaY, String detalles, Brigada brigada) {
        this.tipo = tipo;
        this.fechaSiniestro = fechaSiniestro;
        this.coordenadaX = coordenadaX;
        this.coordenadaY = coordenadaY;
        this.detalles = detalles;
        this.fechaResolucion = null;
        this.puntuacion = Utils.NIL;;
        this.brigada = brigada;
    }

    public Siniestro(String tipo, LocalDateTime fechaSiniestro, int coordenadaX, int coordenadaY, String detalles, Brigada brigada, LocalDateTime fechaResolucion, int puntuacion) {
        this.tipo = tipo;
        this.fechaSiniestro = fechaSiniestro;
        this.coordenadaX = coordenadaX;
        this.coordenadaY = coordenadaY;
        this.detalles = detalles;
        this.brigada = brigada;
        this.fechaResolucion = fechaResolucion;
        this.puntuacion = puntuacion;
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

    public LocalDateTime getFechaSiniestro() {
        return fechaSiniestro;
    }

    public void setFechaSiniestro(LocalDateTime fechaSiniestro) {
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

    public Brigada getBrigada() {
        return brigada;
    }

    public void setBrigada(Brigada brigada) {
        this.brigada = brigada;
    }

    public LocalDateTime getFechaResolucion() {
        return fechaResolucion;
    }

    public void setFechaResolucion(LocalDateTime fechaResolucion) {
        this.fechaResolucion = fechaResolucion;
    }

    public int getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(int puntuacion) {
        this.puntuacion = puntuacion;
    }

    public String DebugToString() {
        if (brigada == null) {
            return "Siniestro{" + "codigoSiniestro=" + codigoSiniestro + ", tipo=" + tipo + ", fechaSiniestro=" + fechaSiniestro + ", coordenadaX=" + coordenadaX + ", coordenadaY=" + coordenadaY + ", detalles=" + detalles + '}';
        } else if (fechaResolucion == null) {
            return "Siniestro{" + "codigoSiniestro=" + codigoSiniestro + ", tipo=" + tipo + ", fechaSiniestro=" + fechaSiniestro + ", coordenadaX=" + coordenadaX + ", coordenadaY=" + coordenadaY + ", detalles=" + detalles + ", codigoBrigada=" + Integer.toString(brigada.getCodigoBrigada()) + '}';
        }
        return "Siniestro{" + "codigoSiniestro=" + codigoSiniestro + ", tipo=" + tipo + ", fechaSiniestro=" + fechaSiniestro + ", coordenadaX=" + coordenadaX + ", coordenadaY=" + coordenadaY + ", detalles=" + detalles + ", codigoBrigada=" + Integer.toString(brigada.getCodigoBrigada()) + ", fechaResolucion=" + fechaResolucion + ", puntuacion=" + puntuacion + '}';
    }

}
