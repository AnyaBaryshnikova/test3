package org.example.framework.managers;

import io.qameta.allure.Step;
import org.example.framework.pages.BasePage;
import org.example.framework.pages.CartPage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MainPage extends BasePage {
    public MainPage() {
    }

    @FindBy(xpath = "//a[@class='btn btn-additional']")
    WebElement cityBtn;
    @Step("Выбираем город")
    public MainPage closeCity(){

        cityBtn.click();
        return pageManager.getMainPage();
    }



}
