package day_10;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;


/**
 * Lösung für Tag 10.
 * 
 * @author Yanik Recke
 */
public class Day_10 {

	public static void main(String[] args) {
		String pathToInput = "src/day_10/input.txt";
		
		System.out.print(part1(pathToInput) + " - ");
		System.out.println(part2(pathToInput));
	}
	
	
	/**
	 * Richtungen.
	 */
	public enum Direction {
		TOP,
		RIGHT,
		DOWN,
		LEFT,
		WILD;
		
		
		/**
		 * Gibt die entgegengesetzte Richtung zurück.
		 * 
		 * @return - entgegengesetzte Richtung
		 */
		public Direction getOpp() {
			switch (this) {
				case TOP: return DOWN;
				case RIGHT: return LEFT;
				case LEFT: return RIGHT;
				case WILD: return WILD;
				default: return TOP;
			}
		}

	}
	
	
	/**
	 * Hilfsklasse, die eine Pipe repräsentiert.
	 */
	public static class Pipe {
		Direction first;
		Direction snd;
		char c;
		Position pos;
		
		
		/**
		 * Erstellt eine Pipe anhand eines Characters und einer
		 * Position.
		 * 
		 * @param c - Character
		 * @param pos - Position der Pipe
		 */
		public Pipe(char c, Position pos) {
			this.pos = pos;
			this.c = c;
			switch (c) {
			case '|':{
				this.first = Direction.TOP;
				this.snd = Direction.DOWN;
				break;
			}
			
			case '-' : {
				this.first = Direction.LEFT;
				this.snd = Direction.RIGHT;
				break;
			}
			
			case 'L' : {
				this.first = Direction.TOP;
				this.snd = Direction.RIGHT;
				break;
			}
			
			case 'J' : {
				this.first = Direction.TOP;
				this.snd = Direction.LEFT;
				break;
			}
			
			case '7' : {
				this.first = Direction.DOWN;
				this.snd = Direction.LEFT;
				break;
			}
			
			case 'S' : {
				this.first = Direction.WILD;
				this.snd = Direction.WILD;
				break;
			}
			
			default : {
				this.first = Direction.DOWN;
				this.snd = Direction.RIGHT;	
			}
			}

		}
		
		
		/**
		 * Prüft, ob die eigene und die übergebene
		 * Pipe verbunden sind.
		 * 
		 * @param p - Andere Pipe
		 * @return - true, wenn verbunden, false, wenn nicht
		 */
		public boolean connects(Pipe p) {
			return this.first == Direction.WILD 
					|| this.snd == Direction.WILD 
					|| p.first == Direction.WILD 
					|| p.snd == Direction.WILD 
					|| this.first == p.first.getOpp() 
					|| this.snd == p.first.getOpp() 
					|| this.first == p.snd.getOpp()
					|| this.snd == p.snd.getOpp();
		}
		
		@Override
		public int hashCode() {
			return pos.hashCode();
		}
		
