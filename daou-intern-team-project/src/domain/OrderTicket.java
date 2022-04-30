package domain;

public class OrderTicket {

	private int orderId;
    private int ticketId;
    
    // Getter, Setter method ����
	public int getOrderId() {return orderId;}
	public void setOrderId(int orderId) {this.orderId = orderId;}
	public int getTicketId() {return ticketId;}
	public void setTicketId(int ticketId) {this.ticketId = ticketId;}

	// ToString �������̵�
	@Override
	public String toString() {
		return "OrderTicket [orderId=" + orderId + ", ticketId=" + ticketId + "]";
	}
}
