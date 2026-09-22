package day_11;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Lösung für Tag 11.
 * 
 * @author Yanik Recke
 */
public class Day_11 {

	public static void main(String[] args) {
		String pathToInput = "src/day_11/input.txt";
		
		System.out.print(part1(pathToInput) + " - ");
		System.out.println(part2(pathToInput));
	}
	
	
	/**
	 * Hilfsklasse, repräsentiert ein Paar von Positionen.
	 */
	public static class Pair {
		Position first;
		Position snd;
		
		
		/**
		 * Erzeugt Paar.
		 * 
		 * @param f - Position 1
		 * @param s - Position 2
		 */
		public Pair (Position f, Position s) {
			this.first = f;
			this.snd = s;
		}
		
		@Override
		public boolean equals(Object obj) {
			return equalsHelp((Pair) obj);
		}
		
		/**
		 * Hilfsmethode für equals(Object)
		 * 
		 * @param p - Zu vergleichendes Paar
		 * @return - true, wenn gleich, false, wenn nicht
		 */
		private boolean equalsHelp(Pair p) {
			return (p.first.equals(this.first) && p.snd.equals(this.snd)) || (p.first.equals(this.snd) && p.snd.equals(this.first)); 
		}
		
	}
	
	
	/**
	 * Summe der kürzesten Pfade berechnen.
	 * 
	 * @param path - Pfad zum Input
	 * @return
	 */
	private static long part1(String path) {
		List<String> input = helpers.HelperMethods.getInputAsListOfString(path);
		Set<Integer> emptyRows = new HashSet<>();
		Set<Integer> emptyCols = new HashSet<>();
		
		// find all empty columns and rows
		for (int i = 0; i < input.size(); i++) {
			if (input.get(i).indexOf("#") == -1) {
				emptyRows.add(i);
			}
		}
		
		boolean isEmpty = true;
		for (int x = 0; x < input.get(0).length(); x++) {
			isEmpty = true;
			for (int y = 0; y < input.size() && isEmpty; y++) {
				if (input.get(y).charAt(x) == '#') {
					isEmpty = false;
				}
			}
			
			if (isEmpty) {
				emptyCols.add(x);
			}
		}

		char[][] field = new char[input.size()][input.get(0).length()];
		
		Set<Position> set =  new HashSet<>();
		
		for (int x = 0; x < field.length; x++) {
			for (int y = 0; y < field[x].length; y++) {
				char c = input.get(x).charAt(y);
				if (c == '#') {
					set.add(new Position(x, y));
				}
			}
		}
		
		List<Pair> pairsTemp = new ArrayList<>();
		
		for (Position source : set) {
			for (Position goal : set) {
				if (!source.equals(goal) && !pairsTemp.contains(new Pair(source, goal))) {
					pairsTemp.add(new Pair(source, goal));
				}
			}
		}

		long sum = 0;
		for (Pair p : pairsTemp) {
			
			Position first = p.first;
			Position snd = p.snd;
			long distX = 0;
			long distY = 0;
			
			long addRows = calcRowsToAdd(first.getX(), snd.getX(), emptyRows, false);
			long addCols = calcColsToAdd(first.getY(), snd.getY(), emptyCols, false);
			
			if (first.getX() > snd.getX()) {
				distX = first.getX() - snd.getX() + addRows;
			} else {
				distX = snd.getX() - first.getX() + addRows;
			}
			
			if (first.getY() > snd.getY()) {
				distY = first.getY() - snd.getY() + addCols;
			} else {
				distY = snd.getY() - first.getY() + addCols;
			}
			sum = sum + (distX + distY);
		}
		
		// 9817200 too high, 9.817.202 too high, 9.817.624 too hgih, 9.816.972, 9.819.146, 9.819.146, 9.805.264, 9.416.523
		return sum;
	}
	
	
	/**
	 * Hilfsmethode zum Berechnen wie viele Reihen hinzugefügt
	 * werden müssen.
	 * 
	 * @param y1 - Koordinate 1
	 * @param y2 - Koordinate 2
	 * @param empty - Menge mit x-Koordinaten von leeren Reihen
	 * @param p2 - Ob Berechnung für Part 2 oder nicht
	 * @return - Anzahl an leeren Reihen
	 */
	private static long calcRowsToAdd(int y1, int y2, Set<Integer> empty, boolean p2) {
		int rowsToAdd = 0;
		if (y1 > y2) {
			int temp = y2;
			y2 = y1;
			y1 = temp;
		}
		
		for (Integer i : empty) {
			if (y1 < i && y2 > i) {
				rowsToAdd++;
			}
		}
		
		return p2 ? rowsToAdd * 999999 : rowsToAdd;
	}
	
	
	/**
	 * Hilfsmethode zum Berechnen wie viele Spalten hinzugefügt
	 * werden müssen.
	 * 
	 * @param x1 - Koordinate 1
	 * @param x2 - Koordinate 2
	 * @param empty - Menge mit y-Koordinaten von leeren Spalten
	 * @param p2 - Ob Berechnung für Part 2 oder nicht
	 * @return - Anzahl an leeren Spalten
	 */
	private static long calcColsToAdd(int x1, int x2, Set<Integer> empty, boolean p2) {
		int colsToAdd = 0;
		if (x1 > x2) {
			int temp = x2;
			x2 = x1;
			x1 = temp;
		}
		
		for (Integer i : empty) {
			if (x1 < i && x2 > i) {
				colsToAdd++;
			}
		}
		
		return p2 ? colsToAdd * 999999 : colsToAdd;
	}
	
	
	/**
	 * Genau das gleiche nur mit p2 = true.
	 * 
	 * @param path - Pfad zum Input
	 * @return
	 */
	private static long part2(String path) {
		List<String> input = helpers.HelperMethods.getInputAsListOfString(path);
		Set<Integer> emptyRows = new HashSet<>();
		Set<Integer> emptyCols = new HashSet<>();
		
		for (int i = 0; i < input.size(); i++) {
			if (input.get(i).indexOf("#") == -1) {
				emptyRows.add(i);
			}
		}
		
		boolean isEmpty = true;
		for (int x = 0; x < input.get(0).length(); x++) {
			isEmpty = true;
			for (int y = 0; y < input.size() && isEmpty; y++) {
				if (input.get(y).charAt(x) == '#') {
					isEmpty = false;
				}
			}
			
			if (isEmpty) {
				emptyCols.add(x);
			}
		}

		char[][] field = new char[input.size()][input.get(0).length()];
		
		Set<Position> set =  new HashSet<>();
		
		for (int x = 0; x < field.length; x++) {
			for (int y = 0; y < field[x].length; y++) {
				char c = input.get(x).charAt(y);
				if (c == '#') {
					set.add(new Position(x, y));
				}
			}
		}		
		
		List<Pair> pairsTemp = new ArrayList<>();
		
		for (Position source : set) {
			for (Position goal : set) {
				if (!source.equals(goal) && !pairsTemp.contains(new Pair(source, goal))) {
					pairsTemp.add(new Pair(source, goal));
				}
			}
		}

		long sum = 0;
		for (Pair p : pairsTemp) {
			
			Position first = p.first;
			Position snd = p.snd;
			long distX = 0;
			long distY = 0;
			
			long addRows = calcRowsToAdd(first.getX(), snd.getX(), emptyRows, true);
			long addCols = calcColsToAdd(first.getY(), snd.getY(), emptyCols, true);
			
			if (first.getX() > snd.getX()) {
				distX = first.getX() - snd.getX() + addRows;
			} else {
				distX = snd.getX() - first.getX() + addRows;
			}
			
			if (first.getY() > snd.getY()) {
				distY = first.getY() - snd.getY() + addCols;
			} else {
				distY = snd.getY() - first.getY() + addCols;
			}
			sum = sum + (distX + distY);
		}
		
		return sum;
	}
}
