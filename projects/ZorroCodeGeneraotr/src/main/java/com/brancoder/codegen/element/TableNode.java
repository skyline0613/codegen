package com.brancoder.codegen.element;

import org.springframework.util.StringUtils;

public class TableNode extends AbstractNode{

	@Override
	public String build() {
		StringBuffer sb = new StringBuffer("");
		sb.append("<table");
		if(this.getName()!=null) {
			sb.append(String.format(" name=\"%s\" ",this.getName()));
		}
		if (StringUtils.hasText(this.getCssClass())) {
			sb.append(String.format(" class=\"%s\" ",this.getCssClass()));
		}		
		sb.append(">\n");
		for(Object obj : super.childNodes) {		
			sb.append(((INode)obj).build());
		}
		sb.append("</table>\n");
		return sb.toString();
	}
	public void appenChild(TrNode trNode) {
		this.childNodes.add(trNode);
	}
	@Override
	protected String buildControl(String inputAttrStr) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	protected String buildInputAttribue() {
		// TODO Auto-generated method stub
		return null;
	}

}
