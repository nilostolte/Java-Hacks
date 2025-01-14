# SVGspecular - Phong shading for SVG radial gradients

[This](SVGspecular.java) is a single class program that creates an **SVG** file containing a square with a non-linear radial gradient based on Phong shading function for specular highlights (function $cos^n(angle)$​). The program produces a complete standalone **SVG** file.

The **SVG** has a number of stop colors $N$ determined by the user. Other parameters, such as the
exponent of Phong's function (the $n$ of $cos^n(angle)$, not $N$), can also be supplied by the user following the program name as usually done in **CLI** mode.

### Overall algorithm

The program takes the **initial color** $\vec{C_0}$ (which is generally white in the center of a the radial
gradient) and the **last color** $\vec{C_1}$ (which is any color to be continued outside the radial gradient),
and linearly interpolates the color $\vec{C}$ for each **stop color** based on a multidimensional parametric line equation, where the value of the parameter $t$ is given by the result of applying an $angle$ $\alpha$ to the function $cos^n(angle)$. In other words, this procedure is shown below:

```math
  \begin{aligned}
     &t = cos^n(\alpha)\\
     &\vec{C} = \vec{C_1}+ (\vec{C_0} - \vec{C_1}) \cdot t\\
     &\\
  \end{aligned}
 ```

Notice that the individual color components need to be processed independently. For simplicity and easier comprehension, the colors are dealt here as a sort of 3D (or 4D, including the alpha channel) vector representation. Indeed, colors can be represented as vectors where each color component corresponds to a different dimension.

### Complete algorithm

