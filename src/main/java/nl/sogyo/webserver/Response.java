package nl.sogyo.webserver;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Date;

public interface Response {
    HttpStatusCode getStatus();
    void setStatus(HttpStatusCode status);
    ZonedDateTime getDate();
    String getContent();
    void setContent(String content);
}