package com.brancoder.codegen.element;


import org.springframework.util.StringUtils;

import com.brancoder.codegen.util.CommonUtil;

public class RadioControlNode extends AbstractIMultiValueNode implements INode{

	public RadioControlNode() {
		super();
	}
	
	public RadioControlNode(String mode) {
		super(mode);
	}


	@Override
	public String buildControl(String inputAttrStr) {
		StringBuffer sb = new StringBuffer("");
	
			sb.append("<nz-radio-group ");
			if (StringUtils.hasText(this.getFormControlName())) {		
				sb.append("formControlName=\""+ CommonUtil.toCamelCase(this.getFormControlName())+"\"");
			}
	//		if (StringUtils.hasText(this.getDefaultValue())) {
	//			sb.append(String.format("value=\"%s\" ", this.getDefaultValue()));
	//		}		
	//		
			sb.append(inputAttrStr);		
			sb.append(">\n");
			
			if (super.getChildNodes().size() > 0) {
	
				for (Object obj : super.getChildNodes()) {
					String[] arr = obj.toString().split(":");
					if (arr != null) {
						if (arr.length == 2) {
							sb.append("<label nz-radio nzValue=\"" + arr[0] + "\">" + arr[1].trim() + "</label>\n");
						} else {
							sb.append("<label nz-radio nzValue=\"" + arr[0] + "\">" + arr[0].trim() + "</label>\n");
						}
					}
				}
			}
			sb.append("</nz-radio-group>\n");		

		
		return sb.toString();
	}	
	
}