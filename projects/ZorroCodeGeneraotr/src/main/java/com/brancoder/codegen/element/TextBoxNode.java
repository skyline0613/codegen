package com.brancoder.codegen.element;

import org.springframework.util.StringUtils;

import com.brancoder.codegen.util.CommonUtil;

public class TextBoxNode extends AbstractInputNode implements INode {

	public TextBoxNode() {
		super();
	}
	
	public TextBoxNode(String mode) {
		super(mode);
	}
	
	@Override
	public String buildControl(String inputAttrStr) {
		StringBuffer sb = new StringBuffer("");
		//sb.append("<div nz-col [nzSpan]=\"5\">");
		
		sb.append("<input nz-input ");
//		if (StringUtils.hasText(this.getFormControlName())) {
//			sb.append(String.format("formControlName=\"%s\" ", CommonUtil.toCamelCase(this.getFormControlName()) ));
//		}
		
//		if (StringUtils.hasText(this.getControlName())) {
//			sb.append(String.format("name=\"%s\" ", this.getControlName()));
//		}
				
		//if (StringUtils.hasText(this.getDefaultValue()))
		//	sb.append(String.format("[ngModel]=\"'%s'\" ", this.getDefaultValue()));
		sb.append(inputAttrStr);
		sb.append(" />\n");
		
		//sb.append("</div>");
		return sb.toString();
	}

}
