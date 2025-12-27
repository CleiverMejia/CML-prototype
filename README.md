## What's Included

- **Primitive Data Types**, the primitive data types are `number`, `string`, and `boolean`.

- **Comments**, comments can be added on a single line using `//`.

- **Expressions**, expressions are sets of elements that can be assigned or compared to each other.

- **Operations**, basic operations such as addition (+), subtraction (-), multiplication (*), and division (/) are defined in the language. (In most cases, a cast is used to perform the operation.)

- **Comparisons**, comparisons such as >, <, >=, <=, \==, !=, &&, and || are defined in the language. (These return a Boolean value.)

- **Variable Creation**, the first character of a variable name can be a letter or an underscore. After that, numbers can be used.

```
// Valid
name = "John Doe";
_age = 18;
Thing1 = true;

// Invalid
1name = "John Doe";
not$valid = "Error";
```

- **Conditional if statement**, A classic if statement
```
age = 18;

if(age >= 18) {
  print("You are of legal age!");
}
```

- **Else**, A good old-fashioned else :p

```
age = 17;

if (age >= 18) {
  print("You are of legal age!");
} else {
  print("You are a minor :c");
}
```
- **While Loop**, A good old-fashioned while loop
```
i = 0;

while(i < 10) {
  print(i);

  i = i + 1;
}
```

- **Functions**, the ability to create functions; There are also pre-loaded functions

```
function sum(a, b) {
    print(a+b);
}

sum(2+3) // > 5

print("Hello world!"); // > Hello world
sqrt(4) // > 2
```

- **Extern**, encoding outside of language. to implement functions with behaviors defined from Java

```java
public Print() {
    Block block = new Block(new ExternStmt() {
        @Override
        public void exec() {
            Expr expr = Frame.get("text");
            System.out.println(expr);
        }
    });

    FuncExpr function = new FuncExpr(block, "text");

    setName("print");
    setFunction(function);
}
```

^ This is the implementation of the print function

## Note

- The `number` data type only accepts positive integers
- Expressions have no type precedence except for parentheses; all are read from left to right
- The not(!) comparison is included and referenced in the code but is not yet available for use
- The functions must be defined before they are used
- 