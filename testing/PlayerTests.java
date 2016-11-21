/*
 * @author William Fiset, Jonathan Whitaker
 * Tick Attack milestone #3
 * Object Oriented Design - COMP 3721
 */

import static org.junit.Assert.*;
import org.junit.*;

public class PlayerTests {

  @Test
  public void testPlayerCreation() {
    new Player();
  }

  @Test
  public void testPlayerAlive() {
    
    Player p = new Player();
    assertTrue(p.isAlive());
    
    p.obtainHealth(Integer.MIN_VALUE);
    assertFalse(p.isAlive());

  }

  @Test
  public void testPlayerMoney() {
    
    Player p = new Player();
    long cash = p.getMoney();

    assertTrue(cash >= 0);
    p.obtainMoney(-cash);

    assertEquals(p.getMoney(), 0);
    
  }


  @Test
  public void testItemsAquiring() {
    
    Player p = new Player();
    Item torch = new Item(0, "Torch", "Shines in the dark");
    p.obtainItem(torch);

    assertTrue(p.hasItem(0));
    assertFalse(p.hasItem(-1));
    assertFalse(p.hasItem(1));
    
  }


  @Test
  public void testPlayerTicks() {
    
    Player p = new Player();

    p.setHasTicks(false);
    assertFalse(p.hasTicks());
    p.setHasTicks(true);
    assertTrue(p.hasTicks());

    p.setDidTickCheck(false);
    assertFalse(p.didTickCheck());
    p.setDidTickCheck(true);
    assertTrue(p.didTickCheck());

    
  }

}

