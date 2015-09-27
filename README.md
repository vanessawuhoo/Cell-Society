## Cell Society Team 1##
Kevin Wang, Vanessa Wu, Chris Hong
**Date Started:** 9/12
**Date Ended:** 9/13
**Hours worked:** 70 hours
**Resource Used:** Java Docs, TAs
**Starting Files:** Main.java
**Testing Files:** DataTester.java
**Data Files:** The XMLs
**Instructions:** Mostly self explanatory. Input the xml you want and then run.
**Bugs:** Have to stop the sim before you load another or change the shape etc...
**Extra Features:** Beyond sprint 3, can change the colors that states represent dynamically

## Modifying the XMLs ##

Also mostly self explanatory but I will explain any more confusing parts.

Simulation Inputs: gameOfLife, fire, waTor, segregation, sugarScape

Shape: SQUARE8, SQUARE4, HEXAGON, TRIANGLE

Color: Must have a color for each cell parameter of "state"

Toroidal: 0 or 1 toggle

State Map: maps the numerical state value to the name of the state

CellFill: defines if fills the grid dynamically or not. ProbFill or ManualFill

ProbFill: add a sub node for each cell parameter and define a decimal for each state of that parameter that sums to 1 (see xmls for example)

Cell: self explanatory. You MUST have all the parameter sub nodes in each cell or else probfill won't work


