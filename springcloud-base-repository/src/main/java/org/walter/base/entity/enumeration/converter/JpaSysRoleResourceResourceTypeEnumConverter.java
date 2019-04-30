package org.walter.base.entity.enumeration.converter;

import javax.persistence.AttributeConverter;

import org.walter.base.entity.JpaSysRoleResource;

public class JpaSysRoleResourceResourceTypeEnumConverter implements AttributeConverter<JpaSysRoleResource.ResourceTypeEnum, String> {

	@Override
	public String convertToDatabaseColumn(JpaSysRoleResource.ResourceTypeEnum resourceTypeEnum) {
		return resourceTypeEnum.getCode();
	}

	@Override
	public JpaSysRoleResource.ResourceTypeEnum convertToEntityAttribute(String resourceType) {
		return JpaSysRoleResource.ResourceTypeEnum.valueOf(resourceType);
	}
}
