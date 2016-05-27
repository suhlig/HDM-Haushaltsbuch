require 'helpers'
require 'securerandom'

#
# Test the entry lookup
#
class TestNew < AcceptanceTest
  def test_lookup_existing
    picked_id = sample
    lookup(picked_id)

    entry = driver.first(css: '.entry')
    assert(entry.displayed?)
    id = entry.first(css: '.id')
    assert(id.displayed?)
    assert_equal(picked_id, id.text)
  end

  def test_lookup_non_existing
    lookup(SecureRandom.uuid)

    error = driver.first(xpath: '//*[@id="error"]')
    assert(error.displayed?)
    assert_includes(error.text, 'kein Eintrag')
  end
end
