require 'minitest/autorun'
require 'selenium-webdriver'

#
# Test the entry lookup
#
class TestNew < MiniTest::Test
  def setup
    @driver = Selenium::WebDriver.for :chrome
  end

  def teardown
    @driver.quit unless failure
  end

  def test_lookup_non_existing
    @driver.navigate.to 'http://localhost:9080/hhb/lookup'

    lookup_form = @driver.first(id: 'lookup')
    assert(lookup_form.displayed?)

    lookup_form.first(name: 'id').send_keys('4711')

    lookup_form.submit

    error = @driver.first(xpath: '//*[@id="error"]')
    assert(error.displayed?)
    assert_includes(error.text, 'konnte nicht gefunden werden')
  end
end
