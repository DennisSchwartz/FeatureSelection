package wrapper;

import dataStructures.FeatureData;

public class ForwardSelectionWrapperConfig extends WrapperConfigBase {

	/**
	 * @param data
	 */
	public ForwardSelectionWrapperConfig(FeatureData data) {
		super(data);
	}
	
	public ForwardSelectionWrapperConfig(WrapperConfigBase config) {
		super(config.getData());
		this.crossVal = config.crossVal;
		this.performance = config.performance;
		this.output = config.output;
	}

	public String getWrapperInfo() {
		String res = "";
		res += "Wrapper Type:\n";
		res += "Forward Selection";
		return res;
	}

}
