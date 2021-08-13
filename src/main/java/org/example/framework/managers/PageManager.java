package org.example.framework.managers;

import org.example.framework.pages.CartPage;
import org.example.framework.pages.ItemCardPage;
import org.example.framework.pages.SearchResultsPage;

public class PageManager {


    private static PageManager pageManager;


    private SearchResultsPage searchResultsPage;
    private ItemCardPage itemCardPage;
    private MainPage mainPage;
    private CartPage cartPage;

    public static PageManager getPageManager() {
        if (pageManager == null) {
            pageManager = new PageManager();
        }
        return pageManager;
    }

    private PageManager() {
    }
    public MainPage getMainPage() {
        if(mainPage == null)
            mainPage = new MainPage();
        return mainPage;
    }


    public SearchResultsPage getSearchResultsPage() {
        if(searchResultsPage == null)
            searchResultsPage = new SearchResultsPage();
        return searchResultsPage;
    }

    public ItemCardPage getItemCardPage() {
        if(itemCardPage == null)
            itemCardPage = new ItemCardPage();
        return itemCardPage;
    }

    public CartPage getCartPage() {
        if(cartPage == null)
            cartPage = new CartPage();
        return cartPage;
    }
}