		@Override
		public String toString() {
			return c + " - " + first.toString() + " & " + snd.toString();
		}
	}
	
	
	/**
	 * Bei 'S' anfangen und den Loop suchen. Dann Länge des 
	 * Weges durch Zwei teilen.
	 * 
	 * //TODO: Für meinen Input gilt c = '|' -> aber sollte für jeden Input funktionieren.
	 * 
	 * @param path - Pfad zum Input
	 * @return
	 */
	private static long part1(String path) {
		char[][] temp = helpers.HelperMethods.getInputAsTwoDimensionalCharArray(path);
		Pipe[][] field = new Pipe[temp.length][temp[0].length];
		Position start = null;
		
		int maxX = field.length - 1;
		int maxY = field[0].length - 1;
		
		for (int x = 0; x < temp.length; x++) {
			for (int y = 0; y < temp[x].length; y++) {
				if (temp[x][y] == 'S') {
					start = new Position(x, y);
				}
				
				if (temp[x][y] != '.') {					
					field[x][y] = new Pipe(temp[x][y], new Position(x, y));
				} else {
					field[x][y] = null;
				}
			}
		}
		
		List<Position> loop = new ArrayList<>();
		List<Position> loopFinal = new ArrayList<>();
		
		boolean done = false;
		Position next = null;
		Pipe tempPipe = null;
		Pipe currPipe = null;
		Position curr = null;
		Position last = new Position(-1, -1);
		
		int tempX;
		int tempY;
		
		Character c = '|';
			
		loop = new ArrayList<>();
		field[start.getX()][start.getY()] = new Pipe(c, new Position(start.getX(), start.getY()));
		curr = start;
		done = false;
		last = new Position(-1, -1);

		while (!done) {
			next = null;
			currPipe = field[curr.getX()][curr.getY()];
			// check top neigh
			tempX = curr.getX();
			tempY = curr.getY() - 1;

			if (tempY >= 0 && currPipe.first == Direction.TOP || currPipe.snd == Direction.TOP) {

				tempPipe = field[tempX][tempY];
				if (tempPipe != null && currPipe.connects(tempPipe)) {
					if (last.getX() != tempX || last.getY() != tempY) {
						next = new Position(tempX, tempY);
					}
				}
			}

			// check right neigh
			if (next == null) {
				tempX = curr.getX() + 1;
				tempY = curr.getY();

				if (tempX <= maxX && currPipe.first == Direction.RIGHT || currPipe.snd == Direction.RIGHT) {
					tempPipe = field[tempX][tempY];
					if (tempPipe != null && currPipe.connects(tempPipe)) {
						if (last.getX() != tempX || last.getY() != tempY) {
							next = new Position(tempX, tempY);
						}
					}
				}
			}

			// check down neigh
			if (next == null) {
				tempX = curr.getX();
				tempY = curr.getY() + 1;

				if (tempY <= maxY && currPipe.first == Direction.DOWN || currPipe.snd == Direction.DOWN) {
					tempPipe = field[tempX][tempY];
					if (tempPipe != null && currPipe.connects(tempPipe)) {
						if (last.getX() != tempX || last.getY() != tempY) {
							next = new Position(tempX, tempY);
						}
					}
				}
			}

			// check left neigh
			if (next == null) {
				tempX = curr.getX() - 1;
				tempY = curr.getY();

				if (tempX >= 0 && currPipe.first == Direction.LEFT || currPipe.snd == Direction.LEFT) {
					tempPipe = field[tempX][tempY];
					if (tempPipe != null && currPipe.connects(tempPipe)) {
						if (last.getX() != tempX || last.getY() != tempY) {
							next = new Position(tempX, tempY);
						}
					}
				}
			}

			if (last.getX() != -1 && curr.getX() == start.getX() && curr.getY() == start.getY()) {
				done = true;
				// fin = true;
				loopFinal = loop;
			} else {
				loop.add(curr);
				last = new Position(curr.getX(), curr.getY());
				curr = next;

				if (curr == null) {
					done = true;
				}
			}
		}
		
		return loopFinal.size() / 2;
	}
	
	
	
