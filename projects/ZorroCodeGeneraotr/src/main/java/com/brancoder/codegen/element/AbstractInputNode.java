package com.brancoder.codegen.element;
import java.util.ArrayList;
import java.util.List;

import org.springframework.util.StringUtils;

import com.brancoder.codegen.util.CommonUtil;

import lombok.*;

@Getter
@Setter
public abstract class AbstractInputNode extends AbstractNode implements INode{
//	private String controlName = "";
//	private String formControlName = ""; 
//	private String controlType = "text";
//	private String defaultValue = ""; 
//	private boolean required = false;
//	private String placeholder;
//	private String body = "";
//	private String hint = "";
//	private String appearance;
//	private String message="";
	private List childNodes = new ArrayList();
	
	protected AbstractInputNode() {
		super();
	}	
	protected AbstractInputNode(String mode) {
		super(mode);
	}
	
	
	public void appendChild(String str) {
		this.childNodes.add(str);
	}

	public String buildInputAttribue() {

		StringBuffer sb = new StringBuffer("");
		sb.append(""
				+ (StringUtils.hasText(this.formControlName) ? String.format(" formControlName=\"%s\" ", CommonUtil.toCamelCase(this.formControlName)): "")
				+ (StringUtils.hasText(this.controlName) ? String.format(" controlName=\"%s\" ", CommonUtil.toCamelCase(this.controlName)) : "")
				+ (StringUtils.hasText(this.placeholder) ? String.format(" placeholder=\"%s\" ", CommonUtil.toCamelCase(this.placeholder)) : "")				
				+ (this.required ? " required" :"")			
				+ "" );

		return sb.toString();

	}

	
	public abstract String buildControl(String inputAttrStr);


	
}
