require 'minitest/autorun'
require 'selenium-webdriver'

#
# Test the home page
#
class TestHome < MiniTest::Test
  def setup
    @driver = Selenium::WebDriver.for :chrome
    @driver.navigate.to 'http://localhost:9080/hhb/'
  end

  def teardown
    @driver.quit unless failure
  end

  def test_title
    assert_equal 'Haushaltsbuch - Home', @driver.title
    assert_equal 'Home', @driver.first(xpath: '//h1').text
  end
end
