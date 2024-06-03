package com.designpatternstudy.behavioral.command;

import com.designpatternstudy.behavioral.command.CommandPattern.ColorCommand.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : iyeong-gyo
 * @package : com.designpatternstudy.behavioral.command
 * @since : 03.06.24
 */
/*
 [Command 패턴]
 - 하나의 명령(기능)을 객체화환 패턴
 - 객체는 전달할 수 있고 보관할 수 있은, 즉 명령(기능)을 전달하고 보관할 수 있게 됨.
 - 배치 싱행, Undo/Redo, 우선순위가 높은 명령을 먼저 실행하기 등이 가능해 짐
 */
public class CommandPattern {

  public static void main(String[] args) {
    ClearCommand clearCommand = new ClearCommand();
    ColorCommand yellowColorCommand = new ColorCommand(Color.YELLOW);
    ColorCommand blueColorCommand = new ColorCommand(Color.BLUE);
    PrintCommand printCmd = new PrintCommand("Hello World");
    MoveCommand moveCommand = new MoveCommand(3, 5);

    CommandGroup commandGroup = new CommandGroup();
    commandGroup.add(clearCommand);
    commandGroup.add(yellowColorCommand);
    commandGroup.add(printCmd);
    commandGroup.add(blueColorCommand);
    commandGroup.add(printCmd);
    commandGroup.run();

  }

  interface Command {
    void run();
//    void undo();
  }

  static class CommandGroup implements Command {
    List<Command> commands = new ArrayList<>();

    public void add(Command command) {
      commands.add(command);
    }

    @Override
    public void run() {
      int cntCommands = commands.size();
      for (int i = 0; i < cntCommands; i++) {
        Command command = commands.get(i);
        command.run();
      }
    }
  }

  static class ClearCommand implements Command {

    @Override
    public void run() {
      for (int i = 0; i < 10000; i++) {
        System.out.println();
      }
    }
  }

  static class PrintCommand implements Command {

    private String content;

    public PrintCommand(String content) {
      this.content = content;
    }

    @Override
    public void run() {
      System.out.println("content = " + content);
    }
  }

  static class MoveCommand implements Command {

    private int x;
    private int y;

    public MoveCommand(int x, int y) {
      this.x = x;
      this.y = y;
    }

    @Override
    public void run() {
      System.out.println(String.format("%c[%d;%df", 0x1B, y, x));
    }
  }

  static class ColorCommand implements Command {

    public enum Color {
      BLACK("\u001B[30m"), RED("\u001B[31m"),
      GREEN("\u001B[32m"), YELLOW("\u001B[33m"),
      BLUE("\u001B[34m"), PURPLE("\u001B[35m"),
      CYAN("\u001B[36m"), WHITE("\u001B[37m");

      private final String color;

      Color(String color) { this.color = color; }
      public String getColor() { return color; }
    }

    private Color color;
    public ColorCommand(Color color) {
      this.color = color;
    }

    @Override
    public void run() {
      System.out.println(color.getColor());
    }
  }
}
