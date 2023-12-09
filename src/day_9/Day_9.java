package day_9;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Lösung für Tag 9.
 * 
 * @author Yanik Recke
 */
public class Day_9 {
	
	public static void main(String[] args) {
		String pathToInput = "src/day_9/input.txt";
		
		System.out.print(part1(pathToInput) + " - ");
		System.out.println(part2(pathToInput));
	} 
	
	
	/**
	 * Von jedem Schritt die letzte Zahl merken
	 * und dann für jeden Schritt die letzte Zahl zuaddieren, bis
	 * alle Zahlen = 0 sind.
	 * 
	 * @param path - Pfad zum Input
	 * @return
	 */
	private static long part1(String path) {
		List<String> input = helpers.HelperMethods.getInputAsListOfString(path);
		List<Integer> lastNumbers = new ArrayList<>();
		
		int res = 0;
		
		for (String line : input) {
			String[] tempS = line.trim().split(" ");
			
			List<Integer> numbers = Arrays.asList(tempS).stream().mapToInt(Integer::parseInt).boxed().collect(Collectors.toList());
			
			res = 0;
			res += numbers.get(numbers.size() - 1);
			
			boolean done = false;
			
			while (!done) {
				done = true;
				List<Integer> newNumbers = new ArrayList<>();
				for (int i = 0; i < numbers.size() - 1; i++) {
					int next = numbers.get(i + 1) - numbers.get(i);
					newNumbers.add(next);
					
					if (done && next != 0) {
						done = false;
					}
				}
				
				res += newNumbers.get(newNumbers.size() - 1);
				numbers = newNumbers;
			}
			
			lastNumbers.add(res);
		}
		
		int sum = 0;
		for (int i : lastNumbers) {
			sum += i;
		}
		
		return sum;
	}
	
	
	/**
	 * Vorher Liste umdrehen.
	 * 
	 * @param path - Pfad zum Input
	 * @return
	 */
	private static long part2(String path) {
		List<String> input = helpers.HelperMethods.getInputAsListOfString(path);
		List<Integer> lastNumbers = new ArrayList<>();
		
		int res = 0;
		
		for (String line : input) {
			String[] tempS = line.trim().split(" ");
			
			List<Integer> numbers = Arrays.asList(tempS).stream().mapToInt(Integer::parseInt).boxed().collect(Collectors.toList());
			Collections.reverse(numbers.subList(0, numbers.size()));
			
			res = 0;
			res += numbers.get(numbers.size() - 1);
			
			boolean done = false;
			
			while (!done) {
				done = true;
				List<Integer> newNumbers = new ArrayList<>();
				for (int i = 0; i < numbers.size() - 1; i++) {
					int next = numbers.get(i + 1) - numbers.get(i);
					newNumbers.add(next);
					
					if (done && next != 0) {
						done = false;
					}
				}
				
				res += newNumbers.get(newNumbers.size() - 1);
				numbers = newNumbers;
			}
			
			lastNumbers.add(res);
		}
		
		int sum = 0;
		for (int i : lastNumbers) {
			sum += i;
		}
		
		return sum;
	}

}
