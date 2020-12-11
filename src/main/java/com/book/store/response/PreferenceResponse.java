
package com.book.store.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PreferenceResponse {
	
	private Boolean isDown;
	
	private String downReason;
	
	private Integer latestVersionCode;
	
	private String latestVersionMessage;

}
