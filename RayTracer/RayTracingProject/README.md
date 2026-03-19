# Overview
## Java Ray Tracer with Advanced GUI
This project is my implementation of a Ray Tracer in Java. It includes an advanced GUI that enables users to create and edit scenes interactively. It also features multi-threading for a more seamless user experience and better render times. Below are the key features.

### Scene Creation and Editing:
The GUI allows users to create complex scenes by adding objects (such as spheres, planes, triangles, and arbitrary polygons) and positioning them within the 3D space.
Users can adjust object properties (e.g., position, material, color) directly through the GUI. The GUI is able to update objects, as well as receive their properties and repopulate any relevant
fields with the respective values of the selected object. The GUI's save screen also features an interactive camera, which is able to turn, tilt, and move in 3D space. Whatever this preview render shows is what will be
saved in the final image. *Note: The quality of the preview is restricted to preserve the experience. The saved image will be rendered with your saved settings.*
### Rendering Features:
- Anti-aliasing: Smoothens edges and reduces jagged artifacts.
  - When we render a pixel, we are casting a ray and seeing what that ray hits, and the color it returns. However, what if we just barely miss the edge of an object? This can cause jagged edges. The fix? Anti-aliasing. The most basic anti-aliasing method simply subdivides each pixel. For example, for 9 samples, a pixel would be subdivided into 9 squares. A ray would then be cast at each square and the color retrieved. Those colors would then be averaged together to derive the color of the pixel. This can help smooth out jagged edges, as well as help make sure any thin objects are represented in your final image.
- Shadows: Accurate shadow casting for realistic lighting.
  - When checking for the color a ray returns, we need to consider whether or not there is another object between the point we are currently looking at and the light we are currently looking at. If there is an object in between, we do not consider this light in our returned color.
- Various materials: Implements the Phong reflection model as well as the mirror phong variant, lambert material model, as well as a perfect mirror material.
  - Lambert
    - A simple flat material that yields its diffuse color at varying intensity based on the intensity and incidence of light relative to the viewing angle.
  - Phong
    - Phong shading is a technique used in ray tracing to simulate the way light interacts with surfaces. It combines both diffuse reflection (for rough surfaces) and specular reflection (for shiny surfaces) to create a more realistic object. The addition of specular highlights represents how color can appear with differing intensity based on the angle of a light relative to the observed point, as well as your camera.
    - Mirror Phong
      - Similar to phong, mirror phong incorporates the idea that objects reflect a portion of the light that comes from other objects surrounding them.
  - Perfect Mirror
    - A perfect mirror that has no color of its own, and only shows a reflection of what's around it. This is done by ignoring any properties of the material itself, and simply casting a ray reflected relative to the viewing ray, and then returning the color of whatever that ray hits.
- Reflections: Objects reflect light based on their material properties.
  - Reflections are accomplished by casting out a reflected version of the viewing ray relative to the point of impact, and then returning the color of whatever point that reflected ray hits.
- Multi-threaded Rendering: Efficiently utilizes multiple CPU cores for faster rendering.
  - In the case of this project, a runnable class was created. Runnable classes will always have a run method, thus, it is possible to give a Thread object a runnable (passed into its constructor) and it will know to run said runnable once its given its start command. The thread objects themselves represent a physical thread on your CPU (essentially a place where information can be processed, the notable part to this is that threads can only complete one computation at a time). Since threads can only perform one computation at a time, for large-scale operations involving many computations, it can be best to split up the load of those computations across multiple threads, and allow them to run simultaneously. This is the essence of multi-threading. In the case of this Ray-Tracer, when rendering an image, the image is handed off in chunks (divided up along the x-axis) to multiple threads. These threads are then told to start their operations. Since the runnable takes in a reference to a ColorImage object that exists within the main thread, any modifications to it, by referencing, will be accessible from the main thread. The main thread then simply waits for all of the threads to complete their tasks before doing whatever it needs to do with the rendered image.


### How to use:
The GUI itself should guide most of the experience. First you will be brought into the settings panel where you can configure your scene render settings. Then, in the editor panel, you can choose to add objects to the default scene or to create a new scene and edit that.
Whatever scene you have selected upon exiting the editor screen is what will be rendered. Upon entering the editor panel, you need to click on the object selector and select an object type to get started. It is recommended you add an object name before clicking create new object, but it is not required.
Updating, removing, and renaming objects can be done in the tab created when clicking the Update Mode button. *Note: any updates made to objects with the same name will affect all of those objects, hence, naming is recommended.* When you are ready to render, head on over to the viewer, maneuver yourself so that the image is how you want it,
fill in your filename (you do *not* need to put .png at the end), and click save. *Note: Nothing happening and being unable to interact with the GUI after clicking save means everything is progressing well. The render will stop if it encounters any errors, and it will notify you.* After clicking save and rendering an image, you may need to swap
screens (between panels) to get the interactive preview to work again.