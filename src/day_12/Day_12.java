package day_12;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * Lösung für Tag 12.
 * 
 * @author Yanik Recke
 */
public class Day_12 {

	public static void main(String[] args) {
		String pathToInput = "src/day_12/input.txt";
		
		System.out.print(part1(pathToInput) + " - ");
		System.err.println(part2(pathToInput));
	} 
	
	
	/**
	 * Jede mögliche Kombination rekursiv bruteforcen.
	 * 
	 * @param path - Pfad zum Input
	 * @return
	 */
	private static long part1(String path) {
		List<String> input = helpers.HelperMethods.getInputAsListOfString(path);
		long result = 0;
		for (String line : input) {
			String row = line.split(" ")[0];
			String instr = line.split(" ")[1];
			List<Integer> nums = new ArrayList<>(Arrays.stream(instr.split(",")).map(x -> Integer.parseInt(x)).collect(Collectors.toList()));
			result += check(0, new String(row), nums);
			
		}
		
		return result;
	}
	
	
	/**
	 * Hilfsmethode zum bruteforcen von p1.
	 * -> String startet mit '.', bei substring(1) weitermachen
	 * -> String startet mit '?', zunächst mit '.' ersetzen, dann mit '#'
	 * -> String startet mit '#', prüfen, ob lang genug um nächste Anzahl zu erfüllen, sonst verwerfen
	 * -> String leer? Prüfen ob /nums/ auch leer, wenn ja +1, sonst +0
	 * -> /nums/ leer? Prüfen, ob String noch '#' enthält, wenn nicht +1, sonst +0
	 * 
	 * @param count - Zwischenanzahl
	 * @param curr - Aktuell zu verarbeitender String
	 * @param nums - Gruppen
	 * @return
	 */
	private static long check(long count, String curr, List<Integer> nums) {
		
		if (curr.charAt(0) == '.') {
			if (curr.length() == 1) {
				return count;
			} else {				
				return check(count, curr.substring(1), new ArrayList<>(nums));
			}
		} else if (curr.charAt(0) == '?'){
			char[] next = curr.toCharArray();
			
			next[0] = '.';
			count = check(count, new String(next), new ArrayList<>(nums));
			
			next[0] = '#';
			count = check(count, new String(next), new ArrayList<>(nums));
		} else {
			int numHash = 0;
			for (int i = 0; i < curr.length() && curr.charAt(i) != '.' ; i++) {
				numHash++;
			}
			
			int len = nums.get(0);
			
			if (numHash >= len) {
				nums.remove(0);
				
				if (nums.isEmpty()) {
					if (len == curr.length()) {
						return ++count;							
					} else {
						return curr.substring(len).contains("#") ? count : ++count;
					}
				} else {
					if (curr.length() - 1 > len && curr.charAt(len) != '#') {
						if (curr.charAt(len) == '?') {
							char[] temp = curr.toCharArray();
							temp[len] = '.';
							return check(count, new String(temp).substring(len), new ArrayList<>(nums));
						} else {							
							return check(count, curr.substring(len), new ArrayList<>(nums));
						}
					} else {
						return count;
					}
				}
			} else {
				return count;
			}
		}
		
		return count;
	}
	
	
	/**
	 * Jede mögliche Kombination rekursiv bruteforcen.
	 * 
	 * @param path - Pfad zum Input
	 * @return
	 */
	private static long part2(String path) {
		List<String> input = helpers.HelperMethods.getInputAsListOfString(path);
		long result = 0;
		// to memorize past results
		Map<String, Long> map = new HashMap<>();
		
		for (String line : input) {
			String orgInstr = line.split(" ")[1]; 
			String orgRow = line.split(" ")[0];;
			
			String row = orgRow;
			String instr = orgInstr;
			
			for (int i = 0; i < 4; i++) {
				row = row.concat("?" + orgRow);
				instr = instr.concat("," + orgInstr);
			}

			
			List<Integer> nums = new ArrayList<>(Arrays.stream(instr.split(",")).map(x -> Integer.parseInt(x)).collect(Collectors.toList()));
			result += check2(0, new String(row), nums, map);
			
		}
		
		return result;
	}
	
	
	/**
	 * Hilfsmethode zum bruteforcen von p2.
	 * -> String startet mit '.', bei substring(1) weitermachen
	 * -> String startet mit '?', zunächst mit '.' ersetzen, dann mit '#'
	 * -> String startet mit '#', prüfen, ob lang genug um nächste Anzahl zu erfüllen, sonst verwerfen
	 * -> String leer? Prüfen ob /nums/ auch leer, wenn ja +1, sonst +0
	 * -> /nums/ leer? Prüfen, ob String noch '#' enthält, wenn nicht +1, sonst +0
	 * 
	 * Zwischenspeichern der Ergebnisse einzelner Strings und bei jeder Berechnung prüfen,
	 * ob ein gleicher String in Kombination mit Gruppen schon verarbeitet wurde.
	 * 
	 * @param count - Zwischenanzahl
	 * @param curr - Aktuell zu verarbeitender String
	 * @param nums - Gruppen
	 * @return
	 */
	private static long check2(long count, String curr, List<Integer> nums, Map<String, Long> cache) {
		
		if (cache.containsKey(curr + "-" + nums)) {
			return cache.get(curr + "-" + nums);
		}
		
		long localCount = 0;
		if (curr.charAt(0) == '.') {
			if (curr.length() == 1) {
				cache.put(curr + "-" + nums, 0L);
				return localCount;
			} else {
				localCount = check2(count, curr.substring(1), new ArrayList<>(nums), cache);
				return localCount;
			}
		} else if (curr.charAt(0) == '?'){
			char[] next = curr.toCharArray();
			
			// replace '?' with '.'
			next[0] = '.';
			localCount += check2(count, new String(next), new ArrayList<>(nums), cache);
			
			// replace '?' with '#'
			next[0] = '#';
			localCount += check2(count, new String(next), new ArrayList<>(nums), cache);
			
			cache.put(curr + "-" + nums, localCount);
			
			return localCount;
		} else {
			int numHash = 0;

			// count appearences of != '.' into numHash
			for (int i = 0; i < curr.length() && curr.charAt(i) != '.' ; i++) {
				numHash++;
			}
			
			int len = nums.get(0);
			
			if (numHash >= len) {
				List<Integer> copy = new ArrayList<>(nums);
				
				nums.remove(0);
				// all groups done, check if string processed, if not check for remaining '#'
				if (nums.isEmpty()) {
					if (len == curr.length()) {
						return ++localCount;							
					} else {
						if (curr.substring(len).contains("#")) {
							cache.put(curr + "-" + copy, 0L);
							return localCount;
						} else {
							return ++localCount;
						}
					}
				} else {
					// check if continuing to look would cause out of bounds
					if (curr.length() - 1 > len && curr.charAt(len) != '#') {
						// if next char == '?' replace with '.' because after group only '.' can follow
						if (curr.charAt(len) == '?') {
							char[] temp = curr.toCharArray();
							temp[len] = '.';
							localCount += check2(count, new String(temp).substring(len), new ArrayList<>(nums), cache);
							cache.put(curr + "-" + copy, localCount);
							return localCount;
						// keep checking
						} else {							
							localCount += check2(count, curr.substring(len), new ArrayList<>(nums), cache);
							cache.put(curr + "-" + copy, localCount);
							return localCount;
						}
					} else {
						cache.put(curr + "-" + copy, 0L);
						return localCount;
					}
				}
			} else {
				cache.put(curr + "-" + nums, 0L);
				return localCount;
			}
		}
	}

}
