package es.codeurjc.topics.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Topic not found")
public class TopicNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 6600084908618284957L;
}
