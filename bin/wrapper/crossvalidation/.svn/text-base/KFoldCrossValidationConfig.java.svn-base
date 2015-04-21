package wrapper.crossvalidation;

public class KFoldCrossValidationConfig extends CrossValidationConfigBase {
	
	public final static int FOLD = 2;

	private int fold = 2;

	/**
	 * @return the fold
	 */
	public int getFold() {
		return fold;
	}

	/**
	 * @param fold the fold to set
	 */
	public void setFold(int fold) {
		this.fold = fold;
	}

	protected String getCrossValInfo() {
		String res = "";
		res += "Cross Validation:\n";
		res += "   Fold:        " + this.fold + "\n";
		res += "   Random Seed: " + this.randomSeed + "\n";
		return res;
	}
	

}
