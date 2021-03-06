package com.kh.food.model.vo;

import java.io.Serializable;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Map;

@SuppressWarnings("serial")
public class User implements Serializable, Comparable<User> {
	private String username;
	private String phone;
	private String email;
	private String address;
	private boolean logged; //로그인 상태 여부
	private Map<Food, Integer> orderList;
	private GregorianCalendar orderCreated;
	private int seatNo; //좌석(1~SEATS)

	public User(String username, String phone, String email, String address, boolean logged,
			Map<Food, Integer> orderList, GregorianCalendar orderCreated, int seatNo) {
		super();
		this.username = username;
		this.phone = phone;
		this.email = email;
		this.address = address;
		this.logged = logged;
		this.orderList = orderList;
		this.orderCreated = orderCreated;
		this.seatNo = seatNo;
	}

	public void showUserInfo() {
		String date = null;
		if(orderCreated !=null) {
			Date temp = new Date(orderCreated.getTimeInMillis());
			SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss yy-MM-dd");
			date = sdf.format(temp);
		}
		System.out.printf("%s\t%s\t%s\t%s\t%s\t%s\n",
							username, phone, email, address,
							(logged? "로그ON":"로그OFF"), date);
		this.showOrderList();
	}
	
	public void showOrderList() {
		for(Map.Entry<Food, Integer> entry : orderList.entrySet())
			System.out.println("\t[" + entry.getKey().getMenuName() + " --- " 
									+ toCurrency(entry.getKey().getMenuPrice()) +", " 
									+ entry.getValue() + "개]");
	}

	@Override
	public int hashCode() {
		return this.phone.length();
	}

	@Override
	public boolean equals(Object obj) {
		User user = (User)obj;
		if(this.phone.equals(user.getPhone()))
			return true;
		else
			return false;
	}
	
	@Override
	public int compareTo(User o) {
		return this.getUsername().compareTo(o.getUsername());
	}

	private String toCurrency(int price) {
		NumberFormat n = NumberFormat.getCurrencyInstance(Locale.KOREA); 
		String s = n.format(price);
		return s;
	}

	//getter setter
	public String getUsername() { return username; }
	public void setUsername(String username) { this.username = username; }
	public String getPhone() { return phone; }
	public void setPhone(String phone) { this.phone = phone; }
	public String getEmail() { return email; }
	public void setEmail(String email) { this.email = email; }
	public String getAddress() { return address; }
	public void setAddress(String address) { this.address = address; }
	public boolean isLogged() { return logged; } 
	public void setLogged(boolean logged) { this.logged = logged; }
	public Map<Food, Integer> getOrderList() { return orderList; }
	public void setOrderList(Map<Food, Integer> orderList) { this.orderList = orderList; }
	public GregorianCalendar getOrderCreated() { return orderCreated; }
	public void setOrderCreated(GregorianCalendar orderCreated) { this.orderCreated = orderCreated; }
	public int getSeatNo() { return seatNo; }
	public void setSeatNo(int seatNo) { this.seatNo = seatNo; }


}
