package com.brancoder.codegen.generator;

import java.util.List;

import org.springframework.util.StringUtils;

import com.brancoder.codegen.bean.TypescriptInterfaceBean;
import com.brancoder.codegen.bean.ZorroCtrlBean;
import com.brancoder.codegen.element.CheckboxNode;
import com.brancoder.codegen.element.DatePickerNode;
import com.brancoder.codegen.element.INode;
import com.brancoder.codegen.element.NumberNode;
import com.brancoder.codegen.element.RadioControlNode;
import com.brancoder.codegen.element.SelectboxNode;
import com.brancoder.codegen.element.TextBoxNode;
import com.brancoder.codegen.element.TextareaNode;
import com.brancoder.codegen.util.CommonUtil;

public class ZorroCodeGenerator {
	public static String genNzTableSource(List<TypescriptInterfaceBean> list, boolean isSortable) {
		
		StringBuffer sb = new StringBuffer("<nz-table #basicTable [nzData]=\"listOfData\" [nzHideOnSinglePage]=\"true\">");
		sb.append("  <thead>\r\n"
				+ "  <tr>\r\n");
		int inx = 0;
		for (TypescriptInterfaceBean tib : list) {
			sb.append("<th");
			if(isSortable) {
				sb.append(" nzColumnKey=\""+ CommonUtil.toCamelCase(tib.getVariableName(),false) +"\"");
				sb.append(" [nzSortFn]=\"listOfColumn["+ inx++ +"].compare\"");
				//sb.append(" [nzSortFn]=\"column.compare\" [nzSortPriority]=\"column.priority\"");
			}
			sb.append(">");
			sb.append(CommonUtil.toCamelCase(tib.getDisplayName(),false));
			sb.append("</th>\n");
		}
		sb.append("</tr>\n</thead>\n<tbody>\n");
		sb.append("@for (data of basicTable.data; track data) {\n");
		sb.append("<tr>\n");
		for (TypescriptInterfaceBean tib : list) {
			sb.append(String.format("<td>{{ data.%s }}</td>\n",CommonUtil.toCamelCase(tib.getVariableName(),false)));
		}	
		sb.append("</tr>\n");
		sb.append("}\n");
		sb.append("</tbody>\n"
				+ "</nz-table>\n\n\n");
		

		
		
		return sb.toString();
	}
	public static String genNzTableColumnSchemaSource(List<TypescriptInterfaceBean> list) {
		StringBuffer sb = new StringBuffer("");
		sb.append("listOfColumn = [\n");
		
		int inx = 0;
		for (TypescriptInterfaceBean tib : list) {
			String columnField = CommonUtil.toCamelCase(tib.getVariableName(),false);
			sb.append("{\n");
			sb.append("\ttitle:'"+ tib.getDisplayName()+"',\n");
			if("number".equals(tib.getVariableType())){
				sb.append("\tcompare: (a: ItemData, b: ItemData) => a."+ columnField+" - b."+ columnField +"  ,\n");			
			}else {
				sb.append("\tcompare: (a: ItemData, b: ItemData) => a."+ columnField+".localeCompare(b."+ columnField +")  ,\n");							
			}
			sb.append("\tpriority:"+inx++ +"\n");
			sb.append("},\n");
		}		
		sb.append("];");
		return sb.toString();
	}
			
	
	public static String genFormGroupSource(List<ZorroCtrlBean> list, String componentName) {
		StringBuffer sb = new StringBuffer("\n");
		//sb.append("export class XXXXComponent {\n");
		sb.append(String.format("export class %sComponent {\r\n", CommonUtil.toProgramName(componentName)));
		sb.append("\tvalidateForm: FormGroup;\n"
				+ "\tconstructor(private fb: FormBuilder) {\n");
		sb.append("\t\tthis.validateForm = this.fb.group({\n");
		for (ZorroCtrlBean cgb : list) {
			if(!cgb.getType().toLowerCase().equals("ctl")) 
				continue;
			
			sb.append("\t\t\t"+ CommonUtil.toCamelCase(cgb.getFormControlName()) +": ");			
			sb.append(" this.fb.control('"+ cgb.getDefault_value() +"', [");
			if(cgb.isRequired())
				sb.append("Validators.required");
			sb.append("]),\n");			
			
			
		}
		sb.append("\t});\r\n"
				+ "}\r\n");	
		
		
		sb.append("\tsubmitForm(): void {\r\n"
				+ "\tif (this.validateForm.valid) {\r\n"
				+ "\t\t      console.log('submit', this.validateForm.value);\r\n"
				+ "\t\t    } else {\r\n"
				+ "\t\t      Object.values(this.validateForm.controls).forEach(control => {\r\n"
				+ "\t\t        if (control.invalid) {\r\n"
				+ "\t\t          control.markAsDirty();\r\n"
				+ "\t\t          control.updateValueAndValidity({ onlySelf: true });\r\n"
				+ "\t\t        }\r\n"
				+ "\t\t      });\r\n"				
				+ "\t\t    }\r\n"				
				+ "\t}\r\n"				
				+ "\r\n"				
				);
		sb.append("}\n\n");		
		
		
		
		
		
		return sb.toString();
	}
	
	

