# Algorithms & Data Structures

A collection of algorithm and data structure implementations in Java, along with code generation helpers for web frameworks. All algorithm methods are annotated with time and space complexity via `@InterestingAlgorithm`.

## Contents

### Core Data Structures

| Category | Package | Highlights |
|----------|---------|------------|
| **Linked Lists** | `linkedlist` | Doubly linked list, LRU cache (HashMap + linked list), singly linked list operations (reverse, cycle detection, intersection detection, merge sorted lists) |
| **Trees** | `trees` | BST operations, tree traversals (pre/in/post-order, level-order, iterative + recursive), sorted array/list to BST, trie with prefix search, height/balance checks |
| **Heaps** | `heap` | Max heap (tree-based), min heap (array-based) |
| **Queues** | `queues` | Min priority queue (binary heap-backed), circular buffer |
| **Sets** | `sets` | Disjoint set / union-find with path compression and union by rank |
| **Graphs** | `graph` | Graph, vertex, edge representations |

### Algorithm Categories

| Category | Package | Highlights |
|----------|---------|------------|
| **Graph Algorithms** | `graph` | BFS, DFS, Dijkstra's shortest path, Bellman-Ford, topological sort, cycle detection, Kruskal's MST, Prim's MST, strongly connected components (Kosaraju's) |
| **Dynamic Programming** | `dp` | 0/1 knapsack, unbounded knapsack, coin change (min coins + ways), longest increasing subsequence, edit distance, matrix chain multiplication, rod cutting, longest palindromic substring, Kadane's max subarray, unique paths, min path sum |
| **Backtracking** | `backtracking` | N-queens, generate parentheses, subsets (power set), permutations, combination sum, word search, sudoku solver |
| **Sliding Window** | `slidingwindow` | Max sum subarray of size K, longest substring without repeating chars, minimum window substring, longest substring with K distinct chars, sliding window maximum |
| **Two Pointers** | `twopointers` | Two sum, three sum, container with most water, trapping rain water, remove duplicates in sorted array |
| **Stack Problems** | `stackproblems` | Valid parentheses, evaluate reverse Polish notation, daily temperatures, largest rectangle in histogram, min stack (O(1) getMin) |
| **Binary Search** | `binarysearch` | Search rotated array, find first/last position, search 2D matrix, find peak element, integer sqrt, find min in rotated sorted array |
| **Sorting** | `sort` | Quicksort, mergesort, introsort, heapsort, bubblesort, selection sort, radix sort |
| **Arrays** | `arrays` | Spiral matrix, merge sorted arrays, interval merging, equilibrium indices, permutations, combinations, binary search, two-number sum |
| **Strings** | `strings` | Palindrome detection, longest common substring/subsequence, anagram detection, string reversal, diff |
| **Words** | `words` | Soundex, rhyming detection, syllable splitting, text justification, Roman numeral conversion, N-grams, word frequency |
| **Math** | `math` | Arithmetic expression evaluation, sieve of Eratosthenes, prime factorization, combinatorics (nCk, Catalan, Pascal's triangle), quadratic solver, GCD/LCM, atoi/itoa |
| **Finance** | `math/finance` | Stock profit (single/multiple transactions), peak/valley detection, volatility |
| **Sequences** | `sequence` | Hamming distance, Levenshtein distance, Needleman-Wunsch scoring, Hirschberg alignment |
| **Bit Manipulation** | `bits` | Bitwise operations, bitmap, bitset |
| **Misc** | `misc` | Fibonacci, Excel column conversions, FizzBuzz, climb stairs (DP), Unix path simplification, version comparison, peak element |
| **Graphics** | `graphics` | Image erosion/dilation, Manhattan distance, color averaging, paint fill, convex hull |
| **Games** | `games` | Sudoku generator, Game of Life, Reversi, word square, crossword, Rubik's cube |

### Code Generation (`gen/`)

Scaffolding helpers for web frameworks, generating CRUD application boilerplate:

- **Rails** (`gen/rails`) - Implemented
- **Spring MVC** (`gen/springmvc`) - Partial
- **MEAN** (`gen/mean`) - Partial

## Language

Java (no build system — compile with `javac`)

## Running Tests

```bash
# Compile (excluding alg/io which requires Apache Commons IO)
javac -d build $(find alg test -name "*.java" ! -path "*/io/*" ! -name "WebTest.java" ! -name "WordTest.java")

# Run tests
java -cp build test.alg.misc.MiscUtilsTest
java -cp build test.alg.tree.TreeTest
java -cp build test.alg.sort.SortTest
java -cp build test.alg.math.MathTest
java -cp build test.alg.arrays.ArraysTest
```
