/*
 * @author William Fiset, Jonathan Whitaker
 * Tick Attack milestone #3
 * Object Oriented Design - COMP 3721
 */

import java.util.*;

public class GameController {

  private Player player;
  private IView view, headerView, inputView, announcementView;
  private QuestController questController;
  private InventoryController inventoryController;
  private BookOfPotions bookOfPotions;
  private int numQuestCompleted = 0;

  private static final int TICK_DAMAGE = -175;

  public GameController() {
    startGame();
  }

  public void resetGame() {

    player = new Player();
    questController = new QuestController(player);
    inventoryController = new InventoryController(player);
    bookOfPotions = new BookOfPotions(player);
    view = new View();
    headerView = new HeaderView("MENU: ");
    inputView = new HeaderView("ENTER QUEST ID: ");
    announcementView = new AnnouncementView();

  }

  public void startGame() {
    
    resetGame();
    displayIntro();
    
    // Kickstart game
    while (player.isAlive()) {
  
      // View gets input
      String userInput = headerView.readLine();

      // Game controller processes input
      executeUserCommand(userInput);

    }
    
    announcementView.display("you have died. game over.");

  }

  private void displayIntro() {
    announcementView.display("Welcome to Tick Attack!");
    headerView.display("Use one of the following commands to navigate through the game\n");
    displayHelp();
  }

  public void executeUserCommand(String command) {
    
    if (command == null) return;
    command = command.trim().toLowerCase();
    if (command.equals("\n") || command.equals("")) return;
    
    if (command.equals("help")) {

      displayHelp();

    } else if (command.equals("sell all")) {

      int totalValue = player.sellAllItems();
      if (totalValue != 0) {
        headerView.display("You sold all your items for a total value of " + totalValue + "$\n");
      }

    } else if (command.equals("use all")) {

      int totalValue = player.useAllItems();
      if (totalValue > 0) {
        headerView.display("You used all your items for a total health boost of " + totalValue + " hp\n");
      }
      else
        headerView.display("Item InnKeys is a quest item and cannot be used or sold\n");


    } else if (command.equals("items") || command.equals("inventory")) {

      inventoryController.invoke();

    } else if (command.equals("potions") || command.equals("book of potions")) {

      bookOfPotions.invoke();

    } else if (command.equals("reset") || command.equals("restart")) {

      resetGame();
      headerView.display("Game has been reset!\n\n");

    } else if (command.equals("exit") || command.equals("quit")) {
    	headerView.display("Goodbye!");
      quitGame();

    // Display the player status
    } else if (command.equals("status")) {
      
      displayPlayerStatus();

    } else if (command.equals("quests") || command.equals("quest")) {
      
      // Display all the available quests and their names
      selectQuest();

    } else if ( command.equals("tickcheck") ) {

      doTickCheck();

    } else {
      headerView.display("Unknown command: '" + command + "'\n");
    }
  }

  // Prints all available quests displaying each of the quests id numbers
  private boolean displayAvailbleQuests() {

    Set <Integer> questIds = questController.getVisitableQuests();
    if (questIds.size() == 0) return false;

    view.display("\nAVAILABLE QUESTS:\n");
    for (Integer id : questIds)
      view.display("QUEST " + id + " : " + questController.getQuestName(id) + "\n" );
    view.display("\n");
    headerView.display("Select one of the quests by typing the quest number\n");

    return true;
  }

  public void selectQuest() {

    if (displayAvailbleQuests()) {
      String line = inputView.readLine().replaceAll("[\"')]", "").trim();
      try {

        int id = Integer.parseInt(line);

        headerView.display("You selected quest #" + id + "\n");
        if (questController.hasQuest(id)) {
          if (questController.questIsVisitable(id)) {
            
            checkForTicks();
            if (player.isAlive()) {
              questController.playQuest(id);
              numQuestCompleted++;
            } else return;
            player.setDidTickCheck(false);

          } else {
            headerView.display("Quest " +id+ " has not been unlocked yet!\n");
            headerView.display("\n");
          }
        } else {
          headerView.display("Quest " +id+ " does not exist.\n");
        }

      } catch (NumberFormatException e) {
        headerView.display("Error. Please type in a number.\n");
      }
    } else {
      headerView.display("No quests are available, sorry! Make sure there are *.quest files in the quests folder.\n");
    }

  }

  private void doTickCheck() {
    
    player.setDidTickCheck(true);
    headerView.display("You did a tickcheck and removed any ticks that were found on your body.\n");

  }

  private void checkForTicks() {

    if (numQuestCompleted > 0) {
      if (!player.didTickCheck()) {

        headerView.display("You forgot to do a tick check! (type 'tickcheck' before starting any quest)\n");

        if (player.hasTicks()) {
          headerView.display("Ticks were found on your body! You lose "+Math.abs(TICK_DAMAGE)+" ♥\n");
          player.obtainHealth(TICK_DAMAGE);
          displayPlayerStatus();
        } else {
          headerView.display("Luckily no Ticks were found on your body, but never forget to do tickchecks.\n");
        }

      } else {
        headerView.display("Good job! You performed a tickcheck after doing the last quest\n");
      }
    }

  }

  private void displayPlayerStatus() {
    view.display( player );
  }

  private void quitGame() {
    System.exit(0);
  }

  public void displayHelp() {
    headerView.display("'help'           - display this help menu\n");
    headerView.display("'exit/quit'      - exit game\n");
    headerView.display("'items'          - display the inventory of items you hold\n");
    headerView.display("'status'         - display player status\n");
    headerView.display("'reset'          - reset game\n");
    headerView.display("'tickcheck'      - perform a tickcheck (Do this after every quest!)\n");
    headerView.display("'quests'         - display and select a quest you wish to do\n");
    headerView.display("'sell all'       - sell all of your items on the black market\n");
    headerView.display("'use all'        - use all of your items for miscellaneous benefits\n");

  }

  public static void main(String[] args) {
    new GameController();
  }

}
