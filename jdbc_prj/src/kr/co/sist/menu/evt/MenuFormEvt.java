package kr.co.sist.menu.evt;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;

import javax.security.auth.login.LoginException;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import kr.co.sist.menu.dao.MenuDAO;
import kr.co.sist.menu.view.LoginForm;
import kr.co.sist.menu.view.MenuAddForm;
import kr.co.sist.menu.view.MenuForm;
import kr.co.sist.menu.view.OrderForm;
import kr.co.sist.menu.view.OrderViewForm;
import kr.co.sist.menu.vo.MenuVO;

/**
 * 
 * @author owner
 */
public class MenuFormEvt extends MouseAdapter implements ActionListener {
	private MenuForm mf;
	private MenuDAO m_dao;
	private OrderViewForm ovf;
	private MenuAddForm maf;
	public static boolean ORDER_SELECT;
	
	public MenuFormEvt(MenuForm mf) {
		this.mf = mf;

		// ��ü ���
		this.m_dao = MenuDAO.getInstance();
		// �޴��� ��ȸ�Ͽ� �����Ѵ�
		setMenu();

	}// MenuFormEvt

	public void setMenu() {
		try {
			List<MenuVO> lstMenu = m_dao.selectMenuList();

			// ��ȣ, �̹���, �޴��ڵ�,�޴�,����,����
			Object[] rowMenu = new Object[6];
			DefaultTableModel dtmMenu = mf.getDtmMenu();
			MenuVO mv = null;

			// �ʱ�ȭ
			// �߰��� �����Ͱ� setRowCount�� �ʱ�ȭ�ǰ�
			// �߰��� �����Ͱ� ������ �ȵǴ� ������ �߻��ϸ�
			// setNumRows(���) method �� ����Ͽ�
			// �����͸� �߰��ϸ� ������ �ذ� �� �� �ִ�.
			dtmMenu.setRowCount(0);
			
			for (int i = 0; i < lstMenu.size(); i++) {
				mv = lstMenu.get(i);
				rowMenu[0] = i + 1;
				rowMenu[1] = new ImageIcon("C:/dev/workspace/jdbc_prj/src/kr/co/sist/menu/img/s_" + mv.getImg());
				rowMenu[2] = mv.getItem_code();
				rowMenu[3] = mv.getMenu();
				rowMenu[4] = mv.getInfo();
				rowMenu[5] = mv.getPrice();

				dtmMenu.addRow(rowMenu);
			} // end for

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(mf, "�˼��մϴ�. �޴��� �ҷ��� �� �����ϴ�. ����� �ٽ� �õ���");
			e.printStackTrace();
		}
	}// setMenu

	@Override
	public void mouseClicked(MouseEvent me) {
		if (me.getClickCount() == 2) {
			// ���ö� �ֹ��� 1�� ������ ����
			Calendar cal = Calendar.getInstance();
			if(cal.get(Calendar.HOUR_OF_DAY)>12){
				JOptionPane.showMessageDialog(mf, "���ö� �ֹ��� 13�� ������ ����");
				return;
			}// end if
			
			JTable temp = mf.getJtMenu();

			MenuVO mv = new MenuVO();
			int selectedRow = temp.getSelectedRow();
			mv.setImg(((ImageIcon) temp.getValueAt(selectedRow, 1)).toString());
			mv.setItem_code((String) temp.getValueAt(selectedRow, 2));
			mv.setMenu((String) temp.getValueAt(selectedRow, 3));
			mv.setInfo((String) temp.getValueAt(selectedRow, 4));
			mv.setPrice((Integer) temp.getValueAt(selectedRow, 5));

			int flag = JOptionPane.showConfirmDialog(mf, mv.getMenu() + "�� �ֹ��Ͻðڽ��ϱ�?");
			switch (flag) {
			case JOptionPane.OK_OPTION:
				
				new OrderForm(mf, mv);
				break;
			}// end switch
		} // end if
	}// mouseClicked

	@Override
	public void actionPerformed(ActionEvent ae) {
		LoginForm lf = new LoginForm(mf); // loginForm Dialog ������ ȣ��
		try {
			if (lf.getFlag()) { // true
				JOptionPane.showMessageDialog(mf, "�α��� ����");
				
				if (ae.getSource() == mf.getJbtMenuAdd()) {
					if (maf == null) {
						maf = new MenuAddForm(mf,this);
					}else{
						maf.setVisible(true);
					}// end if
				} // end if
				
				if (ae.getSource() == mf.getJbtOrderList()) {
					if (ovf == null) {
						ovf = new OrderViewForm(mf);
					}else{
						ORDER_SELECT=true;
						ovf.setVisible(true);
					}// end if
				} // end if
				
			} // end if
		} catch (LoginException le) {
			JOptionPane.showMessageDialog(mf, "���̵�� ��� Ȯ���ϼ���");
		} // catch
	}// actionPerformed

}// class
