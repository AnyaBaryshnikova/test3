package org.example.framework.basetest;


import org.example.framework.managers.DriverManager;
import org.example.framework.managers.InitManager;
import org.example.framework.managers.PageManager;
import org.example.framework.managers.TestPropManager;
import org.example.framework.utils.MyAllureListner;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.example.framework.utils.Constants.BASE_URL;

@ExtendWith(MyAllureListner.class)
public class BaseTest {

    /**
     * Менеджер страничек
     */
    protected PageManager app = PageManager.getPageManager();

    /**
     * Менеджер WebDriver
     *
     */
    private final DriverManager driverManager = DriverManager.getDriverManager();

    @BeforeClass
    public static void beforeAll() {
        InitManager.initFramework();
    }

    @Before
    public void beforeEach() {
        driverManager.getDriver().get(TestPropManager.getTestPropManager().getProperty(BASE_URL));
    }

    @AfterClass
    public static void afterAll() {
        InitManager.quitFramework();
    }
}
