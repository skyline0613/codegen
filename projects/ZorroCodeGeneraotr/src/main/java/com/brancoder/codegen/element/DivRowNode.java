package com.brancoder.codegen.element;

import org.springframework.util.StringUtils;

public class DivRowNode extends AbstractNode implements INode{


	private StringBuffer sb = new StringBuffer("");
	

	public void appenChild(INode node) {
		super.childNodes.add(node);
	}
	@Override
	public String buildInputAttribue() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	protected String buildControl(String inputAttrStr) {
		sb.append("<div nz-row [nzGutter]=\"24\" ");
		if (StringUtils.hasText(this.getCssClass())) {
			sb.append(String.format(" class=\"%s\" ",this.getCssClass()));
		}
		sb.append(">\n");
		for(Object obj : super.childNodes) {		
			sb.append(((INode)obj).build());
		}
		sb.append("</div>\n");
		return sb.toString();
	}
	@Override
	public String build() {
		return this.buildControl(this.buildInputAttribue());
	}
	

	
}
