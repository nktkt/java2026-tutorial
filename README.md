# Java Mastery Tutorial

A comprehensive Java curriculum: **40 lessons** covering beginner to expert Java, **43 competitive programming problems**, and a **payment system project** with Servlet/JSP/JDBC.

---

## Part 1: Lessons

### Basics (01–07)
| # | Topic |
|---|-------|
| 01 | Hello World — program structure |
| 02 | Variables & data types |
| 03 | Operators |
| 04 | Conditional statements (if / switch) |
| 05 | Loops (for / while / do-while) |
| 06 | Arrays & 2D arrays |
| 07 | Methods & overloading |

### OOP (08–10)
| # | Topic |
|---|-------|
| 08 | Classes & objects |
| 09 | Inheritance & polymorphism |
| 10 | Interfaces & abstract classes |

### Standard Library (11–20)
| # | Topic |
|---|-------|
| 11 | String methods & StringBuilder |
| 12 | Scanner & input handling |
| 13 | ArrayList & Collections |
| 14 | HashMap & HashSet |
| 15 | Exception handling (try-catch-finally) |
| 16 | Encapsulation & access modifiers |
| 17 | static keyword |
| 18 | Enum types |
| 19 | Recursion |
| 20 | Lambda & Stream API |

### Advanced Java (21–30)
| # | Topic |
|---|-------|
| 21 | Generics (type parameters, bounded types, wildcards) |
| 22 | Comparable & Comparator |
| 23 | Iterator & Iterable |
| 24 | Collections deep dive (TreeMap, TreeSet, Deque, ArrayDeque) |
| 25 | Functional interfaces (Predicate, Function, Consumer, Supplier) |
| 26 | Optional |
| 27 | File I/O & try-with-resources |
| 28 | Inner classes & anonymous classes |
| 29 | Annotations |
| 30 | Java Memory Model (Stack vs Heap, GC, String pool) |

### Expert Java (31–34)
| # | Topic |
|---|-------|
| 31 | Multi-threading (Thread, synchronized, ExecutorService, Future) |
| 32 | Design Patterns — Creational (Singleton, Factory, Builder) |
| 33 | Design Patterns — Behavioral (Strategy, Observer, Template Method) |
| 34 | Competitive Programming Toolkit (fast I/O, MOD, templates) |

### Web & I/O (35–40)
| # | Topic |
|---|-------|
| 35 | I/O Streams (byte/char streams, Serialization, PrintWriter) |
| 36 | Network (HttpClient, HTTP GET/POST, Socket) |
| 37 | JDBC (database CRUD, PreparedStatement, transactions) |
| 38 | Servlet (Tomcat, doGet/doPost, request/response, session) |
| 39 | JSP (EL expressions, JSTL, MVC pattern) |
| 40 | Web App Architecture (MVC layers, session, filter, deployment) |

---

## Part 3: Payment Project (`payment_project/`)

A practical e-commerce payment system combining OOP, design patterns, and web concepts.

```
payment_project/
├── model/      Product, CartItem, Order (with enum Status)
├── service/    CartService, PaymentService (Strategy pattern)
├── servlet/    Servlet examples (reference code for Tomcat)
└── Main.java   Console demo (run without Tomcat)
```

Run `payment_project.Main` to see the full checkout flow in the console.

---

## Part 2: Problems

### Beginner (P01–P17)
| # | Problem | Key Concept |
|---|---------|-------------|
| 01 | Sum of Digits | while, % |
| 02 | FizzBuzz | if/else ordering |
| 03 | Reverse String | charAt, reverse loop |
| 04 | Palindrome | Two Pointer intro |
| 05 | Char Frequency | Counting pattern |
| 16 | Prime Number | O(√n) |
| 17 | GCD | Euclidean algorithm |

### Intermediate (P06–P23)
| # | Problem | Key Concept |
|---|---------|-------------|
| 06 | Two Sum | HashMap |
| 07 | Max Subarray | Kadane's Algorithm |
| 08 | Valid Parentheses | Stack |
| 09 | Binary Search | O(log n) |
| 10 | Sort Array | Bubble / Selection Sort |
| 18 | Sliding Window | Fixed-size window |
| 19 | Anagram | Char counting |
| 20 | Linked List Reverse | Pointer manipulation |
| 21 | Prefix Sum | Range query O(1) |
| 22 | Greedy (Activity Selection) | Interval scheduling |
| 23 | Two Pointers | Pair counting |

### Advanced (P11–P30)
| # | Problem | Key Concept |
|---|---------|-------------|
| 11 | Climbing Stairs | DP |
| 12 | LCS | 2D DP |
| 13 | Merge Sort | Divide & Conquer |
| 14 | Coin Change | DP optimization |
| 15 | Graph BFS | Queue, BFS |
| 24 | Number of Islands | DFS on grid |
| 25 | Binary Tree Traversal | Pre/In/Post/Level order |
| 26 | Permutations | Backtracking |
| 27 | Kth Largest | PriorityQueue / Heap |
| 28 | Union-Find | Disjoint set |
| 29 | Bit Manipulation | XOR |
| 30 | Dijkstra | Weighted shortest path |

### Expert (P31–P43)
| # | Problem | Key Concept |
|---|---------|-------------|
| 31 | Quick Sort | Partition, random pivot |
| 32 | Topological Sort | DAG, Kahn's algorithm |
| 33 | Trie | Prefix tree |
| 34 | 0/1 Knapsack | 2D DP optimization |
| 35 | Longest Increasing Subsequence | DP + binary search O(n log n) |
| 36 | KMP String Matching | Failure function |
| 37 | Minimum Spanning Tree | Kruskal + Prim |
| 38 | Floyd-Warshall | All-pairs shortest path |
| 39 | Segment Tree | Range query O(log n) |
| 40 | Binary Indexed Tree | Fenwick tree |
| 41 | Interval DP (Burst Balloons) | dp[i][j] pattern |
| 42 | N-Queens | Advanced backtracking |
| 43 | 0-1 BFS | Deque-based BFS |

---

## How to Use

1. Open the project in IntelliJ IDEA (`src` as Sources Root)
2. **Lessons 01–10**: Start here — Java fundamentals
3. **Lessons 11–20**: Standard library essentials
4. **Problems Beginner/Intermediate**: Practice after lessons 01–20
5. **Lessons 21–34**: Advanced/expert Java topics
6. **Problems Advanced/Expert**: Competitive programming mastery

Each file: read comments → write code yourself → run → check solution (uncomment).

## Requirements

- Java 17 or later
- IntelliJ IDEA (recommended)