	/**
	 * Zuerst mit derselben Methode aus Part 1 den Loop finden und
	 * speichern. Dann zwischen jede verbunden Position, also alle 
	 * Pipes in dem Loop eine weitere Verbindungsposition einfügen.
	 * Zwischen (0,0) und (0,1) also z.B. (0,0.5). Dann Floodfill-
	 * Algorithmus für jede Position -> übrig bleiben die, die keinen
	 * Weg nach "draußen" gefunden haben.
	 * 
	 * @param path - Pfad zum Input
	 * @return
	 */
	private static long part2(String path) {
		// BEGINNING PART 1
		char[][] temp = helpers.HelperMethods.getInputAsTwoDimensionalCharArray(path);
		Pipe[][] field = new Pipe[temp.length][temp[0].length];
		Position start = null;

		int maxX = field.length - 1;
		int maxY = field[0].length - 1;

		for (int x = 0; x < temp.length; x++) {
			for (int y = 0; y < temp[x].length; y++) {
				if (temp[x][y] == 'S') {
					start = new Position(x, y);
				}

				if (temp[x][y] != '.') {
					field[x][y] = new Pipe(temp[x][y], new Position(x, y));
				} else {
					field[x][y] = null;
				}
			}
		}

		List<Pipe> loop = new ArrayList<>();
		List<Position> loopPosition =  new ArrayList<>();

		boolean done = false;
		Position next = null;
		Pipe tempPipe = null;
		Pipe currPipe = null;
		Position curr = null;
		Position last = new Position(-1, -1);

		int tempX;
		int tempY;

		Character c = '|';
		char starterSymbol = ' ';

		loop = new ArrayList<>();
		field[start.getX()][start.getY()] = new Pipe(c, new Position(start.getX(), start.getY()));
		curr = start;
		done = false;
		last = new Position(-1, -1);

		while (!done) {
			next = null;
			currPipe = field[curr.getX()][curr.getY()];
			
			// check top neigh
			tempX = curr.getX();
			tempY = curr.getY() - 1;

			if (tempY >= 0 && currPipe.first == Direction.TOP || currPipe.snd == Direction.TOP) {

				tempPipe = field[tempX][tempY];
				if (tempPipe != null && currPipe.connects(tempPipe)) {
					if (last.getX() != tempX || last.getY() != tempY) {
						next = new Position(tempX, tempY);
					}
				}
			}

			// check right neigh
			if (next == null) {
				tempX = curr.getX() + 1;
				tempY = curr.getY();

				if (tempX <= maxX && currPipe.first == Direction.RIGHT || currPipe.snd == Direction.RIGHT) {
					tempPipe = field[tempX][tempY];
					if (tempPipe != null && currPipe.connects(tempPipe)) {
						if (last.getX() != tempX || last.getY() != tempY) {
							next = new Position(tempX, tempY);
						}
					}
				}
			}

			// check down neigh
			if (next == null) {
				tempX = curr.getX();
				tempY = curr.getY() + 1;

				if (tempY <= maxY && currPipe.first == Direction.DOWN || currPipe.snd == Direction.DOWN) {
					tempPipe = field[tempX][tempY];
					if (tempPipe != null && currPipe.connects(tempPipe)) {
						if (last.getX() != tempX || last.getY() != tempY) {
							next = new Position(tempX, tempY);
						}
					}
				}
			}

			// check left neigh
			if (next == null) {
				tempX = curr.getX() - 1;
				tempY = curr.getY();

				if (tempX >= 0 && currPipe.first == Direction.LEFT || currPipe.snd == Direction.LEFT) {
					tempPipe = field[tempX][tempY];
					if (tempPipe != null && currPipe.connects(tempPipe)) {
						if (last.getX() != tempX || last.getY() != tempY) {
							next = new Position(tempX, tempY);
						}
					}
				}
			}

			if (last.getX() != -1 && curr.getX() == start.getX() && curr.getY() == start.getY()) {
				done = true;
			} else {
				loop.add(field[curr.getX()][curr.getY()]);
				loopPosition.add(curr);
				last = new Position(curr.getX(), curr.getY());
				curr = next;

				if (curr == null) {
					done = true;
				}
			}
		}
		//END PART 1
		
		field[start.getX()][start.getY()] = new Pipe(starterSymbol, start);
		
		Set<Position2> halfs = new HashSet<>();
		
		// Zwischenpositionen erstellen
		for (Pipe p : loop) {
			halfs.add(new Position2(p.pos.getX(), p.pos.getY()));
			createHalfPositions(halfs, p.first, p.pos);
			createHalfPositions(halfs, p.snd, p.pos);
		}
		
		// Jede Pipe entfernen, die nicht Teil des Loops ist
		for (int x = 0; x < field.length; x++) {
			for (int y = 0; y < field[x].length; y++) {
				Position pos = new Position(x, y);
				
				if (!loopPosition.contains(pos)) {
					field[x][y] = null;
				}
			}
		}
		
		Set<Position2> notInside = new HashSet<>();
		Queue<Position2> toCheck = new LinkedList<>();
		Set<Position2> maybe = new HashSet<>();
		Set<Position2> alreadyChecked = new HashSet<>();
		double halfX;
		double halfY;
		
		// Ablaufen jeder Position in 0.5er Schritten -> Floodfill
		for (double x = 0; x < field.length; x+=0.5) {
			for (double y = 0; y < field[0].length; y+=0.5) {
				Position2 pos = new Position2(x, y);
				maybe.clear();
				toCheck.add(pos);
				
				while (!toCheck.isEmpty()) {
					pos = toCheck.poll();
					
					
					if (!halfs.contains(pos) && !alreadyChecked.contains(pos)) {
						maybe.add(pos);
						alreadyChecked.add(pos);
						if (!notInside.contains(pos)) {
							// check top
							halfX = pos.getX();
							halfY = pos.getY() - 0.5;
							
							if (halfY < 0 || notInside.contains(new Position2(halfX, halfY))) {
								for (Position2 tempPos : maybe) {
									notInside.add(tempPos);
								}
								maybe.clear();
							} else if (!halfs.contains(new Position2(halfX, halfY))){
								toCheck.add(new Position2(halfX, halfY));
							}
							
							// check right
							halfX = pos.getX() + 0.5;
							halfY = pos.getY();
							
							if (halfX > maxX || notInside.contains(new Position2(halfX, halfY))) {
								for (Position2 tempPos : maybe) {
									notInside.add(tempPos);
								}
								maybe.clear();
							} else if (!halfs.contains(new Position2(halfX, halfY))){
								toCheck.add(new Position2(halfX, halfY));
							}
							
							// check down
							halfX = pos.getX();
							halfY = pos.getY() + 0.5;
							
							if (halfY > maxY || notInside.contains(new Position2(halfX, halfY))) {
								for (Position2 tempPos : maybe) {
									notInside.add(tempPos);
								}
								maybe.clear();
							} else if (!halfs.contains(new Position2(halfX, halfY))){
								toCheck.add(new Position2(halfX, halfY));
							}
							
							// check left
							halfX = pos.getX() - 0.5;
							halfY = pos.getY();
							
							if (halfX < 0 || notInside.contains(new Position2(halfX, halfY))) {
								for (Position2 tempPos : maybe) {
									notInside.add(tempPos);
								}
								maybe.clear();
							} else if (!halfs.contains(new Position2(halfX, halfY))){
								toCheck.add(new Position2(halfX, halfY));
							}
						} else {						
							for (Position2 tempPos : maybe) {
								notInside.add(tempPos);
							}
							maybe.clear();
						}
					}
					
				}
			}
		}
		
		
		/* 
		 * Nochmal jede Position ablaufen, wenn sie
		 * nicht im "notInside" Set und nicht Teil des Loops
		 * sind, dann sind sie eingeschlossen.
		 */
		long count = 0;
		for (double x = 0; x < field.length; x++) {
			for (double y = 0; y < field[0].length; y++) {
				if (!notInside.contains(new Position2(x, y)) && !halfs.contains(new Position2(x, y))) {
					count++;
				}
			}
		}

		return count;
	}
	
	
	/**
	 * Hilfsmethode, die zwischen zwei Positionen eine weitere einfügt.
	 * Zum Beispiel zwischen (0,0) und (0,1) kommt (0,0.5)
	 * 
	 * Die zweite Position wird anhand der übergebenen Direction bestimmt.
	 * 
	 * @param s - Set mit Positionen
	 * @param d - Richtung
	 * @param pos - Position
	 */
	private static void createHalfPositions(Set<Position2> s, Direction d, Position pos) {
		
		switch (d) {
		case TOP : {
			s.add(new Position2(pos.getX(), pos.getY() - 0.5));
			break;
		}
		
		case RIGHT : {
			s.add(new Position2(pos.getX() + 0.5, pos.getY()));
			break;
		}
		
		case DOWN : {
			s.add(new Position2(pos.getX(), pos.getY() + 0.5));
			break;
		}
		
		case LEFT : {
			s.add(new Position2(pos.getX() - 0.5, pos.getY()));
			break;
		}
		
		default : {
			throw new IllegalArgumentException();			}
		}
	}

}
