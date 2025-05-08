package com.brancoder.codegen.element;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.StringUtils;

public class TrNode extends AbstractNode  implements INode{
	private String eleName="";
	private int columnLength = 0;
	
	//private List<AbstractNode> childNodes = new ArrayList<AbstractNode>();

	private StringBuffer sb = new StringBuffer("");
	
	

	public void appenChild(TdNode tdNode) {
		super.childNodes.add(tdNode);
	}
	@Override
	public String buildInputAttribue() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	protected String buildControl(String inputAttrStr) {
		sb.append("<tr");
		if (StringUtils.hasText(this.getCssClass())) {
			sb.append(String.format(" class=\"%s\" ",this.getCssClass()));
		}
		sb.append(">\n");
		for(Object obj : super.childNodes) {		
			sb.append(((TdNode)obj).build());
		}
		sb.append("</tr>\n");
		return sb.toString();
	}

	
}
