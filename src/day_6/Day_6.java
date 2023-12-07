package day_6;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * Lösungen für Tag 6.
 * 
 * @author Yanik Recke
 */
public class Day_6 {

	
	public static void main(String[] args) {
		String pathToInput = "src/day_6/input.txt";
		String pathToInput2 = "src/day_6/input2.txt";
		
		System.out.print(part1(pathToInput) + " - ");
		System.out.println(part2(pathToInput2));
	}
	
	
	/**
	 * Starten in der Mitte der gegebenen Zeit und dann
	 * einmal nach oben bruteforcen und einmal nach
	 * unten.
	 * 
	 * @param path - Pfad zum Input
	 * @return
	 */
	private static int part1(String path) {
		List<String> input = helpers.HelperMethods.getInputAsListOfString(path);
		
		String[] timeStrings = input.get(0).split(":")[1].split(" ");
		String[] distStrings = input.get(1).split(":")[1].split(" ");
		
		List<Integer> timeList = new ArrayList<Integer>();
		List<Integer> distList = new ArrayList<Integer>();
		
		
		for (String t : timeStrings) {
			if (!t.equals("")) {
				timeList.add(Integer.parseInt(t));
			}
		}
		
		for (String d : distStrings) {
			if (!d.equals("")) {
				distList.add(Integer.parseInt(d));
			}
		}
		
		
		Set<Integer> wonSet = new HashSet<>();;
		int won = 0;
		for (int i = 0; i < timeList.size(); i++) {
			int start = (int) Math.ceil(timeList.get(i) / 2.0);
			int fullTime = timeList.get(i);
			int distToBeat = distList.get(i);
			int dist; 
			won = 0;
			
			// go up
			int time = start;
			boolean done = false;
			boolean first = false;
			while (time < timeList.get(timeList.size() - 1) && !done) {
				dist = (fullTime - time) * time;
				if (dist > distToBeat) {
					won++;
					first = true;
				} else if (first) {
					done = true;
				}
				
				time++;
			}
			
			// go down
			time = start - 1;
			done = false;
			first = false;
			while (time > 0 && !done) {
				dist = (fullTime - time) * time;
				if (dist > distToBeat) {
					won++;
					first = true;
				} else if (first) {
					done = true;
				}
				
				time--;
			}
			
			wonSet.add(won);
		}
		
		int margin = 1;
		for (Integer num : wonSet) {
			margin = margin * num;
		}
		
		return margin;
	}
	
	
	/**
	 * Starten in der Mitte der gegebenen Zeit und dann
	 * einmal nach oben bruteforcen und einmal nach
	 * unten.
	 * 
	 * @param path - Pfad zum Input
	 * @return
	 */
	private static long part2(String path) {
		List<String> input = helpers.HelperMethods.getInputAsListOfString(path);

		String[] timeStrings = input.get(0).split(":")[1].split(" ");
		String[] distStrings = input.get(1).split(":")[1].split(" ");

		List<Long> timeList = new ArrayList<Long>();
		List<Long> distList = new ArrayList<Long>();

		for (String t : timeStrings) {
			if (!t.equals("")) {
				timeList.add(Long.parseLong(t));
			}
		}

		for (String d : distStrings) {
			if (!d.equals("")) {
				distList.add(Long.parseLong(d));
			}
		}

		Set<Long> wonSet = new HashSet<>();
		;
		long won = 0;
		for (int i = 0; i < timeList.size(); i++) {
			long start = (long) Math.ceil(timeList.get(i) / 2.0);
			long fullTime = timeList.get(i);
			long distToBeat = distList.get(i);
			long dist;
			won = 0;

			// go up
			long time = start;
			boolean done = false;
			boolean first = false;
			while (time < timeList.get(timeList.size() - 1) && !done) {
				dist = (fullTime - time) * time;
				if (dist > distToBeat) {
					won++;
					first = true;
				} else if (first) {
					done = true;
				}

				time++;
			}

			// go down
			time = start - 1;
			done = false;
			first = false;
			while (time > 0 && !done) {
				dist = (fullTime - time) * time;
				if (dist > distToBeat) {
					won++;
					first = true;
				} else if (first) {
					done = true;
				}

				time--;
			}

			wonSet.add(won);
		}

		long margin = 1;
		for (Long num : wonSet) {
			margin = margin * num;
		}

		return margin;
	}
}
