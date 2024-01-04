package lab1proyectofinal.entidades;

import java.time.LocalDateTime;
import lab1proyectofinal.accesoADatos.Utils;

/**
 *
 * @author Grupo-3
 */
public class Emergencia {

    public static final int PUNTUACION_MIN = 1;
    public static final int PUNTUACION_MAX = 10;

    private int codigoEmergencia = Utils.codigoNoEstablecido;
    private String tipo;
    private LocalDateTime fechaHoraInicio;
    private double coordenadaX;
    private double coordenadaY;
    private String detalles;
    private LocalDateTime fechaHoraResolucion;
    private float desempenio = Utils.desempenioNoEstablecida;
    private Brigada brigada;

    public Emergencia() {
    }

    public Emergencia(String tipo, LocalDateTime fechaHoraInicio, double coordenadaX, double coordenadaY, String detalles) {
        this.tipo = tipo;
        this.fechaHoraInicio = fechaHoraInicio;
        this.coordenadaX = coordenadaX;
        this.coordenadaY = coordenadaY;
        this.detalles = detalles;
        this.fechaHoraResolucion = null;
        this.desempenio = Utils.desempenioNoEstablecida;
        this.brigada = null;
    }

    public Emergencia(String tipo, LocalDateTime fechaHoraInicio, double coordenadaX, double coordenadaY, String detalles, Brigada brigada) {
        this.tipo = tipo;
        this.fechaHoraInicio = fechaHoraInicio;
        this.coordenadaX = coordenadaX;
        this.coordenadaY = coordenadaY;
        this.detalles = detalles;
        this.fechaHoraResolucion = null;
        this.desempenio = Utils.desempenioNoEstablecida;
        this.brigada = brigada;
    }

    public Emergencia(String tipo, LocalDateTime fechaHoraInicio, double coordenadaX, double coordenadaY, String detalles, Brigada brigada, LocalDateTime fechaHoraResolucion, float desempenio) {
        this.tipo = tipo;
        this.fechaHoraInicio = fechaHoraInicio;
        this.coordenadaX = coordenadaX;
        this.coordenadaY = coordenadaY;
        this.detalles = detalles;
        this.fechaHoraResolucion = fechaHoraResolucion;
        this.desempenio = desempenio;
        this.brigada = brigada;
    }

    public int getCodigoEmergencia() {
        return codigoEmergencia;
    }

    public void setCodigoEmergencia(int codigoEmergencia) {
        this.codigoEmergencia = codigoEmergencia;
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

    public double getCoordenadaX() {
        return coordenadaX;
    }

    public void setCoordenadaX(double coordenadaX) {
        this.coordenadaX = coordenadaX;
    }

    public double getCoordenadaY() {
        return coordenadaY;
    }

    public void setCoordenadaY(double coordenadaY) {
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

    public float getDesempenio() {
        return desempenio;
    }

    public void setDesempenio(float desempenio) {
        this.desempenio = desempenio;
    }

    public Brigada getBrigada() {
        return brigada;
    }

    public void setBrigada(Brigada brigada) {
        this.brigada = brigada;
    }

    public String debugToString() {
        return "Emergencia{" + "codigoEmergencia=" + codigoEmergencia + ", tipo=" + tipo + ", fechaHoraInicio=" + fechaHoraInicio + ", coordenadaX=" + coordenadaX + ", coordenadaY=" + coordenadaY + ", detalles=" + detalles + ", fechaHoraResolucion=" + fechaHoraResolucion + ", desempenio=" + desempenio + ", brigada=" + brigada + '}';
    }

    @Override
    public String toString() {
        return "Fecha: " + fechaHoraInicio + ", Coordenadas: (" + coordenadaX + ", " + coordenadaY + "), Tipo: " + tipo;
    }

}
