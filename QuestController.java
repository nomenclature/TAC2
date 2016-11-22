/*
 * @author William Fiset, Jonathan Whitaker
 * Tick Attack milestone #3
 * Object Oriented Design - COMP 3721
 */

import java.util.*;
import java.io.*;

enum EventType {
  MONEY, HEALTH, QUEST_UNLOCK, ITEM
}

class EventObtainable {

  private int value;
  private EventType type;

  public EventObtainable(EventType type, int value) {
    this.type = type;
    this.value = value;
  }

  public EventType getType() {
    return type;
  }
  
  public int getValue() {
    return value;
  }

}

// EventNode objects hold information about a particular event.
// This information can be anything from the event description
// amount of money found, health lost, or items found.
class EventNode {

  private String eventDescription;
  private List <EventObtainable> eventObtainables = new LinkedList<>();

  public EventNode(String description, List<EventObtainable> obtainables) {
    this.eventDescription = description;
    this.eventObtainables = obtainables;
  }

  public String getEventDescription() {
    return eventDescription;
  }

  public List <EventObtainable> getEventObtainables() {
    return eventObtainables;
  }

}



// This Edge class represents a directed edge between EventNode objects
class Edge {

  // 'from' is the node you ar currently on
  // and 'to' is the node your connecting to
  private int from, to;

  // The probably fraction is the probablity that this edge is selected.
  // The sum of all the probablitites exiting a node sums up to one.
  private Fraction probablity;

  public Edge(int from, int to, Fraction probablity) {
    this.from = from;
    this.to = to;
    this.probablity = probablity;
  }

  // Return the destination this edge to pointing to
  public int to() {
    return to;
  }

  // Return the destination this edge is coming from
  public int from() {
    return from;
  }

  public double probablity() {
    return probablity.getRatio();
  }
 
}

// A graph object represents a graph for a particular quest.
// Each node represents an event (this is why node objects are called EventNodes)
// And each edge represents a transition between events (nodes)
class Graph {

  // This adjacency list maps integers (nodes) to a list of edges
  private Map <Integer, List<Edge>> graph;

  // This array stores various events
  private EventNode[] eventNodes;

  public Graph(int numNodes) {

    graph = new HashMap<>(numNodes);
    eventNodes = new EventNode[numNodes];
    for(int i = 0; i < numNodes; i++)
      graph.put(i, new LinkedList<Edge>());
  
  }

  // When constructing the graph this method allows
  // you to associate a node to a node id.
  public void addNode( int nodeId, EventNode newNode ) {
    eventNodes[nodeId] = newNode;
  }

  // This method adds an edge in the graph from node id 'from'
  // to the node id 'to' with a given probability
  public void addEdge( int from, int to, Fraction probabity ) {
    
    List <Edge> edges = graph.get(from);
    edges.add( new Edge(from, to, probabity) );

  }

  // Return a list of edges leading out from the current node
  public List <Edge> getEdges (int nodeId) {
    return graph.get(nodeId);
  }

  // End nodes are nodes which have no outgoing edges
  // indicating that the quest has ended.
  public boolean isEndNode(int nodeId) {
    List <Edge> edges = graph.get(nodeId);
    return edges.size() == 0;
  }

  // Check bounds on nodeId
  public EventNode getNode(int nodeId) {
    return eventNodes[nodeId];
  }

}

public class QuestController {

  private View view;
  private Player player;
  private HeaderView headerView;
  private IView announcementView;

  // This map contains all the graphs (sequence of possible events) for 
  // any particular quest (each quest has a questId).
  private Map <Integer, Graph> graphs;
  private Map <Integer, Item> itemMap;

  // Might want a quest object? It would have an id & a name
  private Set <Integer> visitableQuests = new TreeSet<>();
  private Map <Integer, String> questNameMapping = new HashMap<>();

  public QuestController ( Player player ) {
    
    QuestLoader loader = QuestLoader.getInstance();
    this.graphs = loader.getQuests();
    this.questNameMapping = loader.getQuestNameMapping();
    this.itemMap = loader.getItemMap();

    this.player = player;
    this.view = new View();
    this.headerView = new HeaderView("QUEST: ");
    this.announcementView = new AnnouncementView();
    
    // Initially only quest zero is visitable
    if (questNameMapping.size() > 0) {
      visitableQuests.add(0);
    }

  }

