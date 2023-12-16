package day_15;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Lösung für Tag 15.
 * 
 * @author Yanik Recke
 */
public class Day_15 {

	public static void main(String[] args) {
		String pathToInput = "src/day_15/input.txt";
		
		System.err.print(part1(pathToInput));
		System.out.print(" - ");
		System.err.println(part2(pathToInput));
	}
	
	
	private static long part1(String path) {
		String line = null;
		try (BufferedReader br = new BufferedReader(new FileReader(path))) {
		    line = br.readLine();

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		String[] input = line.split(",");
		long val = 0;
		long result = 0;
		for (String step : input) {
			for (int i = 0; i < step.length(); i++) {
				val += (int) step.charAt(i);
				val *= 17;
				val %= 256;
			}
			
			result += val;
			val = 0;
		}
		
		return result;
	}
	
	
	public static class Lens {
		String label;
		char op;
		Integer num;
		
		public Lens(String l, char o, Integer n) {
			this.label = l;
			this.op = o;
			this.num = n;
		}
		
		@Override
		public boolean equals(Object obj) {
			return ((Lens) obj).label.equals(this.label);
		}
		
		@Override
			public String toString() {
				return label;
			}
	}
	
	private static long part2(String path) {
		String line = null;
		
		try (BufferedReader br = new BufferedReader(new FileReader(path))) {
		    line = br.readLine();

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// boxen erstellen
		List<List<Lens>> boxes = new ArrayList<>();
		for (int i = 0; i < 256; i++) {
			boxes.add(new ArrayList<>());
		}
		
		String[] input = line.split(",");

		Lens curr;
		int boxNum;
		for (String step : input) {
			if (step.contains("=")) {
				curr = new Lens(step.split("=")[0], '=', Integer.parseInt(step.split("=")[1]));
				boxNum = hash(curr.label);
				int idx;
				if ((idx = boxes.get(boxNum).indexOf(curr)) != -1) {
					boxes.get(boxNum).get(idx).num = curr.num;
				} else {
					boxes.get(boxNum).add(curr);
				}
			} else {
				curr = new Lens(step.split("-")[0], '-', null);
				boxNum = hash(curr.label);
				int idx;
				idx = boxes.get(boxNum).indexOf(curr);
				if ((idx = boxes.get(boxNum).indexOf(curr)) != -1) {
					boxes.get(boxNum).remove(idx);
				}
			}
			
		}
		
		long result = 0;
		int temp = 0;
		for (int i = 0; i < boxes.size(); i++) {
			for (int j = 0; j < boxes.get(i).size(); j++) {
				temp = (i + 1) * (j + 1) * boxes.get(i).get(j).num;
				result += temp;
			}
		}
		
		
		// too high 8787660
		return result;
	}
	
	
	private static int hash(String s) {
		int val = 0;
		
		for (int i = 0; i < s.length(); i++) {
			val += (int) s.charAt(i);
			val *= 17;
			val %= 256;
		}
		
		return val;
	}
	
}
