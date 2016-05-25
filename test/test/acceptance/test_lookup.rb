require 'helpers'

#
# Test the entry lookup
#
class TestNew < AcceptanceTest
  def test_lookup_existing
    navigate_to '/lookup'
    candidates = driver.all(xpath: '//*[@id="known-ids"]/option')
    assert(candidates.any?)

    picked_id = candidates.sample['value']

    lookup_form = driver.first(id: 'lookup')
    assert(lookup_form.displayed?)
    lookup_form.first(name: 'id').send_keys(picked_id)
    lookup_form.submit

    entry = driver.first(css: '.entry')
    assert(entry.displayed?)
    id = entry.first(css: '.id')
    assert(id.displayed?)
    assert_equal(picked_id, id.text)
  end

  def test_lookup_non_existing
    navigate_to '/lookup'
    lookup_form = driver.first(id: 'lookup')
    assert(lookup_form.displayed?)

    lookup_form.first(name: 'id').send_keys('4711')
    lookup_form.submit

    error = driver.first(xpath: '//*[@id="error"]')
    assert(error.displayed?)
    assert_includes(error.text, 'kein Eintrag')
  end
end
