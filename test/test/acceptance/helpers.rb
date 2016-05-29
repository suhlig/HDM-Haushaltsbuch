require 'minitest/autorun'
require 'minitest/focus'

require 'selenium-webdriver'
require 'acceptance_test'

module AssertionHelpers
  def assert_content(css_class, allow_empty = false)
    cells = driver.all(xpath: "//td[@class='#{css_class}']")
    assert(1 <= cells.size)

    cells.each do |cell|
      assert(cell.displayed?)
      refute_empty(cell.text) unless allow_empty
    end
  end

  def assert_category_link(link)
    category = link.text
    refute_empty(category)

    url = URI(link['href'])
    assert_equal('/entries/by-category', url.path)
    assert_equal("name=#{URI.escape(category)}", url.query)
  end
end
