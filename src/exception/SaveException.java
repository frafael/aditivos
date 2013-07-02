package exception;

@SuppressWarnings("serial")
public class SaveException extends DBException {

	public SaveException() {
		super("Erro ao salvar.");
	}
}