import com.db4o.ObjectContainer;

import java.time.LocalDateTime;
import java.util.List;

import com.db4o.query.Predicate;


public class Encarrec {
    public static void menu2(ObjectContainer db) {

        Encarrec_Dades[] encarrecs = {
                new Encarrec_Dades(0, 1, LocalDateTime.now()),
                new Encarrec_Dades(1, 1, LocalDateTime.now()),
                new Encarrec_Dades(2, 3, LocalDateTime.now()),
                new Encarrec_Dades(3, 1, LocalDateTime.now()),
                new Encarrec_Dades(4, 1, LocalDateTime.now()),
                new Encarrec_Dades(5, 4, LocalDateTime.now()),
                new Encarrec_Dades(6, 3, LocalDateTime.now())
        };

        for(int i = 0; i < encarrecs.length; i++) {
            db.store(encarrecs[i]);
        }
    }
    public static void menu11(ObjectContainer db){
        try {
            Predicate predicate = new Predicate<Encarrec_Dades>() {
                @Override
                public boolean match(Encarrec_Dades o) {
                    return true;
                }
            };
            List<Encarrec_Dades> encarrecs = db.query(predicate);
            for(Encarrec_Dades encarrec: encarrecs){
                System.out.println(encarrec.toString());
            }
        }finally {

        }
    }
}
