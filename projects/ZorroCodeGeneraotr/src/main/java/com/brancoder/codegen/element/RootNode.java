package com.brancoder.codegen.element;

import java.util.ArrayList;
import java.util.List;

public class RootNode extends AbstractNode {
	private String eleName;
	private String cobolPath;
	private String name;
	private String pic;
	private String body;
	private String picType;
	private int length;
	private List<AbstractNode> childNodes = new ArrayList<AbstractNode>();

	public RootNode(String eleName, String cobolPath, String context) {
		this.eleName = eleName;
		this.cobolPath = cobolPath;
		try {
			context = context.replaceAll(" +", " ");

			String[] rows = context.split("(\\r|\\n)");
			this.body = rows[0];

			context = context.substring(this.body.length());

			String[] ss = this.body.split(" ");
			for (int i = 0; i < ss.length; i++) {
				ss[i] = ss[i].trim();
			}
			this.name = ss[1].replace("-", "_").replace(".", "");

			ss = rows[1].trim().split(" ");

			String key = ss[0];

			String[] arr = context.split(key + " ");
			String[] arrayOfString1;
			int j = (arrayOfString1 = arr).length;
			for (int i = 0; i < j; i++) {
				String aa = arrayOfString1[i];
				if (aa.contains(".")) {
					// this.childNodes.add(new Node(key + " " + aa));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected String buildControl(String inputAttrStr) {

		StringBuffer sb = new StringBuffer("");
		StringBuffer bodyStr = new StringBuffer("");

		sb.append(String.format("<struct name=\"%s\" usage=\"inputoutput\">\n", new Object[] { this.name }));
		if (this.childNodes.size() > 0) {
			for (AbstractNode node : this.childNodes) {
				bodyStr.append(node.build());
			}
		}
		sb.append(bodyStr);

		return sb.toString();
	}

	@Override
	protected String buildInputAttribue() {
		// TODO Auto-generated method stub
		return null;
	}

}
