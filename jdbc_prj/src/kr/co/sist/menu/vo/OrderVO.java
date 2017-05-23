package kr.co.sist.menu.vo;

public class OrderVO {
	private String item_code,name,ipaddr;
	private int quan;
	
	public OrderVO() {

	}

	public OrderVO(String item_code, String name, String ipaddr, int quan) {
		super();
		this.item_code = item_code;
		this.name = name;
		this.ipaddr = ipaddr;
		this.quan = quan;
	}

	public String getItem_code() {
		return item_code;
	}

	public void setItem_code(String item_code) {
		this.item_code = item_code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIpaddr() {
		return ipaddr;
	}

	public void setIpaddr(String ipaddr) {
		this.ipaddr = ipaddr;
	}

	public int getQuan() {
		return quan;
	}

	public void setQuan(int quan) {
		this.quan = quan;
	}

	@Override
	public String toString() {
		return "OrderVO [item_code=" + item_code + ", name=" + name + ", ipaddr=" + ipaddr + ", quan=" + quan + "]";
	}
	
}// class
