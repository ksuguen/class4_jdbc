package kr.co.sist.menu.view;

import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import kr.co.sist.menu.evt.MenuFormEvt;
import kr.co.sist.menu.evt.OrderViewFormEvt;

/**
 * ����Ǵ� ��¥�� 12�ñ����� �ֹ���Ȳ�� 30�ʴ����� �����Ͽ� �����ִ� ȭ��
 * 
 * @author owner
 */
@SuppressWarnings("serial")
public class OrderViewForm extends JDialog {
	private MenuForm mf;
	private JTable jtOrder;
	private DefaultTableModel dtmOrder;
	private JButton btnClose;
	private JLabel orderResult;
	
	public OrderViewForm(MenuForm mf) {
		super(mf, "������ �ֹ� ����Ʈ", false);
		this.mf = mf;
		
		String[] columnNames = {"��ȣ","�ֹ���ȣ","���ö���","���ö��ڵ�","�ֹ���","����","����","�ֹ��Ͻ�"};
		String[][] data ={};
		
		dtmOrder = new DefaultTableModel(data,columnNames){
			// ��������
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}//isCellEditable
		};
		
		jtOrder = new JTable(dtmOrder);
		btnClose = new JButton("�ݱ�");
		orderResult = new JLabel("�ֹ� ���");
		
		
		JScrollPane jspOrder = new JScrollPane(jtOrder);
		jspOrder.setBorder(new TitledBorder("�ֹ� ��Ȳ"));
		
		JPanel jpBtn = new JPanel();
		jpBtn.setLayout(new FlowLayout( FlowLayout.TRAILING));
		
		jpBtn.add(orderResult);
		jpBtn.add(btnClose);
		
		// �÷����� �����Ͽ� �������� ���ϵ��� ����
		jtOrder.getTableHeader().setReorderingAllowed(false);
		// �÷� ũ�� ����
		jtOrder.getColumnModel().getColumn(0).setPreferredWidth(40);
		jtOrder.getColumnModel().getColumn(1).setPreferredWidth(60);
		jtOrder.getColumnModel().getColumn(2).setPreferredWidth(180);
		jtOrder.getColumnModel().getColumn(3).setPreferredWidth(100);
		jtOrder.getColumnModel().getColumn(4).setPreferredWidth(80);
		jtOrder.getColumnModel().getColumn(5).setPreferredWidth(40);
		jtOrder.getColumnModel().getColumn(6).setPreferredWidth(100);
		jtOrder.getColumnModel().getColumn(7).setPreferredWidth(100);
		
		add("Center",jspOrder);
		add("South",jpBtn);
		
		OrderViewFormEvt ovfe = new OrderViewFormEvt(this);
		
		MenuFormEvt.ORDER_SELECT = true;

		// ������ �����Ͽ� �ֹ� ��� ����
		Thread threadOrder = new Thread(ovfe);
		threadOrder.start();
		
		addWindowListener(ovfe);
		btnClose.addActionListener(ovfe);
		
		setBounds(mf.getX(), mf.getY(), 700, 400);

		
		setVisible(true);
		setResizable(false);

		// ���� �̺�Ʈ ó��
		// setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

	}// OrderViewForm


	public JLabel getOrderResult() {
		return orderResult;
	}


	public JTable getJtOrder() {
		return jtOrder;
	}

	public DefaultTableModel getDtmOrder() {
		return dtmOrder;
	}

}// class
