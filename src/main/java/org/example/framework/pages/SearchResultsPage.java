package org.example.framework.pages;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class SearchResultsPage extends BasePage {

    @FindBy(xpath = "//h1")
    WebElement title;

    /**
     * проверка того, что открылась страничка с резулььаьами поиска
     * @return возвращаем ту же странику
     */
    public SearchResultsPage checkSearchPageOpen(){
        waitUtilElementToBeVisible(title);
        Assert.assertTrue("Заголовки не соответсвуют", title.getText().contains("Найдено:"));
        return this;
    }


    /**
     * Найти необходимы товар на странице и кликнуть на него
     * @param itemName Название товара
     * @return страничка -- карточка товара
     */
    public ItemCardPage redirectToItemPage(String itemName){

        String elementXPath = String.format("//span[contains(text(), '%s')]", itemName);
        WebElement itemButton = driverManager.getDriver().findElement(By.xpath(elementXPath));
        itemButton.click();

        return pageManager.getItemCardPage();
    }
}
