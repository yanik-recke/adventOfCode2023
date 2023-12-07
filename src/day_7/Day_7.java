package day_7;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;


/**
 * Lösung für Tag 7.
 * 
 * @author Yanik Recke
 */
public class Day_7 {

	public static void main(String[] args) {
		String pathToInput = "src/day_7/input.txt";
		
		System.out.print(part1(pathToInput) + " - ");
		System.out.println(part2(pathToInput));
	} 
	
	
	/**
	 * Comparator der anhand der Vorgaben aus der Aufgabe
	 * die Hände vergleicht. (Part 1)
	 */
	public static class Comp implements Comparator<String> {

		@Override
		public int compare(String arg0, String arg1) {
		        for (int i = 0; i < arg0.length(); i++) {
		            char char1 = arg0.charAt(i);
		            int val = getVal(char1);
		            char char2 = arg1.charAt(i);
		            int val2 = getVal(char2);

		            if (char1 != char2) {
		                return Integer.compare(val2, val);
		            }
		        }

		        // If the common prefix is the same, then shorter string should come first
		        return Integer.compare(arg0.length(), arg1.length());
		}
		
	}
	
	
	/**
	 * Pro Hand eine Map mit Chars als key und der
	 * Anzahl der Vorkommen als value. Dann Fallunterscheidung.
	 * 
	 * @param path - Pfad zum Puzzle Input
	 * @return
	 */
	private static long part1(String path) {
		
		List<String> input = helpers.HelperMethods.getInputAsListOfString(path);
		List<Integer> ranked = new ArrayList<Integer>();
		Map<Integer, TreeMap<String, Integer>> ordered = new HashMap<>();
		// 5
		ordered.put(1, new TreeMap<>(new Comp()));
		// 4
		ordered.put(2, new TreeMap<>(new Comp()));
		// full house
		ordered.put(3, new TreeMap<>(new Comp()));
		// triple
		ordered.put(4, new TreeMap<>(new Comp()));
		// two pair
		ordered.put(5, new TreeMap<>(new Comp()));
		// one pair
		ordered.put(6, new TreeMap<>(new Comp()));
		// unique
		ordered.put(7, new TreeMap<>(new Comp()));
		
		
		
		for (String line : input) {
			Map<Character, Integer> map = new HashMap<>();
			
			String hand = line.split(" ")[0];
			int value = Integer.parseInt(line.split(" ")[1]);
			int count = 0;
			boolean done = false;
			int val = 0;
			
			for (int i = 0; i < hand.length(); i++) {
				val += getVal(hand.charAt(i));
			}
			
			for (int i = 0; i < hand.length(); i++) {
				char c = hand.charAt(i);
				
				if (map.containsKey(c)) {
					map.put(c, map.get(c) + 1);
				} else {
					map.put(c, 1);
				}
			}
			
			if (map.keySet().size() == 1) {
				ordered.get(1).put(hand, value);
			} else if (map.keySet().size() == 2) {
				for (Character c : map.keySet()) {
					if (map.get(c) == 1 || map.get(c) == 4) {
						ordered.get(2).put(hand, value);
					} else {
						ordered.get(3).put(hand, value);
					}
					
					break;
				}
			} else if (map.keySet().size() == 5) {
				ordered.get(7).put(hand, value);
			} else {
				boolean found = false;
				
				// triple
				for (Character c : map.keySet()) {
					if (map.get(c) == 3) {
						ordered.get(4).put(hand, value);
						found = true;
					}
				}
				
				// check for two pair
				if (!found) {
					int num = 0;
					for (Character c : map.keySet()) {
						if (map.get(c) == 2) {
							num++;
						}
					}
					
					if (num == 2) {
						ordered.get(5).put(hand, value);
					} else if (num == 1) {
						ordered.get(6).put(hand, value);
					}
				}
			}
			
		}
		
		for (Integer i : ordered.keySet()) {
			TreeMap<String, Integer> t = ordered.get(i);
			ranked.addAll(t.values());
		}
		
		
		Collections.reverse(ranked);
		long res = 0;
		int idx = 1;
		for (Integer i : ranked) {
			res += i * idx;
			idx++;
		}
		
		return res;
	}
	
	
	/**
	 * Gibt entsprechend den Wert der "Karte"
	 * wieder. (Part 1)
	 * 
	 * @param c - Karte als char
	 * @return
	 */
	private static int getVal(char c) {
		if (Character.isDigit(c)) {
			return Character.getNumericValue(c);
		}
		
		switch (c) {
		case 'A': return 14;
		case 'K': return 13;
		case 'Q': return 12;
		case 'J': return 11;
		default: return 10;
		}
	}
	
	
	/**
	 * Comparator der anhand der Vorgaben aus der Aufgabe
	 * die Hände vergleicht. (Part 2)
	 */
	public static class Comp2 implements Comparator<String> {

