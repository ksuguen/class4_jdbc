package kr.co.sist.menu.evt;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import kr.co.sist.menu.view.LoginForm;

public class LoginFormEvt implements ActionListener {
	private LoginForm lf;

	public LoginFormEvt(LoginForm lf) {
		this.lf = lf;
	}// LoginFormEvt

	private void chkNull(){
		String id = lf.getJtfId().getText().trim();
		String pass = new String(lf.getJtfPass().getPassword()).trim();
		
		if(id.equals("") || pass.equals("")){
			JOptionPane.showMessageDialog(lf, "���̵�� ��й�ȣ�� �ݵ�� �Է����ּ���");
			return;
		}//end if
		
		boolean flag = false;
		// ������ id : root // pw: tiger
		if(id.equals("root")||pass.equals("tiger")){
			flag = true;
		}// end if
		lf.setFlag(flag,1); // �α��� ��� ����
		lf.dispose(); //�α��� â���ݾ� �������� �����ϵ��� ���� 
	}// chkNull
	
	@Override
	public void actionPerformed(ActionEvent ae) {
		if (ae.getSource() == lf.getJbtClose()) { // �ݱ� ��ư
			lf.dispose();
		} // end if
		if (ae.getSource() == lf.getJbtLogin()) { // �α��� ��ư
			chkNull();
		} // end if
		if (ae.getSource() == lf.getJtfPass()) { // 
			chkNull();
		} // end if
	}//actionPerformed
	
}
