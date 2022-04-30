package domain;

public class OrderTicket {

	private int orderId;
    private int ticketId;
    
    // Getter, Setter method 정의
	public int getOrderId() {return orderId;}
	public void setOrderId(int orderId) {this.orderId = orderId;}
	public int getTicketId() {return ticketId;}
	public void setTicketId(int ticketId) {this.ticketId = ticketId;}

	// ToString 오버라이딩
	@Override
	public String toString() {
		return "OrderTicket [orderId=" + orderId + ", ticketId=" + ticketId + "]";
	}
}
