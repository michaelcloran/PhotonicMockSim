/*
Copyright Michael Cloran 2013


Licenced Software
NOTE:This application is for educational use only and not 
to be used for commercial purposes and it is
provided with no warranty thus no liability 
for damages if anything goes wrong.

It can not be used to base a project on.
Closed Source Software
*/


package Constants;
import com.photoniccomputer.htmleditor.dialogs.DocumentPropsDlg;
import com.photoniccomputer.htmleditor.dialogs.FindDialog;
import com.photoniccomputer.htmleditor.dialogs.HTMLSourceDlg;
import com.photoniccomputer.htmleditor.dialogs.TableDlg;
import com.photoniccomputer.htmleditor.dialogs.utils.DialogLayout2;
import com.photoniccomputer.htmleditor.dialogs.utils.DocumentTokenizer;
import com.photoniccomputer.htmleditor.dialogs.utils.OpenList;
import com.photoniccomputer.htmleditor.tabbedhtmleditordialog.Documents;
import com.photoniccomputer.htmleditor.tabbedhtmleditordialog.DocumentsList;
import com.photoniccomputer.htmleditor.tabbedhtmleditordialog.TabbedHTMLEditorDialog;
import com.photoniccomputer.htmleditor.utils.CustomHTMLEditorKit;
import com.photoniccomputer.htmleditor.utils.MutableHTMLDocument;
import com.photoniccomputer.photonicmocksim.dialogs.*;
import com.photoniccomputer.photonicmocksim.dialogs.blockmodel.*;
import com.photoniccomputer.photonicmocksim.dialogs.logicanalyzer.LogicAnalyzerComponent;
import com.photoniccomputer.photonicmocksim.dialogs.logicanalyzer.LogicAnalyzerFrame;
import com.photoniccomputer.photonicmocksim.dialogs.logicanalyzer.LogicAnalyzerModel;
import com.photoniccomputer.photonicmocksim.dialogs.logicanalyzer.LogicAnalyzerView;
import com.photoniccomputer.photonicmocksim.dialogs.showblockmodelcontents.ShowBlockModelContentsFrame;
import com.photoniccomputer.photonicmocksim.dialogs.showblockmodelcontents.ShowBlockModelContentsView;
import com.photoniccomputer.photonicmocksim.dialogs.textmodemonitor.TextModeMonitorComponent;
import com.photoniccomputer.photonicmocksim.dialogs.textmodemonitor.TextModeMonitorFrame;
import com.photoniccomputer.photonicmocksim.dialogs.textmodemonitor.TextModeMonitorModel;
import com.photoniccomputer.photonicmocksim.dialogs.textmodemonitor.TextModeMonitorView;
import com.photoniccomputer.photonicmocksim.utils.*;

import java.awt.*;

import java.nio.file.Path;

import java.nio.file.Paths;

import javax.swing.*;

