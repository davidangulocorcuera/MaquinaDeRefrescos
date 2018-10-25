package accesoDatos;

import logicaRefrescos.Deposito;
import logicaRefrescos.Dispensador;

import java.util.HashMap;

public class AccesoHibernate implements I_Acceso_Datos {
    @Override
    public HashMap<Integer, Deposito> obtenerDepositos() {
        return null;
    }

    @Override
    public HashMap<String, Dispensador> obtenerDispensadores() {
        return null;
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
