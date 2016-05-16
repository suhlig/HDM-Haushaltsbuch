package haushaltsbuch;

import java.net.URI;
import org.json.JSONException;
import org.json.JSONObject;

public class ElephantSqlConfig
{
  public class ParseException extends Exception
  {
    private static final long serialVersionUID = 1L;

    public ParseException(String vcap_services, JSONException e)
    {
      super("Could not parse vcap_services '" + vcap_services + "'", e);
    }
  }

  private final String _uri;
  private final String _user;
  private final String _password;

  public ElephantSqlConfig(String vcap_services) throws ParseException
  {
    JSONObject obj;

    try
    {
      obj = new JSONObject(vcap_services);
    }
    catch (JSONException e)
    {
      throw new ParseException(vcap_services, e);
    }

    JSONObject credentials = obj.getJSONArray("elephantsql").getJSONObject(0).getJSONObject("credentials");

    URI pgURI = URI.create(credentials.getString("uri"));

    _uri = buildJdbcURL(pgURI);

    String userInfo = pgURI.getUserInfo();

    if (null == userInfo || userInfo.isEmpty())
    {
      _user = null;
      _password = null;
    }
    else
    {
      String[] parts = userInfo.split(":");
      _user = parts[0];
      _password = parts[1];
    }
  }

  private String buildJdbcURL(URI postgresURI)
  {
    String host = postgresURI.getHost();
    int port = postgresURI.getPort();

    StringBuilder jdbcURL = new StringBuilder();

    jdbcURL.append("jdbc:postgresql://");

    if (null != host)
      jdbcURL.append(host);

    if (-1 != port)
    {
      jdbcURL.append(":");
      jdbcURL.append(port);
    }

    jdbcURL.append(postgresURI.getPath());

    String tmpuri = jdbcURL.toString();
    return tmpuri;
  }

  public String getPassword()
  {
    return _password;
  }

  public String getURI()
  {
    return _uri;
  }

  public String getUser()
  {
    return _user;
  }
}
