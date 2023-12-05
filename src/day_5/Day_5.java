package day_5;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.OptionalLong;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

/**
 * Lösung für Tag 5.
 * 
 * @author Yanik Recke
 */
public class Day_5 {

	
	public static void main(String[] args) {
		String pathToInput = "src/day_5/input.txt";
		System.out.print(part1(pathToInput) + " - ");
		System.out.println(part2(pathToInput));
	}
	
	
	/**
	 * Hilfsklasse, repräsentiert Map.
	 */
	public static class Map {
		List<Interval> destIntervals = new ArrayList<>();
		List<Interval> sourceIntervals = new ArrayList<>();
	}
	
	
	/**
	 * Hilfsklasse, repräsentiert Intervall.
	 */
	public static class Interval {
		long start;
		long end;
		long range;
		
		public Interval(long s, long e, long r) {
			this.start = s;
			this.end = e;
			this.range = r;
		}
		
		public boolean isInside(long n) {			
			return n <= end && n >= start;
		}
	}
	
	
	/**
	 * Abarbeiten pro Zeile pro Map.
	 * 
	 * @param path - Pfad zum Input
	 * @return - niedrigster location-score
	 */
	private static long part1(String path) {
		List<String> input = helpers.HelperMethods.getInputAsListOfString(path);
		
		String[] seedsString = input.get(0).split(":")[1].split(" ");
		List<Long> seeds = new ArrayList<>();
		
		for (String seed : seedsString) {
			if (!seed.equals("")) {
				seeds.add(Long.parseLong(seed));
			}
		}
		
		input.remove(0);
		input.remove(0);
		input.remove(0);
		
		Map currMap = new Map();
		
		for (String line : input) {
			if (!line.equals("")) {
				if (Character.isDigit(line.charAt(0))) {
					String[] temp = line.split(" ");
					currMap.sourceIntervals.add(new Interval(Long.parseLong(temp[1]), Long.parseLong(temp[1]) + Long.parseLong(temp[2]), Long.parseLong(temp[2])));
					currMap.destIntervals.add(new Interval(Long.parseLong(temp[0]), Long.parseLong(temp[0]) + Long.parseLong(temp[2]), Long.parseLong(temp[2])));
				}
			} else {
				// calc
				List<Long> newSeeds = new ArrayList<>();
				for(Long seed : seeds) {
					List<Interval> temp = currMap.sourceIntervals;
					boolean added = false;
					for (int i = 0; i < temp.size(); i++) {
						if (temp.get(i).isInside(seed)) {
							long startSource = currMap.sourceIntervals.get(i).start;
							long startDest = currMap.destIntervals.get(i).start;
							long num = startDest + (seed - startSource);
							newSeeds.add(num);
							added = true;
						}
					}
					
					if (!added) {
						newSeeds.add(seed);
					}
				}
				
				currMap = new Map();
				seeds.clear();
				seeds.addAll(newSeeds);
			}
		}
		
		return Collections.min(seeds);
	}
	
	
	/**
	 * Abarbeiten für jeden Seed pro Intervall mit Streams.
	 * 
	 * @param path - Pfad zum Input
	 * @return
	 */
	private static long part2(String path) {
		List<String> input = helpers.HelperMethods.getInputAsListOfString(path);
		
		String[] seedsString = input.get(0).split(":")[1].trim().split(" ");
		
		Set<OptionalLong> result = new HashSet<>();
		input.remove(0);
		input.remove(0);
		input.remove(0);
		
		List<Map> maps = new ArrayList<>();
		Map currMap = new Map();
		
		for (String line : input) {
			if (!line.equals("")) {
				if (Character.isDigit(line.charAt(0))) {
					String[] temp = line.split(" ");
					currMap.sourceIntervals.add(new Interval(Long.parseLong(temp[1]),
							Long.parseLong(temp[1]) + Long.parseLong(temp[2]), 
							Long.parseLong(temp[2])));
					currMap.destIntervals.add(new Interval(Long.parseLong(temp[0]),
							Long.parseLong(temp[0]) + Long.parseLong(temp[2]), 
							Long.parseLong(temp[2])));
				}
			} else {
				maps.add(currMap);
				currMap = new Map();
			}
		}
		
		for (int i = 0; i < seedsString.length - 1; i+=2) {
			long first = Long.parseLong(seedsString[i]);
			long snd = Long.parseLong(seedsString[i + 1]);
			
			OptionalLong s = LongStream.iterate(first, n -> n + 1).limit(snd).map(x -> {
				// map loop
				long num = 0;
				boolean found = false;
				for (int j = 0; j < maps.size(); j++) {
					Map tempMap = maps.get(j);
					found = false;
					// intervals of map loop
					for (int k = 0; k < tempMap.sourceIntervals.size() && !found; k++) {
						if (tempMap.sourceIntervals.get(k).isInside(x)) {
							long startSource = tempMap.sourceIntervals.get(k).start;
							long startDest = tempMap.destIntervals.get(k).start;
							num = startDest + (x - startSource);
							found = true;
							x = num;
						}
					}
				}
				
				if (!found) {
					return x;
				} else {
					return num;
				}
				
			}).min();
			
			result.add(s);
		}
		
		OptionalLong min = result.stream().mapToLong(OptionalLong::getAsLong).min();
		
		return min.getAsLong();
	}
}
