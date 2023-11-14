package lab1proyectofinal.entidades;

import java.util.Objects;
import lab1proyectofinal.accesoADatos.Utils;

/**
 *
 * @author Grupo-3
 */
public class Brigada {

    /**
     * SUJETO A CAMBIOS
     */
    private int codigoBrigada = Utils.NIL;
    private String nombreBrigada;
    private String especialidad;
    private boolean disponible;
    private Cuartel cuartel;
    private boolean estado = true;

    public Brigada() {
    }

    public Brigada(String nombreBrigada, String especialidad, boolean disponible, Cuartel cuartel) {
        this.nombreBrigada = nombreBrigada;
        this.especialidad = especialidad;
        this.disponible = disponible;
        this.cuartel = cuartel;
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

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
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

    public String DebugToString() {
        return "Brigada{" + "codigoBrigada=" + codigoBrigada + ", nombreBrigada=" + nombreBrigada + ", especialidad=" + especialidad + ", disponible=" + disponible + ", codigoCuartel=" + Integer.toString(cuartel.getCodigoCuartel()) + ", estado=" + estado + '}';
    }

    @Override
    public String toString() {
        return codigoBrigada + ". " + nombreBrigada;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + this.codigoBrigada;
        hash = 97 * hash + Objects.hashCode(this.nombreBrigada);
        hash = 97 * hash + Objects.hashCode(this.especialidad);
        hash = 97 * hash + (this.disponible ? 1 : 0);
        hash = 97 * hash + Objects.hashCode(this.cuartel);
        hash = 97 * hash + (this.estado ? 1 : 0);
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
        final Brigada other = (Brigada) obj;
        if (this.codigoBrigada != other.codigoBrigada) {
            return false;
        }
        if (this.disponible != other.disponible) {
            return false;
        }
        if (this.estado != other.estado) {
            return false;
        }
        if (!Objects.equals(this.nombreBrigada, other.nombreBrigada)) {
            return false;
        }
        if (!Objects.equals(this.especialidad, other.especialidad)) {
            return false;
        }
        return Objects.equals(this.cuartel, other.cuartel);
    }

}
