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

    words = inputLine.split(" "); 
    //This currently works for two words but can be modified to work with three (copy line 28-30), only drawback is that both words 2 and 3 will have to be only one word unlike the command
    for(int i = 0; i < commands.getCommandWords().length; i++){
      if(inputLine.indexOf(commands.getCommandWords()[i]) == 0){
        words = new String[2];
        words[0] = inputLine.substring(0, commands.getCommandWords()[i].length());
        if(inputLine.length() - words[0].length() > 0){
          words[1] = inputLine.substring(words[0].length() + 1);
        }
        //System.out.println(words[0] + "." + words[1] + "."); //debug thing
        break;
      }
    }

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