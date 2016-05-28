require 'helpers'

#
# Test the navigation links
#
class TestNavigation < AcceptanceTest
  def test_new
    navigate_home
    link_new = driver.first(xpath: "//a[@href='entries/new']")
    assert(link_new.displayed?)

    link_new.click

    form = driver.first(xpath: '/html/body/form')
    assert(form.displayed?)

    active_menu_item = driver.first(css: '.navbar li .active')
    assert(active_menu_item.displayed?)
    assert_includes(active_menu_item.attribute('href'), 'new')
  end

  def test_all
    navigate_home
    all = driver.first(xpath: "//a[@href='entries/all']")
    assert(all.displayed?)

    all.click

    table = driver.first(xpath: '/html/body/table')
    assert(table.displayed?)

    active_menu_item = driver.first(css: '.navbar li .active')
    assert(active_menu_item.displayed?)
    assert_includes(active_menu_item.attribute('href'), 'entries')
  end

  def test_lookup
    navigate_home
    lookup = driver.first(xpath: "//a[@href='entries/lookup']")
    assert(lookup.displayed?)

    lookup.click

    form = driver.first(xpath: '/html/body/form')
    assert(form.displayed?)

    active_menu_item = driver.first(css: '.navbar li .active')
    assert(active_menu_item.displayed?)
    assert_includes(active_menu_item.attribute('href'), 'lookup')
  end

  def test_categories
    navigate_home
    categories = driver.first(xpath: "//a[@href='categories/all']")
    assert(categories.displayed?)

    categories.click

    category_list = driver.first(xpath: '//*[@id="categories"]')
    assert(category_list.displayed?)

    active_menu_item = driver.first(css: '.navbar li .active')
    assert(active_menu_item.displayed?)
    assert_includes(active_menu_item.attribute('href'), 'categories/all')
  end

  def test_home
    navigate_home
    link_home = driver.first(xpath: "//a[@href='.']")
    assert(link_home.displayed?)

    link_home.click

    h1 = driver.first(xpath: '/html/body/h1')
    assert(h1.displayed?)

    active_menu_item = driver.first(css: '.navbar li .active')
    assert(active_menu_item.displayed?)
  end
end
