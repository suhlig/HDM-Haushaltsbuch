require 'helpers'
require 'uri'

#
# Test the 'all entries' view
#
class TestAll < AcceptanceTest
  def setup
    super
    navigate_to '/entries/all'
  end

  def test_title
    assert_equal 'Haushaltsbuch - Alle Einträge', driver.title
    assert_equal 'Alle Einträge', driver.first(xpath: '//h1').text
  end

  def test_mandatory_fields
    %w(entryDate srcDst description value paymentType).each do |name|
      assert_content(name)
    end
  end

  def test_category
    assert_content('category', true)
    assert_category_links
  end

  def test_actions
    assert_content('actions')
    assert_action_links
    assert_delete_forms
  end

  private

  def assert_action_links
    details_links = driver.all(xpath: "//td[@class='actions']/a")
    assert(1 <= details_links.size)

    details_links.each do |link|
      assert_equal('Details', link.text)
      assert_equal('/entries', URI(link['href']).path)
    end
  end

  def assert_delete_forms
    delete_forms = driver.all(xpath: "//td[@class='actions']/form")
    assert(1 <= delete_forms.size)

    delete_forms.each do |form|
      assert_equal('Delete', form.text)
      assert_equal('/entries/delete', URI(form['action']).path)
    end
  end

  def assert_content(css_class, allow_empty = false)
    cells = driver.all(xpath: "//td[@class='#{css_class}']")
    assert(1 <= cells.size)

    cells.each do |cell|
      assert(cell.displayed?)
      refute_empty(cell.text) unless allow_empty
    end
  end

  def assert_category_links
    details_links = driver.all(xpath: "//td[@class='category']/a")
    assert(1 <= details_links.size)

    details_links.each do |link|
      assert_category_link(link)
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
