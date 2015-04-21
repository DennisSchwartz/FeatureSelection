package wrapper;

/**
 * Enum for the different terminators in EvA2
 * 
 * @author Dennis Schwartz
 * 
 */
public enum EvA2TerminatorTypes {

	EVALUATIONTERMINATOR("Evaluations"), GENERATIONTERMINATOR("Generations"), FITNESSCONVERGENCETERMINATOR(
			"Fitness conversion");

	final String name; // String that is e.g shown if we initialize a ComboBox
						// with enum

	private EvA2TerminatorTypes(String name) {
		this.name = name;
	}
	
	/**
	 * toString() returns name of the enum item
	 */
	public String toString() {
		
		return name;
		
	}

	/**
	 * Get type by index of enum
	 * @param i The index
	 * @return	TerminatorType
	 */
	public static EvA2TerminatorTypes getTypeByIndex(int i) {

		EvA2TerminatorTypes[] types = EvA2TerminatorTypes.values();

		return types[i];

	}
	
	/**
	 * Get type by name
	 * @param name Name of the TerminatorType
	 * @return TerminatorType
	 */
	public static EvA2TerminatorTypes getTypeByName(String name) {
		for (EvA2TerminatorTypes pt : EvA2TerminatorTypes.values()) {
			if (pt.name.equals(name)) {
				return pt;
			}
		}
		return null;
	}

}
