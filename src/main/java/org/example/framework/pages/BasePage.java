package org.example.framework.pages;

import io.qameta.allure.Step;
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

    @FindBy(xpath = "//span[@class='cart-link__price']")
    private WebElement cartBtn;

    //сумма товаров в корзине (значок на корзине)
    @FindBy(xpath = "//span[@class='cart-link__badge']")
    WebElement cartSum;

    protected static int countItems = 0;


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
     * Поиск товара через строку поиска
     * @param itemName название товара
     * @return возвращаем страничку с результатими поиска
     */
    @Step("Ищем товар '{itemName}' в строке поиска")
    public SearchResultsPage searchForItem(String itemName){

        searchLine.click();
        searchLine.sendKeys(itemName);
        searchButton.click();

        return pageManager.getSearchResultsPage();
    }

    @Step("Получаем цену корзины")
    public double getCartPrice(){
        return Double.parseDouble(cartPrice.getText().replaceAll("[^\\d.]", ""));
    }

    /**
     * Переход в корзину
     * @return возвращаем страничку корзины
     */
    @Step("Переходим в корзину")
    public CartPage redirectToCartPage(){
        waitUtilElementToBeClickable(cartBtn);
        cartBtn.click();
        return pageManager.getCartPage();

    }

    protected WebElement scrollToElementJs(WebElement element) {
        js.executeScript("arguments[0].scrollIntoView();", element);
        return element;
    }

    public WebElement scrollWithOffset(WebElement element, int x, int y) {
        String code = "window.scroll(" + (element.getLocation().x + x) + ","
                + (element.getLocation().y + y) + ");";
        ((JavascriptExecutor) driverManager.getDriver()).executeScript(code, element, x, y);
        return element;
    }
}
