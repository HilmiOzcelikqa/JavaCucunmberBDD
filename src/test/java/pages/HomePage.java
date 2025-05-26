package pages;


import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.CommonMethods;
import java.util.List;


public class HomePage extends CommonMethods {
    @FindBy(id = "searchData")
    public WebElement productSearch;
    @FindBy(id="minPrice")
    public WebElement filterRangeMin;
    @FindBy(id = "maxPrice")
    public WebElement filterRangeMax;
    @FindBy(id = "priceSearchButton")
    public WebElement priceFilterButton;

    // Diğer Mağazalar tab'ı
    @FindBy(xpath = "//div[contains(@class, 'tab')]//a[contains(text(), 'Diğer Mağazalar')] | //div[contains(@class, 'tabPanel')]//a[contains(text(), 'Diğer Mağazalar')] | //a[@href='#allSellers']")
    public WebElement otherSellersTab;

    // Ürün listesi locator'ları
    @FindBy(xpath = "//li[contains(@class, 'column')]")
    public List<WebElement> filteredProducts;

    @FindBy(xpath = "//*[@id='unf-sell']/div[2]/div/div/div[4]/div/a")
    public WebElement addToCart;
    @FindBy(xpath = "//*[@id='unf-sell']/div[2]/div/div/div[4]/div/div[1]/div[2]/div[2]/a")
    public WebElement add;

    // Satıcı bölümü elementleri
    @FindBy(xpath = "//div[contains(@class, 'unf-sell')]")
    public List<WebElement> sellerContainers;

    @FindBy(xpath = "//*[@id='unf-sell']/div[2]/div[1]/div/div[1]/div/div")
    public List<WebElement> sellerRatings;



    @FindBy(xpath = "//*[@id='unf-sell']/div[2]/div/div/div[1]/div/a")
    public List<WebElement> sellerNames;


    // Sepet işlemleri için locator'lar
    @FindBy(xpath = "//*[@id='header']/div/div/div/div[2]/div[4]/a")
    public WebElement goToCartButton;





    @FindBy(xpath = "//h1[@class='proName'] | //h1[contains(@class, 'title')] | //h1[contains(@class, 'pro-title')]")
    public WebElement productDetailName;

    @FindBy(xpath = "//*[@id='newCheckout']/div/div[1]/div[1]/div[3]/section/table[2]/tbody/tr/td[1]/div[3]/div[1]/a")
    public WebElement cartProductName;


    public HomePage() {
        PageFactory.initElements(driver,this);
    }
}
