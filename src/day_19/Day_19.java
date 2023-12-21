package day_19;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.function.Predicate;

/**
 * Lösung für Tag 19.
 * 
 * @author Yanik Recke
 */
public class Day_19 {

	public static void main(String[] args) {
		String pathToInput = "src/day_19/input.txt";
		
		System.out.print(part1(pathToInput) + " - ");
		System.out.println(part2(pathToInput));
	}
	
	
	
	/**
	 * Hilfsklasse, repräsentiert einen Workflow.
	 */
	public static class Workflow {
		/** Die Conditions des Workflows als Liste */
		List<Condition> conditions = new ArrayList<>();
		
		/** Name des Wf */
		String name;
		
		/** Name des Wf, wenn keine Condition zutrifft */
		String noneApply;
		
		
		/**
		 * Erstellt einen Workflow.
		 * 
		 * @param n - Name des Wf
		 */
		public Workflow(String n) {
			this.name = n;
		}
		
		
		/**
		 * Hinzufügen des Namen des Wfs zu dem weitergeleitet werden
		 * soll, wenn keine Condition zutrifft.
		 * 
		 * @param none - Name
		 */
		public void addNoneApply(String none) {
			this.noneApply = none;
		}
		
		
		/**
		 * Methode zum Hinzufügen einer Condition.
		 * 
		 * @param c - Condition
		 */
		public void add(Condition c) {
			this.conditions.add(c);
		}
	}
	
	
	/**
	 * Hilfsklasse, repräsentiert eine Condition eines Workflows.
	 */
	public static class Condition implements Predicate<Integer> {
		/** Vergleichende Zahl */
		private final int val;
		
		/** Vergleichende Kategorie */
		private final char cat;
		
		/** Ziel, falls Condition zu true auswertet */
		private final String dest;
		
		/** Vergleichsoperator, entweder '<' oder '>' */
		private final char comp;
		
		
		/**
		 * Erzeugt eine Condition.
		 * 
		 * @param cat - Kategorie
		 * @param comp - Vergleichsoperator
		 * @param x - Zahl
		 * @param dest - Ziel-Workflow
		 */
		public Condition(char cat, char comp, int x, String dest) {
			this.val = x;
			this.dest = dest;
			this.cat = cat;
			this.comp = comp;
		}
		
