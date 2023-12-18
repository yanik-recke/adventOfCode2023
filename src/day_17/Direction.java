package day_17;

public enum Direction {
	TOP,
	RIGHT,
	DOWN,
	LEFT;
	
	Direction opp() {
		return switch (this) {
		case TOP -> DOWN;
		case RIGHT -> LEFT;
		case DOWN -> TOP;
		default -> RIGHT;
		};
	}
}
