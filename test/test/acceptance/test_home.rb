require 'helpers'

#
# Test the home page
#
class TestHome < AcceptanceTest
  def test_title
    driver.navigate.to 'http://localhost:9080/hhb/'
    assert_equal 'Haushaltsbuch - Home', @driver.title
    assert_equal 'Home', @driver.first(xpath: '//h1').text
  end
end
