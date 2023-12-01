package day_1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import helpers.HelperMethods;


/**
 * Lösungen zu Tag 1.
 * 
 * @author Yanik Recke
 */
public class Day_1 {

	public static void main(String[] args) {
		String pathToInput = "src/day_1/input.txt";
		System.out.println(part1(pathToInput) + " - " + part2(pathToInput));
	}

	/**
	 * Läuft jede Zeile einmal von links und einmal von rechts
	 * bis zur ersten Zahl ab.
	 * 
	 * @param path - Pfad zum Puzzle Input
	 * @return - Alle ersten und letzten Digits aufaddiert
	 */
	private static int part1(String path) {
		int val = 0;
		
		List<String> input = HelperMethods.getInputAsListOfString(path);
		for (String line : input) {
			int idx = 0;
			Character found = null;
			while (idx < line.length() && found == null) {
				if (Character.isDigit(line.charAt(idx))) {
					found = line.charAt(idx);
				}
				idx++;
			}
			
			idx = line.length() - 1;
			Character found2 = null;
			while (idx >= 0 && found2 == null) {
				if (Character.isDigit(line.charAt(idx))) {
					found2 = line.charAt(idx);
				}
				idx--;
			}
			
			val+= Integer.parseInt(found + "" + found2);
		}
		
		return val;
	}
	
	
	/**
	 * Findet zuerst die ausgeschrieben Digits und prüft dann,
	 * ob es noch welche gibt, die früher / später vorkommen als 
	 * die aktuell gefundene erste / letzte.
	 * 
	 * Verbesserungen: Man könnte aufhören wenn man weiß, dass es
	 * keine frühere / spätere Zahl mehr geben kann, also Index = 0 oder 
	 * Index = Länge
	 * 
	 * @param path - Pfad zum Puzzle Input
	 * @return - Alle ersten und letzten Ziffern aufaddiert
	 */
	private static int part2(String path) {
		String first = null;
		String last = null;
		
		Map<String, Integer> digits = new LinkedHashMap<>();
		digits.put("one", 1);
		digits.put("two", 2);
		digits.put("three", 3);
		digits.put("four", 4);
		digits.put("five", 5);
		digits.put("six", 6);
		digits.put("seven", 7);
		digits.put("eight", 8);
		digits.put("nine", 9);
		
		int val = 0;
		
		try (BufferedReader br = new BufferedReader(new FileReader(path))) {
		    String line = br.readLine().toLowerCase();
		    char temp;
	    	int firstIdx = Integer.MAX_VALUE;
	    	int lastIdx = 0;
		    
		    while (line != null) {
		    	int idx = 0;
		    	
		    	for (String digit : digits.keySet()) {
		    		if (line.contains(digit)) {
		    			
		    			int tempIdx = line.indexOf(digit);
		    			while (tempIdx != -1) {
		    				if (tempIdx < firstIdx) {
		    					if (last == null && first != null) {
		    						last = first;
		    						lastIdx = firstIdx;
		    					}
		    				
		    					firstIdx = tempIdx;
		    					first = Integer.toString(digits.get(digit));
		    				} else if (tempIdx > lastIdx) {
		    					lastIdx = tempIdx;
		    					last = Integer.toString(digits.get(digit));
		    				}
		    				tempIdx = line.indexOf(digit, tempIdx + 1);
		    			}
		    		}
		    	}
		    	
		    	
		    	idx = 0;
		    	while (idx < line.length()) {
		    		temp = line.charAt(idx);
		    		if (Character.isDigit(temp) && idx < firstIdx) {
		    			if (last == null && first != null) {
		    				last = first;
		    				lastIdx = firstIdx;
		    			}
		    			firstIdx = idx;
		    			first = temp + "";
		    		} else if (first != null && Character.isDigit(temp) && idx > lastIdx) {
		    			last = temp + "";
		    			lastIdx = idx;
		    		}	
		    		idx++;
		    	}
		    	
		    	if (last != null && first != null) {
		    		val += Integer.parseInt(first + last);
		    	} else if (first == null){
		    		val += Integer.parseInt(last + last);
		    	} else if (last == null ) {
		    		val += Integer.parseInt(first + first);
		    	}
		    	
		    	first = null;
		    	last = null;
		    	firstIdx = Integer.MAX_VALUE;
		    	lastIdx = 0;
		        
		        line = br.readLine();
		    }

		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		return val;
	}
}
