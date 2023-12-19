package day_18;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

/**
 * Lösung für Tag 18.
 * 
 * @author Yanik Recke
 */
public class Day_18 {

	
	public static void main(String[] args) {
		String pathToInput = "src/day_18/input.txt";
		
		System.out.print(part1(pathToInput) + " - ");
		System.out.println(part2(pathToInput));
	}
	
	
	/**
	 * Erst Ränder berechnen und dann Flood-Fill.
	 * 
	 * @param path - Pfad zum Input
	 * @return
	 */
	private static long part1(String path) {
		List<String> input = helpers.HelperMethods.getInputAsListOfString(path);
		
		Position curr = new Position(0, 0);
		Position next;
		Set<Position> dug = new HashSet<>();
		dug.add(curr);
		
		for (String line : input) {
			char dir = line.split(" ")[0].trim().charAt(0);
			int steps = Integer.parseInt(line.split(" ")[1].trim());
			
			switch (dir) {
				case 'U' -> {
					for (int i = 0; i < steps; i++) {
						next = new Position(curr.getX(), curr.getY() - 1);
						dug.add(next);
						curr = next;
					}
				}
				
				case 'R' -> {
					for (int i = 0; i < steps; i++) {
						next = new Position(curr.getX() + 1, curr.getY());
						dug.add(next);
						curr = next;
					}
				}
				
				case 'D' -> {
					for (int i = 0; i < steps; i++) {
						next = new Position(curr.getX(), curr.getY() + 1);
						dug.add(next);
						curr = next;
					}
				}
				
				default -> {
					for (int i = 0; i < steps; i++) {
						next = new Position(curr.getX() - 1, curr.getY());
						dug.add(next);
						curr = next;
					}
				}
			}
		}
		
	
		Queue<Position> q = new LinkedList<>();
		// assume 1,1 is inside
		q.add(new Position(1,1));
		
		while (!q.isEmpty()) {
			curr = q.poll();
			
			if (!dug.contains(curr)) {
				dug.add(curr);
				
				for (Direction d : Direction.values()) {
					q.add(curr.getNeighbour(d));
				}
			}
			
		}
		
		return dug.size();
	}
	
	
	/**
	 * Hilfsmethode für Shoelace Algorithmus.
	 * 
	 * @param positions - Liste an Positionen
	 * @return
	 */
    private static long shoelace(List<Position> positions) {
        long area = 0;

        int j;
        for (int i = 0; i < positions.size(); i++) {
            j = (i + 1) % positions.size();
            area += positions.get(i).getX() * positions.get(j).getY() - positions.get(j).getX() * positions.get(i).getY();
        }

        return area / 2;   
    }

    
    /**
     * Berechnen der äußeren Punkte und dann mit Satz von Pick
     * und Shoelace Algorithmus innere Fläche + begrenzende Punkte
     * berechnen.
     * 
     * @param path - Pfad zum Input
     * @return
     */
	private static long part2(String path) {
		List<String> input = helpers.HelperMethods.getInputAsListOfString(path);
		
		Position curr = new Position(0, 0);
		Position next;
		long steps;
		char dir;
		String hexa; 
		
		long sum = 0;
		List<Position> positions = new ArrayList<>();
		positions.add(curr);

		for (String line : input) {
			hexa = line.split(" ")[2].trim();
			hexa = hexa.substring(1, hexa.length() - 1);
			dir = hexa.charAt(hexa.length() - 1);
			hexa = hexa.substring(1, hexa.length() - 1);
			steps = Long.parseLong(hexa, 16);
			sum += steps;
			
			switch (dir) {
				// UP
				case '3' -> {
					next = new Position(curr.getX(), curr.getY() - steps);
					positions.add(next);
					curr = next;
				}
				
				// RIGHT
				case '0' -> {
					next = new Position(curr.getX() + steps, curr.getY());
					positions.add(next);
					curr = next;
				}
				
				// DOWN
				case '1' -> {
					next = new Position(curr.getX(), curr.getY() + steps);
					positions.add(next);
					curr = next;
				}
				
				// LEFT
				default -> {
					next = new Position(curr.getX() - steps, curr.getY());
					positions.add(next);
					curr = next;
				}
			}
		}
		
		
		positions.remove(positions.size() - 1);
		return (shoelace(positions) + (sum / 2) + 1);
	}
	
}
