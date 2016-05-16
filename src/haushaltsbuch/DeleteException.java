package haushaltsbuch;

public class DeleteException extends Exception
{
  private static final long serialVersionUID = 1L;
  private final String _message;

  public DeleteException(String message)
  {
    _message = message;
  }

  @Override
  public String getMessage()
  {
    return _message;
  }
}
