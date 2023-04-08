package ast;

import java.util.*;

/*
  program
  : line+ EOF;
 */

public class Program {
  // A "program" is just a list of lines
  private List<Line> lines = new ArrayList<Line>();
  
  public void addLine(Line l){
    lines.add(l);
  }

  // Evaluate the lines in order
  public void eval(){
    for(int i = 0; i < lines.size(); i++){
      lines.get(i).eval();
    }
  }
}