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
         System.out.println(p);
         p.eval();
         System.out.println(p.getStrLines());
         System.out.println(p.testLines);
    }                                                           
}

program [ ast.Program p ]
  : (line[p] { } )+ EOF
  ;

line [ ast.Program p ] 
  : expression[p] NEWLINE? {  }
  | assignment[p] NEWLINE? {  }
  | NEWLINE
  ;

assignment [ ast.Program p ] 
  : 'm' '[' index=expression[p] ']' '=' value=expression[p] { p.testLines.add("[]="); }
  | 'read' '(' index=expression[p] ')' { p.testLines.add("()"); }
  ;

expression [ ast.Program p ]
  : term[p] { } (ADD o2=term[p] { p.testLines.add($ADD.text); } )*
  ;

term [ ast.Program p ] 
  : factor[p] { } (MUL o2=factor[p] { p.testLines.add($MUL.text); })*
  ;

factor [ ast.Program p ] 
  : atom[p] {  } (EXP factor[p] { p.testLines.add($EXP.text); })?
  ;

atom [ ast.Program p ] 
  : INTEGER                { p.testLines.add($INTEGER.text); }
  | '(' expression[p] ')'     {  }
  | 'm' '[' expression[p] ']' { p.testLines.add("[]"); } 
  | ADD { p.testLines.add("0"); } atom[p] { p.testLines.add($ADD.text); }
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