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
    DiceGame diceGame = new FairDiceGame();

    Player player1 = new EvenBettingPlayer("짝궁댕이");
    Player player2 = new OddBettingPlayer("홀아비");
    Player player3 = new OddBettingPlayer("홀쭉이");

    diceGame.addPlayer(player1);
    diceGame.addPlayer(player2);
    diceGame.addPlayer(player3);

    for (int i = 0; i < 5; i++) {
      diceGame.play();
    }

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
  /* ============================================================ */
}
