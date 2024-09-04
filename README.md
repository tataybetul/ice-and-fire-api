## Installation & Test Run
- Clone this repo.

- Run this command `mvn clean install` on main folder.

- If you are using Java version 17 as project sdk, you can run tests via feature files with right click + run

## In case there is a problem
- Try to do
    - `export MAVEN_OPTS=--add-opens=java.base/java.util=ALL-UNNAMED`
    - `mvn clean install`
- If not solved, check java version 17 to use.

  


