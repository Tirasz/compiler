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
  : expression NEWLINE? { System.out.print($expression.value); }
  | assignment NEWLINE?
  | NEWLINE
  ;

expression returns [ int value ]
  : '(' expression ')' { $value = $expression.value; }
  | o1=expression exp='^' o2=expression { $value = (int) Math.pow($o1.value, $o2.value); } // Math.pow returns double
  | o1=expression mult=('*' | '/' | '%') o2=expression { switch($mult.text){ case "*": $value = $o1.value * $o2.value; break; case "/": $value = $o1.value / $o2.value; break; case "%": $value = $o1.value % $o2.value; break; } }
  | o1=expression add=('+' | '-') o2=expression { $value = ("+".equals($add.text)) ? $o1.value + $o2.value : $o1.value - $o2.value; }
  | 'm[' i=expression ']' { $value = $i.value; }
  | INTEGER { $value = $INTEGER.int; }
  ;

assignment
  : 'm[' expression ']' '=' expression
  | 'read(' expression ')'
  ;

INTEGER      : DIGIT+;
WHITESPACE  : [ \t\r]+ -> skip;
NEWLINE     : [\n];
DIGIT  : [0-9];

// antlr Byte.g4
// antlrc Byte
// grun Byte program [input.file] (-tree / -gui)