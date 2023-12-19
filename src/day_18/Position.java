package day_18;

/**
 * Positionsklasse
 * 
 * @author Yanik Recke
 */
class Position {
	private long x = 0;
	private long y = 0;
	
	/**
	 * Konstruktor, setzt x und y.
	 * 
	 * @param x
	 * @param y
	 */
	public Position(long x, long y) {
		this.x = x;
		this.y = y;
	}
	
	
	/**
	 * Getter für x.
	 * 
	 * @return - x-Koordinate
	 */
	public long getX() {
		return this.x;
	}
	
	/**
	 * Getter für y.
	 * 
	 * @return - y-Koordinate
	 */
	public long getY() {
		return this.y;
	}
	
	
	/**
	 * Nachbarposition in übergebene Richtung
	 * berechnen.
	 * 
	 * @param d - Richtung
	 * @return - neue Position
	 */
	public Position getNeighbour(Direction d) {
		return switch(d) {
			case TOP -> new Position(this.x, this.y - 1);
			case RIGHT -> new Position(this.x + 1, this.y);
			case DOWN -> new Position(this.x, this.y + 1);
			default -> new Position(this.x - 1, this.y);
		};
	}
	
	
	@Override
	public boolean equals(Object obj) {
		return obj != null && obj instanceof Position && ((Position) obj).getX() == this.x && ((Position) obj).getY() == this.y;
	}
	
	@Override
	public int hashCode() {
	    long result = x;
	    result = 31 * result + y;
	    return (int) result;
	}
	
	@Override
	public String toString() {
		return "(" + this.x + "," + this.y + ")";
	}
}
