/*
 * @author William Fiset, Jonathan Whitaker
 * Tick Attack milestone #3
 * Object Oriented Design - COMP 3721
 */

import static org.junit.Assert.*;
import org.junit.*;

public class ViewTests {

  @Test(expected=Exception.class)
  public void testIllegalHeaderViewCreation() {
    new HeaderView(null);
  }

  @Test
  public void testViewCreation() {
    
    IView view = new View();
    IView headerView = new HeaderView("A Header");
    IView announcementView = new AnnouncementView();

  }

  @Test
  public void testDisplay() {
    
    IView view = new View();
    IView headerView = new HeaderView("A Header");
    IView announcementView = new AnnouncementView();

    view.display(null);
    headerView.display(null);
    announcementView.display(null );

    view.display("Hello!\n");
    headerView.display("headerView!\n");
    announcementView.display("announcementView!");

  }

}

