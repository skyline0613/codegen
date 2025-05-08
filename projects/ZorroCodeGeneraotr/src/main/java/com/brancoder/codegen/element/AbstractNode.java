package com.brancoder.codegen.element;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.StringUtils;

import com.brancoder.codegen.util.CommonUtil;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class AbstractNode {
	protected String label = ""; // Label
	protected String cssClass;
	protected String name;
	protected String mode;
	protected String style;
	protected String appearance;
	protected List childNodes = new ArrayList();
	protected String seExtenProperty="";
	protected String controlName = "";
	protected String formControlName = "";
	// private String cssClass = "";
	protected String controlType = "text"; // html control type
	protected String defaultValue = ""; // 預設值
	protected boolean required = false;
	protected String placeholder;
	protected String body = "";
	protected String hint = "";
	protected String message = "";

	protected AbstractNode() {

	}

	protected AbstractNode(String mode) {
		this.mode = mode;
	}

	public void appendChild(INode node) {
		this.childNodes.add(node);
	}

	protected String buildHeader() {
		StringBuffer sb = new StringBuffer("");
		return sb.toString();
	}

	protected String buildLabel() {
		StringBuffer sb = new StringBuffer("");
		sb.append("\r\n<se"
				+ (StringUtils.hasText(this.label) ? String.format(" label=\"%s\" ", this.label) : "")
				+ (StringUtils.hasText(this.message) ? String.format(" error=\"%s\"", this.message) : "")		
				+ (StringUtils.hasText(this.seExtenProperty) ? String.format(" %s", this.seExtenProperty) : "")					
				+ (this.required ? " required" :"")			
				+ ">\r\n" );

		return sb.toString();
	}

	protected String buildTail() {
		StringBuffer sb = new StringBuffer(" ");
		sb.append( "</se>\r\n");
		return sb.toString();
	}


	public String build() {
		return this.buildHeader() + this.buildLabel() 
				+ this.buildControl(this.buildInputAttribue()) + this.buildTail();
	}

	protected abstract String buildControl(String inputAttrStr);

	protected abstract String buildInputAttribue();

}
