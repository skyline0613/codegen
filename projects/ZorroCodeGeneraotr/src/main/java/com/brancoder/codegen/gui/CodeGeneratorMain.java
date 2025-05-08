package com.brancoder.codegen.gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.json.JSONArray;
import org.springframework.util.StringUtils;

import com.brancoder.codegen.element.*;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Clipboard;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.prefs.Preferences;
import com.brancoder.codegen.bean.*;
import com.brancoder.codegen.generator.*;
import com.brancoder.codegen.util.*;

class CodeGeneratorMain {
	private static final String LAST_USED_FOLDER = "lastUsedFolder";
	private Configurations configs = new Configurations();
	private Configuration config = null;
	//private List<ZorroCtrlBean> list = new ArrayList<ZorroCtrlBean>();
	//private List<TypescriptInterfaceBean> list2 = new ArrayList<TypescriptInterfaceBean>();
	private JFrame frame = new JFrame("程式碼產生器");
	private PropertiesUtil propertiesUtil = new PropertiesUtil();
	private DefaultTableModel tm = new DefaultTableModel();
	private PropertyProvider propertyProvider = PropertyProvider.instance();
	private Properties props = null;
	private JFileChooser fc = new JFileChooser();
	private JSplitPane splitPane;
	private JTextArea textAreaLeft = new JTextArea();
	private JTextArea textAreaRight = new JTextArea();	
	private JTextField componentName = new JTextField();
	private String _mode;
    String[] comboBoxItems = {"","Zorro Form","Zorro Table","Json"};
    private JComboBox<String> comboBox = new JComboBox<>(comboBoxItems);
    private JCheckBox chkSortable = new JCheckBox("可排序");
    private JButton chooseFile = new JButton("Choose Excel File");
    //private JLabel label = new JLabel("試算表索引");
    String[] sheetIindexItems = {"1","2","3","4","5","6"};
    private JComboBox<String> sheetIndexComboBox = new JComboBox<>(sheetIindexItems);
	private JTable table = new JTable(tm) {
		@Override
		public Dimension getPreferredScrollableViewportSize() {
			return new Dimension(320, 240);
		}
	};	
	private TableColumnModel colModel = this.table.getColumnModel();
	
	// propertiesUtil.loadTraditionalProperties("bin/Resource.properties");
	// private File file = new File("bin/Resource.properties");

	

	public static void main(String args[]) throws ConfigurationException {
		CodeGeneratorMain builder = new CodeGeneratorMain();
		
	}

	public CodeGeneratorMain() throws ConfigurationException {
		this.buildGUI();
	}	

	public void buildGUI() {
		
		//sheetIndexComboBox.setPreferredSize(new Dimension(10,25));
		
		componentName.setText("");
		componentName.setPreferredSize(new Dimension(120,25));
		comboBox.setPreferredSize(new Dimension(100,25));
		sheetIndexComboBox.setPreferredSize(new Dimension(50,25));
		textAreaLeft.setFont(textAreaLeft.getFont().deriveFont(12f));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1000, 500);
		
		//tm.addColumn("Attribute");
		//tm.addColumn("Value");
		//renderTable();

        // Create a panel with BorderLayout
        JPanel borderLayoutPanel = new JPanel(new BorderLayout());
        borderLayoutPanel.add(new JButton("South"), BorderLayout.SOUTH);
        //borderLayoutPanel.add(new JButton("North"), BorderLayout.NORTH);        
        //borderLayoutPanel.add(new JButton("East"), BorderLayout.EAST);
        //borderLayoutPanel.add(new JButton("West"), BorderLayout.WEST);
        //borderLayoutPanel.add(new JButton("Center"), BorderLayout.CENTER);

        // Create a panel with FlowLayout
        JPanel flowLayoutPanel = new JPanel(new FlowLayout());


        // Create a panel with GridLayout
        JPanel gridLayoutPanel = new JPanel(new GridLayout(2, 2));
        gridLayoutPanel.add(new JButton("Button 1"));
        gridLayoutPanel.add(new JButton("Button 2"));
        gridLayoutPanel.add(new JButton("Button 3"));
        gridLayoutPanel.add(new JButton("Button 4"));	
        
        JTabbedPane tabbedPane = new JTabbedPane();
	    //tabbedPane.addTab("FlowLayout", flowLayoutPanel);        
	    tabbedPane.addTab("BorderLayout", borderLayoutPanel);
	    //tabbedPane.addTab("GridLayout", gridLayoutPanel);

