# BulletAssistant

The Bullet Assistant

This program was developed to assist Air Force personnel in writing bullets for EPRs and OPRs by reducing the amount of time it takes to write and condense a bullet point. This was achieved by reducing the time it takes to look up related action verbs and by reducing the time it takes to look up and replace commonly used shorthand. After a verb is typed, the program will automatically populate related words from a prebuilt CSV file so that the writer has alternate options readily available if needed. When the condense button is clicked, the program will cross reference the writer’s text with a prebuilt shorthand CSV file and automatically condense the writer’s text. Once condensed, the text is automatically copied to the clipboard. Options to add save new shorthand and related words are also provided to the user.

Prerequisites and Installation

The folder containing this program’s .jar file and both CSVs can be downloaded and saved anywhere. When the .jar file is opened, the program is set to automatically read from two CSV files within the same folder containing this program. Therefore, both CSV files must be saved in the same folder as this program’s .jar file. The CSV files must be formatted so that related words are on the same row. Also, only one abbreviation/shorthand is allowed per referenced word.  Note: Code can be modified so that the user is prompted to select the location of the two CSV files manually.

Test Cases

The following are examples of user typed input. The output text is what was copied to the clipboard when the user clicks the “Condense & Copy” button. 
  	
	Input:
  	Set up $10 million system at headquarters
  	Output:
  	- Set up $10 M system at HQ 

	Input:
	Led 100 airman with guidance from Captain America
	Output:
	- Led 100 Amn with guidance from Capt America 
	

Notes for Debugging, Testing, and Development

The System.out.println() function was used at various points in the code to see what data was being processed. There are a few of these lines commented out in useful locations for further testing. To save search time, the abbreviations CSV is put into a binary tree. However, because the CSV file can add special characters to the first entry, the binary tree may not be sorted properly. Also, to save time when adding large amounts of entries to the CSV files, it is recommended that all entries be added to CSV files manually. 


