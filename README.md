# ACDC - Static website generation

### Static Dynamic Website
This project is done as part of my first year of study as a student at IMT Atlantique engineering school. 
The project is developed for the [STACK research group](http://stack.inria.fr/) in order to provide them a tool that allow them to publish news on their static website easily. 

### About project
The project is adding news into the 'News' tab of the [website](http://stack.inria.fr/).
An external tool named [Jekyll](https://jekyllrb.com/) is used to convert markdown into html pages and to build the html website.

The architecture is described in the following class diagram:
![class diagram](https://github.com/CedricNoyel/ACDC/blob/master/ClassDiagram.png)

### Build and execute project
1. Open the folder in Eclipse
2. Export -> Java -> Java runnable file
3. Open CMD and run the generated jar file with  ```java -jar <program.jar>```

### Requirements
- [Java](https://java.com/) (at least v8)
- [Jekyll](https://jekyllrb.com/docs/) installed and up to date
- Your computer must be pre-configured with a [git ssh key](https://help.github.com/articles/connecting-to-github-with-ssh/)

## TODO List
- [x] User CMD Interface to gather information about the new post
- [x] Convert gathered information into markdown code
- [x] Transform the markdown into HTML (using Jekyll)
- [x] Create local server and open the HTML page
- [x] Push generated post on GitHub

### LOG 2018-11-21
- Fix bugs
- Refactor code
- Add javadoc
- Add test on Post class

### LOG 2018-11-14
- Create executeCmd function that allow us to execute any cmd in a new thread
    |-> Add cmd to add, commit and push a git repositery
- Clean code
- Add javadoc
- Generate class diagram

### LOG 2018-11-07
No time this week

### LOG 2018-10-31
No time this week

### LOG 2018-10-23
- Add an author to the news
- Generate markdown file and fill it with information entered by the user

### LOG 2018-10-16
- CMD interface created allowing user to enter information in order to generate the new post
- The user is able to enter all information in order to create the markdown code
