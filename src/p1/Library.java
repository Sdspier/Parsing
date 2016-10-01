package p1;


import java.io.IOException;
import java.util.Scanner;


class Library {

    public static void main(String[] args) throws IOException {
        runApp();
    }

    static void runApp() throws IOException {
        Scanner sc = new Scanner(System.in);
        char[] input;
        try {
            Parser p = new Parser(new Lex());
            while (true) {
                input = sc.nextLine().toCharArray();
                Lex.setLine(input);
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
    static final char ID ='L';
    static char yytext, token;
    static char[] input;
    static int pos;


    static void next() throws ParseError {
        if (pos == input.length) {
            yytext = token = END;
            pos = 0;
        } else {
            yytext = input[pos++];
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
            else if (Character.isLowerCase(yytext)) token = ID;
            else if (yytext == ' ') next();
            else {
                System.out.println("Unexpected character: " + yytext + "\n");
                next();
            }
        }
    }

    static void setLine(char[] c) {
        input = c;
        pos = 0;
    }

    char check(char tok) {
        if (tok == token) return yytext;
        else return 0;
    }

    char match(char tok) throws ParseError { // same but consumes the token
        char lexval = check(tok);
        if (lexval != 0) {
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