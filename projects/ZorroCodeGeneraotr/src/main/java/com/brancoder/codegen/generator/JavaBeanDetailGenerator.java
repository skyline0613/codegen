package com.brancoder.codegen.generator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import com.brancoder.codegen.util.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class JavaBeanDetailGenerator {
	private static Logger logger = Logger.getLogger(JavaBeanDetailGenerator.class);
	private String className;
	private String javaPackage;
	private String javaTargetPath;
	private StringBuffer sb = new StringBuffer("");
	private String basePackage;
	private Node rootNode;
	
	
	public static void main(String[] args) {


	}
	
	/**
	 * @param className
	 * @param javaPackage
	 * @param javaTargetPath
	 * @param rootNode
	 */
	public JavaBeanDetailGenerator(String className, String javaPackage, String javaTargetPath, Node rootNode) {
		this.className = className;
		this.javaPackage = javaPackage;
		this.javaTargetPath = javaTargetPath;
		this.rootNode = rootNode;
	}
	
	public void generate() throws IOException {

		PropertyProvider propertyProvider = PropertyProvider.instance();
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder;
		String srcStr = null;

		//dBuilder = dbFactory.newDocumentBuilder();
		//Document doc = dBuilder.parse(xmlFile);
		//doc.getDocumentElement().normalize();
		//System.out.println("Root element :"
		//		+ doc.getDocumentElement().getNodeName() + "\n");
		
		this.basePackage = propertyProvider.getValue("jt400.generate.java.basePackage");

		srcStr = "" + "package "+ javaPackage +";\n" 

				+ "import java.util.List;\n" 
				+ "import lombok.Data;\n"
				+ "import lombok.EqualsAndHashCode;\n"
				+ "import lombok.ToString;\n"
				+ "import " + this.basePackage + ".annotation.PcmlTag;\n"
				+ "@EqualsAndHashCode\n" 
				+ "@ToString\n\n"
				+"public @Data class "
				+ className + " {";
				
		sb.append(srcStr);
		this.dumpTree(rootNode);
		sb.append("\n}");


		String destFile = String.format("%s\\%s.java", javaTargetPath , className);
		File descFile;
		try {
			descFile = new File(destFile);
			FileWriter fileWriter = new FileWriter(descFile);
			fileWriter.write(sb.toString());
			fileWriter.flush();
			fileWriter.close();
			logger.info(String.format("Generate File:[%s] success!", destFile));
		} catch (IOException e) {
			logger.error(String.format("Generate File:[%s] fail!", destFile), e);
			throw e;
		}		
		
		
	}

	public void dumpTree(Node node) {
		if (node.getChildNodes() == null) {
			return ;
		}
		NodeList nodeList = node.getChildNodes();

		for (int count = 0; count < nodeList.getLength(); count++) {

			Node tempNode = nodeList.item(count);

			// make sure it's element node.
			if (tempNode.getNodeType() == Node.ELEMENT_NODE) {
				
				String nodeCount = tempNode.getAttributes().getNamedItem(
						"count") == null ? "" : tempNode.getAttributes()
						.getNamedItem("count").getNodeValue();				
				String fieldTag = tempNode.getAttributes().getNamedItem("name").getNodeValue();
				
				if (tempNode.getNodeName().equals("data")) {
					String output = this.getPath(tempNode);


					output += "\n\tprivate ";
					if (!"".equals(nodeCount)) {
						output += "List<String>";
					} else {
						output += "String";
					}

					output += " "
							+ CommonUtil.toCamelCase(fieldTag) ;
					output += " = null;";

					this.sb.append(output);
				}else if(tempNode.getNodeName().equals("struct") && !"".equals(nodeCount)){

					System.out.println(tempNode.getAttributes()
							.getNamedItem("name").getNodeValue());
					this.sb.append("\n"+this.getPath(tempNode));
					this.sb.append("\n\tprivate List<"+fieldTag+"> "+CommonUtil.toCamelCase(fieldTag)+";");
				}else {

					this.sb.append("");
				}
				dumpTree(tempNode);

			}
			
		}
	}

	public String getPath(Node node) {
		NamedNodeMap attrs = node.getAttributes();
		String nodeName = attrs.getNamedItem("name")
				.getNodeValue();
		String nodeCount = attrs.getNamedItem("count") == null ? ""
				: attrs.getNamedItem("count").getNodeValue();
		String len = attrs.getNamedItem("length") == null ? ""
				: attrs.getNamedItem("length").getNodeValue();		
		String tmpStr = nodeName;
		while (node.getParentNode().getAttributes().getNamedItem("name") != null) {
			String eleName1 = node.getParentNode().getAttributes()
					.getNamedItem("name").getNodeValue();
			tmpStr = eleName1 + '.' + tmpStr;
			node = node.getParentNode();
		}
		tmpStr = tmpStr.substring(tmpStr.indexOf(".")+1);
		tmpStr = "\n\t@PcmlTag(value=\"" + tmpStr + "\"";
		if (nodeCount != "") {
			tmpStr += " ,size=" + nodeCount + "";
		}else if(len != ""){
			tmpStr += " ,length="+len;
		}

		tmpStr += ")";

		return tmpStr;
	}


}
