# Java Photoshop Project

This project is a custom-built image processing application developed in Java. It allows users to manipulate digital images through a variety of complex filters, chroma keying, and geometric transformations using a custom pixel-manipulation engine and an interactive Swing GUI. This project was made for Advanced Object-Oriented Programming III taught by Benjamin Farrar at Poly Prep Country Day School.

## Project Gallery

To see examples of the filters and transformations in action, please visit the project gallery:

**[Project Test Images & Results](https://drive.google.com/drive/folders/1T427YLbJzOFHdKQzGAMwo_J_JynVoXiG?usp=sharing)**

----------

## Getting Started

#### Prerequisites

-   **Java Development Kit (JDK) 8** or higher.
    
-   A directory named `Images` must exist in the project root to store source and output `.jpg` or `.jpeg` files.
    

#### How to Run

1.  Compile the source files: `Coolor.java`, `CoolorImage.java`, `GUII.java`, and `PhotoshopDriver.java`.
    
2.  Execute the `main` method located in **`PhotoshopDriver.java`**.
    
3.  The GUI will launch and default to a preview of `beautifulCow.jpg`.
    

----------

## 🛠️ Core Features & Filters

The application supports several image processing techniques accessible via the GUI dropdown menu:

#### Color & Artistic Filters

-   **Sepia**: Applies a warm, reddish-brown tone using specific color weights.
    
-   **Monochrome**: Converts images to grayscale by averaging RGB values.
    
-   **Contrast**: Adjusts image contrast based on a user-defined factor ($f$).
    
-   **Blur**: A 3x3 box blur that averages a pixel with its eight surrounding neighbors.
    

#### Advanced Computer Vision

-   **Edge Detection**: Detects sharp color changes using a distance formula. It can highlight edges in white on black, or in a specific color while retaining the original image.
    
-   **Chroma Key**: Replaces a background color (like green) with a different image based on a user-defined "tolerance".
    
-   **Color Average Shift**: A unique "smear" effect that creates a weighted average of pixels in a specific direction.
    

#### Geometric Transformations

-   **Rotation**: Rotate images 90 degrees clockwise or counter-clockwise.
    
-   **Mirroring**: Reflect images along the vertical or horizontal axis (Left-Right, Right-Left, Top-Down, or Down-Up).
    
-   **Crop**: Trims the image based on specified pixel cuts for Top, Bottom, Right, and Left edges.
    

----------

## User Interface Guide

1.  **Selection**: Choose your source image from the left dropdown.
    
2.  **Filter**: Select a filter from the main dropdown. If the filter requires additional data, a text field and instructions will appear.
    
3.  **Input Formatting**:
    
    -   **Single Values**: For Contrast or Tolerance, enter a single number (e.g., `1.5`).
        
    -   **RGB/T Values**: For "Color Edges" or "Chroma Key Color," enter values as `R,G,B,T` (e.g., `255,0,0,30`).
        
    -   **Crop**: Enter as `T,B,R,L` (Top, Bottom, Right, Left).
        
4.  **Processing**: Click **Apply Edit** to process. To save, enter a filename and click **Save As**.
    

----------

## Class Overview

-   **`Coolor.java`**: A custom wrapper for RGB data and color math.
    
-   **`CoolorImage.java`**: The core engine handling 2D color arrays and filter logic.
    
-   **`GUII.java`**: Manages the Swing interface, event listeners, and image scaling.
    
-   **`PhotoshopDriver.java`**: Handles file I/O and conversion between `BufferedImage` and `ColorImage`.
