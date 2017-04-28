package es.daniel.buscaminas.exception;

public class CreateGameException extends Exception {
	private static final long serialVersionUID = -6221558829790352835L;

	public CreateGameException() {
		super();
	}
	
	public CreateGameException(String message) {
		super(message);
	}
	
	public CreateGameException(Throwable cause) {
		super(cause);
	}
	
	public CreateGameException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public CreateGameException(String message, Throwable cause, boolean enableSuppression, boolean writeableStackTrace) {
		super(message, cause, enableSuppression, writeableStackTrace);
	}
}