		@Override
		public boolean test(Integer t) {
			return switch (this.comp) {
				case '<' -> t < this.val;
				default -> t > this.val;
			};
		}
		
	}
	
	
	/**
	 * Repräsentiert einen Part.
	 */
	public static class Part{
		/** Map mit den Kategorien als Schlüssel und Werten als Value */
		Map<Character, Integer> parts = new HashMap<>();
		
		
		/**
		 * Erzeugt einen Part mit den übergebenen Werten.
		 * 
		 * @param x - x-Wert
		 * @param m - m-Wert
		 * @param a - a-Wert
		 * @param s - s-Wert
		 */
		public Part(int x, int m, int a, int s) {
			parts.put('x', x);
			parts.put('m', m);
			parts.put('a', a);
			parts.put('s', s);
		}
		
		
		/** 
		 * Berechnet die Summe aller Kategorien des Parts.
		 */
		public int sum() {
			int sum = 0;
			for (Integer i : parts.values()) {
				sum += i;
			}
			
			return sum;
		}
	}
	
	
	/**
	 * Den Pfad eines jeden Parts ermitteln.
	 * 
	 * @param path - Pfad zum Input
	 * @return
	 */
	private static long part1(String path) {
		List<String> input = helpers.HelperMethods.getInputAsListOfString(path);
		boolean parseParts = false;
		
		Map<String, Workflow> map = new HashMap<>();
		List<Part> parts = new ArrayList<>();
		
		for (String line : input) {
			if (!parseParts && line.equals("")) {
				parseParts = true;
			}
			
			// parse workflows
			if (!parseParts) {
				String[] temp = line.split("\\{");
				String name = temp[0];
				
				String[] conditions = temp[1].split(",");
				Workflow wf = new Workflow(name);
				
				for (String cString : conditions) {
					if (cString.contains("}")) {
						wf.addNoneApply(cString.substring(0, cString.length() - 1));
					} else {
						Condition c = new Condition(cString.charAt(0), 
								cString.charAt(1),
								Integer.parseInt(cString.substring(2, cString.indexOf(':'))), 
								cString.substring(cString.indexOf(':') + 1));
						
						wf.add(c);						
					}
				}
				
				map.put(wf.name, wf);
			// parse parts	
			} else {
				if (!line.equals("")) {					
					line = line.substring(1, line.length() - 1);
					String[] temp = line.split(",");
					parts.add(new Part(Integer.parseInt(temp[0].substring(2)),
							Integer.parseInt(temp[1].substring(2)),
							Integer.parseInt(temp[2].substring(2)),
							Integer.parseInt(temp[3].substring(2))));
				}
			}
		}
		
		int counter = 0;
		for (Part p : parts) {
			boolean done = false;
			String wf = "in";
			Workflow curr = map.get(wf);
			while (!done) {
			
				boolean conditions = false;
				Condition c = null;
				
				for (int i = 0; i < curr.conditions.size() && !conditions; i++) {
					c = curr.conditions.get(i);
					if (c.test(p.parts.get(c.cat))) {
						conditions = true;
					}
				}
				
				if (!conditions) {
					if (curr.noneApply.equals("R")) {
						done = true;
					} else if (curr.noneApply.equals("A")) {
						counter += p.sum();
						done = true;
					} else {
						curr = map.get(curr.noneApply);					
					}
				} else {
					if (c.dest.equals("A")) {
						counter += p.sum();
						done = true;
					} else if (c.dest.equals("R")){
						done = true;
					} else {
						curr = map.get(c.dest);
					}					
				}
			}
		}
		
		return counter;
	}
	
	
	/**
	 * Min und Max sind inklusive.
	 */
	record Interval(int min, int max) {
		
	}
	
	
	/**
	 * Hilfsklasse die anstatt genauen Werten Intervalle pro
	 * Kategorie hat.
	 */
	private static class SndPart {
		/** Map mit den Kategorien und den dazugehörigen Intervallen */
		Map<Character, Interval> parts = new HashMap<>();
		
		/** Workflow in dem sich der Part befindet */
		String wf;
		
		
		/**
		 * Erstellt ein SndPart Objekt mit 4 Intervallen, alle
		 * mit min=1 und max=4_000.
		 */
		public SndPart() {
			this.wf = "in";
			parts.put('x', new Interval(1, 4000));
			parts.put('m', new Interval(1, 4000));
			parts.put('a', new Interval(1, 4000));
			parts.put('s', new Interval(1, 4000));
		}		
		
		
		/**
		 * Erstellt ein SndPart Objekt mit denselben Intervallen
		 * wie das übergebene SndPart Objekt. Tauscht eines der Intervalle
		 * mit dem übergebenen Intervall aus.
		 * 
		 * @param p - Part mit zu kopierenden Werten
		 * @param c - Kategorie die ersetzt werden soll
		 * @param i - Intervall
		 * @param wf - Workflow
		 */
		public SndPart(SndPart p, char c, Interval i, String wf) {
			parts.put('x', p.parts.get('x'));
			parts.put('m', p.parts.get('m'));
			parts.put('a', p.parts.get('a'));
			parts.put('s', p.parts.get('s'));
			parts.put(c, i);
			this.wf = wf;
		}
		
		
		/**
		 * Berechnet alle Kombinationen.
		 * 
		 * @return - Anzahl Kombinationen
		 */
		public long combinations() {
			long result = 1;
			
			for (Interval i : parts.values()) {
				result *= i.max - (i.min - 1);
			}
			
			return result;
		}
	}
	
	
	
