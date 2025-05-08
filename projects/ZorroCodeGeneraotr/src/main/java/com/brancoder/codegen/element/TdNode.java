package com.brancoder.codegen.element;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.StringUtils;

public class TdNode extends AbstractNode implements INode{

	private int columnLength = 0;
	
	private List childNodes = new ArrayList();

	private StringBuffer sb = new StringBuffer("");
	
	public TdNode() {
		
	}
	
	public TdNode(String label, String controlName) {
//		MatControlNode cn = new MatControlNode("input");
//		cn.setLabel(label);
//		cn.setControlName(controlName);
//		this.childNodes.add(cn);
	}
	

	public void appenChild(INode node) {
		this.childNodes.add(node);
	}

	@Override
	public String buildInputAttribue() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String buildControl(String inputAttrStr) {
		sb.append("<td");
		if (StringUtils.hasText(this.getCssClass())) {
			sb.append(String.format(" class=\"%s\" ",this.getCssClass()));
		}
		sb.append(">\n");
		for(Object obj : this.childNodes) {
			INode n = (INode)obj;
			sb.append(n.build());
		}
		sb.append("</td>\n");
		return sb.toString();
	}

}
