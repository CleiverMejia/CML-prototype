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

- **Displaying messages in the console**, to display messages on the screen, use `print();`. You can add expressions, primitive types, or variables to display them in the console.

```
print("Hello world!"); // > Hello world
print(2 + 2); // > 4

name = "John Doe";
print("Hello, my name is " + name); // > Hello, my name is John Doe
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
## Note

- The `number` data type only accepts positive integers
- Expressions have no type precedence except for parentheses; all are read from left to right
- The not(!) comparison is included and referenced in the code but is not yet available for use