package kr.co.sist.menu.evt;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import kr.co.sist.menu.dao.MenuDAO;
import kr.co.sist.menu.view.OrderViewForm;
import kr.co.sist.menu.vo.OrderAllVO;

public class OrderViewFormEvt extends WindowAdapter implements ActionListener, Runnable {

	private OrderViewForm ovf;

	public OrderViewFormEvt(OrderViewForm ovf) {
		this.ovf = ovf;
		setOrder();
	}// OrderViewFormEvt

	public void setOrder() {
		if (MenuFormEvt.ORDER_SELECT) {
			MenuDAO md = MenuDAO.getInstance();
			try {
				List<OrderAllVO> list = md.selectOrder();
				DefaultTableModel dtm = ovf.getDtmOrder();
				JLabel tempResult = ovf.getOrderResult();
				StringBuilder sbResult = new StringBuilder();
				sbResult.append("�ֹ���� : ").append(list.size()).append("�� �� �ݾ� : ");

				int total = 0;
				Object[] data = new Object[8];
				OrderAllVO oav = null;
				dtm.setRowCount(0); // ���̺� �� �ʱ�ȭ
				for (int i = 0; i < list.size(); i++) {
					oav = list.get(i);
					// �� �ֹ� �ݾ�
					total += oav.getTotalPrice();
					data[0] = i + 1;
					data[1] = oav.getOrder_num();
					data[2] = oav.getMenu();
					data[3] = oav.getItem_code();
					data[4] = oav.getName();
					data[5] = oav.getQuan();
					data[6] = oav.getTotalPrice();
					data[7] = oav.getOrderDate();

					// menuDAO�� ���ؼ� ��Ƴ���
					// �ν��Ͻ������� �Ҵ�� ������
					// getter�� ����ؼ� ���� �ҷ���
					// �ҷ��� ���� datatablemodel�� �Ҵ�
					dtm.addRow(data);

				} // end for
				sbResult.append(total).append("��");

				tempResult.setText(sbResult.toString());

				if (list.isEmpty()) {
					String[] emptyData = { "������� ��" };
					dtm.addRow(emptyData);
					JOptionPane.showMessageDialog(ovf, "���ö� �ֹ��� �����ϴ�");
				} // end if

			} catch (SQLException e) {
				JOptionPane.showMessageDialog(ovf, "�������� �߻���");
				e.printStackTrace();
			} // catch
		}// end if
	}// setOrder

	@Override
	public void windowClosing(WindowEvent e) {
		MenuFormEvt.ORDER_SELECT = false;
		ovf.setVisible(false);
	}// windowClosing

	// ���ŵǴ� ����� ���ؼ�
	// ������ ���.
	@Override
	public void run() {

		while (true) {
			setOrder();
			try {
				Thread.sleep(1000 * 10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			} // catch
		} // end while
	}// run

	@Override
	public void actionPerformed(ActionEvent ae) {
		MenuFormEvt.ORDER_SELECT = false;
		ovf.setVisible(false);
	}// actionPerformed

}// class
