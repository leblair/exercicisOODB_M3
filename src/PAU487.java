
import java.io.IOException;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Scanner;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.config.EmbeddedConfiguration;


/**
 *
 * @author gmartinez
 */
public class PAU487 {
    /*
    SISTEMA DE NAVEGACIÓ BASAT EN WAYPOINTS.
    ES DONEN D'ALTA DIVERSOS WAYPOINTS DE L'ESPAI (ORBITA MARCIANA, PUNT LAGRANGE TERRA-LLUNA, PHOBOS, SATURN, LLUNA,...).
    ES PODEN MEMORITZAR DIVERSES RUTES AFEGINT DIVERSOS WAYPOINTS A CADA RUTA.
    
    */
    
    public static void bloquejarPantalla() {
        Scanner in = new Scanner(System.in);
        System.out.print("\nToca 'C' per a continuar ");
        while (in.hasNext()) {
            if ("C".equalsIgnoreCase(in.next())) break;
        }
    }
    
    
    public static void menuPAU487() throws IOException  {
    	// Aquesta configuració és perquè gravi en cascada quan fem un update de qualsevol objecte del nostre
    	// programa (<Client_Dades>, <Encarrec_Dades> o <Producte_Dades>) que contingui altres objectes.
    	EmbeddedConfiguration config = Db4oEmbedded.newConfiguration();
        config.common().objectClass(Departament_Dades.class).cascadeOnUpdate(true);		// D'aquesta manera s'updateja els objectes <Encarrec_Dades> que hi ha a dins.
        config.common().objectClass(Encarrec_Dades.class).cascadeOnUpdate(true);	// D'aquesta manera s'updateja els objectes <Producte_Dades> que hi ha a dins.
        config.common().objectClass(Producte_Dades.class).cascadeOnUpdate(true);	// No hauria de fer falta.
        ObjectContainer db = Db4oEmbedded.openFile(config, IKSRotarranConstants.pathBD);
    	
        String opcio;
        Scanner sc = new Scanner(System.in);
        StringBuilder menu = new StringBuilder("");

        
        do {
        	menu.delete(0, menu.length());
            
            menu.append(System.getProperty("line.separator"));
            menu.append("PAU-487 ");
            menu.append(System.getProperty("line.separator"));
            menu.append(System.getProperty("line.separator"));
            menu.append("1. Crea productes i els fica en la BD per inicialitzar-la");
            menu.append(System.getProperty("line.separator"));
            menu.append("2. Crea encarrecs i els fica en la BD per inicialitzar-la"); 
            menu.append(System.getProperty("line.separator"));
            menu.append("3. Crea els departaments, a partir dels que hi ha en IKSRotarranConstants.DEPARTAMENTS, i els fica en la BD");
            menu.append(System.getProperty("line.separator"));
            menu.append("4. Assigna els encàrrecs als departaments i els productes als encàrrecs");
            menu.append(System.getProperty("line.separator"));
            menu.append(System.getProperty("line.separator"));
            menu.append("10. Veure tots els productes que hi ha en la BD");
            menu.append(System.getProperty("line.separator"));
            menu.append("11. Veure tots els encàrrecs que hi ha en la BD");
            menu.append(System.getProperty("line.separator"));
            menu.append("12. Veure tots els departaments que hi ha en la BD");
            menu.append(System.getProperty("line.separator"));
            menu.append("13. Veure totes les dades de tots els departaments que hi ha en la BD");
            menu.append(System.getProperty("line.separator"));
            menu.append("14. Veure els departaments que han comprat 1 producte concret");
            menu.append(System.getProperty("line.separator"));
            menu.append(System.getProperty("line.separator"));
            menu.append("20. Exporta els productes a un XML");
            menu.append(System.getProperty("line.separator"));
            menu.append("21. Importa en la BD els productes que hi ha en el XML");
            menu.append(System.getProperty("line.separator"));
            menu.append(System.getProperty("line.separator"));
            menu.append("40. Esborrat del \"departament d'Enginyeria\"");
            menu.append(System.getProperty("line.separator"));
            menu.append("41. Esborrat en cascada del \"departament Comandament\"");
            menu.append(System.getProperty("line.separator"));
            menu.append(System.getProperty("line.separator"));
            menu.append(System.getProperty("line.separator"));
            menu.append("50. Tancar el sistema");
            menu.append(System.getProperty("line.separator"));
            
            System.out.print(MenuConstructorPantalla.constructorPantalla(menu));
            
            opcio = sc.next();
            
            switch (opcio) {
                case "1":
                	Producte.menu1(db);
                    bloquejarPantalla();
                    break;
                case "2":
                	Encarrec.menu2(db);
                    bloquejarPantalla();
                    break;
                case "3":
                	Departament.menu3(db);
                    bloquejarPantalla();
                    break;
                case "4":
                	Departament.menu4(db);
                    bloquejarPantalla();
                    break;
                case "10":
                	Producte.menu10(db);
                	bloquejarPantalla();
                    break;
                case "11":
                	Encarrec.menu11(db);
                    bloquejarPantalla();
                    break;
                case "12":
                	Departament.menu12(db);
                    bloquejarPantalla();
                    break;
                case "13":
                	Departament.menu13(db);
                    bloquejarPantalla();
                    break;
                case "14":
                	Departament.menu14(db);
                    bloquejarPantalla();
                    break;
                case "20":
                	Producte.menu20(db, IKSRotarranConstants.pathArxiusExportacioXML);
                    bloquejarPantalla();
                    break;
                case "21":
                	Producte.menu21(db, IKSRotarranConstants.pathArxiusImportacioXML);
                    bloquejarPantalla();
                    break;
                case "40":
                	Departament.menu40(db);
                    bloquejarPantalla();
                    break;
                case "41":
                	db = Departament.menu41(db);
                    bloquejarPantalla();
                    break; 
                case "50":
                	db.close();
                    break; 
                default:
                    System.out.println("COMANDA NO RECONEGUDA");
            }  
        } while (!opcio.equals("50"));
    }
    
}
