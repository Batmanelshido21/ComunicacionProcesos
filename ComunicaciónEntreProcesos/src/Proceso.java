import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.Iterator;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.management.ManagementFactory;
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 
 */

/**
 * @author javier
 *
 */
public class Proceso {

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		Scanner teclado = new Scanner(System.in);

		BufferedWriter bw = null;
		FileWriter fw = null;

		try {
			System.out.println("Introduce el texto deseado");
			String data = teclado.nextLine();
			File file = new File("\\home\\javier\\eclipse-workspace\\ComunicaciónEntreProcesos");
			// Si el archivo no existe, se crea!
			if (!file.exists()) {
				file.createNewFile();
			}
			// flag true, indica adjuntar información al archivo.
			fw = new FileWriter(file.getAbsoluteFile(), true);
			bw = new BufferedWriter(fw);
			bw.write(data);
			bw.newLine();
			System.out.println("se agregó la información solicitada");
			System.out.println("============================== información leida");
			// leer los archivos aguardados
			String cadena;
			FileReader f = new FileReader(file);
			BufferedReader b = new BufferedReader(f);
			while ((cadena = b.readLine()) != null) {
				System.out.println(cadena);
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				// Cierra instancias de FileWriter y BufferedWriter
				if (bw != null)
					bw.close();
				if (fw != null)
					fw.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		System.out.println("========================== Valor de las variables de entorno");

		// Obtener varibles de entorno del sistema.
		Map map = System.getenv();
		Set keys = map.keySet();
		Iterator iterator = keys.iterator();
		System.out.println("Nombre de la variable \t Valor de la Variable");
		while (iterator.hasNext()) {
			String key = (String) iterator.next();
			String value = (String) map.get(key);
			System.out.println(key + " " + value);
		}

		ProcessBuilder builder = new ProcessBuilder().command("cat", "/proc/uptime");
		Process process = builder.start();

		// Esperar a que termine el proceso y obtener su valor de salida
		process.waitFor(10, TimeUnit.SECONDS);
		int value = process.exitValue();
		if (value != 0) {
			throw new Exception(MessageFormat.format("Código de salida con error (%d)", value));
		}

		// Obtener la salida del proceso
		BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream(), "UTF-8"));
		String result = br.lines().collect(Collectors.joining("\n"));
		br.close();

		System.out.println("Obtener PID ======================");

		String id = ManagementFactory.getRuntimeMXBean().getName();
		String[] ids = id.split("@");
		System.out.println(ids[0]);

	}
}