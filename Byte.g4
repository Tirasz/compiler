grammar Byte;

options {
  language = Java;
}

@members {
    public static void main(String[] args) throws Exception {
         ByteLexer lex = new ByteLexer(new ANTLRFileStream(args[0]));
         CommonTokenStream tokens = new CommonTokenStream (lex);
         ByteParser parser = new ByteParser(tokens);
         parser.program();
    }                                                           
}

program
  : line+ EOF;

line
  : expression NEWLINE? { System.out.println($expression.value); }
  | assignment NEWLINE?
  | NEWLINE
  ;

assignment
  : 'm' '[' expression ']' '=' expression
  | 'read' '(' expression ')'
  ;

expression returns [int value]
  : o1=term { $value = $o1.value; } (ADD o2=term {  if ("+".equals($ADD.text)) $value += $o2.value; else $value -= $o2.value; } )*
  ;

term returns [int value]
  : o1=factor { $value = $o1.value; } (MUL o2=factor { switch($MUL.text){ case "*": $value *= $o2.value; break; case "/": $value /= $o2.value; break; case "%": $value = $value % $o2.value; break; } })*
  ;

factor returns [int value]
  : o1=atom { $value = $o1.value; } (EXP o2=factor { $value = (int) Math.pow($value, $o2.value); })?
  ;

atom returns [int value]
  : INTEGER { $value = $INTEGER.int; }
  | '(' expression ')' { $value = $expression.value; }
  | ADD atom { $value = "-".equals($ADD.text) ? -$atom.value : $atom.value; }
  | 'm' '[' expression ']' { $value = 1; } //TODO
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