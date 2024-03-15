# Project #2: NFA

* Author: Karter Melad & Hunter Walp
* Class: CS361 Section #002
* Semester: Spring 2024

## Overview

This is a Java program that simulates an NFA (nondeterministic finite automota).
It is capable of creating an NFA to suit the user's requirements.

## Reflection

### Karter Reflection

The NFA project was easier to grasp after working on the DFA for project one.
The structure of the repository was almost exact and some of the methods for
the NFA.java was nearly identical to some in the DFA.java. This allowed me to
begin completing the project at a faster rate than the previously. A lot of the
same practices worked well for this project as it did for the last one. For me, 
getting started on a project is usually the hardest part. Once the classes are 
started and the method signatures are implemented so I can get a grasp of what 
needs to be worked on, I am a lot more efficient. These projects also help me
clear up any confusion I might have on the type of machine we are creating.

One of my biggest struggles with this assignment was time. It wasn't because
of this project, but the last two weeks I have been very busy with school and
life in general. Finishing this project was the last thing on my todo list and
now I can relax a bit as we head into Spring break. Hunter and I also decided to 
create an NFA with 100 different states and run some tests on it. It was a quick
and fun way to really put our NFA to the test. Our program seemed to handle it fine.

### Hunter Reflection

I found the NFA project to be about the same difficulty as the DFA project, maybe
even a little easier. The project followed along with the class content well, and
made it fairly easy for me to begin working on Project 2. There were some challenges,
primarily the accepts function and the eClosure function. The eClosure function was 
more of a knowledge problem for me, I didn't fully understand how I would find the 
eClosure of a state, and writing the function helped me get a better grasp on the 
concept.

The testing side of things went fairly smoothly. I used the debugger in IntelliJ
IDEA a lot. It helped me to set breakpoints on the test cases, and walk through
the logic of our NFA. After Karter and I thought that we had a good, functioning
NFA, we decided to create a really large NFA, with 100 states and sort of "stress
test" it. There were some weird quirks when writing tests that large, but Karter 
found them and ironed them out. Overall, I enjoyed this project a lot. It was nice
to be able to copy some of the methods over from our DFA, and dig deeper into how
an NFA functions, and it helped me understand some of the more complex functionality 
of an NFA as a whole.



## Compiling and Using

To test the functionality of our NFA the user should run the JUNIT tests provided
in the project. Otherwise the user could manually create their own NFA and modify
it to their liking with the provided NFA methods.
