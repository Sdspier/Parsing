## Parsing

### Part A: Grammar


In Part A of this exercise, transformations are applied to the following grammar for evaluating boolean expressions to make it LL parsable.

first() and Follows() sets are written, and the usual Java/C precendence and associativity rules are applied.


>S      -> Assign EOL | Query EOL

>Assign -> ID EQ Exp

>Query  -> ID QMARK

>Exp    -> Exp AND Exp | Exp OR Exp | Exp XOR Exp | NOT Exp | '(' Exp ')' | ID | TRUE | FALSE


### Part B: Recursive Descent Parser


In Part B of this exercise, a recursive descent parser is written to parse and evaluate using the transformed grammar from Part A.


A yylex function is used to return the required termimnals; Lower case leters are variables, initialized to FALSE;
