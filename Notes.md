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



s = dy/dx

dx^2 + s^2*dx^2 = 1600

dx^2 (1+s^2) = 1600



