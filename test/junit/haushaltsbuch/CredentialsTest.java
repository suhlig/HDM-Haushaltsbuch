package junit.haushaltsbuch;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import haushaltsbuch.Credentials;

public class CredentialsTest {
	private final static String VCAP_SERVICES = "{"
		+ "   \"elephantsql\": ["
		+ "      {"
		+ "         \"name\": \"elephantsql\","
		+ "         \"label\": \"elephantsql\","
		+ "         \"plan\": \"turtle\","
		+ "         \"credentials\": {"
		+ "            \"uri\": \"postgres://foo:bar@example.com:5432/everything\","
		+ "            \"max_conns\": \"5\""
		+ "         }"
		+ "      }"
		+ "   ]"
		+ "}";

	private Credentials _subject;
	
	@Before
	public void setup() {
		_subject = new Credentials(VCAP_SERVICES);
	}
	
	@Test
	public void testGetURI() {
		assertEquals("jdbc:postgresql://example.com:5432/everything", _subject.getURI());
	}

	@Test
	public void testGetUser() {
		assertEquals("foo", _subject.getUser());
	}

	@Test
	public void testGetPassword() {
		assertEquals("bar", _subject.getPassword());
	}

}