One can now devise the complete algorithm. A gradient in **SVG** also needs the **offset** of each stop color from the center of the gradient (considered the gradient's origin). In the algorithm it's called $x$, a variable representing the offset with values ranging from $0$ to $1$. 

Let's start with an $\alpha=0^\circ$, and then increment $\alpha$ with an angular step $\epsilon_\alpha$ at each iteration. Likewise, let's start the first stop color at $x = 0$, the center of the gradient, and increment $x$ with a linear step $\epsilon$ at each iteration. In other words, the pseudocode for the overall procedure described so far is shown below:

```math
 \begin{aligned}
    &\epsilon = 1 / N\\
    &\epsilon_\alpha = 90^\circ / N\\
    &x = \alpha = 0\\
    &for\ (\,i\,\text{=}\,2; \;\; i\,\text{<}\,N; \;\; i\,\text{=}\,i\,\text{+}\,1 \,)\:\{\\
    &\quad x = x + \epsilon\\
    &\quad \alpha = \alpha + \epsilon_\alpha\\
    &\quad t = cos^n(\alpha)\\
    &\quad \vec{C} = \vec{C_1}+ (\vec{C_0} - \vec{C_1}) \cdot t\\
    &\}
  \end{aligned}
```
<br>

Notice that since the control of the loop is totally independent from the incremented variables $\alpha$ and $x$, starting the loop with $i\ \text{=}\ 2$ will have the effect of only discarding the first and last stop colors $\vec{C_0}$ and $\vec{C_1}$. This is done because these are the input colors, and since they are obviously already known in advance they don't need to be calculated.

Evidently, the algorithm above omits many details in order to simplify the logic, to show only the essential information, and to enhance comprehension. It only shows how the program calculates $\vec{C}$ and the offset $x$ for each stop color. To further contrast the algorithm from the actual [code](SVGspecular.java), the notation doesn't follow any specific language syntax either, but just borrows some similar structures from most languages.

Also of note, the names of the variables used in the pseudocode are given to enhance the algorithm comprehension and they don't correspond to the actual variable names.

### Phong shading

Phong shading is a realistic shading algorithm for 3D scenes that calculates the color of each pixel by making the $intensity$ of light in each point in a surface proportional to the dot product between two unitary vectors: $\vec{L}$ and $\vec{N}$, as seen in $Fig. 1$. Vector $\vec{L}$ points to a point light source (or the direction of a directional light source), and $\vec{N}$ is the surface normal vector. Since both are unitary vectors, the result of the dot product is the cosine of the angle between both vectors. Since a cosine is a scalar, the $intensity$ is a scalar as well. This intensity is what's multiplied by the components of the pixel's color (or the components of the polygon's color, if no texture is applied) to produce the shading.

The most impressive feature of Phong's shading, though, is that it also includes an **specular highlight** calculation, producing spectacular glares whenever the surface reflects the light source towards the viewer, which renders the shading tremendously realistic. For this component of the shading one needs to calculate the dot product between the unitary reflected light source vector $\vec{L_r}$ and the unitary vector $\vec{V}$ defined by the direction between the point of the surface being shaded and the position of the observer (see $Fig. 1$).

<p align="center">
    <img src="phong-vectors-3d.svg">
</p>

Given the vectors and angles shown in $Fig. 1$, the Intensity $I$ for the Phong shading with a point light source is calculated by the formula:
```math
\begin{align}
&I = k_aI_a + k_dI_{id}\ (\vec{L_i}\cdot\vec{N})+k_sI_{is}\ (\vec{L_{ri}}\cdot\vec{V})^n\\
&I= k_aI_a + k_dI_{id}\ cos(\theta)+k_sI_{is}\ cos^n(\alpha)
\end{align}\tag{1}
```
Where,
- $k_a$​ is the ambient reflectivity coefficient.
- $I_a$​ is the ambient light intensity.
- $k_d$​ is the diffuse reflectivity coefficient.
- $\vec{L}$​  is the normalized light direction vector pointing to the light source.
- $\vec{N}$ is the normalized surface normal vector.
- $I_{d}$ is the diffuse light intensity for the light source.
- $k_s$​ is the specular reflectivity coefficient.
- $\vec{L_{r}}$ is the normalized reflection vector for the light source.
- $\vec{V}$ is the normalized view direction vector.
- $I_{s}$ is the specular light intensity for the light source.
- $n$ is the shininess exponent, which controls the size and sharpness of the specular highlight (see $Fig. 2$).

Extending the Phong shading for $nl$ point light sources results in:
```math
I = k_aI_a +\sum_{i=1}^{nl} (k_dI_{id}\ (\vec{L_i}\cdot\vec{N})+k_sI_{is}\ (\vec{L_{ri}}\cdot\vec{V})^n)
```
Where,
- $\vec{L_i}$​ is the normalized light direction vector pointing to light source $i$.
- $I_{id}$ is the diffuse light intensity for light source $i$.
- $\vec{L_{ri}}$ is the normalized reflection vector for light source $i$.
- $I_{is}$ is the specular light intensity for light source $i$.

### Specular Highlight

As seen in equation $(1)$, the intensity of specular highlight for a single light source is calculated by the term $k_sI_{s}\ (\vec{L_{r}}\cdot\vec{V})^n$. Notwithstanding, since $\vec{L_{r}}$ and $\vec{V}$ are normalized, $\vec{L_{r}}\cdot\vec{V} = cos(\alpha)$. For simplicity, let's assume $k_s = I_{s}\approx 1$. Therefore:
```math
\begin{align}
&intensity \approx cos^n(\alpha)
\end{align}\tag{2}
```
Let's forget now where $\alpha$ comes from, assuming it is just an arbitrary angle ranging from $0^\circ$ to $90^\circ$. Let's now analyze the shape of the curve obtained when plotting a graph through the application of equation $(2)$ to an infinite succession of angles by incrementing $\alpha$ with $\epsilon_\alpha$ such that:
```math
\epsilon_\alpha = \lim_{\Delta\alpha\to 0}\Delta\alpha
```
The curves are shown in $Fig. 2$. The left side of each curve was artificially produced by changing the sign for negative values of $\alpha$. The intention is to complete the shapes to give a better perception how the curves narrow when the exponent increases. The higher the exponent, the shiner is the surface supposed to be.

<p align="center">
    <img src="phong.svg">
</p>

Notwithstanding, the effect of the specular highlight, is strikingly similar to the ones produced by radial gradients used in vector graphics. The particular interest of using this specific specular highlight calculation to calculate step colors in radial gradients is the smooth appearance of the intensity gradient that it produces. In this context, the gradient expected would be more realistic than the usually obtainable by simply making ad-hoc choices of step colors to be linear interpolated by the gradient mechanism of  the vector graphics engine being used.
