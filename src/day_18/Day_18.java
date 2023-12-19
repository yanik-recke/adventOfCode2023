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
	
	
	class Point2D {
	    public double x;
	    public double y;

	    public Point2D(double x, double y) {
	        this.x = x;
	        this.y = y;
	    }
	}
	
	
    private static long shoelace_formula(List<Position> points) {
        long area = 0L;

        int j = points.size() - 1;
        for (int i = 0; i < points.size(); i++) {
            
            area += points.get(i).getX() * points.get(j).getY() - points.get(j).getX() * points.get(i).getY();
            j = i;
        }

        System.out.println(area);
        return area / 2;
    }

    public static long polyarea(List<Position> points) {
        return Math.abs(shoelace_formula(points));
    }
	
	private static long part2(String path) {
		List<String> input = helpers.HelperMethods.getInputAsListOfString(path);
		
		Position curr = new Position(0, 0);
		Position next;
		Set<Position> dug = new HashSet<>();
		dug.add(curr);
		
		
		List<Position> positions = new ArrayList<>();
		positions.add(curr);
		StringBuilder sb = new StringBuilder();
		sb.append(curr);
		for (String line : input) {
			String hexa = line.split(" ")[2].trim();
			hexa = hexa.substring(1, hexa.length() - 1);
			char dir = hexa.charAt(hexa.length() - 1);
			hexa = hexa.substring(1, hexa.length() - 1);
			
			long steps = Long.parseLong(hexa, 16);
			
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
		//22229015025000 too high, 2222901502500 too low
		System.out.println(polyarea(positions));
		
//		for (Position p : positions) {
//			System.out.println(p);
//		}
		
	
//		Queue<Position> q = new LinkedList<>();
//		// assume 1,1 is inside
//		q.add(new Position(1,1));
//		
//		while (!q.isEmpty()) {
//			curr = q.poll();
//			
//			if (!dug.contains(curr)) {
//				dug.add(curr);
//				
//				for (Direction d : Direction.values()) {
//					q.add(curr.getNeighbour(d));
//				}
//			}
//			
//		}
		
		return dug.size();
	}
	
}
