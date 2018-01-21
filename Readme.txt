Description of how the classes work:

Concrete Classes: 

1. Section.java -> Describes each section of the theater.
2. OrderRequest.java -> Describes each customer order request.



Classes that are responsible to create Section and OrderRequest objects:

(Both these classes are Singleton, meaning that only one instance can be created)
1. Theater.java -> maintains the list of Sections.
2. OrderRequestList.java -> maintains the list of OrderRequests.



Class that create Theater and OrderRequest objects:

1. ObjectHolder -> creates and stores the objects of Theater and OrderRequestList 



Class that uses the above created objects to execute the inputs and process them

1. ExecuteInputs

   

Driver Program:

1. MainClass.java -> Creates ExecuteInputs object and calls the method execute()




