import org.junit.jupiter.api.BeforeEach;  
import org.junit.jupiter.api.Test; 
import org.mockito.Mockito;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.spy;

class RestaurantTest {
    Restaurant restaurant;
    LocalTime openingTime = LocalTime.parse("10:30:00");
    LocalTime closingTime = LocalTime.parse("22:00:00");
    List<Item> mockOrderedItems = new ArrayList<>();

    public void refactorCode_CreateRestaurant(){
        LocalTime openingTime = LocalTime.parse("10:30:00");
        LocalTime closingTime = LocalTime.parse("22:00:00");
        restaurant =new Restaurant("Amelie's cafe","Chennai",openingTime,closingTime);
        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);
    }

    //>>>>>>>>>>>>>>>>>>>>>>>>>OPEN/CLOSED<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    //-------FOR THE 2 TESTS BELOW, YOU MAY USE THE CONCEPT OF MOCKING, IF YOU RUN INTO ANY TROUBLE
    @Test
    public void is_restaurant_open_should_return_true_if_time_is_between_opening_and_closing_time(){
        Restaurant res = spy(new Restaurant("Amelie's cafe","Chennai",openingTime,closingTime));
        LocalTime betweenOpenClose = LocalTime.of(11,35,00);
        Mockito.when(res.getCurrentTime()).thenReturn(betweenOpenClose);
        assertTrue(res.isRestaurantOpen());
    }

    @Test
    public void is_restaurant_open_should_return_false_if_time_is_outside_opening_and_closing_time(){
        Restaurant res = spy(new Restaurant("Amelie's cafe","Chennai",openingTime,closingTime));
        LocalTime outsideOpenClose = LocalTime.of(23, 45, 00);
        Mockito.when(res.getCurrentTime()).thenReturn(outsideOpenClose);
        assertFalse(res.isRestaurantOpen());
    }

    //<<<<<<<<<<<<<<<<<<<<<<<<<OPEN/CLOSED>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //>>>>>>>>>>>>>>>>>>>>>>>>>>>MENU<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void adding_item_to_menu_should_increase_menu_size_by_1(){
        refactorCode_CreateRestaurant();
        int initialMenuSize = restaurant.getMenu().size();
        restaurant.addToMenu("Sizzling brownie",319);
        assertEquals(initialMenuSize+1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_from_menu_should_decrease_menu_size_by_1() throws itemNotFoundException {
        refactorCode_CreateRestaurant();
        int initialMenuSize = restaurant.getMenu().size();
        restaurant.removeFromMenu("Vegetable lasagne");
        assertEquals(initialMenuSize-1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_that_does_not_exist_should_throw_exception() {
        refactorCode_CreateRestaurant();
        assertThrows(itemNotFoundException.class,
                ()->restaurant.removeFromMenu("French fries"));
    }
    //<<<<<<<<<<<<<<<<<<<<<<<MENU>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    //<<<<<<<<<<<<<<<<<<<<<<Order Items>>>>>>>>>>>>>>>>>>>>>
    @Test
    public void selected_items_total_cost(){
        refactorCode_CreateRestaurant();
        mockOrderedItems = restaurant.getMenu();
        int totalCost = restaurant.getTotalOrderCost(mockOrderedItems);
        assertEquals(388, totalCost);
    }

    @Test
    public void removed_items_revised_cost(){
        refactorCode_CreateRestaurant();
        mockOrderedItems = restaurant.getMenu();
        int totalCost = restaurant.getTotalOrderCost(mockOrderedItems);
        int deselectItem = mockOrderedItems.get(0).getPrice();
        assertEquals(269, totalCost - deselectItem);
    }

    //>>>>>>>>>>>>>>>>>>>>>>Order Items<<<<<<<<<<<<<<<<<<<<<<<
}