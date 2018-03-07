package jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.kenshin;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

import jp.or.med.orca.jma_tokutei.common.app.JApplication;
import jp.or.med.orca.jma_tokutei.common.app.JApplication.FlagEnum_Master;
import jp.or.med.orca.jma_tokutei.common.app.JPath;
import jp.or.med.orca.jma_tokutei.common.component.ExtendedImageIcon;
import jp.or.med.orca.jma_tokutei.common.component.ExtendedLabel;
import jp.or.med.orca.jma_tokutei.common.component.TitleLabel;
import jp.or.med.orca.jma_tokutei.common.convert.JQueryConvert;
import jp.or.med.orca.jma_tokutei.common.csv.JCSVReaderStream;
import jp.or.med.orca.jma_tokutei.common.csv.JCSVWriterStream;
import jp.or.med.orca.jma_tokutei.common.errormessage.JErrorMessage;
import jp.or.med.orca.jma_tokutei.common.errormessage.RETURN_VALUE;
import jp.or.med.orca.jma_tokutei.common.filter.SpecialFilterPanel;
import jp.or.med.orca.jma_tokutei.common.frame.ViewSettings;
import jp.or.med.orca.jma_tokutei.common.frame.dialog.DialogFactory;
import jp.or.med.orca.jma_tokutei.common.frame.dialog.IDialog;
import jp.or.med.orca.jma_tokutei.common.openswing.ExtendedOpenEditButton;
import jp.or.med.orca.jma_tokutei.common.openswing.ExtendedOpenExportButton;
import jp.or.med.orca.jma_tokutei.common.openswing.ExtendedOpenFilterButton;
import jp.or.med.orca.jma_tokutei.common.openswing.ExtendedOpenGenericButton;
import jp.or.med.orca.jma_tokutei.common.openswing.ExtendedOpenImportButton;
import jp.or.med.orca.jma_tokutei.common.openswing.ExtendedOpenInsertButton;
import jp.or.med.orca.jma_tokutei.common.openswing.ExtendedOpenSaveButton;
import jp.or.med.orca.jma_tokutei.common.openswing.ExtendedReloadButton;
import jp.or.med.orca.jma_tokutei.kenshin.healthexamination.dataimport.JImportMasterErrorKenshinResultFrameData;

import org.apache.log4j.Logger;
import org.openswing.swing.client.GenericButton;
import org.openswing.swing.client.GridControl;
import org.openswing.swing.client.GridControl.ColumnContainer;
import org.openswing.swing.client.NavigatorBar;
import org.openswing.swing.domains.java.Domain;
import org.openswing.swing.message.receive.java.Response;
import org.openswing.swing.message.receive.java.VOListResponse;
import org.openswing.swing.message.send.java.GridParams;
import org.openswing.swing.table.columns.client.ComboColumn;
import org.openswing.swing.table.columns.client.TextColumn;
import org.openswing.swing.table.model.client.VOListTableModel;
import org.openswing.swing.util.client.ClientSettings;
import org.openswing.swing.util.client.ClientUtils;

/**
 * �ꗗList���
 * @author s.inoue
 * @version 2.0
 */
public class JKenshinMasterMaintenanceListFrame extends JFrame implements KeyListener,ActionListener {

	private static final long serialVersionUID = -8615537027525763087L;	// edit n.ohkubo 2014/10/01�@�ǉ�

//	private Connection conn = null;	// edit n.ohkubo 2014/10/01�@���g�p�Ȃ̂ō폜

	protected GridControl grid = new GridControl();
	protected JPanel buttonOriginePanel = new JPanel();
	protected JPanel buttonPanel = new JPanel();

	protected FlowLayout flowLayoutOriginePanel = new FlowLayout();
	protected FlowLayout flowLayoutPanel = new FlowLayout();  //  @jve:decl-index=0:

	/* button */
//	protected ExtendedOpenReloadButton reloadButton = new ExtendedOpenReloadButton();
//	protected ExtendedOpenDeleteButton deleteButton = new ExtendedOpenDeleteButton();
	protected ExtendedReloadButton reloadButton = new ExtendedReloadButton(this,JPath.Ico_Common_Reload);
// del s.inoue 2013/02/15
//	protected ExtendedDelButton deleteButton = new ExtendedDelButton(this,JPath.Ico_Common_Delete);

	protected ExtendedOpenInsertButton insertButton = new ExtendedOpenInsertButton();
	protected ExtendedOpenEditButton editButton = new ExtendedOpenEditButton();
	protected ExtendedOpenSaveButton saveButton = new ExtendedOpenSaveButton();
	protected ExtendedOpenFilterButton filterButton = new ExtendedOpenFilterButton();
	protected NavigatorBar navigatorBar = new NavigatorBar();
	protected ExtendedOpenExportButton exportButton = new ExtendedOpenExportButton();
	protected ExtendedOpenImportButton importButton = new ExtendedOpenImportButton();
	protected GenericButton buttonClose = null;

	/* control */
	protected ComboColumn textColumn_HokenjaNumber = new ComboColumn();
	protected TextColumn textColumn_KoumokuCd = new TextColumn();
	// eidt s.inoue 2011/06/02
	protected TextColumn textColumn_KoumokuNm = new TextColumn();
	protected TextColumn textColumn_KensaHouhou = new TextColumn();

	protected ComboColumn textColumn_HisuFlg = new ComboColumn();
	protected TextColumn textColumn_DSKagen = new TextColumn();
	protected TextColumn textColumn_DSJyougen = new TextColumn();
	protected TextColumn textColumn_JSKagen = new TextColumn();
	protected TextColumn textColumn_JSJyougen = new TextColumn();
	protected TextColumn textColumn_Tani = new TextColumn();
	protected TextColumn textColumn_Kagen = new TextColumn();
	protected TextColumn textColumn_Jyogen = new TextColumn();
	protected TextColumn textColumn_KijyuntiHani = new TextColumn();
	protected TextColumn textColumn_TankaKenshin = new TextColumn();
	protected TextColumn textColumn_Bikou = new TextColumn();
//	protected ComboBoxControl comboSelectHokenja = null;
	protected FlowLayout flowLayout1 = new FlowLayout();  //  @jve:decl-index=0:

	protected final static String ICON_DELETE_KEKKA = "./Images/kekkaInput_DelKekka.png";
	protected final static String ICON_DELETE_JUSINKEN = "./Images/kekkaInput_DelJusinken.png";
	protected final static String ICON_PRINT_NYURYOKUHYO = "./Images/kekkaInput_CopyNuryokuhyo.png";

	private IDialog filePathDialog;
	private JKenshinMasterMaintenanceListData validatedData = new JKenshinMasterMaintenanceListData();

	private static jp.or.med.orca.jma_tokutei.common.csv.JCSVReaderStream reader = null;
	private static jp.or.med.orca.jma_tokutei.common.csv.JCSVWriterStream writer = null;

	private static Vector<Vector<String>> CSVItems = null;

	private static String saveTitle= "csv�t�@�C���ۑ���I��";
	private static String selectTitle= "csv�t�@�C���I��";
	private static String savaDialogTitle= "csv�t�@�C���̕ۑ����I���A�t�@�C�������w�肵�Ă�������";
	private static String selectDialogTitle= "csv�t�@�C����I�����Ă�������";

	// �����{�^����������SQL�Ŏg�p
	// edit s.inoue 2010/07/07
	private static final String[] TABLE_COLUMNS = {
		"HKNJANUM","KOUMOKU_CD","HISU_FLG",
		"DS_KAGEN","DS_JYOUGEN","JS_KAGEN","JS_JYOUGEN","TANI","TANKA_KENSIN","BIKOU"
		};

	private static String CONST_DATA_VALUE =
		"jp.or.med.orca.jma_tokutei.kenshin.healthexamination.frame.kenshin.JKenshinMasterMaintenanceListData";

	protected final static double CONST_FIX_ICON =0.8;
	// add s.inoue 2010/02/25
	protected ExtendedOpenGenericButton buttonExport = null;
	protected ExtendedOpenGenericButton buttonImport = null;

	private static org.apache.log4j.Logger logger = Logger.getLogger(JKenshinMasterMaintenanceListFrame.class);
	
	// edit n.ohkubo 2014/10/01�@�ǉ� start
	private ColumnContainer columnContainer;
	public ColumnContainer getColumnContainer() {
		return this.columnContainer;
	}
	private JCheckBox savedJCheckBox = new JCheckBox();
	public JCheckBox getSavedJCheckBox() {
		return this.savedJCheckBox;
	}
	// edit n.ohkubo 2014/10/01�@�ǉ� end
	
	// edit n.ohkubo 2015/03/01�@�ǉ�
	protected ExtendedOpenGenericButton buttonDefault = null;		//�����l�{�^��
	protected String afterDefaultToolTipText = null;				//�����l�{�^��������̃c�[���`�b�v����
	protected List<TableCellRenderer> tableCellRendererList = null;	//TableCellRenderer�̏����ݒ�ޔ�p�i�����l�{�^�������Őݒ肵�A�ēǍ��������Ɏg�p�j

	/* �R���X�g���N�^ */
	public JKenshinMasterMaintenanceListFrame(Connection conn,
			JKenshinMasterMaintenanceListFrameCtrl controller) {
		setControl(conn,controller);
	}

	/* �����[�h */
	public void reloadData() {
		grid.reloadData();
	}

	/* �O���b�hgetter */
	public GridControl getGrid() {
		return grid;
	}

