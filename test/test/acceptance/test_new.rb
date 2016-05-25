require 'helpers'

#
# Test the 'new entry' page
#
class TestNew < AcceptanceTest
  def test_new
    create

    message = driver.first(xpath: '//*[@id="message"]')
    assert(message.displayed?)
    assert_includes(message.text, 'erfolgreich angelegt')

    id = driver.first(xpath: '//*[@class="id"]')
    assert(id.displayed?)

    lookup(id.text)
    delete
  end

  def test_umlauts
    umlauts = 'some umlauts: öäüß'
    create(description: umlauts)

    description = driver.first(css: '.description')
    assert(description.displayed?)
    assert_equal(umlauts, description.text)

    id = driver.first(xpath: '//*[@class="id"]')
    assert(id.displayed?)

    lookup(id.text)
    delete
  end

  private

  def value(name, overrides)
    overrides.fetch(name, "acceptance test #{name}")
  end
end
