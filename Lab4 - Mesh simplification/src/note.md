I added a method to the abstract class MeshSimplification 
```java
protected abstract Point_3 getNewPoint(Halfedge<Point_3> h);
```
which provides a point_3 on which to collapse the edge


This way, I moved the ``isLegal`` function to the abstract class in order to share the code