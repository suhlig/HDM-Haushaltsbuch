require 'helpers'

#
# Test the home page
#
class TestHome < AcceptanceTest
  def test_title
    navigate_home
    assert_equal 'Haushaltsbuch - Home', driver.title
    assert_equal 'Home', driver.first(xpath: '//h1').text
  end
end
