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
         System.out.println(g);
    }                                                           
}

program [ generator.Generator g ]
  : (line[g])+ EOF
  ;

line [ generator.Generator g ]
  : expression[g] NEWLINE? { g.addInstruction(";"); }
  | assignment[g] NEWLINE? { g.addInstruction(";;"); }
  | NEWLINE
  ;

assignment [ generator.Generator g ]
  : MEM OB expression[g] CB EQ expression[g]    { g.addInstruction("[]="); }
  | READ OP expression[g] CP                    { g.addInstruction("()"); }
  ;

expression [ generator.Generator g ]
  : term[g]   ( ADD term[g]                     { g.addInstruction($ADD.text); } )*
  ;

term [ generator.Generator g ]
  : factor[g] ( MUL factor[g]                   { g.addInstruction($MUL.text); } )*
  ;

factor [ generator.Generator g ] 
  : atom[g]   ( EXP factor[g]                   { g.addInstruction($EXP.text); } )?
  ;

atom [ generator.Generator g ] 
  : INTEGER                                     { g.addInstruction($INTEGER.text); }
  | MEM OB expression[g] CB                     { g.addInstruction("[]"); } 
  | ADD { g.addInstruction("0"); } atom[g]       { g.addInstruction($ADD.text); } // will accept (+5-8) as well (i think thats ok)
  | OP expression[g] CP   
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