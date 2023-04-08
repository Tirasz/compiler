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
  : term (ADD term)*
  ;

term returns [int value]
  : factor (MUL factor)*
  ;

factor returns [int value]
  : atom (EXP atom)*
  ;

atom returns [int value]
  : INTEGER
  | '(' expression ')'
  | ADD atom
  | 'm' '[' expression ']'
  ;
 

WHITESPACE   : [ \t\r]+ -> skip;
NEWLINE      : [\n];
DIGIT        : [0-9];
INTEGER      : DIGIT+;
ADD          : '+' | '-';
MUL          : '*' | '/' | '%';
EXP          : '^' ;

// antlr Byte.g4
// antlrc Byte
// grun Byte program [input.file] (-tree / -gui)