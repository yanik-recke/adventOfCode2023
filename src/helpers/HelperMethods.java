package helpers;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;


/**
 * Methoden zum Einlesen des Inputs usw.
 * 
 * @author Yanik Recke
 *
 */
public class HelperMethods {
	
	
	/**
	 * Liest einen einzeiligen Input in eine
	 * Liste von Integern.
	 * 
	 * @param path - Pfad zur Datei
	 * @return - Liste mit den Ganzzahlwerten
	 */
	public static List<Integer> getInputAsListOfIntegers(String path){
		assert (path != null);
		
		List<Integer> input = new ArrayList<>();
		
		try (BufferedReader br = new BufferedReader(new FileReader(path))) {
		    String line = br.readLine();

		    while (line != null) {
		        input.add(Integer.parseInt(line));
		        line = br.readLine();
		    }

		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		return input;
	}
	
	
	/**
	 * Liest einen einzeiligen Input in eine
	 * Liste von Integern.
	 * 
	 * @param path - Pfad zur Datei
	 * @return - Liste mit den Ganzzahlwerten
	 */
	public static List<String> getInputAsListOfString(String path){
		assert (path != null);
		
		List<String> input = new ArrayList<>();
		
		try (BufferedReader br = new BufferedReader(new FileReader(path))) {
		    String line = br.readLine();

		    while (line != null) {
		        input.add(line);
		        line = br.readLine();
		    }

		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		return input;
	}
	
	
	/**
	 * Liest einen Input in einen 
	 * mehrdimensionalen Array von Integern.
	 * 
	 * @param path - Pfad zur Datei
	 * @return - mehrdimensionaler Array an Integern
	 */
	public static int[][] getInputAsTwoDimensionalArray(String path) {
		assert (path != null);

		int[][] input;
		int i = 0;
		int j = 0;
		
		try (BufferedReader br = new BufferedReader(new FileReader(path))) {
		    String line = br.readLine();
		    j = line.length();
		    
		    while (line != null) {
		        i++;
		        line = br.readLine();
		    }

		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		input = new int[j][i];
		
		i = 0;
		j = 0;
		
		try (BufferedReader br = new BufferedReader(new FileReader(path))) {
		    String line = br.readLine();
		    
		    while (line != null) {
		    	for (int k = 0; k < line.length(); k++) {
		    		input[k][i] = line.charAt(k) - '0';
		    	}
		    	
		        i++;
		        line = br.readLine();
		    }

		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		return input;
	}
	
	
	
	/**
	 * Liest einen Input in einen 
	 * mehrdimensionalen Array von Integern.
	 * 
	 * @param path - Pfad zur Datei
	 * @return - mehrdimensionaler Array an Integern
	 */
	public static char[][] getInputAsTwoDimensionalCharArray(String path) {
		assert (path != null);

		char[][] input;
		int i = 0;
		int j = 0;
		
		try (BufferedReader br = new BufferedReader(new FileReader(path))) {
		    String line = br.readLine();
		    j = line.length();
		    
		    while (line != null) {
		        i++;
		        line = br.readLine();
		    }

		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		input = new char[j][i];
		
		i = 0;
		j = 0;
		
		try (BufferedReader br = new BufferedReader(new FileReader(path))) {
		    String line = br.readLine();
		    
		    while (line != null) {
		    	for (int k = 0; k < line.length(); k++) {
		    		input[k][i] = line.charAt(k);
		    	}
		    	
		        i++;
		        line = br.readLine();
		    }

		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		return input;
	}
	
	
	/**
	 * Liest einen einzeiligen Input in einen
	 * Array von Integern.
	 * 
	 * @param path - Pfad zur Datei
	 * @return - Liste mit den Ganzzahlwerten
	 */
	public static int[] getInputAsArrayOfIntegers(String path){
		assert (path != null);
		
		return getInputAsListOfIntegers(path).stream().mapToInt(i->i).toArray();
	}
	
	
	/**
	 * Prüft ob eine Position in den Grenzen eines 
	 * Arrays liegt.
	 * 
	 * @param arr - der Array
	 * @param x - x-Koordinate
	 * @param y - y-Koordinate
	 * @return - true, wenn Koordinaten innerhalb liegen, false wenn nicht
	 */
	public static boolean isInbounds(int[][] arr, int x, int y) {
		return x >= 0 && y >= 0 && x < arr.length && y < arr[x].length;
	}

}
