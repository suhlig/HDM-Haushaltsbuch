require 'minitest/autorun'
require 'selenium-webdriver'

#
# Test the 'new entry' page
#
class TestNew < MiniTest::Test
  def setup
    @driver = Selenium::WebDriver.for :chrome
  end

  def teardown
    @driver.quit unless failure
  end

  def test_new
    create

    message = @driver.first(xpath: '//*[@id="message"]')
    assert(message.displayed?)
    assert_includes(message.text, 'erfolgreich angelegt')

    id = @driver.first(xpath: '//*[@class="id"]')
    assert(id.displayed?)

    lookup(id.text)
    delete
  end

  def create
    @driver.navigate.to 'http://localhost:9080/hhb/new'

    @driver.first(name: 'srcDst').send_keys('acceptance test srcDst')
    @driver.first(name: 'description').send_keys('acceptance test description')
    @driver.first(name: 'value').send_keys(42)
    @driver.first(name: 'category').send_keys('acceptance test category')
    @driver.first(name: 'paymentType').send_keys('acceptance test paymentType')

    @driver.first(id: 'new').submit
  end

  def lookup(id)
    @driver.navigate.to 'http://localhost:9080/hhb/lookup'

    lookup_form = @driver.first(id: 'lookup')
    assert(lookup_form.displayed?)
    input = lookup_form.first(name: 'id')
    assert(input.displayed?)
    input.send_keys(id)
    input.submit
  end

  def delete
    delete_button = @driver.first(xpath: '//input[@name="id"]')
    delete_button.submit

    message = @driver.first(xpath: '//*[@id="message"]')
    assert(message.displayed?)
    assert_includes(message.text, 'erfolgreich gelöscht')
  end
end
