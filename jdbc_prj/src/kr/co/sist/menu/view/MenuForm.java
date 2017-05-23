package kr.co.sist.menu.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import kr.co.sist.menu.evt.MenuFormEvt;

/**
 * 탭(메뉴,메뉴선택,메뉴추가,주문현황)을 가지고 사용자에게 보여주는 화면
 * 
 * @author owner
 */
@SuppressWarnings("serial")
public class MenuForm extends JFrame {

	private JTable jtMenu;
	private DefaultTableModel dtmMenu;
	private JTabbedPane jtpTab;
	private JButton jbtOrderList;
	private JButton jbtMenuAdd;

	public MenuForm() {
		super("안도시락 - 주문");

		String[] columnNames = { "번호", "이미지", "메뉴코드", "메뉴", "설명", "가격" };
		String[][] data = {};
		getContentPane();

		dtmMenu = new DefaultTableModel(data, columnNames) {
			// 편집 막기
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}// isCellEditable
		};

		jtMenu = new JTable(dtmMenu) {
			// 컬럼에 이미지를 넣기 위한 override
			@Override
			public Class<?> getColumnClass(int column) {
				return getValueAt(0, column).getClass();
			}// getColumnClass
		};

		// 셀 이동 불가
		jtMenu.getTableHeader().setReorderingAllowed(false);
		;
		// 컬럼 높이 설정
		jtMenu.setRowHeight(110);
		// 컬럼 넓이 설정
		jtMenu.getColumnModel().getColumn(0).setPreferredWidth(40);
		jtMenu.getColumnModel().getColumn(1).setPreferredWidth(150);
		jtMenu.getColumnModel().getColumn(2).setPreferredWidth(80);
		jtMenu.getColumnModel().getColumn(3).setPreferredWidth(100);
		jtMenu.getColumnModel().getColumn(4).setPreferredWidth(480);
		jtMenu.getColumnModel().getColumn(5).setPreferredWidth(50);

		JScrollPane jspMenu = new JScrollPane(jtMenu);

		ImageIcon logo = new ImageIcon("C:/dev/workspace/jdbc_prj/src/kr/co/sist/menu/img/main_logo.png");
		JLabel jlLogo = new JLabel(logo);
		jlLogo.setToolTipText("까다로운 고객님의 입맞을 정확히 맞춰주는");

		JPanel jpMenu = new JPanel();
		jpMenu.setLayout(new BorderLayout());
		jpMenu.add("North", jlLogo);
		jpMenu.add("Center", jspMenu);
		jpMenu.setBorder(new TitledBorder("안 도시락이 엄선한 최고의 메뉴"));

		ImageIcon iiInfo = new ImageIcon("C:/dev/workspace/jdbc_prj/src/kr/co/sist/menu/img/donghaFE.gif");
		JLabel lblInfo = new JLabel(iiInfo);

		JPanel jpComInfo = new JPanel();
		jpComInfo.add(lblInfo);
		String msg = "고객의 요구에 신속하고 철저하게 응답할 수 있는 \n " + "(주)동하는 2017년 급조하여 설립된 이래 \n"
				+ "최고의 도시락을 제공하기 위해 임(동하)직원(기준) 분열된 마음으로\n" + "최선을 다할것 같으냐?";
		jpComInfo.add(new JTextArea(msg));

		jbtOrderList = new JButton("주문조회");
		jbtMenuAdd = new JButton("메뉴추가");

		JPanel jpBtn = new JPanel();
		jpBtn.setLayout(new FlowLayout(FlowLayout.TRAILING));
		jpBtn.add(jbtOrderList);
		jpBtn.add(jbtMenuAdd);

		jtpTab = new JTabbedPane();
		jtpTab.add("메뉴", jpMenu);
		jtpTab.add("안도시락은요?", jpComInfo);

		add("North", jpBtn);
		add("Center", jtpTab);

		// jtpTab.setOpaque(true);
		jpMenu.setBackground(Color.WHITE);

		MenuFormEvt mfe = new MenuFormEvt(this);
		jtMenu.addMouseListener(mfe);
		jbtMenuAdd.addActionListener(mfe);
		jbtOrderList.addActionListener(mfe);

		setBounds(10, 10, 900, 600);
		setVisible(true);

		// 종료 이벤트
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				dispose();
				// 모든 프로그램 종료
				System.exit(0);
			}// windowClosing
		});// addWindowListener

		// 종료 이벤트
		// setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}// menuForm

	public JButton getJbtOrderList() {
		return jbtOrderList;
	}

	public JButton getJbtMenuAdd() {
		return jbtMenuAdd;
	}

	public JTable getJtMenu() {
		return jtMenu;
	}// getJtMenu

	public DefaultTableModel getDtmMenu() {
		return dtmMenu;
	}// getDtmMenu

}// class
