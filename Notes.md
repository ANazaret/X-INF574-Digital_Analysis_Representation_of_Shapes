# INF574 - [Digital Representation and Analysis of Shapes](http://www.enseignement.polytechnique.fr/informatique/INF574/)

Informations : 

- Maks Ovsjanikov : 


- http://www.enseignement.polytechnique.fr/informatique/INF574/
- Slides access : Username `INF574`, Password `GeometryProcessing`

## Lecture 1 - Presentation

> Not dealing with images but with shapes

Geometric/depth scanning - 20$ -> LIDAR scanner

### 1) Transformations

#### Rotations 

- Euler angles : 
- Rodrigues formula : $$R(u,v,w) = I + (sin \theta )U + (1-cos \theta)U^2, ~~ U = \begin{bmatrix} 0&-w&v \\w&0&-u\\-v & u &0  \end{bmatrix}$$
- Quaternion formula : 
  - $q = \cos(\frac{\theta}{2}) + \sin(\frac{\theta}{2})(u_x i + u_yj + u_zk)$
  - $v = 0 + xi+yj+zk$
  - $R = v \mapsto qvq^*$

#### Translation

> If some transformation is not inear, just increase the dimension until it becomes



## Lecture 2 - Curves, splines

### 1) Arc-length parametrization

### 2) Curvature

- In arc-length parametrization, it is given by the 2nd derivative

  $| \kappa | = || \gamma''(s) ||_2$

- More generally: $$| \kappa | = \frac{|x'y''-y'x''|}{(x'^2+y'^2)^{3/2}} $$

- Given curve $c(t)$ let $\theta(t)$ be the angle between $c'(t)$ and the x-axis

  $| \kappa | = | \theta'(t) |$

### 3) Fundamental theorem of planar curve

Given differentiable $f$, unique curve (up to rigid motion) whose signed curvature is $f$





# Lecture 3 - Bezier



### 1) de Casteljau algorithm

##### Recursive definition $\left\{ \begin{array}{} b_i^{(0)} (t) = b_i  \\ b_i^{(r)} = (1-t) b_i^{(r-1)} + t b_{i+1}^{(r-1)}     \end{array} \right.$

Affinely invariant for one given curve : $ratio \left( b_{i}^{(r)}, b_{i}^{(r+1)}, b_{i+1}^{(r)}  \right) = \frac{t}{1-t}$ where $ratio(a,b,c) = \frac{vol_1(a,b)}{vol_1(b,c)}$

#### For $n+1$ points : the final curve is $b_0^{(n)} (t)$





### 2) Bernstein polynomials

$ B^n_i (t) := \left( \begin{array}{} n \\ i  \end{array} \right) t^i(1-t)^{n-i} $   >= 0

$\sum _i ^{n} B_i^n(t) = 1$     (Sum = 1 thus can be use as homogeneous coordinates system)

$B_i^{n+1}(t) = (1-t)B_i^n(t) + tB_{i-1}^n(t) $ 

#### Bezier curve : $b_i^{(r)} (t)  =  \sum_{j=0}^r b_{i+j} \cdot B_j^r(t)$

#### Final curve : $b_0^{(n)}(t) = \sum_{j=0}^n b_{j} \cdot B_j^n(t) $

The $B_j^n(t)$ are **barycentric coordinates** ==> Affinely invariant : $p=ub_0 + vb_1 + wb_2  \Rightarrow \phi(p)=u\phi(b_0) + v\phi(b_1) + w\phi(b_2) $ for any affine transformation $\phi$ 



### 3) Derivative 

$\frac{d}{dt}b_0^{(n)}(t) = \sum_{j=0}^{n-1} (b_{j+1} - b_j) \cdot B_j^{n-1}(t) $











