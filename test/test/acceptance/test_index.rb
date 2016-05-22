require 'minitest/autorun'
require 'selenium-webdriver'

#
# Test the index page and navigation links
#
class TestIndex < MiniTest::Test
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

  def test_new
    link_new = @driver.find_element(xpath: "//a[@href='new']")
    assert(link_new.displayed?)

    link_new.click

    form = @driver.find_element(xpath: '/html/body/form')
    assert(form.displayed?)
  end

  def test_list
    list = @driver.find_element(xpath: "//a[@href='all']")
    assert(list.displayed?)

    list.click

    table = @driver.find_element(xpath: '/html/body/table')
    assert(table.displayed?)
  end

  def test_title
    assert_equal 'HDM Haushaltsbuch', @driver.title
  end
end
