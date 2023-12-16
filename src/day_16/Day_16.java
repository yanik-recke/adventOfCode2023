package day_16;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;


/**
 * Lösung für Tag 16.
 * 
 * @author Yanik Recke
 */
public class Day_16 {
	public static int WIDTH;
	public static int HEIGHT;
	
	
	public static void main(String[] args) {
		String pathToInput = "src/day_16/input.txt";
		
		System.out.print(part1(pathToInput) + " - ");
		System.out.println(part2(pathToInput));
	}
	
	
	/**
	 * Enum, repräsentiert die Ausrichtungen der
	 * Beams.
	 */
	public enum Heading {
		TOP,
		RIGHT,
		DOWN,
		LEFT
	}
	
	
	/**
	 * Hilfklasse, repräsentiert einen Beam anhand seiner
	 * Position und aktuellen Ausrichtung.
	 */
	public static class Beam {
		Heading dir;
		Position pos;
		
		public Beam(Heading d, Position p) {
			this.dir = d;
			this.pos = p;
		}
	}
	
	
	/**
	 * Start bei 0,0 und dann ablaufen bis Loop gefunden.
	 * 
	 * @param path - Pfad zum Input
	 * @return - Anzahl an betretenen tiles
	 */
	private static long part1(String path) {
		char[][] field = helpers.HelperMethods.getInputAsTwoDimensionalCharArray(path);
		
		// keep track of moving beams
		Set<Beam> beams = new HashSet<>();
		Set<Beam> toAdd = new HashSet<>();
		
		// keep track of passed mirrors
		Set<Position> energized = new HashSet<>();
		
		int tempX;
		int tempY;
		Map<Position, Heading> map = new HashMap<>();
		
		WIDTH = field[0].length;
		HEIGHT = field.length;
		
		beams.add(new Beam(Heading.RIGHT, new Position(-1,0)));
		while (!beams.isEmpty()) {
			Iterator<Beam> it = beams.iterator();
			boolean removed = false;
			while (it.hasNext()) {
				Beam beam = it.next();
				
				tempX = beam.pos.getX();
				tempY = beam.pos.getY();
				energized.add(new Position(tempX, tempY));
				
				switch (beam.dir) {
					case TOP -> {
						tempY--;
						Position curr = new Position(tempX, tempY);
						if (isInbounds(curr)) {
							beam.pos = curr;
							
							switch (field[curr.getX()][curr.getY()]) {
							case '/' -> {
								beam.dir = Heading.RIGHT;
								energized.add(curr);
							}

							case '\\' -> {
								beam.dir = Heading.LEFT;
								energized.add(curr);
							}

							case '-' -> {
								beam.dir = Heading.RIGHT;
								toAdd.add(new Beam(Heading.LEFT, curr));
								energized.add(curr);
							}
							}
						} else {
							it.remove();
							removed = true;
						}
					}
				
					case RIGHT -> {
						tempX++;
						Position curr = new Position(tempX, tempY);
						if (isInbounds(curr)) {
							beam.pos = curr;
							switch (field[curr.getX()][curr.getY()]) {
							case '/' -> {
								beam.dir = Heading.TOP;
								energized.add(curr);
							}
						
							case '\\' -> {
								beam.dir = Heading.DOWN;
								energized.add(curr);
							}
						
							case '|' -> {
								beam.dir = Heading.TOP;
								toAdd.add(new Beam(Heading.DOWN, curr));
								energized.add(curr);
							}
						}
						} else {
							it.remove();
							removed = true;
						}
					}
				
					case DOWN -> {
						tempY++;
						Position curr = new Position(tempX, tempY);
						if (isInbounds(curr)) {
							beam.pos = curr;
							switch (field[curr.getX()][curr.getY()]) {
							case '/' -> {
								beam.dir = Heading.LEFT;
								energized.add(curr);
							}
						
							case '\\' -> {
								beam.dir = Heading.RIGHT;
								energized.add(curr);
							}
						
							case '-' -> {
								beam.dir = Heading.RIGHT;
								toAdd.add(new Beam(Heading.LEFT, curr));
								energized.add(curr);
							}
						}
						} else {
							it.remove();
							removed = true;
						}
					}
				
					default -> {
						tempX--;
						Position curr = new Position(tempX, tempY);
						if (isInbounds(curr)) {
							beam.pos = curr;
							switch (field[curr.getX()][curr.getY()]) {
							case '/' -> {
								beam.dir = Heading.DOWN;
								energized.add(curr);
							}
						
							case '\\' -> {
								beam.dir = Heading.TOP;
								energized.add(curr);
							}
						
							case '|' -> {
								beam.dir = Heading.TOP;
								toAdd.add(new Beam(Heading.DOWN, curr));
								energized.add(curr);
							}
						}
						} else {
							it.remove();
							removed = true;
						}
					}
				}
			
				if (map.containsKey(beam.pos) 
						&& map.get(beam.pos).equals(beam.dir) && !removed) {
					it.remove();
				} else {
					map.put(beam.pos, beam.dir);
				}
				
			}
			
			for (Beam b : toAdd) {
				beams.add(b);
			}
			
			toAdd.clear();
		}
		
		return energized.size() - 1;
	}
	
	
	/**
	 * Berechnet, ob die übergebene Position inbounds 
	 * ist oder nicht.
	 * 
	 * @param pos - die zu prüfende Position
	 * @return - true, wenn inbounds, false wenn nicht
	 */
	private static boolean isInbounds(Position pos) {
		return pos.getX() >= 0 && pos.getY() >= 0 && pos.getX() < WIDTH && pos.getY() < HEIGHT;
	}
	
	
	/**
	 * Jede mögliche Startposition bruteforcen.
	 * 
	 * @param path - Pfad zum Input
	 * @return - höchste Zahl an betretenen tiles
	 */
	private static long part2(String path) {
		char[][] field = helpers.HelperMethods.getInputAsTwoDimensionalCharArray(path);
		
		WIDTH = field[0].length;
		HEIGHT = field.length;
		long max = 0;
		long temp = 0;
		for (int x = 0; x < WIDTH; x++) {
			if (max < (temp = calc(field, new Beam(Heading.DOWN, new Position(x, -1))))) {
				max = temp;
			}
		}
		
		for (int x = 0; x < WIDTH; x++) {
			if (max < (temp = calc(field, new Beam(Heading.DOWN, new Position(x, HEIGHT))))) {
				max = temp;
			}
		}
		
		for (int y = 0; y < HEIGHT; y++) {
			if (max < (temp = calc(field, new Beam(Heading.DOWN, new Position(-1, y))))) {
				max = temp;
			}
		}
		
		
		for (int y = 0; y < HEIGHT; y++) {
			if (max < (temp = calc(field, new Beam(Heading.DOWN, new Position(WIDTH, y))))) {
				max = temp;
			}
		}
		
		return max;
		
	}
	
	
	private static long calc(char[][] field, Beam beam) {
		Set<Beam> beams = new HashSet<>();
		Set<Beam> toAdd = new HashSet<>();
		
		Map<Position, Heading> map = new HashMap<>();
		
		// keep track of passed mirrors
		Set<Position> energized = new HashSet<>();
		
		int tempX;
		int tempY;
		
		beams.add(beam);
		while (!beams.isEmpty()) {
			Iterator<Beam> it = beams.iterator();
			boolean removed = false;
			while (it.hasNext()) {
				beam = it.next();
				
				tempX = beam.pos.getX();
				tempY = beam.pos.getY();
				energized.add(new Position(tempX, tempY));
				
				switch (beam.dir) {
					case TOP -> {
						tempY--;
						Position curr = new Position(tempX, tempY);
						if (isInbounds(curr)) {
							beam.pos = curr;
							
							switch (field[curr.getX()][curr.getY()]) {
							case '/' -> {
								beam.dir = Heading.RIGHT;
								energized.add(curr);
							}

							case '\\' -> {
								beam.dir = Heading.LEFT;
								energized.add(curr);
							}

							case '-' -> {
								beam.dir = Heading.RIGHT;
								toAdd.add(new Beam(Heading.LEFT, curr));
								energized.add(curr);
							}
							}
						} else {
							it.remove();
							removed = true;
						}
					}
				
					case RIGHT -> {
						tempX++;
						Position curr = new Position(tempX, tempY);
						if (isInbounds(curr)) {
							beam.pos = curr;
							switch (field[curr.getX()][curr.getY()]) {
							case '/' -> {
								beam.dir = Heading.TOP;
								energized.add(curr);
							}
						
							case '\\' -> {
								beam.dir = Heading.DOWN;
								energized.add(curr);
							}
						
							case '|' -> {
								beam.dir = Heading.TOP;
								toAdd.add(new Beam(Heading.DOWN, curr));
								energized.add(curr);
							}
						}
						} else {
							it.remove();
							removed = true;
						}
					}
				
					case DOWN -> {
						tempY++;
						Position curr = new Position(tempX, tempY);
						if (isInbounds(curr)) {
							beam.pos = curr;
							switch (field[curr.getX()][curr.getY()]) {
							case '/' -> {
								beam.dir = Heading.LEFT;
								energized.add(curr);
							}
						
							case '\\' -> {
								beam.dir = Heading.RIGHT;
								energized.add(curr);
							}
						
							case '-' -> {
								beam.dir = Heading.RIGHT;
								toAdd.add(new Beam(Heading.LEFT, curr));
								energized.add(curr);
							}
						}
						} else {
							it.remove();
							removed = true;
						}
					}
				
					default -> {
						tempX--;
						Position curr = new Position(tempX, tempY);
						if (isInbounds(curr)) {
							beam.pos = curr;
							switch (field[curr.getX()][curr.getY()]) {
							case '/' -> {
								beam.dir = Heading.DOWN;
								energized.add(curr);
							}
						
							case '\\' -> {
								beam.dir = Heading.TOP;
								energized.add(curr);
							}
						
							case '|' -> {
								beam.dir = Heading.TOP;
								toAdd.add(new Beam(Heading.DOWN, curr));
								energized.add(curr);
							}
						}
						} else {
							it.remove();
							removed = true;
						}
					}
				}
			
				if (map.containsKey(beam.pos) 
						&& map.get(beam.pos).equals(beam.dir) && !removed) {
					it.remove();
				} else {
					map.put(beam.pos, beam.dir);
				}
				
			}
			
			for (Beam b : toAdd) {
				beams.add(b);
			}
			
			toAdd.clear();
		}
		
		// too high 6995
		return energized.size() - 1;
	}
	
}
