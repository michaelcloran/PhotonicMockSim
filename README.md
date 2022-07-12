# PhotonicMockSim
an optical computer simulation suite.

It is a Java 8 application with the Java 8 MVC pattern. It is capable of simulation and with it it is possible to design modules chips and mothreboards.

Please note that this software was developed by me when I was learning Java thus there is some bad coding and it will be replaced on a need to basis. I put the project 
here for collaboration and I need help specificially with the building of the execution queue. At the moment the circuit is in a graph. A part has 1 or more layers
which has 1 or more modules wheich has 1 or more components. The execution queue builds a list of optical pathways from an (deemed) input port to an output port. This
execution queue mechanism is a weakness in the project as with complex feedback loops you have to include an optical coupler 1 to many to link in the feedback loop.

If anyone can help with this project then I will be delighted to hear from you.!!

you can contact me via email: michaelcloran2010@gmail.com

@12/7/22 @ 14:45
I am awaiting a laptop to be delivered to me before I can go further with this project. As I am in the process of overhauling my development environment on my workstation. I currently have a windows 10 development environment. I want to be able to boot Red Hat Linux and then boot Win 10 as a virtual machine. This will require me backing up about 500 to 700GB of data and creating a Windows 10 backup image and installing this on a VM ontop of Red Hat Linux. I need the laptop in order to ensure I have internet connectivity and to be able to backup correctly in case anything goes wrong. The estimated date of delivery is next week to the week after. I thought this would only take 5 to 10 days to deliver but it has taken about a month so far which is annoying to say the least. So bear with me and I will branch to a new edition of the simulator to V2.03 and create buses. Two differnt types. One with a control unit kinda multiplexing the input ports to output ports and one with bus arbitration. I will also have to add new comnponents optical flip flops with pr and clear pins and then lastly but not least I will have to refine the way debugging is done to a flag system in the constants file a flag for each major class. So I will be able to debug functionality by class. This will make debugging more modular and the simulator run faster!!.

I am really annoyed about the late delivery of my laptop but life goes on!!!!!!!!.

As time goes on and when the system upgrade is done I will add to the documentation and show you what this system is capable of!!.
Regards
Michael







![image](https://user-images.githubusercontent.com/107754541/174435515-2f15a520-4091-4cf4-bab6-ed286bf472c5.png)
