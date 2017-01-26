package test.org.spring.websocket.api.model;

import java.io.Serializable;

/**
 * 
 * @author dr.ricalde
 *
 */
public class ChatMessage implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8602756071658886976L;

	private String message;

	private String recipient;

	private String sender;

	private String destination;

	private String time;
	
	private Boolean receivedByServer;
	
	private Boolean receivedByClient;
	
	private String id;
	
	private ChatMessageState state;

	public ChatMessage(String message) {
		super();
		this.message = message;
	}

	public ChatMessage() {
		super();
	}

	public ChatMessage(String message, String sender) {
		super();
		this.message = message;
		this.sender = sender;
	}

	public ChatMessage(String message, String sender, String recipient) {
		super();
		this.message = message;
		this.sender = sender;
		this.recipient = recipient;
	}

	public ChatMessage(String message, String recipient, String sender, String destination) {
		super();
		this.message = message;
		this.recipient = recipient;
		this.sender = sender;
		this.destination = destination;
	}

	public ChatMessage(String message, String recipient, String sender, String destination, String time, Boolean receivedByServer) {
		super();
		this.message = message;
		this.recipient = recipient;
		this.sender = sender;
		this.destination = destination;
		this.time = time;
		this.receivedByServer = receivedByServer;
	}
	
	public ChatMessage(String id, String message, String recipient, String sender, String destination, String time, Boolean receivedByServer) {
		super();
		this.id = id;
		this.message = message;
		this.recipient = recipient;
		this.sender = sender;
		this.destination = destination;
		this.time = time;
		this.receivedByServer = receivedByServer;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message
	 *            the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return the recipient
	 */
	public String getRecipient() {
		return recipient;
	}

	/**
	 * @param recipient
	 *            the recipient to set
	 */
	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}

	/**
	 * @return the sender
	 */
	public String getSender() {
		return sender;
	}

	/**
	 * @param sender
	 *            the sender to set
	 */
	public void setSender(String sender) {
		this.sender = sender;
	}

	@Override
	public String toString() {
		return "ChatMessage [message=" + message + ", recipient=" + recipient + ", sender=" + sender + ", destination=" + destination + "]";
	}

	/**
	 * @return the destination
	 */
	public String getDestination() {
		return destination;
	}

	/**
	 * @param destination
	 *            the destination to set
	 */
	public void setDestination(String destination) {
		this.destination = destination;
	}

	/**
	 * @return the time
	 */
	public String getTime() {
		return time;
	}

	/**
	 * @param time
	 *            the time to set
	 */
	public void setTime(String time) {
		this.time = time;
	}

	/**
	 * @return the receivedByServer
	 */
	public Boolean getReceivedByServer() {
		return receivedByServer;
	}

	/**
	 * @param receivedByServer the receivedByServer to set
	 */
	public void setReceivedByServer(Boolean receivedByServer) {
		this.receivedByServer = receivedByServer;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the receivedByClient
	 */
	public Boolean getReceivedByClient() {
		return receivedByClient;
	}

	/**
	 * @param receivedByClient the receivedByClient to set
	 */
	public void setReceivedByClient(Boolean receivedByClient) {
		this.receivedByClient = receivedByClient;
	}

	public ChatMessageState getState() {
		return state;
	}

	public void setState(ChatMessageState state) {
		this.state = state;
	}

}
