package org.example.framework.pages;

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
    public double choseWarranty(int years){

        double currentPrice = getCartPrice();
        if(years > 2 | years < 0)
            years = 0;

        warranty.click();
        waitUtilElementToBeVisible(warrantyBox);

        String choseWarranty = String.format("//div[@class = 'product-warranty__items']//label[%s]/input", years + 1);
        WebElement warrantyRadio = driverManager.getDriver().findElement(By.xpath(choseWarranty));
        warrantyRadio.click();

        WebElement price = warrantyRadio.findElement(By.xpath("/../span[@class='product-warranty__price']"));
        waitUtilPriceChanged(currentPrice);

        String stringPrice = price.getText();
        stringPrice = stringPrice.replaceAll("[^\\d.]", "");
        double tprice = Double.parseDouble(stringPrice);

        return tprice;
    }

    /**
     * Купить товар
     * @return возвращаем ту же страничку
     */
    public ItemCardPage buyItem(){

        buyBtn.click();
        waitUtilElementToBeVisible(driverManager.getDriver().findElement(By.xpath("//span[@class='cart-link__price']")));
        return this;
    }
}
