package ast;

public class IndexExpression extends Expression {
  private Expression index;

  public IndexExpression(Expression index){
    this.index = index;
  }

  @Override
  public int eval(Program prog) {
    int index = this.index.eval(prog);
    System.out.println("Should eval memory at index: " + Integer.toString(index)); 
    return 0;
  }

  
}
