ReadMe
This file is to show state of development version of Version 2.0 of the simulator.

On 16/8/22
Added debug flags on all main classes and upgraded to V2.02.01

On 2/6/22
A new version has been created 2.01 with oriented bounding boxes collision detection. I have also set the bounds on the pivot points to a smaller value (this needs more testing)

On 27/7/21
Added CROM8x16 CROM8x20 CROM8x24 CROM8x30 also got the saveAs to work and I have setup a project folder for the 4 bit optical CPU projects. This is to keep all things todo with the CPU in a project list under the parent folder.

On 17/6/21
got the logicAnalyzer open to work with next and previous(there is a bug with sticky buttons). I have added a validator to validate circuits. The button to validate a circuit is on the simulation dialog for now!. 

On 4/6/21
Got save to XML to work and fixed a bug with the tick steps not spacing correctly.

On 25/5/21
Got a basic multi-trace logic analyzer to work with step count on the plot with scrolling via scrollbars.

On 15/5/21
started working on Version 1.01 this is to have a logic Analyzer added LogicProbeDialog (with just component level probing) and context menu options for addition and deletion

On 25/6/19
Added Arithmetic Shift Right component with functional tests for stability done.

On 11/5/19
I have added a new feedback mechanism where a coupler1xM is deemed as start of a potential feedback loop. the normal propagate mechanism builds the executionQ first and then if an output port node of a coupler1xM is not in the executionQ then the propagate function propagates from this node.

On 7/3/19
Added SimulateBuildExecutionQueueProgressDialog and used for building the execution queue and simulate dialog a separate thread off the EDT thread thus the simulator does not hang when building the execution queue and some increase in time needed to build the execution queue is noticed.

On 1/3/19
I have completed component,module,chip and motherboard level stability testing with rubberbanding. 

On 23/1/19
I have fixed stability bugs with the keyboard hub and text mode monitor hub.

On 14/1/19
I have fixed stability issues with delete pivot point. I have fixed issue with part,layer,module,component numbers in that they can be set to number on open project to the number in the XML file.

On 12/1/19
Got pivot points to work with save and re-open with move capability.

On 7/1/19
I am debugging the move pivot point code I have added debug info before,during and after a move so I can trace individual component ports and locations!.

On 3/1/19
I think I have stabilized the pivot point addition with to left,to right and between 2 pivot points save and re-open. I tested it over several ports drawing lines and pivot points in no particular order and it showed stability after save and re-open.

On 31/12/18
I have started to debug pivot points with some progress but still have pivot point output port to input port of component and a pivot point between 2 pivot points to debug.

On 17/12/18
Fixed bug with copy and paste.(context menu not displaying properly and bounds on copied and pasted lines)

On 16/12/18
Fixed problem with line at angles 0, 90,180,270 not drawing properly.I was using degrees instead of radians in my debug script.

On 12/12/18
Added optical matching unit and it simulates.

On 20/9/18
Fixed bug (36 troublesome components) with rotate with rubber-banding on current components. 

On 12/9/18
Added ability to rubberband when rotating a component.

On 4/9/18 
Added ability to load ROM contents into ROM from file also added core dump capability for RAM.

On 28/8/18
Added ability to initialise a RAM or ROM chip. Added feedback loop detection for all components. Added ability to dump a ROM chips contents to file need to be able to read the ROM contents from file into memory. Added ability to clear the inputportsCalled flag when a simulate call is finished thus setting up for next simulate call.

On 22/8/18
got the child circuit editor to recognise block model modules and allow it to show the contents of a chip and if a chip has a module within it to also be able to show the contents of the module within the chip.

On 21/8/18
fixed bug with PLN/MLN not saving and opening correctly, added new IML node algorithm for DLIMLs and SLIMLs. Fixed bug with multiple modules within a chip not simulating. I used a normal default module and then a block model module on top of this as test case and got it to import into a motherboard and got it to simulate.
Fixed bug with multiple modules within a chip not adjusting correctly when imported from block model browser.

On 14/8/18
Added tabbing to block model browser. Fixed bug with initfirstTab not setting documents.m_currentfile correctly.

On 13/8/18
added packaging of the photonic simulator. Fixed bug with manual population of hyperlinks. Added auto populate of hyperlinks in a master document in the HTML Editor.

On 5/8/18
Fixed bugs with menu items not working for multiple tabs in the specification editor.Also added the Specification.html file with headings to projects module, chip and motherboard.

On 3/8/18
Added tabbed HTML editor dialog and the specification file menu

On 2/7/18
Added pin outs, Corrected winding order, Added pin 1 marker.

On 29/6/18
Added basic feedback capability experimentally and largely untested for normal 1 module circuits and also for chips and module feedback via IMLs.

On 25/6/18
Added basic 1 module in module circuit basic feedback

On 24/6/18
At present I have simulation capability with Block Model Modules and Block Model Parts I also have save and open capability with pivot points. with save and open I have rubberbanding and highlight works well.
