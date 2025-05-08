package com.brancoder.codegen.element;

import org.springframework.util.StringUtils;

public class CheckboxNode extends AbstractIMultiValueNode implements INode {

	public CheckboxNode() {
		super();
	}

	public CheckboxNode(String mode) {
		super(mode);
	}

	@Override
	public String buildInputAttribue() {
		StringBuffer sb = new StringBuffer("");
		if(super.getChildNodes().size()==0) {
		
		}else {
			if (StringUtils.hasText(this.formControlName)) {
				sb.append(String.format("formControlName=\"%s\" ", this.formControlName));
			}
			
			if(StringUtils.hasText(this.cssClass)) {
				sb.append(String.format("class=\"%s\" ", this.cssClass));
				
			}
		
			if (StringUtils.hasText(this.controlName)) {
				sb.append(String.format("name=\"%s\" ", this.controlName));
			}
			if (StringUtils.hasText(this.placeholder)) {
				sb.append(String.format("placeholder=\"%s\" ", this.placeholder));
			}
			if (this.isRequired()) {
				sb.append("nzRequired ");
			}
		}
		return sb.toString();
	}

	@Override
	public String buildControl(String inputAttrStr) {
		StringBuffer sb = new StringBuffer("");

		sb.append(inputAttrStr);
		if(super.getChildNodes().size()==0) {

				sb.append("<label nz-checkbox ");
				if (StringUtils.hasLength(super.getCssClass()))
					sb.append("class=" + super.getCssClass());
				if (StringUtils.hasText(this.formControlName)) 
					sb.append(String.format("formControlName=\"%s\" ", this.formControlName));			
				sb.append(">" + super.getLabel() + "");
				sb.append("</label>\n");

		}else {
			for (Object obj : super.getChildNodes()) {
				String[] arr = obj.toString().split(":");
				if (arr == null)
					continue;
				sb.append("<label nz-checkbox nzValue=\"B\" ");
				if (StringUtils.hasLength(super.getCssClass()))
					sb.append("class=" + super.getCssClass());
				if (arr.length == 2) {
					sb.append(">" + arr[1].trim() + " ");
					sb.append("");
				} else {
					sb.append(">" + arr[0].trim() + " ");
				}
				sb.append("</label>\n");
			}			
		}

		return sb.toString();
	}
}
