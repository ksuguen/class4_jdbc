package kr.co.sist.menu.view;

import java.awt.GridLayout;
import java.io.File;

import javax.swing.DefaultComboBoxModel;
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
import javax.swing.border.TitledBorder;

import kr.co.sist.menu.evt.OrderFormEvt;
import kr.co.sist.menu.vo.MenuVO;

/**
 * 메뉴에대한 세부항목(큰이미지,메뉴코드,메뉴이름,메뉴설명,가격 등),<br>
 * 주문사항(수량, 총 가격, 주문자)을 입력하는 form(화면)
 * @author user
 */
@SuppressWarnings("serial")
public class OrderForm extends JDialog {

	private JTextField jtfItemCode, jtfMenu, jtfPrice, jtfName;
	private JComboBox<Integer> jcbQuan;
	private JButton jbtOrder, jbtClose;

	public OrderForm(JFrame jf, MenuVO mv) {// MenuVO는 어떤도시락을 넣었는지 알게해줌.
		// 주문시스템 창에서 더블클릭후 예를 누르면
		// 도시락 주문창의 자식창이 생기면서 도시락 주문 창이 나옴
		super(jf, "도시락 주문창", true); // 자식창이 떠있으면 부모창이 눌릴수 없게 모달로 해줌.

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder("상세주문")); // 타이틀바설정
		panel.setLayout(null);// 수동배치를 하기위하여 널을사용
		
		// 객체화
		jtfItemCode = new JTextField();
		jtfMenu = new JTextField();
		jtfPrice = new JTextField();
		jtfName = new JTextField(10);

		// 메뉴코드,메뉴명,가격은 읽기전용(수정불가)
		jtfItemCode.setEditable(false);
		jtfMenu.setEditable(false);
		jtfPrice.setEditable(false);
		
		// 도시락을 1~10개까지 시킬 수있음
		Integer[] num = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 }; 
		
		// 2차원배열을 넣어주기 위하여 사용
		DefaultComboBoxModel<Integer> dcmb = new DefaultComboBoxModel<Integer>(num);
		
		// 얘는 콤보박스로 수량을 1~10중에 하나선택가능하게 만들어줌.
		jcbQuan = new JComboBox<Integer>(dcmb);
		jbtOrder = new JButton("주문");
		jbtClose = new JButton("닫기");

		File file = new File(mv.getImg());// 이미지가 포함되어 있는 모든경로
		// getParent부모의 경로를 불러주고 getName은 파일의 이름만 불러온다
		// 경로에 /를 붙이고 그뒤에 2글자를 짜른 파일이름을 불러준다.
		ImageIcon ii = new ImageIcon(file.getParent() + "/" + file.getName().substring(2));
		JLabel jlimg = new JLabel(ii); // 라벨에 이미지 넣어줌
		jlimg.setToolTipText("상기 이미지는 견본이므로 실제 제품과는 차이가 있을수도 있습니다.");

		JPanel jpLbl = new JPanel();
		// 상품이미지 옆에 붙음
		jpLbl.setLayout(new GridLayout(4, 1));
		jpLbl.add(new JLabel("도시락명  : "));
		jpLbl.add(new JLabel("제품코드  : "));
		jpLbl.add(new JLabel("제품가격  : "));
		jpLbl.add(new JLabel("제품설명  : "));

		JPanel jtTxt = new JPanel();
		jtTxt.setLayout(new GridLayout(4, 1));

		// j라벨옆에 도시락명,코드,가격,명들을 적어서 내보내줌.
		jtTxt.add(jtfMenu);
		jtTxt.add(jtfItemCode);
		jtTxt.add(jtfPrice);

		// 입력한 값을 받아옴.
		jtfMenu.setText(mv.getMenu());
		jtfItemCode.setText(mv.getItem_code());
		jtfPrice.setText(String.valueOf(mv.getPrice()));

		JTextArea jtfInfo = new JTextArea(mv.getInfo());
		jtfInfo.setEditable(false); // 수정불가
		jtfInfo.setLineWrap(true);
		jtfInfo.setWrapStyleWord(true);
		JScrollPane jspInfo = new JScrollPane(jtfInfo);

		ImageIcon iiAd = new ImageIcon("C:/dev/workspace/jdbc_prj/src/kr/co/sist/menu/img/ad.gif");
		JLabel jlAd = new JLabel(iiAd);
		
		JPanel jpOrder= new JPanel();
		jpOrder.setBorder(new TitledBorder("주문정보"));
		jpOrder.add(new JLabel("주문자"));
		jpOrder.add(jtfName);
		jpOrder.add(new JLabel("수량 : "));
		jpOrder.add(jcbQuan);
		
		JPanel jpBtn= new JPanel();
		jpBtn.add(jbtOrder);
		jpBtn.add(jbtClose);
		
		jpOrder.setBounds(280, 250, 300, 90);
		jpBtn.setBounds(360, 360, 300, 40);
		jpLbl.setBounds(280, 20, 80, 120);
		jlimg.setBounds(10, 20, 244, 220);
		jtTxt.setBounds(370, 20, 210, 120);
		jspInfo.setBounds(280, 140, 302, 100);
		jlAd.setBounds(11, 250, 200, 90);

		panel.add(jlAd);
		panel.add(jpLbl);
		panel.add(jlimg);
		panel.add(jtTxt);
		panel.add(jspInfo);
		panel.add(jpOrder);
		panel.add(jpBtn);
		
		OrderFormEvt ofe = new OrderFormEvt(this);
		jbtOrder.addActionListener(ofe);
		jbtClose.addActionListener(ofe);
		
		add("Center", panel);
		// pack(); // 윈도우안에 들어가 있는 컴포넌트 크기에 맞게 크기를 자동조절 해줌 (자동배치에서만 사용가능)

		setResizable(false);// 도시락 창의 크기 변경불가능
		// 부모창안에서 자식창이 뜨게끔 크기 설정
		setBounds(jf.getX() + 160, jf.getY() + 120, 600, 440);
		setVisible(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

	}// 인자있는 생성자

	public JTextField getJtfItemCode() {
		return jtfItemCode;
	}

	public JTextField getJtfMenu() {
		return jtfMenu;
	}

	public JTextField getJtfPrice() {
		return jtfPrice;
	}

	public JTextField getJtfName() {
		return jtfName;
	}

	public JComboBox<Integer> getJcbQuan() {
		return jcbQuan;
	}

	public JButton getJbtOrder() {
		return jbtOrder;
	}

	public JButton getJbtClose() {
		return jbtClose;
	}
	
}// class 