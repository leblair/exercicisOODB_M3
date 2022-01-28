import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.config.EmbeddedConfiguration;
import com.db4o.query.Predicate;

import java.util.*;

public class Departament {
    public static void menu3(ObjectContainer db) {
        Departament_Dades departament;
        String email;
        String telefon;

        for (int i = 1; i < IKSRotarranConstants.DEPARTAMENTS.length; i++) {
            email = IKSRotarranConstants.DEPARTAMENTS[i] + "@IKSRotarran.ik";

            telefon = "";
            for (int j = 0; j < 9; j++) {
                telefon = telefon + String.valueOf(i);
            }

            departament = new Departament_Dades(i, IKSRotarranConstants.DEPARTAMENTS[i], email, telefon);

            db.store(departament);
        }
    }

    public static void menu4(ObjectContainer db) {
        Predicate p = new Predicate<Departament_Dades>() {
            @Override
            public boolean match(Departament_Dades d) {
                return true;
            }
        };

        ObjectSet<Departament_Dades> result = db.query(p);

        while (result.hasNext()) {
            Departament_Dades departamentTmp = result.next();

            Predicate p2 = new Predicate<Encarrec_Dades>() {
                @Override
                public boolean match(Encarrec_Dades e) {
                    return (e.getIdDepartamentQueElDemana() == departamentTmp.getId());
                }
            };

            ObjectSet<Encarrec_Dades> result2 = db.query(p2);

            while (result2.hasNext()) {
                Encarrec_Dades encarrecTmp = result2.next();

                departamentTmp.getLlistaEncarrecs().add(encarrecTmp);
            }

            db.store(departamentTmp);
        }

        Predicate p3 = new Predicate<Producte_Dades>() {
            @Override
            public boolean match(Producte_Dades p) {
                return true;
            }
        };

        List<Producte_Dades> result3 = db.query(p3);

        for (Producte_Dades producteTmp : result3) {
            Predicate p4 = new Predicate<Departament_Dades>() {
                @Override
                public boolean match(Departament_Dades d) {
                    if ((d.getId() == producteTmp.getIdDepartamentQueElPotDemanar()) || (producteTmp.getIdDepartamentQueElPotDemanar() == 0)){
                        return true;
                    } else {
                        return false;
                    }
                }
            };

            ObjectSet<Departament_Dades> result4 = db.query(p4);

            while (result4.hasNext()) {
                Departament_Dades departamentTmp = result4.next();

                Iterator<Encarrec_Dades> it = departamentTmp.getLlistaEncarrecs().iterator();

                while (it.hasNext()) {
                    Encarrec_Dades encarrecTmp = it.next();

                    encarrecTmp.getLlistaProductes().add(producteTmp);
                    db.store(encarrecTmp);
                }
            }
        }
    }

    public static void menu12(ObjectContainer db){
        try{
            Predicate predicate = new Predicate<Departament_Dades>() {
                @Override
                public boolean match(Departament_Dades o) {
                    return true;
                }
            };
            List<Departament_Dades> deps = db.query(predicate);
            System.out.println("DEPARTAMENTS:");
            for(Departament_Dades deparament: deps){
                System.out.println(deparament.getId() +". " + deparament.getNom()   );
            }
        }finally {

        }
    }
    public static void menu13(ObjectContainer db){
        try{
            Predicate predicate = new Predicate<Departament_Dades>() {
                @Override
                public boolean match(Departament_Dades o) {
                    return true;
                }
            };
            List<Departament_Dades> deps = db.query(predicate);
            for(Departament_Dades deparament: deps){
                String str ="";
                List<Encarrec_Dades> enca = deparament.getLlistaEncarrecs();
                for(Encarrec_Dades e: enca){
                    List<Producte_Dades> prods = e.getLlistaProductes();
                    for(Producte_Dades p: prods){
                        str += p.toString() + "\n";

                    }
                }
                        System.out.println(deparament.getNom().toUpperCase()+
                        " (id:" +deparament.getId() +
                        ", email:" + deparament.getEmail() +
                        ", telefon:" + deparament.getTelefon() +
                        ")\nEncarrecs:" + str);
            }
        }finally {

        }
    }

