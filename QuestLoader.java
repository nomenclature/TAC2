import java.util.*;
import java.io.*;

class QuestLoader {


  private static final String QUESTS_FOLDER = "./quests";
  private static final String QUEST_FILE_ENDING = ".quest";  

  private Map <Integer, Item> itemMap = new HashMap<>();
  private Map <Integer, Graph> graphs = new HashMap<>();
  private Map <Integer, String> questNameMapping = new HashMap<>();

  private QuestLoader() {
    createGraphs();
  }

  private static QuestLoader instance = new QuestLoader();

  public static QuestLoader getInstance(){
    return instance;
  }

  public Map <Integer, Graph> getQuests() {
    return graphs;
  }

  public Map <Integer, String> getQuestNameMapping() {
    return questNameMapping;
  }

  public Map <Integer, Item> getItemMap() {
    return itemMap;
  }

  // This helper method removes the first token it finds in the string
  private String removeFirstToken(String str) {
    str = str.trim();
    int index = 0;
    for(;index < str.length(); index++)
      if (str.charAt(index) == ' ')
        break;
    return str.substring(index, str.length()).trim();
  }

  private File[] findQuestFiles () {
    
    // find all *.quest files in directory ./*.quests and
    // process each quest file one at a time and 
    File dir = new File(QUESTS_FOLDER);
    File [] files = dir.listFiles(new FilenameFilter() {
      @Override public boolean accept(File dir, String name) {
        return name.endsWith(QUEST_FILE_ENDING);
      }
    });

    return files;

  }

  private void createGraphs() {

    for (File questFile : findQuestFiles()) {
      try {

        Scanner sc = new Scanner(questFile);

        // grab four lines with quest id
        String questIdStr = removeFirstToken(sc.nextLine());
        String questNameStr = removeFirstToken(sc.nextLine());
        String numEventNodesStr = removeFirstToken(sc.nextLine());
        String numEdgesStr = removeFirstToken(sc.nextLine());
        String numItemsStr = removeFirstToken(sc.nextLine());

        sc.nextLine();

        int questId  = Integer.parseInt(questIdStr);
        int numNodes = Integer.parseInt(numEventNodesStr);
        int numEdges = Integer.parseInt(numEdgesStr);
        int numItems = Integer.parseInt(numItemsStr);

        questNameMapping.put(questId, questNameStr);

        Graph graph = new Graph(numNodes);
        String[] descriptions = new String[numNodes];

        // Read event node descriptions
        for(int i = 0; i < numNodes; i++) {
          String eventDescription = removeFirstToken(sc.nextLine());
          descriptions[i] = eventDescription;
        }

        sc.nextLine();

        // Read event benefits (money, health, itemdrops...)
        for (int nodeId = 0; nodeId < numNodes; nodeId++ ) {
          
          String line = removeFirstToken(sc.nextLine());
          if (!line.equals("")) {

            String[] split = line.split(" +");
            if (split.length % 2 != 0) {
              System.err.println("Error parsing file.");
              System.exit(0);
            }

            List <EventObtainable> obtainables = new LinkedList<>();

            for (int j = 0; j < split.length; j += 2) {
              
              String type = split[j];
              Integer value = Integer.valueOf( split[j+1] );

              if (type.equals("money")) {
                obtainables.add(new EventObtainable(EventType.MONEY, value));
              } else if (type.equals("health")) {
                obtainables.add(new EventObtainable(EventType.HEALTH, value));
              } else if (type.equals("item")) {
                obtainables.add(new EventObtainable(EventType.ITEM, value));
              } else if (type.equals("newquest")) {
                obtainables.add(new EventObtainable(EventType.QUEST_UNLOCK, value));
              }

            }

            EventNode node = new EventNode(descriptions[nodeId], obtainables);
            graph.addNode(nodeId, node);

          } else {

            EventNode node = new EventNode(descriptions[nodeId], new LinkedList<EventObtainable>());
            graph.addNode(nodeId, node);
          
          }
        }

        sc.nextLine();

        // Read graph edges
        for (int i = 0; i < numEdges; i++) {
          
          String[] split = sc.nextLine().split(" +");
          int from  = Integer.parseInt(split[0]);
          int to  = Integer.parseInt(split[2]);
          Fraction probablity = new Fraction(split[3]);
          graph.addEdge( from, to, probablity );

        }

        graphs.put(questId, graph);
        sc.nextLine();

        // Read Items 
        for (int i = 0; i < numItems; i++) {
          String [] line = sc.nextLine().split(" +");
          int itemID = Integer.parseInt(line[0]);
          String itemName = line[1];
          
          StringBuilder sb = new StringBuilder();
          for(int j = 2; j < line.length; j++)
            sb.append(line[j] + " "); 
          String itemDescription = sb.toString();
          
          Item item = new Item(itemID, itemName, itemDescription);
          itemMap.put(itemID, item);

        }

      } catch (FileNotFoundException e) {
        e.printStackTrace();
        System.exit(0);
      }
    }

  }

}
