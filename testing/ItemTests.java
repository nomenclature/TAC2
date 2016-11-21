/*
 * @author William Fiset, Jonathan Whitaker
 * Tick Attack milestone #3
 * Object Oriented Design - COMP 3721
 */

import static org.junit.Assert.*;
import org.junit.*;
import java.util.*;

public class ItemTests {

  @Test
  public void testItemCreation() {
    new Item(0, "Torch", "Shines light");
  }

  @Test(expected=Exception.class)
  public void testIllegalItemCreation1() {
    new Item(0, null, "Shines light");
  }

  @Test(expected=Exception.class)
  public void testIllegalItemCreation2() {
    new Item(0, "Shines light", null);
  }

  @Test(expected=Exception.class)
  public void testIllegalItemCreation3() {
    new Item(0, null, null);
  }

  @Test
  public void testItemSort() {

    Item item1 = new Item(4, "B", "1");
    Item item2 = new Item(1, "A", "2");
    Item item3 = new Item(2, "D", "3");
    Item item4 = new Item(3, "C", "4");
    Item item5 = new Item(5, "E", "5");

    Item[] items = {item1, item2, item3, item4, item5 };
    Arrays.sort(items);

    // Array should be sorted by name
    assertEquals( items[0].getName(), "A" );
    assertEquals( items[1].getName(), "B" );
    assertEquals( items[2].getName(), "C" );
    assertEquals( items[3].getName(), "D" );
    assertEquals( items[4].getName(), "E" );

  }

}

