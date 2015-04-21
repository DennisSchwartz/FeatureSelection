package wrapper.performance;

/**	 
 *Enum that represents the different Performancers that can be choosen to process feature Data 
 *  
 * @author julianschwab
 *
 */

public enum PerformanceTypes {

	
	MCC("Matthews Correlation"),ACCURACY("Accuracy");
	
	final String name; //String that is e.g shown if we initialize a ComboBox with enum
	
	/**
	 * Constructor
	 * @param name
	 */
	private PerformanceTypes(String name) {
		
		this.name = name;
	}
	
	/**
	 * toString() returns name of the enum item
	 */
	public String toString() {
		
		return name;
	}
	
	/**
	 * get type by index
	 */
	public static PerformanceTypes getTypeByIndex(int i) {

		PerformanceTypes[] types = PerformanceTypes.values();

		return types[i];

	}
	
	public static PerformanceTypes getTypeByName(String name) {
		for (PerformanceTypes pt : PerformanceTypes.values()) {
			if (pt.name.equals(name)) {
				return pt;
			}
		}
		return null;
	}
}
