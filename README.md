# BulletAssistant

Introduction

This program was developed to assist Air Force personnel in writing bullets for EPRs and OPRs by reducing the amount of time it takes to write and condense a bullet point. This was achieved by reducing the time it takes to look up commonly used shorthand and synonyms to action verbs. After a verb is typed, the program will automatically populate related words from a prebuilt CSV file so that the writer has alternate options readily available. When the condense button is clicked, the program will cross-reference the writer’s text with a prebuilt shorthand CSV file and automatically replace the writer’s text with the corresponding shorthand. Once condensed, the text is automatically copied to the clipboard. Options to change spacing width are also provided.

Prerequisites and Installation

Java 8 is required to run the BulletAssistant.jar program. The recently released Java version has bugs that affect JavaFX GUI. The folder containing this program’s .jar file and both .csv files can be downloaded and saved anywhere. Both .csv files must be saved in the same folder as this program’s .jar file. To run the program, double click the BulletAssistant.jar file. The .csv files must be formatted so that related words are on the same row, and only one abbreviation is allowed per referenced word.

	**For Linux based OS, follow the steps below**
	sudo apt-get install default-jre
	sudo apt-get install openjfx
	sudo update-alternatives --config java <-Press option 2 for Java 8
	sudo reboot
	java -version  <-Should no longer be Java 11
	Double Click BulletAssistant.jar to open  <-Opening with “java -jar” will give compile error

Test Cases

The following are examples of user typed input. The output text is what was copied to the clipboard
when the user clicks the “Condense” button.
  	
	Input:
  	Destroyed Hydra intelligence, surveillance, and reconnaissance headquarters; led one thousand 	Airman into battle with guidance 	from Master Sergeant Barnes--found $900 thousand in ammunition
  
	Output:
	-Destroyed Hydra ISR HQ; led one thousand Amn into battle w/guidance from MSgt Barnes--found $900K in ammo
	

Notes for Debugging, Testing, and Development

Hash tables and self-balancing trees should be used to speed up the time required to search through
synonyms and abbreviations. The current setup uses an array of lists for synonyms and a binary tree for
abbreviations.

	Version 2 Changes:
	- Added Linux compatibility
	- Added progress bar to track bullet length when typing directly into the box
	- Fixed initial double dash bug
	- Fixed issue preventing words from being checked if they had a ‘;’ at the end
	- Fixed GUI padding
	- Added highlighting capability to “Related Words:” suggestion list at the bottom
	- Updated README


	Additional User Requests:
	- Only senior leadership should be able to add to abbreviations list; users can make quarterly
	  requests to add new words if needed
	- Application should have a feature that can highlight repeated words, unapproved
	  abbreviations, and commonly misused phrases for proofreading purposes
	- Application should only condense enough words to keep empty space to a minimum
