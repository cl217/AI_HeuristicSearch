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
5. Time(ms), Runtime(O(n)), Memory(O(n))
	Uniform Cost
		Manhattan
			(Average) Time: 169.3, Runtime: 85339.1, Length: 100.66393466243366, Nodes Expanded: 10765.74, Memory: 32934.04
		Euclidean
			(Average) Time: 161.4, Runtime: 85339.1, Length: 100.66393466243366, Nodes Expanded: 10765.74, Memory: 32934.04
		Chebyshev
			(Average) Time: 160.8, Runtime: 85339.1, Length: 100.66393466243366, Nodes Expanded: 10765.74, Memory: 32934.04
		Avg(Eucidean+Manhattan)
			(Average) Time: 167.78, Runtime: 85339.1, Length: 100.66393466243366, Nodes Expanded: 10765.74, Memory: 32934.04
		Manhattan/2
			(Average) Time: 166.52, Runtime: 85339.1, Length: 100.66393466243366, Nodes Expanded: 10765.74, Memory: 32934.04
	A*
		Manhattan
			(Average) Time: 3.72, Runtime: 9230.88, Length: 133.43162489477584, Nodes Expanded: 1157.94, Memory: 4402.28
		Euclidean
			(Average) Time: 7.5, Runtime: 13961.82, Length: 112.98029483439484, Nodes Expanded: 1752.2, Memory: 6212.88
		Chebyshev
			(Average) Time: 16.1, Runtime: 22623.6, Length: 110.2950094990893, Nodes Expanded: 2839.18, Memory: 9460.38
		Avg(Eucidean+Manhattan)
			(Average) Time: 1.44, Runtime: 3479.92, Length: 180.51632813814905, Nodes Expanded: 435.44, Memory: 2054.08
		Manhattan/2
			(Average) Time: 40.16, Runtime: 35739.58, Length: 112.00099251423686, Nodes Expanded: 4495.24, Memory: 14433.5	
	Weighted A* (w=1.25)
		Manhattan
			(Average) Time: 2.32, Runtime: 6333.02, Length: 165.35989975712164, Nodes Expanded: 793.6, Memory: 3200.14
		Euclidean
			(Average) Time: 3.24, Runtime: 7333.44, Length: 129.83744525108995, Nodes Expanded: 918.42, Memory: 3665.18
		Chebyshev
			(Average) Time: 9.94, Runtime: 16443.88, Length: 125.20607604191908, Nodes Expanded: 2061.54, Memory: 7131.88
		Avg(Eucidean+Manhattan)
			(Average) Time: 0.54, Runtime: 1440.4, Length: 199.33486726142996, Nodes Expanded: 180.56, Memory: 1222.34
		Manhattan/2
			(Average) Time: 21.7, Runtime: 24924.38, Length: 116.01451415252235, Nodes Expanded: 3133.52, Memory: 10376.5
	Weighted A* (w=2.0)
		Manhattan
			(Average) Time: 0.7, Runtime: 1317.5, Length: 207.6181998726367, Nodes Expanded: 165.4, Memory: 1169.96
		Euclidean
			(Average) Time: 0.6, Runtime: 1286.4, Length: 179.77895350665239, Nodes Expanded: 160.86, Memory: 1157.32
		Chebyshev
			(Average) Time: 1.26, Runtime: 3724.7, Length: 178.29328414370298, Nodes Expanded: 466.72, Memory: 2112.56
		Avg(Eucidean+Manhattan)
			(Average) Time: 0.46, Runtime: 984.3, Length: 208.6058414570998, Nodes Expanded: 123.54, Memory: 1032.8
		Manhattan/2
			(Average) Time: 4.54, Runtime: 10096.74, Length: 132.01942845846065, Nodes Expanded: 1267.68, Memory: 4798.64
		
	

(6)
(7)
(8)
(9)