	public static INode convertBeanToNode(ZorroCtrlBean cgb) {

		
		switch (cgb.getControlType()) {
		case "date":
			DatePickerNode dpn = new DatePickerNode();
			dpn.setLabel(cgb.getLabel());
			dpn.setFormControlName(cgb.getFormControlName());
			dpn.setControlName(cgb.getControlName());
			dpn.setRequired(cgb.isRequired());
			dpn.setCssClass(cgb.getCssClass());
			dpn.setSeExtenProperty(cgb.getDivCss_class());
			dpn.setPlaceholder(cgb.getPlaceholder());
			dpn.setDefaultValue(cgb.getDefault_value());
			dpn.setMessage(cgb.getMessage());			
			return dpn;
		case "text":
			TextBoxNode tbn = new TextBoxNode();
			tbn.setLabel(cgb.getLabel());
			tbn.setFormControlName(cgb.getFormControlName());
			tbn.setControlName(cgb.getControlName());
			tbn.setRequired(cgb.isRequired());
			tbn.setCssClass(cgb.getCssClass());
			tbn.setSeExtenProperty(cgb.getDivCss_class());
			tbn.setPlaceholder(cgb.getPlaceholder());
			tbn.setDefaultValue(cgb.getDefault_value());
			tbn.setMessage(cgb.getMessage());
			return tbn;
		case "number":
			NumberNode nbr = new NumberNode();
			nbr.setLabel(cgb.getLabel());
			nbr.setFormControlName(cgb.getFormControlName());
			nbr.setControlName(cgb.getControlName());
			nbr.setRequired(cgb.isRequired());
			nbr.setCssClass(cgb.getCssClass());
			nbr.setSeExtenProperty(cgb.getDivCss_class());
			nbr.setPlaceholder(cgb.getPlaceholder());
			nbr.setDefaultValue(cgb.getDefault_value());
			nbr.setMessage(cgb.getMessage());
			return nbr;			
		case "select":
			SelectboxNode sn = new SelectboxNode();
			sn.setLabel(cgb.getLabel());
			sn.setRequired(cgb.isRequired());
			sn.setControlName(cgb.getControlName());
			sn.setFormControlName(cgb.getControlName());
			sn.setCssClass(cgb.getCssClass());
			sn.setRequired(cgb.isRequired());
			sn.setSeExtenProperty(cgb.getDivCss_class());
			sn.setDefaultValue(cgb.getDefault_value());
			sn.setMessage(cgb.getMessage());
			if (StringUtils.hasText(cgb.getList())) {
				String[] arr = cgb.getList().split("\n");
				sn.appendChilds(arr);
			}
			return sn;
		case "radio":
			RadioControlNode rcn = new RadioControlNode();
			rcn.setLabel(cgb.getLabel());
			rcn.setRequired(cgb.isRequired());
			rcn.setControlName(cgb.getControlName());
			rcn.setFormControlName(cgb.getControlName());
			rcn.setCssClass(cgb.getCssClass());
			rcn.setSeExtenProperty(cgb.getDivCss_class());
			rcn.setDefaultValue(cgb.getDefault_value());
			rcn.setMessage(cgb.getMessage());
			if (StringUtils.hasText(cgb.getList())) {
				String[] arr = cgb.getList().split("\n");
				rcn.appendChilds(arr);
			}
			return rcn;
		case "checkbox":
			CheckboxNode cn = new CheckboxNode();
			cn.setLabel(cgb.getLabel());
			cn.setCssClass(cgb.getCssClass());
			cn.setRequired(cgb.isRequired());
			cn.setFormControlName(cgb.getControlName());
			cn.setSeExtenProperty(cgb.getDivCss_class());
			cn.setDefaultValue(cgb.getDefault_value());
			cn.setMessage(cgb.getMessage());
			if (StringUtils.hasText(cgb.getList())) {
				String[] arr = cgb.getList().split("\n");
				cn.appendChilds(arr);
			}
			return cn;			
			
		case "textarea":
			TextareaNode tn = new TextareaNode();
			tn.setDefaultValue("");
			tn.setRequired(cgb.isRequired());
			tn.setFormControlName(cgb.getControlName());
			tn.setLabel(cgb.getLabel());
			tn.setPlaceholder(cgb.getPlaceholder());
			tn.setCssClass(cgb.getCssClass());
			tn.setSeExtenProperty(cgb.getDivCss_class());
			tn.setDefaultValue(cgb.getDefault_value());
			tn.setMessage(cgb.getMessage());
			return tn;
		default:

		}

		return null;		
		
	}
	
