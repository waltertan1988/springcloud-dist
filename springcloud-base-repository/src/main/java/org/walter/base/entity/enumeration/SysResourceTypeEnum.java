package org.walter.base.entity.enumeration;

public enum SysResourceTypeEnum {

	MENU("MENU"),
	ACTION("ACTION");
	
	private String code;
	
	SysResourceTypeEnum(String code){
		this.code = code;
	}
	
	public String getCode() {
		return code;
	}

	@Override
	public String toString() {
		return code;
	}
	
	public boolean sameAs(SysResourceTypeEnum e) {
		return code.equals(e.getCode());
	}
}
