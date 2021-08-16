package org.example.framework.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class ItemCardPage extends BasePage {


    // Цена товара
    @FindBy(xpath = "//div[@class='product-buy product-buy_one-line']//div[@class='product-buy__price']")
    private WebElement itemPrice;

    @FindBy(xpath = "//div[contains(text(),'Гарантия')]")
    private WebElement warranty;

    @FindBy(xpath = "//div[@class = 'product-warranty__items']")
    private WebElement warrantyBox;

    @FindBy(xpath = "//div[@class='product-card-top__buy']//button[contains(text(), 'Купить')]")
    private WebElement buyBtn;

    /**
     * Получить цену товара
     * @return цена
     */
    @Step("Получаем цену товара со страницы - карточка товара")
    public double getItemPrice(){
        //waitUtilElementToBeVisible(itemPrice);
        double price = 0;
        String stringPrice = itemPrice.getText();
        stringPrice = stringPrice.replaceAll("[^\\d.]", "");
        price = Double.parseDouble(stringPrice);
        return price;
    }

    /**
     * Выбрать гарантию
     * @param years количество лет дополнительной гарантии (0 -- без дополнительно гарантии.
     *              Если years больше двух то дополнительная гарантия не выбирается)
     * @return  цена гарантии
     */
    @Step("Выбираем для товара дополнительную гарантию: '{years + 1}' года")
    public double choseWarranty(int years){

        if(years > 2 | years < 0)
            years = 0;

        warranty.click();
        waitUtilElementToBeVisible(warrantyBox);

        String choseWarranty = String.format("//div[@class = 'product-warranty__items']//label[%s]", years + 1);
        WebElement warrantyRadio = driverManager.getDriver().findElement(By.xpath(choseWarranty));
        waitUtilElementToBeClickable(warrantyRadio).click();

        WebElement price = warrantyRadio.findElement(By.xpath(".//span[@class='product-warranty__price']"));


        String stringPrice = price.getText();
        stringPrice = stringPrice.replaceAll("[^\\d.]", "");

        return Double.parseDouble(stringPrice);
    }

    /**
     * Купить товар
     * @return возвращаем ту же страничку
     */
    @Step("Нажимаем кнопочку купить товар на странице - карточка товара")
    public ItemCardPage buyItem(){

        buyBtn.click();
        ++countItems;

        waitUtilElementToBeVisible(cartSum);
        wait.until(ExpectedConditions.textToBePresentInElement(cartSum, "" + countItems));
        return this;
    }
}
