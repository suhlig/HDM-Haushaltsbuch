class AcceptanceTest < MiniTest::Test
  attr_reader :driver

  def setup
    @driver = Selenium::WebDriver.for :chrome
  end

  def teardown
    driver.quit unless failure
  end

  def navigate_to(path)
    base = ENV.fetch('HAUSHALTSBUCH_URL', 'http://localhost:9080').chomp('/')
    url = "#{base}#{path}"
    driver.navigate.to(url)
  end

  def navigate_home
    navigate_to('')
  end

  def create(fields = {})
    navigate_to '/entries/new'

    fill(:srcDst, fields)
    fill(:description, fields)
    fill(:value, 42)
    fill(:category, fields)
    fill(:paymentType, fields)

    driver.first(id: 'new').submit

    id = driver.first(xpath: '//*[@class="id"]')
    assert(id.displayed?)
    id.text
  end

  def lookup(id)
    navigate_to '/entries/lookup'

    lookup_form = driver.first(id: 'lookup')
    assert(lookup_form.displayed?)
    input = lookup_form.first(name: 'id')
    assert(input.displayed?)
    input.send_keys(id)
    input.submit
  end

  def delete(id = nil)
    if id.nil?
      # assume we are already on the entry's page
    else
      lookup(id)
    end

    delete_button = driver.first(xpath: '//input[@name="id"]')
    delete_button.submit

    message = driver.first(xpath: '//*[@id="message"]')
    assert(message.displayed?)
    assert_includes(message.text, 'gelöscht')
  end

  def sample
    navigate_to '/entries/lookup'
    candidates = driver.all(xpath: '//*[@id="known-ids"]/option')
    assert(candidates.any?)

    candidates.sample['value']
  end

  private

  def fill(name, overrides)
    value = if overrides.respond_to?(:fetch)
              overrides.fetch(name, "acceptance test #{name}")
            else
              overrides
            end

    driver.first(name: name).send_keys(value)
  end
end
