package accesoDatos;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import logicaRefrescos.Deposito;
import logicaRefrescos.Dispensador;

/*
 * Todas los accesos a datos implementan la interfaz de Datos
 */

public class FicherosTexto implements I_Acceso_Datos {
	
	File fDis; // FicheroDispensadores
	File fDep; // FicheroDepositos

	public FicherosTexto(){
		System.out.println("ACCESO A DATOS - FICHEROS DE TEXTO");
	}
	
	@Override
	public HashMap<Integer, Deposito> obtenerDepositos() {
		fDep= new File("Ficheros/datos/depositos.txt");
		HashMap<Integer, Deposito> depositosCreados = new HashMap<Integer, Deposito>();
		Deposito deposito;
		try {
			FileReader fr = new FileReader(fDep);
			BufferedReader br = new BufferedReader(fr);
			String line;
			//CINCO CENTIMOS;5;10
			while((line = br.readLine()) != null) {
				deposito = new Deposito();
				String[] data = line.split(";");

				String nombre = data[0];
				deposito.setNombreMoneda(nombre);

				int valor = Integer.parseInt(data[1]);
				deposito.setValor(valor);

				int cantidad = Integer.parseInt(data[2]);
				deposito.setCantidad(cantidad);

				depositosCreados.put(valor,deposito);
			}

		}
		catch (Exception e) {
			// TODO: handle exception
		}

		return depositosCreados;
	}
	

	@Override
	public HashMap<String, Dispensador> obtenerDispensadores()  {


		fDep= new File("Ficheros/datos/dispensadores.txt");
		HashMap<String, Dispensador> dispensadoresCreados = new  HashMap<String, Dispensador>();
		Dispensador dispensador;
		try {
			FileReader fr = new FileReader(fDep);
			BufferedReader br = new BufferedReader(fr);
			String line;
			//CINCO CENTIMOS;5;10
			while((line = br.readLine()) != null) {
				dispensador = new Dispensador();
				String[] data = line.split(";");
				//coca;Coca-Cola;105;3
				String clave = data[0];
				dispensador.setClave(clave);

				String nombre = data[1];
				dispensador.setNombreProducto(nombre);

				int precio = Integer.parseInt(data[2]);
				dispensador.setPrecio(precio);

				int cantidad = Integer.parseInt(data[3]);
			dispensador.setCantidad(cantidad);

				dispensadoresCreados.put(clave,dispensador);
			}

		}
		catch (Exception e) {
			// TODO: handle exception
		}




		return dispensadoresCreados;

	}

	@Override
	public boolean guardarDepositos(HashMap<Integer, Deposito> depositos) {
//CINCO CENTIMOS;5;10
		boolean todoOK = true;
		try {
			FileWriter fw = new FileWriter("Ficheros/datos/depositos.txt", false);
			BufferedWriter bw = new BufferedWriter(fw);
			fw.write("");
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		for (Map.Entry<Integer, Deposito> entry : depositos.entrySet()) {
			Integer k = entry.getKey();
			Deposito v = entry.getValue();
			try {
				FileWriter fw = new FileWriter("Ficheros/datos/depositos.txt", true);
				BufferedWriter bw = new BufferedWriter(fw);
				fw.write(depositos.get(k).getNombreMoneda() + ";");
				fw.write(depositos.get(k).getValor() + ";");
				fw.write(depositos.get(k).getCantidad() + ";");
				fw.write("\n");


				bw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return todoOK;

	}

	@Override
	public boolean guardarDispensadores(HashMap<String, Dispensador> dispensadores) {
//coca;Coca-Cola;105;3
		boolean todoOK = true;
		try {
			FileWriter fw = new FileWriter("Ficheros/datos/dispensadores.txt", false);
			BufferedWriter bw = new BufferedWriter(fw);
			fw.write("");
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		for (Map.Entry<String, Dispensador> entry : dispensadores.entrySet()) {
			String k = entry.getKey();
			Dispensador v = entry.getValue();
			try {
				FileWriter fw = new FileWriter("Ficheros/datos/dispensadores.txt", true);
				BufferedWriter bw = new BufferedWriter(fw);
				fw.write(dispensadores.get(k).getClave() + ";");
				fw.write(dispensadores.get(k).getNombreProducto() + ";");
				fw.write(dispensadores.get(k).getPrecio() + ";");
				fw.write(dispensadores.get(k).getCantidad() + ";");
				fw.write("\n");

				bw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return todoOK;
	}

} // Fin de la clase