	    // Add the tabbed pane to the frame
	    frame.add(tabbedPane);
	    frame.setVisible(true);			
	    
	    
		//btnOpenFolder.setBackground(Color.MAGENTA);
		chooseFile.setSize(100, 50);
		chooseFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	chooseExcel();
            }
        });		
		

	    JScrollPane jScrollPane = new JScrollPane();
	    textAreaLeft.setSize(200,200);
	    textAreaRight.setSize(200,200);
	    flowLayoutPanel.add(new JLabel("元件名稱"));
	    flowLayoutPanel.add(componentName);
	    flowLayoutPanel.add(Box.createRigidArea(new Dimension(15, 0)));	
	    flowLayoutPanel.add(new JLabel("轉換類型"));
	    flowLayoutPanel.add(comboBox);
	    flowLayoutPanel.add(Box.createRigidArea(new Dimension(15, 0)));	    
	    flowLayoutPanel.add(new JLabel("試算表"));
	    flowLayoutPanel.add(sheetIndexComboBox);	    
	    flowLayoutPanel.add(Box.createRigidArea(new Dimension(15, 0)));
	    flowLayoutPanel.add(chkSortable);
	    flowLayoutPanel.add(Box.createRigidArea(new Dimension(15, 0)));
	    flowLayoutPanel.add(chooseFile);
	    
	    borderLayoutPanel.add(flowLayoutPanel, BorderLayout.NORTH);
	 
        JScrollPane scrollPane = new JScrollPane(textAreaLeft);
        JScrollPane scrollPane2 = new JScrollPane(textAreaRight);

        
        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
        		scrollPane, scrollPane2);
		splitPane.setResizeWeight(0.5);
		splitPane.setOneTouchExpandable(true);
		splitPane.setContinuousLayout(true);

		borderLayoutPanel.add(splitPane, BorderLayout.CENTER);
		frame.add(tabbedPane);
		frame.setVisible(true);

	}	


	private void chooseExcel() {
		
        Preferences prefs = Preferences.userNodeForPackage(CodeGeneratorMain.class);
        String lastUsedFolder = prefs.get(LAST_USED_FOLDER, new File(".").getAbsolutePath());

		fc.setCurrentDirectory(new File(lastUsedFolder));

		
		this.textAreaLeft.setText("");
		this.textAreaRight.setText("");
		
		int result = fc.showOpenDialog(frame);
		if (result == JFileChooser.APPROVE_OPTION) {
			
			File selectedFile = fc.getSelectedFile();
			
			int sheetIndex = getSheetIndex();
			String selectedItem = comboBox.getSelectedItem().toString();
			try {
				switch(selectedItem) {
					case "Zorro Form":
						genZorroFormCode(selectedFile, sheetIndex);
						break;
					case "Zorro Table":
						genNgTableCode(selectedFile, sheetIndex);
						break;							
					case "Json":
						genExcelToJson(selectedFile, sheetIndex);
						break;					
					case "JavaBean":
						genJavaBean();
						break;	
					default:
						break;
				}
			}catch(Exception e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "轉換失敗");
				textAreaLeft.setText("");
				textAreaRight.setText("");
			}
		}

	}

	private int getSheetIndex() {
		
		return Integer.valueOf( sheetIndexComboBox.getSelectedItem().toString()) - 1;
	}
	

	private void genExcelToJson(File selectedFile, int sheetIndex) {
		
		ConvertExcelToJSONArray cetJsonArr = new ConvertExcelToJSONArray();
		
		
		JSONArray arr = cetJsonArr.convertExceltoJSONArray_withObject(selectedFile.getAbsolutePath(), sheetIndex);
		
		StringBuffer sb = new StringBuffer(" listOfData: ItemData[] = "+arr.toString(4));
		setClipBoard(sb.toString());
		this.textAreaLeft.setText(sb.toString());	
		this.textAreaRight.setText("");
	}

	/***
	 * 轉換為Zorro Control Bean
	 * @param row
	 * @return
	 */
	private ZorroCtrlBean convertToZorroCtrlBean(Row row) {

		if (row == null)
			return null;

		String type = row.getCell(0).getStringCellValue(); // 類型
		if (!StringUtils.hasText(type))
			return null;		
		String controlType = row.getCell(1) != null ? row.getCell(1).getStringCellValue() : null; // 控制項
		String label = row.getCell(2) != null ? row.getCell(2).getStringCellValue() : null; // 顯示名稱
		String formControlName = row.getCell(3) != null ? row.getCell(3).getStringCellValue() : null; // 控制項名稱
		String controlName = row.getCell(4) != null ? row.getCell(4).getStringCellValue() : null; // 控制項名稱
		String cssClass = row.getCell(5) != null ? row.getCell(5).getStringCellValue() : null; // CSS_CLASS
		String list = row.getCell(6) != null ? row.getCell(6).getStringCellValue() : null; // 值域清單

		
		boolean required = false;	//是否必填
		try {
			required = Boolean.valueOf(row.getCell(7).getBooleanCellValue());
		}catch(Exception e) {
			System.out.println(e);
		}
		
		String placeHolder = row.getCell(8) != null ? row.getCell(8).getStringCellValue() : null; // 值域清單		

		String attribute = row.getCell(9) != null ? row.getCell(9).getStringCellValue() : null; // 值域清單
		
		String divCssClass = row.getCell(10) != null ? row.getCell(10).getStringCellValue() : null; // DIV_CSS_CLASS		

		String defaultValue = row.getCell(11) != null ? row.getCell(11).getStringCellValue() : null; // 預設值		
		String message = row.getCell(12) != null ? row.getCell(12).getStringCellValue() : null; // 預設值		
		
		double columnSzSize = 6;
		try {
			columnSzSize = Double.valueOf(row.getCell(13).getNumericCellValue());
		}catch(Exception e) {
			System.out.println(e);
		}
				
		ZorroCtrlBean cgb = new ZorroCtrlBean(type, controlType, label, formControlName, controlName, cssClass, 
				list, required, placeHolder, attribute, divCssClass, defaultValue, message, columnSzSize);

		return cgb;

	}


	private List<TypescriptInterfaceBean> excel2TypescriptInterfaceBean(File selectedFile, int sheetIndex) {
		List<TypescriptInterfaceBean> lst = new ArrayList<TypescriptInterfaceBean>();
		
		Sheet sheet = ExcelUtil.readSheet(selectedFile, sheetIndex);		
		if(sheet == null)
			return lst;
		

		int rowcnt = 0;
		// iterate each row
		for (Row row : sheet) {
			rowcnt++;
			if (rowcnt == 1) {
				continue;
			}

			TypescriptInterfaceBean tib = convertToTypescriptInterfaceBean(row);
			if (tib == null)
				continue;

			lst.add(tib);
		}		
		return lst;
	}
	

	private void genNgTableCode(File selectedFile, int sheetIndex) {
		List<TypescriptInterfaceBean> lst =  excel2TypescriptInterfaceBean(selectedFile, sheetIndex);
		
		
		String source = "";

		source = convertNgTableCode(lst, CodeGenEnum.ZORRO_TABLE_HTML);
		this.textAreaLeft.setText(source);

		setClipBoard(source);
		
		source = "";
		String interfaceSource = convertNgTableCode(lst, CodeGenEnum.NG_INTERFACE );
		String columnsDefinitionSource = convertNgTableCode(lst, CodeGenEnum.ZORRO_TABLE_SCHEMA);
		source= AngularCodeGenerator.genTableTypescriptImports(componentName.getText().toString());
		source = String.format(source, interfaceSource, columnsDefinitionSource);

		this.textAreaRight.setText(source);

	}
	
	private String convertNgTableCode(List<TypescriptInterfaceBean> lst , CodeGenEnum cge) {
	
		
		String source = "";

		switch(cge){
		case NG_INTERFACE: //Angular Interface
			source = AngularCodeGenerator.genTypescriptInterfaceSource(lst);
			break;
		case ZORRO_TABLE_HTML://NzTable html source
			boolean isSortable = chkSortable.isSelected();
			source = ZorroCodeGenerator.genNzTableSource(lst, isSortable);
			break;
		case ZORRO_TABLE_SCHEMA://NzTable
			source= ZorroCodeGenerator.genNzTableColumnSchemaSource(lst);
			break;	
			

		}
		
		

		return source;
	}	
	

	private TypescriptInterfaceBean convertToTypescriptInterfaceBean(Row row) {
		String type = row.getCell(0).getStringCellValue(); // 顯示名稱
		if (!StringUtils.hasText(type))
			return null;	
		
		String displayName = row.getCell(0) != null ? row.getCell(0).getStringCellValue() : null; // 控制項
		String variableName = row.getCell(1) != null ? row.getCell(1).getStringCellValue() : null; // 顯示名稱
		String variableType = row.getCell(2) != null ? row.getCell(2).getStringCellValue() : null; // 控制項名稱

		
		TypescriptInterfaceBean tib = 
				new TypescriptInterfaceBean(displayName, variableName, variableType);
		
		
		return tib;
	}
	




	private void genZorroFormCode(File selectedFile, int sheetIndex) {
		
		Sheet sheet = ExcelUtil.readSheet(selectedFile, sheetIndex);
		if(sheet == null) {
			return;
		}
		List<ZorroCtrlBean> list = new ArrayList<ZorroCtrlBean>();
		
		int rowcnt = 0;
		// 迭代遍歷每一行
		for (Row row : sheet) {
			rowcnt++;
			if (rowcnt == 1) {
				continue;
			}

			ZorroCtrlBean cgb = convertToZorroCtrlBean(row);
			if (cgb == null)
				continue;

			list.add(cgb);
		}		
		
		
		String source = genZorroControlCode(list);
		this.textAreaLeft.setText(source);	
		
		setClipBoard(source);
		
		StringBuffer sb = new StringBuffer("");
		//source = getNgCode(lst, CodeGenEnum.ZORRO_IMPORT_LIBRARY);
		sb.append(ZorroCodeGenerator.genNzFormImportString(componentName.getText().toString()));
		sb.append("\n\n");
		sb.append(ZorroCodeGenerator.genFormGroupSource(list, componentName.getText().toString()));
		
		
		this.textAreaRight.setText(sb.toString());
		
		
	}
	
	/**
	 * iterate list, then generate whole zorro code
	 * @param list
	 * @return
	 */
	private String genZorroControlCode(List<ZorroCtrlBean> list) {
		FormNode root = null;
		//DivRowNode row = null;
		INode node = null;
		for (ZorroCtrlBean cgb : list) {
			switch (cgb.getType().toLowerCase()) {
			case "root":
			case "form":				
				root = new FormNode();
				
				break;
			case "form-":
			case "-form":					
			case "root-":
			case "-root":				


				break;

			case "control":				
			case "ctl":
				node = ZorroCodeGenerator.convertBeanToNode(cgb);
				root.appenChild(ZorroCodeGenerator.convertBeanToNode(cgb));
				break;
			}
		}
		return root.build();
	}		

	


	private void genJavaBean() {
		// TODO Auto-generated method stub
		
	}	
	/**
	 * 輸出程式碼到TextArea
	 * @param source
	 */
	private void setClipBoard(String source) {
		
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Clipboard clipboard = toolkit.getSystemClipboard();
		StringSelection strse1 = new StringSelection(source);
		clipboard.setContents(strse1, strse1);
			

	}




	/***
	
	private ActionListener actionListener = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			String command = e.getActionCommand();
			switch (command) {
			case "Open Folder":
				chooseExcel();
				break;
			}
		}
	};
	

	private void renderTable() {

		DefaultTableModel dm = (DefaultTableModel) table.getModel();
		dm.getDataVector().removeAllElements();
		dm.fireTableDataChanged();
		propertyProvider.refresh();
		props = propertyProvider.getProperties();
		List<String> keys = new ArrayList<String>(props.stringPropertyNames());
		Collections.sort(keys);
		for (String key : keys) {
			tm.addRow(new String[] { key, props.getProperty(key) });
		}
	}


	private void loadProperties() throws ConfigurationException {
		try {
			this.config = configs.properties(new File("bin/Resource.properties"));

		} catch (ConfigurationException cex) {
			throw cex;
		}
		Iterator<String> keys = config.getKeys();
		List<String> keyList = new ArrayList<String>();
		while (keys.hasNext()) {
			keyList.add(keys.next());
		}
	}

	public String getProperty(String key) {
		TableModel tModel = this.table.getModel();
		for (int row = 0; row < tModel.getRowCount(); row++) {

			String _key = (String) tModel.getValueAt(row, 0);
			String _value = (String) tModel.getValueAt(row, 1);
			if (!key.equals(_key)) {
				continue;
			} else {
				return _value;
			}

		}
		return "";
	}

	private void saveProperties() {
		Properties props = new Properties();
		TableModel tModel = this.table.getModel();
		for (int row = 0; row < tModel.getRowCount(); row++) {
			String key = (String) tModel.getValueAt(row, 0);
			String value = (String) tModel.getValueAt(row, 1);

			props.put(key, value);
		}

		FileOutputStream fOut;
		try {
			propertiesUtil.saveProperties("bin/Resource.properties", props);
			renderTable();
			JOptionPane.showMessageDialog(AnguarCodeBuilder.this.frame, "Saved!");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	**/

	private void exit() {
		System.exit(0);
	}


}
