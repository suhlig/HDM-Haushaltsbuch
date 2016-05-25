require 'helpers'

#
# Test the navigation links
#
class TestNavigation < AcceptanceTest
  def test_new
    driver.navigate.to 'http://localhost:9080/hhb/'
    link_new = driver.first(xpath: "//a[@href='new']")
    assert(link_new.displayed?)

    link_new.click

    form = driver.first(xpath: '/html/body/form')
    assert(form.displayed?)

    active_menu_item = driver.first(css: '.navbar li .active')
    assert(active_menu_item.displayed?)
    assert_includes(active_menu_item.attribute('href'), 'new')
  end

  def test_all
    driver.navigate.to 'http://localhost:9080/hhb/'
    all = driver.first(xpath: "//a[@href='all']")
    assert(all.displayed?)

    all.click

    table = driver.first(xpath: '/html/body/table')
    assert(table.displayed?)

    active_menu_item = driver.first(css: '.navbar li .active')
    assert(active_menu_item.displayed?)
    assert_includes(active_menu_item.attribute('href'), 'all')
  end

  def test_lookup
    driver.navigate.to 'http://localhost:9080/hhb/'
    lookup = driver.first(xpath: "//a[@href='lookup']")
    assert(lookup.displayed?)

    lookup.click

    form = driver.first(xpath: '/html/body/form')
    assert(form.displayed?)

    active_menu_item = driver.first(css: '.navbar li .active')
    assert(active_menu_item.displayed?)
    assert_includes(active_menu_item.attribute('href'), 'lookup')
  end

  def test_home
    driver.navigate.to 'http://localhost:9080/hhb/'
    link_home = driver.first(xpath: "//a[@href='.']")
    assert(link_home.displayed?)

    link_home.click

    h1 = driver.first(xpath: '/html/body/h1')
    assert(h1.displayed?)

    active_menu_item = driver.first(css: '.navbar li .active')
    assert(active_menu_item.displayed?)
  end
end
