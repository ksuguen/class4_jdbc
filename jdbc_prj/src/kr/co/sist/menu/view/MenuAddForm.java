package kr.co.sist.menu.view;

import java.awt.Color;
import java.awt.GridLayout;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import kr.co.sist.menu.evt.MenuAddFormEvt;
import kr.co.sist.menu.evt.MenuFormEvt;

/**
 * 
 * @author owner
 */
@SuppressWarnings("serial")
public class MenuAddForm extends JDialog {
	private MenuForm mf;
	private JTextField jtfMenu, jtfPrice;
	private JTextArea jtaMenuInfo;
	private JButton jbtImage, jbtAdd, jbtClose;
	private JLabel jlPreview;

	public MenuAddForm(MenuForm mf, MenuFormEvt mfe) {
		super(mf, "�� ���ö� �޴� �߰�", true);
		this.mf = mf;

		jtfMenu = new JTextField();
		jtfPrice = new JTextField();
		jtaMenuInfo = new JTextArea();
		jbtImage = new JButton("�̹�������");
		jbtAdd = new JButton("�޴��߰�");
		jbtClose = new JButton("�ݱ�");

		 ImageIcon noImage = null; 
		 noImage= new ImageIcon("C:/dev/workspace/jdbc_prj/src/kr/co/sist/menu/img/default.jpg");
		// ���������� �̹��� ��������
		// ImageIcon noImage = null;
		// try {
		// noImage = new ImageIcon(new URL("http://sist.co.kr/default.jpg"));
		// } catch (MalformedURLException e) {
		// // ������ ������ ���� ���ÿ��� �ø���
		// noImage = new
		// ImageIcon("C:/dev/workspace/jdbc_prj/src/kr/co/sist/menu/img/default.jpg");
		// } // catch

		jlPreview = new JLabel(noImage);
		jlPreview.setBorder(new TitledBorder("���ö� �̹���"));

		JPanel panelLbl = new JPanel();
		panelLbl.setLayout(new GridLayout(3, 1));
		panelLbl.add(new JLabel("��ǰ�� : "));
		panelLbl.add(new JLabel("�� �� : "));
		panelLbl.add(new JLabel("�� �� : "));

		JPanel panelTxt = new JPanel();
		panelTxt.setLayout(new GridLayout(2, 1));
		panelTxt.add(jtfMenu);
		panelTxt.add(jtfPrice);

		jtfMenu.setToolTipText("���ö��� �̸��� �Է��մϴ�.");
		jtfPrice.setToolTipText("���ö��� ������ �Է��մϴ�.");

		// �׵θ� �ֱ�
		// jtaMenuInfo.setBorder(new LineBorder(Color.lightGray));
		
		jtaMenuInfo.setWrapStyleWord(true);
		jtaMenuInfo.setLineWrap(true);
		JScrollPane jspMenuInfo = new JScrollPane(jtaMenuInfo);

		JPanel panelBtn = new JPanel();
		panelBtn.add(jbtAdd);
		panelBtn.add(jbtClose);
		
		panelBtn.setBounds(323, 160, 200, 40);
		jspMenuInfo.setBounds(350, 90, 150, 60);
		panelTxt.setBounds(350, 25, 150, 60);
		panelLbl.setBounds(260, 25, 80, 90);
		jlPreview.setBounds(10, 20, 244, 220);
		jbtImage.setBounds(60, 250, 120, 30);
		
		add(jbtImage);
		add(jlPreview);
		add(panelBtn);
		add(panelLbl);
		add(panelTxt);
		add(jspMenuInfo);
		
		setLayout(null);
		setBounds(mf.getX() + 100, mf.getY() + 100, 530, 400);

		MenuAddFormEvt mafe = new MenuAddFormEvt(this,mfe);
		addWindowListener(mafe);
		jbtImage.addActionListener(mafe);
		jbtAdd.addActionListener(mafe);
		jbtClose.addActionListener(mafe);
		
		setVisible(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

	}// MenuAddForm

	public MenuForm getMf() {
		return mf;
	}

	public JTextField getJtfMenu() {
		return jtfMenu;
	}

	public JTextField getJtfPrice() {
		return jtfPrice;
	}

	public JTextArea getJtaMenuInfo() {
		return jtaMenuInfo;
	}

	public JButton getJbtImage() {
		return jbtImage;
	}

	public JButton getJbtAdd() {
		return jbtAdd;
	}

	public JButton getJbtClose() {
		return jbtClose;
	}

	public JLabel getJlPreview() {
		return jlPreview;
	}

	

}// class
