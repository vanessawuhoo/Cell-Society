package simulation_type;

public abstract class Rule {
	
	/*
	 * Update cell states
	 */
	public abstract void updateCell();
	
	/*
	 * Update any general parameters
	 */
	public abstract void updateParameter();
	
	/*
	 * update cell parameters
	 */
	public abstract void updateCellParameters();
}
