package nl.sogyo.webserver;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Date;

public class ResponseMessage implements Response {
	
	private HttpStatusCode status;
	private ZonedDateTime date;
	private String content;
	
	public ResponseMessage(HttpStatusCode status, String content) {
		this.status = status;
		date = ZonedDateTime.now();
		this.content = content;
	}
	
	public HttpStatusCode getStatus() {
		return status;
	}
	
    public void setStatus(HttpStatusCode status) {
    	this.status = status;
    }
    
    public ZonedDateTime getDate() {
    	return date;
    }
    
    public String getContent() {
    	return content;
    }
    
    public void setContent(String content) {
    	this.content = content;
    }
}
