class AcceptanceTest < MiniTest::Test
  attr_reader :driver

  def setup
    @driver = Selenium::WebDriver.for :chrome
  end

  def teardown
    driver.quit unless failure
  end

  def navigate_to(uri)
    base_url = ENV.fetch('HAUSHALTSBUCH_URL', 'http://localhost:9080/hhb')

    # TODO Deal with trailing slashes in HAUSHALTSBUCH_URL

    driver.navigate.to("#{base_url}#{uri}")
  end

  def navigate_home
    navigate_to('')
  end

  def create(fields = {})
    navigate_to '/new'

    driver.first(name: 'srcDst').send_keys(value(:srcDst, fields))
    driver.first(name: 'description').send_keys(value(:description, fields))
    driver.first(name: 'value').send_keys(42)
    driver.first(name: 'category').send_keys(value(:category, fields))
    driver.first(name: 'paymentType').send_keys(value(:paymentType, fields))

    driver.first(id: 'new').submit
  end

  def lookup(id)
    navigate_to '/lookup'

    lookup_form = driver.first(id: 'lookup')
    assert(lookup_form.displayed?)
    input = lookup_form.first(name: 'id')
    assert(input.displayed?)
    input.send_keys(id)
    input.submit
  end

  def delete
    delete_button = driver.first(xpath: '//input[@name="id"]')
    delete_button.submit

    message = driver.first(xpath: '//*[@id="message"]')
    assert(message.displayed?)
    assert_includes(message.text, 'gelöscht')
  end

  def sample
    navigate_to '/lookup'
    candidates = driver.all(xpath: '//*[@id="known-ids"]/option')
    assert(candidates.any?)

    candidates.sample['value']
  end
end
