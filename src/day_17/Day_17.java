package day_17;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

/**
 * Lösungen für Tag 17.
 * 
 * @author Yanik Recke
 */
public class Day_17 {
	private static int WIDTH;
	private static int HEIGHT;
	
	private static long currMin = Integer.MAX_VALUE;
	
	
	public static void main(String[] args) {
		String pathToInput = "src/day_17/input.txt";
		
		System.out.print(part1(pathToInput));
		System.out.print(" - ");
		System.out.println(part2(pathToInput));
	}
	
	
	public static class StateComparator implements Comparator<State> {

		@Override
		public int compare(State o1, State o2) {
			return Integer.compare(o1.heatloss, o2.heatloss);
		}
		
	}
	
	
	record Node(int x, int y, Direction d, int steps) {
		
	}
	
	public static class State {
		int heatloss;
		Position pos;
		Direction d;
		int steps;
		
		
		public State(int h, Position p, Direction d, int s) {
			this.heatloss = h;
			this.pos = p;
			this.d = d;
			this.steps = s;
		}
		
		
		@Override
		public final int hashCode() {
			int hash = 7;
			String temp = this.toString();
			
			for (int i = 0; i < temp.length(); i++) {
			    hash = hash * 31 + temp.charAt(i);
			}
			
			return hash;
		}
		
		@Override
		public String toString() {
			return this.pos + "-" + d + "-" + steps;
		}
		
		@Override
		public final boolean equals(Object other) {
			State s = (State) other;
			
			return this.heatloss == s.heatloss && this.pos.equals(s.pos) && this.d == s.d && this.steps == s.steps;
		}
	}
	
	
	private static long part1(String path) {
		int[][] field = helpers.HelperMethods.getInputAsTwoDimensionalArray(path);
		
		WIDTH = field.length;
		HEIGHT = field[0].length;
		
		Set<Node> visited = new HashSet<>();
		
		PriorityQueue<State> queue = new PriorityQueue<>(new StateComparator());
		queue.add(new State(0, new Position(0,0), null, 0));
		
		long result = 0;
		
		State curr;
		Position next;
		boolean done = false;
		
		while (!queue.isEmpty() && !done) {
			curr = queue.poll();
			Node temp = new Node(curr.pos.getX(), curr.pos.getY(), curr.d, curr.steps);
			if (curr.pos.equals(new Position(WIDTH - 1, HEIGHT -1))) {
				done = true;
				result = curr.heatloss;
			}
			
			if (!visited.contains(temp)) {
				visited.add(temp);
				
				if (curr.steps < 3 && curr.d != null) {
					next = curr.pos.getNeighbour(curr.d);
					
					if (isInbounds(next)) {
						queue.add(new State(curr.heatloss + field[next.getX()][next.getY()], next, curr.d, curr.steps + 1));
					}
				}
				
				for (Direction d : Direction.values()) {
					if (curr.d == null || (d != curr.d && d != curr.d.opp())) {
						next = curr.pos.getNeighbour(d);
						if (isInbounds(next)) {
							queue.add(new State(curr.heatloss + field[next.getX()][next.getY()], next, d, 1));
						}
					}
				}
			}
		}
		
		return result;
	}
	

	
	
	/**
	 * Prüft, ob eine übergebene Position inbounds ist
	 * oder nicht.
	 * 
	 * @param pos - Zu prüfende Position
	 * @return - true, wenn inbounds, false, wenn nicht
	 */
	private static boolean isInbounds(Position pos) {
		return pos.getX() >= 0  && pos.getY() >= 0 && pos.getX() < WIDTH && pos.getY() < HEIGHT;
	}
	
	
	private static long part2(String path) {
		int[][] field = helpers.HelperMethods.getInputAsTwoDimensionalArray(path);
		
		WIDTH = field.length;
		HEIGHT = field[0].length;
		
		Set<Node> visited = new HashSet<>();
		
		PriorityQueue<State> queue = new PriorityQueue<>(new StateComparator());
		queue.add(new State(0, new Position(0,0), null, 0));
		
		long result = 0;
		
		State curr;
		Position next;
		boolean done = false;
		
		while (!queue.isEmpty() && !done) {
			curr = queue.poll();
			
			Node node = new Node(curr.pos.getX(), curr.pos.getY(), curr.d, curr.steps);
			
			if (!visited.contains(node)) {
				if (curr.pos.equals(new Position(WIDTH - 1, HEIGHT -1)) && curr.steps > 3) {
					done = true;
					result = curr.heatloss;
				} else {
					visited.add(node);
					
					if (curr.steps < 10 && curr.d != null) {
						next = curr.pos.getNeighbour(curr.d);
						
						if (isInbounds(next)) {
							queue.add(new State(curr.heatloss + field[next.getX()][next.getY()], next, curr.d, curr.steps + 1));
						}
					}
					
					if (curr.steps > 3 || curr.d == null) {					
						for (Direction d : Direction.values()) {
							if (curr.d == null || (d != curr.d && d != curr.d.opp())) {
								next = curr.pos.getNeighbour(d);
								if (isInbounds(next)) {
									queue.add(new State(curr.heatloss + field[next.getX()][next.getY()], next, d, 1));
								}
							}
						}
					}
				}
				
			}
		}
		
		return result;
	}
}