public class PhotonicMockSimConstants {

//components
    //binary
//logical

public final static int  AND_GATE				= 101;
public final static int  AND_GATE_2INPUTPORT                    = 102;
public final static int  AND_GATE_3INPUTPORT                    = 103;
public final static int  AND_GATE_4INPUTPORT                    = 104;
public final static int  AND_GATE_5INPUTPORT                    = 105;
public final static int  AND_GATE_6INPUTPORT                    = 106;
public final static int  AND_GATE_7INPUTPORT                    = 107;
public final static int  AND_GATE_8INPUTPORT                    = 108;
    
public final static int  NAND_GATE				= 109;
public final static int  NAND_GATE_2INPUTPORT                   = 110;
public final static int  NAND_GATE_3INPUTPORT                   = 111;
public final static int  NAND_GATE_4INPUTPORT                   = 112;
public final static int  NAND_GATE_5INPUTPORT                   = 113;
public final static int  NAND_GATE_6INPUTPORT                   = 114;
public final static int  NAND_GATE_7INPUTPORT                   = 115;
public final static int  NAND_GATE_8INPUTPORT                   = 116;
    
public final static int  OR_GATE				= 117;
public final static int  OR_GATE_2INPUTPORT                     = 118;
public final static int  OR_GATE_3INPUTPORT                     = 119;
public final static int  OR_GATE_4INPUTPORT                     = 120;
public final static int  OR_GATE_5INPUTPORT                     = 121;
public final static int  OR_GATE_6INPUTPORT                     = 122;
public final static int  OR_GATE_7INPUTPORT                     = 123;
public final static int  OR_GATE_8INPUTPORT                     = 124;
    
public final static int  NOR_GATE				= 125;
public final static int  NOR_GATE_2INPUTPORT                    = 126;
public final static int  NOR_GATE_3INPUTPORT                    = 127;
public final static int  NOR_GATE_4INPUTPORT                    = 128;
public final static int  NOR_GATE_5INPUTPORT                    = 129;
public final static int  NOR_GATE_6INPUTPORT                    = 130;
public final static int  NOR_GATE_7INPUTPORT                    = 131;
public final static int  NOR_GATE_8INPUTPORT                    = 132;
    
public final static int  NOT_GATE				= 133;

public final static int  EXOR_GATE				= 134;
public final static int  EXOR_GATE_2INPUTPORT                   = 135;
public final static int  EXOR_GATE_3INPUTPORT                   = 136;
public final static int  EXOR_GATE_4INPUTPORT                   = 137;
public final static int  EXOR_GATE_5INPUTPORT                   = 138;
public final static int  EXOR_GATE_6INPUTPORT                   = 139;
public final static int  EXOR_GATE_7INPUTPORT                   = 140;
public final static int  EXOR_GATE_8INPUTPORT                   = 141;

public final static int  WAVELENGTH_CONVERTER	                = 142;
public final static int  MEMORY_UNIT			        = 143;
public final static int  OPTICAL_SWITCH			        = 144;
public final static int  LOPASS_FILTER			        = 145;
public final static int  BANDPASS_FILTER		        = 146;
public final static int  HIPASS_FILTER			        = 147;
public final static int  OPTICAL_INPUT_PORT			= 148;
public final static int  OUTPUT_PORT			        = 149;
public final static int  OPTICAL_INPUT_CONSOLE			= 150;
public final static int  DISPLAY				= 151;
public final static int  OPTICAL_WAVEGUIDE		        = 152;
public final static int  TEST_POINT				= 153;
public final static int  MACH_ZEHNER			        = 154;
public final static int  CLOCK					= 155;
public final static int  SLM                                    = 156;
public final static int  RAM8                                   = 157;
public final static int  RAM16                                  = 158;
public final static int  RAM20                                  = 159;
public final static int  RAM24                                  = 160;
public final static int  RAM30                                  = 161;
//end binary
    
public final static int PIVOT_POINT                             = 170;
public final static int OPTICAL_COUPLER1X2                      = 171;
public final static int OPTICAL_COUPLER1X3                      = 172;
public final static int OPTICAL_COUPLER1X4                      = 173;
public final static int OPTICAL_COUPLER1X5                      = 174;
public final static int OPTICAL_COUPLER1X6                      = 175;
public final static int OPTICAL_COUPLER1X8                      = 176;
public final static int OPTICAL_COUPLER1X9                      = 177;
public final static int OPTICAL_COUPLER1X10                     = 178;
public final static int OPTICAL_COUPLER1X16                     = 179;
public final static int OPTICAL_COUPLER1X20                     = 180;
public final static int OPTICAL_COUPLER1X24                     = 181;
public final static int OPTICAL_COUPLER1X30                     = 182;

public final static int OPTICAL_COUPLER2X1                      = 190;
public final static int OPTICAL_COUPLER3X1                      = 191;
public final static int OPTICAL_COUPLER4X1                      = 192;
public final static int OPTICAL_COUPLER5X1                      = 193;
public final static int OPTICAL_COUPLER6X1                      = 194;
public final static int OPTICAL_COUPLER7X1                      = 195;
public final static int OPTICAL_COUPLER8X1                      = 196;

public final static int SR_LATCH                                = 200;
public final static int JK_LATCH                                = 201;
public final static int D_LATCH                                 = 202;
public final static int T_LATCH                                 = 203;
        
public final static int SR_FLIPFLOP                             = 210;
public final static int JK_FLIPFLOP                             = 211;
public final static int JK_FLIPFLOP_5INPUT                      = 212;
public final static int D_FLIPFLOP                              = 213;
public final static int T_FLIPFLOP                              = 214;

public final static int OPTICAL_AMPLIFIER                       = 215;
public final static int OPTICAL_MATCHING_UNIT                   = 216;

public final static int ARITH_SHIFT_RIGHT                       = 217;

