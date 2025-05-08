package com.brancoder.codegen.generator;

import java.util.List;

import com.brancoder.codegen.bean.TypescriptInterfaceBean;
import com.brancoder.codegen.bean.ZorroCtrlBean;
import com.brancoder.codegen.util.CommonUtil;

public class AngularCodeGenerator {
	
	
	public static String genTableTypescriptImports(String componentName) {
		StringBuffer sb = new StringBuffer("");
		sb.append("import { Component } from '@angular/core';\r\n"
				+ "import { NzTableModule } from 'ng-zorro-antd/table';\r\n\r\n"
				+ "%s\r\n\r\n"
				+ "@Component({\r\n"
				+ String.format("  selector: 'app-%s',\r\n", componentName)
				+ "  standalone: true,\r\n"
				+ "  imports: [NzTableModule],\r\n"
				+ String.format("  templateUrl: './%s.component.html',\r\n", componentName)
				+ String.format("  styleUrl: './%s.component.scss'\r\n", componentName)
				+ "})\r\n"
				+ String.format("export class %sComponent {\r\n", CommonUtil.toProgramName(componentName))
				+ "\r\n%s\r\n"
				+ "\tlistOfData: ItemData[] = [];\t //此行以Json取代 \r\n\r\n"
				+ "}");

	
		
		
		
		
		
		return sb.toString();
	}
		
	
	
	
	public static String genTypescriptInterfaceSource(List<TypescriptInterfaceBean> list) {
		
		StringBuffer sb = new StringBuffer("interface ItemData {\n");
		for (TypescriptInterfaceBean tib : list) {
			sb.append("\t" + CommonUtil.toCamelCase(tib.getVariableName(),false)+": "+tib.getVariableType()+";\n");
		}
		sb.append("}\n");
		
		return sb.toString();
	}
}
