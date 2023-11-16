package lab1proyectofinal.entidades;

import java.util.Objects;
import lab1proyectofinal.accesoADatos.Utils;

/**
 *
 * @author Grupo-3
 */
public class Cuartel {

    private int codigoCuartel = Utils.NIL;
    private String nombreCuartel;
    private String direccion;
    private int coordenadaX;
    private int coordenadaY;
    private String telefono;
    private String correo;
    private boolean estado = true;

    public Cuartel() {
    }

    public Cuartel(String nombreCuartel, String direccion, int coordenadaX, int coordenadaY, String telefono, String correo) {
        this.nombreCuartel = nombreCuartel;
        this.direccion = direccion;
        this.coordenadaX = coordenadaX;
        this.coordenadaY = coordenadaY;
        this.telefono = telefono;
        this.correo = correo;
    }

    public int getCodigoCuartel() {
        return codigoCuartel;
    }

    public void setCodigoCuartel(int codigoCuartel) {
        this.codigoCuartel = codigoCuartel;
    }

    public String getNombreCuartel() {
        return nombreCuartel;
    }

    public void setNombreCuartel(String nombreCuartel) {
        this.nombreCuartel = nombreCuartel;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
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

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public String debugToString() {
        return "Cuartel{" + "codigoCuartel=" + codigoCuartel + ", nombreCuartel=" + nombreCuartel + ", direccion=" + direccion + ", coordenadaX=" + coordenadaX + ", coordenadaY=" + coordenadaY + ", telefono=" + telefono + ", correo=" + correo + ", estado=" + estado + '}';
    }

    @Override
    public String toString() {
        return nombreCuartel + " (codigoCuartel=" + codigoCuartel + ")";
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 61 * hash + this.codigoCuartel;
        hash = 61 * hash + Objects.hashCode(this.nombreCuartel);
        hash = 61 * hash + Objects.hashCode(this.direccion);
        hash = 61 * hash + this.coordenadaX;
        hash = 61 * hash + this.coordenadaY;
        hash = 61 * hash + Objects.hashCode(this.telefono);
        hash = 61 * hash + Objects.hashCode(this.correo);
        hash = 61 * hash + (this.estado ? 1 : 0);
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
        final Cuartel other = (Cuartel) obj;
        if (this.codigoCuartel != other.codigoCuartel) {
            return false;
        }
        if (this.coordenadaX != other.coordenadaX) {
            return false;
        }
        if (this.coordenadaY != other.coordenadaY) {
            return false;
        }
        if (this.estado != other.estado) {
            return false;
        }
        if (!Objects.equals(this.nombreCuartel, other.nombreCuartel)) {
            return false;
        }
        if (!Objects.equals(this.direccion, other.direccion)) {
            return false;
        }
        if (!Objects.equals(this.telefono, other.telefono)) {
            return false;
        }
        return Objects.equals(this.correo, other.correo);
    }

}
