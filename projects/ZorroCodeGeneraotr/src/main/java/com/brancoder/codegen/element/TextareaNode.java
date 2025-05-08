package com.brancoder.codegen.element;

import org.springframework.util.StringUtils;

public class TextareaNode extends AbstractInputNode implements INode{

	public TextareaNode() {
		super();
	}
	
	public TextareaNode(String mode) {
		super(mode);
	}
	
	@Override
	public String buildControl(String inputAttrStr) {

		StringBuffer sb = new StringBuffer("");		
		sb.append("<textarea rows=\"2\" nz-input");
		sb.append(inputAttrStr);
		sb.append(">");
		if (StringUtils.hasText(this.getDefaultValue())) {
			sb.append(String.format("%s\"", this.getDefaultValue()));
		}	
		sb.append("</textarea>");			
	
		return sb.toString();
	}

}
