package org.example.framework.pages;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;


import java.util.List;

// Корзина
public class CartPage extends BasePage {

    @FindBy(xpath = "//div[@class='group-tabs']//span[@class='restore-last-removed']")
    WebElement restoreLastItem;         // кнопка чтобы вернуть удаленный товар

    @FindBy(xpath = "//h1[@class='cart-title']")
    WebElement title;

    /**
     * проверка того, что открылась корзина
     * @return возвращаем ту же странику
     */
    public CartPage checkCartPageOpen(){
        waitUtilElementToBeVisible(title);
        Assert.assertTrue("Заголовки не соответсвуют", title.getText().contains("Корзина"));
        return this;
    }


    /**
     * Увеличить количество товара в корзине на 1
     * @param itemName название товара
     * @return возвращаем ту же странику
     */
    public CartPage addItem(String itemName){
        String itemXpath = String
                .format("//a[contains(text(), '%s')]/../../../../../..//button[@class='count-buttons__button count-buttons__button_plus']/i"
                        , itemName);
        WebElement addItemBtn = driverManager.getDriver().findElement(By.xpath(itemXpath));
        addItemBtn.click();
        ++countItems;
        wait.until(ExpectedConditions.textToBePresentInElement(cartSum, countItems + ""));

        return this;
    }

    /**
     * удалить товар
     * @param itemName название товара
     * @return возвращаем ту же страничку
     */
    public CartPage deleteItem(String itemName){
        String itemXpath = String
                .format("//a[contains(text(), '%s')]/../../../../../..//button[contains(text(), 'Удалить')]", itemName);
        WebElement deleteBtn = driverManager.getDriver().findElement(By.xpath(itemXpath));
        deleteBtn.click();
        --countItems;
        wait.until(ExpectedConditions.textToBePresentInElement(cartSum, "" + countItems));


        return this;

    }

    /**
     * Получить количество лет дополнительной гарантии товара и ее цену
     * 0 -- без доп гарантии
     * 1 -- 1 год доп гарантии
     * 2 -- 2 года доп гарантии
     * @param itemName название товара
     * @return количество лет доп гарантии
     */
    public int getItemWarranty(String itemName){
        String itemXpath = String
                .format("//a[contains(text(), '%s')]/../../../../../..//div[contains(@class, 'additional-warranties-row__warranty')]/div/span",
                        itemName);

        List<WebElement> listRadios  = driverManager.getDriver().findElements(By.xpath(itemXpath));
        for(int i = 0; i < listRadios.size(); ++i)
        {
            if(listRadios.get(i).getAttribute("class").contains("checked"))
                return i;
        }
        return 0;
    }

    /**
     * Метод получения цены товара
     * @param itemName название товара
     * @return цена товара
     */
    public double getItemPrice(String itemName){
        String itemXpath = String
                .format("//a[contains(text(), '%s')]/../../../../..//span[@class='price__current']", itemName);
        WebElement price = driverManager.getDriver().findElement(By.xpath(itemXpath));

        return Double.parseDouble(price.getText().replaceAll("[^\\d.]", ""));
    }

    /**
     * Вернуть последний удаленный товар
     * @return возвращаем ту же страничку
     */
    public CartPage restoreLastRemoved(){

        scrollWithOffset(restoreLastItem, 0, -300).click();
        ++countItems;
        wait.until(ExpectedConditions.textToBePresentInElement(cartSum, ""  + countItems));

        return pageManager.getCartPage();
    }

}
