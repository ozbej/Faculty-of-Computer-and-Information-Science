# Description

Algorithms implement the following graph searches:

* BFS
* DFS
* IDDFS
* A*
* IDA*

Heuristics for A* and IDA* is calculated using Manhattan distance.

Algorithms are searching through labyrints in the following form:
-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1<br/>
-1,-2,4,4,-1,4,4,4,4,4,-1<br/>
-1,-1,-1,4,-1,4,-1,-1,-1,4,-1<br/>
-1,4,4,4,4,4,-1,4,4,4,-1<br/>
-1,4,-1,4,-1,4,-1,4,-1,4,-1<br/>
-1,4,-1,4,-1,4,-1,-3,-1,4,-1<br/>
-1,4,-1,4,-1,4,-1,-1,-1,4,-1<br/>
-1,4,4,4,-1,4,4,4,4,4,-1<br/>
-1,-1,-1,4,-1,4,-1,4,-1,4,-1<br/>
-1,4,4,4,4,4,4,4,-1,4,-1<br/>
-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1<br/>

The numbers represent:

* -1: wall
* -2: start
* -3: end
* Other numbers are cost of passing the field
