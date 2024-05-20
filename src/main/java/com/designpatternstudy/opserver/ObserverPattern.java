package com.designpatternstudy.opserver;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * @author : iyeong-gyo
 * @package : com.designpatternstudy.opserver
 * @since : 20.05.24
 */
public class ObserverPattern {

  public static void main(String[] args) {
    int magicNumber = 4;

    DiceGame diceGame = getDiceGame(magicNumber);
    diceGame.addPlayer(new EvenBettingPlayer("짝궁댕이"));
    diceGame.addPlayer(new OddBettingPlayer("홀아비"));
    diceGame.addPlayer(new OddBettingPlayer("홀쭉이"));
    diceGame.addPlayer(new PickBettingPlayer("타짜", magicNumber));

    System.out.println("############");
    System.out.println("게임 시작");
    System.out.println("############");
    for (int i = 0; i < 5; i++) {
      diceGame.play();
    }
  }

  private static DiceGame getDiceGame(int bound) {
    return new UnFairDiceGame(bound);
  }

  /* ============================================================ */
  static abstract class Player {
    private String name;

    public Player(String name) {
      this.name = name;
    }

    public String getName() {
      return name;
    }

    public abstract void update(int diceNumber);
  }

  static class OddBettingPlayer extends Player{
    public OddBettingPlayer(String name) {
      super(name);
    }

    @Override
    public void update(int diceNumber) {
      if (diceNumber % 2 == 1) {
        System.out.println(getName() + " win!");
      }
    }
  }

  static class EvenBettingPlayer extends Player{
    public EvenBettingPlayer(String name) {
      super(name);
    }

    @Override
    public void update(int diceNumber) {
      if (diceNumber % 2 == 0) {
        System.out.println(getName() + " win!");
      }
    }
  }

  static class PickBettingPlayer extends Player{
    private int picNum;
    public PickBettingPlayer(String name) {
      super(name);
    }

    public PickBettingPlayer(String name, int picNum) {
      super(name);
      this.picNum = picNum;
    }

    @Override
    public void update(int diceNumber) {
      if (diceNumber == picNum) {
        System.out.println(getName() + " win!");
      }
    }
  }
  /* ============================================================ */




  /* ============================================================ */
  static abstract class DiceGame {
    protected List<Player> players = new LinkedList<>();

    public void addPlayer(Player player) {
      players.add(player);
    }

    public abstract void play();
  }

  static class FairDiceGame extends DiceGame {
    private Random random = new Random();

    @Override
    public void play() {
      int diceNumber  = random.nextInt(6) + 1;
      System.out.println("주사위 던졌다. = " + diceNumber);

      Iterator<Player> iterator = players.iterator();
      while (iterator.hasNext()) {
        iterator.next().update(diceNumber);
      }
    }
  }

  /*
    - 특정한 숫자만 나오는 게임
   */
  static class UnFairDiceGame extends DiceGame {
    private final int NUMBER;

    public UnFairDiceGame(int number) {
      this.NUMBER = number;
    }

    @Override
    public void play() {
      System.out.println("주사위 던졌다. = " + NUMBER);
      for (Player player : players) {
        player.update(NUMBER);
      }
    }
  }
  /* ============================================================ */
}
