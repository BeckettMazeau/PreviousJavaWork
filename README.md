
# Advanced Object-Oriented Programming II Portfolio

This repository contains a collection of Java projects and technical demonstrations developed for the Advanced Object-Oriented Programming II course at Poly Prep Country Day School. The work was completed under the instruction of Benjamin Farrar and focuses on high-level object-oriented design, graphical user interface (GUI) implementation, and concurrent programming.

## Repository Structure

The repository is organized into three primary sections:

## 1. Java Ray Tracer

A multi-threaded ray tracing engine capable of rendering 3D scenes with realistic lighting and material properties.

-   **Interactive GUI**: Includes a scene editor for adding and positioning spheres, planes, and polygons.
    
-   **Rendering Engine**: Utilizes multi-threading to distribute the rendering load across CPU cores for improved performance.
    
-   **Material Support**: Implements Lambertian, Phong, and Mirror Phong reflection models, as well as perfect mirrors.
    
-   **Visual Effects**: Features include anti-aliasing via pixel subdivision, recursive reflections, and accurate shadow casting.
    

## 2. Image Processing Application (Java Photoshop)

A custom image manipulation tool built with a proprietary pixel-manipulation engine and a Swing-based interface.

-   **Artistic Filters**: Supports Sepia, Monochrome, and box blur transformations.
    
-   **Computer Vision**: Includes edge detection using color distance formulas and chroma keying (green screen) with adjustable tolerance.
    
-   **Geometric Transformations**: Provides tools for 90-degree rotations, horizontal/vertical mirroring, and custom cropping.
    
-   **Color Math**: Implements contrast adjustment and a directional color average shift effect.
    

## 3. Advanced Java Class

A directory containing modular classes and short scripts designed to demonstrate core Java functionalities. These files serve as a technical reference for:

-   Standard OOP properties (Inheritance, Polymorphism, Encapsulation).
    
-   Data structure implementation and algorithm logic.
    
-   Java syntax and built-in library usage.
    

----------

## Technical Specifications

-   **Language**: Java 8 or higher.
    
-   **GUI Library**: Java Swing.
    
-   **Threading**: Java Concurrency (Runnable and Thread classes).
    
-   **Graphics**: Custom `ColorImage` and `Coolor` classes for direct pixel manipulation without relying on high-level external libraries.
