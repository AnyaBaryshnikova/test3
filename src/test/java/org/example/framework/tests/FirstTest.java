package org.example.framework.tests;

import org.example.framework.Items.Item;
import org.example.framework.basetest.BaseTest;
import org.junit.Assert;
import org.junit.Test;

public class FirstTest extends BaseTest {

     @Test
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

          app.getItemCardPage()
                  .buyItem();
          // Добавляем гарантию
          double warrantyVacuumPrice = app.getItemCardPage().choseWarranty(2);

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

          // покупаем товар и пролучаем цену корзины
          double cartPrice = app.getItemCardPage()
                  .buyItem()
                  .getCartPrice();

          double price = vacuumCleaner.getPrice() * vacuumCleaner.getAmount() + vacuumCleaner.getWarrantyPrice()
                  +  detroitItem.getPrice() * detroitItem.getAmount() + detroitItem.getWarrantyPrice();
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
                  .getCartPrice();
          Assert.assertTrue("Цены не совпадают", newCartPrice == cartPrice - detroitItem.getPrice());

          double threeVacuumPrice = app.getCartPage()
                  .addItem(vacuumCleaner.getName())
                  .addItem(vacuumCleaner.getName())
                  .getCartPrice();

          Assert.assertTrue("цены не совпадают", threeVacuumPrice == 3 * (vacuumCleaner.getPrice() + vacuumCleaner.getWarranty()));

          double fullCartPrice = app.getCartPage()
                  .restoreLasRemoved().getCartPrice();
          Assert.assertTrue("цены не совпадают", fullCartPrice == detroitItem.getPrice() + 3 * (vacuumCleaner.getPrice() + vacuumCleaner.getWarranty()));

     }
}
