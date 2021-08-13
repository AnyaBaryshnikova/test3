package org.example.framework.pages;

import org.example.framework.managers.DriverManager;
import org.example.framework.managers.PageManager;
import org.example.framework.managers.TestPropManager;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BasePage {

    @FindBy(xpath = "//span[@class='cart-link__price']")
    WebElement cartPrice;

    //поиск по сайту
    @FindBy(xpath = "//nav[@class='header-bottom slide']//input")
    private WebElement searchLine;

    //кнопка поиск
    @FindBy(xpath = "//nav[@class='header-bottom slide']//form//span[2]")
    private WebElement searchButton;


    protected final DriverManager driverManager = DriverManager.getDriverManager();

    protected PageManager pageManager = PageManager.getPageManager();

    protected Actions action = new Actions(driverManager.getDriver());

    protected JavascriptExecutor js = (JavascriptExecutor) driverManager.getDriver();

    protected WebDriverWait wait = new WebDriverWait(driverManager.getDriver(), 10, 1000);

    private final TestPropManager props = TestPropManager.getTestPropManager();


    public BasePage() {
        PageFactory.initElements(driverManager.getDriver(), this);
    }

    /**
     * Явное ожидание состояния clickable элемента
     *
     * @param element - веб-элемент который требует проверки clickable
     * @return WebElement - возвращаем тот же веб элемент что был передан в функцию
     */
    protected WebElement waitUtilElementToBeClickable(WebElement element) {
        return wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    /**
     * Явное ожидание того что элемент станет видимым
     *
     * @param element - веб элемент который мы ожидаем что будет  виден на странице
     */
    protected WebElement waitUtilElementToBeVisible(WebElement element) {
        return wait.until(ExpectedConditions.visibilityOf(element));
    }

    /**
     * Явное ожидание того что цена в корзине измениласть
     *
     * @param price - текущая цена корзины
     */
    protected void waitUtilPriceChanged(double price) {
        while(getCartPrice() == price)
        {
            try {
                wait(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    //Найти товар и перейти на страницу с результатами поиска
    public SearchResultsPage searchForItem(String itemName){

        searchLine.click();
        searchLine.sendKeys(itemName);
        searchButton.click();

        return pageManager.getSearchResultsPage();
    }
    public double getCartPrice(){
        return Double.parseDouble(cartPrice.getText().replaceAll("[^\\d.]", ""));
    }
}
