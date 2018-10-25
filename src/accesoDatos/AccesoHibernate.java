package accesoDatos;

import logicaRefrescos.Deposito;
import logicaRefrescos.Dispensador;
import org.hibernate.Query;
import org.hibernate.Session;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class AccesoHibernate implements I_Acceso_Datos {
    Session session;

    public AccesoHibernate() {

        HibernateUtil util = new HibernateUtil();

        session = util.getSessionFactory().openSession();

    }
    @Override
    public HashMap<Integer, Deposito> obtenerDepositos() {
        HashMap<Integer,Deposito>  hm_depositos = new HashMap<Integer,Deposito>();
        Deposito deposito;
        Query q = session.createQuery("select e from Deposito e");
        List results = q.list();

        Iterator depositosIterator = results.iterator();

        while (depositosIterator.hasNext()) {
             deposito = (Deposito) depositosIterator.next();
            hm_depositos.put(deposito.getValor(), deposito);

        }


        return hm_depositos;
    }

    @Override
    public HashMap<String, Dispensador> obtenerDispensadores() {
        HashMap<String,Dispensador>  hm_dispensadores = new HashMap<String, Dispensador>();
        Dispensador dispensador;
        Query q = session.createQuery("select e from Dispensador e");
        List results = q.list();

        Iterator dispensadoresIterator = results.iterator();

        while (dispensadoresIterator.hasNext()) {
            dispensador = (Dispensador) dispensadoresIterator.next();
            hm_dispensadores.put(dispensador.getClave(), dispensador);

        }


        return hm_dispensadores;
    }

    @Override
    public boolean guardarDepositos(HashMap<Integer, Deposito> depositos) {
        return false;
    }

    @Override
    public boolean guardarDispensadores(HashMap<String, Dispensador> dispensadores) {
        return false;
    }
}
