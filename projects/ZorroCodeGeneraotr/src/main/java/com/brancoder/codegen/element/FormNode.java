package com.brancoder.codegen.element;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.StringUtils;

public class FormNode implements INode{

	
	protected List childNodes = new ArrayList();
	
	public void appenChild(INode row) {
		this.childNodes.add(row);
	}


	protected String buildControl() {
		StringBuffer sb = new StringBuffer("");
		//sb.append("<div");
//		if (StringUtils.hasText(this.getCssClass())) {
//			sb.append(String.format(" class=\"%s\" ",this.getCssClass()));
//		}
//	
//		sb.append(">\n");
		
		sb.append("<form nz-form #f=\"ngForm\" [formGroup]=\"validateForm\" [se-container]=\"3\" gutter=\"32\" ingoreDirty");
//		if (StringUtils.hasText(this.getCssClass())) {
//			sb.append(String.format(" class=\"%s\" ",this.getCssClass()));
//		}
		sb.append(" (ngSubmit)=\"submitForm()\">\r\n");
		
		for(Object obj : this.childNodes) {	
			if(obj == null)
				continue;
			
			try {
				sb.append(((INode)obj).build());
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		sb.append("\r\n<div style=\"width: 100%; text-align: center\">\r\n"
				+ "<button nz-button [nzType]=\"'primary'\">儲存</button>\r\n"
				+ "</div>\r\n"
				+ "</form>\r\n");
		
		//sb.append("</div>\n");
		return sb.toString();
	}



	public String build() {
		return this.buildControl();
	}


	public String buildInputAttribue() {
		return "";
	}

	
}
