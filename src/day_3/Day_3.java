package day_3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import helpers.HelperMethods;


/**
 * Lösung für Tag 3.
 * 
 * @author Yanik Recke
 */
public class Day_3 {

	public static void main(String[] args) {
		String pathToInput = "src/day_3/input.txt";
		System.out.print(part1(pathToInput) + " - ");
		System.out.println(part2(pathToInput));
	}
	
	
	/**
	 * Wenn Zahl gefunden in alle Richtungen gucken, ob Symbol vorhanden,
	 * wenn ja dann Rest der Zahl ermitteln und zum Endergebnis hinzurechnen.
	 * 
	 * @param path - Pfad zum Input
	 * @return - Summe
	 */
	private static long part1(String path) {
		char[][] field = HelperMethods.getInputAsTwoDimensionalCharArray(path);
		Set<Character> digits = new HashSet<>(Arrays.asList('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '.'));

		long sum = 0;
		String temp = "";	
		boolean done = false;
		
		for (int y = 0; y < field[0].length; y++) {
			done = false;
			temp = "";
			
			for (int x = 0; x < field.length; x++) {
				
				char curr = field[x][y];
				int number = 0;
				
				
				if (Character.isDigit(curr)) {
					if (done) {
						continue;
					}
					
					temp += curr + "";
					int tempX;
					int tempY;

					// left
					tempX = x - 1;
					
					// check if inbounds
					if (tempX >= 0 && !done) {
						// found symbol
						if (!digits.contains(field[tempX][y])) {
							// bis ende ablaufen
							number = getRestOfNumber(temp, field, x, y);
							done = true;
						}
					}
					
					// top left
					tempY = y - 1;
					
					if (tempX >= 0 && tempY >= 0 && !done) {
						// found symbol
						if (!digits.contains(field[tempX][tempY])) {
							// bis ende ablaufen
							number = getRestOfNumber(temp, field, x, y);
							done = true;
						}
					}
					
					// top
					if (tempY >= 0 && !done) {
						// found symbol
						if (!digits.contains(field[x][tempY])) {
							// bis ende ablaufen
							number = getRestOfNumber(temp, field, x, y);
							done = true;
						}
					}
					
					tempX = x + 1;
					
					// top right
					if (tempY >= 0 && tempX < field.length && !done) {
						// found symbol
						if (!digits.contains(field[tempX][tempY])) {
							// bis ende ablaufen
							number = getRestOfNumber(temp, field, x, y);
							done = true;
						}
					}
					
					// right 
					if (tempX < field.length && !done) {
						// found symbol
						if (!digits.contains(field[tempX][y])) {
							// bis ende ablaufen
							number = getRestOfNumber(temp, field, x, y);
							done = true;
						}
					}
					
					// down right
					tempY = y + 1;
					
					if (tempX < field.length && tempY < field[x].length && !done) {
						// found symbol
						if (!digits.contains(field[tempX][tempY])) {
							// bis ende ablaufen
							number = getRestOfNumber(temp, field, x, y);
							done = true;
						}
					}
					
					// down
					
					tempY =  y + 1;
					if (tempY < field[x].length && !done) {
						// found symbol
						if (!digits.contains(field[x][tempY])) {
							// bis ende ablaufen
							number = getRestOfNumber(temp, field, x, y);
							done = true;
						}
					}
					
					// down left
					tempX = x - 1;
					
					if (tempX >= 0 && tempY < field[x].length && !done) {
						// found symbol
						if (!digits.contains(field[tempX][tempY])) {
							// bis ende ablaufen
							number = getRestOfNumber(temp, field, x, y);
							done = true;
						}
					}
					
					sum += number;
					
				} else {
					temp = "";
					done = false;
				}
			}
		}
		
		return sum;
	}
	
	/**
	 * Berechnet den Rest der Zahl aus dem Array.
	 * 
	 * @param temp - String der die bisherigen Ziffern enthält
	 * @param field - Zweidimensionaler Array
	 * @param idx - Aktuelle x-Koordinate
	 * @param y - y-Koordinate
	 * @return - Ganze Zahl
	 */
	private static int getRestOfNumber(String temp, char[][] field, int idx, int y) {
		boolean done = false;
		idx++;
		
		while (!done && idx < field.length) {
			if (Character.isDigit(field[idx][y])) {
				temp += field[idx][y];
			} else {
				done = true;
			}
			
			idx++;
		}
		
		return Integer.parseInt(temp);
	}
	
	
	/**
	 * Wenn ein Engine Part gefunden wurde, prüfen,
	 * ob '*' das Symbol ist. Wenn ja dann zu Map hinzufügen ->
	 * Schlüssel sind die Koordinaten von '*'. Am Ende prüfen, 
	 * welches '*' genau zwei Engine Parts als Nachbar hat.
	 * 
	 * @param path - Pfad zum Input
	 * @return 
	 */
	private static long part2(String path) {
		char[][] field = HelperMethods.getInputAsTwoDimensionalCharArray(path);
		Set<Character> digits = new HashSet<>(Arrays.asList('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '.'));
		
		Map<Position, Set<Integer>> map = new HashMap<>();
		
		long sum = 0;
		String temp = "";	
		boolean done = false;
		
		for (int y = 0; y < field[0].length; y++) {
			done = false;
			temp = "";
			for (int x = 0; x < field.length; x++) {
				
				char curr = field[x][y];
				int number = 0;
				
				
				if (Character.isDigit(curr)) {
					if (done) {
						continue;
					}
					
					temp += curr + "";
					int tempX;
					int tempY;

					// left
					tempX = x - 1;
					
					// check if inbounds
					if (tempX >= 0 && !done) {
						// found symbol
						if (!digits.contains(field[tempX][y])) {
	
							// bis ende ablaufen
							number = getRestOfNumber(temp, field, x, y);
							done = true;
							
							Position pos = new Position(tempX, y);
							if (field[tempX][y] == '*') {
								if (map.containsKey(pos)) {
									map.get(pos).add(number);
								} else {
									map.put(pos, new HashSet<>(Arrays.asList(number)));
								}
							} 
						}
					}
					
					// top left
					tempY = y - 1;
					
					if (tempX >= 0 && tempY >= 0 && !done) {
						// found symbol
						if (!digits.contains(field[tempX][tempY])) {
							// bis ende ablaufen
							number = getRestOfNumber(temp, field, x, y);
							done = true;
							if (field[tempX][tempY] == '*') {
								Position pos = new Position(tempX, tempY);
								if (map.containsKey(pos)) {
									map.get(pos).add(number);
								} else {
									map.put(pos, new HashSet<>(Arrays.asList(number)));
								}
							}
						}
						
					}
					
					// top
					if (tempY >= 0 && !done) {
						// found symbol
						if (!digits.contains(field[x][tempY])) {
							// bis ende ablaufen
							number = getRestOfNumber(temp, field, x, y);
							done = true;
							if (field[x][tempY] == '*') {
								Position pos = new Position(x, tempY);
								if (map.containsKey(pos)) {
									map.get(pos).add(number);
								} else {
									map.put(pos, new HashSet<>(Arrays.asList(number)));
								}
							}
						}
						
					}
					
					tempX = x + 1;
					
					// top right
					if (tempY >= 0 && tempX < field.length && !done) {
						// found symbol
						if (!digits.contains(field[tempX][tempY])) {
							// bis ende ablaufen
							number = getRestOfNumber(temp, field, x, y);
							done = true;
							if (field[tempX][tempY] == '*') {
								Position pos = new Position(tempX, tempY);
								if (map.containsKey(pos)) {
									map.get(pos).add(number);
								} else {
									map.put(pos, new HashSet<>(Arrays.asList(number)));
								}
							}
						}
						
					}
					
					// right 
					if (tempX < field.length && !done) {
						// found symbol
						if (!digits.contains(field[tempX][y])) {
							// bis ende ablaufen
							number = getRestOfNumber(temp, field, x, y);
							done = true;
							if (field[tempX][y] == '*') {
								Position pos = new Position(tempX, y);
								if (map.containsKey(pos)) {
									map.get(pos).add(number);
								} else {
									map.put(pos, new HashSet<>(Arrays.asList(number)));
								}
							}
						}
						
					}
					
					// down right
					tempY = y + 1;
					
					if (tempX < field.length && tempY < field[x].length && !done) {
						// found symbol
						if (!digits.contains(field[tempX][tempY])) {
							// bis ende ablaufen
							number = getRestOfNumber(temp, field, x, y);
							done = true;
							if (field[tempX][tempY] == '*') {
								Position pos = new Position(tempX, tempY);
								if (map.containsKey(pos)) {
									map.get(pos).add(number);
								} else {
									map.put(pos, new HashSet<>(Arrays.asList(number)));
								}
							}
						}
						
					}
					
					// down
					
					tempY =  y + 1;
					if (tempY < field[x].length && !done) {
						// found symbol
						if (!digits.contains(field[x][tempY])) {
							// bis ende ablaufen
							number = getRestOfNumber(temp, field, x, y);
							done = true;
							if (field[x][tempY] == '*') {
								Position pos = new Position(x, tempY);
								if (map.containsKey(pos)) {
									map.get(pos).add(number);
								}else {
									map.put(pos, new HashSet<>(Arrays.asList(number)));
								}
							}
						}
						
					}
					
					// down left
					tempX = x - 1;
					
					if (tempX >= 0 && tempY < field[x].length && !done) {
						// found symbol
						if (!digits.contains(field[tempX][tempY])) {
							// bis ende ablaufen
							number = getRestOfNumber(temp, field, x, y);
							done = true;
							if (field[tempX][tempY] == '*') {
								Position pos = new Position(tempX, tempY);
								if (map.containsKey(pos)) {
									map.get(pos).add(number);
								} else {
									map.put(pos, new HashSet<>(Arrays.asList(number)));
								}
							}
						}
						
					}
					
				} else {
					temp = "";
					done = false;
				}
			}
		}
		
		// TODO
		for (Set<Integer> set : map.values()) {
			List<Integer> list = new ArrayList<>();
			if (set.size() == 2) {
				for (Integer num : set) {
					list.add(num);
				}
				sum += list.get(0) * list.get(1);
				
			}
		}
		
		return sum;
	}
	
}
