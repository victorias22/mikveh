package utilities;

public class Mishap {
	private boolean fixable;
	private double reductionFactor;
	private int turnsToFix;
	
	/**
	 * Mishap constructor
	 * @param fixable {@code boolean}
	 * @param turnsToFix {@code int}
	 * @param reductionFactor {@code double}
	 */
	public Mishap(boolean fixable, int turnsToFix, double reductionFactor) {
		setFixable(fixable);
		setReductionFactor(reductionFactor);
		setTurnsToFix(turnsToFix);
	}
	
	/**
	 * Decreases 1 turn from turns to fix
	 */
	public void nextTurn() {
		if(this.fixable)
			setTurnsToFix(this.turnsToFix - 1);
	}
	
	/**
	 * @return mishap as a String
	 */
	public String toString() {
		return String.format("(%b, %d, %.2f)", this.fixable, this.turnsToFix, this.reductionFactor);
	}
	
	/**
	 * @return if mishap is fixable as a boolean
	 */
	public boolean getFixable() { return this.fixable; }
	/**
	 * @return reduction factor as a double
	 */
	public double getReductionFactor() { return this.reductionFactor; }
	/**
	 * @return turns left to fix mishap as int
	 */
	public int getTurnsToFix() { return this.turnsToFix; }
	
	/**
	 * Sets if the mishap is fixable
	 * @param fixable {@code boolean}
	 */
	private void setFixable(boolean fixable) {
		this.fixable = fixable;
	}
	
	/**
	 * Sets the mishap reduction factor
	 * @param reductionFactor {@code double}
	 * @return True if set, else False
	 */
	private boolean setReductionFactor(double reductionFactor) {
		if(reductionFactor < 0) {
			this.reductionFactor = 0;
			return false;
		}
		this.reductionFactor = reductionFactor;
		return true;
	}
	/**
	 * Sets the mishaps turns to fix mishap
	 * @param reductionFactor {@code int}
	 * @return True if set, else False
	 */
	private boolean setTurnsToFix(int turnsToFix) {
		if(turnsToFix < 0) {
			this.turnsToFix = 0;
			return false;
		}
		this.turnsToFix = turnsToFix;
		return true;
	}
}
