package ast;

public abstract class Expression {
  // Both assigment and expression need to be evaluated
  public abstract void eval(Program prog);
}
