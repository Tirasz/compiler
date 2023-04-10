package ast;

import java.util.*;

public class Program {
 
  private List<Line> lines = new ArrayList<Line>();
  
  private List<String> strLines = new ArrayList<>();

  public List<String> testLines = new ArrayList<>();

  public void addLine(Line l){
    lines.add(l);
  }

  public void addStrLine(String str){
    this.strLines.add(str);
  }

  public String getStrLines(){
    return strLines.toString();
  }

  // Evaluate the lines in order
  public void eval(){
    for(int i = 0; i < lines.size(); i++){
      lines.get(i).eval();
    }
  }

  public String toString() {
    StringBuffer prg = new StringBuffer("## Generated source\n\n");
    for (Line l: lines) {
        prg.append(l);
    }
    return prg.toString();
}

}