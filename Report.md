# CS440-Assignment1 Report
**Optimizations:**

The purpose of optimization is to achieve the best, most efficient design. Adhereing to the criteria and constraints outlined by the assignment, we optimized our algorhythms by using various data structures, calculating h, g, and ƒ values, and testing our project as we worked on it. We considered factors such as productivity, runtime, efficacy, space, and more. 

**Proposed Heuristics:**

Admissible/consistent heuristic: Manhattan Distance 
The manhattan distance is the safest option to calculate the heuristic value. If the value was overestimated, it would pose a problem in accuracy. Since it is counted block by block, the value will not be overestimated.  

Inadmissible heuristics: 
1. Euclidean Distance- Can be underestimated, but is still a good measurement.
2. Chebyshev distance- Provides a maximum distance.
3. Average of Euclidean and Manhattan Distance- Manhattan can potentially overestimate, and euclidian can underestimate. If we average them, a more accurate heuristic value can be found.
4. Manhattan/2 - Rough estimate which is an okay starting place.

**Experimental Results:** 
(5) 
(6)
(7)
(8)
(9)

