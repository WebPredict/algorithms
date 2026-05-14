# Algorithms & Data Structures

A collection of algorithm and data structure implementations in Java, along with code generation helpers for web frameworks.

## Contents

### Algorithms (`alg/`)

| Category | Package | Highlights |
|----------|---------|------------|
| **Arrays** | `arrays` | Spiral matrix, merge sorted arrays, interval merging, equilibrium indices, permutations, combinations, binary search, two-number sum |
| **Sorting** | `sort` | Quicksort, mergesort, introsort, heapsort, bubblesort, selection sort, radix sort |
| **Searching** | `search` | Indexer, crawler (partial) |
| **Trees** | `trees` | BST operations, tree traversals (pre/in/post-order, level-order, iterative + recursive), sorted array/list to BST, trie with prefix search, height/balance checks |
| **Graphs** | `graph` | Graph, vertex, edge representations |
| **Heaps** | `heap` | Max heap with extract/insert/heapify |
| **Queues** | `queues` | Priority queue, min priority queue (partial) |
| **Strings** | `strings` | Palindrome detection, longest common substring/subsequence, anagram detection, string reversal, diff |
| **Words** | `words` | Soundex, rhyming detection, syllable splitting, text justification, Roman numeral conversion, N-grams, word frequency |
| **Math** | `math` | Arithmetic expression evaluation, sieve of Eratosthenes, prime factorization, combinatorics (nCk, Catalan, Pascal's triangle), quadratic solver, GCD/LCM, atoi/itoa |
| **Finance** | `math/finance` | Stock profit (single/multiple transactions), peak/valley detection, volatility |
| **Sequences** | `sequence` | Hamming distance, Levenshtein distance, Needleman-Wunsch scoring, Hirschberg alignment |
| **Bit Manipulation** | `bits` | Bitwise operations, bitmap, bitset |
| **Sets** | `sets` | Disjoint set (union-find) |
| **Misc** | `misc` | Fibonacci, reverse linked list, cycle detection, intersection detection, merge sorted lists, Excel column conversions, FizzBuzz, climb stairs (DP), Unix path simplification, version comparison |
| **Graphics** | `graphics` | Image erosion/dilation, Manhattan distance, color averaging |
| **Games** | `games` | Sudoku generator, Towers of Hanoi, word square, Game of Life (partial) |
| **Machine Learning** | `machlearn` | Genetic algorithm, neural network, naive Bayes, ant colony (partial) |
| **Other** | `compiler`, `distributed`, `optimization`, `security`, `web`, `routing`, `io`, `analytics` | Various stubs and utilities |

### Code Generation (`gen/`)

Scaffolding helpers for web frameworks, generating CRUD application boilerplate:

- **Rails** (`gen/rails`) - Implemented
- **Spring MVC** (`gen/springmvc`) - Partial
- **MEAN** (`gen/mean`) - Partial

## Language

Java

## Status

This is a work-in-progress reference/learning project. Many core algorithms are fully implemented and tested, while some packages contain stubs or partial implementations (noted as "partial" above).
