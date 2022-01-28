import java.time.LocalDateTime;
import java.util.ArrayList;

public class Encarrec_Dades {
    private int idEncarrec;
    private int idDepartamentQueElDemana;
    private ArrayList<Producte_Dades> llistaProductes = new ArrayList<Producte_Dades>();
    private LocalDateTime data;

    public Encarrec_Dades(int idEncarrec, int idDepartamentQueElDemana, LocalDateTime data) {
        this.idEncarrec = idEncarrec;
        this.idDepartamentQueElDemana = idDepartamentQueElDemana;
        this.data = data;
    }

    public int getIdEncarrec() {
        return idEncarrec;
    }

    public void setIdEncarrec(int idEncarrec) {
        this.idEncarrec = idEncarrec;
    }

    public int getIdDepartamentQueElDemana() {
        return idDepartamentQueElDemana;
    }

    public void setIdDepartamentQueElDemana(int idDepartamentQueElDemana) {
        this.idDepartamentQueElDemana = idDepartamentQueElDemana;
    }

    public ArrayList<Producte_Dades> getLlistaProductes() {
        return llistaProductes;
    }

    public void setLlistaProductes(ArrayList<Producte_Dades> llistaProductes) {
        this.llistaProductes = llistaProductes;
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }

    @Override
    public String toString() {
        String str ="";
        for(Producte_Dades producte: this.llistaProductes){
            str +=producte.toString() + "\n";
        }
        return "Encarrec_Dades{" +
                "idEncarrec=" + idEncarrec +
                ", idDepartamentQueElDemana=" + idDepartamentQueElDemana +
                ", llistaProductes=\n" + str +
                ", data=" + data +
                '}';
    }
}
