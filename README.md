# mazeGame

This repository is a Java maze game project packaged with source, compiled classes, documentation, and resources.

Structure
- mazeGame-main/src/ — Java source (packages: logic, UI).
- mazeGame-main/bin/ — compiled .class files (rebuildable from src).
- mazeGame-main/doc/ — Javadoc HTML (open doc\index.html).
- mazeGame-main/docs/ — game assets and design docs (images, sounds, diagrams, USECASE, CRC).
- LICENSE, .project, .classpath, MazeGame.iml — project metadata and license.

Run
- Recommended: open in an IDE (Eclipse/IntelliJ) and run the main class.
- Command-line example:
  javac -d out src\\**\\*.java
  java -cp out <package>.MainClass

Notes
- Ensure working directory includes resource paths so media load correctly.