	/* ���䃁�\�b�h */
	public void setControl(Connection conn,
			JKenshinMasterMaintenanceListFrameCtrl controller){
//		this.conn = conn;	// edit n.ohkubo 2014/10/01�@���g�p�Ȃ̂ō폜
		try {
			jbInit();

			this.setSize(ViewSettings.getFrameCommonSize());
			this.setLocationRelativeTo( null );
			grid.setController(controller);
			grid.setGridDataLocator(controller);
			setVisible(true);

			grid.addKeyListener(new KeyAdapter() {
			      @Override
				public void keyPressed(KeyEvent e) {
			        if (e.getKeyCode()==e.VK_CANCEL || e.getKeyCode()==e.VK_BACK_SPACE || e.getKeyCode()==e.VK_DELETE)
			          System.out.println("����");
			      }
			    });
			
			// edit n.ohkubo 2014/10/01�@�ǉ� start�@������ʂɃ`�F�b�N�{�b�N�X��ǉ�
			//�t�B���^�[�p�l���p��WindowListener
			SpecialFilterPanel specialFilterPanel = new SpecialFilterPanel(savedJCheckBox, grid.getParent().getComponents());
			
			//���̃t���[���i�ꗗ��ʁj���A�N�e�B�u���i��ʉE�̌����E�B���h�E�j�@or�@��A�N�e�B�u���i�����E�B���h�E���|�b�v�A�b�v�ŊJ����ꍇ�j���ꂽ�Ƃ��ɓ��삷��悤�ɁAListener��ݒ�iFilterPanel�ւ̃`�F�b�N�{�b�N�X�ǉ���Listener���ōs���j
			this.addWindowListener(specialFilterPanel);
			// edit n.ohkubo 2014/10/01�@�ǉ� end�@������ʂɃ`�F�b�N�{�b�N�X��ǉ�

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/* ���������� */
	public void jbInit() throws Exception {

//		// combobox�ݒ�
//		Response res = ClientUtils.getData("loadCallOutTypes",new GridParams());
//	    Domain d = new Domain("CALL_OUT_TYPES");
//	    if (!res.isError()) {
//	      JKenshinMasterMaintenanceHokenjyaVO vo = null;
//	      java.util.List list = ((VOListResponse)res).getRows();
//	      for(int i=0;i<list.size();i++) {
//	        vo = (JKenshinMasterMaintenanceHokenjyaVO)list.get(i);
//	        d.addDomainPair(vo.getHokenjyaNumber(),vo.getHokenjyaName());
//	      }
//	    }
//	    comboSelectHokenja = new ComboBoxControl();
//	    comboSelectHokenja.setDomain(d);
//	    comboSelectHokenja.getComboBox().addItemListener(new ItemListener() {
//	      public void itemStateChanged(ItemEvent e) {
//	        if (e.getStateChange()==e.SELECTED) {
////	          hierarTreePanel.setProgressiveHIE02((BigDecimal)comboBoxControl1.getValue());
////	          hierarTreePanel.reloadTree();
//	          grid.clearData();
//	        }
//	      }
//	    });
//	    if (d.getDomainPairList().length==1)
//	      comboSelectHokenja.getComboBox().setSelectedIndex(0);
//	    else
//	      comboSelectHokenja.getComboBox().setSelectedIndex(-1);
//
//	    comboSelectHokenja.setDomainId("SEX");

		/* control ��ClientApplication�ƈ�v*/
		// �ی��Ҕԍ�(*)
		textColumn_HokenjaNumber.setColumnFilterable(true);
		textColumn_HokenjaNumber.setColumnName("HKNJANUM");
		textColumn_HokenjaNumber.setColumnSortable(true);
		textColumn_HokenjaNumber.setPreferredWidth(200);
		textColumn_HokenjaNumber.setColumnRequired(true);
		textColumn_HokenjaNumber.setDomainId("T_HOKENJYA");
		/*add tanaka 2013/11/15*/
//		textColumn_HokenjaNumber.setColumnVisible(true);
		textColumn_HokenjaNumber.setColumnVisible(!JApplication.flag_Master.contains(FlagEnum_Master.HKNJANUM));

		// ���ڃR�[�h(*)
		textColumn_KoumokuCd.setColumnFilterable(true);
		textColumn_KoumokuCd.setColumnName("KOUMOKU_CD");
		textColumn_KoumokuCd.setColumnSortable(true);
		textColumn_KoumokuCd.setPreferredWidth(150);
		textColumn_KoumokuCd.setColumnRequired(true);
		/*add tanaka 2013/11/15*/
//		textColumn_KoumokuCd.setColumnVisible(true);
		textColumn_KoumokuCd.setColumnVisible(!JApplication.flag_Master.contains(FlagEnum_Master.KOUMOKU_CD));
		textColumn_KoumokuCd.setMaxCharacters(17);	// edit n.ohkubo 2014/10/01�@�ǉ�

		// eidt s.inoue 2011/06/02
		// ���ږ�
		textColumn_KoumokuNm.setColumnFilterable(true);
		textColumn_KoumokuNm.setColumnName("KOUMOKU_NAME");
		textColumn_KoumokuNm.setColumnSortable(true);
		textColumn_KoumokuNm.setPreferredWidth(200);
		textColumn_KoumokuNm.setColumnRequired(false);
		/*add tanaka 2013/11/15*/
//		textColumn_KoumokuNm.setColumnVisible(true);
		textColumn_KoumokuNm.setColumnVisible(!JApplication.flag_Master.contains(FlagEnum_Master.KOUMOKU_NAME));
		textColumn_KoumokuNm.setMaxCharacters(200);	// edit n.ohkubo 2014/10/01�@�ǉ�
		
		// �������@
		textColumn_KensaHouhou.setColumnFilterable(true);
		textColumn_KensaHouhou.setColumnName("KENSA_HOUHOU");
		textColumn_KensaHouhou.setColumnSortable(true);
		textColumn_KensaHouhou.setPreferredWidth(180);
		textColumn_KensaHouhou.setColumnRequired(false);
		/*add tanaka 2013/11/15*/
//		textColumn_KensaHouhou.setColumnVisible(true);
		textColumn_KensaHouhou.setColumnVisible(!JApplication.flag_Master.contains(FlagEnum_Master.KENSA_HOUHOU));
		textColumn_KensaHouhou.setMaxCharacters(200);	// edit n.ohkubo 2014/10/01�@�ǉ�
		
		// �K�{�t���O(�ҏW��)
		textColumn_HisuFlg.setColumnFilterable(true);
		textColumn_HisuFlg.setColumnName("HISU_FLG");
		textColumn_HisuFlg.setColumnSortable(true);
		textColumn_HisuFlg.setPreferredWidth(70);
	    textColumn_HisuFlg.setEditableOnEdit(true);
	    textColumn_HisuFlg.setEditableOnInsert(true);
	    textColumn_HisuFlg.setColumnRequired(false);

	    textColumn_HisuFlg.setDomainId("HISU_FLG");
	    /*add tanaka 2013/11/15*/
//	    textColumn_HisuFlg.setColumnVisible(true);
	    textColumn_HisuFlg.setColumnVisible(!JApplication.flag_Master.contains(FlagEnum_Master.HISU_FLG));
	    
		// ��l����(�ҏW��)
		textColumn_DSKagen.setColumnFilterable(true);
		textColumn_DSKagen.setColumnName("DS_KAGEN");
		textColumn_DSKagen.setColumnSortable(true);
		textColumn_DSKagen.setPreferredWidth(120);
		textColumn_DSKagen.setEditableOnEdit(true);
		textColumn_DSKagen.setEditableOnInsert(true);
		textColumn_DSKagen.setColumnRequired(false);
		// eidt s.inoue 2012/10/25
		textColumn_DSKagen.setColumnVisible(true);
		textColumn_DSKagen.setColumnFilterable(false);
		textColumn_DSKagen.setColumnSortable(false);
		/*add tanaka 2013/11/15*/
//		textColumn_DSKagen.setColumnVisible(true);
		textColumn_DSKagen.setColumnVisible(!JApplication.flag_Master.contains(FlagEnum_Master.DS_KAGEN));
		
		// ��l���(�ҏW��)
		textColumn_DSJyougen.setColumnFilterable(true);
		textColumn_DSJyougen.setColumnName("DS_JYOUGEN");
		textColumn_DSJyougen.setColumnSortable(true);
		textColumn_DSJyougen.setPreferredWidth(120);
		textColumn_DSJyougen.setEditableOnEdit(true);
		textColumn_DSJyougen.setEditableOnInsert(true);
		textColumn_DSJyougen.setColumnRequired(false);
		// eidt s.inoue 2012/10/25
		textColumn_DSJyougen.setColumnVisible(true);
		textColumn_DSJyougen.setColumnFilterable(false);
		textColumn_DSJyougen.setColumnSortable(false);
		/*add tanaka 2013/11/15*/
//		textColumn_DSJyougen.setColumnVisible(true);
		textColumn_DSJyougen.setColumnVisible(!JApplication.flag_Master.contains(FlagEnum_Master.DS_JYOUGEN));

		// ��l����l(�ҏW��)
		textColumn_JSKagen.setColumnFilterable(true);
		textColumn_JSKagen.setColumnName("JS_KAGEN");
		textColumn_JSKagen.setEditableOnEdit(true);
		textColumn_JSKagen.setEditableOnInsert(true);
		textColumn_JSKagen.setPreferredWidth(120);
		textColumn_JSKagen.setColumnRequired(false);
		// eidt s.inoue 2012/10/25
		textColumn_JSKagen.setColumnVisible(true);
		textColumn_JSKagen.setColumnFilterable(false);
		textColumn_JSKagen.setColumnSortable(false);
		/*add tanaka 2013/11/15*/
//		textColumn_JSKagen.setColumnVisible(true);
		textColumn_JSKagen.setColumnVisible(!JApplication.flag_Master.contains(FlagEnum_Master.JS_KAGEN));

		// ��l�����l(�ҏW��)
		textColumn_JSJyougen.setColumnFilterable(true);
		textColumn_JSJyougen.setColumnName("JS_JYOUGEN");
		textColumn_JSJyougen.setColumnSortable(true);
		textColumn_JSJyougen.setEditableOnEdit(true);
		textColumn_JSJyougen.setEditableOnInsert(true);
		textColumn_JSJyougen.setPreferredWidth(120);
		textColumn_JSJyougen.setColumnRequired(false);
		// eidt s.inoue 2012/10/25
		textColumn_JSJyougen.setColumnVisible(true);
		textColumn_JSJyougen.setColumnFilterable(false);
		textColumn_JSJyougen.setColumnSortable(false);
		/*add tanaka 2013/11/15*/
//		textColumn_JSJyougen.setColumnVisible(true);
		textColumn_JSJyougen.setColumnVisible(!JApplication.flag_Master.contains(FlagEnum_Master.JS_JYOUGEN));
		
		// �P��
		textColumn_Tani.setColumnFilterable(true);
		textColumn_Tani.setColumnName("TANI");
		textColumn_Tani.setColumnSortable(true);
		textColumn_Tani.setPreferredWidth(300);
		textColumn_Tani.setColumnRequired(false);
		// eidt s.inoue 2012/10/25
		textColumn_Tani.setColumnVisible(true);
		textColumn_Tani.setColumnFilterable(false);
		textColumn_Tani.setColumnSortable(false);
		/*add tanaka 2013/11/15*/
//		textColumn_Tani.setColumnVisible(true);
		textColumn_Tani.setColumnVisible(!JApplication.flag_Master.contains(FlagEnum_Master.TANI));

		// �����l
		textColumn_Kagen.setColumnFilterable(true);
		textColumn_Kagen.setColumnName("KAGEN");
		textColumn_Kagen.setColumnSortable(true);
		textColumn_Kagen.setPreferredWidth(80);
		textColumn_Kagen.setColumnRequired(false);
		// eidt s.inoue 2012/10/25
		textColumn_Kagen.setColumnVisible(true);
		textColumn_Kagen.setColumnFilterable(false);
		textColumn_Kagen.setColumnSortable(false);
		/*add tanaka 2013/11/15*/
//		textColumn_Kagen.setColumnVisible(true);
		textColumn_Kagen.setColumnVisible(!JApplication.flag_Master.contains(FlagEnum_Master.KAGEN));

		// ����l
		textColumn_Jyogen.setColumnFilterable(true);
		textColumn_Jyogen.setColumnName("JYOUGEN");
		textColumn_Jyogen.setColumnSortable(true);
		textColumn_Jyogen.setPreferredWidth(80);
		textColumn_Jyogen.setColumnRequired(false);
		// eidt s.inoue 2012/10/25
		textColumn_Jyogen.setColumnVisible(true);
		textColumn_Jyogen.setColumnFilterable(false);
		textColumn_Jyogen.setColumnSortable(false);
		/*add tanaka 2013/11/15*/
//		textColumn_Jyogen.setColumnVisible(true);
		textColumn_Jyogen.setColumnVisible(!JApplication.flag_Master.contains(FlagEnum_Master.JYOUGEN));

		// ��l�͈�
		textColumn_KijyuntiHani.setColumnFilterable(true);
		textColumn_KijyuntiHani.setColumnName("KIJYUNTI_HANI");
		textColumn_KijyuntiHani.setColumnSortable(true);
		textColumn_KijyuntiHani.setPreferredWidth(80);
		textColumn_KijyuntiHani.setColumnRequired(false);
		// eidt s.inoue 2012/10/25
		textColumn_KijyuntiHani.setColumnVisible(true);
		textColumn_KijyuntiHani.setColumnFilterable(false);
		textColumn_KijyuntiHani.setColumnSortable(false);
		/*add tanaka 2013/11/15*/
//		textColumn_KijyuntiHani.setColumnVisible(true);
		textColumn_KijyuntiHani.setColumnVisible(!JApplication.flag_Master.contains(FlagEnum_Master.KIJYUNTI_HANI));

		// �P�����f
		textColumn_TankaKenshin.setColumnFilterable(true);
		textColumn_TankaKenshin.setColumnName("TANKA_KENSIN");
		textColumn_TankaKenshin.setColumnSortable(true);
		textColumn_TankaKenshin.setPreferredWidth(80);
		textColumn_TankaKenshin.setEditableOnEdit(true);
		textColumn_TankaKenshin.setEditableOnInsert(true);
		textColumn_TankaKenshin.setColumnRequired(false);
		// eidt s.inoue 2012/10/25
		textColumn_TankaKenshin.setColumnVisible(true);
		textColumn_TankaKenshin.setColumnFilterable(false);
		textColumn_TankaKenshin.setColumnSortable(false);
		/*add tanaka 2013/11/15*/
//		textColumn_TankaKenshin.setColumnVisible(true);
		textColumn_TankaKenshin.setColumnVisible(!JApplication.flag_Master.contains(FlagEnum_Master.TANKA_KENSIN));

		// ���l
		textColumn_Bikou.setColumnFilterable(true);
		textColumn_Bikou.setColumnName("BIKOU");
		textColumn_Bikou.setColumnSortable(true);
		textColumn_Bikou.setPreferredWidth(150);
		textColumn_Bikou.setEditableOnEdit(true);
		textColumn_Bikou.setEditableOnInsert(true);
		textColumn_Bikou.setColumnRequired(false);
		// eidt s.inoue 2012/10/25
		textColumn_Bikou.setColumnVisible(true);
		textColumn_Bikou.setColumnFilterable(false);
		textColumn_Bikou.setColumnSortable(false);
		/*add tanaka 2013/11/15*/
//		textColumn_Bikou.setColumnVisible(true);
		textColumn_Bikou.setColumnVisible(!JApplication.flag_Master.contains(FlagEnum_Master.BIKOU));

		/* button */
		setJButtons();

		// openswing s.inoue 2011/02/03
		flowLayoutOriginePanel.setAlignment(FlowLayout.LEFT);
		buttonOriginePanel.setLayout(flowLayoutOriginePanel);
//	    buttonsPanel.add(insertButton, null);
	    buttonOriginePanel.add(editButton, null);
	    buttonOriginePanel.add(saveButton, null);
	    buttonOriginePanel.add(filterButton, null);
	    buttonOriginePanel.add(reloadButton, null);
//	    buttonOriginePanel.add(deleteButton, null);
	    buttonOriginePanel.add(exportButton, null);
	    buttonOriginePanel.add(navigatorBar, null);

	    // openswing s.inoue 2011/02/03
	    flowLayoutPanel.setAlignment(FlowLayout.LEFT);
	    buttonPanel.setLayout(flowLayoutPanel);
	    buttonPanel.add(buttonClose, null);
	    buttonPanel.add(buttonExport,null);
	    buttonPanel.add(buttonImport,null);
	    buttonPanel.add(buttonDefault,null);	// edit n.ohkubo 2015/03/01�@�ǉ�

		// action�ݒ�
		buttonClose.addActionListener(new ListFrame_button_actionAdapter(this));
		buttonExport.addActionListener(new ListFrame_button_actionAdapter(this));
		buttonImport.addActionListener(new ListFrame_button_actionAdapter(this));
		buttonDefault.addActionListener(new ListFrame_button_actionAdapter(this));	// edit n.ohkubo 2015/03/01�@�ǉ�z

		// openswing s.inoue 2011/01/25
		Box origineBox = new Box(BoxLayout.X_AXIS);

//		origineBox.add(saveButton);
//		origineBox.add(editButton);
//		origineBox.add(reloadButton);
//		origineBox.add(deleteButton);
//		origineBox.add(saveButton);
//		origineBox.add(exportButton);
//		origineBox.add(navigatorBar);
		// openswing s.inoue 2011/02/03
		origineBox.add(buttonOriginePanel);

		origineBox.add(Box.createVerticalStrut(2));

//		// box2�s��
		Box recordBox = new Box(BoxLayout.X_AXIS);
//		recordBox.add(buttonJusinkenAdd);
//		recordBox.add(buttonKekkaCopy);
//		recordBox.add(buttonJusinkenCall);
//		recordBox.add(buttonKekkaInputCall);
//		recordBox.add(buttonDeleteKekka);
//		recordBox.add(buttonDeleteKojin);
//		recordBox.add(buttonPrint);
//		recordBox.add(buttonIrai);

// openswing s.inoue 2011/02/03 combo���g�p
//		LabelControl labelCombo = new LabelControl();
//	    labelCombo.setText("combobox");
//	    comboSelectHokenja.setCanCopy(true);
//	    comboSelectHokenja.setDomainId("ORDERSTATE");
//	    comboSelectHokenja.setLinkLabel(labelCombo);
//	    comboSelectHokenja.setRequired(true);
//	    recordBox.add(Box.createHorizontalStrut(2), BorderLayout.WEST);
//		recordBox.add(comboSelectHokenja);
//		recordBox.add(Box.createHorizontalStrut(2), BorderLayout.WEST);
		recordBox.add(buttonPanel);
//		recordBox.add(Box.createHorizontalStrut(910), BorderLayout.WEST);

		Box buttonBox = Box.createVerticalBox();
		buttonBox.add(origineBox);
		buttonBox.add(new JSeparator());
		buttonBox.add(recordBox);

		/* list */
		grid.setDefaultQuickFilterCriteria(org.openswing.swing.util.java.Consts.CONTAINS);

		// eidt s.inoue 2012/11/30
		filterButton.setMnemonic(KeyEvent.VK_F);
		reloadButton.setMnemonic(KeyEvent.VK_Z);

		grid.setEditButton(editButton);
		grid.setReloadButton(reloadButton);
//		grid.setDeleteButton(deleteButton);
		grid.setSaveButton(saveButton);
		grid.setNavBar(navigatorBar);
		grid.setExportButton(exportButton);
		grid.setFilterButton(filterButton);

		grid.setMaxSortedColumns(5);

		grid.setValueObjectClassName(CONST_DATA_VALUE);
		grid.setOrderWithLoadData(false);

		grid.getColumnContainer().add(textColumn_HokenjaNumber, null);
		grid.getColumnContainer().add(textColumn_KoumokuCd, null);
		grid.getColumnContainer().add(textColumn_KoumokuNm, null);
		grid.getColumnContainer().add(textColumn_KensaHouhou, null);
		grid.getColumnContainer().add(textColumn_HisuFlg, null);
		grid.getColumnContainer().add(textColumn_DSKagen, null);
		grid.getColumnContainer().add(textColumn_DSJyougen, null);
		grid.getColumnContainer().add(textColumn_JSKagen, null);
		grid.getColumnContainer().add(textColumn_JSJyougen, null);
		grid.getColumnContainer().add(textColumn_Tani, null);
		grid.getColumnContainer().add(textColumn_Kagen, null);
		grid.getColumnContainer().add(textColumn_Jyogen, null);
		grid.getColumnContainer().add(textColumn_KijyuntiHani, null);

		grid.getColumnContainer().add(textColumn_TankaKenshin, null);
		grid.getColumnContainer().add(textColumn_Bikou, null);

		columnContainer = grid.getColumnContainer();// edit n.ohkubo 2014/10/01�@�ǉ�
		
		// add s.inoue 2012/11/12
		jLabel_Title = new TitleLabel("tokutei.kenshin-item-mastermaintenance.frame.title","tokutei.kenshin-item-mastermaintenance.frame.guidance");
		jLabel_Title.setFont(new Font("�l�r �S�V�b�N", Font.PLAIN, 14));

		this.getContentPane().add(grid, BorderLayout.CENTER);
		this.getContentPane().add(getJPanel_TitleArea(buttonBox), BorderLayout.NORTH);

		this.setTitle(ViewSettings.getTokuteFrameTitleWithKikanInfomation());

	}

	/**
	 * This method initializes jPanel_TitleArea
	 *
	 * @return javax.swing.JPanel
	 */
	protected JPanel jPanel_TitleArea = null;
	protected ExtendedLabel jLabel_Title = null;

	private JPanel getJPanel_TitleArea(Box buttonBox) {
		if (jPanel_TitleArea == null) {
			GridBagConstraints gridBagConstraints26 = new GridBagConstraints();
			gridBagConstraints26.insets = new Insets(0, 0, 0, 0);
			gridBagConstraints26.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints26.gridy = 1;
			gridBagConstraints26.gridx = 0;

			GridBagConstraints gridBagConstraints25 = new GridBagConstraints();
			gridBagConstraints25.gridy = 0;
//			gridBagConstraints25.ipady = 10;
			gridBagConstraints25.anchor = GridBagConstraints.WEST;
			// gridBagConstraints25.insets = new Insets(0, 0, 5, 0);
			gridBagConstraints25.insets = new Insets(5, 5, 0, 0);
			gridBagConstraints25.weightx = 1.0D;
			gridBagConstraints25.gridx = 0;

			jPanel_TitleArea = new JPanel();
			jPanel_TitleArea.setLayout(new GridBagLayout());
			jPanel_TitleArea.setName("jPanel2");
			jPanel_TitleArea.add(jLabel_Title, gridBagConstraints25);
			 jPanel_TitleArea.add(buttonBox, gridBagConstraints26);
		}
		return jPanel_TitleArea;
	}

	/* �{�^���A�N�V�����p�����N���X */
	class ListFrame_button_actionAdapter implements java.awt.event.ActionListener,java.awt.event.KeyListener {
	  JKenshinMasterMaintenanceListFrame adaptee;

		  ListFrame_button_actionAdapter(JKenshinMasterMaintenanceListFrame adaptee) {
		    this.adaptee = adaptee;
		  }
		  @Override
		public void actionPerformed(ActionEvent e) {
			  Object source = e.getSource();
			  if (source == buttonClose){
				  logger.info(buttonClose.getText());
				  presevColumnStatus();//add tanaka 2013/11/15
				  adaptee.closeButtton_actionPerformed(e);
			  }else if(source == buttonExport){
				  logger.info(buttonExport.getText());
				  pushedExportButton(e);
			  }else if(source == buttonImport){
				  logger.info(buttonImport.getText());
				  pushedImportButton(e);
			  }
			// edit n.ohkubo 2015/03/01�@�ǉ��@start
			  else if (source == buttonDefault) {
				  logger.info(buttonDefault.getText());
				  pushedDefaultButton();
			  }
			// edit n.ohkubo 2015/03/01�@�ǉ��@end
		  }
		private void presevColumnStatus() {
			if(!textColumn_HokenjaNumber.isVisible()) {
				if(!JApplication.flag_Master.contains(FlagEnum_Master.HKNJANUM))
					JApplication.flag_Master.addAll(EnumSet.of(FlagEnum_Master.HKNJANUM));
			} else {
				JApplication.flag_Master.removeAll(EnumSet.of(FlagEnum_Master.HKNJANUM));
			}
			
			if(!textColumn_KoumokuCd.isVisible()) {
				if(!JApplication.flag_Master.contains(FlagEnum_Master.KOUMOKU_CD))
					JApplication.flag_Master.addAll(EnumSet.of(FlagEnum_Master.KOUMOKU_CD));
			} else {
				JApplication.flag_Master.removeAll(EnumSet.of(FlagEnum_Master.KOUMOKU_CD));
			}
			
			if(!textColumn_KoumokuNm.isVisible()) {
				if(!JApplication.flag_Master.contains(FlagEnum_Master.KOUMOKU_NAME))
					JApplication.flag_Master.addAll(EnumSet.of(FlagEnum_Master.KOUMOKU_NAME));
			} else {
				JApplication.flag_Master.removeAll(EnumSet.of(FlagEnum_Master.KOUMOKU_NAME));
			}
			
			if(!textColumn_KensaHouhou.isVisible()) {
				if(!JApplication.flag_Master.contains(FlagEnum_Master.KENSA_HOUHOU))
					JApplication.flag_Master.addAll(EnumSet.of(FlagEnum_Master.KENSA_HOUHOU));
			} else {
				JApplication.flag_Master.removeAll(EnumSet.of(FlagEnum_Master.KENSA_HOUHOU));
			}
			
			if(!textColumn_HisuFlg.isVisible()) {
				if(!JApplication.flag_Master.contains(FlagEnum_Master.HISU_FLG))
					JApplication.flag_Master.addAll(EnumSet.of(FlagEnum_Master.HISU_FLG));
			} else {
				JApplication.flag_Master.removeAll(EnumSet.of(FlagEnum_Master.HISU_FLG));
			}
			
			if(!textColumn_DSKagen.isVisible()) {
				if(!JApplication.flag_Master.contains(FlagEnum_Master.DS_KAGEN))
					JApplication.flag_Master.addAll(EnumSet.of(FlagEnum_Master.DS_KAGEN));
			} else {
				JApplication.flag_Master.removeAll(EnumSet.of(FlagEnum_Master.DS_KAGEN));
			}
			
			if(!textColumn_DSJyougen.isVisible()) {
				if(!JApplication.flag_Master.contains(FlagEnum_Master.DS_JYOUGEN))
					JApplication.flag_Master.addAll(EnumSet.of(FlagEnum_Master.DS_JYOUGEN));
			} else {
				JApplication.flag_Master.removeAll(EnumSet.of(FlagEnum_Master.DS_JYOUGEN));
			}
			
			if(!textColumn_JSKagen.isVisible()) {
				if(!JApplication.flag_Master.contains(FlagEnum_Master.JS_KAGEN))
					JApplication.flag_Master.addAll(EnumSet.of(FlagEnum_Master.JS_KAGEN));
			} else {
				JApplication.flag_Master.removeAll(EnumSet.of(FlagEnum_Master.JS_KAGEN));
			}
			
			if(!textColumn_JSJyougen.isVisible()) {
				if(!JApplication.flag_Master.contains(FlagEnum_Master.JS_JYOUGEN))
					JApplication.flag_Master.addAll(EnumSet.of(FlagEnum_Master.JS_JYOUGEN));
			} else {
				JApplication.flag_Master.removeAll(EnumSet.of(FlagEnum_Master.JS_JYOUGEN));
			}
			
			if(!textColumn_Tani.isVisible()) {
				if(!JApplication.flag_Master.contains(FlagEnum_Master.TANI))
					JApplication.flag_Master.addAll(EnumSet.of(FlagEnum_Master.TANI));
			} else {
				JApplication.flag_Master.removeAll(EnumSet.of(FlagEnum_Master.TANI));
			}
			
			if(!textColumn_Kagen.isVisible()) {
				if(!JApplication.flag_Master.contains(FlagEnum_Master.KAGEN))
					JApplication.flag_Master.addAll(EnumSet.of(FlagEnum_Master.KAGEN));
			} else {
				JApplication.flag_Master.removeAll(EnumSet.of(FlagEnum_Master.KAGEN));
			}
			
			if(!textColumn_Jyogen.isVisible()) {
				if(!JApplication.flag_Master.contains(FlagEnum_Master.JYOUGEN))
					JApplication.flag_Master.addAll(EnumSet.of(FlagEnum_Master.JYOUGEN));
			} else {
				JApplication.flag_Master.removeAll(EnumSet.of(FlagEnum_Master.JYOUGEN));
			}
			
			if(!textColumn_KijyuntiHani.isVisible()) {
				if(!JApplication.flag_Master.contains(FlagEnum_Master.KIJYUNTI_HANI))
					JApplication.flag_Master.addAll(EnumSet.of(FlagEnum_Master.KIJYUNTI_HANI));
			} else {
				JApplication.flag_Master.removeAll(EnumSet.of(FlagEnum_Master.KIJYUNTI_HANI));
			}
			
			if(!textColumn_TankaKenshin.isVisible()) {
				if(!JApplication.flag_Master.contains(FlagEnum_Master.TANKA_KENSIN))
					JApplication.flag_Master.addAll(EnumSet.of(FlagEnum_Master.TANKA_KENSIN));
			} else {
				JApplication.flag_Master.removeAll(EnumSet.of(FlagEnum_Master.TANKA_KENSIN));
			}
			
			if(!textColumn_Bikou.isVisible()) {
				if(!JApplication.flag_Master.contains(FlagEnum_Master.BIKOU))
					JApplication.flag_Master.addAll(EnumSet.of(FlagEnum_Master.BIKOU));
			} else {
				JApplication.flag_Master.removeAll(EnumSet.of(FlagEnum_Master.BIKOU));
			}
			
			// edit n.ohkubo 2014/10/01�@�ǉ� start
			//�u�\�� or ��\���v��DB�֓o�^����
			((JKenshinMasterMaintenanceListFrameCtrl)grid.getController()).preserveColumnStatus();
			// edit n.ohkubo 2014/10/01�@�ǉ� end
		}
		@Override
		public void keyPressed(KeyEvent e) {
			adaptee.closeButtton_keyPerformed(e);

		}
		@Override
		public void keyReleased(KeyEvent e) {

		}
		@Override
		public void keyTyped(KeyEvent e) {

		}
	}


	/* �{�^���Q */
	/**
	 * This method initializes jButton_Close
	 *
	 * @return jp.or.med.orca.jma_tokutei.common.component.ExtendedButton
	 */
	private void setJButtons() {

		// combobox�ݒ�
		Response res = ClientUtils.getData("loadCallOutTypes",new GridParams());
	    Domain d = new Domain("CALL_OUT_TYPES");
	    if (!res.isError()) {
	      JKenshinMasterMaintenanceHokenjyaVO vo = null;
	      java.util.List list = ((VOListResponse)res).getRows();
	      for(int i=0;i<list.size();i++) {
	        vo = (JKenshinMasterMaintenanceHokenjyaVO)list.get(i);
	        d.addDomainPair(vo.getHokenjyaNumber(),vo.getHokenjyaName());
	      }
	    }

//		if (comboSelectHokenja == null) {
//		    comboSelectHokenja = new ComboBoxControl();
//
////		    comboSelectHokenja.setPreferredSize(new Dimension(100,20));
////		    comboSelectHokenja.setVerticalAlignment(SwingConstants.BOTTOM);
////		    comboSelectHokenja.setHorizontalTextPosition(JLabel.CENTER);
////		    comboSelectHokenja.setVerticalTextPosition(SwingConstants.BOTTOM);
//
//		    comboSelectHokenja.setDomain(d);
//		    comboSelectHokenja.getComboBox().addItemListener(new ItemListener() {
//		      public void itemStateChanged(ItemEvent e) {
//		        if (e.getStateChange()==e.SELECTED) {
////		          hierarTreePanel.setProgressiveHIE02((BigDecimal)comboBoxControl1.getValue());
////		          hierarTreePanel.reloadTree();
//		          grid.clearData();
//		        }
//		      }
//		    });
//		    if (d.getDomainPairList().length==1)
//		      comboSelectHokenja.getComboBox().setSelectedIndex(0);
//		    else
//		      comboSelectHokenja.getComboBox().setSelectedIndex(-1);
//
//		    comboSelectHokenja.setDomainId("SEX");
//		}

		if (buttonClose == null) {
			// ����button
			ExtendedImageIcon iIcon = new ExtendedImageIcon(JPath.Ico_Common_Back);
			ImageIcon icon = iIcon.setStrechIcon(this, JPath.CONST_FIX_ICON);

			buttonClose= new ExtendedOpenGenericButton(
					"Close","�߂�(R)","�߂�(ALT+R)",KeyEvent.VK_R,icon);
		}
		if (buttonExport == null) {
			ExtendedImageIcon iIcon = new ExtendedImageIcon(JPath.Ico_Common_Export);
			ImageIcon icon = iIcon.setStrechIcon(this, JPath.CONST_FIX_ICON);

			buttonExport= new ExtendedOpenGenericButton(
					"Exort","���o(O)","���o(ALT+O)",KeyEvent.VK_O,icon);
			buttonExport.addActionListener(this);
		}
		if (buttonImport == null) {
			ExtendedImageIcon iIcon = new ExtendedImageIcon(JPath.Ico_Common_Import);
			ImageIcon icon = iIcon.setStrechIcon(this, JPath.CONST_FIX_ICON);

			buttonImport= new ExtendedOpenGenericButton(
					"Import","�捞(I)","�捞(ALT+I)",KeyEvent.VK_I,icon);
			buttonImport.addActionListener(this);
		}
		
		// edit n.ohkubo 2015/03/01�@�ǉ��@start
		if (buttonDefault == null) {
			ExtendedImageIcon iIcon = new ExtendedImageIcon(JPath.Ico_Default);
			ImageIcon icon = iIcon.setStrechIcon(this, JPath.CONST_FIX_ICON);

			buttonDefault= new ExtendedOpenGenericButton(
					"Default","�����l(Q)","�����l(ALT+Q)",KeyEvent.VK_Q,icon);
			buttonDefault.addActionListener(this);
			
			//�����\�����͔񊈐�
			buttonDefault.setEnabled(false);
		}
		//�����l�{�^���̐����ǉ�
		if (editButton != null) {
			editButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					//�C���{�^�������Ŋ�����
					buttonDefault.setEnabled(true);
				}
			});
		}
		// edit n.ohkubo 2015/03/01�@�ǉ��@end
		
//		// ���ʍ폜
//		if (buttonDeleteKekka == null) {
//			ExtendedImageIcon iIcon = new ExtendedImageIcon(ICON_DELETE_KEKKA);
//			ImageIcon icon = iIcon.setStrechIcon(this, CONST_FIX_ICON);
//			buttonDeleteKekka = new GenericButton();
//			buttonDeleteKekka.setVerticalAlignment(SwingConstants.BOTTOM);
//			buttonDeleteKekka.setHorizontalTextPosition(JLabel.CENTER);
//			buttonDeleteKekka.setVerticalTextPosition(SwingConstants.BOTTOM);
//			buttonDeleteKekka.setAttributeName("Close");
//			buttonDeleteKekka.setMnemonic(KeyEvent.VK_C);
//			buttonDeleteKekka.setText("���ʍ폜");
//			buttonDeleteKekka.setToolTipText("���ʍ폜(ALT+C)");
//			buttonDeleteKekka.setIcon(icon);
//		}
//
//		// ��f���폜
//		if (buttonDeleteKojin == null) {
//			ExtendedImageIcon iIcon = new ExtendedImageIcon(ICON_DELETE_JUSINKEN);
//			ImageIcon icon = iIcon.setStrechIcon(this, CONST_FIX_ICON);
//			buttonDeleteKojin = new GenericButton();
//			buttonDeleteKojin.setVerticalAlignment(SwingConstants.BOTTOM);
//			buttonDeleteKojin.setHorizontalTextPosition(JLabel.CENTER);
//			buttonDeleteKojin.setVerticalTextPosition(SwingConstants.BOTTOM);
//			buttonDeleteKojin.setAttributeName("Close");
//			buttonDeleteKojin.setMnemonic(KeyEvent.VK_C);
//			buttonDeleteKojin.setText("��f���폜");
//			buttonDeleteKojin.setToolTipText("��f���폜(ALT+C)");
//			buttonDeleteKojin.setIcon(icon);
//		}
//
//		// ���͕[���
//		if (buttonPrint == null) {
//			ExtendedImageIcon iIcon = new ExtendedImageIcon(ICON_PRINT_NYURYOKUHYO);
//			ImageIcon icon = iIcon.setStrechIcon(this, CONST_FIX_ICON);
//			buttonPrint = new GenericButton();
//			buttonPrint.setVerticalAlignment(SwingConstants.BOTTOM);
//			buttonPrint.setHorizontalTextPosition(JLabel.CENTER);
//			buttonPrint.setVerticalTextPosition(SwingConstants.BOTTOM);
//			buttonPrint.setAttributeName("Close");
//			buttonPrint.setMnemonic(KeyEvent.VK_C);
//			buttonPrint.setText("���͕[���");
//			buttonPrint.setToolTipText("���͕[���(ALT+C)");
//			buttonPrint.setIcon(icon);
//		}
//
//		// ��f���ǉ�
//		if (buttonJusinkenAdd == null) {
//			ExtendedImageIcon iIcon = new ExtendedImageIcon(ICON_ADD_JUSINKEN);
//			ImageIcon icon = iIcon.setStrechIcon(this, CONST_FIX_ICON);
//			buttonJusinkenAdd = new GenericButton();
//			buttonJusinkenAdd.setVerticalAlignment(SwingConstants.BOTTOM);
//			buttonJusinkenAdd.setHorizontalTextPosition(JLabel.CENTER);
//			buttonJusinkenAdd.setVerticalTextPosition(SwingConstants.BOTTOM);
//			buttonJusinkenAdd.setAttributeName("Close");
//			buttonJusinkenAdd.setMnemonic(KeyEvent.VK_C);
//			buttonJusinkenAdd.setText("��f���ǉ�");
//			buttonJusinkenAdd.setToolTipText("��f���ǉ�(ALT+C)");
//			buttonJusinkenAdd.setIcon(icon);
//		}
//
//		// �˗������
//		if (buttonIrai == null) {
//			ExtendedImageIcon iIcon = new ExtendedImageIcon(ICON_PRINT_IRAISHO);
//			ImageIcon icon = iIcon.setStrechIcon(this, CONST_FIX_ICON);
//			buttonIrai = new GenericButton();
//			buttonIrai.setVerticalAlignment(SwingConstants.BOTTOM);
//			buttonIrai.setHorizontalTextPosition(JLabel.CENTER);
//			buttonIrai.setVerticalTextPosition(SwingConstants.BOTTOM);
//			buttonIrai.setAttributeName("Close");
//			buttonIrai.setMnemonic(KeyEvent.VK_C);
//			buttonIrai.setText("�˗������");
//			buttonIrai.setToolTipText("�˗������(ALT+C)");
//			buttonIrai.setIcon(icon);
//		}
//
//		// ���ʕ���
//		if (buttonKekkaCopy == null) {
//			ExtendedImageIcon iIcon = new ExtendedImageIcon(ICON_COPY_KEKKA);
//			ImageIcon icon = iIcon.setStrechIcon(this, CONST_FIX_ICON);
//			buttonKekkaCopy = new GenericButton();
//			buttonKekkaCopy.setVerticalAlignment(SwingConstants.BOTTOM);
//			buttonKekkaCopy.setHorizontalTextPosition(JLabel.CENTER);
//			buttonKekkaCopy.setVerticalTextPosition(SwingConstants.BOTTOM);
//			buttonKekkaCopy.setAttributeName("Close");
//			buttonKekkaCopy.setMnemonic(KeyEvent.VK_C);
//			buttonKekkaCopy.setText("���ʕ���");
//			buttonKekkaCopy.setToolTipText("���ʕ���(ALT+C)");
//			buttonKekkaCopy.setIcon(icon);
//		}
//
//		// ���ʓ��͌ďo
//		if (buttonKekkaInputCall == null) {
//			ExtendedImageIcon iIcon = new ExtendedImageIcon(ICON_INPUT_KEKKA);
//			ImageIcon icon = iIcon.setStrechIcon(this, CONST_FIX_ICON);
//			buttonKekkaInputCall = new GenericButton();
//			buttonKekkaInputCall.setVerticalAlignment(SwingConstants.BOTTOM);
//			buttonKekkaInputCall.setHorizontalTextPosition(JLabel.CENTER);
//			buttonKekkaInputCall.setVerticalTextPosition(SwingConstants.BOTTOM);
//			buttonKekkaInputCall.setAttributeName("INPUT_KEKKA");
//			buttonKekkaInputCall.setMnemonic(KeyEvent.VK_C);
//			buttonKekkaInputCall.setText("���ʓ���");
//			buttonKekkaInputCall.setToolTipText("���ʓ���(ALT+C)");
//			buttonKekkaInputCall.setIcon(icon);
//		}
//
//		// ��f���ďo
//		if (buttonJusinkenCall == null) {
//			ExtendedImageIcon iIcon = new ExtendedImageIcon(ICON_INPUT_JUSINKEN);
//			ImageIcon icon = iIcon.setStrechIcon(this, CONST_FIX_ICON);
//			buttonJusinkenCall = new GenericButton();
//			buttonJusinkenCall.setVerticalAlignment(SwingConstants.BOTTOM);
//			buttonJusinkenCall.setHorizontalTextPosition(JLabel.CENTER);
//			buttonJusinkenCall.setVerticalTextPosition(SwingConstants.BOTTOM);
//			buttonJusinkenCall.setAttributeName("INPUT_KEKKA");
//			buttonJusinkenCall.setMnemonic(KeyEvent.VK_C);
//			buttonJusinkenCall.setText("��f���ďo");
//			buttonJusinkenCall.setToolTipText("��f���ďo(ALT+C)");
//			buttonJusinkenCall.setIcon(icon);
//		}
	}


	// add s.inoue 2010/03/12
	/**ImportStart����������**********************************************************/
	/**
	 * �捞�{�^��
	 */
	public void pushedImportButton( ActionEvent e )
	{
		try {
			filePathDialog = DialogFactory.getInstance().createDialog(this,null);

			filePathDialog.setMessageTitle(selectTitle);
			filePathDialog.setDialogTitle(selectDialogTitle);
			filePathDialog.setEnabled(false);
			filePathDialog.setDialogSelect(true);
			filePathDialog.setVisible(true);

			// eidt s.inoue 2012/07/06
			if (filePathDialog.getStatus() == null)return;
			if (filePathDialog.getStatus().equals(RETURN_VALUE.CANCEL))
				return;

			String filePath = filePathDialog.getFilePath();
			// add s.inoue 2010/03/05
			File fileImport = new File(filePath);
			if(!fileImport.exists())
				return;
			importCsvData(filePath);
			// del s.inoue 2011/04/07
			// initializeSetting();
			// initializeCombobox();

		} catch (Exception ex) {
			JErrorMessage.show("M3824",this);
			logger.error(ex.getMessage());
		}
	}

	/* �捞���� */
	private void importCsvData(String filePath){

		RETURN_VALUE retValue = JErrorMessage.show("M3825", this);

		// cancel��
		if (retValue == RETURN_VALUE.NO){
			return;
		}else if (retValue == RETURN_VALUE.YES){

			JImportMasterErrorKenshinResultFrameData data = new JImportMasterErrorKenshinResultFrameData();

			// CSV�Ǎ�����
			reader = new JCSVReaderStream();

			try {
				reader.openCSV(filePath,JApplication.CSV_CHARSET,',');
			} catch (IOException e) {
				JErrorMessage.show("M3824",this);
				logger.error(e.getMessage());
			}

			CSVItems = reader.readAllTable();

			try {
				// eidt s.inoue 2011/06/07
				// JApplication.kikanDatabase.Transaction();
				JApplication.kikanDatabase.getMConnection().setAutoCommit(false);

				int csvCount = CSVItems.size();

				if(csvCount == 0){
					JErrorMessage.show("M3827",this);
					return;
				}

				// �f�[�^�捞����
				for (int i = 1; i < csvCount; i++) {

					Vector<String> insertRow = new Vector<String>();

					insertRow =CSVItems.get(i);

					// �������擾 CSV�f�[�^�����[�J���ϐ��ɃZ�b�g(�u"�v��������������)
					setCSVItemsToDB(data,insertRow);
// edit s.inoue 2011/04/07
//					if(!validateData(data))
//						return;
					validateData(data);

					// ���f�}�X�^�o�^
					kenshinMasterRegister(data);
				}

				// eidt s.inoue 2011/06/07
				// JApplication.kikanDatabase.Commit();
				JApplication.kikanDatabase.getMConnection().commit();
				reloadButton.doClick();

				String[] messageParams = {String.valueOf(csvCount-1)};
				JErrorMessage.show("M3828",this,messageParams);
			} catch (SQLException e) {
				try {
					// eidt s.inoue 2011/06/07
					// JApplication.kikanDatabase.rollback();
					JApplication.kikanDatabase.getMConnection().rollback();
				} catch (SQLException e1) {}
				JErrorMessage.show("M3826",this);
				logger.error(e.getMessage());
			}
		}
	}

	// edit s.inoue 2010/03/04
	/* csv�f�[�^�捞 */
	private void setCSVItemsToDB(JImportMasterErrorKenshinResultFrameData data,
			Vector<String> insertRow){

			data.CSV_COLUMN_HKNJANUM = (reader.rmQuart(insertRow.get(0)));
			data.CSV_COLUMN_KOUMOKU_CD = (reader.rmQuart(insertRow.get(1)));
			data.CSV_COLUMN_HISU_FLG = (reader.rmQuart(insertRow.get(2)));
			data.CSV_COLUMN_DS_KAGEN = (reader.rmQuart(insertRow.get(3)));
			data.CSV_COLUMN_DS_JYOUGEN = (reader.rmQuart(insertRow.get(4)));
			data.CSV_COLUMN_JS_KAGEN = (reader.rmQuart(insertRow.get(5)));
			data.CSV_COLUMN_JS_JYOUGEN = (reader.rmQuart(insertRow.get(6)));
			data.CSV_COLUMN_TANI = (reader.rmQuart(insertRow.get(7)));
			// edit s.inoue 2010/07/06
			data.CSV_TANKA_KENSIN = (reader.rmQuart(insertRow.get(8)));
			data.CSV_COLUMN_BIKOU = (reader.rmQuart(insertRow.get(9)));
	}

	/* validate���� */
	private void validateData(JImportMasterErrorKenshinResultFrameData data) {
// eidt s.inoue 2011/04/07
//		boolean rettanka = false;
//		rettanka= validatedData.setHKNJANUM(data.CSV_COLUMN_HKNJANUM)
//			&& validatedData.setKOUMOKU_CD(data.CSV_COLUMN_KOUMOKU_CD)
//			&& validatedData.setHISU_FLG(data.CSV_COLUMN_HISU_FLG)
//			&& validatedData.setJS_KAGEN(data.CSV_COLUMN_JS_KAGEN)
//			&& validatedData.setJS_JYOUGEN(data.CSV_COLUMN_JS_JYOUGEN)
//			&& validatedData.setDS_KAGEN(data.CSV_COLUMN_DS_KAGEN)
//			&& validatedData.setDS_JYOUGEN(data.CSV_COLUMN_DS_JYOUGEN)
//			&& validatedData.setTANI(data.CSV_COLUMN_TANI)
//			&& validatedData.setTANKA_KENSIN(data.CSV_TANKA_KENSIN)
//			&& validatedData.setBIKOU(data.CSV_COLUMN_BIKOU);

		validatedData.setHKNJANUM(data.CSV_COLUMN_HKNJANUM);
		validatedData.setKOUMOKU_CD(data.CSV_COLUMN_KOUMOKU_CD);
		validatedData.setHISU_FLG(data.CSV_COLUMN_HISU_FLG);
		validatedData.setJS_KAGEN(data.CSV_COLUMN_JS_KAGEN);
		validatedData.setJS_JYOUGEN(data.CSV_COLUMN_JS_JYOUGEN);
		validatedData.setDS_KAGEN(data.CSV_COLUMN_DS_KAGEN);
		validatedData.setDS_JYOUGEN(data.CSV_COLUMN_DS_JYOUGEN);
		validatedData.setTANI(data.CSV_COLUMN_TANI);
		validatedData.setTANKA_KENSIN(data.CSV_TANKA_KENSIN);
		validatedData.setBIKOU(data.CSV_COLUMN_BIKOU);
//		return rettanka;
	}

	/**
	 * CSV�f�[�^�o�^
	 * @throws SQLException
	 */
	private void kenshinMasterRegister(JImportMasterErrorKenshinResultFrameData data)
		throws SQLException
	{
		StringBuffer buffer = new StringBuffer();

		buffer = new StringBuffer("");
		buffer.append("UPDATE T_KENSHINMASTER SET ");
		buffer.append(" HISU_FLG = "
				+ JQueryConvert.queryConvertAppendComma(validatedData.getHISU_FLG()));
		buffer.append(" DS_KAGEN = "
				+ JQueryConvert.queryConvertAppendComma(validatedData.getDS_KAGEN()));
		buffer.append(" DS_JYOUGEN = "
				+ JQueryConvert.queryConvertAppendComma(validatedData.getDS_JYOUGEN()));
		buffer.append(" JS_KAGEN = "
				+ JQueryConvert.queryConvertAppendComma(validatedData.getJS_KAGEN()));
		buffer.append(" JS_JYOUGEN = "
				+ JQueryConvert.queryConvertAppendComma(validatedData.getJS_JYOUGEN()));
		buffer.append(" TANI = "
				+ JQueryConvert.queryConvertAppendComma(validatedData.getTANI()));
		buffer.append(" TANKA_KENSIN = "
				+ JQueryConvert.queryConvertAppendComma(validatedData.getTANKA_KENSIN()));
		buffer.append(" BIKOU = "
				+ JQueryConvert.queryConvert(validatedData.getBIKOU()));
		buffer.append(" WHERE HKNJANUM ="
				+ JQueryConvert.queryConvert(validatedData.getHKNJANUM()));
		buffer.append(" AND KOUMOKU_CD ="
				+ JQueryConvert.queryConvert(validatedData.getKOUMOKU_CD()));

		JApplication.kikanDatabase.sendNoResultQuery(buffer.toString());

	}
	/**ImportEnd����������**********************************************************/

	/**ExportStart����������**********************************************************/
	// add s.inoue 2010/02/25
	/**
	 * ���o�{�^��
	 */
	public void pushedExportButton( ActionEvent e )
	{
		try {
			String saveFileName = JPath.createDirectoryUniqueName("KenshinMaster");

			String defaltPath = JPath.getDesktopPath() +
			File.separator +
			saveFileName;

			filePathDialog = DialogFactory.getInstance().createDialog(this, defaltPath);
			filePathDialog.setMessageTitle(saveTitle);
			filePathDialog.setEnabled(false);
			filePathDialog.setDialogSelect(false);
			filePathDialog.setDialogTitle(savaDialogTitle);
			filePathDialog.setVisible(true);

			// eidt s.inoue 2012/07/06
			if (filePathDialog.getStatus() == null)return;
			if (filePathDialog.getStatus().equals(RETURN_VALUE.CANCEL))
				return;

			String filePath = filePathDialog.getFilePath();
			if (filePath.equals(""))
				return;
			exportCsvData(filePath);

		} catch (Exception ex) {
			JErrorMessage.show("M3829", this);
			logger.error(ex.getMessage());
		}
	}

	/* export���� */
	private void exportCsvData(String filePath){
		// JImportMasterErrorKenshinResultFrameData data = new JImportMasterErrorKenshinResultFrameData();

		// CSV�Ǎ�����
		writer = new JCSVWriterStream();

		try {
			writer.writeTable(getExportData());
			writer.saveCSV(filePath,JApplication.CSV_CHARSET);
		} catch (IOException e) {
			JErrorMessage.show("M3829", this);
			logger.error(e.getMessage());
		}
	}

	/* DB���f�[�^�擾*/
	private Vector<Vector<String>> getExportData(){

		Vector<Vector<String>> newTable = new Vector<Vector<String>>();

		ArrayList<Hashtable<String, String>> result
			= new ArrayList<Hashtable<String, String>>();

// del s.inoue 2011/04/07
//		if (jComboBox_HokenjyaNumber.getItemCount() <= 0)
//			return null;
//		String curHokenjyaNumber = new String(
//					((String) jComboBox_HokenjyaNumber.getSelectedItem()).substring(0, 8));

		StringBuilder sb = new StringBuilder();
		// edit s.inoue 2010/07/07
		sb.append(" SELECT HKNJANUM,KOUMOKU_CD,HISU_FLG,DS_KAGEN,DS_JYOUGEN,JS_KAGEN,JS_JYOUGEN,TANI,TANKA_KENSIN,BIKOU");
		sb.append(" FROM T_KENSHINMASTER");
//		sb.append(" WHERE HKNJANUM = " + JQueryConvert.queryConvert(curHokenjyaNumber));
		// eidt s.inoue 2011/04/07
		sb.append(" ORDER BY HKNJANUM,XMLITEM_SEQNO ");

		try {
			result = JApplication.kikanDatabase.sendExecuteQuery(sb.toString());
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

		Hashtable<String, String> resultItem = new Hashtable<String, String>();
		for( int i=0; i<result.size(); ++i )
		{
			resultItem = result.get(i);

			Vector<String> data = new Vector<String>();

			for (int j=0; j<resultItem.size(); ++j){
				data.add(resultItem.get(TABLE_COLUMNS[j]).trim());
			}

			// header�ݒ�
			if (i==0){
				Vector colmn = new Vector<String>();
				List l = java.util.Arrays.asList(TABLE_COLUMNS);
				for (Iterator item = l.iterator(); item.hasNext();) {
					colmn.add((String)item.next());
				}
				newTable.add(colmn);
			}
			newTable.add(data);
		}
		return newTable;
	}
	/**ExportEnd����������**********************************************************/

	/* �{�^���A�N�V���� */
	public void closeButtton_actionPerformed(ActionEvent e) {
		dispose();
	}
	/* �{�^���A�N�V���� */
	public void closeButtton_keyPerformed(KeyEvent e) {
		dispose();
	}

	// �C�x���g����
	@Override
	public void actionPerformed(ActionEvent ae) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
	}

	@Override
	public void keyReleased(KeyEvent e) {

	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

//	/* �{�^���A�N�V�����p�����N���X */
//	class ListFrame_closeButton_keyAdapter implements java.awt.event.KeyListener {
//	  JHokenjyaMasterMaintenanceListFrame adaptee;
//
//		ListFrame_closeButton_keyAdapter(JHokenjyaMasterMaintenanceListFrame adaptee) {
//		this.adaptee = adaptee;
//		}
//		@Override
//		public void keyPressed(KeyEvent e) {
//			adaptee.closeButtton_keyPerformed(e);
//		}
//		public void keyReleased(KeyEvent e) {
//		}
//		public void keyTyped(KeyEvent e) {
//
//		}
//	}
//	/* �{�^���A�N�V���� */
//	public void closeButtton_keyPerformed(KeyEvent e) {
//		dispose();
//	}
	
	// edit n.ohkubo 2015/03/01�@�ǉ��@start
	/**
	 * �����l�{�^����������
	 */
	private void pushedDefaultButton() {
		
		try {
			//�u�ēǍ��v���s�ŁA�Z���̐F�ƃc�[���`�b�v��߂����߁A������s���ɁA�����ݒ��TableCellRenderer��ޔ����Ă���
			if (tableCellRendererList == null) {
				tableCellRendererList = new ArrayList<TableCellRenderer>();
				if (isColumnVisible("DS_KAGEN")) {
					tableCellRendererList.add(grid.getTable().getGrid().getColumn(ClientSettings.getInstance().getResources().getResource("DS_KAGEN")).getCellRenderer());
				} else {
					tableCellRendererList.add(null);
				}
				if (isColumnVisible("DS_JYOUGEN")) {
					tableCellRendererList.add(grid.getTable().getGrid().getColumn(ClientSettings.getInstance().getResources().getResource("DS_JYOUGEN")).getCellRenderer());
				} else {
					tableCellRendererList.add(null);
				}
				if (isColumnVisible("JS_KAGEN")) {
					tableCellRendererList.add(grid.getTable().getGrid().getColumn(ClientSettings.getInstance().getResources().getResource("JS_KAGEN")).getCellRenderer());
				} else {
					tableCellRendererList.add(null);
				}
				if (isColumnVisible("JS_JYOUGEN")) {
					tableCellRendererList.add(grid.getTable().getGrid().getColumn(ClientSettings.getInstance().getResources().getResource("JS_JYOUGEN")).getCellRenderer());
				} else {
					tableCellRendererList.add(null);
				}
			}
			
			//�f�t�H���g�iHKNJANUM=99999999�j�̃f�[�^���擾
			Map<String, List<String>> defaultData = ((JKenshinMasterMaintenanceListFrameCtrl)grid.getController()).getDefaultData();
//			System.out.println(defaultData);
			
			//TableCellRenderer�Ŏg�p����l�̕ێ��pBeanList
			List<CellInfoBean> beanList_DS_KAGEN = new ArrayList<JKenshinMasterMaintenanceListFrame.CellInfoBean>();
			List<CellInfoBean> beanList_DS_JYOUGEN = new ArrayList<JKenshinMasterMaintenanceListFrame.CellInfoBean>();
			List<CellInfoBean> beanList_JS_KAGEN = new ArrayList<JKenshinMasterMaintenanceListFrame.CellInfoBean>();
			List<CellInfoBean> beanList_JS_JYOUGEN = new ArrayList<JKenshinMasterMaintenanceListFrame.CellInfoBean>();
			
			//�e�[�u���i���ݕ\�����Ă���l�j�̃f�[�^���擾
			VOListTableModel voListTableModel = grid.getVOListTableModel();
			for (int i = 0; i < voListTableModel.getRowCount(); i++) {
				
				//���ڃR�[�h���L�[�Ƀf�t�H���g�l���擾
				List<String> defaultDataList = defaultData.get(voListTableModel.getField(i, "KOUMOKU_CD"));
				
				//�f�t�H���g�l�ƍ��ق��L��ꍇ�ABean��add����
				//"��l����(�j��)"
				String dispVal_DS_KAGEN = (voListTableModel.getField(i, "DS_KAGEN") != null) ? (String)voListTableModel.getField(i, "DS_KAGEN") : "";
				if (!defaultDataList.get(0).equals(dispVal_DS_KAGEN)) {
					beanList_DS_KAGEN.add(
							createCellInfoBean(
									"DS_KAGEN", 
									defaultDataList.get(0), 
									dispVal_DS_KAGEN, 
									i
							)
					);
				}
				//"��l���(�j��)"
				String dispVal_DS_JYOUGEN = (voListTableModel.getField(i, "DS_JYOUGEN") != null) ? (String)voListTableModel.getField(i, "DS_JYOUGEN") : "";
				if (!defaultDataList.get(1).equals(dispVal_DS_JYOUGEN)) {
					beanList_DS_JYOUGEN.add(
							createCellInfoBean(
									"DS_JYOUGEN", 
									defaultDataList.get(1), 
									dispVal_DS_JYOUGEN, 
									i
							)
					);
				}
				//"��l����(����)"
				String dispVal_JS_KAGEN = (voListTableModel.getField(i, "JS_KAGEN") != null) ? (String)voListTableModel.getField(i, "JS_KAGEN") : "";
				if (!defaultDataList.get(2).equals(dispVal_JS_KAGEN)) {
					beanList_JS_KAGEN.add(
							createCellInfoBean(
									"JS_KAGEN", 
									defaultDataList.get(2), 
									dispVal_JS_KAGEN, 
									i
							)
					);
				}
				//"��l���(����)"
				String dispVal_JS_JYOUGEN = (voListTableModel.getField(i, "JS_JYOUGEN") != null) ? (String)voListTableModel.getField(i, "JS_JYOUGEN") : "";
				if (!defaultDataList.get(3).equals(dispVal_JS_JYOUGEN)) {
					beanList_JS_JYOUGEN.add(
							createCellInfoBean(
									"JS_JYOUGEN", 
									defaultDataList.get(3), 
									dispVal_JS_JYOUGEN, 
									i
							)
					);
				}
			}
			
			//�f�t�H���g�l�ƕ\���l�ɍ��ق��L��ꍇ�A��p��TableCellRenderer��ݒ肵�A�u�\���l�v�u�Z���̐F�v�u�c�[���`�b�v�v��ύX����
			if (beanList_DS_KAGEN.size() != 0 && isColumnVisible("DS_KAGEN")) {
				createMyTableCellRenderer(beanList_DS_KAGEN);
			}
			if (beanList_DS_JYOUGEN.size() != 0 && isColumnVisible("DS_JYOUGEN")) {
				createMyTableCellRenderer(beanList_DS_JYOUGEN);
			}
			if (beanList_JS_KAGEN.size() != 0 && isColumnVisible("JS_KAGEN")) {
				createMyTableCellRenderer(beanList_JS_KAGEN);
			}
			if (beanList_JS_JYOUGEN.size() != 0 && isColumnVisible("JS_JYOUGEN")) {
				createMyTableCellRenderer(beanList_JS_JYOUGEN);
			}

			//�Z���̐F�\���̔��f�i�{�^�����������ł͕\�������f����Ȃ��̂ŁA�e�[�u���ɑ΂��ĉ����A�N�V������������i�e�[�u���̍s�Ƀ}�E�X�̃J�[�\����������Ε\�������f����邪�A�{�^�������݂̂Ŕ��f������j�j
			grid.getTable().getGrid().clearSelection();
			
		} catch (Exception ex) {
			JErrorMessage.show("M3820", null);
			ex.printStackTrace();
		}
	}
	
	/**
	 * TableCellRenderer�̐ݒ�ƁA�\���l���f�t�H���g�l�֕ς���
	 * 
	 * @param cellInfoBeanList
	 */
	private void createMyTableCellRenderer(List<CellInfoBean> cellInfoBeanList) {
		
		//TableCellRenderer�ŁA�u�Z���̐F�v�Ɓu�c�[���`�b�v�v��ύX����
		MyTableCellRenderer myTableCellRenderer = new MyTableCellRenderer(cellInfoBeanList);
		grid.getTable().getGrid().getColumn(ClientSettings.getInstance().getResources().getResource(cellInfoBeanList.get(0).getAttributeName())).setCellRenderer(myTableCellRenderer);

		for (int i = 0; i < cellInfoBeanList.size(); i++) {
			//�\���l���f�t�H���g�l�֕ς���
			grid.getVOListTableModel().setField(cellInfoBeanList.get(i).getTargetRow(), cellInfoBeanList.get(i).getAttributeName(), cellInfoBeanList.get(i).getDefaultValue());
		}
	}

	
	/**
	 * �J�������\������Ă��邩�̔���
	 * 
	 * @param attributeName		�Ώۂ̃J������
	 * @return					�\�����Ă���Ftrue�@��\���Ffalse
	 */
	private boolean isColumnVisible(String attributeName) {
		boolean isResult = true;
		try {
			grid.getTable().getGrid().getColumn(ClientSettings.getInstance().getResources().getResource(attributeName));
		} catch (IllegalArgumentException iex) {
			isResult = false;
		}
		return isResult;
	}

	/**
	 * �����̒l��ݒ肵��Bean���쐬����
	 * 
	 * @param attributeName
	 * @param defaultValu
	 * @param oldValue
	 * @param row
	 * 
	 * @return
	 */
	private CellInfoBean createCellInfoBean(String attributeName, String defaultValu, String oldValue, int row) {
		
		CellInfoBean bean = new CellInfoBean();
		bean.setAttributeName(attributeName);
		bean.setDefaultValue(defaultValu);
		bean.setOldValue(oldValue);
		bean.setTargetColumn(getColumnindex(attributeName));
		bean.setTargetRow(row);
		return bean;
	}
	
	/**
	 * ��l�i�j���̏㉺���j�̃J������index���擾����
	 * ���O�̍��ڂ��u��\���v��������A�J�����̈ʒu���u�ړ��v���Ă��Ă��A���ݕ\�����Ă���index���擾����
	 * 
	 * @param attributeName	��l�i�j���̏㉺���j�̃J������
	 * @return
	 */
	private int getColumnindex(String attributeName) {
		
		int modelColumnIndex = (
				"DS_KAGEN".equals(attributeName) ? 5 : 
					"DS_JYOUGEN".equals(attributeName) ? 6 : 
						"JS_KAGEN".equals(attributeName) ? 7 : 8);
		
		int result = grid.getTable().getGrid().convertColumnIndexToView(modelColumnIndex);
//		System.out.println("attributeName:[" + attributeName + "] index:[" + modelColumnIndex + "] result:[" + result + "]");
		return result;
	}
	
	
	/**
	 * �Z���̐F�ƃc�[���`�b�v�ɐݒ肷�镶����ύX����N���X
	 */
	private class MyTableCellRenderer implements TableCellRenderer {
		
		private int targetColumn;
		private List<CellInfoBean> cellInfoBeanList;
		private List<TableCellRenderer> rendererList;
		
		/**
		 * �R���X�g���N�^
		 * 
		 * @param cellInfoBeanList
		 */
		public MyTableCellRenderer(List<CellInfoBean> cellInfoBeanList) {
			
			this.cellInfoBeanList = cellInfoBeanList;
			this.targetColumn = cellInfoBeanList.get(0).getTargetColumn();	//targetColumn�̒l�͓����͂��Ȃ̂ŁA�����ň�x�����Z�b�g
			this.rendererList = new ArrayList<TableCellRenderer>();			//�utable.prepareRenderer()�v���Œ��ڐݒ肷��ƁA�uStackOverflowError�v�ɂȂ�̂ł����Őݒ肵�Ă���
			for (int i = 0; i < grid.getTable().getGrid().getModel().getRowCount(); i++) {
				TableCellRenderer renderer = grid.getTable().getGrid().getCellRenderer(i, targetColumn);
				this.rendererList.add(renderer);
			}
		}
		
		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
//			System.out.println("MyTableCellRenderer targetColumn:[" + targetColumn + "] value:[" + (String)value + "] row:[" + row + "] column:[" + column + "]");
			
			//�Z��
			Component component = null;
			try {
				//�c�[���`�b�v�̕���
				String toolTipText = null;
				afterDefaultToolTipText = null;
				
				for (int i = 0; i < cellInfoBeanList.size(); i++) {
					
					//�s�Ɨ��Index����A�Ώۂ̃Z���𔻒�
					if ((targetColumn == column) && (row == cellInfoBeanList.get(i).getTargetRow())) {
						
						//�Z���̃c�[���`�b�v������ύX
						toolTipText = "\"" + cellInfoBeanList.get(i).getOldValue() + "\"����\"" + ((cellInfoBeanList.get(i).getDefaultValue() != null) ? cellInfoBeanList.get(i).getDefaultValue() : "") + "\"�֕ύX";
						afterDefaultToolTipText = toolTipText;
						
						//�ύX�Ώۂ̃Z�����擾�iprepareRenderer���\�b�h����Ctrl�N���X��getCellTooltip���\�b�h���Ă΂��H�̂ŁA�ʒu�ɒ��Ӂj
						component = table.prepareRenderer(rendererList.get(row), row, column);
						
						//�Z���̐F��ύX
						component.setBackground(Color.PINK);
						
						break;
					}
				}
				
				//�ύX�Ώۂ̃Z�������������ꍇ
				if (component == null) {
					component = table.prepareRenderer(rendererList.get(row), row, column);
				}
//				System.out.println("ToolTipText:[" + toolTipText + "]�@afterDefaultToolTipText:[" + afterDefaultToolTipText + "]");
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			return component;
		}
	}
	
	/**
	 * �Z�����iTableCellRenderer���Ŏg�p�j��ێ�����Bean
	 */
	private class CellInfoBean {
		private int targetRow;
		private int targetColumn;
		private String oldValue;
		private String defaultValue;
		private String attributeName;
		
		public int getTargetRow() {
			return targetRow;
		}
		public void setTargetRow(int targetRow) {
			this.targetRow = targetRow;
		}
		public int getTargetColumn() {
			return targetColumn;
		}
		public void setTargetColumn(int targetColumn) {
			this.targetColumn = targetColumn;
		}
		public String getOldValue() {
			return oldValue;
		}
		public void setOldValue(String oldValue) {
			this.oldValue = oldValue;
		}
		public String getDefaultValue() {
			return defaultValue;
		}
		public void setDefaultValue(String defaultValue) {
			this.defaultValue = defaultValue;
		}
		public String getAttributeName() {
			return attributeName;
		}
		public void setAttributeName(String attributeName) {
			this.attributeName = attributeName;
		}
	}
	// edit n.ohkubo 2015/03/01�@�ǉ��@end
}