package com.walter.base.entity.converter;

import javax.persistence.AttributeConverter;

import com.walter.base.entity.JpaAclRoleResource;

public class JpaAclRoleResourceResourceTypeEnumConverter implements AttributeConverter<JpaAclRoleResource.ResourceTypeEnum, String> {

	@Override
	public String convertToDatabaseColumn(JpaAclRoleResource.ResourceTypeEnum resourceTypeEnum) {
		return resourceTypeEnum.getCode();
	}

	@Override
	public JpaAclRoleResource.ResourceTypeEnum convertToEntityAttribute(String resourceType) {
		return JpaAclRoleResource.ResourceTypeEnum.valueOf(resourceType);
	}
}
