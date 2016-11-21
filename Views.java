 /*
 * @author William Fiset, Jonathan Whitaker
 * Tick Attack milestone #3
 * Object Oriented Design - COMP 3721
 */

import java.util.Scanner;
import java.io.*;

interface IView {

  public String readLine();
  public void display(Object obj);

}

class View implements IView {

  protected Scanner scanner;
  protected Writer writer;  

  public View () {
    this(System.in, System.out);
  }

  public View(InputStream in, OutputStream out) {
    scanner = new Scanner(in);
    writer = new PrintWriter(out);
  }  

  public void display(Object obj) {
    try {
      if (obj != null) 
        writer.write(obj.toString());
      else
        writer.write("null\n");
      writer.flush();
    } catch (IOException e) { e.printStackTrace(); }
  }

  // Grab a single line of input from the user
  public String readLine() {
    return scanner.nextLine();
  }  

}

class HeaderView extends View {

  private String header;

  public HeaderView(String header) {
    if (header == null)
      throw new IllegalArgumentException("Header cannot be null");
    this.header = header;
  }

  public HeaderView(InputStream in, OutputStream out, String header) {
    super(in, out);
    if (header == null)
      throw new IllegalArgumentException("Header cannot be null");
    this.header = header;
  }

  public void setHeader(String newHeader) {
    if (newHeader == null)
      throw new IllegalArgumentException("Header cannot be null");
    header = newHeader;
  }

  @Override public void display(Object obj) {
    if (obj != null) {
      super.display(header + obj.toString());
    } else {
      super.display(header + " null\n");
    }
  }

  @Override public String readLine() {
    display("");
    return super.readLine();
  }

}


class AnnouncementView extends View {

  @Override public void display(Object obj) {

    if (obj != null) {
    
      String strOutput = obj.toString().toUpperCase();
      int len = strOutput.length();
    
      StringBuilder sb = new StringBuilder();
      
      // top
      sb.append("\n*");
      for (int i = 0; i < len + 12; i++) sb.append("*");
      sb.append("*\n");
      
      for (int i = 0; i < 6; i++ ) sb.append("*");
      sb.append(" " + strOutput + " ");
      for (int i = 0; i < 6; i++ ) sb.append("*");
      sb.append("\n");

      // bottom
      sb.append("*");
      for (int i = 0; i < len + 12; i++) sb.append("*");
      sb.append("*\n\n");

      super.display(sb);

    } else {
      super.display("null\n");
    }
  }

}

