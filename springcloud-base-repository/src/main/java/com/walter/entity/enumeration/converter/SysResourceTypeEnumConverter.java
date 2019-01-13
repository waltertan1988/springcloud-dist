package com.walter.entity.enumeration.converter;

import javax.persistence.AttributeConverter;

import com.walter.entity.enumeration.SysResourceTypeEnum;

public class SysResourceTypeEnumConverter implements AttributeConverter<SysResourceTypeEnum, String> {

	@Override
	public String convertToDatabaseColumn(SysResourceTypeEnum sysResourceTypeEnum) {
		return sysResourceTypeEnum.getCode();
	}

	@Override
	public SysResourceTypeEnum convertToEntityAttribute(String sysResourceType) {
		return SysResourceTypeEnum.valueOf(sysResourceType);
	}
}
