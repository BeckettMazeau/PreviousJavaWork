 
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static javax.swing.JOptionPane.showMessageDialog;
public class GUI implements ActionListener {
    //Creating list of filters for use in combobox/dropdown
    private String[] filters = {"Sepia","Monochrome","Contrast","Chroma Key Green","Chroma Key Color","Color Edges","Edge Detection w/ Color",
            "Edge Detection B&W","Rotate 90 Counterclockwise","Rotate 90 Clockwise","Crop","Flip","Left to Right Mirror",
            "Right to Left Mirror","Top Down Mirror", "Down Up Mirror","Average Blur",/*"Mosaic Blur",*/"Color Average Shift Up Left","Color Average Shift Up Right",
            "Color Average Shift Down Left","Color Average Shift Down Right"};
    //https://www.geeksforgeeks.org/introduction-to-java-swing/
    //https://docs.oracle.com/javase/8/docs/api/javax/swing/package-summary.html
    //Initialize selected image
    private String selectedImage = "./Images/beautifulCow.jpg";
    //Initializing all components of GUI
    private JFrame mainFrame = new JFrame();
    private JPanel mainPanel = new JPanel();
    private JLabel selectionPreview = new JLabel();
    private JComboBox selectionDrop = new JComboBox<>(getImagePaths());

    private JButton applyEdit = new JButton("Apply Edit");
    //https://docs.oracle.com/javase/tutorial/uiswing/components/combobox.html#uneditable
    private JLabel mainPreview = new JLabel();
    private JComboBox mainFilterDrop = new JComboBox<>(filters);
    private JComboBox mainBackgroundDrop = new JComboBox<>(getImagePaths());
    private JTextField mainFactorField = new JTextField("");
    private Label dropdownLabel = new Label("<--- Main Image | Background Image --->");
    private Label filterLabel = new Label("Filter: ");
    private JButton saveButton = new JButton("Save as: ");
    private JTextField fileNameField = new JTextField("file.jpg");


