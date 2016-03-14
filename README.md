# Parser
#### Input files
* Semantic: `Parser/src/main/resources/semantic.bnf`
* Input text: `Parser/src/main/resources/input.txt`

#### How to run
```bash
git clone https://github.com/vladislavoff1/Parser
cd Parser
mvn compile
java -cp target/classes main.Main
```

For simplified tree add `-s`:
```bash
java -cp target/classes main.Main -s
```


#### Examples
##### Math expression

***`semantic.bnf`***
```
<expression> ::= <term> | <expression> "+" <term>
<term>       ::= <factor> | <term> "*" <factor>
<factor>     ::= <constant> | <variable> | "(" <expression> ")"
<variable>   ::= "x" | "y" | "z"
<constant>   ::= <digit> | <digit> <constant>
<digit>      ::= "0" | "1" | "2" | "3" | "4" | "5" | "6" | "7" | "8" | "9"
```

***`input.txt`***
```
1+(6+2)*3
```

***`result simpified`***
```
expression
├── 1
├── +
└── term
    ├── factor
    │   ├── (
    │   ├── expression
    │   │   ├── 6
    │   │   ├── +
    │   │   └── 2
    │   └── )
    ├── *
    └── 3
```

***`result full`***
```
expression
├── expression
│   └── term
│       └── factor
│           └── constant
│               └── digit
│                   └── '1'
├── '+'
└── term
    ├── term
    │   └── factor
    │       ├── '('
    │       ├── expression
    │       │   ├── expression
    │       │   │   └── term
    │       │   │       └── factor
    │       │   │           └── constant
    │       │   │               └── digit
    │       │   │                   └── '6'
    │       │   ├── '+'
    │       │   └── term
    │       │       └── factor
    │       │           └── constant
    │       │               └── digit
    │       │                   └── '2'
    │       └── ')'
    ├── '*'
    └── factor
        └── constant
            └── digit
                └── '3'

```

##### Bad semantic

***`semantic.bnf`***
```
<bad> ::= "1" > "2"
```

***`result`***
```
Parser error (semantic.bnf): Unexpected symbol '>'
<bad> ::= "1" > "2"
              ^
```

##### Bad semantic 2

***`semantic.bnf`***
```
<bad> := "1" | "2"
```

***`result`***
```
Parser error (semantic.bnf): '::=' expected
<bad> := "1" | "2"
      ^
```

##### Bad input

***`semantic.bnf`***
```
<brackets> ::= '()' | '(' <brackets> ')' | <brackets> <brackets>
```

***`input.txt`***
```
())
```

***`result`***
```
Parser error (input.txt): Unexpected symbol ')'
())
  ^
```



#### TODO

* Input files names in `args`
* Parse `semantic` by `EarleyParser` (not special parser)
* Improve BNF: parse that rules like `<number> ::= ["-"] <positive>`
* Save to file `Grammar`. Split `semantic` and `input` parsing
* More tests
* Improve parsers: `InputStream` instead of `String`
* Error correction in parsers
* Add lexer parser
* Use LL-parser if possible


