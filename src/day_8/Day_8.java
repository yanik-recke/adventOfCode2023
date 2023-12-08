package day_8;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Lösung für Tag 8.
 * 
 * @author Yanik Recke
 */
public class Day_8 {

	public static void main(String[] args) {
		String pathToInput = "src/day_8/input.txt";
		
		System.out.print(part1(pathToInput) + " - ");
		System.out.println(part2(pathToInput));
	}
	
	
	/**
	 * Hilfsklasse, repräsentiert ein Paar.
	 */
	public static class Pair {
		String left;
		String right;
		
		Pair(String l, String r) {
			this.left = l;
			this.right = r;
		}
	}
	
	
	/**
	 * Start bei AAA und Ablaufen der Instructions.
	 * 
	 * @param path - Pfad zum Input
	 * @return
	 */
	private static int part1(String path) {
		List<String> input = helpers.HelperMethods.getInputAsListOfString(path);
		String instr = input.remove(0);
		input.remove(0);
		
		Map<String, Pair> map = new HashMap<>();
		
		for (String line : input) {
			String left = line.split("=")[1].split(",")[0].substring(2);
			String right = line.split("=")[1].split(",")[1].trim().substring(0, 3);
			map.put(line.split("=")[0].trim(), new Pair(left, right));
		}
		
		int step = 0;
		boolean done = false;
		char curr = instr.charAt(0);
		int idx = 0;
		String currPos = "AAA";
		while (!done) {
			Pair p = map.get(currPos);
			if (curr == 'L') {
				currPos = p.left;
			} else {
				currPos = p.right;
			}
			
			step++;
			
			if ("ZZZ".equals(currPos)) {
				done = true;
			} else {
				if (idx < instr.length() - 1) {
					idx++;
				} else {
					idx = 0;
				}
				curr = instr.charAt(idx);
			}
		}
		
		return step;
	}

	
	/**
	 * Jeden Pfad ablaufen, bis Zyklus erkannt, dann
	 * für jeden Pfad das erste Aufkommen von "..Z" finden
	 * und von den Indexen KGV berechnen.
	 * 
	 * @param path - Pfad zum Input
	 * @return
	 */
	private static long part2(String path) {
		List<String> input = helpers.HelperMethods.getInputAsListOfString(path);
		String instr = input.remove(0);
		input.remove(0);
		
		Set<String> positions = new HashSet<>();
		Map<String, Pair> map = new HashMap<>();
		
		for (String line : input) {
			String left = line.split("=")[1].split(",")[0].substring(2);
			String right = line.split("=")[1].split(",")[1].trim().substring(0, 3);
			String pos = line.split("=")[0].trim();
			map.put(pos, new Pair(left, right));
			
			if (pos.charAt(2) == 'A') {
				positions.add(pos);
			}
		}
		
		boolean done = false;
		char curr = instr.charAt(0);
		int idx = 0;
		
		List<List<String>> paths = new ArrayList<>();
		
		int pathIdx = -1;
		
		for (String pos : positions) {
			String currPos = pos;
			
			pathIdx++;
			paths.add(new ArrayList<>());
			paths.get(pathIdx).add(pos + "," + idx);
			
			done = false;
			idx = 0;
			curr = instr.charAt(0);
			
			while (!done) {
				Pair p = map.get(currPos);
				if (curr == 'L') {
					currPos = p.left;
				} else {
					currPos = p.right;
				}
				
				if (paths.get(pathIdx).contains(currPos + "," + idx)) {
					done = true;
				} else {
					paths.get(pathIdx).add(currPos + "," + idx);
				}
				
				
				if (idx < instr.length() - 1) {
					idx++;
				} else {
					idx = 0;
				}
				
				curr = instr.charAt(idx);
			}
		}
		
		List<Integer> list = new ArrayList<Integer>();
		for (List<String> l : paths) {
			done = false;
			for (int i = 0; i < l.size() && !done; i++) {
				if (l.get(i).charAt(2) == 'Z') {
					done = true;
					list.add(i);
				}
			}
		}
		
	    long result = list.get(0);
	    
	    for(int i = 1; i < list.size(); i++)  {
	    	result = lcm(result, list.get(i));
	    }
	    
	    return result;
	}
	
	
	/**
	 * Hilfsmethode zum Berechnen des größten
	 * gemeinsamen Nenners zweier Zahlen.
	 * 
	 * @param a - Zahl a
	 * @param b - Zahl b
	 * @return - GCD
	 */
	private static long gcd(long a, long b)
	{
	    while (b > 0)
	    {
	        long temp = b;
	        b = a % b; // % is remainder
	        a = temp;
	    }
	    return a;
	}
	
	
	/**
	 * Hilfsmethode zum Berechnen des kleinsten gemeinsamen
	 * Vielfachen zweier Zahlen.
	 * 
	 * @param a - Zahl 1
	 * @param b - Zahl 2
	 * @return - LCM
	 */
	private static long lcm(long a, long b)
	{
	    return a * (b / gcd(a, b));
	}

}
