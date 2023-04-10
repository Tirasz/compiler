package ast;
/*
 line
  : expression NEWLINE? { System.out.println($expression.value); }
  | assignment NEWLINE?
  | NEWLINE
  ;
 */
public class Line {
  // A line is either an assigment (read/m[]) or an expression
  // Its parent is a program node

  private Program program;
  private Expression expression;
  private boolean isAss;

  public Line(Program p, Expression e, boolean isAss){
    this.program = p;
    this.expression = e;
    this.isAss = isAss;
  }

  public void eval(){
    expression.eval(this.program);
  }

  public String toString(){
    return expression.toString() + "\n";
  }
}
