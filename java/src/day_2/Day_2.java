package day_2;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Lösung für Tag 2.
 * 
 * @author Yanik Recke 
 */
public class Day_2 {

	public static void main(String[] args) {
		String pathToInput = "src/day_2/input.txt";
		System.out.print(part1(pathToInput));
		System.out.print(" - ");
		System.out.println(part2(pathToInput));
	}
	
	
	/**
	 * Prüfen, ob in einem Set eines Games zu viele Bälle einer
	 * Farbe gezogen wurde.
	 * 
	 * @param path - Pfad zum Puzzle Input
	 * @return - Summe der möglichen Game IDs
	 */
	private static int part1(String path) {
		// 12 red, 13 green, 14 blue
		int red = 12;
		int green = 13;
		int blue = 14;
		
		int idx = 1;
		int sum = 0;
		
		List<String> input = helpers.HelperMethods.getInputAsListOfString(path);
		
		Map<String, Integer> map = new HashMap<>();
		map.put("red", 0);
		map.put("green", 0);
		map.put("blue", 0);
		
		boolean possible = true;
		
		for (String line : input) {
			String temp = line.split(":")[1];
			String[] sets = temp.split(";");
			
			possible = true;
			
			for (String set : sets) {
				String[] curr = set.split(",");
				
				map.put("red", 0);
				map.put("green", 0);
				map.put("blue", 0);
				
				for (String entry : curr) {
					for (String key : map.keySet()) {
						if (entry.contains(key)) {
							String number = "";
							int tempIdx = 0;
							boolean done = false;
							
							while (!done && tempIdx < entry.length()) {
								if (tempIdx == 0 || Character.isDigit(entry.charAt(tempIdx))) {
									if (!Character.isDigit(entry.charAt(tempIdx))) {
										tempIdx++;
									} else {
										number += entry.charAt(tempIdx);
										tempIdx++;
									}
								} else {
									done = true;
								}
							}
							
							map.put(key, map.get(key) + Integer.parseInt(number));
						}
					}
				}
				
				if (possible && (map.get("red") <= red && map.get("green") <= green && map.get("blue") <= blue)) {
					possible = true;
				} else {
					possible = false;
				}
			}
			
			
			if (possible) {
				sum += idx;
			}
			
			idx++;
		}
		
		return sum;
	}
	
	
	/**
	 * Für jedes Game das Maximum jeder gezogenen Farbe
	 * ermitteln.
	 * 
	 * @param path - Pfad zum Input
	 * @return
	 */
	private static int part2(String path) {
		// 12 red, 13 green, 14 blue
		int sum = 0;
		
		List<String> input = helpers.HelperMethods.getInputAsListOfString(path);
		
		Map<String, Integer> map = new HashMap<>();
		map.put("red", 0);
		map.put("green", 0);
		map.put("blue", 0);
		
		for (String line : input) {
			map.put("red", 0);
			map.put("green", 0);
			map.put("blue", 0);
			
			String temp = line.split(":")[1];
			String[] sets = temp.split(";");
			
			for (String set : sets) {
				String[] curr = set.split(",");
				
				
				for (String entry : curr) {
					for (String key : map.keySet()) {
						if (entry.contains(key)) {
							String number = "";
							int tempIdx = 0;
							boolean done = false;
							
							while (!done && tempIdx < entry.length()) {
								if (tempIdx == 0 || Character.isDigit(entry.charAt(tempIdx))) {
									if (!Character.isDigit(entry.charAt(tempIdx))) {
										tempIdx++;
									} else {
										number += entry.charAt(tempIdx);
										tempIdx++;
									}
								} else {
									done = true;
								}
							}
							
							int numInt = Integer.parseInt(number);
							if (map.get(key) < numInt) {
								map.put(key, Integer.parseInt(number));
							}
						}
					}
				}
				
			}
			
			sum += map.get("red") * map.get("blue") * map.get("green");
		}
		
		return sum;
	}
}
