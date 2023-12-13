package day_12;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


/**
 * Lösung für Tag 12.
 * 
 * @author Yanik Recke
 */
public class Day_12 {

	public static void main(String[] args) {
		String pathToInput = "src/day_12/input.txt";
		
		System.out.print(part1(pathToInput) + " - ");
	} 
	
	private static long part1(String path) {
		List<String> input = helpers.HelperMethods.getInputAsListOfString(path);
		long result = 0;
		for (String line : input) {
			String row = line.split(" ")[0];
			String instr = line.split(" ")[1];
			List<Integer> nums = new ArrayList<>(Arrays.stream(instr.split(",")).map(x -> Integer.parseInt(x)).collect(Collectors.toList()));
			result += check(0, new String(row), nums);
			
		}
		
		return result;
	}
	
	
	private static long check(long count, String curr, List<Integer> nums) {
		
		if (curr.charAt(0) == '.') {
			if (curr.length() == 1) {
				return count;
			} else {				
				return check(count, curr.substring(1), new ArrayList<>(nums));
			}
		} else if (curr.charAt(0) == '?'){
			char[] next = curr.toCharArray();
			
			next[0] = '.';
			count = check(count, new String(next), new ArrayList<>(nums));
			
			next[0] = '#';
			count = check(count, new String(next), new ArrayList<>(nums));
		} else {
			int numHash = 0;
			for (int i = 0; i < curr.length() && curr.charAt(i) != '.' ; i++) {
				numHash++;
			}
			
			int len = nums.get(0);
			
			if (numHash >= len) {
				nums.remove(0);
				
				if (nums.isEmpty()) {
					if (len == curr.length()) {
						return ++count;							
					} else {
						return curr.substring(len).contains("#") ? count : ++count;
					}
				} else {
					if (curr.length() - 1 > len && curr.charAt(len) != '#') {
						if (curr.charAt(len) == '?') {
							char[] temp = curr.toCharArray();
							temp[len] = '.';
							return check(count, new String(temp).substring(len), new ArrayList<>(nums));
						} else {							
							return check(count, curr.substring(len), new ArrayList<>(nums));
						}
					} else {
						return count;
					}
				}
			} else {
				return count;
			}
		}
		
		return count;
	}

}
