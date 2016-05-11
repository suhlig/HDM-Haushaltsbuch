package haushaltsbuch;

import java.net.URI;

import org.json.JSONException;
import org.json.JSONObject;

public class Credentials {
	public class ParseException extends Exception {
		private static final long serialVersionUID = 1L;

		public ParseException(String vcap_services, JSONException e) {
			super("Could not parse vcap_services '" + vcap_services + "'", e);
		}
	}

	private final String _uri;
	private final String _user;
	private final String _password;

	public Credentials(String vcap_services) throws ParseException {
		JSONObject obj;

		try {
			obj = new JSONObject(vcap_services);
		} catch (JSONException e) {
			throw new ParseException(vcap_services, e);
		}

		JSONObject credentials = obj.getJSONArray("elephantsql").getJSONObject(0).getJSONObject("credentials");

		URI uri = URI.create(credentials.getString("uri"));

		_uri = "jdbc:postgresql://" + uri.getHost() + ":" + uri.getPort() + uri.getPath();

		String[] parts = uri.getUserInfo().split(":");

		if (2 != parts.length)
			throw new ArrayIndexOutOfBoundsException("Could not find username:password");

		_user = parts[0];
		_password = parts[1];
	}

	public String getURI() {
		return _uri;
	}

	public String getUser() {
		return _user;
	}

	public String getPassword() {
		return _password;
	}
}
