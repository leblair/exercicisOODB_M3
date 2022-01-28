import java.util.LinkedList;
import java.util.List;

public class Departament_Dades {
    private int id;
    private String nom;
    private String email;
    private String telefon;
    private List<Encarrec_Dades> llistaEncarrecs = new LinkedList<Encarrec_Dades>();

    public Departament_Dades(int id, String nom, String email, String telefon) {
        this.id = id;
        this.nom = nom;
        this.email = email;
        this.telefon = telefon;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public List<Encarrec_Dades> getLlistaEncarrecs() {
        return llistaEncarrecs;
    }

    public void setLlistaEncarrecs(List<Encarrec_Dades> llistaEncarrecs) {
        this.llistaEncarrecs = llistaEncarrecs;
    }

    @Override
    public String toString() {
        String encarrecs = "";
        for(Encarrec_Dades encarrec: this.llistaEncarrecs){
            encarrecs += encarrec.toString() + "\n";
        }
        String str ="";
        str += "Nom: " + this.nom + "\nId: " + this.id + "\nEmail: " + this.email
        + "\nTelefon: " + this.telefon +
        "Encarrecs: \n" + encarrecs;
        return str;
    }

    public String toStringSenzill(Encarrec_Dades encarrecTrobat){
        String str ="";
        str += "Nom: " + this.nom + "\nId: " + this.id + "\nEmail: " + this.email
                + "\nTelefon: " + this.telefon +
                "Encarrec: \n" + encarrecTrobat.toString();
        return str;
    }
}
