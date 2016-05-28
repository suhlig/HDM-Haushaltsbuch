require 'helpers'

#
# Test the 'new entry' page
#
class TestNew < AcceptanceTest
  def test_new
    create

    message = driver.first(xpath: '//*[@id="message"]')
    assert(message.displayed?)
    assert_includes(message.text, 'erfolgreich angelegt')

    id = driver.first(xpath: '//*[@class="id"]')
    assert(id.displayed?)

    lookup(id.text)
    delete
  end

  def test_umlauts
    umlauts = 'some umlauts: öäüß'
    create(description: umlauts)

    description = driver.first(css: '.description')
    assert(description.displayed?)
    assert_equal(umlauts, description.text)

    id = driver.first(xpath: '//*[@class="id"]')
    assert(id.displayed?)

    lookup(id.text)
    delete
  end

  def test_categories_suggested
    ids = create_categories(['cat one', 'cat two', 'cat three'])
    navigate_to '/entries/new'

    # emulate auto-completion with type and select
    ['cat', :arrow_down, :arrow_down, :arrow_down, :return].each do |key|
      driver.first(name: 'category').send_keys(key)
      sleep 0.1
    end

    # read back via JS because Selenium seems unable to get the current value
    js = 'return document.getElementById("entry_category").value'
    assert_equal('cat three', driver.script(js))

    delete_categories(ids)
  end

  private

  def create_categories(names)
    names.map do |name|
      create(category: name)
    end
  end

  def delete_categories(ids)
    ids.each do |id|
      delete(id)
    end
  end
end
