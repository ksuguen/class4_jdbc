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
 * ��(�޴�,�޴�����,�޴��߰�,�ֹ���Ȳ)�� ������ ����ڿ��� �����ִ� ȭ��
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
		super("�ȵ��ö� - �ֹ�");

		String[] columnNames = { "��ȣ", "�̹���", "�޴��ڵ�", "�޴�", "����", "����" };
		String[][] data = {};
		getContentPane();

		dtmMenu = new DefaultTableModel(data, columnNames) {
			// ���� ����
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}// isCellEditable
		};

		jtMenu = new JTable(dtmMenu) {
			// �÷��� �̹����� �ֱ� ���� override
			@Override
			public Class<?> getColumnClass(int column) {
				return getValueAt(0, column).getClass();
			}// getColumnClass
		};

		// �� �̵� �Ұ�
		jtMenu.getTableHeader().setReorderingAllowed(false);
		;
		// �÷� ���� ����
		jtMenu.setRowHeight(110);
		// �÷� ���� ����
		jtMenu.getColumnModel().getColumn(0).setPreferredWidth(40);
		jtMenu.getColumnModel().getColumn(1).setPreferredWidth(150);
		jtMenu.getColumnModel().getColumn(2).setPreferredWidth(80);
		jtMenu.getColumnModel().getColumn(3).setPreferredWidth(100);
		jtMenu.getColumnModel().getColumn(4).setPreferredWidth(480);
		jtMenu.getColumnModel().getColumn(5).setPreferredWidth(50);

		JScrollPane jspMenu = new JScrollPane(jtMenu);

		ImageIcon logo = new ImageIcon("C:/dev/workspace/jdbc_prj/src/kr/co/sist/menu/img/main_logo.png");
		JLabel jlLogo = new JLabel(logo);
		jlLogo.setToolTipText("��ٷο� ������ �Ը��� ��Ȯ�� �����ִ�");

		JPanel jpMenu = new JPanel();
		jpMenu.setLayout(new BorderLayout());
		jpMenu.add("North", jlLogo);
		jpMenu.add("Center", jspMenu);
		jpMenu.setBorder(new TitledBorder("�� ���ö��� ������ �ְ��� �޴�"));

		ImageIcon iiInfo = new ImageIcon("C:/dev/workspace/jdbc_prj/src/kr/co/sist/menu/img/donghaFE.gif");
		JLabel lblInfo = new JLabel(iiInfo);

		JPanel jpComInfo = new JPanel();
		jpComInfo.add(lblInfo);
		String msg = "���� �䱸�� �ż��ϰ� ö���ϰ� ������ �� �ִ� \n " + "(��)���ϴ� 2017�� �����Ͽ� ������ �̷� \n"
				+ "�ְ��� ���ö��� �����ϱ� ���� ��(����)����(����) �п��� ��������\n" + "�ּ��� ���Ұ� ������?";
		jpComInfo.add(new JTextArea(msg));

		jbtOrderList = new JButton("�ֹ���ȸ");
		jbtMenuAdd = new JButton("�޴��߰�");

		JPanel jpBtn = new JPanel();
		jpBtn.setLayout(new FlowLayout(FlowLayout.TRAILING));
		jpBtn.add(jbtOrderList);
		jpBtn.add(jbtMenuAdd);

		jtpTab = new JTabbedPane();
		jtpTab.add("�޴�", jpMenu);
		jtpTab.add("�ȵ��ö�����?", jpComInfo);

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

		// ���� �̺�Ʈ
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				dispose();
				// ��� ���α׷� ����
				System.exit(0);
			}// windowClosing
		});// addWindowListener

		// ���� �̺�Ʈ
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
