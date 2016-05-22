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
    link_home = @driver.find_element(xpath: "//a[@href='.']")
    assert(link_home.displayed?)

    link_home.click

    h1 = @driver.find_element(xpath: '/html/body/h1')
    assert(h1.displayed?)
  end

  def test_title
    assert_equal 'HDM Haushaltsbuch', @driver.title
  end
end
