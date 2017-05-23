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

	private MenuDAO() { // 외부에서 접근하지못하게 프라이빗으로 접근지정자를 줌(싱글턴패턴)

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

				try {// 드라이버로딩
					Class.forName(driver);
					con = DriverManager.getConnection(url, id, pw);
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} // catch
			} else {
				JOptionPane.showMessageDialog(null, "경로를 확인하세요");
			} // end else if
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} // catch

		return con;
	}// getConnection

	/**
	 * 입력된 메뉴의 목록을 조회<br>
	 * item_code,img,menu,info,price를 조회하여 MenuVO에 저장하고 List에 추가하여 반환하는 일을 한다.
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
			// 1.드라이버로딩
			// 2.커넥션 얻기
			con = getConnection();
			// 3.쿼리문 생성 객체 얻기
			String selectMenu = "select item_code,img,menu,info,price from menu";
			pstmt = con.prepareStatement(selectMenu);
			// 4.쿼리문 실행 후 객체 얻기
			rs = pstmt.executeQuery();

			MenuVO mv = null;
			while (rs.next()) {
				mv = new MenuVO();
				// 생성자에 넣으면 무슨 값이 들어갔는지 알수 없다.
				// setter를 이용해서 어디에 무슨값이 들어갔는지 알수 있다
				// 실수를 줄일 수 있다.
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
	 * 메뉴 주문 정보(item_code,name,quan)를 테이블에 추가하는 일
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
			// 커넥션 얻기
			con = getConnection();

			// 3.
			// 쿼리문 수행 객체 얻기
			String insertOrdering = "insert into ordering(order_num,name,item_code,quan,ipaddr,order_date) values(seq_ordering.nextval,?,?,?,?,sysdate)";
			pstmt = con.prepareStatement(insertOrdering);

			// 4.
			// 바인드 변수에 값 할당
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
	 * 메뉴를 테이블에 추가하는 일
	 * 
	 * @param mv
	 * @throws SQLException
	 */
	public void insertMenu(MenuVO mv) throws SQLException {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			// 커넥션 얻기
			con = getConnection();

			// 쿼리 생성 객체 얻기
			String insertMenu = "insert into menu(item_code,menu,price,info,img,inputdate) "
					+ "values(menu_num,?,?,?,?,sysdate)";
			pstmt = con.prepareStatement(insertMenu);

			// 바인드 변수 값넣기
			pstmt.setString(1, mv.getMenu());
			pstmt.setInt(2, mv.getPrice());
			pstmt.setString(3, mv.getInfo());
			pstmt.setString(4, mv.getImg());

			// 쿼리 실행
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
	 * 그날 1시까지의 주문정보를 조회하는 일
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
				
				// 리스트에 담아줌
				// list의 데이터형은 OrderAllVO
				// 각각의 값을 저장하는 인스턴스 변수에
				// list를 이용해서 값을 할당
				// 그것을 리턴
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

	///////////// 단위테스트 메소드 시작/////////////////
	public static void main(String[] args) {

		// try {
		// // 피씨의 아이디와 ,아이피를 얻을 수 있음(추적가능 허브에 남아서 라우터에 올라감.)
		// // 이사람이 했는지 안했는지를 알기위하여
		// String ip = InetAddress.getLocalHost().toString();// 피씨의아이디와,아이피
		// MenuDAO md = MenuDAO.getInstance();
		// OrderVO ov = new OrderVO("AL_00001", "우동하", ip, 1);
		// md.insertOrder(ov);
		// System.out.println("주문추가성공");
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
			// ip얻어오기
			// String ip = InetAddress.getLocalHost().toString();
			// System.out.println(MenuDAO.getInstance().getConnection());

			MenuDAO md = new MenuDAO();
//
//			MenuVO mv = new MenuVO("", "m1_l2.gif", "돈까스", "누구나 좋아하는 돈까스! " + "바삭튀겨낸 튀김옷안에 숨겨진 육질을 확인하세요. 소스맛도 끝내줍니다",
//					1);
//			md.insertMenu(mv);

			System.out.println(md.selectOrder());
			
//			System.out.println("메뉴 추가 성공");

		} catch (SQLException e) {
			e.printStackTrace();
		} // catch

	}// main
		///////////// 단위테스트 메소드 끝 /////////////////
}// class