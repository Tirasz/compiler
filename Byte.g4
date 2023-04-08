grammar Byte;

options {
  language = Java;
}

@members {
    public static void main(String[] args) throws Exception {
         ByteLexer lex = new ByteLexer(new ANTLRFileStream(args[0]));
         CommonTokenStream tokens = new CommonTokenStream (lex);
         ByteParser parser = new ByteParser(tokens);
         ast.Program p = new ast.Program();
         parser.program(p);
         p.eval();
    }                                                           
}

program [ ast.Program p ]
  : (line[p] { $p.addLine($line.node); } )+ EOF
  ;

line [ ast.Program p ] returns [ ast.Line node ]
  : expression NEWLINE? { $node = new ast.Line($p, $expression.node, false); }
  | assignment NEWLINE? { $node = new ast.Line($p, $assignment.node, true); }
  | NEWLINE
  ;

assignment returns [ ast.Expression node ]
  : 'm' '[' index=expression ']' '=' value=expression { $node = new ast.IndexAssignment($index.node, $value.node); }
  | 'read' '(' index=expression ')' { $node = new ast.ReadAssignment($index.node); }
  ;

expression returns [ ast.Expression node ]
  : term { $node = $term.node; } (ADD o2=term { $node = new ast.BinaryExpression($node, $o2.node, $ADD.text); } )*
  ;

term returns [ ast.Expression node ]
  : factor { $node = $factor.node; } (MUL o2=factor { $node = new ast.BinaryExpression($node, $o2.node, $MUL.text); })*
  ;

factor returns [ ast.Expression node ]
  : atom { $node = $atom.node; } (EXP factor { $node = new ast.BinaryExpression($node, $factor.node, $EXP.text); })?
  ;

atom returns [ ast.Expression node ]
  : INTEGER                { $node = new ast.ConstExpression($INTEGER.text); }
  | '(' expression ')'     { $node = $expression.node; }
  | 'm' '[' expression ']' { $node = new ast.IndexExpression($expression.node); } 
  | ADD atom               { $node = new ast.UnaryExpression($atom.node, $ADD.text); }
  ;
 

WHITESPACE   : [ \t\r]+ -> skip;
NEWLINE      : [\n];
INTEGER      : [0-9]+;
ADD          : '+' | '-';
MUL          : '*' | '/' | '%';
EXP          : '^' ;

// antlr Byte.g4
// antlrc Byte
// grun Byte program [input.file] (-tree / -gui)