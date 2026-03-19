import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.MaskFormatter;
import java.awt.GridLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import static javax.swing.JOptionPane.showMessageDialog;

/**
 * Write a description of class GUI here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class GUI implements ActionListener, KeyListener {
    //Last key trigger storage, used to insure some actions dont infinitely loop, as making certain modifications to
    //components with actionlisteners can trigger actionperformed repeatedly
    private long lastKeyTrigger = 0;
    //Instance variable to store accessible panels
    private final String[] panels = {"Settings", "Scene Creator", "Viewer"};
    //instance variable to store created scenes
    private ArrayList<CreatedScene> scenes = new ArrayList<CreatedScene>();
    //main frame for application + panel used for organization and easy movement
    private JFrame mainFrame = new JFrame();
    private JPanel mainOrganizationPanel = new JPanel();
    //JComboBox for selecting panel
    private JComboBox panelSelector = new JComboBox(panels);

    // ==== PANELS ====
    /* In this class, there are 4 'layers' of panels
     *
     * - Layer 1: The main frame
     *       This layer, and its accompanying organization panel, are where all other panels are added to in order for the
     *       part of the GUI we're looking at to be changed. This is by far the simplest.
     * - Layer 2: The Different Screens
     *       This is the "settings", "viewer", etc. These are the panels that are added and removed from layer 1.
     * - Layer 3: Component Panels:
     *       This is where things like panel controls live. They are the static elements for each screen.
     * - Layer 4: Sub-Component Panels
     *       This is where a majority of the complexity comes in. This layer is composed of things like the different object panels and material panel.
     *       Most commonly accessed collections of components are stored like this. To save on space and complexity, there are some shared components across sub-componenet panels.
     *       doing this helps to reduce the complexity of remembering which variables are used for what, and instead only requires you to know WHAT you need. To deal with this,
     *       most sub-component panels are cleared and re-initialized whenever they are brought onto the mainframe.
     *
     * - Misc. Layer: Organization Panels
     *       These are things like vertexPanelTRIANGLE. These serve to help organize many small components within other panels while still using more basic layout managers like boxlayout and gridlayout to save on
     *       complexity.
     *
     * ==== INITIALIZE PANEL METHODS ====
     * These are simple methods containing code which has been extracted from the main method body for convenience and organization.
     * These methods are all mostly identical. Each method:
     * 1. Sets up any layouts required to appropriately place components
     * 2. Adds any relevant components
     * 3. Adds any relevant placeholder text
     * 4. Adds any relevant listeners (Actionlistener, keylistener) and defines their actioncommands*/


    //Viewer Panel
        private JPanel viewerPanel = new JPanel();
        private JPanel viewerControlPanel = new JPanel();

        private JLabel mainPreview = new JLabel();
        private JTextField savedFileName = new JTextField("FileName", 10);
        private JButton saveFileButton = new JButton("Save");
        private CreatedScene currentScene;

        private void initializeViewerPanel() {
        //Creating new layout for organization
        viewerPanel.setLayout(new BoxLayout(viewerPanel, BoxLayout.Y_AXIS));
        viewerControlPanel.setLayout(new BoxLayout(viewerControlPanel, BoxLayout.X_AXIS));

        //adding tooltip text
            savedFileName.setToolTipText("Enter File Name");

        //adding components
        viewerControlPanel.add(savedFileName);
        viewerControlPanel.add(saveFileButton);
        viewerPanel.add(viewerControlPanel);
        viewerPanel.add(new JLabel("CONTROLS: W = Move Forward, S = Move Backwards, A = Move Left, D = Move Right, Arrow Keys Control Looking Direction"));
        viewerPanel.add(mainPreview);
        //Adding action and key listeners to detect user actions
        saveFileButton.addActionListener(this);
        saveFileButton.setActionCommand("saveFile");
        viewerPanel.addKeyListener(this);
        viewerPanel.setEnabled(false);
    }


    //SCENE CREATOR PANEL COMPONENTS
        private JPanel creationPanel = new JPanel();
        private JComboBox sceneSelector = new JComboBox<>();
        private JPanel creatorOrganizationPanel = new JPanel();
        private void initializeCreatorPanel() {
            //Createdscene requires 3 things, a scene, a name, and settings. To initialize our scene, we first need a camera.
            //Our camera will be created with some default settings, and put into the scene, which will then go into the created scene again with default settings.
            Camera defaultCamera = new Camera(new Point(0, 0, 0), new Vector(0, 0, -1), new Vector(0, 1, 0), 40.0, 16.0 / 9.0);
            Scene defaultScene = new Scene(defaultCamera);
            HashMap settings = new HashMap<>();
            settings.put("FoV", Integer.parseInt(FoVInput.getValue().toString()));
            settings.put("xRes", Integer.parseInt(xResInput.getValue().toString()));
            settings.put("aspect", Double.parseDouble(aspectInput1.getValue().toString()) / Double.parseDouble(aspectInput2.getValue().toString()));
            settings.put("aspectpt1", Double.parseDouble(aspectInput1.getValue().toString()));
            settings.put("aspectpt2", Double.parseDouble(aspectInput2.getValue().toString()));
            settings.put("aliasing", aliasingCheck.isSelected());
            settings.put("numSamples", Integer.parseInt(aliasingNumInput.getValue().toString()));
            settings.put("multiThread", multiThreadCheck.isSelected());
            CreatedScene defaultCreatedScene = new CreatedScene(defaultScene, "DefaultScene", settings);
            currentScene = defaultCreatedScene;
            scenes.add(defaultCreatedScene);
            //add tooltips
                objectNameInput.setToolTipText("Enter Object Name");
                sceneNameInput.setToolTipText("Enter Scene Name");


            sceneSelector.addItem(defaultCreatedScene.getSceneName());
            creatorOrganizationPanel.setLayout(new GridLayout(1, 2));
            creationPanelControls.setLayout(new BoxLayout(creationPanelControls, BoxLayout.X_AXIS));
            creationPanelControls.add(sceneSelector);
            creationPanelControls.add(objectSelector);
            creationPanelControls.add(objectNameInput);
            creationPanelControls.add(createNewObjectButton);
            creationPanelControls.add(sceneNameInput);
            creationPanelControls.add(createNewSceneButton);
            creationPanelControls.add(updateModeButton);
            sceneSelector.addActionListener(this);
            sceneSelector.setActionCommand("sceneSelector");
            createNewSceneButton.addActionListener(this);
            createNewSceneButton.setActionCommand("createNewScene");
            updateModeButton.addActionListener(this);
            updateModeButton.setActionCommand("updateMode");
            createNewObjectButton.addActionListener(this);
            createNewObjectButton.setActionCommand("createNewObject");
            objectSelector.addActionListener(this);
            objectSelector.setActionCommand("objectSelector");
            creationPanel.add(creationPanelControls);
            creationPanel.add(creatorOrganizationPanel);
            creatorOrganizationPanel.add(objectPanel);
            objectNameInput.setColumns(10);
            sceneNameInput.setColumns(10);
            updateModeButton.setBackground(new java.awt.Color(255, 0, 0));
        }
        //objects
        private final String[] objects = {"Sphere", "Triangle", "Tube", "Ring", "Cone", "PointLight"};
        private JPanel creationPanelControls = new JPanel();
        private JTextField objectNameInput = new JTextField();
        private JButton createNewObjectButton = new JButton("Create New Object");
        private JTextField sceneNameInput = new JTextField();
        private JButton createNewSceneButton = new JButton("Create New Scene");
        private JToggleButton updateModeButton = new JToggleButton("Update Mode");
        private JComboBox objectSelector = new JComboBox(objects);
        private JPanel objectPanel = new JPanel();

        //PointLight
            private JPanel pointLightPanel = new JPanel();
            private void initializePointLightPanel() {
                //Creating new gridlayout to organize components
                pointLightPanel.setLayout(new GridLayout(0, 4));
                //adding components
                pointLightPanel.add(vertex1Label);
                pointLightPanel.add(vertex1XInput);
                pointLightPanel.add(vertex1YInput);
                pointLightPanel.add(vertex1ZInput);
                pointLightPanel.add(new JLabel("Color/Intensity:"));
                pointLightPanel.add(diffuseColorButton);
            }

        //Sphere
            private JPanel spherePanel = new JPanel();
            private JPanel originPanelSPHERE = new JPanel();
            private JFormattedTextField radiusInput = new JFormattedTextField(createDecimalFormatter("#.#"));
            private void initializeSpherePanel() {
                originPanelSPHERE.setLayout(new BoxLayout(originPanelSPHERE, BoxLayout.X_AXIS));
                originPanelSPHERE.add(vertex1XInput);
                originPanelSPHERE.add(vertex1YInput);
                originPanelSPHERE.add(vertex1ZInput);

                spherePanel.setLayout(new GridLayout(0, 2));
                spherePanel.add(new JLabel("Origin X,Y,Z:"));
                spherePanel.add(originPanelSPHERE);

                spherePanel.add(new JLabel("Radius:"));
                spherePanel.add(radiusInput);
                spherePanel.add(materialPanel);
            }

        //Triangle
            private JPanel trianglePanel = new JPanel();
            private JPanel vertexPanelTRIANGLE = new JPanel();
            private JLabel vertex1Label = new JLabel("Vertex1 X,Y,Z:");
            private JFormattedTextField vertex1XInput = new JFormattedTextField(createDecimalFormatter("#.#"));
            private JFormattedTextField vertex1YInput = new JFormattedTextField(createDecimalFormatter("#.#"));
            private JFormattedTextField vertex1ZInput = new JFormattedTextField(createDecimalFormatter("#.#"));
            private JLabel vertex2Label = new JLabel("Vertex2 X,Y,Z:");
            private JFormattedTextField vertex2XInput = new JFormattedTextField(createDecimalFormatter("#.#"));
            private JFormattedTextField vertex2YInput = new JFormattedTextField(createDecimalFormatter("#.#"));
            private JFormattedTextField vertex2ZInput = new JFormattedTextField(createDecimalFormatter("#.#"));
            private JLabel vertex3Label = new JLabel("Vertex3 X,Y,Z:");
            private JFormattedTextField vertex3XInput = new JFormattedTextField(createDecimalFormatter("#.#"));
            private JFormattedTextField vertex3YInput = new JFormattedTextField(createDecimalFormatter("#.#"));
            private JFormattedTextField vertex3ZInput = new JFormattedTextField(createDecimalFormatter("#.#"));

            private void initializeTrianglePanel() {
                trianglePanel.setLayout(new BoxLayout(trianglePanel, BoxLayout.Y_AXIS));
                vertexPanelTRIANGLE.setLayout(new GridLayout(3, 4));
                vertexPanelTRIANGLE.add(vertex1Label);
                vertexPanelTRIANGLE.add(vertex1XInput);
                vertexPanelTRIANGLE.add(vertex1YInput);
                vertexPanelTRIANGLE.add(vertex1ZInput);
                vertexPanelTRIANGLE.add(vertex2Label);
                vertexPanelTRIANGLE.add(vertex2XInput);
                vertexPanelTRIANGLE.add(vertex2YInput);
                vertexPanelTRIANGLE.add(vertex2ZInput);
                vertexPanelTRIANGLE.add(vertex3Label);
                vertexPanelTRIANGLE.add(vertex3XInput);
                vertexPanelTRIANGLE.add(vertex3YInput);
                vertexPanelTRIANGLE.add(vertex3ZInput);
                trianglePanel.add(vertexPanelTRIANGLE);
                trianglePanel.add(materialPanel);
            }

        //Tube
            private JPanel vertexPanelTUBE = new JPanel();
            private JPanel tubePanel = new JPanel();

            private void initializeTubePanel() {
                tubePanel.setLayout(new BoxLayout(tubePanel, BoxLayout.Y_AXIS));
                vertexPanelTUBE.setLayout(new GridLayout(3, 4));
                vertexPanelTUBE.add(new JLabel("Base Center X,Y,Z:"));
                vertexPanelTUBE.add(vertex1XInput);
                vertexPanelTUBE.add(vertex1YInput);
                vertexPanelTUBE.add(vertex1ZInput);
                vertexPanelTUBE.add(new JLabel("Top X,Y,Z:"));
                vertexPanelTUBE.add(vertex2XInput);
                vertexPanelTUBE.add(vertex2YInput);
                vertexPanelTUBE.add(vertex2ZInput);
                vertexPanelTUBE.add(new JLabel("Radius:"));
                vertexPanelTUBE.add(radiusInput);
                tubePanel.add(vertexPanelTUBE);
                tubePanel.add(materialPanel);
            }

        //Ring
            private JPanel ringPanel = new JPanel();
            private JPanel vertexPanelRING = new JPanel();
            private JFormattedTextField radiusInput2 = new JFormattedTextField(createDecimalFormatter("#.#"));

            private void initializeRingPanel() {
                ringPanel.setLayout(new BoxLayout(ringPanel, BoxLayout.Y_AXIS));
                vertexPanelRING.setLayout(new GridLayout(3, 4));
                vertexPanelRING.add(new JLabel("Center X,Y,Z:"));
                vertexPanelRING.add(vertex1XInput);
                vertexPanelRING.add(vertex1YInput);
                vertexPanelRING.add(vertex1ZInput);
                vertexPanelRING.add(new JLabel("Normal Vector dX,dY,dZ:"));
                vertexPanelRING.add(vertex2XInput);
                vertexPanelRING.add(vertex2YInput);
                vertexPanelRING.add(vertex2ZInput);
                vertexPanelRING.add(new JLabel("Outer Radius:"));
                vertexPanelRING.add(radiusInput);
                vertexPanelRING.add(new JLabel("Inner Radius:"));
                vertexPanelRING.add(radiusInput2);
                ringPanel.add(vertexPanelRING);
                ringPanel.add(materialPanel);
            }

        //Cone
            private JPanel conePanel = new JPanel();
            private JPanel vertexPanelCONE = new JPanel();

            private void initializeConePanel() {
                conePanel.setLayout(new BoxLayout(conePanel, BoxLayout.Y_AXIS));
                vertexPanelCONE.setLayout(new GridLayout(3, 4));
                vertexPanelCONE.add(new JLabel("Base Center X,Y,Z:"));
                vertexPanelCONE.add(vertex1XInput);
                vertexPanelCONE.add(vertex1YInput);
                vertexPanelCONE.add(vertex1ZInput);
                vertexPanelCONE.add(new JLabel("Top X,Y,Z:"));
                vertexPanelCONE.add(vertex2XInput);
                vertexPanelCONE.add(vertex2YInput);
                vertexPanelCONE.add(vertex2ZInput);
                vertexPanelCONE.add(new JLabel("Radius:"));
                vertexPanelCONE.add(radiusInput);
                conePanel.add(vertexPanelCONE);
                conePanel.add(materialPanel);
            }

        //Materials
            private JPanel materialPanel = new JPanel();
            private JPanel materialPanelOrganizer = new JPanel();
            JComboBox materialSelector = new JComboBox<>(new String[]{"Lambert", "Phong", "MirrorPhong", "PerfectMirror"});

            private void initializeMaterialPanel() {

                initializeLambertPanel();

                materialPanel.setLayout(new BoxLayout(materialPanel, BoxLayout.Y_AXIS));
                materialPanel.add(materialSelector);
                materialPanel.add(materialPanelOrganizer);
                materialPanelOrganizer.add(lambertPanel);
                materialSelector.addActionListener(this);
                materialSelector.setActionCommand("materialSelector");
            }

            private JLabel diffuseLabel = new JLabel("Diffuse Color:");
            private JButton diffuseColorButton = new JButton("Choose Color");
            private JLabel specularColorLabel = new JLabel("Specular Color:");
            private JButton specularColorButton = new JButton("Choose Color");
            private JLabel specularExponentLabel = new JLabel("Choose Specular Exponent");
            private JFormattedTextField specularExponentInput = new JFormattedTextField(createDecimalFormatter("#.#"));
            //Lambert
                private JPanel lambertPanel = new JPanel();
                private void initializeLambertPanel() {
                    lambertPanel.setLayout(new BoxLayout(lambertPanel, BoxLayout.X_AXIS));
                    lambertPanel.add(diffuseLabel);
                    diffuseColorButton.addActionListener(this);
                    diffuseColorButton.setActionCommand("diffuseColorButton");
                    lambertPanel.add(diffuseColorButton);
                }

            //Phong
                long lastButtonTrigger = 0L;
                private JPanel phongPanel = new JPanel();
                private void initializePhongPanel() {

                    phongPanel.setLayout(new GridLayout(0, 2));
                    phongPanel.add(diffuseLabel);
                    diffuseColorButton.addActionListener(this);
                    diffuseColorButton.setActionCommand("diffuseColorButton");
                    phongPanel.add(diffuseColorButton);
                    specularColorButton.addActionListener(this);
                    specularColorButton.setActionCommand("specularColorButton");
                    phongPanel.add(specularColorLabel);
                    phongPanel.add(specularColorButton);
                    phongPanel.add(specularExponentLabel);
                    phongPanel.add(specularExponentInput);


                }

            //Mirror Phong
                private JPanel mirrorPhongPanel = new JPanel();
                private JLabel reflectivityLabel = new JLabel("Reflective Power:");
                private JFormattedTextField reflectivityInput = new JFormattedTextField(createDecimalFormatter("#.#"));
                private void initializeMirrorPhongPanel() {
                    mirrorPhongPanel.setLayout(new GridLayout(0, 2));
                    mirrorPhongPanel.add(diffuseLabel);
                    diffuseColorButton.addActionListener(this);
                    diffuseColorButton.setActionCommand("diffuseColorButton");
                    mirrorPhongPanel.add(diffuseColorButton);
                    specularColorButton.addActionListener(this);
                    specularColorButton.setActionCommand("specularColorButton");
                    mirrorPhongPanel.add(specularColorLabel);
                    mirrorPhongPanel.add(specularColorButton);
                    mirrorPhongPanel.add(specularExponentLabel);
                    mirrorPhongPanel.add(specularExponentInput);
                    mirrorPhongPanel.add(reflectivityLabel);
                    mirrorPhongPanel.add(reflectivityInput);
                }



    //SETTINGS PANEL COMPONENTS
        private JPanel settingsPanel = new JPanel();
        private JLabel xResLabel = new JLabel("Width Resolution");
        private JFormattedTextField xResInput = new JFormattedTextField(createDecimalFormatter("#.0", RoundingMode.HALF_UP));
        private JLabel aspectLabel = new JLabel("Aspect Ratio");
        private JPanel aspectInputOrganizerPanel = new JPanel();
        private JFormattedTextField aspectInput1 = new JFormattedTextField(createDecimalFormatter("#.0"));
        private JFormattedTextField aspectInput2 = new JFormattedTextField(createDecimalFormatter("#.0"));
        private JLabel FoVLabel = new JLabel("FOV");
        private JFormattedTextField FoVInput = new JFormattedTextField(createDecimalFormatter("#.0", RoundingMode.HALF_UP));
        private JLabel aliasingLabel = new JLabel("Aliasing On/Off");
        private JCheckBox aliasingCheck = new JCheckBox();
        private JLabel aliasingNumLabel = new JLabel("Aliasing Samples");
        private JFormattedTextField aliasingNumInput = new JFormattedTextField(createDecimalFormatter("#", RoundingMode.HALF_UP));
        private JLabel multiThreadLabel = new JLabel("Multi Thread On/Off");
        private JCheckBox multiThreadCheck = new JCheckBox();
        private JButton saveButton = new JButton("Save Settings");
        private JButton quitButton = new JButton("Quit");
        private void initializeSettingsPanel() {
            settingsPanel.removeAll();
            aspectInputOrganizerPanel.removeAll();
            aspectInputOrganizerPanel.setLayout(new BoxLayout(aspectInputOrganizerPanel, BoxLayout.X_AXIS));
            settingsPanel.add(xResLabel);
            settingsPanel.add(xResInput);
            settingsPanel.add(aspectLabel);
            aspectInputOrganizerPanel.add(aspectInput1);
            aspectInputOrganizerPanel.add(new JLabel(":"));
            aspectInputOrganizerPanel.add(aspectInput2);
            settingsPanel.add(aspectInputOrganizerPanel);
            settingsPanel.add(FoVLabel);
            settingsPanel.add(FoVInput);
            settingsPanel.add(aliasingLabel);
            settingsPanel.add(aliasingCheck);
            settingsPanel.add(aliasingNumLabel);
            settingsPanel.add(aliasingNumInput);
            settingsPanel.add(multiThreadLabel);
            settingsPanel.add(multiThreadCheck);
            settingsPanel.add(saveButton);
            settingsPanel.add(quitButton);
            quitButton.addActionListener(this);
            saveButton.addActionListener(this);

            quitButton.setActionCommand("quitButton");
            saveButton.setActionCommand("saveButton");
            xResInput.setValue(1920);
            FoVInput.setValue(40);
            aspectInput1.setValue(16);
            aspectInput1.setColumns(3);
            aspectInput2.setValue(9);
            aspectInput2.setColumns(3);
            aliasingNumInput.setValue(3);
            aliasingCheck.setSelected(true);
            multiThreadCheck.setSelected(true);


        }
    //Main Frame
        //Constraints for gridbag layout manager used on mainframe
        private GridBagConstraints panelConstraints = new GridBagConstraints();
        private GridBagConstraints c = new GridBagConstraints();
        private void initialLayoutSetup() throws Exception {

        //Set up panel and panel layout using java swing gridbag layout

        //https://docs.oracle.com/javase/tutorial/uiswing/layout/gridbag.html
        mainFrame.setLayout(new GridBagLayout());
        mainOrganizationPanel.setLayout(new GridLayout(2, 0));

        creationPanel.setLayout(new BoxLayout(creationPanel, BoxLayout.Y_AXIS));
        viewerPanel.setLayout(new GridLayout(0, 2));
        settingsPanel.setLayout(new GridLayout(0, 2));

        c.fill = GridBagConstraints.PAGE_START;
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 5;
        c.weighty = 1;

        panelConstraints.fill = GridBagConstraints.PAGE_END;
        panelConstraints.gridx = 0;
        panelConstraints.gridy = 1;
        panelConstraints.weightx = 1;
        panelConstraints.weighty = 9;
    }
    //constructor essentially just creates the GUI and initializes anything that requires it, calling the initalize methods previously made.
    public GUI() throws Exception {

        initialLayoutSetup();
        //These two panels are the "default", and so they are initialized in advance instead of relying on the initializations in actionperformed
        initializeSpherePanel();

        initializeMaterialPanel();

        //Adding actionlisteners and keylisteners. Actionlistener triggers the actionperformed method when any type of action takes place on the listened to object. The key listener is similar, but instead listening for keystrokes
        mainFrame.setFocusable(true);
        mainOrganizationPanel.addKeyListener(this);
        viewerPanel.setFocusable(true);
        viewerPanel.addKeyListener(this);
        mainFrame.addKeyListener(this);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        mainFrame.add(panelSelector, c);
        mainFrame.add(mainOrganizationPanel, panelConstraints);

        initializePanels();


        panelSelector.addActionListener(this);
        //These action commands are essentially the "id" for anything that uses the actionlistener. Allows us to tell what the action was performed on.
        panelSelector.setActionCommand("panelSelector");


        mainOrganizationPanel.add(settingsPanel);
        mainFrame.pack();
        mainFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        mainFrame.setVisible(true);

    }

    //Helper Methods
        //Used in various JFormattedTextField's to insure only proper inputs are committed. Essentially this method takes in a pattern and produces a formatter which the
        //JFormattedTextField can read, only allowing inputs that match. The main thing to understand is that '#' implies a numerical digit, and that this space will be a 0 if a digit is absent. The '.' is a decimal separator (it's just a normal period)
        private DecimalFormat createDecimalFormatter(String s) {
            //creating new simple decimal format.
            DecimalFormat d = new DecimalFormat(s);
            return d;
        }

        private DecimalFormat createDecimalFormatter(String s, RoundingMode r) {
            //creating new simple decimal format.
            DecimalFormat d = new DecimalFormat(s);
            //defining rounding mode
            d.setRoundingMode(r);
            return d;
        }
        //Helper method to initialize all panels
        private void initializePanels() {

            initializeSettingsPanel();

            initializeCreatorPanel();

            initializeViewerPanel();
        }

        //Creates new CreatedScene object (used in actionperformed)
        private CreatedScene createNewScene(String name) {
            try {
                //invalid input protections, retrieving inputs and comparing them.

                //NOTE: You may notice that the input is being converted to a string before being parsed as an int. This is because even though the formatter prevents invalid inputs,
                //depending on exactly how the number was input, it may be a Long or a Double. Java really does not want you to cast from these, so we're using String as a middleman. you will see this a lot in actionperformed where inputs are retrieved.

                if (Integer.parseInt(FoVInput.getValue().toString()) <= 0 || Integer.parseInt(FoVInput.getValue().toString()) > 360) {
                    showMessageDialog(null, "ERROR | INVALID FOV, SETTING TO DEFAULTS");
                    initializeSettingsPanel();
                }
                if (!(Double.parseDouble(aspectInput1.getValue().toString()) / Double.parseDouble(aspectInput2.getValue().toString()) > 0)) {
                    showMessageDialog(null, "ERROR | INVALID ASPECT RATIO, SETTING TO DEFAULTS");
                    initializeSettingsPanel();
                }
                if ((int) Integer.parseInt(aliasingNumInput.getValue().toString()) < 0) {
                    showMessageDialog(null, "ERROR | INVALID SAMPLES INPUT, SETTING TO DEFAULTS");
                    initializeSettingsPanel();
                }
                //creating new camera
                Camera defaultCamera = new Camera(new Point(0, 0, 0), new Vector(0, 0, -1), new Vector(0, 1, 0), 40.0, 16.0 / 9.0);
                //creating new scene
                Scene defaultScene = new Scene(defaultCamera);

                //filling in default settings (the lazy way)
                HashMap settings = new HashMap<>();
                settings.put("FoV", Integer.parseInt(FoVInput.getValue().toString()));
                settings.put("xRes", Integer.parseInt(xResInput.getValue().toString()));
                settings.put("aspect", Double.parseDouble(aspectInput1.getValue().toString()) / Double.parseDouble(aspectInput2.getValue().toString()));
                settings.put("aspectpt1", Double.parseDouble(aspectInput1.getValue().toString()));
                settings.put("aspectpt2", Double.parseDouble(aspectInput2.getValue().toString()));
                settings.put("aliasing", aliasingCheck.isSelected());
                settings.put("numSamples", Integer.parseInt(aliasingNumInput.getValue().toString()));
                settings.put("multiThread", multiThreadCheck.isSelected());
                //initialization of scene with some filler values
                CreatedScene createdScene = new CreatedScene(defaultScene, name, settings);
                currentScene = createdScene;
                return createdScene;
            } catch (Exception error) {
                showMessageDialog(null, "ERROR | Failed to Create Scene | Message:" + error.getMessage());
            }
            return null;

        }

        //Methods to take in vertex xyz formatted textfields and return them all as a point
            private void vertex1XYZ(Point point) {
                double x = point.getX();
                double y = point.getY();
                double z = point.getZ();
                vertex1XInput.setValue(x);
                vertex1YInput.setValue(y);
                vertex1ZInput.setValue(z);
            }

            private void vertex2XYZ(Point point) {
                double x = point.getX();
                double y = point.getY();
                double z = point.getZ();
                vertex2XInput.setValue(x);
                vertex2YInput.setValue(y);
                vertex2ZInput.setValue(z);
            }

            private void vertex3XYZ(Point point) {
                double x = point.getX();
                double y = point.getY();
                double z = point.getZ();
                vertex3XInput.setValue(x);
                vertex3YInput.setValue(y);
                vertex3ZInput.setValue(z);
            }

            private void vertex3XYZ(Vector vector) {
                double x = vector.getDX();
                double y = vector.getDY();
                double z = vector.getDZ();
                vertex3XInput.setValue(x);
                vertex3YInput.setValue(y);
                vertex3ZInput.setValue(z);
            }


    /*====ACTION PERFORMED ====
     * If the panels are the body of this GUI, actionperformed is the beating heart. Contained inside this method is just about the only way that this class is able to interact with the user.
     * It is what creates the scenes, changes them, organizes them, displays them, etc. See more inside.*/

    public void actionPerformed(ActionEvent e) {

        String actionCommand = e.getActionCommand();
        if (actionCommand.equals("saveButton")) {
            //essentially same code as inside createNewScene()
            //checks to insure valid inputs
            if ((int) Integer.parseInt(FoVInput.getValue().toString()) <= 0 || (int) Integer.parseInt(FoVInput.getValue().toString()) > 360) {
                showMessageDialog(null, "ERROR | INVALID FOV");
                initializeSettingsPanel();
                return;
            }
            if (!((double) Double.parseDouble(aspectInput1.getValue().toString()) / Double.parseDouble(aspectInput2.getValue().toString()) > 0)) {
                showMessageDialog(null, "ERROR | INVALID ASPECT RATIO");
                initializeSettingsPanel();
                return;
            }
            if ((int) Integer.parseInt(aliasingNumInput.getValue().toString()) < 0) {
                showMessageDialog(null, "ERROR | INVALID SAMPLES INPUT");
                initializeSettingsPanel();
                return;
            }
            //storing settings values
            HashMap settings = new HashMap<>();
            settings.put("FoV", Integer.parseInt(FoVInput.getValue().toString()));
            settings.put("xRes", Integer.parseInt(xResInput.getValue().toString()));
            settings.put("aspect", Double.parseDouble(aspectInput1.getValue().toString()) / Double.parseDouble(aspectInput2.getValue().toString()));
            settings.put("aliasing", (boolean) aliasingCheck.isSelected());
            settings.put("aspectpt1", Double.parseDouble(aspectInput1.getValue().toString()));
            settings.put("aspectpt2", Double.parseDouble(aspectInput2.getValue().toString()));
            settings.put("numSamples", Integer.parseInt(aliasingNumInput.getValue().toString()));
            settings.put("multiThread", multiThreadCheck.isSelected());
            currentScene.setSettings(settings);
            showMessageDialog(null, "SUCCESS");
        } else if (actionCommand.equals("quitButton")) {
            mainFrame.dispose();
            System.exit(0);
        } else if (actionCommand.equals("saveFile")) {
            /*====SAVING THE FINAL IMAGE====
             * Here is where we actually save what we create. This method disables the viewerpanel and mainframe to prevent any inputs from messing up the rendering process, or taking up unnessecary resources. Disabling viewpanel is also the trigger that stops the mainpreview rendering loop
             * It also retrieves and parses the settings from the CreatedScene object for use in the rendering method. Finally, it is responsible for calling the render and writing out the image*/
            try {


                //retrieving settings
                long start = System.currentTimeMillis();
                int xRes = (int) (currentScene.getSettings().get("xRes"));
                double aspect = (double) (currentScene.getSettings().get("aspect"));
                int yRes = (int) (xRes / aspect);
                int numSamples = (int) (currentScene.getSettings().get("numSamples"));
                boolean multiThread = (boolean) (currentScene.getSettings().get("multiThread"));
                boolean aliasing = (boolean) (currentScene.getSettings().get("aliasing"));
                //Colorimage to be initialized based on whether or not using multithreading
                ColorImage colorImage;
                //disabling panel and mainframe
                viewerPanel.setEnabled(false);
                mainFrame.setEnabled(false);
                //Sleeping to let the mainpreview thread complete to free up as many cores/threads as possible. Adds a bit of extra time for small images, but is dwarfed by the
                //amount of time it saves by yielding extra cores for larger images.
                try {
                    Thread.sleep(1000);
                } catch (Exception j) {
                    //j.printStackTrace();
                }
                //selecting correct render method (thread type)
                if (multiThread) {
                    colorImage = ((Scene) currentScene.getScene()).renderMultiThread(xRes, yRes, aliasing, numSamples);
                } else {
                    colorImage = ((Scene) currentScene.getScene()).renderSingleThread(xRes, yRes, aliasing, numSamples);
                }

                //Writing out image
                //RaytracerDriver.saveImage(savedFileName.getText() + ".png", colorImage); <--- This, for some unimaginable reason, does not work
                BufferedImage bi = colorImage.toBufferedImage();
                ImageIO.write(bi, "PNG", new File(savedFileName.getText() + ".png"));
                //re-eneabling panel and frame
                viewerPanel.setEnabled(true);
                mainFrame.setEnabled(true);
                long end = System.currentTimeMillis();
                long diff = end - start;
                long temp = 0L;
                long seconds = TimeUnit.MILLISECONDS.toSeconds(diff);
                temp = seconds;
                seconds = seconds%60;
                long minutes = TimeUnit.SECONDS.toMinutes(temp-seconds);
                temp = minutes;
                minutes = minutes%60;
                long hours = TimeUnit.MINUTES.toHours(temp-minutes);
                showMessageDialog(null, "SUCCESS | Time to Complete: "+hours+"h : "+minutes+"m : "+seconds+"s | Please switch panel to continue.");
            } catch (Exception ex) {
                //ex.printStackTrace();
                showMessageDialog(null, "ERROR | Saving Failed");

            }

        } else if (actionCommand.equals("sceneSelector")) {
            //simple swap of currentscene variable, however, we have to use names as the sceneselector stores them as strings
            for (CreatedScene createdScene : scenes) {
                if (createdScene.getSceneName().equals((String) sceneSelector.getSelectedItem())) {
                    currentScene = createdScene;
                    break;
                }
            }
        } else if (actionCommand.equals("createNewScene")) {

            //NOTE: Like many other objects in this class, this button has two uses. Based on whether or not we are in update mode, this button will either create a new scene, or remove an object from the selected scene.

            if (!(updateModeButton.isSelected())) {
                //creating new createdscene
                CreatedScene newScene = createNewScene(sceneNameInput.getText());
                if (newScene != null) {
                    //adding scene to sceneslist
                    scenes.add(newScene);
                    //updating sceneselector
                    sceneSelector.addItem(newScene.getSceneName());
                    sceneSelector.setSelectedIndex(sceneSelector.getItemCount() - 1);
                    currentScene = newScene;
                    showMessageDialog(null, "SUCCESS");
                } else {
                    showMessageDialog(null, "ERROR | Failed to Create Scene");
                    sceneSelector.setSelectedIndex(0);
                }
            } else {
                try {
                    //removing any objects in the scene with selected name
                    String name = (String) objectSelector.getSelectedItem();
                    currentScene.removeCreatedLight(name);
                    currentScene.removeCreatedShape(name);
                } catch (Exception error) {
                    showMessageDialog(null, "ERROR | No Object to Remove");
                }
                //updating object selector
                objectSelector.removeAllItems();
                ArrayList<CreatedShape> createdShapes = currentScene.getCreatedShapes();
                for (CreatedShape createdShape : createdShapes) {
                    objectSelector.addItem(createdShape.getName());
                }

                ArrayList<CreatedPointLight> createdLights = currentScene.getCreatedLights();
                for (CreatedPointLight createdPointLight : createdLights) {
                    objectSelector.addItem(createdPointLight.getName());
                }
                objectSelector.setSelectedIndex(0);
            }
        } else if (actionCommand.equals("updateMode")) {
            //To prevent rapid triggering from breaking GUI
            if (System.currentTimeMillis() - lastButtonTrigger > 500) {
                creationPanelControls.removeAll();
                if (updateModeButton.isSelected()) {
                    //setting up update version of editor screen

                    creationPanelControls.add(objectSelector);
                    creationPanelControls.add(objectNameInput);
                    creationPanelControls.add(createNewObjectButton);
                    creationPanelControls.add(createNewSceneButton);
                    creationPanelControls.add(updateModeButton);
                    objectSelector.removeAllItems();

                    ArrayList<CreatedShape> createdShapes = currentScene.getCreatedShapes();
                    for (CreatedShape createdShape : createdShapes) {
                        objectSelector.addItem(createdShape.getName());
                    }

                    ArrayList<CreatedPointLight> createdLights = currentScene.getCreatedLights();
                    for (CreatedPointLight createdPointLight : createdLights) {
                        objectSelector.addItem(createdPointLight.getName());
                    }

                    createNewObjectButton.setText("Update");
                    updateModeButton.setForeground(new java.awt.Color(0, 255, 0));
                    createNewSceneButton.setText("Remove Object");

                } else {

                    //setting up normal version of scene creator screen
                    createNewObjectButton.setText("Create New Object");
                    createNewSceneButton.setText("Create New Scene");
                    creationPanelControls.add(sceneSelector);
                    creationPanelControls.add(objectSelector);
                    objectSelector.removeAllItems();
                    creationPanelControls.add(objectNameInput);
                    creationPanelControls.add(createNewObjectButton);
                    creationPanelControls.add(sceneNameInput);
                    creationPanelControls.add(createNewSceneButton);
                    creationPanelControls.add(updateModeButton);

                    for (String object : objects) {
                        objectSelector.addItem(object);
                    }

                    updateModeButton.setForeground(new java.awt.Color(0, 0, 0));

                    updateModeButton.setBackground(new java.awt.Color(255, 0, 0));
                    mainFrame.revalidate();

                }
                //revalidating controls and updating last button trigger
                creationPanelControls.revalidate();
                lastButtonTrigger = System.currentTimeMillis();
            }
        } else if (actionCommand.equals("createNewObject")) {

            //NOTE: Based on whether we are in update mode, this button is either the create object button or the update object button

            if (!(updateModeButton.isSelected())) {

//                ====CREATING A NEW OBJECT====
                /*To create a new object, all this section does is check if the string currently selected by the object selector matches a certain object name. Based on that,
                 * that objects respetive parameters are taken in from the relevant inputs. Sometimes fields are repurposed, such as vertex input 2, where its xyz inputs are used as
                 * dx dy dz inputs for the normal vector for the ring object. Once all relevant inputs are collected, and stored in the correct variable type, a CreatedShape object is
                 * created and that is added to the currentscene. See ((String) objectSelector.getSelectedItem()).equals("Ring") for a breakdown*/

                if (((String) objectSelector.getSelectedItem()).equals("PointLight")) {
                    try {
                        java.awt.Color rawColor = diffuseColorButton.getBackground();
                        Color processedColor = new Color(rawColor.getRed()/255.0, rawColor.getGreen()/255.0, rawColor.getBlue()/255.0);
                        Point processedPoint = new Point((double) (Double.valueOf(vertex1XInput.getValue().toString())), (double) (Double.valueOf(vertex1YInput.getValue().toString())), (double) ((Double.valueOf(vertex1ZInput.getValue().toString()))));
                        PointLight light = new PointLight(processedColor, processedPoint);
                        CreatedPointLight createdLight = new CreatedPointLight(objectNameInput.getText(), light);
                        currentScene.addCreatedLight(createdLight);
                        showMessageDialog(null, "Successfully added Light");
                    } catch (Exception exception) {
                        showMessageDialog(null, "ERROR | Message: " + exception.getMessage());
                    }
                } else if (((String) objectSelector.getSelectedItem()).equals("Sphere")) {
                    try {

                        Point processedOrigin = new Point((double) (Double.valueOf(vertex1XInput.getValue().toString())), (double) (Double.valueOf(vertex1YInput.getValue().toString())), (double) ((Double.valueOf(vertex1ZInput.getValue().toString()))));
                        double sphereRadius = (double) (Double.valueOf(radiusInput.getValue().toString()));
                        Material newMaterial = null;
                        if (((String) materialSelector.getSelectedItem()).equals("Lambert")) {
                            java.awt.Color rawColor = diffuseColorButton.getBackground();
                            Color processedColor = new Color(rawColor.getRed()/255.0, rawColor.getGreen()/255.0, rawColor.getBlue()/255.0);
                            newMaterial = new Lambert(processedColor);
                        } else if (((String) materialSelector.getSelectedItem()).equals("Phong")) {
                            java.awt.Color rawDiffuseColor = diffuseColorButton.getBackground();
                            Color processedDiffuseColor = new Color(rawDiffuseColor.getRed(), rawDiffuseColor.getGreen(), rawDiffuseColor.getBlue());
                            java.awt.Color rawSpecularColor = specularColorButton.getBackground();
                            Color processedSpecularColor = new Color(rawSpecularColor.getRed(), rawSpecularColor.getGreen(), rawSpecularColor.getBlue());
                            double specularExp = (double) (Double.valueOf(specularExponentInput.getValue().toString()));
                            newMaterial = new Phong(processedDiffuseColor, processedSpecularColor, specularExp);
                        } else if (((String) materialSelector.getSelectedItem()).equals("PerfectMirror")) {
                            newMaterial = new PerfectMirror();
                        } else if (((String) materialSelector.getSelectedItem()).equals("MirrorPhong")) {
                            java.awt.Color rawDiffuseColor = diffuseColorButton.getBackground();
                            Color processedDiffuseColor = new Color(rawDiffuseColor.getRed(), rawDiffuseColor.getGreen(), rawDiffuseColor.getBlue());
                            java.awt.Color rawSpecularColor = specularColorButton.getBackground();
                            Color processedSpecularColor = new Color(rawSpecularColor.getRed(), rawSpecularColor.getGreen(), rawSpecularColor.getBlue());
                            double specularExp = (double) (Double.valueOf(specularExponentInput.getValue().toString()));
                            double reflPwr = (double) (Double.valueOf(reflectivityInput.getValue().toString()));
                            newMaterial = new MirrorPhong(processedDiffuseColor, processedSpecularColor, specularExp, reflPwr);
                        } else {
                            showMessageDialog(null, "ERROR | Unable to find material");
                        }
                        Sphere newSphere = new Sphere(processedOrigin, sphereRadius, newMaterial);
                        CreatedShape newCreatedShape = new CreatedShape(objectNameInput.getText(), newSphere, "Sphere");

                        currentScene.addCreatedShape(newCreatedShape);
                        showMessageDialog(null, "Successfully added Sphere");
                    } catch (Exception objectError) {
                        showMessageDialog(null, "ERROR | Message: " + objectError.getMessage());
                    }
                } else if (((String) objectSelector.getSelectedItem()).equals("Triangle")) {
                    try {
                        Point processedVertex1 = new Point((double) (Double.valueOf(vertex1XInput.getValue().toString())), (double) (Double.valueOf(vertex1YInput.getValue().toString())), (double) ((Double.valueOf(vertex1ZInput.getValue().toString()))));
                        Point processedVertex2 = new Point((double) (Double.valueOf(vertex2XInput.getValue().toString())), (double) (Double.valueOf(vertex2YInput.getValue().toString())), (double) ((Double.valueOf(vertex2ZInput.getValue().toString()))));
                        Point processedVertex3 = new Point((double) (Double.valueOf(vertex3XInput.getValue().toString())), (double) (Double.valueOf(vertex3YInput.getValue().toString())), (double) ((Double.valueOf(vertex3ZInput.getValue().toString()))));
                        Material newMaterial = null;
                        if (((String) materialSelector.getSelectedItem()).equals("Lambert")) {
                            java.awt.Color rawColor = diffuseColorButton.getBackground();
                            Color processedColor = new Color(rawColor.getRed()/255.0, rawColor.getGreen()/255.0, rawColor.getBlue()/255.0);
                            newMaterial = new Lambert(processedColor);
                        } else if (((String) materialSelector.getSelectedItem()).equals("PerfectMirror")) {
                            newMaterial = new PerfectMirror();
                        } else if (((String) materialSelector.getSelectedItem()).equals("Phong")) {
                            java.awt.Color rawDiffuseColor = diffuseColorButton.getBackground();
                            Color processedDiffuseColor = new Color(rawDiffuseColor.getRed(), rawDiffuseColor.getGreen(), rawDiffuseColor.getBlue());
                            java.awt.Color rawSpecularColor = specularColorButton.getBackground();
                            Color processedSpecularColor = new Color(rawSpecularColor.getRed(), rawSpecularColor.getGreen(), rawSpecularColor.getBlue());
                            double specularExp = (double) (Double.valueOf(specularExponentInput.getValue().toString()));
                            newMaterial = new Phong(processedDiffuseColor, processedSpecularColor, specularExp);
                        } else if (((String) materialSelector.getSelectedItem()).equals("MirrorPhong")) {
                            java.awt.Color rawDiffuseColor = diffuseColorButton.getBackground();
                            Color processedDiffuseColor = new Color(rawDiffuseColor.getRed(), rawDiffuseColor.getGreen(), rawDiffuseColor.getBlue());
                            java.awt.Color rawSpecularColor = specularColorButton.getBackground();
                            Color processedSpecularColor = new Color(rawSpecularColor.getRed(), rawSpecularColor.getGreen(), rawSpecularColor.getBlue());
                            double specularExp = (double) (Double.valueOf(specularExponentInput.getValue().toString()));
                            double reflPwr = (double) (Double.valueOf(reflectivityInput.getValue().toString()));
                            newMaterial = new MirrorPhong(processedDiffuseColor, processedSpecularColor, specularExp, reflPwr);
                        } else {
                            showMessageDialog(null, "ERROR | Unable to find material");
                        }
                        Triangle newTriangle = new Triangle(processedVertex1, processedVertex2, processedVertex3, newMaterial);
                        CreatedShape newCreatedShape = new CreatedShape(objectNameInput.getText(), newTriangle, "Triangle");
                        currentScene.addCreatedShape(newCreatedShape);
                        showMessageDialog(null, "Successfully added Triangle");
                    } catch (Exception objectError) {
                        showMessageDialog(null, "ERROR | Message: " + objectError.getMessage());
                    }
                } else if (((String) objectSelector.getSelectedItem()).equals("Tube")) {
                    try {
                        Point processedVertex1 = new Point((double) (Double.valueOf(vertex1XInput.getValue().toString())), (double) (Double.valueOf(vertex1YInput.getValue().toString())), (double) (Double.valueOf(vertex1ZInput.getValue().toString())));
                        Point processedVertex2 = new Point((double) (Double.valueOf(vertex2XInput.getValue().toString())), (double) (Double.valueOf(vertex2YInput.getValue().toString())), (double) (Double.valueOf(vertex2ZInput.getValue().toString())));
                        double tubeRadius = (double) (Double.valueOf(radiusInput.getValue().toString()));
                        Material newMaterial = null;
                        if (((String) materialSelector.getSelectedItem()).equals("Lambert")) {
                            java.awt.Color rawColor = diffuseColorButton.getBackground();
                            Color processedColor = new Color(rawColor.getRed()/255.0, rawColor.getGreen()/255.0, rawColor.getBlue()/255.0);
                            newMaterial = new Lambert(processedColor);
                        } else if (((String) materialSelector.getSelectedItem()).equals("PerfectMirror")) {
                            newMaterial = new PerfectMirror();
                        } else if (((String) materialSelector.getSelectedItem()).equals("Phong")) {
                            java.awt.Color rawDiffuseColor = diffuseColorButton.getBackground();
                            Color processedDiffuseColor = new Color(rawDiffuseColor.getRed(), rawDiffuseColor.getGreen(), rawDiffuseColor.getBlue());
                            java.awt.Color rawSpecularColor = specularColorButton.getBackground();
                            Color processedSpecularColor = new Color(rawSpecularColor.getRed(), rawSpecularColor.getGreen(), rawSpecularColor.getBlue());
                            double specularExp = (double) (Double.valueOf(specularExponentInput.getValue().toString()));
                            newMaterial = new Phong(processedDiffuseColor, processedSpecularColor, specularExp);
                        } else if (((String) materialSelector.getSelectedItem()).equals("MirrorPhong")) {
                            java.awt.Color rawDiffuseColor = diffuseColorButton.getBackground();
                            Color processedDiffuseColor = new Color(rawDiffuseColor.getRed(), rawDiffuseColor.getGreen(), rawDiffuseColor.getBlue());
                            java.awt.Color rawSpecularColor = specularColorButton.getBackground();
                            Color processedSpecularColor = new Color(rawSpecularColor.getRed(), rawSpecularColor.getGreen(), rawSpecularColor.getBlue());
                            double specularExp = (double) (Double.valueOf(specularExponentInput.getValue().toString()));
                            double reflPwr = (double) (Double.valueOf(reflectivityInput.getValue().toString()));
                            newMaterial = new MirrorPhong(processedDiffuseColor, processedSpecularColor, specularExp, reflPwr);
                        } else {
                            showMessageDialog(null, "ERROR | Unable to find material");
                        }
                        Tube newTube = new Tube(processedVertex1, processedVertex2, tubeRadius, newMaterial);
                        CreatedShape newCreatedShape = new CreatedShape(objectNameInput.getText(), newTube, "Tube");
                        currentScene.addCreatedShape(newCreatedShape);
                        showMessageDialog(null, "Successfully added Tube");
                    } catch (Exception objectError) {
                        showMessageDialog(null, "ERROR | Message: " + objectError.getMessage());
                    }
                } else if (((String) objectSelector.getSelectedItem()).equals("Ring")) {
                    try {
                        //Taking in the users inputs and storing in relevant variable. As mentioned in createnewscene, inputs need to be first converted to a string before they can be parse into their correct type. Any directional vectors (vectors not meant to represent movement) are normalized before being saved.
                        Point processedVertex1 = new Point((double) (Double.valueOf(vertex1XInput.getValue().toString())), (double) (Double.valueOf(vertex1YInput.getValue().toString())), (double) (Double.valueOf(vertex1ZInput.getValue().toString())));
                        Vector processedVector = new Vector((double) (Double.valueOf(vertex2XInput.getValue().toString())), (double) (Double.valueOf(vertex2YInput.getValue().toString())), (double) (Double.valueOf(vertex2ZInput.getValue().toString()))).normalize();
                        double outerRadius = (double) (Double.valueOf(radiusInput.getValue().toString()));
                        double innerRadius = (double) (Double.valueOf(radiusInput2.getValue().toString()));
                        //A new blank material is created, the below if tree determines the selected material and collects the required inputs. See ((String) materialSelector.getSelectedItem()).equals("Lambert") for more detail
                        Material newMaterial = null;
                        if (((String) materialSelector.getSelectedItem()).equals("Lambert")) {
                            //The selected color is stored in the background of the button that triggers the relevant color chooser. This not only functions as a convenient reminder and display of the chosen color, but is also good storage.
                            //However, this color is stored using the java.awt.color version of color. to avoid importing the java.awt.color class and creating conflicts, java.awt.color is directly referenced. Its r g and b values are then extracted and
                            //passed into our native color class. The material of the relevant type and attributes is then created and store in the newMaterial variable (placed as such to have the correct scope)
                            java.awt.Color rawColor = diffuseColorButton.getBackground();
                            Color processedColor = new Color(rawColor.getRed()/255.0, rawColor.getGreen()/255.0, rawColor.getBlue()/255.0);
                            newMaterial = new Lambert(processedColor);
                        } else if (((String) materialSelector.getSelectedItem()).equals("PerfectMirror")) {
                            //requires no inputs
                            newMaterial = new PerfectMirror();
                        } else if (((String) materialSelector.getSelectedItem()).equals("Phong")) {
                            java.awt.Color rawDiffuseColor = diffuseColorButton.getBackground();
                            Color processedDiffuseColor = new Color(rawDiffuseColor.getRed(), rawDiffuseColor.getGreen(), rawDiffuseColor.getBlue());
                            java.awt.Color rawSpecularColor = specularColorButton.getBackground();
                            Color processedSpecularColor = new Color(rawSpecularColor.getRed(), rawSpecularColor.getGreen(), rawSpecularColor.getBlue());
                            double specularExp = (double) (Double.valueOf(specularExponentInput.getValue().toString()));
                            newMaterial = new Phong(processedDiffuseColor, processedSpecularColor, specularExp);
                        } else if (((String) materialSelector.getSelectedItem()).equals("MirrorPhong")) {
                            java.awt.Color rawDiffuseColor = diffuseColorButton.getBackground();
                            Color processedDiffuseColor = new Color(rawDiffuseColor.getRed(), rawDiffuseColor.getGreen(), rawDiffuseColor.getBlue());
                            java.awt.Color rawSpecularColor = specularColorButton.getBackground();
                            Color processedSpecularColor = new Color(rawSpecularColor.getRed(), rawSpecularColor.getGreen(), rawSpecularColor.getBlue());
                            double specularExp = (double) (Double.valueOf(specularExponentInput.getValue().toString()));
                            double reflPwr = (double) (Double.valueOf(reflectivityInput.getValue().toString()));
                            newMaterial = new MirrorPhong(processedDiffuseColor, processedSpecularColor, specularExp, reflPwr);
                        } else {
                            showMessageDialog(null, "ERROR | Unable to find material");
                        }
                        //creating newRing using collected attributes
                        Ring newRing = new Ring(processedVertex1, outerRadius, innerRadius, processedVector, newMaterial);
                        //creating CreatedShape object for interaction with CreatedScene class
                        CreatedShape newCreatedShape = new CreatedShape(objectNameInput.getText(), newRing, "Ring");
                        currentScene.addCreatedShape(newCreatedShape);
                        showMessageDialog(null, "Successfully added Ring");
                    } catch (Exception objectError) {
                        showMessageDialog(null, "ERROR | Message: " + objectError.getMessage());
                    }
                } else if (((String) objectSelector.getSelectedItem()).equals("Cone")) {
                    try {
                        Point processedVertex1 = new Point((double) (Double.valueOf(vertex1XInput.getValue().toString())), (double) (Double.valueOf(vertex1YInput.getValue().toString())), (double) (Double.valueOf(vertex1ZInput.getValue().toString())));
                        Point processedVertex2 = new Point((double) (Double.valueOf(vertex2XInput.getValue().toString())), (double) (Double.valueOf(vertex2YInput.getValue().toString())), (double) (Double.valueOf(vertex2ZInput.getValue().toString())));
                        double coneRadius = (double) (Double.valueOf(radiusInput.getValue().toString()));
                        Material newMaterial = null;
                        if (((String) materialSelector.getSelectedItem()).equals("Lambert")) {
                            java.awt.Color rawColor = diffuseColorButton.getBackground();
                            Color processedColor = new Color(rawColor.getRed()/255.0, rawColor.getGreen()/255.0, rawColor.getBlue()/255.0);
                            newMaterial = new Lambert(processedColor);
                        } else if (((String) materialSelector.getSelectedItem()).equals("PerfectMirror")) {
                            newMaterial = new PerfectMirror();
                        } else if (((String) materialSelector.getSelectedItem()).equals("Phong")) {
                            java.awt.Color rawDiffuseColor = diffuseColorButton.getBackground();
                            Color processedDiffuseColor = new Color(rawDiffuseColor.getRed(), rawDiffuseColor.getGreen(), rawDiffuseColor.getBlue());
                            java.awt.Color rawSpecularColor = specularColorButton.getBackground();
                            Color processedSpecularColor = new Color(rawSpecularColor.getRed(), rawSpecularColor.getGreen(), rawSpecularColor.getBlue());
                            double specularExp = (double) (Double.valueOf(specularExponentInput.getValue().toString()));
                            newMaterial = new Phong(processedDiffuseColor, processedSpecularColor, specularExp);
                        } else if (((String) materialSelector.getSelectedItem()).equals("MirrorPhong")) {
                            java.awt.Color rawDiffuseColor = diffuseColorButton.getBackground();
                            Color processedDiffuseColor = new Color(rawDiffuseColor.getRed(), rawDiffuseColor.getGreen(), rawDiffuseColor.getBlue());
                            java.awt.Color rawSpecularColor = specularColorButton.getBackground();
                            Color processedSpecularColor = new Color(rawSpecularColor.getRed(), rawSpecularColor.getGreen(), rawSpecularColor.getBlue());
                            double specularExp = (double) (Double.valueOf(specularExponentInput.getValue().toString()));
                            double reflPwr = (double) (Double.valueOf(reflectivityInput.getValue().toString()));
                            newMaterial = new MirrorPhong(processedDiffuseColor, processedSpecularColor, specularExp, reflPwr);
                        } else {
                            showMessageDialog(null, "ERROR | Unable to find material");
                        }
                        Cone newCone = new Cone(processedVertex1, processedVertex2, coneRadius, newMaterial);
                        CreatedShape newCreatedShape = new CreatedShape(objectNameInput.getText(), newCone, "Cone");
                        currentScene.addCreatedShape(newCreatedShape);
                        showMessageDialog(null, "Successfully added Cone");
                    } catch (Exception objectError) {
                        showMessageDialog(null, "ERROR | Message: " + objectError.getMessage());
                    }
                } else {
                    showMessageDialog(null, "ERROR | No Object Type Found");
                }

            } else {
                String selectedItem = (String) objectSelector.getSelectedItem();
                String type = null;
                try {
                    type = currentScene.getCreatedShape(selectedItem).getType();
                } catch (Exception nevermind) {
                    type = currentScene.getCreatedLight(selectedItem).getType();
                }
                String name = "";

                if (type == null) {
                    showMessageDialog(null, "ERROR | Unable to find Object Type");
                    return;
                }
                try {
                    if (type.equals("PointLight")) {
                        try {
                            java.awt.Color rawColor = diffuseColorButton.getBackground();
                            Color processedColor = new Color(rawColor.getRed()/255.0, rawColor.getGreen()/255.0, rawColor.getBlue()/255.0);
                            Point processedPoint = new Point((double) (Double.valueOf(vertex1XInput.getValue().toString())), (double) (Double.valueOf(vertex1YInput.getValue().toString())), (double) ((Double.valueOf(vertex1ZInput.getValue().toString()))));
                            PointLight light = new PointLight(processedColor, processedPoint);
                            CreatedPointLight createdLight = new CreatedPointLight(selectedItem, light);
                            currentScene.updateCreatedLight(selectedItem, createdLight);
                            showMessageDialog(null, "Successfully updated Light");
                        } catch (Exception exception) {
                            showMessageDialog(null, "ERROR | Message: " + exception.getMessage());
                        }
                    } else if (type.equals("Sphere")) {
                        try {
                            Point processedOrigin = new Point((double) (Double.valueOf(vertex1XInput.getValue().toString())), (double) (Double.valueOf(vertex1YInput.getValue().toString())), (double) ((Double.valueOf(vertex1ZInput.getValue().toString()))));
                            double sphereRadius = (double) (Double.valueOf(radiusInput.getValue().toString()));
                            Material newMaterial = null;
                            if (((String) materialSelector.getSelectedItem()).equals("Lambert")) {
                                java.awt.Color rawColor = diffuseColorButton.getBackground();
                                Color processedColor = new Color(rawColor.getRed()/255.0, rawColor.getGreen()/255.0, rawColor.getBlue()/255.0);
                                newMaterial = new Lambert(processedColor);
                            } else if (((String) materialSelector.getSelectedItem()).equals("Phong")) {
                                java.awt.Color rawDiffuseColor = diffuseColorButton.getBackground();
                                Color processedDiffuseColor = new Color(rawDiffuseColor.getRed(), rawDiffuseColor.getGreen(), rawDiffuseColor.getBlue());
                                java.awt.Color rawSpecularColor = specularColorButton.getBackground();
                                Color processedSpecularColor = new Color(rawSpecularColor.getRed(), rawSpecularColor.getGreen(), rawSpecularColor.getBlue());
                                double specularExp = (double) (Double.valueOf(specularExponentInput.getValue().toString()));
                                newMaterial = new Phong(processedDiffuseColor, processedSpecularColor, specularExp);
                            }else if (((String) materialSelector.getSelectedItem()).equals("PerfectMirror")) {
                                newMaterial = new PerfectMirror();
                            } else if (((String) materialSelector.getSelectedItem()).equals("MirrorPhong")) {
                                java.awt.Color rawDiffuseColor = diffuseColorButton.getBackground();
                                Color processedDiffuseColor = new Color(rawDiffuseColor.getRed(), rawDiffuseColor.getGreen(), rawDiffuseColor.getBlue());
                                java.awt.Color rawSpecularColor = specularColorButton.getBackground();
                                Color processedSpecularColor = new Color(rawSpecularColor.getRed(), rawSpecularColor.getGreen(), rawSpecularColor.getBlue());
                                double specularExp = (double) (Double.valueOf(specularExponentInput.getValue().toString()));
                                double reflPwr = (double) (Double.valueOf(reflectivityInput.getValue().toString()));
                                newMaterial = new MirrorPhong(processedDiffuseColor, processedSpecularColor, specularExp, reflPwr);
                            } else {
                                showMessageDialog(null, "ERROR | Unable to find material");
                            }
                            Sphere newSphere = new Sphere(processedOrigin, sphereRadius, newMaterial);
                            CreatedShape newCreatedShape = new CreatedShape(selectedItem, newSphere, "Sphere");
                            currentScene.updateCreatedShape(selectedItem, newCreatedShape);
                            showMessageDialog(null, "Successfully added Sphere");
                        } catch (Exception objectError) {
                            //objectError.printStackTrace();
                            showMessageDialog(null, "ERROR | Message: " + objectError.getMessage());
                        }
                    } else if (type.equals("Triangle")) {
                        try {
                            Point processedVertex1 = new Point((double) (Double.valueOf(vertex1XInput.getValue().toString())), (double) (Double.valueOf(vertex1YInput.getValue().toString())), (double) ((Double.valueOf(vertex1ZInput.getValue().toString()))));
                            Point processedVertex2 = new Point((double) (Double.valueOf(vertex2XInput.getValue().toString())), (double) (Double.valueOf(vertex2YInput.getValue().toString())), (double) ((Double.valueOf(vertex2ZInput.getValue().toString()))));
                            Point processedVertex3 = new Point((double) (Double.valueOf(vertex3XInput.getValue().toString())), (double) (Double.valueOf(vertex3YInput.getValue().toString())), (double) ((Double.valueOf(vertex3ZInput.getValue().toString()))));
                            Material newMaterial = null;
                            if (((String) materialSelector.getSelectedItem()).equals("Lambert")) {
                                java.awt.Color rawColor = diffuseColorButton.getBackground();
                                Color processedColor = new Color(rawColor.getRed()/255.0, rawColor.getGreen()/255.0, rawColor.getBlue()/255.0);
                                newMaterial = new Lambert(processedColor);
                            }else if (((String) materialSelector.getSelectedItem()).equals("PerfectMirror")) {
                                newMaterial = new PerfectMirror();
                            } else if (((String) materialSelector.getSelectedItem()).equals("PerfectMirror")) {
                                newMaterial = new PerfectMirror();
                            } else if (((String) materialSelector.getSelectedItem()).equals("Phong")) {
                                java.awt.Color rawDiffuseColor = diffuseColorButton.getBackground();
                                Color processedDiffuseColor = new Color(rawDiffuseColor.getRed(), rawDiffuseColor.getGreen(), rawDiffuseColor.getBlue());
                                java.awt.Color rawSpecularColor = specularColorButton.getBackground();
                                Color processedSpecularColor = new Color(rawSpecularColor.getRed(), rawSpecularColor.getGreen(), rawSpecularColor.getBlue());
                                double specularExp = (double) (Double.valueOf(specularExponentInput.getValue().toString()));
                                newMaterial = new Phong(processedDiffuseColor, processedSpecularColor, specularExp);
                            } else if (((String) materialSelector.getSelectedItem()).equals("MirrorPhong")) {
                                java.awt.Color rawDiffuseColor = diffuseColorButton.getBackground();
                                Color processedDiffuseColor = new Color(rawDiffuseColor.getRed(), rawDiffuseColor.getGreen(), rawDiffuseColor.getBlue());
                                java.awt.Color rawSpecularColor = specularColorButton.getBackground();
                                Color processedSpecularColor = new Color(rawSpecularColor.getRed(), rawSpecularColor.getGreen(), rawSpecularColor.getBlue());
                                double specularExp = (double) (Double.valueOf(specularExponentInput.getValue().toString()));
                                double reflPwr = (double) (Double.valueOf(reflectivityInput.getValue().toString()));
                                newMaterial = new MirrorPhong(processedDiffuseColor, processedSpecularColor, specularExp, reflPwr);
                            } else {
                                showMessageDialog(null, "ERROR | Unable to find material");
                            }
                            Triangle newTriangle = new Triangle(processedVertex1, processedVertex2, processedVertex3, newMaterial);
                            CreatedShape newCreatedShape = new CreatedShape(selectedItem, newTriangle, "Triangle");
                            currentScene.updateCreatedShape(selectedItem, newCreatedShape);
                            showMessageDialog(null, "Successfully added Triangle");
                        } catch (Exception objectError) {
                            showMessageDialog(null, "ERROR | Message: " + objectError.getMessage());
                        }
                    } else if (type.equals("Tube")) {
                        try {
                            Point processedVertex1 = new Point((double) (Double.valueOf(vertex1XInput.getValue().toString())), (double) (Double.valueOf(vertex1YInput.getValue().toString())), (double) (Double.valueOf(vertex1ZInput.getValue().toString())));
                            Point processedVertex2 = new Point((double) (Double.valueOf(vertex2XInput.getValue().toString())), (double) (Double.valueOf(vertex2YInput.getValue().toString())), (double) (Double.valueOf(vertex2ZInput.getValue().toString())));
                            double tubeRadius = (double) (Double.valueOf(radiusInput.getValue().toString()));
                            Material newMaterial = null;
                            if (((String) materialSelector.getSelectedItem()).equals("Lambert")) {
                                java.awt.Color rawColor = diffuseColorButton.getBackground();
                                Color processedColor = new Color(rawColor.getRed()/255.0, rawColor.getGreen()/255.0, rawColor.getBlue()/255.0);
                                newMaterial = new Lambert(processedColor);
                            }else if (((String) materialSelector.getSelectedItem()).equals("PerfectMirror")) {
                                newMaterial = new PerfectMirror();
                            } else if (((String) materialSelector.getSelectedItem()).equals("PerfectMirror")) {
                                newMaterial = new PerfectMirror();
                            } else if (((String) materialSelector.getSelectedItem()).equals("Phong")) {
                                java.awt.Color rawDiffuseColor = diffuseColorButton.getBackground();
                                Color processedDiffuseColor = new Color(rawDiffuseColor.getRed(), rawDiffuseColor.getGreen(), rawDiffuseColor.getBlue());
                                java.awt.Color rawSpecularColor = specularColorButton.getBackground();
                                Color processedSpecularColor = new Color(rawSpecularColor.getRed(), rawSpecularColor.getGreen(), rawSpecularColor.getBlue());
                                double specularExp = (double) (Double.valueOf(specularExponentInput.getValue().toString()));
                                newMaterial = new Phong(processedDiffuseColor, processedSpecularColor, specularExp);
                            } else if (((String) materialSelector.getSelectedItem()).equals("MirrorPhong")) {
                                java.awt.Color rawDiffuseColor = diffuseColorButton.getBackground();
                                Color processedDiffuseColor = new Color(rawDiffuseColor.getRed(), rawDiffuseColor.getGreen(), rawDiffuseColor.getBlue());
                                java.awt.Color rawSpecularColor = specularColorButton.getBackground();
                                Color processedSpecularColor = new Color(rawSpecularColor.getRed(), rawSpecularColor.getGreen(), rawSpecularColor.getBlue());
                                double specularExp = (double) (Double.valueOf(specularExponentInput.getValue().toString()));
                                double reflPwr = (double) (Double.valueOf(reflectivityInput.getValue().toString()));
                                newMaterial = new MirrorPhong(processedDiffuseColor, processedSpecularColor, specularExp, reflPwr);
                            } else {
                                showMessageDialog(null, "ERROR | Unable to find material");
                            }
                            Tube newTube = new Tube(processedVertex1, processedVertex2, tubeRadius, newMaterial);
                            CreatedShape newCreatedShape = new CreatedShape(selectedItem, newTube, "Tube");
                            currentScene.updateCreatedShape(selectedItem, newCreatedShape);
                            showMessageDialog(null, "Successfully added Tube");
                        } catch (Exception objectError) {
                            showMessageDialog(null, "ERROR | Message: " + objectError.getMessage());
                        }
                    } else if (type.equals("Ring")) {
                        try {
                            Point processedVertex1 = new Point((double) (Double.valueOf(vertex1XInput.getValue().toString())), (double) (Double.valueOf(vertex1YInput.getValue().toString())), (double) (Double.valueOf(vertex1ZInput.getValue().toString())));
                            Vector processedVector = new Vector((double) (Double.valueOf(vertex2XInput.getValue().toString())), (double) (Double.valueOf(vertex2YInput.getValue().toString())), (double) (Double.valueOf(vertex2ZInput.getValue().toString()))).normalize();
                            double outerRadius = (double) (Double.valueOf(radiusInput.getValue().toString()));
                            double innerRadius = (double) (Double.valueOf(radiusInput2.getValue().toString()));
                            Material newMaterial = null;
                            if (((String) materialSelector.getSelectedItem()).equals("Lambert")) {
                                java.awt.Color rawColor = diffuseColorButton.getBackground();
                                Color processedColor = new Color(rawColor.getRed()/255.0, rawColor.getGreen()/255.0, rawColor.getBlue()/255.0);
                                newMaterial = new Lambert(processedColor);
                            }else if (((String) materialSelector.getSelectedItem()).equals("PerfectMirror")) {
                                newMaterial = new PerfectMirror();
                            } else if (((String) materialSelector.getSelectedItem()).equals("PerfectMirror")) {
                                newMaterial = new PerfectMirror();
                            } else if (((String) materialSelector.getSelectedItem()).equals("Phong")) {
                                java.awt.Color rawDiffuseColor = diffuseColorButton.getBackground();
                                Color processedDiffuseColor = new Color(rawDiffuseColor.getRed(), rawDiffuseColor.getGreen(), rawDiffuseColor.getBlue());
                                java.awt.Color rawSpecularColor = specularColorButton.getBackground();
                                Color processedSpecularColor = new Color(rawSpecularColor.getRed(), rawSpecularColor.getGreen(), rawSpecularColor.getBlue());
                                double specularExp = (double) (Double.valueOf(specularExponentInput.getValue().toString()));
                                newMaterial = new Phong(processedDiffuseColor, processedSpecularColor, specularExp);
                            } else if (((String) materialSelector.getSelectedItem()).equals("MirrorPhong")) {
                                java.awt.Color rawDiffuseColor = diffuseColorButton.getBackground();
                                Color processedDiffuseColor = new Color(rawDiffuseColor.getRed(), rawDiffuseColor.getGreen(), rawDiffuseColor.getBlue());
                                java.awt.Color rawSpecularColor = specularColorButton.getBackground();
                                Color processedSpecularColor = new Color(rawSpecularColor.getRed(), rawSpecularColor.getGreen(), rawSpecularColor.getBlue());
                                double specularExp = (double) (Double.valueOf(specularExponentInput.getValue().toString()));
                                double reflPwr = (double) (Double.valueOf(reflectivityInput.getValue().toString()));
                                newMaterial = new MirrorPhong(processedDiffuseColor, processedSpecularColor, specularExp, reflPwr);
                            } else {
                                showMessageDialog(null, "ERROR | Unable to find material");
                            }
                            Ring newRing = new Ring(processedVertex1, outerRadius, innerRadius, processedVector, newMaterial);
                            CreatedShape newCreatedShape = new CreatedShape(selectedItem, newRing, "Ring");
                            currentScene.updateCreatedShape(selectedItem, newCreatedShape);
                            showMessageDialog(null, "Successfully added Ring");
                        } catch (Exception objectError) {
                            showMessageDialog(null, "ERROR | Message: " + objectError.getMessage());
                        }
                    } else if (type.equals("Cone")) {
                        try {
                            Point processedVertex1 = new Point((double) (Double.valueOf(vertex1XInput.getValue().toString())), (double) (Double.valueOf(vertex1YInput.getValue().toString())), (double) (Double.valueOf(vertex1ZInput.getValue().toString())));
                            Point processedVertex2 = new Point((double) (Double.valueOf(vertex2XInput.getValue().toString())), (double) (Double.valueOf(vertex2YInput.getValue().toString())), (double) (Double.valueOf(vertex2ZInput.getValue().toString())));
                            double coneRadius = (double) (Double.valueOf(radiusInput.getValue().toString()));
                            Material newMaterial = null;
                            if (((String) materialSelector.getSelectedItem()).equals("Lambert")) {
                                java.awt.Color rawColor = diffuseColorButton.getBackground();
                                Color processedColor = new Color(rawColor.getRed()/255.0, rawColor.getGreen()/255.0, rawColor.getBlue()/255.0);
                                newMaterial = new Lambert(processedColor);
                            }else if (((String) materialSelector.getSelectedItem()).equals("PerfectMirror")) {
                                newMaterial = new PerfectMirror();
                            } else if (((String) materialSelector.getSelectedItem()).equals("PerfectMirror")) {
                                newMaterial = new PerfectMirror();
                            } else if (((String) materialSelector.getSelectedItem()).equals("Phong")) {
                                java.awt.Color rawDiffuseColor = diffuseColorButton.getBackground();
                                Color processedDiffuseColor = new Color(rawDiffuseColor.getRed(), rawDiffuseColor.getGreen(), rawDiffuseColor.getBlue());
                                java.awt.Color rawSpecularColor = specularColorButton.getBackground();
                                Color processedSpecularColor = new Color(rawSpecularColor.getRed(), rawSpecularColor.getGreen(), rawSpecularColor.getBlue());
                                double specularExp = (double) (Double.valueOf(specularExponentInput.getValue().toString()));
                                newMaterial = new Phong(processedDiffuseColor, processedSpecularColor, specularExp);
                            } else if (((String) materialSelector.getSelectedItem()).equals("MirrorPhong")) {
                                java.awt.Color rawDiffuseColor = diffuseColorButton.getBackground();
                                Color processedDiffuseColor = new Color(rawDiffuseColor.getRed(), rawDiffuseColor.getGreen(), rawDiffuseColor.getBlue());
                                java.awt.Color rawSpecularColor = specularColorButton.getBackground();
                                Color processedSpecularColor = new Color(rawSpecularColor.getRed(), rawSpecularColor.getGreen(), rawSpecularColor.getBlue());
                                double specularExp = (double) (Double.valueOf(specularExponentInput.getValue().toString()));
                                double reflPwr = (double) (Double.valueOf(reflectivityInput.getValue().toString()));
                                newMaterial = new MirrorPhong(processedDiffuseColor, processedSpecularColor, specularExp, reflPwr);
                            } else {
                                showMessageDialog(null, "ERROR | Unable to find material");
                            }
                            Cone newCone = new Cone(processedVertex1, processedVertex2, coneRadius, newMaterial);
                            CreatedShape newCreatedShape = new CreatedShape(selectedItem, newCone, "Cone");
                            currentScene.updateCreatedShape(selectedItem, newCreatedShape);
                            showMessageDialog(null, "Successfully added Cone");
                        } catch (Exception objectError) {
                            showMessageDialog(null, "ERROR | Message: " + objectError.getMessage());
                        }
                    } else {
                        showMessageDialog(null, "ERROR | No Object Type Found");
                    }

                    objectSelector.removeAllItems();
                    ArrayList<CreatedShape> createdShapes = currentScene.getCreatedShapes();
                    for (CreatedShape createdShape : createdShapes) {
                        objectSelector.addItem(createdShape.getName());
                    }

                    ArrayList<CreatedPointLight> createdLights = currentScene.getCreatedLights();
                    for (CreatedPointLight createdPointLight : createdLights) {
                        objectSelector.addItem(createdPointLight.getName());
                    }
                } catch (Exception error) {
                    //error.printStackTrace();
                }
            }
        } else if (actionCommand.equals("objectSelector")) {

            //NOTE:Affected by updateMode. Normally displays object types. In update mode, shows created objects.

            //preventing self triggering
            try {
                if (System.currentTimeMillis() - lastButtonTrigger > 150) {
                    if (!updateModeButton.isSelected()) {
                        try {
                            //remove current object panel from objectpanel and replace with relevant object panel (spherePanel, trianglePanel, etc.). see "Sphere" for additional note
                            objectPanel.removeAll();
                            if (objectSelector.getSelectedItem().equals("Sphere")) {
                                spherePanel.removeAll();
                                //re-initializing due to shared components
                                initializeSpherePanel();
                                objectPanel.add(spherePanel);

                            } else if (objectSelector.getSelectedItem().equals("Triangle")) {
                                trianglePanel.removeAll();
                                initializeTrianglePanel();
                                objectPanel.add(trianglePanel);
                            } else if (objectSelector.getSelectedItem().equals("Tube")) {
                                tubePanel.removeAll();
                                vertexPanelTUBE.removeAll();
                                initializeTubePanel();
                                objectPanel.add(tubePanel);
                            } else if (objectSelector.getSelectedItem().equals("Ring")) {
                                ringPanel.removeAll();
                                vertexPanelRING.removeAll();
                                initializeRingPanel();
                                objectPanel.add(ringPanel);
                            } else if (objectSelector.getSelectedItem().equals("Cone")) {
                                conePanel.removeAll();
                                vertexPanelCONE.removeAll();
                                initializeConePanel();
                                objectPanel.add(conePanel);
                            } else if (objectSelector.getSelectedItem().equals("PointLight")) {
                                pointLightPanel.removeAll();
                                initializePointLightPanel();
                                objectPanel.add(pointLightPanel);
                            }
                            objectPanel.revalidate();
                        } catch (Exception no) {
                            //no.printStackTrace();
                        }
                    } else {
                        //Update Fields

                        //Trying to get an object of objectSelector.getSelectedItem() name.
                        CreatedShape currentShape = currentScene.getCreatedShape((String) objectSelector.getSelectedItem());
                        CreatedPointLight createdPointLight = currentScene.getCreatedLight((String) objectSelector.getSelectedItem());
                        //blank object types
                        Surface shape = null;
                        PointLight pointLight = null;
                        String type = "";
                        //checking if both null, if this happens, I really messed up
                        //otherwise, depending on which is null, the correct object is filled in and the type is set
                        if (currentShape == null && createdPointLight == null) {
                            return;
                        } else if (currentShape == null) {
                            type = "PointLight";
                            pointLight = createdPointLight.getLight();
                        } else if (createdPointLight == null) {
                            type = currentShape.getType();
                            shape = currentShape.getShape();
                        }
                        //all components removed from object panel, see type.equals("Sphere") for more detail
                        objectPanel.removeAll();
                        if (type.equals("Sphere")) {
                            //removing all components form panel and then re-initializing
                            spherePanel.removeAll();
                            initializeSpherePanel();
                            //adding panel to object panel
                            objectPanel.add(spherePanel);
                            //retrieving stored attributes of shape
                            double radius = ((Sphere) shape).getR();
                            Point center = ((Sphere) shape).getCenter();
                            Material mat = ((Sphere) shape).getMat();
                            String matType = mat.getType();
                            //displaying stored attributes of shape
                            radiusInput.setValue(radius);
                            vertex1XInput.setValue(center.getX());
                            vertex1YInput.setValue(center.getY());
                            vertex1ZInput.setValue(center.getZ());
                            //checking material type, and then retrieving and displaying relevant values based on the attributes of material object retrieved from sphere object
                            if (matType.equals("Lambert")) {
                                materialSelector.setSelectedIndex(0);
                                diffuseColorButton.setBackground(((Color) (((Lambert) mat).getDiffuse())).toAWTColor());
                            } else if (matType.equals("Phong")) {
                                materialSelector.setSelectedIndex(1);
                                diffuseColorButton.setBackground(((Color) (((Phong) mat).getDiffuse())).toAWTColor());
                                specularColorButton.setBackground(((Color) (((Phong) mat).getSpecular())).toAWTColor());
                                specularExponentInput.setValue(((Phong) mat).getExponent());
                            } else if (matType.equals("MirrorPhong")) {
                                materialSelector.setSelectedIndex(2);
                                diffuseColorButton.setBackground(((Color) (((MirrorPhong) mat).getDiffuse())).toAWTColor());
                                specularColorButton.setBackground(((Color) (((MirrorPhong) mat).getSpecular())).toAWTColor());
                                specularExponentInput.setValue(((MirrorPhong) mat).getExponent());
                                reflectivityInput.setValue(((MirrorPhong) mat).getReflectiveness());
                            }else if (matType.equals("PerfectMirror")) {
                                materialSelector.setSelectedIndex(3);
                            } else {
                                materialSelector.setSelectedIndex(0);
                                showMessageDialog(null, "ERROR | No Material Found (UpdateMode > objectSelector > Update Fields");
                            }


                        } else if (type.equals("Triangle")) {
                            trianglePanel.removeAll();
                            initializeTrianglePanel();
                            objectPanel.add(trianglePanel);
                            Point v0 = ((Triangle) shape).getV0();
                            Point v1 = ((Triangle) shape).getV1();
                            Point v2 = ((Triangle) shape).getV2();
                            Material mat = ((Triangle) shape).getMat();
                            String matType = mat.getType();
                            vertex1XYZ(v0);
                            vertex2XYZ(v1);
                            vertex3XYZ(v2);
                            if (matType.equals("Lambert")) {
                                materialSelector.setSelectedIndex(0);
                                diffuseColorButton.setBackground(((Color) (((Lambert) mat).getDiffuse())).toAWTColor());
                            } else if (matType.equals("Phong")) {
                                materialSelector.setSelectedIndex(1);
                                diffuseColorButton.setBackground(((Color) (((Phong) mat).getDiffuse())).toAWTColor());
                                specularColorButton.setBackground(((Color) (((Phong) mat).getSpecular())).toAWTColor());
                                specularExponentInput.setValue(((Phong) mat).getExponent());
                            } else if (matType.equals("MirrorPhong")) {
                                materialSelector.setSelectedIndex(2);
                                diffuseColorButton.setBackground(((Color) (((MirrorPhong) mat).getDiffuse())).toAWTColor());
                                specularColorButton.setBackground(((Color) (((MirrorPhong) mat).getSpecular())).toAWTColor());
                                specularExponentInput.setValue(((MirrorPhong) mat).getExponent());
                                reflectivityInput.setValue(((MirrorPhong) mat).getReflectiveness());
                            }else if (matType.equals("PerfectMirror")) {
                                materialSelector.setSelectedIndex(3);
                            } else {
                                materialSelector.setSelectedIndex(0);
                                showMessageDialog(null, "ERROR | No Material Found (UpdateMode > objectSelector > Update Fields");
                            }
                        } else if (type.equals("Tube")) {
                            tubePanel.removeAll();
                            vertexPanelTUBE.removeAll();
                            initializeTubePanel();
                            objectPanel.add(tubePanel);
                            Point v0 = ((Tube) shape).getVertex1();
                            Point v1 = ((Tube) shape).getVertex2();
                            double radius = ((Tube) shape).getR();
                            Vector axis = ((Tube) shape).getAxis();
                            Material mat = ((Tube) shape).getMat();
                            String matType = mat.getType();
                            vertex1XYZ(v0);
                            vertex2XYZ(v1);
                            vertex3XYZ(axis);
                            radiusInput.setValue(radius);
                            if (matType.equals("Lambert")) {
                                materialSelector.setSelectedIndex(0);
                                diffuseColorButton.setBackground(((Color) (((Lambert) mat).getDiffuse())).toAWTColor());
                            } else if (matType.equals("Phong")) {
                                materialSelector.setSelectedIndex(1);
                                diffuseColorButton.setBackground(((Color) (((Phong) mat).getDiffuse())).toAWTColor());
                                specularColorButton.setBackground(((Color) (((Phong) mat).getSpecular())).toAWTColor());
                                specularExponentInput.setValue(((Phong) mat).getExponent());
                            } else if (matType.equals("MirrorPhong")) {
                                materialSelector.setSelectedIndex(2);
                                diffuseColorButton.setBackground(((Color) (((MirrorPhong) mat).getDiffuse())).toAWTColor());
                                specularColorButton.setBackground(((Color) (((MirrorPhong) mat).getSpecular())).toAWTColor());
                                specularExponentInput.setValue(((MirrorPhong) mat).getExponent());
                                reflectivityInput.setValue(((MirrorPhong) mat).getReflectiveness());
                            }else if (matType.equals("PerfectMirror")) {
                                materialSelector.setSelectedIndex(3);
                            } else {
                                materialSelector.setSelectedIndex(0);
                                showMessageDialog(null, "ERROR | No Material Found (UpdateMode > objectSelector > Update Fields");
                            }
                        } else if (type.equals("Ring")) {
                            ringPanel.removeAll();
                            vertexPanelRING.removeAll();
                            initializeRingPanel();
                            objectPanel.add(ringPanel);
                            Point center = ((Ring) shape).getCenter();
                            double outerradius = ((Ring) shape).getR1();
                            double innerradius = ((Ring) shape).getR2();
                            Vector norm = ((Ring) shape).getNormal();
                            Material mat = ((Ring) shape).getMat();
                            String matType = mat.getType();
                            Point fakePoint = new Point(norm.getDX(), norm.getDY(), norm.getDZ());
                            vertex2XYZ(fakePoint);
                            vertex1XYZ(center);
                            radiusInput.setValue(outerradius);
                            radiusInput2.setValue(innerradius);
                            if (matType.equals("Lambert")) {
                                materialSelector.setSelectedIndex(0);
                                diffuseColorButton.setBackground(((Color) (((Lambert) mat).getDiffuse())).toAWTColor());
                            } else if (matType.equals("Phong")) {
                                materialSelector.setSelectedIndex(1);
                                diffuseColorButton.setBackground(((Color) (((Phong) mat).getDiffuse())).toAWTColor());
                                specularColorButton.setBackground(((Color) (((Phong) mat).getSpecular())).toAWTColor());
                                specularExponentInput.setValue(((Phong) mat).getExponent());
                            } else if (matType.equals("MirrorPhong")) {
                                materialSelector.setSelectedIndex(2);
                                diffuseColorButton.setBackground(((Color) (((MirrorPhong) mat).getDiffuse())).toAWTColor());
                                specularColorButton.setBackground(((Color) (((MirrorPhong) mat).getSpecular())).toAWTColor());
                                specularExponentInput.setValue(((MirrorPhong) mat).getExponent());
                                reflectivityInput.setValue(((MirrorPhong) mat).getReflectiveness());
                            }else if (matType.equals("PerfectMirror")) {
                                materialSelector.setSelectedIndex(3);
                            } else {
                                materialSelector.setSelectedIndex(0);
                                showMessageDialog(null, "ERROR | No Material Found (UpdateMode > objectSelector > Update Fields");
                            }
                        } else if (type.equals("Cone")) {
                            conePanel.removeAll();
                            vertexPanelCONE.removeAll();
                            initializeConePanel();
                            objectPanel.add(conePanel);
                            Point v0 = ((Cone) shape).getBase();
                            Point v1 = ((Cone) shape).getPeak();
                            double radius = ((Cone) shape).getR();
                            Vector axis = ((Cone) shape).getAxis();
                            Material mat = ((Cone) shape).getMat();
                            String matType = mat.getType();
                            vertex1XYZ(v0);
                            vertex2XYZ(v1);
                            vertex3XYZ(axis);
                            radiusInput.setValue(radius);
                            if (matType.equals("Lambert")) {
                                materialSelector.setSelectedIndex(0);
                                diffuseColorButton.setBackground(((Color) (((Lambert) mat).getDiffuse())).toAWTColor());
                            } else if (matType.equals("Phong")) {
                                materialSelector.setSelectedIndex(1);
                                diffuseColorButton.setBackground(((Color) (((Phong) mat).getDiffuse())).toAWTColor());
                                specularColorButton.setBackground(((Color) (((Phong) mat).getSpecular())).toAWTColor());
                                specularExponentInput.setValue(((Phong) mat).getExponent());
                            } else if (matType.equals("MirrorPhong")) {
                                materialSelector.setSelectedIndex(2);
                                diffuseColorButton.setBackground(((Color) (((MirrorPhong) mat).getDiffuse())).toAWTColor());
                                specularColorButton.setBackground(((Color) (((MirrorPhong) mat).getSpecular())).toAWTColor());
                                specularExponentInput.setValue(((MirrorPhong) mat).getExponent());
                                reflectivityInput.setValue(((MirrorPhong) mat).getReflectiveness());
                            }else if (matType.equals("PerfectMirror")) {
                                materialSelector.setSelectedIndex(3);
                            } else {
                                materialSelector.setSelectedIndex(0);
                                showMessageDialog(null, "ERROR | No Material Found (UpdateMode > objectSelector > Update Fields");
                            }                        } else if (type.equals("PointLight")) {
                            pointLightPanel.removeAll();
                            initializePointLightPanel();
                            objectPanel.add(pointLightPanel);
                            Point location = pointLight.getPosition();
                            Color intensity = pointLight.getLightColor();
                        } else {
                            showMessageDialog(null, "ERROR | Unable to find object (CreatedObject Selector > Update)");
                        }
                        //revalidating to ensure updated
                        objectPanel.revalidate();
                    }
                }
            } catch (Exception bad) {
               // bad.printStackTrace();
            }
        } else if (actionCommand.equals("diffuseColorButton")) {
            //Cooldown to prevent actionlistener from considering setbackground as an action
            if (System.currentTimeMillis() - lastButtonTrigger > 1000) {

                /*====CHOOSING COLORS====
                 * Java swing provides an incredibly convenient, and incredibly stylish class for choosing colors: JColorChooser!
                 * Calling the static method JColorChooser.showDialog will bring up a color chooser pane, and return whatever color the user chooses (in java.awt.color format of course).
                 * Storing this chosen color in the background of its respective button is how both diffuse and specular colors are stored in this class.*/

                java.awt.Color tempColor = JColorChooser.showDialog(mainFrame, "Choose a diffuse color", new java.awt.Color(0, 0, 0));
                diffuseColorButton.setBackground(tempColor);
                lastButtonTrigger = System.currentTimeMillis();
            }
        } else if (actionCommand.equals("specularColorButton")) {
            //see actionCommand.equals("diffuseColorButton")
            //Cooldown to prevent actionlistener from considering setbackground as an action
            if (System.currentTimeMillis() - lastButtonTrigger > 1000) {
                java.awt.Color tempColor = JColorChooser.showDialog(mainFrame, "Choose a specular color", new java.awt.Color(0, 0, 0));
                specularColorButton.setBackground(tempColor);
                lastButtonTrigger = System.currentTimeMillis();
            }
        } else if (actionCommand.equals("materialSelector")) {

            materialPanelOrganizer.removeAll();

            //Update procedure is essentially the same as when creating new objects. Remove old, re-initialize, add back.
            //Nothing required for perfect mirror, so it has no if statement here
            if (materialSelector.getSelectedItem().equals("Lambert")) {
                lambertPanel.removeAll();
                initializeLambertPanel();
                materialPanelOrganizer.add(lambertPanel);
            } else if (materialSelector.getSelectedItem().equals("Phong")) {
                phongPanel.removeAll();
                initializePhongPanel();
                materialPanelOrganizer.add(phongPanel);
            } else if (materialSelector.getSelectedItem().equals("MirrorPhong")) {
                mirrorPhongPanel.removeAll();
                initializeMirrorPhongPanel();
                materialPanelOrganizer.add(mirrorPhongPanel);
            }

            //revalidating everything under the sun to make sure things show up when they're supposed to
            materialPanelOrganizer.revalidate();
            creatorOrganizationPanel.revalidate();
            mainOrganizationPanel.revalidate();
            mainFrame.revalidate();
            objectPanel.revalidate();


        } else if (actionCommand.equals("panelSelector")) {


            mainOrganizationPanel.remove(0);

            //To change panels we are removing the panel (the only component on hte mainorganization panel) and adding the new appropriate panel.
            //We are disabling any unused panels to avoid unwanted bafoonery, as well as in the case of the viewerpanel, disabling the mainpreview
            mainFrame.add(panelSelector, c);
            if (panelSelector.getSelectedItem().equals("Settings")) {
                settingsPanel.setEnabled(true);
                mainOrganizationPanel.add(settingsPanel);
                creationPanel.setEnabled(false);
                viewerPanel.setEnabled(false);
                //Retrieving all settings for currentscene and setting the fields to their respective values
                HashMap settings = currentScene.getSettings();
                xResInput.setValue(settings.get("xRes"));
                aspectInput1.setValue(settings.get("aspectpt1"));
                aspectInput2.setValue(settings.get("aspectpt2"));
                aliasingCheck.setSelected((boolean) settings.get("aliasing"));
                multiThreadCheck.setSelected((boolean) settings.get("multiThread"));
                aliasingNumInput.setValue((int) settings.get("numSamples"));
                FoVInput.setValue((int) settings.get("FoV"));


            } else if (panelSelector.getSelectedItem().equals("Scene Creator")) {
                creationPanel.setEnabled(true);
                mainOrganizationPanel.add(creationPanel);
                settingsPanel.setEnabled(false);
                viewerPanel.setEnabled(false);

            } else if (panelSelector.getSelectedItem().equals("Viewer")) {
                viewerPanel.setEnabled(true);
                mainOrganizationPanel.add(viewerPanel);
                creationPanel.setEnabled(false);
                settingsPanel.setEnabled(false);
                //Starting the viewerupdater on an alternate thread so that the code managing the preview does not get in the way of gui actions (which would all be occuring
                //on the current thread)
                //Creating runnable
                ViewerUpdater viewerUpdater = new ViewerUpdater(viewerPanel, currentScene, mainPreview);
                //Creating new thread with runnable as its executable
                Thread otherThread = new Thread(viewerUpdater);
                //starting new thread
                otherThread.start();
            }

            mainOrganizationPanel.revalidate();
            objectPanel.revalidate();
            //mainFrame.pack();

        }
    }

    /**
     * Handle the key typed event from the text field.
     */
    public void keyTyped(KeyEvent e) {
        //Trigger camera movements for respective keys
        if (viewerPanel.isEnabled()) {
            if (e.getKeyChar() == 'w' || e.getKeyChar() == 'W') {
                currentScene.moveForward();
            } else if (e.getKeyChar() == 'a' || e.getKeyChar() == 'A') {
                currentScene.moveLeft();
            } else if (e.getKeyChar() == 'd' || e.getKeyChar() == 'D') {
                currentScene.moveRight();
            } else if (e.getKeyChar() == 's' || e.getKeyChar() == 'S') {
                currentScene.moveBackward();
            } else if (e.getKeyChar() == ' ') {
                currentScene.moveUp();
            }


        }
    }

    /**
     * Handle the key-pressed event from the text field.
     */
    public void keyPressed(KeyEvent e) {
        //Trigger camera movements for respective keys
        if (viewerPanel.isEnabled()) {
            if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
                currentScene.moveDown();
            } else if (e.getKeyCode() == KeyEvent.VK_UP) {
                currentScene.lookUp();
            } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                currentScene.lookDown();
            } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                currentScene.lookLeft();
            } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                currentScene.lookRight();
            }
            lastKeyTrigger = System.currentTimeMillis();
        }
    }

    /**
     * Required because KeyListener was implemented, but is not used
     */
    public void keyReleased(KeyEvent e) {
    }

    //https://docs.oracle.com/javase/tutorial/uiswing/components/formattedtextfield.html#format


}
