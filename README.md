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
I am awaiting a laptop to be delivered to me before I can go further with this project. As I am in the process of overhauling my development environment on my workstation. I currently have a windows 10 development environment. I want to be able to boot Red Hat Linux and then boot Win 10 as a virtual machine. This will require me backing up about 500 to 700GB of data and creating a Windows 10 backup image and installing this on a VM ontop of Red Hat Linux. I need the laptop in order to ensure I have internet connectivity and to be able to backup correctly in case anything goes wrong. The estimated date of delivery is next week to the week after. I thought this would only take 5 to 10 days to deliver but it has taken about a month so far which is annoying to say the least. So bear with me and I will branch to a new edition of the simulator to V2.02 and create buses. Two differnt types. One with a control unit kinda multiplexing the input ports to output ports and one with bus arbitration. I will also have to add new comnponents optical flip flops with pr and clear pins and then lastly but not least I will have to refine the way debugging is done to a flag system in the constants file a flag for each major class. So I will be able to debug functionality by class. This will make debugging more modular and the simulator run faster!!.

I am really annoyed about the late delivery of my laptop but life goes on!!!!!!!!.

@2/8/22 @21:27
I have finally got the laptop and have installed IntelliJ Idea Community edition on it and ran my cloned downloaded code and I get image shown below. I have checked the code for current with the new collision detection algotithms. I am now going to have to install MySQL community edition and install the dictionary database for soundex word prediction for the database management system. I also have to get the MySQL java connector jar file and include it in the IntelliJ Idea project. Once this is done and verified then I can move on to installing Red Hat Linux Developer edition on my workstation and see how things go there. I have working code on a Linux system so now I am going to report the code to Red Hat Linux (I think I originally started development on Red Hat Linux so now I am returning to it). So over the coming days I should have a professionaly installed system which I can have a lot of fun with!!.
Regards Michael
![image](https://user-images.githubusercontent.com/107754541/182467258-2d4a64a7-5bea-41b3-97b5-7426bbf0da98.png)


@3/8/22 @7:34
I have installed MySQL comunity setup a dictionary database and imported a default dictionary for the document management system.(screenshot shows a file in a WYSIWYG editor and the tast word not in database so the popup dialog shows a list of words and if you click on suggest and change the word will be changed to test.This works via a soundex. I had to install the java-connector which was tedious as I tried installing the mysql.com java connector but I could not find the jar file to include it in my project. So I resorted to installing libmariadb and in /usr/share/java the mysql-connector.jar file was found. So I added this to the project dependencies and I got a working document management system with spell check and soundex suggestion!!.

I can now move on to the next stage of the setting up new systems to reading the licencing for Red Hat Linux Developer edition and then downloading the image and installing that to a bootable USB key. THen I will be able to install the Red Hat system on my workstation!!. Fingers crossed for everything working first try???.
![image](https://user-images.githubusercontent.com/107754541/182541023-28718383-5d6a-46b5-af7d-8bf253b0836d.png)

@3/8/22 @15:00
I have installed Red Hat Linux Developer Edition but dont have KDE just Gnome which is disappointing. I also only have dual monitors. The default drivers dont detect my third monitor?? I am going to raise a ticket as soon as I get my head around Reh Hat again/reorientate!!. I have to check the firewall and antivirus and then things should be good to go. I have installed IntelliJ Idea Community and got a sucessful compile and build of my repo code with version 2.01.

I have to install MySQL Community edition and the java connector and then run my application and check the word soundex auto suggest works and then I am good to go. This will take another day!!.

@11:09 @ 5/8/22
I have successifully installed mysql and mysql-connector-java.jar file to the project in order for spell checking to work on the document management and editing system.

I have sucessfully got my development environment up and running on Red Hat Linux with IntelliJ Idea IDE. This was relatively straight forward. This will mena that now I am open to developing I think first to fork? a new development edition on GitHub to V2.02. V2.02 is going to be an update with busses and pr and clear flipflops and new debugging functionality. I have to read the manual on GitHub on how to create a new fork(I have to read the manual but I think I can do it through IntelliJ Idea IDE)

@5/8/22 @ 15:00
I am stuck for a while I am reading the Git and GitHub manuals and cant seem to get my local to sync with remote or something like that!! I have to read the manuals!!!.

As time goes on and when the system upgrade is done I will add to the documentation and show you what this system is capable of!!.
Regards
Michael







![image](https://user-images.githubusercontent.com/107754541/174435515-2f15a520-4091-4cf4-bab6-ed286bf472c5.png)
