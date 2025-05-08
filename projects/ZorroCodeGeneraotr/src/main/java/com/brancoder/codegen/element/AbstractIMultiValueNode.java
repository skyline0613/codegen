package com.brancoder.codegen.element;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.StringUtils;

import com.brancoder.codegen.util.CommonUtil;

import lombok.*;

@Getter
@Setter
public abstract class AbstractIMultiValueNode extends AbstractNode implements INode {


	public AbstractIMultiValueNode() {
		super();
	}

	public AbstractIMultiValueNode(String mode) {
		super(mode);
	}

	public void appendChild(String str) {
		this.childNodes.add(str);
	}

	public void appendChilds(String[] arr) {
		if (arr == null)
			return;

		for (String str : arr) {
			this.childNodes.add(str);
		}
	}

	/**

	public String buildInputAttribue() {
		StringBuffer sb = new StringBuffer("");
		if (StringUtils.hasText(this.formControlName)) {
			sb.append(String.format("formControlName=\"%s\" ", CommonUtil.toCamelCase(this.getFormControlName())));
		}
		
		if(StringUtils.hasText(this.cssClass)) {
			sb.append(String.format("class=\"%s\" ", this.cssClass));
		}
		if (StringUtils.hasText(this.getControlName())) {
			sb.append(String.format("id=\"%s\" ", CommonUtil.toCamelCase(this.getControlName())));
		}	
		if (StringUtils.hasText(this.controlName)) {
			sb.append(String.format("name=\"%s\" ", CommonUtil.toCamelCase(this.getControlName())));
		}
		if (StringUtils.hasText(this.placeholder)) {
			sb.append(String.format("placeholder=\"%s\" ", this.placeholder));
		}
		if (this.isRequired()) {
			sb.append("nzRequired ");
		}
		return sb.toString();
	}
	*/
	
	public String buildInputAttribue() {

		StringBuffer sb = new StringBuffer("");
		sb.append(""
				+ (StringUtils.hasText(this.formControlName) ? String.format(" formControlName=\"%s\" ", CommonUtil.toCamelCase(this.formControlName)) : "")
				+ (StringUtils.hasText(this.controlName) ? String.format(" controlName=\"%s\" ", CommonUtil.toCamelCase(this.controlName)) : "")
				+ (StringUtils.hasText(this.placeholder) ? String.format(" placeholder=\"%s\" ", this.placeholder) : "")	
				+ (StringUtils.hasText(this.cssClass) ? String.format(" class=\"%s\" ", this.cssClass) : "")					
				+ (this.required ? " required" :"")			
				+ "" );

		return sb.toString();

	}	
	
	

	public abstract String buildControl(String inputAttrStr);


}
