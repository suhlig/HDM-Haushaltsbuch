require 'helpers'

#
# Test the 'show entry' view
#
class TestShow < AcceptanceTest
  include AssertionHelpers

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
    link = driver.first(xpath: "//td[@class='category']/a")
    assert_category_link(link)
  end

  def test_delete
    form = driver.first(xpath: "//td[@class='actions']/form")

    assert_equal('Delete!', form.text)
    assert_equal('/entries/delete', URI(form['action']).path)
  end
end
