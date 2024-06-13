package com.designpatternstudy.behavioral.observer;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * @author : iyeong-gyo
 * @package : com.designpatternstudy.opserver
 * @since : 20.05.24
 */
/*
 [옵저버 패턴]
 - 객체 간의 일대다 의존성을 정의
 - 이 패턴을 사용하면 하나의 객체 상태가 변경될 때, 그 객체에 의존하는 다른 객체들이 자동으로 통지받고 갱신
 - 주로 이벤트 핸들링 시스템이나 데이터의 변경을 다른 객체들에게 전파해야 하는 경우에 사용
 */
public class ObserverPattern {

	private static DiceGame getDiceGame(int bound) {
		return new UnFairDiceGame(bound);
	}

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

	static abstract class Player {
		private final String name;

		public Player(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}

		public abstract void update(int diceNumber);
	}

	static class OddBettingPlayer extends Player {
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

	static class EvenBettingPlayer extends Player {
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

	static class PickBettingPlayer extends Player {
		private int picNum;

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

	static abstract class DiceGame {
		protected List<Player> players = new LinkedList<>();

		public void addPlayer(Player player) {
			players.add(player);
		}

		public abstract void play();
	}

	/*
	  - 공정한 게임
	 */
	static class FairDiceGame extends DiceGame {
		private final Random random = new Random();

		// 게임 결과를 모든 Player에게 전파
		@Override
		public void play() {
			int diceNumber = getDiceNumber();
			System.out.println("주사위 던졌다. = " + diceNumber);
			for (Player player : players) {
				player.update(diceNumber);
			}
		}

		// 공정한 주사위 번호 생성
		private int getDiceNumber() {
			return random.nextInt(6) + 1;
		}
	}

	/*
	  - 불공정한 게임
	 */
	static class UnFairDiceGame extends DiceGame {

		private final int NUMBER;

		public UnFairDiceGame(int number) {
			this.NUMBER = number;
		}

		// 게임 결과를 모든 Player에게 전파
		@Override
		public void play() {
			System.out.println("주사위 던졌다. = " + NUMBER);
			players.forEach(player -> player.update(NUMBER));
		}
	}
	/* ============================================================ */
}
