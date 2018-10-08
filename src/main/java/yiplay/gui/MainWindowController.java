package yiplay.gui;

import java.net.URL;
import java.util.ResourceBundle;

import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.LineNumberFactory;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TitledPane;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import yiplay.LogicFacade;
import yiplay.gui.entities.MemEntity;
import yiplay.tc.memory.Memory;
import yiplay.util.Translate;
import yiplay.util.observer.GPRObserver;
import yiplay.util.observer.IBObserver;
import yiplay.util.observer.MemoryObserver;

public class MainWindowController implements Initializable, MemoryObserver, GPRObserver, IBObserver{

	@FXML
	private TitledPane editorTab;

	@FXML
	private TitledPane statusTab;

	@FXML
	private Accordion mainAccordion;

	@FXML
	private Button compileButton;

	@FXML
	private Button stepButton;

	@FXML
	private Button executeButton;

	@FXML
	private TableView<MemEntity> stackTable;

	@FXML
	private TableView<MemEntity> memoryTable;

	@FXML
	private Text r0_data;

	@FXML
	private Text r1_data;

	@FXML
	private Text r2_data;

	@FXML
	private Text r3_data;

	@FXML
	private Text r4_data;

	@FXML
	private Text r5_data;

	@FXML
	private Text r6_data;

	@FXML
	private Text r7_data;
	
	@FXML
	private Text ib_data;

	private CodeArea codeArea;
	private MemEntity[] memoryCells = new MemEntity[Memory.MAX_POS];

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		initializeAccordion();
		initializeCodeArea();
		initializeButtons();
		initializeTables();
		initializeObservers();
	}

	public void initializeObservers() {
		LogicFacade.getInstance().registerMemoryObserver(this);
		LogicFacade.getInstance().registerGPRObserver(this);
		LogicFacade.getInstance().registerIBObserver(this);
	}

	private void initializeTables() {
		for(int i=0;i<memoryCells.length;i++)
			memoryCells[i] = new MemEntity(i);

		initMemTable(stackTable, memoryCells);
		initMemTable(memoryTable, memoryCells);

		stackTable.scrollTo(memoryCells.length-1);
	}

	@SuppressWarnings("unchecked")
	private void initMemTable(TableView<MemEntity> table, MemEntity[] data) {
		TableColumn<MemEntity, String> addressColumn = new TableColumn<MemEntity,String>("Dirección");
		addressColumn.setCellValueFactory(new PropertyValueFactory<>("hexAddress"));

		TableColumn<MemEntity, Integer> statusColumn = new TableColumn<MemEntity,Integer>("Estado");
		statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

		table.getColumns().addAll(addressColumn,statusColumn);
		table.getItems().addAll(data);
	}

	private void initializeButtons() {
		compileButton.setOnAction( (e) -> compile() );
		stepButton.setOnAction( (e) -> LogicFacade.getInstance().runInstruction() );
		executeButton.setOnAction( (e) -> LogicFacade.getInstance().runProgram() );
	}

	private void initializeAccordion() {
		for (TitledPane pane: mainAccordion.getPanes()) 
			pane.setAnimated(true);

		editorTab.expandedProperty().addListener((obs, wasExpanded, isNowExpanded) -> {
			if (!isNowExpanded) {
				expandPane(statusTab);
			}
		});
	}

	private void expandPane(TitledPane pane) {
		mainAccordion.setExpandedPane(pane);
	}

	private void initializeCodeArea() {
		codeArea = new CodeArea();
		codeArea.setParagraphGraphicFactory(LineNumberFactory.get(codeArea));
		codeArea.setStyle("-fx-font-family: inconsolata; -fx-font-size: 10pt; ");

		editorTab.setContent(codeArea);
		mainAccordion.setExpandedPane(editorTab);
	}

	private void compile() {
		String code = codeArea.getText();
		LogicFacade.getInstance().compileAndSave(code);
	}

	@Override
	public void updateMemory(short[] memory) {
		if(memory.length != memoryCells.length)
			System.err.println("Error with memory size.");

		for(int i=0;i<memory.length;i++) {
			memoryCells[i].setStatus(memory[i]);
			memoryTable.getItems().set(i, memoryCells[i]);
			stackTable.getItems().set(i, memoryCells[i]);
		}

	}

	@Override
	public void updateGPR(int register, short data) {
		Text[] texts = {r0_data,r1_data,r2_data,r3_data,r4_data,r5_data,r6_data,r7_data};

		int temp = data;

		if(register==7)
			temp = Translate.toMemoryPosition(Translate.toBinaryString(data, 16));


		String hex = Integer.toHexString(temp).toUpperCase();
		while(hex.length() < 4)
			hex = '0' + hex;
		hex += 'h';

		texts[register].setText(hex); 
	}

	@Override
	public void updateIB(short data) {
		int temp = data;
		String hex = Integer.toHexString(temp).toUpperCase();
		
		while(hex.length() < 4)
			hex = '0' + hex;
		hex += 'h';
		
		ib_data.setText(hex);
	}

}
