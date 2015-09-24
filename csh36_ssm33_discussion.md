Collaborators: Chris (csh36), Sherry (ssm33)

One piece of duplicated code was making neighbors for squares; adding eight neighbors has the same function as adding four neighbors except the corner neighbors are also added.  In order to reduce duplicated code, the eight neighbor making class was extended from the four neighbor making class so that its method to add neighbors is inherited.

Another piece of duplicated code was in Hub, changing the speed of the animation.  Another method was added to add the newer animation once the speed was set.

A third piece of duplicated code was in CellGraph, where the functionality of code was duplicated in a preexisting method.
> Written with [StackEdit](https://stackedit.io/).