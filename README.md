## Requirements
JRE 8. Download here if you dont have it https://docs.oracle.com/javase/8/docs/technotes/guides/install/install_overview.html

## How to run application
Clone the repository on your local file system. cd into cloned source folder `cd your/path/headspace/src`
Now start library in interactive mode just type 
`./library`

    $./library
    Welcome to your library!
    
    > help
    
    add "$title" "$author": adds a books to the library with the given title and author. All books are unread by default.
    read "$title": marks a given books as read.
    show all: displays all of the books in the library
    show unread: display all of the books that are unread
    show all by "$author": shows all of the books in the library by the given author.
    show unread by "$author": shows the unread books in the library by the given author
    undo: undoes the last mutational command (if a books was marked as read it marks it as unread; if a books was added, it gets removed)
    quit: quits the program.
    
    > 

## How to run tests
cd into cloned source folder `cd your/path/headspce/src`
Now start library tests just type 
`./library-test`
    
## Design decisions

1. After reading the requirements from https://gist.github.com/mattfonda/54375c58edebbc797a7f3b0b4bde1fe6; I realized that there are two main resources
    * Author
    	- name
    	- books (empty by default)
    * Book
    	- title
    	- author
    	- read (false by default)
  
    The resources are represented by there own classes in resources package
2. Each resource gets their own service class where the application logic/ business logic is handled. Library is implemented as an orchestrator that uses other services. All services are found in service package
3. Validators are run before creating and storing records in database. They are under validator package.
4. Database is in memory which does not persist after the application is closed.
5. All commands and errors are available as string constants in constants package.
6. Did not use any testing framework because the application was fairly simple to test. Tests are included in tests package.
7. Scripts `library` and `library-test` simply run `headspace.jar` and `headspace-test.jar` respectively.

## Choice of Language : Java
Java runs on almost all environments. I used `java 8` for this project. I chose java because of I feel comfortable programming in java. It is a strongly typed, forces me to make less syntax errors and it supports both procedural as well as functional programming styles which gives me more flexibility as a programmer. 
