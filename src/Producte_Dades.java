import java.text.Collator;
import java.util.Locale;

public class Producte_Dades implements Comparable<Producte_Dades>{
    private int producteId;
    private String producteNom;
    private int productePreu;// La moneda de l'Imperi Klingon és el darkset.
    private int idDepartamentQueElPotDemanar;

    public Producte_Dades() {
    }

    public Producte_Dades(int producteId, String producteNom, int productePreu, int idDepartamentQueElPotDemanar) {
        this.producteId = producteId;
        this.producteNom = producteNom;
        this.productePreu = productePreu;
        this.idDepartamentQueElPotDemanar = idDepartamentQueElPotDemanar;
    }

    public int getProducteId() {
        return producteId;
    }

    public void setProducteId(int producteId) {
        this.producteId = producteId;
    }

    public String getProducteNom() {
        return producteNom;
    }

    public void setProducteNom(String producteNom) {
        this.producteNom = producteNom;
    }

    public int getProductePreu() {
        return productePreu;
    }

    public void setProductePreu(int productePreu) {
        this.productePreu = productePreu;
    }

    public int getIdDepartamentQueElPotDemanar() {
        return idDepartamentQueElPotDemanar;
    }

    public void setIdDepartamentQueElPotDemanar(int idDepartamentQueElPotDemanar) {
        this.idDepartamentQueElPotDemanar = idDepartamentQueElPotDemanar;
    }

    @Override
    public String toString() {
        return "Producte_Dades{" +
                "producteId=" + producteId +
                ", producteNom='" + producteNom + '\'' +
                ", productePreu=" + productePreu +
                '}';
    }

    @Override
    public int compareTo(Producte_Dades o) {
        Collator comparador = Collator.getInstance(new Locale("es"));
        comparador.setStrength(Collator.PRIMARY);

        return comparador.compare(this.getProducteNom(), o.getProducteNom());
    }
}
