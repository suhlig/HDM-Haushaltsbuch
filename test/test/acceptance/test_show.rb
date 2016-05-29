require 'helpers'

#
# Test the 'show entry' view
#
class TestShow < AcceptanceTest
  def setup
    super
    @id = create
  end

  def teardown
    delete(@id)
    super
  end

  def test_title
    lookup(@id)
    assert_equal 'Haushaltsbuch - Eintrag', driver.title
    assert_equal 'Eintrag', driver.first(xpath: '//h1').text
  end

  def test_mandatory_fields
    %w(entryDate srcDst description value paymentType).each do |name|
      assert_content(name)
    end
  end

  def test_category
    assert_content('category', true)
  end

  def test_delete
    form = driver.first(xpath: "//td[@class='actions']/form")

    assert_equal('Delete!', form.text)
    assert_equal('/entries/delete', URI(form['action']).path)
  end

  private

  def assert_content(css_class, allow_empty = false)
    cells = driver.all(xpath: "//td[@class='#{css_class}']")
    assert(1 <= cells.size)

    cells.each do |cell|
      assert(cell.displayed?)
      refute_empty(cell.text) unless allow_empty
    end
  end
end
