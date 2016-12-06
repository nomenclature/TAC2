public class PlayerTests {

  @Test public void testPlayerIsAlive() {
    Player p = new Player();
    assertTrue(p.isAlive());
    
    p.obtainHealth(0-(p.getHealth()+1));
    assertFalse(p.isAlive());

  }

  @Test public void testPlayerMoney() {
    Player p = new Player();
    assertTrue(cash >= 0);
    p.obtainMoney(-cash);
    assertEquals(p.getMoney(), 0);
  }

  @Test public void testGetItems() {
    Player p = new Player();
    Item item = new Item(0, "Test Object", "for testing purposes");
    p.obtainItem(item);
    assertTrue(p.hasItem(0));
    assertFalse(p.hasItem(1));
  }

  @Test public void testTicks() {
    Player p = new Player();

    p.setHasTicks(false);
    assertFalse(p.hasTicks());
    p.setHasTicks(true);
    assertTrue(p.hasTicks());
  }
}

