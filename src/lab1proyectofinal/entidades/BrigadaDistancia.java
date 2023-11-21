package lab1proyectofinal.entidades;

/**
 *
 * @author Grupo-3
 */
public class BrigadaDistancia implements Comparable<BrigadaDistancia> {

    private Brigada brigada;
    private double distancia; // Distancia Manhattan en 'unidades de distancia' [UD]

    public BrigadaDistancia(Brigada brigada, double distancia) {
        this.brigada = brigada;
        this.distancia = distancia;
    }

    public Brigada getBrigada() {
        return brigada;
    }

    public void setBrigada(Brigada brigada) {
        this.brigada = brigada;
    }

    public double getDistancia() {
        return distancia;
    }

    public void setDistancia(double distancia) {
        this.distancia = distancia;
    }

    @Override
    public String toString() {
        return String.format("%.2f", distancia) + "UD - " + brigada.getNombreBrigada() + " (" + brigada.getEspecialidad() + ")";
    }

    @Override
    public int compareTo(BrigadaDistancia bd) {
        double d1 = this.distancia;
        double d2 = bd.getDistancia();

        if (d1 > d2) {
            return 1;
        } else if (d2 > d1) {
            return -1;
        }

        return 0;
    }

}
