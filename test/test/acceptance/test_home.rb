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
    @driver.quit
  end

  def test_home
    link_home = @driver.first(xpath: "//a[@href='.']")
    assert(link_home.displayed?)

    link_home.click

    h1 = @driver.first(xpath: '/html/body/h1')
    assert(h1.displayed?)
  end

  def test_title
    assert_equal 'Haushaltsbuch - Home', @driver.title
    assert_equal 'Home', @driver.first(xpath: '//h1').text
  end
end
