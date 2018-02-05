# Andy Rosales Elias, Durva Kapadne

* (a) (20 pts) A brief description of the project.  Here, I'm looking for a short description: probably 1 sentence, 2-3 at most.

  The project is a game called Subsink where the player is the pilot of a boat and the goal is to defeat and destroy the enemy submaries that are moving underneath the boat. The enemies can be defeated by dropping bombs from the boat. The boat can also be destroyed by the submarines with rockets that float towards the surface. The boat tha the player pilots has different speed settings and can more forward and backwards.

* (b) (20 pts) a set of user stories (as a X I can Y so that Z) that describe what the current software in its current state can do.

   <br/> As a player, I can drop bombs on enemy submarines
   <br/> As a player, I can move forward and backwards
   <br/> As a player, I can switch to different speed settings
   <br/> As a player, I can release up to 3 bombs consecutively to destroy enemies
    

    * First, review how [User Stories](https://ucsb-cs56-pconrad.github.io/topics/user_stories/) are supposed to be written.
* (c) (20 pts) a brief assessment of whether the software runs or not.   If it runs, briefly describe what it does
  
  
  The game compiles and runs. As an user I am able to run the game and play it without errors.
  
* (d) (20 pts) a set of user stories (at least 2, but you are encouraged to write up to 4 or more if you can, as many as you think is reasonable) about features that COULD be added to the software to make it more useful, fun, better, etc.
    * Again, review the preferred way to write [User Stories](https://ucsb-cs56-pconrad.github.io/topics/user_stories/).
   
   <br />As a user, I can use my bombs to destroy enemy rockets
   <br /> As a user, I can move my boat with one keystroke instead of 6
    <br />As a user, I can throw bombs from the middle of the boat instead of two sides to reduce complexity
    <br />As a user, I can make submarines explode instead of just dissapearing when they are defeated
    <br /> As a user, I can see my past scores and high scores
    
* (e) (20 pts) An assessment of the current quality of the README.md.   What information could be added to make it easier for the next generation of folks maintaining this code to use the software, and/or maintain the software? 
  <br /> It would be much easier for Java beginners if the README.md contained step-by-step instructions on how to compile and run the game
  
  <br /> Modify the "Code Structure" section to explain with more detail
  <br /> Need to think of new name for "height charges".  

* (f) (20 pts) An assessment of the current state of the build.xml file if applicable, or if the project has been converted to Maven or Gradle, note this.

<br />looks fine

   * If it's based on Ant, Are there targets that need descriptions?  Is there old legacy JWS stuff that needs to be removed?  (More on this below).
   
   <br />build file lloks fine. No descriptions needed as if yet.
   
   * It it's based on Maven or Gradle, is there sufficient documentation in the README.md that someone new to those tools has the information they need to get started?
  
  <br />not based on maven or gradle
   
* (g) (20 pts) An assessment of the current "issues".  Are there enough issues that you could earn 1000 points by working on this project?   Are the issues clear in terms of what the expectations are?

<br />Issues look good. There are more than 1000 points avaible to earn. The descriptions are suffiecient, though a little more detail wouldn't hurt.


* (h) (20 pts) A list of additional issues that you may have added, if any. For each, a link to the issue is good enough.

<br />no additional issues added as of yet, but they may be added as we delve into the project


* (i) (100 pts) Most important: an assessment of the actual code.  Write a bit about how the code is organized.  Are the purposes of the classes, and their methods clear?  Is it obvious how the classes relate to one another?   Is the code easy to read and understand?   If you had to give someone else that was going to work on the code just "one screenful of text" to help that programmer get up to speed quickly, what information would you convey?

<br />Entity is a class that describes the characteristics of any objects in the game (including boat, submarines, bombs, charges, etc). This class determines if the object in on the screen or not, and also reports it's status as destroyed or undestroyed. There is  class for the ship that is a subclass for entity. It has the location and speed of the ship. It also makes sure the ship stops when it reaches the side of the screen. It also keeps track of its "health" and if it loses "health" then it sinks and the game is over. There is also a class for the submarines which also controls its location. One difference between the submarine and the boat is that the boat is controlled by the user and the submarine is controlled by the program. This class is also a subclass for entity. Image Loader  is a class that caches and loads images. The HeightCharge class is what controls the bombs or orange circles that rise from the submarines. The World class encompasses everything that is the game. The code is understandable and comments are sufficient to understand how and why the code was  written. The comments at the top of the file are more high level if you want to know general information and its gets more specific next to the functions.


* (j) (40 pts) Related to code quality, but factored out into a separate issue because it is so important: how is the test coverage?   Are there JUnit tests at all?  If so, how much of the project is covered by testing?  Are there opportunities to expand test coverage, and if so, how would you go about it?-pconrad.github.io/topics/user_stories/) are supposed to be written.

<br />There are opportunites to cover test coverage, as the tests are not yet written.
<br /> Probably will uses JUnit tests



