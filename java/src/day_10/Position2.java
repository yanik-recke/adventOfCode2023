package day_10;

/**
 * Positionsklasse
 * 
 * @author Yanik Recke
 */
class Position2 {
	private double x = 0;
	private double y = 0;
	
	/**
	 * Konstruktor, setzt x und y.
	 * 
	 * @param x
	 * @param y
	 */
	public Position2(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	
	/**
	 * Getter für x.
	 * 
	 * @return - x-Koordinate
	 */
	public double getX() {
		return this.x;
	}
	
	/**
	 * Getter für y.
	 * 
	 * @return - y-Koordinate
	 */
	public double getY() {
		return this.y;
	}
	
	
	@Override
	public boolean equals(Object obj) {
		return obj != null && obj instanceof Position2 && ((Position2) obj).getX() == this.x && ((Position2) obj).getY() == this.y;
	}
	
	@Override
	public int hashCode() {
		long bits = Double.doubleToLongBits(this.x);
		return (int) (bits ^ (bits >>> 32));
	}
	
	@Override
	public String toString() {
		return this.x + "|" + this.y;
	}
}
