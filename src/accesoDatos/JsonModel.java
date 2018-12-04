package accesoDatos;

import auxiliares.ApiRequests;
import logicaRefrescos.Deposito;
import logicaRefrescos.Dispensador;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.util.HashMap;
import java.util.Map;

public class JsonModel implements I_Acceso_Datos {
    private Deposito newDeposito;
    private Dispensador newDispensador;
    Dispensador Dispensador;
    ApiRequests encargadoPeticiones;
    private String SERVER_PATH, GET_DEPOSITO, GET_DISPENSADOR, SET_DEPOSITO, SET_DISPENSADOR; // Datos de la conexion

    public JsonModel() {
        char c;

        encargadoPeticiones = new ApiRequests();

        SERVER_PATH = "http://localhost/DavidAngulo/MaquinadeRefrescosCrud/";
        GET_DEPOSITO = "readDeposito.php";
        GET_DISPENSADOR = "readDispensador.php";
        SET_DEPOSITO = "writeDeposito.php";
        SET_DISPENSADOR = "writeDispensador.php";

    }
    @Override
    public HashMap<Integer, Deposito> obtenerDepositos() {
        HashMap<Integer, Deposito> hm_depositos = new HashMap<>();
        try {

            String url = SERVER_PATH + GET_DEPOSITO; // Sacadas de configuracion
            String response = encargadoPeticiones.getRequest(url);

            // Parseamos la respuesta y la convertimos en un JSONObject
            JSONObject respuesta = (JSONObject) JSONValue.parse(response);
            System.out.print(url);
            // El JSON recibido es correcto
            System.out.print(respuesta);
            String estado = (String) respuesta.get("estado");
            // Si ok, obtenemos array de jugadores para recorrer y generar hashmap
            if (estado.equals("ok")) {
                JSONArray array = (JSONArray) respuesta.get("Depositos");
                // dispensadores clave string
                // depositos valor
                if (array.size() > 0) {
                    for (int i = 0; i < array.size(); i++) {
                        JSONObject row = (JSONObject) array.get(i);

                        int id = Integer.parseInt(row.get("id").toString());
                        String nombre = row.get("nombre").toString();
                        int valor = Integer.parseInt(row.get("valor").toString());
                        int cantidad = Integer.parseInt(row.get("cantidad").toString());


                        newDeposito = new Deposito(nombre, valor, cantidad);


                        hm_depositos.put(valor, newDeposito);
                    }

                }

            }


        } catch (Exception e) {
            System.out.println("Ha ocurrido un error en la busqueda de datos");

            e.printStackTrace();

            System.exit(-1);
        }

        return hm_depositos;
    }

    @Override
    public HashMap<String, Dispensador> obtenerDispensadores() {
        HashMap<String, Dispensador> hm_dispensadores = new HashMap<>();
        try {

            String url = SERVER_PATH + GET_DISPENSADOR; // Sacadas de configuracion
            String response = encargadoPeticiones.getRequest(url);
            System.out.print(response);
            // Parseamos la respuesta y la convertimos en un JSONObject
            JSONObject respuesta = (JSONObject) JSONValue.parse(response);

            // El JSON recibido es correcto
            String estado = (String) respuesta.get("estado");
            // Si ok, obtenemos array de jugadores para recorrer y generar hashmap
            if (estado.equals("ok")) {
                JSONArray array = (JSONArray) respuesta.get("Dispensador");
// dispensadores clave string
                // depositos valor
                if (array.size() > 0) {
                    for (int i = 0; i < array.size(); i++) {
                        JSONObject row = (JSONObject) array.get(i);

                        int id = Integer.parseInt(row.get("id").toString());
                        String clave = row.get("clave").toString();
                        String nombre = row.get("nombre").toString();
                        int precio = Integer.parseInt(row.get("precio").toString());
                        int cantidad = Integer.parseInt(row.get("cantidad").toString());


                        newDispensador = new Dispensador(clave, nombre, precio,cantidad);


                        hm_dispensadores.put(clave, newDispensador);
                    }

                }

            }


        } catch (Exception e) {
            System.out.println("Ha ocurrido un error en la busqueda de datos");

            e.printStackTrace();

            System.exit(-1);
        }

        return hm_dispensadores;
    }

    @Override
    public boolean guardarDepositos(HashMap<Integer, Deposito> depositos) {
        Deposito auxDeposito = new Deposito();

        for (Map.Entry<Integer, Deposito> entry : depositos.entrySet()) {
            Integer k = entry.getKey();
            Deposito v = entry.getValue();

            Deposito deposito = depositos.get(k);
            try {
                JSONObject objJCurse = new JSONObject();
                JSONObject objPeticion = new JSONObject();


                objJCurse.put("id", deposito.getId());
                objJCurse.put("nombre", deposito.getNombreMoneda());
                objJCurse.put("valor", deposito.getValor());
                objJCurse.put("cantidad", deposito.getCantidad());


                objPeticion.put("peticion", "add");
                objPeticion.put("deposito", objJCurse);

                String json = objPeticion.toJSONString();
                String url = SERVER_PATH + SET_DEPOSITO;
                String response = encargadoPeticiones.postRequest(url, json);



            } catch (Exception e) {
                System.out.println(
                        "Excepcion desconocida.");
                // e.printStackTrace();
                System.out.println("Fin ejecución");
                System.exit(-1);
            }
        }
        return true;
    }

    @Override
    public boolean guardarDispensadores(HashMap<String, Dispensador> dispensadores) {
        Deposito auxDeposito = new Deposito();

        for (Map.Entry<String, Dispensador> entry : dispensadores.entrySet()) {
            String k = entry.getKey();
            Dispensador v = entry.getValue();

            Dispensador dispensador = dispensadores.get(k);
            try {
                JSONObject objJCurse = new JSONObject();
                JSONObject objPeticion = new JSONObject();



                objJCurse.put("id", dispensador.getId());
                objJCurse.put("clave", dispensador.getClave());
                objJCurse.put("nombre", dispensador.getNombreProducto());
                objJCurse.put("precio", dispensador.getPrecio());
                objJCurse.put("cantidad", dispensador.getCantidad());



                objPeticion.put("peticion", "add");
                objPeticion.put("dispensador", objJCurse);

                String json = objPeticion.toJSONString();
                String url = SERVER_PATH + SET_DISPENSADOR;
                String response = encargadoPeticiones.postRequest(url, json);



            } catch (Exception e) {
                System.out.println(
                        "Excepcion desconocida.");
                // e.printStackTrace();
                System.out.println("Fin ejecución");
                System.exit(-1);
            }
        }
        return true;
    }
    }

