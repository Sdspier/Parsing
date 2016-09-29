package p1;


import java.io.IOException;
import java.util.Scanner;


class Library {

    public static void main(String[] args) throws IOException {
        runApp();
    }

    static void runApp() throws IOException {
        Scanner sc = new Scanner(System.in);
        char[] car;
        try {
            Parser p = new Parser(new Lex());
            while (true) {
                car = sc.nextLine().toCharArray();
//                for(int i=0; i<car.length; i++){
//                    System.out.println(car[i]+" ");
//                }
                Lex.setLine(car);
                p.parse();

            }
        } catch (ParseError ex) {
            System.out.println("\nSyntax Error: " + ex);
        }
    }
}


class Lex {
    static final char END = '$';
    static final char FALSE = '0';
    static final char TRUE = '1';
    static char yytext, token, LIT;
    static char[] cr;
    static int current;

//    Lex() throws ParseError {
//    }

    static void next() throws ParseError {
//        System.out.println("yytext: " + yytext + "\ntoken: " + token);
        if (current == cr.length) {
            yytext = token = END;
            current = 0;
            System.out.println("In next() 'if' block.");
        } else {
            System.out.println("In next() 'else' block.");
            yytext = cr[current++];
            if (yytext == '=') token = yytext;
            else if (yytext == '?') token = yytext;
            else if (yytext == '(') token = yytext;
            else if (yytext == ')') token = yytext;
            else if (yytext == '~') token = yytext;
            else if (yytext == '&') token = yytext;
            else if (yytext == '^') token = yytext;
            else if (yytext == '|') token = yytext;
            else if (yytext == '0') token = FALSE;
            else if (yytext == '1') token = TRUE;
            else if (Character.isLowerCase(yytext)) LIT = token = yytext;
            else if (yytext == ' ') next();
            else {
                System.out.println("Unexpected character: " + yytext + "\n");
                next();
            }
        }
    }

    static void setLine(char[] c) {
        yytext = token = ' ';
        cr = c;
    }

    char check(char tok) { //return lex val if matched else null char
        System.out.println("-------------------------------\\");
        System.out.println("tok in check: " + tok);
        System.out.println("token in check: " + token);
        System.out.println("Current yytext: " + yytext);
        System.out.println("-------------------------------/");
        if (tok == token) return yytext;
        else return 0;
    }

    char match(char tok) throws ParseError { // same but consumes the token
        char lexval = check(tok);
        if (lexval != 0) {
            System.out.println("Match found!: {" + lexval + "}\n");
            next();
        }
        return lexval;
    }
}

class ParseError extends Exception {
    ParseError(String saw, String expected) {
        System.out.println("p1.ParseError: Saw " + saw + ", Expected " + expected);
    }
}