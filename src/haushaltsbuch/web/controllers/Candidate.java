package haushaltsbuch.web.controllers;

import java.util.Map;
import haushaltsbuch.Entry;

public interface Candidate<T> extends Entry
{
  Map<String, String> getValidationErrors();

  boolean isValid();
}
