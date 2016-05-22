require 'minitest/autorun'
require 'selenium-webdriver'

#
# Test the navigation links
#
class TestNavigation < MiniTest::Test
  def setup
    @driver = Selenium::WebDriver.for :chrome
    @driver.navigate.to 'http://localhost:9080/hhb/'
  end

  def teardown
    @driver.quit
  end

  def test_new
    link_new = @driver.first(xpath: "//a[@href='new']")
    assert(link_new.displayed?)

    link_new.click

    form = @driver.first(xpath: '/html/body/form')
    assert(form.displayed?)
  end

  def test_list
    list = @driver.first(xpath: "//a[@href='all']")
    assert(list.displayed?)

    list.click

    table = @driver.first(xpath: '/html/body/table')
    assert(table.displayed?)
  end

  def test_lookup
    lookup = @driver.first(xpath: "//a[@href='lookup']")
    assert(lookup.displayed?)

    lookup.click

    form = @driver.first(xpath: '/html/body/form')
    assert(form.displayed?)
  end
end
