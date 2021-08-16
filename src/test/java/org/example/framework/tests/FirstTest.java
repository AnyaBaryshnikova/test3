package org.example.framework.tests;

import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import org.example.framework.Items.Item;
import org.example.framework.basetest.BaseTest;
import org.junit.Assert;
import org.junit.Test;

public class FirstTest extends BaseTest {

     @Test
     @DisplayName("Проверка dns-shop")
     public void startTest() {
          /*
          Переходим на главную страничку
          ищем робот пылесос
          переходим на страничку с результатими поиска
          находим нужный пылесос
          переходим на страничку товара
          получаем цену
           */
          double vacumCleanerPrice = app.getMainPage()
                  .closeCity()
                  .searchForItem("Робот-пылесос")
                  .checkSearchPageOpen()
                  .redirectToItemPage("Пылесос-робот Xiaomi Mi Robot Vacuum Mop SKV4093GL белый")
                  .getItemPrice();

          // Создаем экземпляр класса Item
          Item vacuumCleaner = new Item("Пылесос-робот Xiaomi Mi Robot Vacuum Mop SKV4093GL белый", vacumCleanerPrice);


          // Добавляем гарантию
          double warrantyVacuumPrice = app.getItemCardPage().choseWarranty(2);

          app.getItemCardPage()
                  .buyItem();

          vacuumCleaner.setAmount(1);
          vacuumCleaner.setWarranty(2);
          vacuumCleaner.setWarrantyPrice(warrantyVacuumPrice);

          /*
          ищем игру detroit
          переходим на страничку с результатами поиска
          переходим в карточку товара
          получаем цену товара
           */
          double detroitPrice = app.getItemCardPage()
                  .searchForItem("Detroit")
                  .checkSearchPageOpen()
                  .redirectToItemPage("Игра Detroit: Стать человеком (PS4)")
                  .getItemPrice();

          Item detroitItem = new Item("Игра Detroit: Стать человеком (PS4)", detroitPrice);
          detroitItem.setAmount(1);

          // покупаем товар и получаем цену корзины
          double cartPrice = app.getItemCardPage()
                  .buyItem()
                  .getCartPrice();
          // Переходим на страничку корзины
          app.getItemCardPage().redirectToCartPage();

          double price = vacuumCleaner.getPrice() * vacuumCleaner.getAmount() + vacuumCleaner.getWarrantyPrice()
                  +  detroitItem.getPrice() * detroitItem.getAmount() + detroitItem.getWarrantyPrice();
          Assert.assertEquals("Сумма в корзине и сумма покупок не равны", cartPrice, price, 0.0);
          Assert.assertEquals("Сумма в корзине и сумма покупок не равны", cartPrice, price, 0.0);

          int vacuumWarranty = app.getCartPage()
                  .checkCartPageOpen()
                  .getItemWarranty(vacuumCleaner.getName());
          Assert.assertEquals("Гарантии не совпадают", 2, vacuumWarranty);

          double cleanerPrice = app.getCartPage()
                  .getItemPrice(vacuumCleaner.getName());
          Assert.assertEquals("цена " + vacuumCleaner.getName() + " в корзине не совпадает", cleanerPrice, vacuumCleaner.getPrice(), 0.0);

          double detPrice = app.getCartPage()
                  .getItemPrice(detroitItem.getName());
          Assert.assertEquals("цена " + detroitItem.getName() + " в корзине не совпадает", detPrice, detroitItem.getPrice(), 0.0);

          double newCartPrice = app.getCartPage()
                  .deleteItem(detroitItem.getName())
                  .checkCartPageOpen()
                  .getCartPrice();
          Assert.assertTrue("Цены не совпадают", newCartPrice == cartPrice - detroitItem.getPrice());

          double threeVacuumPrice = app.getCartPage()
                  .addItem(vacuumCleaner.getName())
                  .checkCartPageOpen()
                  .addItem(vacuumCleaner.getName())
                  .checkCartPageOpen()
                  .getCartPrice();

          vacuumCleaner.addItem();
          vacuumCleaner.addItem();

          Assert.assertTrue("цены не совпадают", threeVacuumPrice == vacuumCleaner.getAmount() * (vacuumCleaner.getPrice() + vacuumCleaner.getWarrantyPrice()));

          double fullCartPrice = app.getCartPage()
                  .restoreLastRemoved().getCartPrice();
          Assert.assertTrue("цены не совпадают", fullCartPrice == detroitItem.getPrice() + 3 * (vacuumCleaner.getPrice() + vacuumCleaner.getWarrantyPrice()));

     }
}
