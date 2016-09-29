package p1;


import static p1.Lex.next;

class Parser {

    Lex lex;
    private static char litv;
    private static int litvI;
    private static final char END = '$';
    private static int[] letterArray = new int[26];


    Parser(Lex l) {
        lex = l;
    }

    void parse() throws ParseError {
        System.out.println("In parse");
        next();
        S();
    }

    void S() throws ParseError { System.out.println("In S()");

        int letterCheck = lex.check(Lex.LIT);
        System.out.println("letterCheck: " + letterCheck);

        //Check for letter first
        if (letterCheck != 0) { //If you see a letter first
            S2();
        } else {
            throw new ParseError("!= id", "id");
        }

        //Check for EOL
        if (lex.match(END) == 0) {              //If you don't see an $ at the end of the parsing
            throw new ParseError("!= END", "END");   //Throw an error
        } else {
            System.out.println("Matched $... end of current expression.");  //Otherwise you're done
        }
    }

    void S2() throws ParseError { System.out.println("In S2()");
        int r;
        char id = lex.match(Lex.LIT);
        System.out.println("ID seen: "+id);
        int idVal = (int)(id) - 97;
        System.out.println("ID value calculated: " + idVal);
        if (lex.match('?') != 0) {
            r = E();
            System.out.println("Value of " + id + " is: " + letterArray[idVal]+ "or new E() try: " + r );
        } else if (lex.match('=') != 0) {
            r = E();
            letterArray[idVal] = r;
            System.out.println("Stored value " + r + " for " + id);
        }
    }

    // OR
    int E() throws ParseError { System.out.println("In E()");
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
        if (lex.match('~') != 0) {
            return G() == 1 ? 0 : 1;
        } else {
            return H();
        }
    }

    // letter or FALSE or TRUE or '(' or ')'
    int H() throws ParseError {

        if (lex.match(Lex.FALSE) != 0) {
            System.out.println("In FALSE section");
            return 0;
        }
        if (lex.match(Lex.TRUE) != 0) {
            System.out.println("In TRUE section");
            return 1;
        }

        if (lex.match('(') != 0) {
            System.out.println("In '(' section 1");
            int r = E();
            if (lex.match(')') != 0) return r;
            else throw new ParseError(")", "(");

//        } else { //letter
//            System.out.println("In 'letter' section 1");
//            char litval = lex.match(Lex.LIT);
//            System.out.println("last litval seen in H():" + litval);
//            if (Character.isLowerCase(litval)) {
//                System.out.println("In 'letter' section 2");
//                litv = litval;
//                litvI = (int) (litval) - 97;
//                return litvI;
            } else throw new ParseError("!= id2", "id2");
        }
    }
//}