package kr.co.sist.menu.vo;

public class OrderAllVO {
	private String item_code,menu,name,orderDate,ipAddr;
	private int order_num,quan,totalPrice;
	
	public OrderAllVO() {

	}

	public OrderAllVO(String item_code, String menu, String name, String orderDate, String ipAddr, int order_num,
			int quan, int totalPrice) {
		super();
		this.item_code = item_code;
		this.menu = menu;
		this.name = name;
		this.orderDate = orderDate;
		this.ipAddr = ipAddr;
		this.order_num = order_num;
		this.quan = quan;
		this.totalPrice = totalPrice;
	}

	public String getItem_code() {
		return item_code;
	}

	public void setItem_code(String item_code) {
		this.item_code = item_code;
	}

	public String getMenu() {
		return menu;
	}

	public void setMenu(String menu) {
		this.menu = menu;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}

	public String getIpAddr() {
		return ipAddr;
	}

	public void setIpAddr(String ipAddr) {
		this.ipAddr = ipAddr;
	}

	public int getOrder_num() {
		return order_num;
	}

	public void setOrder_num(int order_num) {
		this.order_num = order_num;
	}

	public int getQuan() {
		return quan;
	}

	public void setQuan(int quan) {
		this.quan = quan;
	}

	public int getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(int totalPrice) {
		this.totalPrice = totalPrice;
	}

	@Override
	public String toString() {
		return "OrderAllVO [item_code=" + item_code + ", menu=" + menu + ", name=" + name + ", orderDate=" + orderDate
				+ ", ipAddr=" + ipAddr + ", order_num=" + order_num + ", quan=" + quan + ", totalPrice=" + totalPrice
				+ "]";
	}

}// class
