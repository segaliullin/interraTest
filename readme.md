Interra Problem

Hello Interra, sorry for delay, I was ill for a long time, there are some kind of epidemy in the city.
Current solution is not completed from all sides of view, but I am forced to send it. I am very
sorry for quality of this solution. It is true to say that it is more likely sketch or demo, than 
completed solution, but it works at least on sunny-cases.

**To run:**

**terraTest.jar in.txt out.txt**

Not completed solution:
1. Unit-testing - TBD
2. Class decomposition - TBD
3. "Unlimited input" -> "Unlimited output" requirement - TBD
4. Error handling

Additional points of improvement:
First, fix already mentioned TODOs.
Second, make it multithreaded (it is very possible that implementation should be completely reworked)

Current state:
There is a very simple solution for problem, based on hash calculation. Every email used as a key in 
a hashtable, every "hash+equal collision" will tag true email owner. So every next email in this line 
will be associated with found user.

According to requirement, Big-O of implementation should be O(n) or near to it, so I assume that every
processing step should have linear or less difficulty.
Lets take a look into the code:
1. Preparation step = O(1)
2. Reading of input file line. It depends on line length. Assuming O(n), where n - line length.
3. Splitting line to array, O(n)
4. Preparing array for processing, O(n)
5. Processing - calculation hashes for emails, O(n)
6. Repeat from 2 until EOF.
7. Process email to user hashtable, merge emails to user, O(n)
8. File writing, depends on line length -> O(n)

In summary, we need to process N users (lines) with M emails, so in fact we need to process N*M emails 
to solve the problem. Weak point of current implementation is an unknown final size of hashtable, 
extending hashtable (+ rehashing all values) can become a problem and can move current implementation 
from O(n). Also, it is possible to have "String"-hashcode collisions during processing, but it very rare 
case and should not affect processing.
