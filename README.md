# Coursera: Princeton Algorithms 

Self learning of Princeton University's Algorithms course in Coursera.

Instructors: Robert Sedgewick, Kevin Wayne

Textbook: [Algorithms, 4th][algs4]

Coursera: [Algorithms, Part I][part1], [Algorithms, Part II][part2]



## Schedule

*Note*: week 1-6 are from [part I][part1], week 7-12 are from [part II][part2].   
Interview Questions are optional, you should write it if you want a job.
Every week has an assignment which requires all content of that week and previous weeks.   

| Week | Lecture | Textbook | Practice Quiz | Programming Assignments |
| :--: | :-----: | :------: | :-----------------: | :---------: |
| 1 | Course Introduction      | 1.1, 1.2 |              | [Project 0: Hello, World][hello] |
|   | Union-Find               | 1.5 | Union-Find        | [Project 1: Percolation][percolation] |
|   | Analysis of Algorithms   | 1.4 | Analysis of Algorithms |  |
| 2 | Stacks and Queues        | 1.3 | Stacks and Queues | [Project 2: Queues][queues] |
|   | Elementary Sorts         | 2.1 | Elementary Sorts  |  |
| 3 | Mergesort                | 2.2 | Mergesort         | [Project 3: Collinear][collinear] |
|   | Quicksort                | 2.3 | Quicksort         |  |
| 4 | Priority Queues          | 2.4 | Priority Queues   | [Project 4: 8 Puzzle][8-puzzle] |
|   | Elementary Symbol Tables | 3.1, 3.2 | Elementary Symbol Tables |  |
| 5 | Balanced Search Trees    | 3.3 | Balanced Search Trees |  |
|   | Geometric Applications of BSTs|  |                 | [Project 5: Kd-Trees][kdtree] |
| 6 | Hash Tables              | 3.4 | Hash Tables       |  |
|   |Symbol Table Applications |     |                   |  |



## Assignments

### Project 0: Hello, World    
Implementations of class *HelloWorld* and class *HelloGoodbye* are uncessary import *algs4.jar*.   
Thus, CLI use *javac* and *java* instead of *javac-algs4* and *java-algs4*.   
**Note**: Knuth’s method, when reading *i*th word which *i* is start from 1, not 0.

### Project 1: Percolation    
In class *Percolation*, method *open()* should not open site repeated times.
If you get a more larger value, check your open() method whether it open site multiply times
and count the repeated times.

### Project 2: Queues    
For *RandomizedQueue.java*'s deque operation, I swap random item with tail item.     
For client part, command below:
```bash
java Permutation 3 < distinct.txt
```
should be:
```bash
java-algs4 Permutation 3 < distinct.txt
```

### Project 3: Collinear    
*General*:
1. For Comparable's compareTo() and Comparator's compare(), if not all operators
are integers, don't use subtraction and then cast to int (which will cause 
small real number (positive or negative) to be zero).    
2. Don't mutate argument array's content.   

*FastCollinearPoints*:
1. Build a copy of array (since sort is inplace) for correct interation.     
2. Make use of stability of sort to avoid invalid segments.   
3. If your number of collinear segments is less, consider all points are collinear.     
 
### Project 4: 8 Puzzle      
*Board*:       
1. Recommend use 2D array instead 1D array to implemnet.     
2. twin() method must return same Board every time.     
3. Skip blank (since it is not tile). Check that you skip blank in isGoal(),  hamming() and manhattan() methods.

*Solver*:     
1. Key problem is to implemnet a class about search node in game tree.  
2. You can use stack to store solution for correct move order.     

**Note**: If you want to understand A* algorithm, UC Berkeley's [cs188][cs188] is a good reference.

### Project 5: Kd Trees    
I only get 97 scores at this project since (my nearest() method).
1. insert() and contains() are similar as implementation in BST (consider level).    
2. nearest() only coniders rectangle(s) which would contains closer point (consider 
using RectHW's distanceSquaredTo() (or distanceTo()) method). 



[algs4]: https://algs4.cs.princeton.edu/   
[cs188]: https://inst.eecs.berkeley.edu/~cs188/
[part1]: https://www.coursera.org/learn/algorithms-part1   
[part2]: https://www.coursera.org/learn/algorithms-part2   
[hello]: https://coursera.cs.princeton.edu/algs4/assignments/hello/specification.php   
[percolation]: https://coursera.cs.princeton.edu/algs4/assignments/percolation/specification.php   
[queues]: https://coursera.cs.princeton.edu/algs4/assignments/queues/specification.php   
[collinear]: https://coursera.cs.princeton.edu/algs4/assignments/collinear/specification.php   
[8-puzzle]: https://coursera.cs.princeton.edu/algs4/assignments/8puzzle/specification.php
[kdtree]: https://coursera.cs.princeton.edu/algs4/assignments/kdtree/specification.php
