/*
 * @author William Fiset, Jonathan Whitaker
 * Tick Attack milestone #3
 * Object Oriented Design - COMP 3721
 */

import java.util.*;

public class InventoryController {

  private IView view, headerView;
  private Player player;

  public InventoryController (Player player) {
    this.player = player;
    view = new View();
    headerView = new HeaderView("INVENTORY: ");
  }

  public void invoke() {

    int count = 0;
    Iterator <Item> iter = player.getItemsIterator();

    while (iter.hasNext()) {
      Item item = iter.next();
      view.display( "\n");
      headerView.display( (count++) + ") " + item.getName() + "\n");
      headerView.display( item.getDescription() + "\n");
    }
    view.display( "\n");

  }

}

