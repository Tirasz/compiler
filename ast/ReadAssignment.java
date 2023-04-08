package ast;

public class ReadAssignment extends Expression{

  private Expression index;

  public ReadAssignment(Expression index){
    this.index = index;
  }

  @Override
  public int eval(Program prog) {
    int index = this.index.eval(prog);

    System.out.println("Memory at index: " + Integer.toString(index) + " Should be: <INPUT>"); 
    return 0;
  }
  
}
