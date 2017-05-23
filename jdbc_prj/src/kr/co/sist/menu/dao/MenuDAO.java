package kr.co.sist.menu.dao;

import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import javax.swing.JOptionPane;

import kr.co.sist.menu.vo.MenuVO;
import kr.co.sist.menu.vo.OrderAllVO;
import kr.co.sist.menu.vo.OrderVO;

public class MenuDAO {
	private static MenuDAO m_dao;

	private MenuDAO() { // �ܺο��� �����������ϰ� �����̺����� ���������ڸ� ��(�̱�������)

	}// MenuDAO
 
	public static MenuDAO getInstance() {
		if (m_dao == null) {
			m_dao = new MenuDAO();
		} // end if
		return m_dao;
	}// getInstance

	private Connection getConnection() throws SQLException {
		Connection con = null;
		Properties prop = new Properties();
		try {
			File file = new File("C:/dev/jdbc_git/jdbc_prj/src/kr/co/sist/menu/dao/menu_db.properties");
			if (file.exists()) {
				prop.load(new FileInputStream(file));
				String driver = prop.getProperty("driver");
				String url = prop.getProperty("url");
				String id = prop.getProperty("dboid");
				String pw = prop.getProperty("dbopwd");

				try {// ����̹��ε�
					Class.forName(driver);
					con = DriverManager.getConnection(url, id, pw);
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} // catch
			} else {
				JOptionPane.showMessageDialog(null, "��θ� Ȯ���ϼ���");
			} // end else if
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} // catch

		return con;
	}// getConnection

	/**
	 * �Էµ� �޴��� ����� ��ȸ<br>
	 * item_code,img,menu,info,price�� ��ȸ�Ͽ� MenuVO�� �����ϰ� List�� �߰��Ͽ� ��ȯ�ϴ� ���� �Ѵ�.
	 * 
	 * @return : List<MenuVO>
	 * @throws SQLException
	 */
	public List<MenuVO> selectMenuList() throws SQLException {
		List<MenuVO> list = new ArrayList<MenuVO>();

		Connection con = null;
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		try {
			// 1.����̹��ε�
			// 2.Ŀ�ؼ� ���
			con = getConnection();
			// 3.������ ���� ��ü ���
			String selectMenu = "select item_code,img,menu,info,price from menu";
			pstmt = con.prepareStatement(selectMenu);
			// 4.������ ���� �� ��ü ���
			rs = pstmt.executeQuery();

			MenuVO mv = null;
			while (rs.next()) {
				mv = new MenuVO();
				// �����ڿ� ������ ���� ���� ������ �˼� ����.
				// setter�� �̿��ؼ� ��� �������� ������ �˼� �ִ�
				// �Ǽ��� ���� �� �ִ�.
				mv.setItem_code(rs.getString("item_code"));
				mv.setImg(rs.getString("img"));
				mv.setMenu(rs.getString("menu"));
				mv.setInfo(rs.getString("info"));
				mv.setPrice(rs.getInt("price"));

				list.add(mv);

			} // end while

		} finally {
			// 5
			if (pstmt != null) {
				pstmt.close();
			} // end if
			if (rs != null) {
				rs.close();
			} // end if
			if (con != null) {
				con.close();
			} // end if
		} // finally
		
		return list;
	}// selectMenuList

	
	/**
	 * �޴� �ֹ� ����(item_code,name,quan)�� ���̺� �߰��ϴ� ��
	 * 
	 * @param ov
	 * @throws SQLException
	 */
	public void insertOrder(OrderVO ov) throws SQLException {
		Connection con = null;
		PreparedStatement pstmt = null;

		// item_code,name,quan

		try {
			// 1.
			// 2.
			// Ŀ�ؼ� ���
			con = getConnection();

			// 3.
			// ������ ���� ��ü ���
			String insertOrdering = "insert into ordering(order_num,name,item_code,quan,ipaddr,order_date) values(seq_ordering.nextval,?,?,?,?,sysdate)";
			pstmt = con.prepareStatement(insertOrdering);

			// 4.
			// ���ε� ������ �� �Ҵ�
			pstmt.setString(1, ov.getName());
			pstmt.setString(2, ov.getItem_code());
			pstmt.setInt(3, ov.getQuan());
			pstmt.setString(4, ov.getIpaddr());

			pstmt.executeUpdate();

		} finally {
			// 5.
			if (pstmt != null) {
				pstmt.close();
			}
			if (con != null) {
				con.close();
			}
		}
	}// insertOrder

