package com.brancoder.codegen.element;

import org.springframework.util.StringUtils;

import com.brancoder.codegen.util.CommonUtil;


public class SelectboxNode extends AbstractIMultiValueNode implements INode{

	public SelectboxNode() {
		super();
	}
	public SelectboxNode(String mode) {
		super(mode);
	}
	
	@Override
	public String buildControl(String inputAttrStr) {
		StringBuffer sb = new StringBuffer("");

		sb.append("<nz-select nzPlaceHolder=\"Choose\" nzAllowClear");
		if (StringUtils.hasText(this.getFormControlName())) {		
			sb.append(" formControlName=\""+ CommonUtil.toCamelCase(this.getFormControlName())+"\"");
		}		
		if (StringUtils.hasText(this.getDefaultValue())) {
			sb.append(String.format(" value=\"%s\" ", this.getDefaultValue()));
		}		
		
		sb.append(inputAttrStr);		
		sb.append(">\n");
		
		if (super.getChildNodes().size() > 0) {

			for (Object obj : super.getChildNodes()) {
				String[] arr = obj.toString().split(":");
				if (arr != null) {
					if (arr.length == 2) {
						sb.append("<nz-option nzValue=\"" + arr[0] + "\" nzLabel=\"" + arr[1].trim() + "\"></nz-option>\n");
					} else {
						sb.append("<nz-option nzValue=\"" + arr[0] + "\" nzLabel=\"" + arr[0].trim() + "\"></nz-option>\n");					}
				}
			}
		}
		sb.append("</nz-select>\n");			
		return sb.toString();
	}
}