    //constructor for gui that sets it up and runs it
    public GUI() throws Exception{
        //https://stackhowto.com/how-to-add-an-image-to-a-jpanel-in-java-swing/
        //Set filler preview image.
            Image scaled = scaleImage("./Images/beautifulCow.jpg");
            selectionPreview.setIcon(new ImageIcon(scaled));
            mainPreview.setIcon(new ImageIcon(scaled));
            selectionPreview.setHorizontalAlignment(JLabel.CENTER);
            selectionPreview.setVerticalAlignment(0);
            mainPreview.setHorizontalAlignment(0);
            mainPreview.setVerticalAlignment(0);
         //Set up panel and panel layout using java swing gridbag layout
        selectionPreview.setBorder(BorderFactory.createEmptyBorder(25,25,25,25));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));
        //https://docs.oracle.com/javase/tutorial/uiswing/layout/gridbag.html
        mainPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();





        //colorizing labels and aligning for readability. java awt directly referenced due to naming conflict
        dropdownLabel.setAlignment(2);
        dropdownLabel.setBackground(new java.awt.Color(255,255,255));
        dropdownLabel.setForeground(new java.awt.Color(0,0,0));

        filterLabel.setAlignment(2);
        filterLabel.setBackground(new java.awt.Color(255,255,255));
        filterLabel.setForeground(new java.awt.Color(0,0,0));



        //Laying out components on the panel
        c.fill = GridBagConstraints.HORIZONTAL;
        //x pos
        c.gridx=0;
        //y pos
        c.gridy=0;
        //how much the grid bag layout constructor should prioritize the size of this component along the y axis
        c.weighty = 0.1;
        //how much the grid bag layout constructor should prioritize the size of this component along the y axis
        c.weightx=2;
        //adding component
        mainPanel.add(selectionDrop,c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx=1;
        c.gridy=0;
        c.weighty = 0.1;
        mainPanel.add(dropdownLabel,c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx=2;
        c.gridy=0;
        c.weighty = 0.1;
        c.weightx=2;
        mainPanel.add(mainBackgroundDrop,c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx=3;
        c.gridy=0;
        c.weighty = 0.1;
        mainPanel.add(filterLabel,c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx=4;
        c.gridy=0;
        c.weighty = 0.1;
        c.weightx=2;
        mainPanel.add(mainFilterDrop,c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx=5;
        c.gridy=0;
        c.weighty = 0.1;
        c.weightx = 5;
        //setting minimum size to insure input is always possible
        mainFactorField.setMinimumSize(new Dimension(80,28));
        //setting default state to invisible (field is only required for certain filters)
        mainFactorField.setVisible(false);
        mainPanel.add(mainFactorField,c);


        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx=0;
        c.gridy=1;
        c.weighty = 2;
        c.weightx = 2;
        mainPanel.add(selectionPreview,c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx=1;
        c.gridy=1;
        c.weighty = 2;
        c.weightx = 0.5;
        mainPanel.add(applyEdit,c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx=2;
        c.gridy=1;
        c.weighty = 2;
        c.weightx = 2;
        mainPanel.add(mainPreview,c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx=3;
        c.gridy=1;
        c.weighty = 2;
        c.weightx = 2;
        mainPanel.add(saveButton,c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx=4;
        c.gridy=1;
        c.weighty = 2;
        c.weightx = 2;
        mainPanel.add(fileNameField,c);


        //Action Listeners, check for changes to the specified component
            //Add component to actionlistener implemented on this class (GUI)
            applyEdit.addActionListener(this);
            //Sets ActionEvent command for this components actionlistener, used in actionperformed function
            applyEdit.setActionCommand("ApplyEdit");
            saveButton.addActionListener(this);
            saveButton.setActionCommand("SaveAs");
            selectionDrop.addActionListener(this);
            selectionDrop.setActionCommand("SelectionChanged");
            mainFilterDrop.addActionListener(this);
            mainFilterDrop.setActionCommand("FilterChanged");
            mainBackgroundDrop.addActionListener(this);
            mainBackgroundDrop.setActionCommand("BackgroundChanged");
        //Set Background Colors
            applyEdit.setBackground(new java.awt.Color(0,0,0));
            applyEdit.setForeground(new java.awt.Color(255, 255, 255));
            mainPanel.setBackground(new java.awt.Color(75, 32, 101));
        //add mainpanel to the mainframe
        mainFrame.add(mainPanel, BorderLayout.CENTER);
        //Set default close operation
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setTitle("OurGUI");
        //forces frame to shrink itself around interior components (mainpanel)
        mainFrame.pack();
        //sets visible
        mainFrame.setVisible(true);



    }
    //https://docs.oracle.com/javase/tutorial/uiswing/components/button.html
    //function activated by actionlisteners
    public void actionPerformed(ActionEvent e) {
        //checks which action was performed
        if ("SelectionChanged".equals(e.getActionCommand())){
                //try catch because the actionPerformed method, which is dependent on another class (actionlistener), is not able to be modified to throw exceptions.
                try {
                    //Sets currently selected image to the selected image in the dropdown
                    selectedImage = (String)selectionDrop.getSelectedItem();
                    //scales image for consistent sizing
                    Image scaled = scaleImage(selectedImage);
                    //updates selectionpreview and mainpreview to the new selection
                    selectionPreview.setIcon(new ImageIcon(scaled));
                    mainPreview.setIcon(new ImageIcon(scaled));
                } catch(Exception d){
                    System.out.println("Image Selection Error (actionPerformed)");
                }
                
            
        }
        if ("FilterChanged".equals(e.getActionCommand())){
            String filter = (String)mainFilterDrop.getSelectedItem();
            //checks which filter was selected to modify UI
            if (filter.contains("Chroma Key")){
                //discerns if simple green chroma key or specified color chroma key
                if (filter.contains("Color")){
                    //UI updates
                    mainBackgroundDrop.setVisible(true);
                    dropdownLabel.setText("<--- Main Image | Background Image --->");
                    dropdownLabel.setAlignment(1);
                    mainFactorField.setVisible(true);
                    //creates alert messages to inform user what to input and how it should be formatted.
                    showMessageDialog(null, "Select Background.");
                    //https://stackoverflow.com/questions/9119481/how-to-present-a-simple-alert-message-in-java
                    showMessageDialog(null, "Enter RGB Value and Tolerance in Text Field as R,G,B,T.");
                } else {
                    mainBackgroundDrop.setVisible(true);
                    dropdownLabel.setText("<--- Main Image | Background Image --->");
                    dropdownLabel.setAlignment(1);
                    mainFactorField.setVisible(true);
                    showMessageDialog(null, "Select Background.");
                    showMessageDialog(null, "Enter Tolerance in Text Field");
                }
            }
            else if (filter.equals("Crop")) {
                dropdownLabel.setText("<--- Main Image");
                dropdownLabel.setAlignment(0);
                mainBackgroundDrop.setVisible(false);
                mainFactorField.setVisible(true);
                showMessageDialog(null, "Enter Top, Bottom, Right, and Left Cut in Text Field as T,B,R,L.");
            }
            else if (filter.equals("Color Edges")) {
                dropdownLabel.setText("<--- Main Image");
                dropdownLabel.setAlignment(0);
                mainBackgroundDrop.setVisible(false);
                mainFactorField.setVisible(true);
                showMessageDialog(null, "Enter RGB Value and Tolerance in Text Field as R,G,B,T.");

            }
            else if (filter.contains("Edge Detection")) {
                if (filter.contains("Color")){
                    dropdownLabel.setText("<--- Main Image");
                    dropdownLabel.setAlignment(0);
                    mainBackgroundDrop.setVisible(false);
                    mainFactorField.setVisible(true);
                    showMessageDialog(null, "Enter RGB Value and Tolerance in Text Field as R,G,B,T.");
                }else {
                    mainBackgroundDrop.setVisible(false);
                    mainFactorField.setVisible(true);
                    dropdownLabel.setText("<--- Main Image");
                    dropdownLabel.setAlignment(0);
                    showMessageDialog(null, "Enter Tolerance in Text Field");
                }

            }
            else if (filter.contains("Contrast")) {
                dropdownLabel.setText("<--- Main Image");
                dropdownLabel.setAlignment(0);
                mainBackgroundDrop.setVisible(false);
                mainFactorField.setVisible(true);
                showMessageDialog(null, "Enter Contrast Factor in Text Field.");
                //re-render factor field
                mainFactorField.repaint();
            }else if (filter.contains("Blur")) {
                dropdownLabel.setText("<--- Main Image");
                dropdownLabel.setAlignment(0);
                mainBackgroundDrop.setVisible(false);
                mainFactorField.setVisible(false);

                //re-render factor field
                mainFactorField.repaint();
            }else if (filter.contains("Shift")) {
                dropdownLabel.setText("<--- Main Image");
                dropdownLabel.setAlignment(0);
                mainBackgroundDrop.setVisible(false);
                mainFactorField.setVisible(true);
                showMessageDialog(null, "Enter Fun Factor in Text Field (FILTER WILL CAUSE DISTORTION).");
                //re-render factor field
                mainFactorField.repaint();
            } else{
                mainBackgroundDrop.setVisible(false);
                mainFactorField.setVisible(false);
                dropdownLabel.setText("<--- Main Image");
                dropdownLabel.setAlignment(0);
            }
            //re-render factor field due to issues regarding it not appearing after being set to visible
            mainFactorField.repaint();
        }
        //save image button code
        if ("SaveAs".equals(e.getActionCommand())){
            //load current output image
            ColorImage image = PhotoshopDriver.loadImage("output1.jpg");
            //format filename such that the saveimage method will send the image to the "images" subdirectory
            String filename = "./Images/"+fileNameField.getText();
            //save image
            PhotoshopDriver.saveImage(filename,image);
        }
        if("ApplyEdit".equals(e.getActionCommand())) {
            try {
                //calling apply edit method
                applyEdit(selectedImage);
                //updating mainPreview of the outputted image. Selection preview left alone for reference.
                Image scaled = scaleImage(selectedImage);
                mainPreview.setIcon(new ImageIcon(scaled));

            } catch(Exception d){
                System.out.println("Image Editing Error (actionPerformed)");
                showMessageDialog(null, "Editing Error! Check input format and values if applicable.");
            }
        }

    }
    private void applyEdit(String imageLocation) throws Exception {
        //Loading in color image using photoshop driver so colorimage filters and modifications can be used
        ColorImage img = PhotoshopDriver.loadImage(imageLocation);
        String filter = (String)mainFilterDrop.getSelectedItem();
        //warning to insure proper input of arguments
        if(!(isOnlyNumbers( mainFactorField.getText()))){
            showMessageDialog(null, "ERROR: DO NOT PLACE TEXT IN THE INPUT FIELD, ONLY NUMBERS AND COMMAS");
            //return statement to end method and avoid crash
            return;
        }
        try {
            //checking which filter was utilized
            if (filter.contains("Chroma Key")) {
                //Scale background so it fits image (otherwise may cause out of bounds errors or improper fitting)
                //temporary bufferedimage for use in loadimage method to create colorimage (loadimage method modified to accomodate bufferedimage)
                //https://stackoverflow.com/questions/13605248/java-converting-image-to-bufferedimage
                //Selected background image path is retrieved from dropdown and fed to scale image function, along with the needed end width and end height.
                Image tempImage = scaleImage((String) mainBackgroundDrop.getSelectedItem(), img.getWidth(), img.getHeight());
                //new blank buffered image in int RGB format of correct size created for later use
                BufferedImage b_img = new BufferedImage(tempImage.getWidth(null), tempImage.getHeight(null), BufferedImage.TYPE_INT_RGB);
                //Using drawimage method to draw the values from the loaded image (tempimage) onto the blank image (b_img). getGraphics required to facilitate this.
                b_img.getGraphics().drawImage(tempImage, 0, 0, null);
                //Converting newly created buffered image into colorimage for use in colorimage methods
                ColorImage backgroundImg = PhotoshopDriver.loadImage(b_img);
                //checking whether to perform basic green chromakey or for specified color
                if (filter.contains("Color")) {
                    //Grab values from text field
                    String unsortedValues = mainFactorField.getText();
                    //sort using dedicated function
                    double[] values = getRGBT(unsortedValues);
                    //casting to int's for relevant values to prevent lossy conversion warnings
                    int R = (int) values[0];
                    int G = (int) values[1];
                    int B = (int) values[2];
                    double T = values[3];
                    //calling relevant colorimage function and saving
                    //all outputs go to output1.jpg to allow for stacked filters/modifications
                    PhotoshopDriver.saveImage("output1.jpg", img.chromaKey(backgroundImg, T, R, G, B));
                } else {
                    //retrieving tolerance
                    double T = Double.valueOf(mainFactorField.getText());
                    //calling relevant colorimage function and saving
                    PhotoshopDriver.saveImage("output1.jpg", img.chromaKey(backgroundImg, T));
                }
            } else if (filter.equals("Crop")) {
                //Grab values from text field
                String unsortedValues = mainFactorField.getText();
                //use getRGBT method to sort values
                double[] values = getRGBT(unsortedValues);
                //cast so method accepts variables
                int T = (int) values[0];
                int B = (int) values[1];
                int R = (int) values[2];
                int L = (int) values[3];
                //apply filter
                PhotoshopDriver.saveImage("output1.jpg", img.toCrop(T, B, R, L));
            } else if (filter.equals("Color Edges")) {
                //Grab values from text field
                String unsortedValues = mainFactorField.getText();
                double[] values = getRGBT(unsortedValues);
                int R = (int) values[0];
                int G = (int) values[1];
                int B = (int) values[2];
                double T = values[3];
                PhotoshopDriver.saveImage("output1.jpg", img.edgeDetectionC(R, G, B, T));
            } else if (filter.contains("Edge Detection")) {
                //determine which variant of edge detection is being requested
                if (filter.contains("Color")) {
                    //Grab values from text field
                    String unsortedValues = mainFactorField.getText();
                    double[] values = getRGBT(unsortedValues);
                    int R = (int) values[0];
                    int G = (int) values[1];
                    int B = (int) values[2];
                    double T = values[3];
                    PhotoshopDriver.saveImage("output1.jpg", img.edgeDetectionB(R, G, B, T));
                } else {
                    double T = Double.valueOf(mainFactorField.getText());
                    PhotoshopDriver.saveImage("output1.jpg", img.edgeDetectionBW(T));
                }
            } //Other less complex filters
            else if (filter.equals("Contrast")) {
                double F = Double.valueOf(mainFactorField.getText());
                PhotoshopDriver.saveImage("output1.jpg", img.toContrast(F));
            } else if (filter.equals("Average Blur")) {
                PhotoshopDriver.saveImage("output1.jpg", img.blur());
            } else if (filter.equals("Color Average Shift Up Left")) {
                double F = Double.valueOf(mainFactorField.getText());
                PhotoshopDriver.saveImage("output1.jpg", img.shiftPerspective(F, ColorImage.UP_LEFT));
            } else if (filter.equals("Color Average Shift Up Right")) {
                double F = Double.valueOf(mainFactorField.getText());
                PhotoshopDriver.saveImage("output1.jpg", img.shiftPerspective(F, ColorImage.UP_RIGHT));
            } else if (filter.equals("Color Average Shift Down Left")) {
                double F = Double.valueOf(mainFactorField.getText());
                PhotoshopDriver.saveImage("output1.jpg", img.shiftPerspective(F, ColorImage.DOWN_LEFT));
            } else if (filter.equals("Color Average Shift Down Right")) {
                double F = Double.valueOf(mainFactorField.getText());
                PhotoshopDriver.saveImage("output1.jpg", img.shiftPerspective(F, ColorImage.DOWN_RIGHT));
            } else if (filter.equals("Sepia")) {
                PhotoshopDriver.saveImage("output1.jpg", img.toSepia());
            } else if (filter.equals("Monochrome")) {
                PhotoshopDriver.saveImage("output1.jpg", img.toMonochrome());
            } else if (filter.equals("Rotate 90 Counterclockwise")) {
                PhotoshopDriver.saveImage("output1.jpg", img.toRotate90Counter());
            } else if (filter.equals("Rotate 90 Clockwise")) {
                PhotoshopDriver.saveImage("output1.jpg", img.toRotate90Clock());
            } else if (filter.equals("Flip")) {
                PhotoshopDriver.saveImage("output1.jpg", img.rightFlip());
            } else if (filter.equals("Left to Right Mirror")) {
                PhotoshopDriver.saveImage("output1.jpg", img.toLeftRightMirror());
            } else if (filter.equals("Right to Left Mirror")) {
                PhotoshopDriver.saveImage("output1.jpg", img.toRightLeftMirror());
            } else if (filter.equals("Top Down Mirror")) {
                PhotoshopDriver.saveImage("output1.jpg", img.toTopDownMirror());
            } else if (filter.equals("Down Up Mirror")) {
                PhotoshopDriver.saveImage("output1.jpg", img.toDownTopMirror());
            } else {
                //Show popup to let user know if for some reason image editing didn't work (in the case that, for some impossible reason, the code could not figure out which filter was selected)
                showMessageDialog(null, "Editing Error");
            }
        }catch(Exception k){
            showMessageDialog(null, "ERROR: ENSURE INPUTS ARE WITHIN MAXIMUM OR MINIMUM VALUES, SEE README FOR MORE INFO. ENSURE FORMATTING OF INPUTS IS CORRECT.");
        }
        //Change selectedImage to output whilst not changing selection preview to allow for stacked filters/modifications
        selectedImage = "output1.jpg";

    }
    private double[] getRGBT(String unsortedValues){
        //preparing values for sorting
        unsortedValues = unsortedValues.replace(" ","");
        //index of ,
        int temp = unsortedValues.indexOf(',');
        //grab value
        double R = Integer.valueOf(unsortedValues.substring(0,temp));
        //remove grabbed portion
        unsortedValues = unsortedValues.substring(temp+1,unsortedValues.length());
        //index of ,
        temp = unsortedValues.indexOf(',');
        //grab value
        double G = Integer.valueOf(unsortedValues.substring(0,temp));
        //remove grabbed portion
        unsortedValues = unsortedValues.substring(temp+1,unsortedValues.length());
        //index of ,
        temp = unsortedValues.indexOf(',');
        //grab value
        double B = Integer.valueOf(unsortedValues.substring(0,temp));
        //remove grabbed portion
        unsortedValues = unsortedValues.substring(temp+1,unsortedValues.length());
        //retrieve left over
        double T = Double.valueOf(unsortedValues);
        //package
        double[] values = {R,G,B,T};
        return values;
    }
    public Image scaleImage(String image) throws IOException {
        //retrieve specified image/image path as a buffered image for use of getScaledInstance method
        BufferedImage img = ImageIO.read(new File(image));
        //initialize variables
        int newWidth = 0;
        int newHeight = 0;
        //this function is only used to scale the previews, so it's a little different from its other version,
        // this check is done to see which part is bigger so it can be scaled properly (say we had an image 200px tall but 900px wide), scales whilst maintaining aspect ratio)
        if(img.getHeight()>img.getWidth()){
            //determine ratio height and width need to be scaled by to maintain aspect ratio
            double ratio = 500.0/img.getHeight();
            //find new height and width based on calculated ratio
            newHeight = (int)(img.getHeight()*ratio);
            newWidth = (int)(img.getWidth()*ratio);
        }else{
            double ratio = 500.0/img.getWidth();
            newHeight = (int)(img.getHeight()*ratio);
            newWidth = (int)(img.getWidth()*ratio);
        }
        //use calculated values to get a scaled image, smooth scaling used for aesthetic purposes
        Image scaled =  img.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
        return scaled;
    }
    public Image scaleImage(String image,int Width, int Height) throws IOException {
        //retrieve specified image/image path as a buffered image for use of getScaledInstance method
        BufferedImage img = ImageIO.read(new File(image));
        //Use built in getScaledInstance method to scale image
        Image scaled =  img.getScaledInstance(Width, Height, Image.SCALE_SMOOTH);
        return scaled;
    }
    public boolean isOnlyNumbers (String str){
        //https://www.geeksforgeeks.org/how-to-check-if-string-contains-only-digits-in-java/
        //This source led me to the Character class, method I used is different for my purpose.
        //Checks if there are any letters in the given string.
        for (int i = 0; i < str.length(); i++) {
            //Use Character class method to check is character is a letter
            if (Character.isLetter(str.charAt(i))){
                return false;
            }
        }
        return true;
    }
    public String[] getImagePaths(){
        //this function goes through both the root folder and the images folder to retrieve the paths of all images, allows for addition of new images
        //and the usage of already made/modified ones.
        //https://stackoverflow.com/questions/5694385/getting-the-filenames-of-all-files-in-a-folder
        //Create lists of Files in both folders
        File[] filesImages = new File("./Images").listFiles();
        File[] filesMain = new File("./").listFiles();
        //Create arraylist which will hold relative paths for any found images
        ArrayList<String> names = new ArrayList<String>();
        for (int i = 0; i < filesImages.length; i++) {
            //Check if item in list is actually file
            //Check if image is a JPG
            if(filesImages[i].isFile() && (filesImages[i].getPath().contains(".jpg") || filesImages[i].getPath().contains(".jpeg"))){
                names.add(filesImages[i].getPath());
            }
        }
        for (int i = 0; i < filesMain.length; i++) {
            if(filesMain[i].isFile() && (filesMain[i].getPath().contains(".jpg") || filesMain[i].getPath().contains(".jpeg"))){
                names.add(filesMain[i].getPath());
            }
        }
        //convert arraylist to String[]
        String[] toReturn = new String[names.size()];
        for (int i = 0; i < names.size(); i++) {
            toReturn[i] = names.get(i);
        }
        return toReturn;
    }
}

