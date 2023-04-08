grammar Byte;

options {
  language = Java;
}

program
  : line+ EOF;

line
  : (expression | assignment) NEWLINE
  ;

expression
  : '(' expression ')'
  | expression exp='^' expression
  | expression mult=('*' | '/' | '%') expression
  | expression add=('+' | '-') expression
  | 'm[' expression ']'
  | INTEGER
  ;

assignment
  : 'm[' expression ']' '=' expression
  | 'read(' expression ')'
  ;

INTEGER      : DIGIT+;
WHITESPACE  : [ \t\r]+ -> skip;
NEWLINE     : [\n];
DIGIT  : [0-9];

