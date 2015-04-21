package wrapper.crossvalidation.classifier;

/**	 
 *Enum that represents the different Classiefiers that can be choosen to process feature Data 
 *  
 * @author julianschwab
 *
 */

public enum ClassifierTypes {
	
	

	
	CSVC("CSVC-linear");

	
	final String name;
	
	/**
	 * Constructor
	 * @param name
	 */
	private ClassifierTypes (String name) {
		
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
	public static ClassifierTypes getTypeByIndex(int i) {

		ClassifierTypes[] types = ClassifierTypes.values();

		return types[i];

	}
	
}