		@Override
		public int compare(String arg0, String arg1) {
		        for (int i = 0; i < arg0.length(); i++) {
		            char char1 = arg0.charAt(i);
		            int val = getVal2(char1);
		            char char2 = arg1.charAt(i);
		            int val2 = getVal2(char2);

		            if (char1 != char2) {
		                return Integer.compare(val2, val);
		            }
		        }

		        // If the common prefix is the same, then shorter string should come first
		        return Integer.compare(arg0.length(), arg1.length());
		}
		
	}
	
	
	/**
	 * Gleiches Konzept wie in Part 1, aber sobald
	 * ein 'J' in der Hand vorkommt wird die Anzahl der
	 * 'J' dem key mit dem größten Value hinzugefügt -> automatisch
	 * best mögliche Hand.
	 * 
	 * @param path - Pfad zum Input
	 * @return
	 */
	private static long part2(String path) {
		List<String> input = helpers.HelperMethods.getInputAsListOfString(path);
		List<Integer> ranked = new ArrayList<Integer>();
		Map<Integer, TreeMap<String, Integer>> ordered = new HashMap<>();
		// 5
		ordered.put(1, new TreeMap<>(new Comp2()));
		// 4
		ordered.put(2, new TreeMap<>(new Comp2()));
		// full house
		ordered.put(3, new TreeMap<>(new Comp2()));
		// triple
		ordered.put(4, new TreeMap<>(new Comp2()));
		// two pair
		ordered.put(5, new TreeMap<>(new Comp2()));
		// one pair
		ordered.put(6, new TreeMap<>(new Comp2()));
		// unique
		ordered.put(7, new TreeMap<>(new Comp2()));
		
		
		
		for (String line : input) {
			Map<Character, Integer> map = new HashMap<>();
			
			String hand = line.split(" ")[0];
			int value = Integer.parseInt(line.split(" ")[1]);
			
			for (int i = 0; i < hand.length(); i++) {
				char c = hand.charAt(i);
				
				if (map.containsKey(c)) {
					map.put(c, map.get(c) + 1);
				} else {
					map.put(c, 1);
				}
			}
			
			Character highest = null;
			int val = 0;
			if (map.containsKey('J')) {
				for (char k : map.keySet()) {
					if (highest == null && k != 'J') {
						highest = k;
						val = map.get(highest);
					} else if (map.get(k) > val && k != 'J') {
						highest = k;
						val = map.get(highest);
					}
				}
				
				if (highest != null) {
					map.put(highest, map.get('J') + map.get(highest));
					map.remove('J');
				}
			}
			
			
			if (map.keySet().size() == 1) {
				ordered.get(1).put(hand, value);
			} else if (map.keySet().size() == 2) {
				for (Character c : map.keySet()) {
					if (map.get(c) == 1 || map.get(c) == 4) {
						ordered.get(2).put(hand, value);
					} else {
						ordered.get(3).put(hand, value);
					}
					
					break;
				}
			} else if (map.keySet().size() == 5) {
				ordered.get(7).put(hand, value);
			} else {
				boolean found = false;
				
				// triple
				for (Character c : map.keySet()) {
					if (map.get(c) == 3) {
						ordered.get(4).put(hand, value);
						found = true;
					}
				}
				
				// check for two pair
				if (!found) {
					int num = 0;
					for (Character c : map.keySet()) {
						if (map.get(c) == 2) {
							num++;
						}
					}
					
					if (num == 2) {
						ordered.get(5).put(hand, value);
					} else if (num == 1) {
						ordered.get(6).put(hand, value);
					}
				}
			}
			
		}
		
		for (Integer i : ordered.keySet()) {
			TreeMap<String, Integer> t = ordered.get(i);
			List<Integer> l = new ArrayList<>(t.values());
			ranked.addAll(l);
		}
		
		
		Collections.reverse(ranked);
		long res = 0;
		int idx = 1;
		for (Integer i : ranked) {
			res += i * idx;
			idx++;
		}
		
		return res;
	}

	/**
	 * Gibt entsprechend den Wert der "Karte"
	 * wieder. (Part 2 -> 'J' jetzt am wenigsten Wert)
	 * 
	 * @param c - Karte als char
	 * @return
	 */
	private static int getVal2(char c) {
		if (Character.isDigit(c)) {
			return Character.getNumericValue(c);
		}
		
		switch (c) {
		case 'A': return 14;
		case 'K': return 13;
		case 'Q': return 12;
		case 'J': return 1;
		default: return 10;
		}
	}
}
