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
			JOptionPane.showMessageDialog(lf, "아이디와 비밀번호는 반드시 입력해주세요");
			return;
		}//end if
		
		boolean flag = false;
		// 관리자 id : root // pw: tiger
		if(id.equals("root")||pass.equals("tiger")){
			flag = true;
		}// end if
		lf.setFlag(flag,1); // 로그인 결과 설정
		lf.dispose(); //로그인 창을닫아 다음으로 진행하도록 만듬 
	}// chkNull
	
	@Override
	public void actionPerformed(ActionEvent ae) {
		if (ae.getSource() == lf.getJbtClose()) { // 닫기 버튼
			lf.dispose();
		} // end if
		if (ae.getSource() == lf.getJbtLogin()) { // 로그인 버튼
			chkNull();
		} // end if
		if (ae.getSource() == lf.getJtfPass()) { // 
			chkNull();
		} // end if
	}//actionPerformed
	
}
