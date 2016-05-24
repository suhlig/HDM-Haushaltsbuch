package haushaltsbuch;

public class ArgumentException extends RuntimeException
{
  private static final long serialVersionUID = 1L;
  private final String _message;

  public ArgumentException(String message)
  {
    _message = message;
  }

  @Override
  public String getMessage()
  {
    return _message;
  }
}
