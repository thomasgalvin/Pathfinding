# Pathfinding #

A collection of pathfinding / graph traversal algorithms.

Each of the search methods provided can be configured to return fast 
(as soon as any valid path between the origin and the target is found) or
to be exhaustive (guaranteeing that the path returned is equal to or 
lower-cost than any other path between the two nodes within the search
space).

Each method can also be configured to allow or disallow diagonal movement 
between nodes.

No particular path is guaranteed to be returned, but the algorithms are
deterministic (each method will always return the same result for the 
same inputs).

In all cases, if no valid path exists, null will be returned.

In general, the performance of the algorithms is (from best to worst)

1. Best-first
2. Dijkstra's
3. A*

However, a return-fast algorithm will almost always be faster than an 
exhaustive search, especially over large search spaces. 

Similarly, an algorithm that allows diagonal traversal will generally be
faster than one that does not.

This class also contains a method for generating a search space of a given
width and height, and mapped to "real world" coordinates.