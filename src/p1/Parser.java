package p1;


import static p1.Lex.next;

class Parser {

    Lex lex;
    private static final char END = '$';
    private static int[] letterArray = new int[26];


    Parser(Lex l) {
        lex = l;
    }

    void parse() throws ParseError {
        next();
        S();
    }

    void S() throws ParseError {
        //Check for seeing an ID first
        int letterCheck = lex.check(Lex.ID);
        if (letterCheck != 0) S2();
        else throw new ParseError("(1) != id", "id");
        //Check for EOL
        if (lex.match(END) == 0) throw new ParseError("!= END", "END");
    }

    //Query or assign
    void S2() throws ParseError {
        int r;
        char id = lex.match(Lex.ID);
        int index = (int)(id) - 97;
        if (lex.match('?') != 0)
            System.out.println(id + ": " + letterArray[index]);
        else if (lex.match('=') != 0) {
            r = E();
            letterArray[index] = r;
        }
    }

    // OR
    int E() throws ParseError {
        int r = T();
        while (lex.match('|') != 0) {
            r |= T();
        }
        return r;
    }

    // XOR
    int T() throws ParseError {
        int r = F();
        while (lex.match('^') != 0) {
            r ^= F();
        }
        return r;
    }

    // AND
    int F() throws ParseError {
        int r = G();
        while (lex.match('&') != 0) {
            r &= G();
        }
        return r;
    }

    // NOT
    int G() throws ParseError {
        if (lex.match('~') != 0) return G() == 1 ? 0 : 1;
        else return H();
    }

    // Parens and subsequent IDs
    int H() throws ParseError {
        if (lex.match(Lex.FALSE) != 0) return 0;
        if (lex.match(Lex.TRUE) != 0) return 1;
        if (lex.match('(') != 0) {
            int r = E();
            if (lex.match(')') != 0) return r;
            else throw new ParseError(")", "(");
        } else { //letter
            char id = lex.match(Lex.ID);
            if (Character.isLowerCase(id)) {
                int index = (int) (id) - 97;
                return letterArray[index];
            } else throw new ParseError("(2) != id", "id");
        }
    }
}