  public boolean questIsVisitable(int questId) {
    return visitableQuests.contains(questId);
  }

  public Set<Integer> getVisitableQuests() {
    return visitableQuests;
  }

  public boolean hasQuest(int questId) {
    return questNameMapping.containsKey(questId);
  }

  public String getQuestName(int questId) {
    if (hasQuest(questId))
      return questNameMapping.get(questId);
    throw new IllegalArgumentException("questId not found");
  }

  public void visitNode(Graph graph, int nodeId) {

    EventNode node = graph.getNode(nodeId);
    String eventDescription = node.getEventDescription();
    List <EventObtainable> obtainables = node.getEventObtainables();

    headerView.display(eventDescription + "\n");

    for (EventObtainable eventObtainable : obtainables) {
      
      EventType type = eventObtainable.getType();
      int value = eventObtainable.getValue();

      if (type == EventType.MONEY) {
        
        int moneyGained = value; 
        player.obtainMoney(moneyGained);
        boolean gainedMoney = (moneyGained >= 0);
        moneyGained = Math.abs(moneyGained);

        headerView.display("You " + (gainedMoney ? "obtain " : "lose ") + moneyGained + " dollars\n");
        headerView.display("You now have: " + player.getMoney() + " $\n");

      } else if (type == EventType.HEALTH) {

        int healthGained = value;
        player.obtainHealth(healthGained);
        boolean gainedHealth = (healthGained >= 0);
        healthGained = Math.abs(healthGained);

        headerView.display("You " + (gainedHealth ? "obtain " : "lose ") + healthGained + " health\n");
        headerView.display("Your new health is: " + player.getHealth() + " ♥\n");

      } else if (type == EventType.ITEM) {

        int itemID = value;
        Item item = itemMap.get(itemID);
        player.obtainItem(item);

        headerView.display("You have found a new item: \""+ item +"\"\n");

      } else if (type == EventType.QUEST_UNLOCK) {
        int unlockedQuest = value;
        if (!visitableQuests.contains(unlockedQuest)) {
          announcementView.display("YOU HAVE UNLOCKED NEW QUEST: \"" + questNameMapping.get(unlockedQuest) + "\"");
          visitableQuests.add(unlockedQuest);
        }
      }

    }

  }

  // Given a questId of a quest which is available this
  // method will play the quest!
  public void playQuest(int questId) {

    // Check if we are allowed to play the quest the user has requested
    if (visitableQuests.contains(questId)) {

      headerView.setHeader("QUEST ("+ questNameMapping.get(questId)+"): ");
      headerView.display("PRESS 'ENTER' TO ADVANCE TO THE NEXT EVENT.\n\n");

      int curNode = 0;
      Graph graph = graphs.get(questId);

      while(!graph.isEndNode(curNode)) {

        visitNode(graph, curNode);

        String userInput = view.readLine().trim().toLowerCase();
        if (userInput.equals("quit") || userInput.equals("exit")) return;

        List <Edge> edges = graph.getEdges(curNode);

        // Select an edge at random
        Edge nextEdge = selectRandomEdge(edges);

        // update curNode to edge.to (the edge you selected)
        curNode = nextEdge.to();

      }

      // displays information for the last node
      visitNode(graph, curNode);
      String userInput = view.readLine();
      if (userInput.equals("quit") || userInput.equals("exit")) return;

    }

    // After completing quest perhaps indicate net gained money/health?

  } 

  // Given a list of edges with probabilities,
  // select an edge at random!
  private Edge selectRandomEdge(List <Edge> edges) {
    
    if (edges == null || edges.size() == 0)
      return null;

    // Select random number in the range [0, 1)
    double randomNum = Math.random();

    Edge edge = edges.get(0);
    double lo = 0, hi = edge.probablity();

    // Check if edge falls within the bounds [lo, hi)
    for (int i = 1; i < edges.size(); i++) {

      // found the edge we're looking for! It falls within the bounds!
      if ( lo <= randomNum && randomNum < hi )
        return edge;
      
      // Adjust bounds
      lo = hi;
      hi += edge.probablity();

      // get next edge
      edge = edges.get(i);

    }

    return edge;

  }

}