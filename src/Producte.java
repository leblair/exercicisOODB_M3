import com.db4o.ObjectContainer;

import com.db4o.ObjectSet;
import com.db4o.query.Predicate;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Producte {

    public static Producte_Dades producteDelXML;
    public static void menu1(ObjectContainer db){
        Producte_Dades p1 = new Producte_Dades(1,"Ració de combat",1,0 );
        Producte_Dades p2 = new Producte_Dades(2,"Torpede mark 2",100,2 );
        Producte_Dades p3 = new Producte_Dades(3,"Autopilot SAU-6",10,3 );
        Producte_Dades p4 = new Producte_Dades(4,"Sistema inercial MIS-P",11,3);
        Producte_Dades p5 = new Producte_Dades(5,"Bobina d'inducció magnèticadel reactor principal",10000,4 );
        Producte_Dades p6 = new Producte_Dades(6,"Sistema de navegació dellarg abast RSDN-10",12,3 );
        List<Producte_Dades> productes = new ArrayList<>();
        Collections.addAll(productes,p1,p2,p3,p4,p5,p6);
        for(int i=0;i<6;i++){
            db.store(productes.get(i));
        }

    }
    public static void menu10(ObjectContainer db){
        try{
            Predicate predicate = new Predicate<Producte_Dades>() {
                @Override
                public boolean match(Producte_Dades o) {
                    return true;
                }
            };
            List<Producte_Dades> prods = db.query(predicate);
            System.out.println("\nProductes en la BD:");
            for(Producte_Dades producte_dades: prods){
                System.out.println(producte_dades.toString());
            }
        }finally {

        }

    }
    public static void menu20(ObjectContainer db, String pathArxiusXML) {
        ArrayList<Producte_Dades> llistaOrdenadaProductes;
        XMLEncoder e = null;


        Predicate p = new Predicate<Producte_Dades>() {
            @Override
            public boolean match(Producte_Dades c) {
                return true;
            }
        };

        List<Producte_Dades> result = db.query(p);

        llistaOrdenadaProductes = new ArrayList<Producte_Dades>();
        llistaOrdenadaProductes.addAll(result);

        Collections.sort(llistaOrdenadaProductes);

        try {
            e = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(pathArxiusXML)));

            for (Producte_Dades producteTmp : llistaOrdenadaProductes) {
                e.writeObject(producteTmp);
            }
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } finally {
            e.close();
        }
    }
    public static void menu21(ObjectContainer db, String pathArxiusXML) {
        XMLDecoder d = null;

        try {
            d = new XMLDecoder(new BufferedInputStream(new FileInputStream(pathArxiusXML)));

            while ((producteDelXML = (Producte_Dades) d.readObject()) != null){
                System.out.println();

                Predicate p = new Predicate<Producte_Dades>() {
                    @Override
                    public boolean match(Producte_Dades c) {
                        return c.getProducteId() == producteDelXML.getProducteId();
                    }
                };

                ObjectSet<Producte_Dades> result = db.query(p);

                if (!result.isEmpty()) {

                    Producte_Dades producteTrobatEnLaBD = result.next();

                    System.out.println("menu21(): UPDATE");
                    System.out.println("producte ID: " + producteTrobatEnLaBD.getProducteId() + ", nom: " + producteTrobatEnLaBD.getProducteNom() + ", preu: " + producteTrobatEnLaBD.getProductePreu());
                    System.out.println("canviat a");
                    System.out.println("producte ID: " + producteDelXML.getProducteId() + ", nom: " + producteDelXML.getProducteNom() + ", preu: " + producteDelXML.getProductePreu());

                    producteTrobatEnLaBD.setProducteNom(producteDelXML.getProducteNom());
                    producteTrobatEnLaBD.setProductePreu(producteDelXML.getProductePreu() * 2);

                    db.store(producteTrobatEnLaBD);

                } else{
                    //No hem trobat en la BD un producte amb el mateix ID. L'insertem.

                    System.out.println("menu21(): INSERT");
                    System.out.println("producte ID: " + producteDelXML.getProducteId() + ", nom: " + producteDelXML.getProducteNom() + ", preu: " + producteDelXML.getProductePreu());

                    db.store(producteDelXML);
                }
            }
        } catch (ArrayIndexOutOfBoundsException ex) {
            System.out.println();
            System.out.println("NO HI HA MÉS PRODUCTES AL FITXER " + pathArxiusXML);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            d.close();
            System.out.println();
            System.out.println("TANQUEM EL FITXER " + pathArxiusXML);
        }

    }
}
