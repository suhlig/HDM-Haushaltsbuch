package haushaltsbuch;

import java.net.URI;

import org.json.JSONObject;

public class Credentials {
		private final String _uri;
		private final String _user;
		private final String _password;

		public Credentials(String vcap_services) {
			JSONObject obj = new JSONObject(vcap_services);
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
