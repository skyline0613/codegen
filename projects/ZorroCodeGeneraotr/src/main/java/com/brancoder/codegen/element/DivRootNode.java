package com.brancoder.codegen.element;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.StringUtils;

public class DivRootNode extends AbstractNode{

	
	
	public void appenChild(DivRowNode row) {
		super.childNodes.add(row);
	}


	@Override
	protected String buildControl(String inputAttrStr) {
		StringBuffer sb = new StringBuffer("");
		//sb.append("<div");
//		if (StringUtils.hasText(this.getCssClass())) {
//			sb.append(String.format(" class=\"%s\" ",this.getCssClass()));
//		}
//	
//		sb.append(">\n");
		
		sb.append("<form nz-form #f=\"ngForm\" [formGroup]=\"validateForm\" [se-container]=\"3\" gutter=\"32\"  size=\"compact\" gutter=\"24\" ingoreDirty");
		if (StringUtils.hasText(this.getCssClass())) {
			sb.append(String.format(" class=\"%s\" ",this.getCssClass()));
		}
		sb.append(" (ngSubmit)=\"submitForm()\">\r\n");
		
		for(Object obj : super.childNodes) {		
			sb.append(((DivRowNode)obj).build());
		}
		
		sb.append("\n<button nz-button class=\"login-form-button login-form-margin\"\r\n"
				+ "    [nzType]=\"'primary'\">Submit</button>\r\n"
				+ "</form>\r\n");
		
		//sb.append("</div>\n");
		return sb.toString();
	}


	@Override
	protected String buildInputAttribue() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String build() {
		return this.buildControl(this.buildInputAttribue());
	}

	
}
