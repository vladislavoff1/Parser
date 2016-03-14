# Parser

#### Source files
Semantic: `Parser/src/main/resources/semantic.bnf`
Input text: `Parser/src/main/resources/input.txt`

#### How to run
```
git clone https://github.com/vladislavoff1/Parser
cd Parser
mvn compile
java -cp target/classes main.Main
```

#### TODO

* Input files names in `args`
* Parse `semantic` by `EarleyParser` (not special parser)
* Improve BNF: parse that rules like `<number> ::= ["-"] <positive>`
* Save to file `Grammar`. Split `semantic` and `input` parsing
* More tests
* Improve parsers: `InputStream` instead of `String`
* Error correction in parsers