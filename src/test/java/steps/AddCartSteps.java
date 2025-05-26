package steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import utils.CommonMethods;
import java.util.*;


public class AddCartSteps extends CommonMethods {
    private String selectedProductName;
    private String selectedSellerName;

    @Given("User searchs for product")
    public void userSearchsForProduct() throws InterruptedException {
        waitForClickability(homePage.productSearch);
        click(homePage.productSearch);
        sendText(homePage.productSearch,"cep telefonu");
        homePage.productSearch.sendKeys(Keys.ENTER);
        Thread.sleep(3000);
    }

    @And("User arrange price filter")
    public void userArrangePriceFilter() throws InterruptedException {
        waitForClickability(homePage.filterRangeMin);
        click(homePage.filterRangeMin);
        sendText(homePage.filterRangeMin,"15000");

        waitForClickability(homePage.filterRangeMax);
        click(homePage.filterRangeMax);
        sendText(homePage.filterRangeMax, "20000");

        waitForClickability(homePage.priceFilterButton);
        click(homePage.priceFilterButton);
        Thread.sleep(3000);
    }

    @And("User choose the product at the bottom of list and go to product details")
    public void userChooseTheProductAtTheBottomOfListAndGoToProductDetails() throws InterruptedException {
        try {

            getWait().until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector("li.column")));

            // Sayfanın en altına scroll yap
            scrollToBottom();
            Thread.sleep(2000);

            List<WebElement> products = homePage.filteredProducts;
            System.out.println("Bulunan toplam ürün sayısı: " + products.size());

            if (products.isEmpty()) {
                throw new RuntimeException("Ürün listesi boş!");
            }

            int startIndex = Math.max(0, products.size() - 4);
            List<WebElement> bottomProducts = products.subList(startIndex, products.size());
            System.out.println("En alt satırdaki ürün sayısı: " + bottomProducts.size());

            Random random = new Random();
            WebElement pickedProduct = bottomProducts.get(random.nextInt(bottomProducts.size()));

            WebElement productLink = pickedProduct.findElement(By.cssSelector("a.plink"));

            selectedProductName = productLink.getAttribute("title");
            if (selectedProductName == null || selectedProductName.isEmpty()) {
                WebElement nameElement = pickedProduct.findElement(By.cssSelector("h3.productName"));
                selectedProductName = nameElement.getText();
            }
            System.out.println("Seçilen ürün: " + selectedProductName);

            scrollElementToCenter(productLink);
            waitForClickability(productLink);
            click(productLink);

        } catch (Exception e) {
            System.out.println("Hata oluştu: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    public boolean findSellerSection() {
        try {
            System.out.println("Satıcı bölümü arama başladı...");

            // Sayfanın yüklenmesini bekle
            getWait();

            // Diğer Mağazalar tab'ı varsa tıkla
            try {
                if (homePage.otherSellersTab.isDisplayed()) {
                    System.out.println("Diğer Mağazalar tab'ı bulundu, tıklanıyor...");
                    waitForClickability(homePage.otherSellersTab);
                    click(homePage.otherSellersTab);
                    Thread.sleep(1000);
                }
            } catch (Exception e) {
                System.out.println("Diğer Mağazalar tab'ı bulunamadı, direkt satıcı listesi aranacak");
            }

            // Satıcı listesini kontrol et
            getWait().until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[contains(@class, 'unf-sell')]")));
            return !homePage.sellerContainers.isEmpty();

        } catch (Exception e) {
            System.out.println("Satıcı bölümü aranırken hata: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    @And("User choose the product which belongs to the worst rated seller and adds to the cart")
    public void userChooseTheProductWhichBelongsToTheWorstRatedSellerAndAddsToTheCart() throws InterruptedException {
        try {
            switchToNewWindow();
            Thread.sleep(2000);


            getWait().until(ExpectedConditions.visibilityOf(homePage.productDetailName));
            selectedProductName = homePage.productDetailName.getText().trim();
            System.out.println("Seçilen ürün: " + selectedProductName); // Ürün adını detay sayfasından al


            if (!findSellerSection()) {
                throw new RuntimeException("Satıcı bölümü bulunamadı!");
            }

            // Satıcı listesini al
            List<WebElement> sellers = homePage.sellerContainers;
            if (sellers.isEmpty()) {
                throw new RuntimeException("Satıcı listesi boş!");
            }

            System.out.println("Bulunan satıcı sayısı: " + sellers.size());// Satıcı bölümünü bul


            int selectedSellerIndex = 0;
            if (sellers.size() > 1) {
                double lowestRating = Double.MAX_VALUE;
                List<WebElement> ratings = homePage.sellerRatings;
                System.out.println("Bulunan satıcı puanı sayısı: " + ratings.size());
                // Tek satıcı varsa direkt onu seç, birden fazla varsa en düşük puanlıyı bul

                for (int i = 0; i < ratings.size(); i++) {
                    try {
                        String ratingText = ratings.get(i).getText().trim().replace(",", ".");
                        ratingText = ratingText.replaceAll("[^0-9.,]", "");
                        double ratingValue = Double.parseDouble(ratingText);

                        System.out.println("Satıcı " + (i+1) + " puanı: " + ratingValue);

                        if (ratingValue < lowestRating) {
                            lowestRating = ratingValue;
                            selectedSellerIndex = i;
                        }
                    } catch (Exception e) {
                        System.out.println("Satıcı " + (i+1) + " puanı işlenirken hata: " + e.getMessage());
                    }
                }
            }

            // Seçilen satıcının ismini  al
            List<WebElement> sellerNames = homePage.sellerNames;
            if (!sellerNames.isEmpty() && selectedSellerIndex < sellerNames.size()) {
                selectedSellerName = sellerNames.get(selectedSellerIndex).getText();
                System.out.println("Seçilen satıcı: " + selectedSellerName);
            }

            click(homePage.addToCart);
            click(homePage.add);
            click(homePage.goToCartButton);

        } catch (Exception e) {
            System.out.println("Satıcı seçimi ve sepete ekleme işleminde hata: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    @Then("User checks if the correct product added to the cart")
    public void userChecksIfTheCorrectProductAddedToTheCart() throws InterruptedException {
        try {
            // Sepetteki ürün adını al
            getWait().until(ExpectedConditions.visibilityOf(homePage.cartProductName));
            String cartProductName = homePage.cartProductName.getText().trim().toLowerCase();

            System.out.println("Sepetteki ürün adı: " + cartProductName);
            System.out.println("Beklenen ürün adı: " + selectedProductName);
            Assert.assertEquals(cartProductName.toLowerCase(),selectedProductName.toLowerCase());

            System.out.println("✅ Ürün başarıyla sepete eklendi. Satıcı: " + selectedSellerName);

        } catch (Exception e) {
            System.out.println("❌ Sepet kontrolünde hata: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }
}
