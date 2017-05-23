package kr.co.sist.menu.evt;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import kr.co.sist.menu.dao.MenuDAO;
import kr.co.sist.menu.view.OrderForm;
import kr.co.sist.menu.vo.OrderVO;

/**
 *
 * @author owner
 */
public class OrderFormEvt extends WindowAdapter implements ActionListener {

	private OrderForm of;
	
	public OrderFormEvt(OrderForm of) {
		this.of = of;
	}// OrderFormEvt

	private void setOrder() {
		String name = of.getJtfName().getText();
		
		if(name.equals("")){
			JOptionPane.showMessageDialog(of, "이름을 입력해주세요");
			return;
		}// end if
		
		if(name.length()>10){
			JOptionPane.showMessageDialog(of, "이름이 너무 길어요ㅠ 10자 이하 플리즈");
			of.getJtfName().setText("");
			of.getJtfName().requestFocus();
			return;
		}// end if
		
		String item_code = of.getJtfItemCode().getText();
		String menu = of.getJtfMenu().getText();
		String ipaddr = "";
		try {
			ipaddr = InetAddress.getLocalHost().toString();
		} catch (UnknownHostException uhe) {
			uhe.printStackTrace();
		} // catch
		Integer quan = (Integer) of.getJcbQuan().getSelectedItem();
		String price = of.getJtfPrice().getText();

		StringBuilder order = new StringBuilder();
		order.append(name).append("님의 주문 사항\n")
		.append("ip : ").append(ipaddr).append("\n")
		.append("메뉴 : ").append(menu).append("(코드 : ").append(item_code).append(")\n")
		.append("수량 : ").append(quan).append("\n")
		.append("가격 : ").append(price).append("\n")
		.append("총가격 : ").append(Integer.parseInt(price) * quan).append("\n")
		.append("주문 하시겠습니까?");

		int flag = JOptionPane.showConfirmDialog(of, order.toString());
		
		switch (flag) {
		case JOptionPane.OK_OPTION:
			MenuDAO md = MenuDAO.getInstance();
			OrderVO ov = new OrderVO(item_code, name, ipaddr, quan);
			
			try {
				md.insertOrder(ov);
				ImageIcon iiDongha = new ImageIcon("C:/dev/workspace/jdbc_prj/src/kr/co/sist/menu/img/donghaFE.gif");
				JLabel lblfinal = new JLabel(iiDongha);
				JPanel jp = new JPanel();
				jp.setLayout(new BorderLayout());
				jp.add("North", new JLabel("주문이 성공입니다"));
				jp.add("Center", lblfinal);
				JOptionPane.showMessageDialog(of, jp);
				// 부모창 닫아주기
				of.dispose();
			} catch (SQLException e) {
				e.printStackTrace();
			}// catch
			
			break;
		}// switch
		
	}// setOrder

	@Override
	public void actionPerformed(ActionEvent ae) {

		if (ae.getSource() == of.getJbtOrder()) {
			setOrder();
		} // end if

		if (ae.getSource() == of.getJbtClose()) {
			int selectNum = JOptionPane.showConfirmDialog(of, "주문창을 닫으시겠어요?");
			switch (selectNum) {
			case JOptionPane.OK_OPTION:
				of.dispose();
				break;
			}// switch
		} // end if

	}// actionPerformed

}// class
