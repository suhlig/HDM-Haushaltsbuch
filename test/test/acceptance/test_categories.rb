require 'helpers'

#
# Test the 'categories' page
#
class TestCategories < AcceptanceTest
  def setup
    super
    @categories = ['testcat one', 'testcat two', 'testcat three']
    @ids = create_categories(@categories)
  end

  def teardown
    delete_categories(@ids) unless failure
    super
  end

  def test_categories
    navigate_to '/categories/all'

    @categories.each do |name|
      category = driver.first(xpath: "//ul/li/a[text() = '#{name}']")
      assert(category.displayed?)
    end
  end
end
