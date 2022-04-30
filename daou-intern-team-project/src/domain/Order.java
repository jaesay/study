package domain;

import java.sql.Timestamp;

public class Order {

	private int orderId;
    private int memberId;
    private Timestamp orderDate;
	
    // Getter, Setter method ����
    public int getOrderId() {return orderId;}
	public void setOrderId(int orderId) {this.orderId = orderId;}
	public int getMemberId() {return memberId;}
	public void setMemberId(int memberId) {this.memberId = memberId;}
	public Timestamp getOrderDate() {return orderDate;}
	public void setOrderDate(Timestamp orderDate) {this.orderDate = orderDate;}

	// ToString �������̵�
	@Override
	public String toString() {
		return "Order [orderId=" + orderId + ", memberId=" + memberId + ", orderDate=" + orderDate + "]";
	}
}