	public static String genTypescriptStructureSource(String componentName) {
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
		
	
	
	public static String genNzFormImportString(String componentName) {

		StringBuffer sb = new StringBuffer("");
		sb.append("import { Component, OnInit } from '@angular/core';\n"
				+ "import { FormGroup, FormBuilder, Validators, ReactiveFormsModule } from '@angular/forms';\n"
				+ "import { SHARED_IMPORTS } from '@shared';\n"				
				+ "@Component({\r\n"
				+ String.format("  selector: 'app-%s',\r\n", componentName)
				+ "  standalone: true,\r\n"
				+ "imports: [SHARED_IMPORTS,FormControl, FormBuilder, ReactiveFormsModule,Validators, \r\n"
				+ "    ],\r\n"
				+ String.format("  templateUrl: './%s.component.html',\r\n", componentName)
				+ String.format("  styleUrl: './%s.component.scss'\r\n", componentName)
				+ "})\r\n"							
				);
		
		
		
		return sb.toString();
	}		
	
	public static String genAngularImportString() {

		StringBuffer sb = new StringBuffer("");
		sb.append("import { Component, OnInit } from '@angular/core';\n"
				+ "import { FormControl, FormGroup, FormBuilder, Validators, ReactiveFormsModule } from '@angular/forms';\n"
				+ "import { SHARED_IMPORTS } from '@shared';\n"				
				
				+ "import { NzButtonModule } from 'ng-zorro-antd/button';\n"
				+ "import { NzTableModule } from 'ng-zorro-antd/table';\n"	
				+ "import { NzFormModule } from 'ng-zorro-antd/form';\n"				
				+ "import { NzInputModule } from 'ng-zorro-antd/input';\n"				
				+ "import { NzSelectModule } from 'ng-zorro-antd/select';\n"
				+ "import { NzCheckboxModule } from 'ng-zorro-antd/checkbox';\n"
				+ "import { NzAutocompleteModule } from 'ng-zorro-antd/auto-complete';\n\n");
		
		sb.append("imports: [SHARED_IMPORTS,FormControl, ReactiveFormsModule, NzButtonModule, NzFormModule, NzInputModule, NzTableModule,\r\n"
				+ "    NzDatePickerModule, NzAutocompleteModule, NzSelectModule, NzCheckboxModule");
		
		
		
		return sb.toString();
	}	
	
	
	
	
}
