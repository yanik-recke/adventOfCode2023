package day_14;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Lösung für Tag 14.
 * 
 * @author Yanik Recke
 */
public class Day_14 {

	
	public static void main(String[] args) {
		String pathToInput = "src/day_14/input.txt";
		
		System.out.print(part1(pathToInput) + " - ");
		System.out.println();
		System.out.println(part2(pathToInput));
	}
	
	
	/**
	 * Feld von oben ablaufen und jeden 'O' Stein
	 * so lange nach oben verschieben bis man auf einen anderen
	 * Stein oder das Ende des Feldes stößt.
	 * 
	 * @param path - Pfad zum Input
	 * @return
	 */
	private static long part1(String path) {
		char[][] field = helpers.HelperMethods.getInputAsTwoDimensionalCharArray(path);
		int maxLoad = field[0].length;
		
		long load = 0;
		int tempX;
		int tempY;
		
		for (int x = 0; x < field.length; x++) {
			for (int y = 0; y < field[x].length; y++) {
				if (field[x][y] == 'O') {
					tempX = x;
					tempY = y;
					
					if (y - 1 >= 0) {
						
						while (tempY > 0 && field[tempX][tempY - 1] == '.') {
							field[x][tempY] = '.';
							field[x][tempY - 1] = 'O';
							tempY-= 1;
						}
						
						
						field[x][tempY] = 'O';
						load += (maxLoad - tempY);
					} else {
						load += maxLoad;
					}
					
				}
			}
		}

		return load;
	}
	
	
	/**
	 * Nach jeder Verschiebung das Feld inklusive load
	 * merken und prüfen, ob es dieses Feld schonmal gab.
	 * Falls ja, aufhören und Zyklus bis 4_000_000_000
	 * Durchläufe erreicht sind ablaufen.
	 * 
	 * @param path - Pfad zum Input
	 * @return
	 */
	private static long part2(String path) {
		char[][] field = helpers.HelperMethods.getInputAsTwoDimensionalCharArray(path);
		
		long load = 0;
		
		
		Map<String, Integer> map = new LinkedHashMap<>();
		List<Integer> loop = new ArrayList<>();
		long stopped = 0;
		boolean found = false;
		int count = 0;
		Pair p;
		for (long i = 0; i < 4000000000L && !found; i++) {
			if (count == 0) {
				count++;
				p = tiltNorth(field);
				stopped = i;
			} else if (count == 1) {
				count++;
				p = tiltWest(field);
			} else if (count == 2) {
				count++;
				p = tiltSouth(field);
			} else {
				count = 0;
				p = tiltEast(field);
			}
			
			if (map.containsKey(p.field)) {
				stopped = i;
				for (String key : map.keySet()) {
					if (key.equals(p.field)) {
						found = true;
					}
					
					if (found) {
						loop.add(map.get(key));
					}
				}
			} else {
				map.put(p.field, p.load);
			}
		}
		
		load = 0;
		while (stopped < 4000000000L) {
			for (Integer i : loop) {
				if (stopped < 4000000000L) {
					stopped++;					
					load = i;					
				}
			}
		}
		
		return load;
	}
	
	
	/**
	 * Hilfsklasse, repräsentiert Paar eines Feldes
	 * in Stringform und dem entsprechenden load Wert.
	 */
	public static class Pair {
		String field;
		Integer load;
		
		
		/**
		 * Erzeugt ein Paar.
		 * 
		 * @param f - String
		 * @param l - Load
		 */
		public Pair(String f, Integer l) {
			this.field = f;
			this.load = l;
		}
	}
	
	
	/**
	 * Alle 'O' nach Norden rollen lassen.
	 * 
	 * @param field - Feld
	 * @return - Paar(Feld, Load)
	 */
	private static Pair tiltNorth(char[][] field) {
		int tempX;
		int tempY;
		int load = 0;
		int maxLoad = field[0].length;
		
		for (int x = 0; x < field.length; x++) {
			for (int y = 0; y < field[x].length; y++) {
				if (field[x][y] == 'O') {
					tempX = x;
					tempY = y;
					
					if (y - 1 >= 0) {
						while (tempY > 0 && field[tempX][tempY - 1] == '.') {
							field[tempX][tempY] = '.';
							field[tempX][--tempY] = 'O';
						}
						
						field[tempX][tempY] = 'O';
						load += (maxLoad - tempY);
					} else {
						load += maxLoad;
					}
					
				}
			}
		}

		return new Pair("NORTH|" + hash(field), load);
	}
	
	
	/**
	 * Alle 'O' nach Westen rollen lassen.
	 * 
	 * @param field - Feld
	 * @return - Paar(Feld, Load)
	 */
	private static Pair tiltWest(char[][] field) {
		int tempX;
		int tempY;
		int load = 0;
		int maxLoad = field[0].length;
		
		
		for (int y = 0; y < field.length; y++) {
			for (int x = 0; x < field[y].length; x++) {
				if (field[x][y] == 'O') {
					tempX = x;
					tempY = y;
					
					if (x - 1 >= 0) {
						while (tempX > 0 && field[tempX - 1][tempY] == '.') {
							field[tempX][tempY] = '.';
							field[--tempX][tempY] = 'O';
						}
						
						field[tempX][tempY] = 'O';
						load += (maxLoad - tempY);
					} else {
						load += (maxLoad - tempY);
					}
					
				}
			}
		}

		return new Pair("WEST|" + hash(field), load);
	}
	
	
	/**
	 * Alle 'O' nach Süden rollen lassen.
	 * 
	 * @param field - Feld
	 * @return - Paar(Feld, Load)
	 */
	private static Pair tiltSouth(char[][] field) {
		int tempX;
		int tempY;
		int load = 0;
		int maxLoad = field[0].length;
		
		for (int x = field.length - 1; x >= 0; x--) {
			for (int y = field[x].length - 1; y >= 0; y--) {
				if (field[x][y] == 'O') {
					tempX = x;
					tempY = y;
					
					if (y + 1 < field[x].length) {
						while (tempY < field[x].length - 1 && field[tempX][tempY + 1] == '.') {
							field[tempX][tempY] = '.';
							field[tempX][++tempY] = 'O';
						}
						
						field[tempX][tempY] = 'O';
						load += (maxLoad - tempY);
					} else {
						load += (maxLoad - tempY);
					}
					
				}
			}
		}

		
		return new Pair("SOUTH|" + hash(field), load);
	}
	
	
	/**
	 * Alle 'O' nach Osten rollen lassen.
	 * 
	 * @param field - Feld
	 * @return - Paar(Feld, Load)
	 */
	private static Pair tiltEast(char[][] field) {
		int tempX;
		int tempY;
		int load = 0;
		int maxLoad = field[0].length;
		
		for (int y = field.length - 1; y >= 0; y--) {
			for (int x = field.length - 1; x >= 0; x--) {
				if (field[x][y] == 'O') {
					tempX = x;
					tempY = y;
					
					if (x + 1 < (field.length)) {
						while (tempX < field.length - 1 && field[tempX + 1][tempY] == '.') {
							field[tempX][tempY] = '.';
							field[++tempX][tempY] = 'O';
						}
						
						field[tempX][tempY] = 'O';
						load += (maxLoad - tempY);
					} else {
						load += (maxLoad - tempY);
					}
					
				}
			}
		}
		
		return new Pair("EAST|" + hash(field), load);
	}
	
	
	/**
	 * Methode um Feld zu Stringdarstellung
	 * zu konvertieren ("hashen").
	 * 
	 * @param field - Feld
	 * @return - Feld in StringBuilder Darstellung
	 */
	private static StringBuilder hash(char[][] field) {
		StringBuilder sb = new StringBuilder();
		for (int y = 0; y < field.length; y++) {
			for (int x = 0; x < field.length; x++) {
				sb.append(field[x][y]);
			} 
			sb.append("-");
		}
		
		return sb;
	}
	
}
