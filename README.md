# QuodAI-Plugin-Challenge

## App description

Create a simple JetBrains extensions to:

 - Open local files  on Github Webpage 


 - Show the number of stars for the repo using the Github API https://docs.github.com/en/rest

## Depedencies used
- Git4IDEA extensions for receiving branches and repo
- Org.Json for parsing JSON data
- Jgoodies for Form UI

## How to set up this project locally

- Clone the project to your desire local file using Terminal

   ```sh
    git clone https://github.com/minhquan0902/QuodAI-Plugin-Challenge.git
   ```
- Open the Project using IntelliJ
- Make sure you are using the latest version of IntelliJ: https://www.jetbrains.com/idea/download/#section=windows
- Make sure you have JRE of the latest Java 16 and have the latest JDK 16 version

## Technical decision

- What would I improve:

 Throughout this Challenges, I have face many issues and in the end, I don't know how to conduct the build to suit all run time environment with different version of Java. I would need to research more on this. Also, the search function for the star count using GitHub API is not yet efficient, I will need to find a better coding technique regarding search and display data in the future
