package kr.co.sist.menu.evt;

import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import kr.co.sist.menu.dao.MenuDAO;
import kr.co.sist.menu.view.MenuAddForm;
import kr.co.sist.menu.vo.MenuVO;

public class MenuAddFormEvt extends WindowAdapter implements ActionListener {

	private MenuAddForm maf;
	private MenuFormEvt mfe;

	public MenuAddFormEvt(MenuAddForm maf , MenuFormEvt mfe) {
		this.maf = maf;
		this.mfe = mfe;
	}// end MenuAddFormEvt

	public void setImg() {
		FileDialog fdImage = new FileDialog(maf, "도시락 이미지 선택", FileDialog.LOAD);
		fdImage.setVisible(true);

		String path = fdImage.getDirectory();
		String file = fdImage.getFile();

		if (file != null) {
			String validFile = "jpg,gif,png,bmp,psd";
			if (!validFile.contains(file.substring(file.lastIndexOf(".") + 1))) {
				JOptionPane.showMessageDialog(maf, "선택한것 불가능. 이미지 파일만 가능함");
				return;
			} // end if

			ImageIcon temp = new ImageIcon(path + file);
			maf.getJlPreview().setIcon(temp);

		} // end if

	}// setImg

	private void addMenu() {
		// 이미지는 C:/dev/workspace/jdbc_prj/src/kr/co/sist/menu/img/에 복사
		// 이미지의 경로
		// 메뉴이름, 가격, 설명

		// 아이콘 경로 가져오기
		ImageIcon icon = (ImageIcon) maf.getJlPreview().getIcon();
		File file = new File(icon.toString());
		String tempFile = file.getName();
		if (tempFile.equals("default.jpg")) {
			// 기본이미지라면 등록하지 않음
			JOptionPane.showMessageDialog(maf, "기본이미지는 사용할 수 없습니다");
			return;
		} // end if
		
		File sFile = new File(file.getParent() + "/s_" + tempFile);
		// 파일앞에 s_가 붙는 파일이 없다면
		if (!sFile.exists()) {
			JOptionPane.showMessageDialog(maf, "메뉴 선택시 추가되는 파일인 s_" + tempFile + "이 필요합니다");
			return;
		} // end if

		// 파일을 사용위치에 복붙.
		// 선택한 위치가 파일을 보여주는 위치가 아니라면
		// 복붙을 시도한다.
		if (!file.getParent().equals("C:\\dev\\workspace\\jdbc_prj\\src\\kr\\co\\sist\\menu\\img")) {
			try {
				// 원본파일 복.붙
				FileInputStream fis = new FileInputStream(file);
				FileOutputStream fos = new FileOutputStream("C:/dev/workspace/jdbc_prj/src/kr/co/sist/menu/img/"+file.getName());
				
				// 하드디스크가 한번회전에서 읽는 크기만큼
				// 읽어들이도록 해서 속도를 개선한다.
				byte[] temp = new byte[512];
				
				int readData = 0;
				while((readData=fis.read(temp))!=-1 ){
					fos.write(temp,0,readData);
				}//while
				
				// 파일 분출
				fos.flush();
				
				// 연결 끊기
				if(fis!=null){fis.close();}// end if
				if(fos!=null){fos.close();} // end if
				
				// 메뉴 선택파일 복.붙 위해 재연결
				fis = new FileInputStream(file.getParent()+"/s_"+file.getName());
				fos = new FileOutputStream("C:/dev/workspace/jdbc_prj/src/kr/co/sist/menu/img/s_"+file.getName());
				
				while((readData=fis.read(temp))!=-1){
					fos.write(temp,0,readData);
				}// while
				
				fos.flush();
				
				// 연결 끊기
				if(fis!=null){fis.close();}// end if
				if(fos!=null){fos.close();} // end if
				
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} // catch
			
		} // end if

		String menu = maf.getJtfMenu().getText();
		String price = maf.getJtfPrice().getText();
		String info = maf.getJtaMenuInfo().getText();

		MenuDAO md = MenuDAO.getInstance();
		
		MenuVO mv = new MenuVO();
		
		try {
			// menuVO에 값넣기
			mv.setImg(tempFile);
			mv.setMenu(menu);
			mv.setPrice(Integer.parseInt(price));
			mv.setInfo(info);
			
			// mv에 할당된 값을 
			// DB로 넣는 곳에 매개변수로 넣어줌
			md.insertMenu(mv);
			
			// 정상 추가후 MenuList 갱신
			mfe.setMenu();
			
			// 관리자에게 처리결과를 보여줌
			JOptionPane.showMessageDialog(maf, "메뉴가 정상적으로 추가 되었습니다");
			
			// 창닫기
			addFormClose();
			
		}catch(NumberFormatException nfe){
			JOptionPane.showMessageDialog(maf, "가격은 숫자만 입력가능합니다");
		}catch (SQLException e) {
			JOptionPane.showMessageDialog(maf, "문제 발생 ㅠ");
			e.printStackTrace();
		} // catch
		
	}// addMenu

	private void addFormClose(){
		String path = "C:/dev/workspace/jdbc_prj/src/kr/co/sist/menu/img/default.jpg";
		ImageIcon icon = new ImageIcon(path);
		maf.getJlPreview().setIcon(icon);
		maf.getJtfMenu().setText("");
		maf.getJtfPrice().setText("");
		maf.getJtaMenuInfo().setText("");
		
		// 입력창 숨기기
		maf.setVisible(false);
	}//addFormClose
	
	@Override
	public void windowClosing(WindowEvent e) {
		maf.setVisible(false);
	}// windowClosing

	@Override
	public void actionPerformed(ActionEvent ae) {
		if (ae.getSource() == maf.getJbtAdd()) {
			addMenu();
		} // end if

		if (ae.getSource() == maf.getJbtClose()) {
			addFormClose();
		} // end if

		if (ae.getSource() == maf.getJbtImage()) {
			setImg();
		} // end if

	}// actionPerformed

}// class
