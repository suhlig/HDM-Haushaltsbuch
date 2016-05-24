package haushaltsbuch.persistence;

import haushaltsbuch.Entry;

public abstract class AbstractEntry implements Entry
{
  @Override
  public boolean equals(Object obj)
  {
    if (this == obj)
      return true;

    if (obj == null)
      return false;

    if (getClass() != obj.getClass())
      return false;

    if (!(obj instanceof Entry))
      return false;

    Entry other = (Entry) obj;

    if (getId() == null)
    {
      if (other.getId() != null)
        return false;
    }
    else
      if (!getId().equals(other.getId()))
        return false;

    return true;
  }

  @Override
  public int hashCode()
  {
    final int prime = 31;
    int result = 1;

    result = prime * result + (getId() == null ? 0 : getId().hashCode());

    return result;
  }

}