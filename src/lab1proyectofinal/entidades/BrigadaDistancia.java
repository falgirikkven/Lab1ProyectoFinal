package lab1proyectofinal.entidades;

/**
 *
 * @author Grupo-3
 */
public class BrigadaDistancia implements Comparable<BrigadaDistancia> {

    private Brigada brigada;
    private int distancia; // Distancia Manhattan en 'unidades de distancia' [ud]

    public BrigadaDistancia(Brigada brigada, int distancia) {
        this.brigada = brigada;
        this.distancia = distancia;
    }

    public Brigada getBrigada() {
        return brigada;
    }

    public void setBrigada(Brigada brigada) {
        this.brigada = brigada;
    }

    public int getDistancia() {
        return distancia;
    }

    public void setDistancia(int distancia) {
        this.distancia = distancia;
    }

    @Override
    public String toString() {
        return distancia + "ud - " + brigada.getNombreBrigada() + " (" + brigada.getEspecialidad() + ")";
    }

    @Override
    public int compareTo(BrigadaDistancia bd) {
        return this.distancia - bd.getDistancia();
    }

}
