package tech.grasshopper.combiner.exception;

public class CombinerException extends RuntimeException {

	public CombinerException() {
		super();
	}

	public CombinerException(String string) {
		super(string);
	}

	public CombinerException(Throwable throwable) {
		super(throwable);
	}

	public CombinerException(String string, Throwable throwable) {
		super(string, throwable);
	}

	private static final long serialVersionUID = -6211995537090274343L;

}