    public static void menu14(ObjectContainer db){
        Scanner sc = new Scanner(System.in);
        try{
            Predicate predicate = new Predicate<Producte_Dades>() {
                @Override
                public boolean match(Producte_Dades o) {
                    return true;
                }
            };
            List<Producte_Dades> prods = db.query(predicate);

            ArrayList<Producte_Dades> sortedList = new ArrayList<>();
            sortedList.addAll(prods);
            Collections.sort(sortedList);
            System.out.println("Opcio?:");
            System.out.println("Llista de productes:\n");
            int cont =0;
            for(Producte_Dades p: sortedList){
                System.out.println(cont+ ": " + p.getProducteNom());
                cont++;
            }
            boolean valid = false;
            int option=0;
            while (!valid){
                System.out.println("Selecciona el producte");
                option = sc.nextInt();
                if(option>sortedList.size() || option<0){
                    System.out.println("ERROR. HAS SELECCIONAT UN PRODUCTE QUE NO EXISTEIX");
                    sc.next();
                }else {
                    valid = true;
                }
            }
            Producte_Dades seleccionat = sortedList.get(option);
            Predicate pEncarrec = new Predicate<Encarrec_Dades>() {
                @Override
                public boolean match(Encarrec_Dades o) {
                    return o.getLlistaProductes().contains(seleccionat);
                }
            };
            List<Encarrec_Dades> encarrecs = db.query(pEncarrec);
            System.out.println("Trobat " + encarrecs.size() + " amb el producte " +
                    seleccionat.getProducteNom());
            Predicate pDep = new Predicate<Departament_Dades>() {
                @Override
                public boolean match(Departament_Dades o) {
                    for(Encarrec_Dades e: encarrecs){
                        return o.getLlistaEncarrecs().contains(e);
                    }
                    return false;
                }
            };
            ObjectSet<Departament_Dades> result3 = db.query(pDep);
            for(Encarrec_Dades en : encarrecs){
            if (result3.size() == 0) {
                System.out.println("ERROR: NO S'HA TROBAT CAP DEPARTAMENT QUE TINGUI ASSIGNAT L'ENCÀRREC AMB ID " + en.getIdEncarrec());
            } else {
                for (Departament_Dades departamentTmp : result3) {

                        System.out.println(departamentTmp.toStringSenzill(en));


                }
            }
            }


        }finally {

        }
    }

    public static void menu40(ObjectContainer db) {
        int departamentAEsborrar;


        departamentAEsborrar = 4;

        Predicate p = new Predicate<Departament_Dades>() {
            @Override
            public boolean match(Departament_Dades d) {
                return (d.getId() == departamentAEsborrar);
            }
        };

        ObjectSet<Departament_Dades> result = db.query(p);

        if (result.size() != 1) {
            System.out.println("No es pot eliminar aquest departament. N'hi ha més d'1 o 0 amb el mateix ID en la BD.");
        } else {
            Departament_Dades d = result.next();

            System.out.println("ELIMINAT EL DEPARTAMENT " + d.getNom());

            db.delete(d);
        }
    }

    public static ObjectContainer menu41(ObjectContainer db) {
        int departamentAEsborrar;

        db.close();

        EmbeddedConfiguration config = Db4oEmbedded.newConfiguration();
        config.common().objectClass(Departament_Dades.class).cascadeOnDelete(true);
        config.common().objectClass(Encarrec_Dades.class).cascadeOnDelete(true);
        config.common().objectClass(Producte_Dades.class).cascadeOnDelete(true);
        db = Db4oEmbedded.openFile(config, IKSRotarranConstants.pathBD);

        departamentAEsborrar = 1;

        Predicate p = new Predicate<Departament_Dades>() {
            @Override
            public boolean match(Departament_Dades d) {
                if (d.getId() == departamentAEsborrar) {
                    return true;
                } else {
                    return false;
                }
            }
        };

        ObjectSet<Departament_Dades> result = db.query(p);

        if (result.size() != 1) {
            System.out.println("No es pot eliminar aquest departament. N'hi ha més d'1 o 0 amb el mateix ID en la BD.");
        } else {
            Departament_Dades d = result.next();

            System.out.println("ELIMINAT EN CASCADA EL DEPARTAMENT " + d.getNom());

            db.delete(d);
        }

        db.close();
        config = Db4oEmbedded.newConfiguration();
        config.common().objectClass(Departament_Dades.class).cascadeOnUpdate(true);
        config.common().objectClass(Encarrec_Dades.class).cascadeOnUpdate(true);
        config.common().objectClass(Producte_Dades.class).cascadeOnUpdate(true);
        db = Db4oEmbedded.openFile(config, IKSRotarranConstants.pathBD);

        return db;
    }
}
