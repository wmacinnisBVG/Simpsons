package zork;

import java.util.Scanner;

public class Parser {
  private CommandWords commands; // holds all valid command words
  private Scanner in;

  public Parser() {
    commands = new CommandWords();
    in = new Scanner(System.in);
  }

  public Command getCommand() throws java.io.IOException {
    String inputLine = "";
    String[] words;
    

    System.out.print("> "); // print prompt

    inputLine = in.nextLine();
    int space = inputLine.indexOf(" ");
    int space2 = inputLine.indexOf(" ", space+1);
    if(space2 > 0){
       words = new String[2];
      words[0] = inputLine.substring(0, space2);
      words[1] = inputLine.substring(space2);
    }else
    words = inputLine.split(" ");
    
    String word1 = words[0];
    String word2 = null;
    if (words.length > 1)
      word2 = words[1];

    if (commands.isCommand(word1))
      return new Command(word1, word2);
    else
      return new Command(null, word2);
  }

  /**
   * Print out a list of valid command words.
   */
  public void showCommands() {
    commands.showAll();
  }
}
