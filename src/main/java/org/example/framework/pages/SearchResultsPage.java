package org.example.framework.pages;

import io.qameta.allure.Step;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;


public class SearchResultsPage extends BasePage {

    @FindBy(xpath = "//h1")
    WebElement title;

    /**
     * проверка того, что открылась страничка с резулььаьами поиска
     * @return возвращаем ту же странику
     */
    @Step("Проверяем, что оказались на странице с результатами поиска")
    public SearchResultsPage checkSearchPageOpen(){
        waitUtilElementToBeVisible(title);
        Assert.assertTrue("Мы не на странице с результатами поиска", title.getText().contains("Найдено:"));
        return this;
    }


    /**
     * Найти необходимы товар на странице и кликнуть на него
     * @param itemName Название товара
     * @return страничка -- карточка товара
     */
    @Step("Ищем на странице с результатами поиска необходимый товар из всех представленных и кликаем на него")
    public ItemCardPage redirectToItemPage(String itemName){

        String elementXPath = String.format("//span[contains(text(), '%s')]", itemName);
        WebElement itemButton = driverManager.getDriver().findElement(By.xpath(elementXPath));
        itemButton.click();

        return pageManager.getItemCardPage();
    }
}
