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
				sbResult.append("주문결과 : ").append(list.size()).append("건 총 금액 : ");

				int total = 0;
				Object[] data = new Object[8];
				OrderAllVO oav = null;
				dtm.setRowCount(0); // 테이블 값 초기화
				for (int i = 0; i < list.size(); i++) {
					oav = list.get(i);
					// 총 주문 금액
					total += oav.getTotalPrice();
					data[0] = i + 1;
					data[1] = oav.getOrder_num();
					data[2] = oav.getMenu();
					data[3] = oav.getItem_code();
					data[4] = oav.getName();
					data[5] = oav.getQuan();
					data[6] = oav.getTotalPrice();
					data[7] = oav.getOrderDate();

					// menuDAO를 통해서 담아놓은
					// 인스턴스변수에 할당된 값들을
					// getter를 사용해서 값을 불러옴
					// 불러온 값을 datatablemodel에 할당
					dtm.addRow(data);

				} // end for
				sbResult.append(total).append("원");

				tempResult.setText(sbResult.toString());

				if (list.isEmpty()) {
					String[] emptyData = { "비어잇지 ㅋ" };
					dtm.addRow(emptyData);
					JOptionPane.showMessageDialog(ovf, "도시락 주문이 없습니다");
				} // end if

			} catch (SQLException e) {
				JOptionPane.showMessageDialog(ovf, "서버문제 발생ㄴ");
				e.printStackTrace();
			} // catch
		}// end if
	}// setOrder

	@Override
	public void windowClosing(WindowEvent e) {
		MenuFormEvt.ORDER_SELECT = false;
		ovf.setVisible(false);
	}// windowClosing

	// 갱신되는 목록을 위해서
	// 쓰레드 사용.
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