	/**
	 * Parts haben nicht mehr einzelne Werte pro Kategorie, sondern Intervalle.
	 * Man startet mit einem einzigen Part bei dem alle Kategorien das Intervall
	 * mit min=1 und max=4_000 haben.
	 * 
	 * Beginn mit Workflow "in" und abarbeiten der Conditionen. Wenn das gesamte
	 * Intervall die Condition erfüllt, wird das gesamte Part unverändert an 
	 * den nächsten Workflow gegeben. Wenn das gesamte Intervall die Condition nicht
	 * erfüllt, wird die nächste Condition geprüft. Falls nur ein Teil des
	 * Intervalls die Condition erfüllt, wird ein neues Part Objekt erstellt mit 
	 * dem Intervall, das nicht die Condition erfüllt und in die Queue eingefügt.
	 * Das Intervall des aktuellen Parts wird so angepasst, dass es die Condition
	 * erfüllt und an den nächsten Workflow gegeben. 
	 * 
	 * Wenn dann ein Part fertig ist, wird mit dem nächsten Part weitergemacht, der
	 * dadurch entstanden ist, dass ein Intervall eine Condition nicht
	 * komplett erfüllt hat.
	 * 
	 * @param path - Pfad zum Input
	 * @return
	 */ 
	private static long part2(String path) {
		// PARSING
		List<String> input = helpers.HelperMethods.getInputAsListOfString(path);
		boolean parseParts = false;
		
		Map<String, Workflow> map = new HashMap<>();
		
		for (String line : input) {
			if (!parseParts && line.equals("")) {
				parseParts = true;
			}
			
			// parse workflows - ignore parts
			if (!parseParts) {
				String[] temp = line.split("\\{");
				String name = temp[0];
				
				String[] conditions = temp[1].split(",");
				Workflow wf = new Workflow(name);
				
				for (String cString : conditions) {
					if (cString.contains("}")) {
						wf.addNoneApply(cString.substring(0, cString.length() - 1));
					} else {
						Condition c = new Condition(cString.charAt(0), 
								cString.charAt(1),
								Integer.parseInt(cString.substring(2, cString.indexOf(':'))), 
								cString.substring(cString.indexOf(':') + 1));
						
						wf.add(c);						
					}
				}
				
				map.put(wf.name, wf);
			} 
		}
		
		long counter = 0;
		
		// SOLVING
		SndPart p;
		
		// Start with Part that has 4 intervals from 1 - 4_000 and is in workflow "in"
		Queue<SndPart> q = new LinkedList<>();
		q.add(new SndPart());
		
		
		while (!q.isEmpty()) {
			// Get part
			p = q.poll();
			boolean done = false;
			String wf = p.wf;
			Workflow curr = map.get(wf);
			while (!done) {	
				boolean conditions = false;
				Condition c = null;
				
				// Check conditions of current wf
				for (int i = 0; !conditions && i < curr.conditions.size(); i++) {
					c = curr.conditions.get(i);
					char cat = c.cat;
					int min = p.parts.get(cat).min;
					int max = p.parts.get(cat).max;
					
					// Outcome divided into three scenarios per comparator char
					switch (c.comp) {
					case '<' -> {
						if (max < c.val) {
							// whole interval goes to new wf
							conditions = true;
							curr = map.get(c.dest);
						} else if (min > c.val) {
							// next condition, entire interval does not match
						} else if (min < c.val && max > c.val){
							// split, one part next wf, one part next c
							conditions = true;
							// TODO remember at which condition to continue
							q.add(new SndPart(p, cat, new Interval(c.val, max), curr.name));
							p.parts.put(cat, new Interval(min, c.val - 1));
							curr = map.get(c.dest);
						}
					}
					
					case '>' -> {
						if (min > c.val) {
							// whole interval goes to new wf
							conditions = true;
							curr = map.get(c.dest);
						} else if (max < c.val) {
							// next condition, entire interval does not match
						} else if (min < c.val && max > c.val){
							// split
							conditions = true;
							// TODO remember at which condition to continue
							q.add(new SndPart(p, cat, new Interval(min, c.val), curr.name));
							p.parts.put(cat, new Interval(c.val + 1, max));
							curr = map.get(c.dest);
						}
					}
					}
				}
				
				
				// if no condition returned true -> go to "noneApply"
				if (!conditions) {
					if (curr.noneApply.equals("R")) {
						done = true;
					} else if (curr.noneApply.equals("A")) {
						counter += p.combinations();
						done = true;
					} else {
						curr = map.get(curr.noneApply);					
					}
				} else {
					
					// check if condition's destination is either "A" or "R"
					if (c.dest.equals("A")) {
						counter += p.combinations();
						done = true;
					} else if (c.dest.equals("R")){
						done = true;
					}					
				}
			}
		}
		
		return counter;
	}
	
}
