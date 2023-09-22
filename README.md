# command-line

![Maintained Label](https://img.shields.io/badge/Maintained-No-red?style=for-the-badge)
![Deprecated Label](https://img.shields.io/badge/Deprecated-Yes-lightgray?style=for-the-badge)
![Archived Label](https://img.shields.io/badge/Archived-Yes-lightgray?style=for-the-badge)

A Simple Command Line That Creates a System Process or one of the Following Commands:

~~~
cd
cd..
history
!! - Execute the previous command
!<integer value> - Execute a command from history with a given index
help
exit
~~~

You can start the program two ways.  Using the commandLine.ps1 file or directly compiling and running the java files

### Using PowerShell ###

Navigate to the project directory and Enter:

> & .\commandLine.ps1

This may throw an error.  If so, execute the following command and try again:

> Set-ExecutionPolicy RemoteSigned

### Other Methods ###

You can also directly compile the java code and run the commandLine file

**Compile the files**
>javac CommandLine.java

>javac SystemProcess.java

>javac DirectoryProcess.java

**Run the Java Code**
>java CommandLine
