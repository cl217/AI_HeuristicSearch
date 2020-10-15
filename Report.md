# CS440-Assignment1 Report
**Optimizations:**

We optimized our algorhythms by using various data structures, calculating h, g, and ƒ values, 

**Proposed Heuristics:**

Admissible/consistent heuristic: Manhattan Distance 
The manhattan distance is the safest option to calculate the heuristic value. If the value was overestimated, it would pose a problem in accuracy. Since it is counted block by block, the value will not be overestimated.  

Inadmissible heuristics: 
1. Euclidean Distance- Can be underestimated, but is still a good measurement.
2. Chebyshev distance- Provides a maximum distance.
3. Average of Euclidean and Manhattan Distance- Manhattan can potentially overestimate, and euclidian can underestimate. If we average them, a more accurate heuristic value can be found.
4. Manhattan/2 - Rough estimate which is an okay starting place.

**Experimental Results:** 
(5-9)

