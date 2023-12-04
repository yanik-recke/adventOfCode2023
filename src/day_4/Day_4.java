package day_4;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Lösung für Tag 4.
 * 
 * @author Yanik Recke
 */
public class Day_4 {

	
	public static void main(String[] args) {
		String pathToInput = "src/day_4/input.txt";
		System.out.print(part1(pathToInput) + " - ");
		System.out.println(part2(pathToInput));
	}
	
	
	/**
	 * Parsen der "Winning numbers" und "Numbers you have" und
	 * dann zählen wie viele gleich sind. Ab ersten gefundenem Score 
	 * verdoppeln.
	 * 
	 * @param path - Pfad zum Input
	 * @return
	 */
	private static int part1(String path) {
		List<String> input = helpers.HelperMethods.getInputAsListOfString(path);
		int sum = 0;
		for (String line : input) {
			String[] numbers = line.split(":")[1].replace("|", "@").split("@");

			String winningString = numbers[0];
			String havingString = numbers[1];
			
			Set<String> winners = new HashSet<>(Arrays.asList(winningString.split(" ")));
			Set<String> havers = new HashSet<>(Arrays.asList(havingString.split(" ")));

			int count = 0;
			for (String winner : winners) {
				if (havers.contains(winner) && !winner.equals("")) {
					if (count == 0) {
						count++;						
					} else {
						count = count * 2;
					}
				}
			}
			
			 sum += count;
		}
		
		return sum;
	}
	
	
	/**
	 * Hilfsklasse um zu speichern wie viele
	 * Winning Numbers eine Card hat und wie viele
	 * man von dieser Karte schon hat.
	 */
	public static class Card {
		public int number;
		public int winning;
		
		public Card(int number, int winning) {
			this.number = number;
			this.winning = winning;
		}
	}
	
	
	/**
	 * Für jede Karte die man hat prüfen, wie viele
	 * neue Karten durch sie gewonnen werden und entsprechend
	 * in Map hochzählen. So weiter bis für alle Karten
	 * gemacht.
	 * 
	 * @param path - Pfad zum Input
	 * @return - Anzahl der Karten am Ende (inkl. Kopien)
	 */
	private static int part2(String path) {
		List<String> input = helpers.HelperMethods.getInputAsListOfString(path);
		Map<Integer, Card> map = new LinkedHashMap<>();
		
		int sum = 0;
		int currCard = 1;
		
		for (String line : input) {
			String[] numbers = line.split(":")[1].replace("|", "@").split("@");

			String winningString = numbers[0];
			String havingString = numbers[1];
			
			Set<String> winners = new HashSet<>(Arrays.asList(winningString.split(" ")));
			Set<String> havers = new HashSet<>(Arrays.asList(havingString.split(" ")));
			
			int count = 0;
			for (String winner : winners) {
				
				if (havers.contains(winner) && !winner.equals("")) {
					count++;
				}
			}
			
			map.put(currCard, new Card(1, count));
			currCard++;
		}
		
		for (Integer key : map.keySet()) {
			int amount = 0;
			while (amount < map.get(key).number) {
				int count = 0;
				int tempKey = key;
				while (count < map.get(key).winning) {
					map.get(++tempKey).number++;
					count++;
				}
				amount++;
			}
		}
		
		for (Card num : map.values()) {
			sum += num.number;
		}
		
		return sum;
	}
	
}