	/**
	 * �޴��� ���̺� �߰��ϴ� ��
	 * 
	 * @param mv
	 * @throws SQLException
	 */
	public void insertMenu(MenuVO mv) throws SQLException {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			// Ŀ�ؼ� ���
			con = getConnection();

			// ���� ���� ��ü ���
			String insertMenu = "insert into menu(item_code,menu,price,info,img,inputdate) "
					+ "values(menu_num,?,?,?,?,sysdate)";
			pstmt = con.prepareStatement(insertMenu);

			// ���ε� ���� ���ֱ�
			pstmt.setString(1, mv.getMenu());
			pstmt.setInt(2, mv.getPrice());
			pstmt.setString(3, mv.getInfo());
			pstmt.setString(4, mv.getImg());

			// ���� ����
			pstmt.executeUpdate();
		} finally {
			if (pstmt != null) {
				pstmt.close();
			}
			if (con != null) {
				con.close();
			}

		} // finally

	}// insertMenu

	/**
	 * �׳� 1�ñ����� �ֹ������� ��ȸ�ϴ� ��
	 * 
	 * @return
	 * @throws SQLException
	 */
	public List<OrderAllVO> selectOrder() throws SQLException {
		List<OrderAllVO> list = new ArrayList<OrderAllVO>();
		// item_code,name,orderName,quan,price;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		
		try {
			// 1.
			// 2.
			con = getConnection();
			
			// 3.
			StringBuilder selectOrdering = new StringBuilder();
			selectOrdering.append("select m.item_code,m.menu,o.order_num,o.name,to_char(o.order_date,'hh24:mi:ss') order_date,o.ipaddr,o.quan,(m.price*o.quan) totalPrice")
			.append(" from menu m, ordering o")
			.append(" where (o.item_code=m.item_code)")
			.append(" and to_char(order_date,'hh24')>8")
			.append(" and to_char(order_date,'hh24')<13")
			.append(" and to_char(order_date,'yyyymmdd')=to_char(sysdate,'yyyymmdd')")
			.append(" order by order_date asc");
			
			pstmt  = con.prepareStatement(selectOrdering.toString());
			
			// 4.
			rs = pstmt.executeQuery();
			
			OrderAllVO oav = null;
			while (rs.next()){
				oav = new OrderAllVO();
				oav.setItem_code(rs.getString("item_code"));
				oav.setMenu(rs.getString("menu"));
				oav.setName(rs.getString("name"));
				oav.setOrderDate(rs.getString("order_date"));
				oav.setIpAddr(rs.getString("ipaddr"));
				oav.setQuan(rs.getInt("quan"));
				oav.setTotalPrice(rs.getInt("totalPrice"));
				oav.setOrder_num(rs.getInt("order_num"));
				
				// ����Ʈ�� �����
				// list�� ���������� OrderAllVO
				// ������ ���� �����ϴ� �ν��Ͻ� ������
				// list�� �̿��ؼ� ���� �Ҵ�
				// �װ��� ����
				list.add(oav);
				
			}// while
			
			
		} finally {
			// 5.
			if(rs!=null){rs.close();}
			if(pstmt!=null){pstmt.close();}
			if(con!=null){con.close();}
		} // finally

		return list;
	}// selectOrder

	///////////// �����׽�Ʈ �޼ҵ� ����/////////////////
	public static void main(String[] args) {

		// try {
		// // �Ǿ��� ���̵�� ,�����Ǹ� ���� �� ����(�������� ��꿡 ���Ƽ� ����Ϳ� �ö�.)
		// // �̻���� �ߴ��� ���ߴ����� �˱����Ͽ�
		// String ip = InetAddress.getLocalHost().toString();// �Ǿ��Ǿ��̵��,������
		// MenuDAO md = MenuDAO.getInstance();
		// OrderVO ov = new OrderVO("AL_00001", "�쵿��", ip, 1);
		// md.insertOrder(ov);
		// System.out.println("�ֹ��߰�����");
		// } catch (UnknownHostException e) {
		// e.printStackTrace();
		// } catch (SQLException e) {
		// e.printStackTrace();
		// }

		// try {
		// System.out.println(MenuDAO.getInstance().getConnection());
		// } catch (SQLException e) {
		// e.printStackTrace();
		// }// catch

		try {
			// ip������
			// String ip = InetAddress.getLocalHost().toString();
			// System.out.println(MenuDAO.getInstance().getConnection());

			MenuDAO md = new MenuDAO();
//
//			MenuVO mv = new MenuVO("", "m1_l2.gif", "���", "������ �����ϴ� ���! " + "�ٻ�Ƣ�ܳ� Ƣ��ʾȿ� ������ ������ Ȯ���ϼ���. �ҽ����� �����ݴϴ�",
//					1);
//			md.insertMenu(mv);

			System.out.println(md.selectOrder());
			
//			System.out.println("�޴� �߰� ����");

		} catch (SQLException e) {
			e.printStackTrace();
		} // catch

	}// main
		///////////// �����׽�Ʈ �޼ҵ� �� /////////////////
}// class