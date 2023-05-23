---
layout:			post
title:			"Java API"
subtitle: 		"Java API"
author:			"XiaoZaiz"
header-style: 	text
catalog:      	true
tags:
    - Java
    - API
---

## Util

### 一、Scanner

#### 1.1 构造函数

1. 标准输入流

```java
Scanner input = new Scanner(System.in);
```

2. 文件

```java
Scanner input = new Scanner(new File("data.txt"));
```

3. 字符串

```java
String inputString = "Hello world!";
Scanner input = new Scanner(inputString);
```

4. 可变字符序列（StringBuilder或StringBuffer）

```java
StringBuilder inputBuilder = new StringBuilder("Hello world!");
Scanner input = new Scanner(inputBuilder);
```

5. InputStream构造方法：从指定的**字节输入流**中读取数据。

```java
InputStream inputStream = new FileInputStream("data.txt");
Scanner input = new Scanner(inputStream);
```

6. Reader构造方法：从指定的**字符输入流**中读取数据。

```java
Reader reader = new FileReader("data.txt");
Scanner input = new Scanner(reader);
```

#### 1.2 方法

1. 读取字符串

```java
String next()  // 读取下一个字符串
String nextLine()  // 读取一行字符串，换行
```

2. 读取数字

```java
int nextInt()  // 读取下一个整数
long nextLong()  // 读取下一个长整数
float nextFloat()  // 读取下一个单精度浮点数
double nextDouble()  // 读取下一个双精度浮点数
byte nextByte()  // 读取下一个字节（-128~127）
short nextShort()  // 读取下一个短整数
BigInteger nextBigInteger()  // 读取下一个大整数
BigDecimal nextBigDecimal()  // 读取下一个大浮点数
```

3. 读取布尔值

```java
boolean nextBoolean()  // 读取下一个布尔值（true/false）
```

4. 查找和跳过匹配项

```java
String findInLine(String pattern)  // 查找匹配项
String findWithinHorizon(String pattern, int horizon)  // 查找指定长度内的匹配项
boolean skip(Pattern pattern)  // 跳过匹配项
boolean skip(String pattern)  // 跳过匹配项
boolean skip(Pattern pattern)  // 跳过匹配项
```

5. 读取位置信息

```java
int nextIndex()  // 返回下一个字符的索引
int radix()  // 返回数字的基数
Matcher match()  // 返回当前Scanner的匹配器
String toString()  // 返回Scanner的字符串表示形式
```

##### （1）byte nextByte()

`nextByte()`方法是`Scanner`类提供的读取标准输入中下一个字节的方法。它会将下一个**字符**解释为一个字节，并返回对应的十进制整数。

需要注意的是，`nextByte()`方法只能读取一个字节，即一个介于 -128 到 127 之间的整数。如果输入超出了这个范围，则会抛出异常。

以下是一个示例代码：

```java
Scanner scanner = new Scanner(System.in);
System.out.print("Please enter a byte: ");
byte b = scanner.nextByte();
System.out.println("You entered byte: " + b);
```

如果用户输入大于127或小于-128的值，则会抛出`InputMismatchException`异常，例如：

```java
Please enter a byte: 200
Exception in thread "main" java.util.InputMismatchException: Value out of range. Value:"200" Radix:10
```

因为200超出了字节的范围，因此会抛出`InputMismatchException`异常。而如果输入的值在 -128 到 127 之间，则会正常解释并返回对应的整数。

需要注意的是，由于**`nextByte()`方法不会自动跳过空白字符**（例如空格、制表符和换行符等），因此在使用该方法之前，通常需要**使用`next()`或`nextLine()`方法来跳过多余的空白字符**。

例如，如果要读取一个包含多个字节的字符串，可以使用以下代码：

```java
Scanner scanner = new Scanner(System.in);
System.out.print("Please enter a byte string: ");
String byteStr = scanner.nextLine();
Scanner byteScanner = new Scanner(byteStr);

while (byteScanner.hasNextByte()) {
    byte b = byteScanner.nextByte();
    System.out.println("You entered byte: " + b);
}
```

在上面的示例中，首先使用`nextLine()`方法读取用户输入的一行字符串，并存储到变量`byteStr`中。然后创建一个新的`Scanner`对象`byteScanner`，用于解析该字符串。最后使用`hasNextByte()`和`nextByte()`方法分别读取每个字节，并打印输出。
