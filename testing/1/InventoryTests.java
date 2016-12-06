/*
 * @author William Fiset, Jonathan Whitaker
 * Tick Attack milestone #3
 * Object Oriented Design - COMP 3721
 */

import static org.junit.Assert.*;
import org.junit.*;

public class InventoryTests {

  // Create an inventory
  @Test public void testInventoryCreation() {
    new Inventory();
  }

  // test adding items to the inventory and check if they're actually in there
  @Test public void testAddingItems() {

    Item item1 = new Item(0, "Torch", "Shines light");
    Item item2 = new Item(1, "Apple", "tastes good");
    Item item3 = new Item(2, "glasscup", "can drink with this");

    Inventory inv = new Inventory();
    inv.add(item1, item2, item3);

    assertFalse(inv.contains(-1));
    assertTrue(inv.contains(0));
    assertTrue(inv.contains(1));
    assertTrue(inv.contains(2));
    assertFalse(inv.contains(3));

  }

  // Test whether or not we can actaully get items 
  // we placed inside out inventory
  @Test public void testGettingItems() {

    Item item1 = new Item(0, "Torch", "Shines light");
    Item item2 = new Item(1, "Apple", "tastes good");
    Item item3 = new Item(2, "glasscup", "can drink with this");

    Inventory inv = new Inventory();
    inv.add(item1, item2, item3);

    Item i1 = inv.getItem(0);
    Item i2 = inv.getItem(1);
    Item i3 = inv.getItem(2);

    assertNotNull(i1);
    assertNotNull(i2);
    assertNotNull(i3);

  }

}

