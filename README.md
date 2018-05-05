## Learning goals

 * The observer/observable pattern
 * Advanced testing techniques
 * Implementing basic algorithms

## Getting Started

 1. [Fork][github-fork] this repo.

     > _Note:_ Your fork is private and is only visible to you, the TAs and the instructors.

 2. Clone the fork to your local machine (using the `git clone` command).

 3. Open the project in your IDE.       
    If you are using Eclipse, _File --> Import --> Existing Maven Projects_.


## Your Task

Implement the code to pass the provided unit tests.        
See the marking scheme below for more details.

### Guidance

For this assignment, you will need to implement the following three interfaces:

 * [`IGridRobot`](/src/main/java/edu/toronto/csc301/robot/IGridRobot.java),
   a slightly modified version of the interface from A2.
    * We don't care about which direction the robot is facing, so it's not part
      of the interface anymore.
    * An `IGridRobot` is observable.          
      Listeners (aka observers) expect to get called whenever the robot starts
      a step and ends a step.
 * [`IWarehouse`](/src/main/java/edu/toronto/csc301/warehouse/IWarehouse.java),
   a domain object that keeps the full state of the warehouse:
    * The state of the warehouse is essentially:
      * Floor plan (`IGrid<Rack>`)
      * Collection of `IGridRobot`s that are on the floor.
      * Keeping track of which robots are currently in motion (and which direction
        they are stepping towards).
    * An `IWarehouse` is observable.        
      Subscribers (aka observers) expect to get called whenever something
      happens in the warehouse (e.g. a robot was added to the floor, a robot
      starts moving, a robot finished moving, etc.)
 * [`IPathPlanner`](/src/main/java/edu/toronto/csc301/warehouse/IPathPlanner.java),
   essentially a helper function used for planning the paths of robots through a grid.
    * Given a warehouse (`IWarehouse`) and a set of goals (robots and their
      destinations), return the next step (robot and a step direction)
      that will get us closer to satisfying the goals.
    * For this assignment, we only test your code with a single  goal.         
      That is, we only ask you to plan a path for a single robot.

As always, you should start by passing the tests in [`SetupTest`](/src/test/java/edu/toronto/csc301/SetupTest.java).


 > *Note:* Why is a robot step divided into two events (`stepStart` and `stepEnd`)?        
   In a realistic scenario, it takes time for a physical robot to make
   a step. It is extremely useful to be able to trigger functionality right
   before a robot starts moving (e.g. safety system that prevents robots from
   crashing into walls/objects) as well as right after it finished moving
   (e.g. updating the internal state of our application).

#### A few tips when working on the path planner:

Remember, you are not suppose to move anything in the warehouse, you are only
asked to return what the next step should be.

The return type of the `nextStep` method is `Entry<IGridRobot, Direction>`.
Think of it as simply a pair of objects, one (the key) is an `IGridRobot` and
the other (the value) is a `Direction`.

You can create such <robot,direction> pairs as follows:
```
// robot is an IGridRobot, and direction is a Direction
new AbstractMap.SimpleEntry<IGridRobot, Direction>(robot, direction);
```

Coding tips:

 * Break `nextStep` into several steps, use helper functions, and make sure
    you're dealing with one problem at a time.
 * Keep your code clean while you're working on it.
   It will make bugs harder to create and easier to find.
 * Don't reinvent the wheel - Use existing algorithms and adapt them to your situation.     
    For example, my solution adapts [BFS](https://en.wikipedia.org/wiki/Breadth-first_search) to our domain and uses it to find a shortest path from one location to another.
 * Do not try to optimize at this stage.
  * You might end up sacrificing design flexibility.
  * In a realistic scenario, an improvement in the computation runtime might
    end up being insignificant in comparison to the time it takes physical
    robots to move in space.


## Deliverables

Your code, submitted as a single, non-conflicting [pull-request][github-pull-requests] from your fork to the handout repo (i.e. the repo you forked).

## Marking Scheme

This assignment is divided into 3 parts.
Your code will be **marked automatically**, according to the following scheme:

 * Tests under [`step1`](/src/test/java/edu/toronto/csc301/step1) are worth 40%
   * You must pass all of them, there are no partial marks for this part.
 * Tests under [`step2`](/src/test/java/edu/toronto/csc301/step2) are worth 30%
   * If you fail a single test method, you will earn 20 out of 30 point.
   * If you fail two test methods, you will earn 10 out of 30 point.
   * If you fail three or more test methods, you will not earn a mark for this part.
 * Tests under [`step3`](/src/test/java/edu/toronto/csc301/step3) are worth 30%
   * Pass all the tests in [`PathPlannerTest`](/src/test/java/edu/toronto/csc301/step3/PathPlannerTest.java)
     to earn the first 15 out of 30 points. No partial marks.
   * Pass all the tests in [`PathPlannerAdvancedTest`](/src/test/java/edu/toronto/csc301/step3/PathPlannerAdvancedTest.java)
       to earn the remaining 15 out of 30 points. No partial marks.


----

Please submit your work early to give yourself time to deal with
unexpected bugs, and **always double-check your pull-request**!

 * Make sure that Travis CI passed the tests.
 * Make sure you did not change the testing code.         
   Go over the files that were changed (available as a tab in the pull-request
   page on GitHub) and make sure you did not commit any changes to files/folders
   under `src/test`.


## Important Notes

 1. Do not add any collaborators or teams to your fork!

    > Since you are the owner of your fork repo, GitHub allows you to share it with
others. Note that GitHub also allows us (the instructors and TA's) to see if
share your fork with anyone.

  If you share your fork with anyone, you will **automatically get a 0 mark** for this assignment.

 2. After you submit your assignment, make sure to check the results of Travis CI.

     > If your code passes the tests on your computer, but fails on Travis, then your code is broken.       

    It is **your responsibility** to make sure your code compiles!

 3. Do not modify any of given interfaces or testing code!

    > If you do, then Travis will no longer be useful, since it will no longer run the same tests as our auto-marker.


[IGrid]: /src/main/java/edu/toronto/csc301/grid/IGrid.java "IGrid interface"
[github-guides]: https://guides.github.com/ "GitHub guides"
[github-fork]: https://guides.github.com/activities/forking/ "Guide to GitHub fork"
[github-pull-requests]: https://help.github.com/articles/using-pull-requests/ "Guide to GitHub Pull-Requests"
