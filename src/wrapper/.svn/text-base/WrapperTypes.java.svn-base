package wrapper;
	
/**	 
 *Enum that represents the different Wrappers that can be choosen to process feature Data 
 *  
 * @author julianschwab
 *
 */
public enum WrapperTypes {
	
	FORWARDSELECTION("Forward Selection"), EVA("EvA2");
	
	final String name; //String that is e.g shown if we initialize a ComboBox with enum
	
	/**
	 * Constructor that sets Name
	 * @param name
	 */
	private WrapperTypes(String name) {
		
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
	public static WrapperTypes getTypeByIndex(int i) {

		WrapperTypes[] types = WrapperTypes.values();

		return types[i];

	}
	
	public static WrapperTypes getTypeByName(String name) {
		for (WrapperTypes pt : WrapperTypes.values()) {
			if (pt.name.equals(name)) {
				return pt;
			}
		}
		return null;
	}
}
