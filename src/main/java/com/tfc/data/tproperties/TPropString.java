package com.tfc.data.tproperties;

public class TPropString extends com.tfc.data.tproperties.TPropObject {
	private String val;
	private boolean hideKey = false;
	
	public TPropString(com.tfc.data.tproperties.TPropObject parent, String key) {
		super(parent, key);
	}
	
	public TPropString(com.tfc.data.tproperties.TPropObject parent, String key, String val) {
		super(parent, key);
		this.val = val;
	}
	
	public void set(String val) {
		this.val = val;
	}
	
	public String get() {
		return val;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		
		if (!hideKey) {
			for (int i = 0; i < getParentCount() - 1; i++) builder.append(" ");
			if (getKey() != null) builder.append(getKey()).append(" is ");
		}
		
		builder.append(val);
		
		return builder.toString();
	}
	
	public com.tfc.data.tproperties.TPropObject setHideKey() {
		hideKey = true;
		return this;
	}
}
