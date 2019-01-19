package org.walter.base.openapi;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
public class OpenApiResponse {
	@Getter @Setter private boolean isSuccess;
	@Getter @Setter private Object body;
	@Getter @Setter private ErrorCodeEnum errorCode;
	@Getter @Setter private String errorMessage;
	
	public enum ErrorCodeEnum{
		ERR_500("500", "Internal Server Error");
		
		@Getter private String code;
		@Getter private String desc;
		
		private ErrorCodeEnum(String code, String desc) {
			this.code = code;
			this.desc = desc;
		}
	}
}
