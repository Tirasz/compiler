grammar Byte;

options {
  language = Java;
}

@members {
    public static void main(String[] args) throws Exception {
         ByteLexer lex = new ByteLexer(new ANTLRFileStream(args[0]));
         CommonTokenStream tokens = new CommonTokenStream (lex);
         ByteParser parser = new ByteParser(tokens);
         generator.Generator g = new generator.Generator();
         parser.program(g);
         g.generateByteCode();
    }                                                           
}

program [ generator.Generator g ]
  : (line[g])+ EOF
  ;

line [ generator.Generator g ]
  : expression[g] NEWLINE? { g.addInstruction(";");  }
  | assignment[g] NEWLINE? { g.addInstruction(";;"); }
  | NEWLINE
  ;

assignment [ generator.Generator g ]
  : MEM OB expression[g] CB EQ expression[g]    { g.addInstruction("[]="); }
  | READ OP expression[g] CP                    { g.addInstruction("()");  }
  ;

expression [ generator.Generator g ] returns [ boolean canEval, int v ]
  : o1=term[g] { $canEval = $o1.canEval; $v = $o1.v; } 
    ( ADD o2=term[g] 
      { 
        g.addInstruction($ADD.text);
        $canEval = $canEval && $o2.canEval;
        if("+".equals($ADD.text))
          $v += $o2.v;
        else
          $v -= $o2.v;
        if($canEval)
          g.replaceExpression($v); 
      } 
    )*
  ;

term [ generator.Generator g ] returns [ boolean canEval, int v ]
  : o1=factor[g] { $canEval = $o1.canEval; $v = $o1.v; } 
    ( MUL o2=factor[g] 
      { 
        g.addInstruction($MUL.text);
        $canEval = $canEval && $o2.canEval;
        switch ($MUL.text){
          case "*": $v = $v * $o2.v; break;
          case "/": $v = $v / $o2.v; break;
          case "%": $v = $v % $o2.v; break;
        }
        if($canEval) 
          g.replaceExpression($v); 
      } 
    )*
  ;

factor [ generator.Generator g ] returns [ boolean canEval, int v ]
  : atom[g] { $canEval = $atom.canEval; $v = $atom.v; }  
    ( EXP factor[g] 
      {
        if($factor.canEval && $factor.v < 0)
          throw new IllegalArgumentException("Cannot raise to negative power");
        g.addInstruction($EXP.text);
        $canEval = $canEval && $factor.canEval;
        $v = (int) Math.pow($v, $factor.v);
        if($canEval) 
          g.replaceExpression($v);
      } 
    )?
  ;

atom [ generator.Generator g ] returns [ boolean canEval, int v ]
  : INTEGER                                     { g.addInstruction($INTEGER.text); $canEval = true; $v = Integer.parseInt($INTEGER.text); }
  | MEM OB expression[g] CB                     { g.addInstruction("[]"); $canEval = false; } 
  | ADD { g.addInstruction("0"); } atom[g]      { g.addInstruction($ADD.text); $canEval = $atom.canEval; if("+".equals($ADD.text)) $v = $atom.v; else $v = -$atom.v; if($canEval) g.replaceExpression($v); } // will accept (+5-8) as well (i think thats ok)
  | OP expression[g] CP                         { $canEval = $expression.canEval; $v = $expression.v; }
  ;


WHITESPACE   : [ \t\r]+ -> skip;
NEWLINE      : [\n];
INTEGER      : [0-9]+;
READ         : 'read';
ADD          : '+' | '-' ;
MUL          : '*' | '/' | '%';
EXP          : '^' ;
MEM          : 'm' ;
EQ           : '=' ;
OB           : '[' ;
CB           : ']' ;
OP           : '(' ;
CP           : ')' ;



// antlr Byte.g4
// antlrc Byte
// grun Byte program [input.file] (-tree / -gui)