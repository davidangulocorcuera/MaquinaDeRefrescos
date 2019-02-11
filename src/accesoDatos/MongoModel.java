package accesoDatos;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import logicaRefrescos.Deposito;
import logicaRefrescos.Dispensador;
import org.bson.Document;

import java.util.HashMap;

public class MongoModel implements I_Acceso_Datos {
    private MongoDatabase db;
    public MongoModel() {
        try {
            MongoClient mongoClient = new MongoClient("localhost", 27017);
            db = mongoClient.getDatabase("maquina_refrescos");
            System.out.println("connected");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    @Override
    public HashMap<Integer, Deposito> obtenerDepositos() {
        HashMap<Integer, Deposito> depositos = new HashMap<>();
        Deposito deposito = new Deposito();
        MongoCollection<Document> colection = db.getCollection("depositos");
        for (Document document : colection.find()) {
            deposito = new Deposito();
            deposito.setId(document.getInteger("id"));
            deposito.setNombreMoneda(document.getString("nombre"));
            deposito.setValor(document.getInteger("cantidad"));
            deposito.setValor(document.getInteger("valor"));

            depositos.put(deposito.getValor(), deposito);
        }
        return depositos;
    }

    @Override
    public HashMap<String, Dispensador> obtenerDispensadores() {
        HashMap<String, Dispensador> dispensadores = new HashMap<>();
        Dispensador dispensador = new Dispensador();
        MongoCollection<Document> colection = db.getCollection("dispensadores");
        for (Document document : colection.find()) {
            dispensador = new Dispensador();
            dispensador.setId(document.getInteger("id"));
            dispensador.setCantidad(document.getInteger("cantidad"));
            dispensador.setClave(document.getString("clave"));
            dispensador.setNombreProducto(document.getString("nombre"));
            dispensador.setPrecio(document.getInteger("precio"));

            dispensadores.put(dispensador.getClave(), dispensador);
        }
        return dispensadores;
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
