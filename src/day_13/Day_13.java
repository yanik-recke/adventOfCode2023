package day_13;

import java.util.ArrayList;
import java.util.List;

/**
 * Lösung für Tag 13.
 * 
 * //TODO: needs cleaning up
 * 
 * @author Yanik Recke
 */
public class Day_13 {

	public static void main(String[] args) {
		String pathToInput = "src/day_13/input.txt";
		
		System.out.print(part1(pathToInput) + " - ");
		System.out.println(part2(pathToInput));
	}
	
	
	/**
	 * Repräsentiert ein Pattern.
	 */
	public static class Pattern {
		List<String> lines;
		
		
		/**
		 * Erstellt ein neues Pattern
		 * 
		 * @param l - Zeilen des Pattern
		 */
		public Pattern(List<String> l) {
			this.lines = l;
		}
		
		
		/**
		 * Transponiert das Pattern.
		 * 
		 * @return - transponiertes Pattern
		 */
		public Pattern transpose() {
			List<StringBuilder> transposedBuilder = new ArrayList<>();
			List<String> transposed = new ArrayList<>();
			
			for (int i = 0; i < this.lines.size(); i++) {
				for (int j = 0; j < this.lines.get(i).length(); j++) {
					if (transposedBuilder.size() == j) {
						transposedBuilder.add(new StringBuilder());
					}
					
					transposedBuilder.get(j).append(this.lines.get(i).charAt(j));
				}
			}
			
			for (StringBuilder sb : transposedBuilder) {
				transposed.add(sb.toString());
			}
			return new Pattern(transposed);
		}
	}
	
	
	/**
	 * Lösung zu Part 1. Parsen und dann mit
	 * Hilfsmethode.
	 * 
	 * @param path - Pfad zum Input
	 * @return
	 */
	private static long part1(String path) {
		List<String> input = helpers.HelperMethods.getInputAsListOfString(path);
		input.add("");

		long sum = 0;
		
		List<Pattern> patterns = new ArrayList<>();
		List<String> tempList = new ArrayList<>();
		
		for (String line : input) {
			if (!line.equals("")) {
				tempList.add(line);
			} else {
				patterns.add(new Pattern(tempList));
				tempList = new ArrayList<>();
			}
		}
		
		long temp = 0;
		for (Pattern p : patterns) {
			temp = calc(p) * 100;
			if (temp != 0) {
				sum += temp;
			} else {
				sum +=calc(p.transpose());
			}
		}
		
		return sum;
	}
	
	
	/**
	 * Sucht zwei gleiche Reihen und vergleicht
	 * dann mithilfe von zwei Indizes die entsprechenden Reihen.
	 * 
	 * @param p - Pattern
	 * @return
	 */
	private static long calc(Pattern p) {
		String prev = null;
		boolean possible = false;
		boolean done = false;
		
		for (int idx = 0; idx < p.lines.size(); idx++) {
			possible = false;
			done = false;
			String line = p.lines.get(idx);
			if (!line.equals("")) {
				if (line.equals(prev)) {
					int idxBack = idx - 1;
					int temp = idx;
					possible = true;

					while (possible && !done) {
						if (temp > p.lines.size() - 1 || idxBack < 0) {
							done = true;
						} else {
							if (!p.lines.get(temp++).equals(p.lines.get(idxBack--))) {
								possible = false;
								done = true;
							}
						}
					}

					if (possible) {
						return idx;
					}
				} else {
					prev = line;
				}
			} else {
				prev = null;
				done = false;
			}
		}

		return 0;
		
	}
	
	
	/**
	 * Lösung für Part 2. Erst parsen dann mit
	 * Hilfsmethode berechnen.
	 * 
	 * @param path - Pfad zum Input
	 * @return
	 */
	private static long part2(String path) {
		List<String> input = helpers.HelperMethods.getInputAsListOfString(path);
		input.add("");

		long sum = 0;
		
		List<Pattern> patterns = new ArrayList<>();
		List<String> tempList = new ArrayList<>();
		
		for (String line : input) {
			if (!line.equals("")) {
				tempList.add(line);
			} else {
				patterns.add(new Pattern(tempList));
				tempList = new ArrayList<>();
			}
		}
		
		long temp = 0;
		for (Pattern p : patterns) {
			temp = calc2(p) * 100;
			if (temp != 0) {
				sum += temp;
			} else {
				sum +=calc2(p.transpose());
			}
		}
		
		return sum;
	}
	
	
	/**
	 * Prüft, ob mit Veränderungen eines Zeichens
	 * eine Reihe so verändert werden kann, dass sie der
	 * mit der verglichen wird gleicht, wenn ja, dann wird
	 * das Pattern Objekt entsprechend verändert und 
	 * weiter verglichen.
	 * 
	 * @param p - Pattern
	 * @return
	 */
	private static long calc2(Pattern p) {
		String prev = null;
		boolean possible = false;
		boolean done = false;
		boolean joker = false;
		String one = null;
		String two = null;
		
		String memorize = null;
		String memorize2 = null;
		
		for (int idx = 0; idx < p.lines.size(); idx++) {
			memorize = null;
			memorize2 = null;
			int memIdx = 0;
			possible = false;
			joker = false;
			done = false;
			
			String line = p.lines.get(idx);
			if (!line.equals("")) {
				if (line.equals(prev)) {
					int idxBack = idx - 1;
					int temp = idx;
					possible = true;

					while (possible && !done) {
						if (temp > p.lines.size() - 1 || idxBack < 0) {
							done = true;
						} else {
							one = p.lines.get(temp++);
							two = p.lines.get(idxBack--);
							if (!one.equals(two) && (amountOfDifferentChars(one, two).size() > 1 || joker)) {								
								possible = false;
								done = true;
							} else if (amountOfDifferentChars(one, two).size() == 1) {
								if (joker) {
									possible = false;
								} else {									
									memorize = one;
									memorize2 = two;
									memIdx = idxBack + 1;
									joker = true;
								}
							} 
						}
					}

					if (possible) {
						if (joker) {
							int toChange = amountOfDifferentChars(memorize, memorize2).get(0);
							String temp2 = p.lines.remove(memIdx);
							char[] temp3 = temp2.toCharArray();
							temp3[toChange] = temp3[toChange] == '.' ? '#' : '.';
							p.lines.add(memIdx, new String(temp3));
							return idx;
						} 
					}
				} else {
					if (prev != null && amountOfDifferentChars(line, prev).size() == 1) {
						joker = true;
						int idxBack = idx - 2;
						int temp = idx + 1;
						int toChange = amountOfDifferentChars(line, prev).get(0);
						possible = true;

						while (possible && !done) {
							if (temp > p.lines.size() - 1 || idxBack < 0) {
								done = true;
							} else {
								one = p.lines.get(temp++);
								two = p.lines.get(idxBack--);
								if (!one.equals(two)) {								
									possible = false;
									done = true;
								}
							}
						}

						if (possible) {
							String temp2 = p.lines.remove(idx);
							char[] temp3 = temp2.toCharArray();
							temp3[toChange] = temp3[toChange] == '.' ? '#' : '.';
							p.lines.add(idx, new String(temp3));
							return idx;
						}
					}
				}
			}
			
			prev = line;
		}

		return 0;
		
	}
	
	/**
	 * Berechnet die Anzahl an unterschiedlichen Charakteren
	 * in zwei Strings.
	 * 
	 * @param str1 - String 1
	 * @param str2 - String 2
	 * @return - Liste mit den Indizes an denen sich unterschieden wird
	 */
    private static List<Integer> amountOfDifferentChars(String str1, String str2) {
    	List<Integer> idxs = new ArrayList<>();
        for (int i = 0; i < str1.length(); i++) {
            if (str1.charAt(i) != str2.charAt(i)) {
                idxs.add(i);
            }
        }

        return idxs;
    }
	
}
