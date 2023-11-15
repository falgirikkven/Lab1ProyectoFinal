package lab1proyectofinal.entidades;

import java.time.LocalDateTime;
import java.util.Objects;
import lab1proyectofinal.accesoADatos.Utils;

/**
 *
 * @author Grupo-3
 */
public class Siniestro {

    public static final int PUNTUACION_MIN = 1;
    public static final int PUNTUACION_MAX = 10;

    private int codigoSiniestro = Utils.NIL;
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
        this.puntuacion = Utils.NIL;
        this.brigada = null;
    }

    // usuario inicializa todo menos 'codigoSiniestro', 'fechaHoraResolucion' y 'puntuacion'
    public Siniestro(String tipo, LocalDateTime fechaHoraInicio, int coordenadaX, int coordenadaY, String detalles, Brigada brigada) {
        this.tipo = tipo;
        this.fechaHoraInicio = fechaHoraInicio;
        this.coordenadaX = coordenadaX;
        this.coordenadaY = coordenadaY;
        this.detalles = detalles;
        this.fechaHoraResolucion = null;
        this.puntuacion = Utils.NIL;
        this.brigada = brigada;
    }

    // usuario inicializa todo menos 'codigoSiniestro'
    public Siniestro(String tipo, LocalDateTime fechaHoraInicio, int coordenadaX, int coordenadaY, String detalles, Brigada brigada, LocalDateTime fechaHoraResolucion, int puntuacion) {
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

    public String debugToString() {
        return "Siniestro{" + "codigoSiniestro=" + codigoSiniestro + ", tipo=" + tipo + ", fechaHoraInicio=" + fechaHoraInicio + ", coordenadaX=" + coordenadaX + ", coordenadaY=" + coordenadaY + ", detalles=" + detalles + ", fechaHoraResolucion=" + fechaHoraResolucion + ", puntuacion=" + puntuacion + ", brigada=" + brigada + '}';
    }

    @Override
    public String toString() {
        return "Siniestro{" + "codigoSiniestro=" + codigoSiniestro + ", tipo=" + tipo + ", fechaHoraInicio=" + fechaHoraInicio + ", coordenadaX=" + coordenadaX + ", coordenadaY=" + coordenadaY + ", detalles=" + detalles + ", fechaHoraResolucion=" + fechaHoraResolucion + ", puntuacion=" + puntuacion + ", brigada=" + brigada + '}';
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + this.codigoSiniestro;
        hash = 89 * hash + Objects.hashCode(this.tipo);
        hash = 89 * hash + Objects.hashCode(this.fechaHoraInicio);
        hash = 89 * hash + this.coordenadaX;
        hash = 89 * hash + this.coordenadaY;
        hash = 89 * hash + Objects.hashCode(this.detalles);
        hash = 89 * hash + Objects.hashCode(this.fechaHoraResolucion);
        hash = 89 * hash + this.puntuacion;
        hash = 89 * hash + Objects.hashCode(this.brigada);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Siniestro other = (Siniestro) obj;
        if (this.codigoSiniestro != other.codigoSiniestro) {
            return false;
        }
        if (this.coordenadaX != other.coordenadaX) {
            return false;
        }
        if (this.coordenadaY != other.coordenadaY) {
            return false;
        }
        if (this.puntuacion != other.puntuacion) {
            return false;
        }
        if (!Objects.equals(this.tipo, other.tipo)) {
            return false;
        }
        if (!Objects.equals(this.detalles, other.detalles)) {
            return false;
        }
        if (!Objects.equals(this.fechaHoraInicio, other.fechaHoraInicio)) {
            return false;
        }
        if (!Objects.equals(this.fechaHoraResolucion, other.fechaHoraResolucion)) {
            return false;
        }
        return Objects.equals(this.brigada, other.brigada);
    }

}
