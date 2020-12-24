package com.tfc.data.tproperties;

public class TPropArray extends com.tfc.data.tproperties.TPropObject {
	private int index = 0;
	
	public TPropArray(com.tfc.data.tproperties.TPropObject parent, String key) {
		super(parent, key);
	}
	
	public TPropArray() {
	}
	
	public void add(com.tfc.data.tproperties.TPropObject val) {
		set((index++) + "", val);
	}
	
	public com.tfc.data.tproperties.TPropObject get(int index) {
		return get("" + index);
	}
	
	public int getSize() {
		return index;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		
		if (this.getKey() != null) builder.append(this.getKey()).append(" are\n");
		
		for (com.tfc.data.tproperties.TPropObject object : getObjects().values()) {
			for (int i = 0; i < getParentCount(); i++) builder.append(" ");
			builder.append(object.toString()).append("\n");
		}
		
		return builder.toString();
	}
	
	public com.tfc.data.tproperties.TPropObject[] toArray() {
		return getObjects().values().toArray(new com.tfc.data.tproperties.TPropObject[0]);
	}
}
