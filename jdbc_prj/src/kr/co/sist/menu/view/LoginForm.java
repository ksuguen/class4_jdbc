package kr.co.sist.menu.view;

import java.awt.Color;
import java.awt.GridLayout;

import javax.security.auth.login.LoginException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import kr.co.sist.menu.evt.LoginFormEvt;

public class LoginForm extends JDialog {
	private JTextField jtfId;
	private JPasswordField jtfPass;
	private JButton jbtLogin, jbtClose;
	private MenuForm mf;
	private LoginFormEvt lfe;

	public LoginForm(MenuForm mf) {
		super(mf, "안 도시락 관리자 로그인", true);
		this.mf = mf;

		JPanel panelLogin = new JPanel();
		panelLogin.setLayout(new GridLayout(3, 1));
		panelLogin.setBorder(new TitledBorder("로그인"));
		jtfId = new JTextField("root");
		jtfId.setBorder(new TitledBorder("아이디"));
		jtfPass = new JPasswordField("tiger");
		jtfPass.setBorder(new TitledBorder("비번"));
		JPanel panelBtn = new JPanel();
		jbtLogin = new JButton("로그인");
		jbtClose = new JButton("닫기");

		panelBtn.add(jbtLogin);
		panelBtn.add(jbtClose);

		panelLogin.add(jtfId);
		panelLogin.add(jtfPass);
		panelLogin.add(panelBtn);

		setLayout(null);
		ImageIcon icon = new ImageIcon("C:/dev/workspace/jdbc_prj/src/kr/co/sist/menu/img/login_bg.jpg");
		JLabel backgroundImg = new JLabel(icon);

		backgroundImg.setBounds(0, 0, 500, 441);
		panelLogin.setBounds(300, 200, 200, 220);
		panelLogin.setBackground(Color.WHITE);
		panelBtn.setBackground(Color.WHITE);
		setBounds(mf.getX() + 100, mf.getY() + 100, 500, 441);

		add(panelLogin);
		add(backgroundImg);

		lfe = new LoginFormEvt(this);
		jbtLogin.addActionListener(lfe);
		jbtClose.addActionListener(lfe);

		setVisible(true);
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}// LoginForm

	public JTextField getJtfId() {
		return jtfId;
	}

	public JPasswordField getJtfPass() {
		return jtfPass;
	}

	public JButton getJbtLogin() {
		return jbtLogin;
	}

	public JButton getJbtClose() {
		return jbtClose;
	}

	public MenuForm getMf() {
		return mf;
	}

	private boolean flag;
	private int cnt;

	// loginFormEvt에서 로그인 결과 설정
	public void setFlag(boolean flag, int cnt) {
		this.cnt = cnt;
		this.flag = flag;
	}// logResult

	// MenuFormEvt에서 로그인 결과를 얻어감
	public boolean getFlag() throws LoginException {
		// 로그인을 수행하였으나 아이디나 비밀번호가 틀려서 
		// 로그인 exception 강제 발생
		if(cnt == 1&&!flag){
			throw new LoginException();
		}// end if
		
		// 로그인을 수행하지 않고 닫기를 클릭하면 
		// 무조건 false가 나감
		return flag;
	}// logResult

}// class
