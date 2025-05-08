package com.brancoder.codegen.element;

import org.springframework.util.StringUtils;

public class DatePickerNode extends AbstractInputNode implements INode {

	public DatePickerNode() {
		super();
	}
	
	public DatePickerNode(String mode) {
		super(mode);
	}
	
	@Override
	public String buildControl(String inputAttrStr) {
		StringBuffer sb = new StringBuffer("");
		//sb.append("<div nz-col [nzSpan]=\"5\">");
		
		sb.append("<nz-date-picker ");
		if (StringUtils.hasText(this.getFormControlName())) {
			sb.append(String.format("formControlName=\"%s\" ", this.getFormControlName()));
		}
		
//		if (StringUtils.hasText(this.getControlName())) {
//			sb.append(String.format("name=\"%s\" ", this.getControlName()));
//		}
				
		if (StringUtils.hasText(this.getDefaultValue()))
			sb.append(String.format("[ngModel]=\"'%s'\" ", this.getDefaultValue()));
		sb.append(inputAttrStr);
		sb.append("></nz-date-picker>\n");
		
		//sb.append("</div>");
		return sb.toString();
	}

}
