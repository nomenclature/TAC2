public class InventoryTest {
  @Test public void testAddItems() {

    Item item1 = new Item(1, "Test", "Test Object");
    Item item2 = new Item(2, "Test2", "Test Object 2: The test-ening");
    Item item3 = new Item(3, "Test3", "3 Object Test");

    Inventory inv = new Inventory();
    inv.add(item1, item2, item3);

    assertFalse(inv.contains(0));
    assertTrue(inv.contains(1));
    assertTrue(inv.contains(2));
    assertTrue(inv.contains(3));
    assertFalse(inv.contains(4));

  }

  @Test public void testGetItems() {

    Item item1 = new Item(1, "Test", "Test Object");
    Item item2 = new Item(2, "Test2", "Test Object 2: The test-ening");
    Item item3 = new Item(3, "Test3", "3 Object Test");
    Inventory inv = new Inventory();
    inv.add(item1, item2, item3);
    Item ti1 = inv.getItem(1);
    Item ti2 = inv.getItem(2);
    Item ti3 = inv.getItem(3);
    assertNotNull(ti1);
    assertNotNull(ti2);
    assertNotNull(ti3);
  }
}