    //electronic
public final static int  ROM8                                   = 220;
public final static int  ROM16                                  = 221;
public final static int  ROM20                                  = 222;
public final static int  ROM24                                  = 223;
public final static int  ROM30                                  = 224;
public final static int  ELECTRICAL_INPUT_CONSOLE               = 225;
public final static int  ELECTRICAL_INPUT_PORT                  = 226;

public final static int  CROM8x16                               = 227;
public final static int  CROM8x20                               = 228;
public final static int  CROM8x24                               = 229;
public final static int  CROM8x30                               = 230;
//end electronic
public final static int TEXT                                    = 231;
public final static int DEBUG_TESTPOINT                         = 232;
//decimal
//logical
public final static int  DECIMAL_AND_GATE                       = 240;
public final static int  DECIMAL_NAND_GATE                      = 241;
public final static int  DECIMAL_OR_GATE                        = 242;
public final static int  DECIMAL_NOR_GATE                       = 243;
public final static int  DECIMAL_NOT_GATE                       = 244;
public final static int  DECIMAL_EXOR_GATE                      = 245;

public final static int  DECIMAL_OPTICAL_INPUT_PORT             = 250;
public final static int  DECIMAL_OPTICAL_INPUT_CONSOLE          = 251;
public final static int  DECIMAL_DISPLAY                        = 252;
public final static int  DECIMAL_OPTICAL_SWITCH                 = 253;

public final static int  DECIMAL_RAM8                           = 260;
public final static int  DECIMAL_RAM16                          = 261;
public final static int  DECIMAL_RAM20                          = 262;
public final static int  DECIMAL_RAM24                          = 263;
public final static int  DECIMAL_RAM30                          = 264;
//end decimal

public final static int  SAME_LAYER_INTER_MODULE_LINK_END                       = 350;
public final static int  SAME_LAYER_INTER_MODULE_LINK_START                     = 351;
public final static int  DIFFERENT_LAYER_INTER_MODULE_LINK_START                = 352;
public final static int  DIFFERENT_LAYER_INTER_MODULE_LINK_END                  = 353;
public final static int  DIFFERENT_LAYER_INTER_MODULE_LINK_THROUGHHOLE          = 354;
//end components

//job functions
public final static int  SIMULATE				= 400;//??
public final static int LOGICAL3PORT                            = 401;//??
public final static int RECTANGLE                               = 402;
public final static int DESCRIPTION                             = 403;
public final static int LINE                                    = 404;
public final static int KEYBOARD                                = 405;
public final static int KEYBOARDHUB                             = 406;
public final static int TEXTMODEMONITORHUB                      = 407;
public final static int LOGIC_ANALYZER_STEP_LINE                = 408;
public final static int LOGIC_ANALYZER_STEP_TEXT                = 409;

public final static int POINT_SIZE_MIN                          = 8;
public final static int POINT_SIZE_MAX                          = 24;
public final static int POINT_SIZE_STEP                         = 2;

public final static String NORMAL                       = "Normal";
public final static String MOVE                         = "Move";
public final static String ROTATE                       = "Rotate";
public final static String COPYANDSAVE                  = "CopyAndSave";
public final static String COPYASOBJECTS                = "CopyAsObjects";
public final static String MOVE_MODULE                  = "MoveModule";
public final static String MOVE_TEMP_MODULE             = "MoveTempModule";
public final static String MOVE_BLOCK_MODEL             = "MoveBlockModel";
public final static String MAIN                         = "MAIN";   //used by BlockModelEditor for setting a Rectangle to be the main rectangle
public final static String SUB                          = "SUB";    //used by BlockModelEditor for setting a Rectangle to be the sub rectangle
public final static String TXT                          = "TXT";    //used by block model editor to determine a text type
public final static String PORTTXT                      = "PORTTXT";//used by block model editor to determine a text type
public final static String PARTTXT                      = "PARTTXT";//used by block model editor to determine a text type
public final static String INPUT_PORT_LINE              = "INPUT_PORT";
public final static String OUTPUT_PORT_LINE             = "OUTPUT_PORT";
public final static String PLN                          = "PLN";
public final static String MLN                          = "MLN";
public final static Point DEFAULT_PLN_POS               = new Point(60,60);
public final static Point DEFAULT_PARTNUMBER_POSITION   = new Point (60,40);
public final static int COPYANDSAVETOFILE   = 600;
public final static int COPYANDSAVEORPASTE  = 601;

//parts boards or chips
public final static int         BOARD                                   = 356;//boards link/connect chips via board level modules on a board layer
public final static int         MAIN_BOARD                              = 357;
public final static int         SUB_BOARD                               = 358;
public final static int         CHIP                                    = 359;//chip sits on board layer and is connected via modules on board
public final static int         PART                                    = 360;//this is used for menu option which creates board or chip
public final static int         MODULE                                  = 361;//this is used for menu option to create a module within part
public final static int         BLOCK_MODEL                             = 362;
public final static int         MOTHERBOARD                             = 363;

public final static int         MAIN_WINDOW                             = 364;
public final static int         CHILD_WINDOW                            = 365;

//module stacking in window view
public final static int         HORIZONTAL                              = 366;
public final static int         VERTICAL                                = 367;
public final static int         DEFAULT_STACKING_ORDER                  = HORIZONTAL;

public final static int         INPUT                                   = 370;
public final static int         OUTPUT                                  = 371;

//stacking constants
public final static int         DEFAULT_MODULE_X_POSITION               = 20;
public final static int         DEFAULT_MODULE_Y_POSITION               = 20;
public final static int         DEFAULT_MODULE_WIDTH                    = 400;
public final static int         DEFAULT_MODULE_HEIGHT                   = 400;
public final static int         DEFAULT_MODULE_SPACING                  = 20;

//settings
public final static int         DEFAULT_COMPONENT_TYPE                  = AND_GATE;
public final static Color       DEFAULT_COMPONENT_COLOR                 = Color.BLACK;
public final static Color       DEFAULT_MODULE_COLOR                    = Color.BLACK;
public final static float       DEFAULT_LINE_WIDTH                      = 1.0f;
public final static Font        DEFAULT_FONT                            = new Font("Arial", Font.PLAIN, 8); //= new Font("Sans", Font.PLAIN, 8);
public final static Font        DEFAULT_MODULE_FONT                     = new Font("Arial", Font.PLAIN, 9);
public final static Font        DEFAULT_BLOCK_COMPONENT_FONT            = new Font("Arial", Font.PLAIN, 12);
public final static Font        DEFAULT_TEXTAREA_FONT                   = new Font("Arial", Font.PLAIN, 14);
public final static Font        DEFAULT_VIRTUAL_MONITOR_FONT            = new Font("Consolas", Font.PLAIN, 16);
public final static Font        DEFAULT_LOGICANALYZER_TAG_FONT          = new Font("ARIAL", Font.BOLD, 14);
public final static Color       DEFAULT_VIRTUAL_MONITOR_TEXT_COLOR      = Color.GREEN;
public final static Color       DEFAULT_VIRTUAL_MONITOR_BACKGROUND_COLOR = Color.BLACK;
public final static Color       HIGHLIGHT_COLOR                         = Color.MAGENTA;
public final static Color       HIGHLIGHT_MODULE_COLOR                  = Color.MAGENTA;
public final static Color       DEFAULT_BACKGROUND_COLOR                = new Color(8,228,241);//light blue
public final static Color       BACKGROUND_COLOR                        = new Color(255,255,255);//white
public final static Path        DEFAULT_DIRECTORY                       = Paths.get(System.getProperty("user.home")).resolve("PhotonicMockSim");
public final static Path        DEFAULT_PARTLIBRARY_DIRECTORY           = Paths.get(System.getProperty("user.home")).resolve("PhotonicMockSim").resolve("partsLibrary");
public final static Path        DEFAULT_MODULELIBRARY_DIRECTORY         = Paths.get(System.getProperty("user.home")).resolve("PhotonicMockSim").resolve("modulesLibrary");
public final static Path        DEFAULT_PROJECT_ROOT                    = Paths.get(System.getProperty("user.home")).resolve("PhotonicMockSim").resolve("projects");
public final static Path        DEFAULT_LOG_DIRECTORY                   = Paths.get(System.getProperty("user.home")).resolve("PhotonicMockSim").resolve("logs");
public final static Path        DEFAULT_LOGICANALYZER_TRACES_DIRECTORY  = Paths.get(System.getProperty("user.home")).resolve("PhotonicMockSim").resolve("logicAnalyzerTraceFiles");
public final static String      DEFAULT_PROJECT_NAME                    = "partChipProjectName";
public final static String      DEFAULT_PROJECT_PART_FILENAME           = "partDefinitionFile";
public final static String      DEFAULT_PROJECT_LAYER_FILENAME          = "layerDefinitionFile";
public final static String      DEFAULT_PROJECT_MODULE_FILENAME         = "moduleDefinitionFile";
public final static String      DEFAULT_PROJECT_COMPONENT_FILENAME      = "CircuitComponentFile";
public final static String      DEFAULT_FILENAME                        = "Circuit1.ckt";
public final static String      DEFAULT_SPECIFICATION_FILENAME          = "Specification.html";
public final static String      DEFAULT_LOGICANALYZER_FILENAME          = "Buffer.xml";
public final static Path        DEFAULT_IMAGE_DIRECTORY                 = Paths.get(System.getProperty("user.home")).resolve("PhotonicMockSim").resolve("images");
public final static String      DEFAULT_IMAGE_FILENAME                  = "image1.jpg";
public final static Color       DEFAULT_GRID_COLOR                      = new Color(197,223,239);//new Color(18,167,237);
public final static int         DEFAULT_SIMULATION_DELAY_TIME           = 1000;
public final static int         DEFAULT_BLOCKMODEL_LINE_LENGTH          = 10;


//debug functionality via debug file
public final static boolean DEBUG                                               = false;

//CircuitComponent.java
public final static boolean DEBUG_CIRCUITCOMPONENT                              = false;

//PhotonicMockSim.java
public final static boolean DEBUG_PHOTONICMOCKSIM                               = false;

//PhotonicMockSimView.java
public final static boolean DEBUG_PHOTONICMOCKSIMVIEW                           = false;

//PhotonicMockSimFrame
public final static boolean DEBUG_PHOTONICMOCKSIMFRAME                        = false;
public final static boolean DEBUG_SIMULATEDIALOG                                = false;
public final static boolean DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE            = false;
public final static boolean DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE  = false;
public final static boolean DEBUG_INITIALISESYSTEM                              = false;
public final static boolean DEBUG_SIMULATESYSTEM                                = false;

//PhotonicMockSimModel
public final static boolean DEBUG_PHOTONICMOCKSIMMODEL                          = false;

//idealSimulationModel.java
public final static boolean DEBUG_IDEALSIMULATIONMODEL                          = false;

//LinkDialog.java
public final static boolean DEBUG_LINKDIALOG                                    = false;


//AddBlockModelPartDialog
public final static boolean DEBUG_ADDLOCKMODELPARTDIALOG                        = false;

//AddDebugTestpointsDialog
public final static boolean DEBUG_ADDDEBUGTESTPOINTSDIALOG                      = false;    //not used

//addLayerDialog
public final static boolean DEBUG_ADDLAYERDIALOG                                = false;    //not used

//addModuleDialog
public final static boolean DEBUG_ADDMODULEDIALOG                               = false; //not used

//addPartDialog
public final static boolean DEBUG_ADDPARTDIALOG                                 = false;  //not used

//ChooseBlockModelTypeDialog
public final static boolean DEBUG_CHOOSEBLOCKMODELTYPEDIALOG                    = false; //not used

//ChooseKeyboardHubDialog
public final static boolean DEBUG_CHOOSEKEYBOARDHUBDIALOG                       = false;    //not used

//ChooseModuleForBlockModelDialog
public final static boolean DEBUG_CHOOSEMODULEFORBLOCKMODELDIALOG               = false; //not used

//ChooseMonitorHubDialog
public final static boolean DEBUG_CHOOSEMONITORHUBDIALOG                        = false;    //not used

//ChoosePartForBlockModelDialog
public final static boolean DEBUG_CHOOSEPARTFORBLOCKMODELDIALOG                 = false; //not used#

//CreatePivotPointDialog
public final static boolean DEBUG_CREATEPIVOTPOINTDIALOG                        = false;

//CreateProjectDialog
public final static boolean DEBUG_CREATEPROJECTDIALOG                           = false;

//DeleteComponent
public final static boolean DEBUG_DELETECOMPONENT                               = false;

//deleteLayerDialog
public final static boolean DEBUG_DELETELAYERDIALOG                             = false;    //not used

//deleteModuleDialog
public final static boolean DEBUG_DELETEMODULEDIALOG                            = false;    //not used

//deletePartDialog
public final static boolean DEBUG_DELETEPARTDIALOG                              = false; //not used

//FontDialog
public final static boolean DEBUG_FONTDIALOG                                    = false;    //not used

//GridConfigurationDialog
public final static boolean DEBUG_GRIDCONFIGURATIONDIALOG                       = false; //not used

//KeyboardDialog
public final static boolean DEBUG_KEYBOARDDIALOG                                = false;    //not used

//LoadHTMLEditorWithDescriptionDialog
public final static boolean DEBUG_LOADHTMLEDITORWITHDESCRIPTIONDIALOG           = false;

//LogicAnalyzerDialog
public final static boolean DEBUG_LOGICANALYZERDIALOG                           = false;

//LogicProbeDialog
public final static boolean DEBUG_LOGICPROBEDIALOG                              = false; //not used

//PropertiesDialog
public final static boolean DEBUG_PROPERTIESDIALOG                              = false;

//PropertiesModuleDialog
public final static boolean DEBUG_PROPERTIESMODULEDIALOG                        = false;

//resizeModuleDialog
public final static boolean DEBUG_RESIZEMODULEDIALOG                            = false;    //not used

//SetBlockModelPortNumberDialog
public final static boolean DEBUG_SETBLOCKMODELPORTNUMBERDIALOG                 = false;

//SetLineWidthDialog
public final static boolean DEBUG_SETLINEWIDTHDIALOG                            = false;    //not used

//SetSimulationDelayTimeDialog
public final static boolean DEBUG_SETSIMULATIONDELAYTIMEDIALOG                  = false;    //not used

//ShowBlockModelContentsDialog
public final static boolean DEBUG_SHOWBLOCKMODELCONTENTSDIALOG                  = false; //not used

//ShowBlockModelPadsDialog
public final static boolean DEBUG_SHOWBLOCKMODELPADSDIALOG                      = false;

//SimulateBuildExecutionQueueProgressDialog
public final static boolean DEBUG_SIMULATEBUILDEXECUTIONQUEUEPROGRESSDIALOG     = false;    //not used

//TextModeMonitorDialog
public final static boolean DEBUG_TEXTMODEMONITORDIALOG                         = false;

//ViewLineDialog
public final static boolean DEBUG_VIEWLINEDIALOG                                = false;   //not used

//ViewLineLinksDialog
public final static boolean DEBUG_VIEWLINELINKSDIALOG                           = false;    //not used

//ViewLinksDialog
public final static boolean DEBUG_VIEWLINKSDIALOG                               = false;

//ViewValuesDialog
public final static boolean DEBUG_VIEWVALUESDIALOG                          = false;

//ComponentLink
public final static boolean DEBUG_COMPONENTLINK                             = false;    //not used

//componentLinkage
public final static boolean DEBUG_COMPONENTLINKAGE                          = false;    //not used

//ExecutionQueueNode
public final static boolean DEBUG_EXECUTIONQUEUENODE                        = false;    //not used

//ExtensionFilter
public final static boolean DEBUG_EXTENSIONFILTER                           = false;    //not used

//ImageData
public final static boolean DEBUG_IMAGEDATA                                 = false;    //not used

//ImageSelection
public final static boolean DEBUG_IMAGESELECTION                            = false; //not used

//InputConnector
public final static boolean DEBUG_INPUTCONNECTOR                            = false;

//InterModuleLink
public final static boolean DEBUG_INTERMODULELINK                           = false;    //not used

//Layer
public final static boolean DEBUG_LAYER                                     = false;

//LineManagement
public final static boolean DEBUG_LINEMANAGEMENT                            = false; //not used

//Module
public final static boolean DEBUG_MODULE                                    = false;

//OutputConnector
public final static boolean DEBUG_OUTPUTCONNECTOR                           = false;

//Part
public final static boolean DEBUG_PART                                      = false;

//dialogs.blockmodel
//AddBlockModelDescriptionDialog
public final static boolean DEBUG_ADDBLOCKMODELDESCRIPTIONDIALOG            = false;    //not used

//AddGridDialog
public final static boolean DEBUG_ADDGRIDDIALOG                             = false; //not used

//BlockModel
public final static boolean DEBUG_BLOCKMODEL                                = false;

//BlockModelComponent
public final static boolean DEBUG_BLOCKMODELCOMPONENT                       = false;

//BlockModelFrame
public final static boolean DEBUG_BLOCKMODELFRAME                           = false;

//BlockModelSetTypeDialog
public final static boolean DEBUG_BLOCKMODELSETTYPEDIALOG                   = false;    //not used

//BlockModelView
public final static boolean DEBUG_BLOCKMODELVIEW                            = false;

//CreateBlockModelRectangleDialog
public final static boolean DEBUG_CREATEBLOCKMODELRECTANGLEDIALOG           = false;

//HTMLEditorSample
public final static boolean DEBUG_HTMLEDITORSAMPLE                          = false;

//PortSpacingDialog
public final static boolean DEBUG_PORTSPACINGDIALOG                         = false; //not used

//ResizeBlockModelDialog
public final static boolean DEBUG_RESIZEBLOCKMODELDIALOG                    = false;    //not used

//SetBlockModelPortWavelength
public final static boolean DEBUG_SETBLOCKMODELPORTWAVELENGTH               = false;

//photonicmocksim.dialogs.logicanalyzer
//LogicAnalyzerComponent
public final static boolean DEBUG_LOGICANALYZERCOMPONENT                    = false;

//LogicAnalyzerFrame
public final static boolean DEBUG_LOGICANALYZERFRAME                        = false;

//LogicAnalyzerModel
public final static boolean DEBUG_LOGICANALYZERMODEL                        = false;    // not used

//LogicAnalyzerView
public final static boolean DEBUG_LOGICANALYZERVIEW                         = false;

//photonicmocksim.dialogs.showblockmodelcontents
//ShowBlockModelContentsFrame
public final static boolean DEBUG_SHOWBLOCKMODELCONTENTSFRAME               = false;

//ShowBlockModelContentsView
public final static boolean DEBUG_SHOWBLOCKMODELCONTENTSVIEW                = false;

//photonicmocksim.dialogs.textmodemonitor
//TextModeMonitorComponent
public final static boolean DEBUG_TEXTMODEMONITORCOMPONENT                  = false;

//TextModeMonitorFrame
public final static boolean DEBUG_TEXTMODEMONITORFRAME                     = false; //not used

//TextModeMonitorModel
public final static boolean DEBUG_TEXTMODEMONITORMODEL                     = false;

//TextModeMonitorView
public final static boolean DEBUG_TEXTMODEMONITORVIEW                       = false;    //not used

//com.photonicmocksim.htmleditor.dialogs.utils
//DialogLayout2
public final static boolean DEBUG_DIALOGLAYOUT2                             = false;    //not used

//DocumentTokenizer
public final static boolean DEBUG_DOCUMENTTOKENIZER                         = false;    //not used

//OpenList
public final static boolean DEBUG_OPENLIST                                  = false;    //not used

//photoniccomputer.htmleditor.dialogs
//DocumentPropsDlg
public final static boolean DEBUG_DOCUMENTPROPSDLG                          = false; //not used

//FindDialog
public final static boolean DEBUG_FINDDIALOG                                = false;    //not used

//HTMLSourceDlg
public final static boolean DEBUG_HTMLSOURCEDLG                             = false;    //not used

//TableDlg
public final static boolean DEBUG_TABLEDLG                                  = false; //not used

//photoniccomputer.tabbedhtmleditordialog
//Documents
public final static boolean DEBUG_DOCUMENTS                                 = false; //not used

//DocumentsList
public final static boolean DEBUG_DOCUMENTSLIST                             = false; //not used

//TabbedHTMLEditorDialog
public final static boolean DEBUG_TABBEDHTMLEDITORDIALOG                    = false;

//photoniccomputer.htmleditor.utils
//CustomHTMLEditorKit
public final static boolean DEBUG_CUSTOMHTMLEDITORKIT                       = false; //not used

//MutableHTMLDocument
public final static boolean DEBUG_MUTABLEHTMLDOCUMENT                       = false; //not used

//Utils
public final static boolean DEBUG_UTILS                                     = false;    //not used




















//deprecated
public final static int NOT_DONE                = 0;
public final static int DIRECTION_RIGHT         = 1;
public final static int DIRECTION_RIGHT_DONE    = 2;
public final static int DIRECTION_LEFT          = 3;
public final static int DIRECTION_LEFT_DONE     = 4;
public final static int IN_THE_MIDDLE           = 5;

//public final static int BOARD                   = 6;
//public final static int CHIP                    = 7;

public final static int YES                     = 11;
public final static int NO                      = 12;

}