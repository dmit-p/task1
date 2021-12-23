package task1;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class MethodInterception {

    @Test
    public void annotationValue() {
        MainPage mainPage = createPage(MainPage.class);
        System.out.println(MainPage.class);
        assertNotNull(mainPage);
        assertEquals(mainPage.buttonSearch(), ".//*[@test-attr='button_search']");
        assertEquals(mainPage.textInputSearch(), ".//*[@test-attr='input_search']");
    }
    
    private MainPage createPage(Class clazz) {
    	
        class Handler implements InvocationHandler {
            public Object invoke(Object proxy, Method method, Object[] args)
                    throws IllegalAccessException, IllegalArgumentException,
                    InvocationTargetException {
                Selector annotation = method.getAnnotation(Selector.class);
                return (annotation != null) ? annotation.xpath() : null;
            }
        }
    	Handler handler = new Handler();
    	MainPage mainPage = (MainPage) Proxy.newProxyInstance(clazz.getClassLoader(), new Class[] { clazz }, handler); 			
		return mainPage;
    }
}
