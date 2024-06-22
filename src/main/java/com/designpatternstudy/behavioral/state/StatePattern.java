package com.designpatternstudy.behavioral.state;

import java.util.Scanner;

/**
 * @author : iyeong-gyo
 * @package : com.designpatternstudy.behavioral.state
 * @since : 22.06.24
 * - 상태를 객체화 한 패턴
 * - 조건절을 효과적으로 줄여주는 패턴
 */
public class StatePattern {

	public static void main(String[] args) {
		Player player = new Player();
		Scanner scanner = new Scanner(System.in);

		while (true) {
			System.out.println("플레이어의 상태 : "
				+ player.getState().getDescription()
				+ " /속도 : " + player.getSpeed() + "km/h");
			System.out.println("[1]제자리 서기 [2]앉기 [3]걷기 [4]뛰기 [5]종료");
			String input = scanner.next();
			System.out.println();

			if (input.equals("1")) {
				player.getState().standUP();
			} else if (input.equals("2")) {
				player.getState().sitDown();
			} else if (input.equals("3")) {
				player.getState().walk();
			} else if (input.equals("4")) {
				player.getState().run();
			} else if (input.equals("5"))
				break;
		}

		scanner.close();

	}

	static class Player {
		private int speed;

		public int getSpeed() {
			return speed;
		}

		public void setSpeed(int speed) {
			this.speed = speed;
		}

		private State state = new StandUpState(this);

		public State getState() {
			return state;
		}

		public void setState(State state) {
			this.state = state;
		}

		public void talk(String msg) {
			System.out.println("플레이어의 말: [" + msg + "]");
		}
	}

	static abstract class State {
		protected Player player;

		public State(Player player) {
			this.player = player;
		}

		public abstract void standUP();

		public abstract void sitDown();

		public abstract void walk();

		public abstract void run();

		public abstract String getDescription(); // 현재 상태를 문자여로 반환
	}

	static class StandUpState extends State {

		public StandUpState(Player player) {
			super(player);
		}

		@Override
		public void standUP() {
			player.talk("언제 움직일꺼야?");
		}

		@Override
		public void sitDown() {
			player.setState(new SitDownState(player));
			player.talk("앉으니깐 편하고 좋습니다.");
		}

		@Override
		public void walk() {
			player.setSpeed(5);
			player.setState(new WalkState(player));
			player.talk("걷기는 제2의 생각하기다.");
		}

		@Override
		public void run() {
			player.setSpeed(10);
			player.setState(new RunState(player));
			player.talk("갑자기 뛴다.");
		}

		@Override
		public String getDescription() {
			return "제자리에 서 있음";
		}
	}

	static class SitDownState extends State {

		public SitDownState(Player player) {
			super(player);
		}

		@Override
		public void standUP() {
			player.setState(new StandUpState(player));
			player.talk("인나자");
		}

		@Override
		public void sitDown() {
			player.talk("쥐나겠다.");
		}

		@Override
		public void walk() {
			player.talk("앉아서 어떻게 걸어? 일단은 서자");
			player.setState(new StandUpState(player));
		}

		@Override
		public void run() {
			player.talk("앉아서 어떻게 뛰어? 일단은 서자");
			player.setState(new StandUpState(player));
		}

		@Override
		public String getDescription() {
			return "앉아있음";
		}
	}

	static class WalkState extends State {
		public WalkState(Player player) {
			super(player);
		}

		@Override
		public void standUP() {
			player.setSpeed(0);
			player.talk("멈춰");
			player.setState(new SitDownState(player));
		}

		@Override
		public void sitDown() {
			player.setSpeed(0);
			player.talk("걷다가 앉으면 넘어질 수 있어요.");
			player.setState(new SitDownState(player));
		}

		@Override
		public void walk() {
			player.talk("난 걷는걸 좋아하지.");
		}

		@Override
		public void run() {
			player.setSpeed(20);
			player.talk("걷다가 뛰면 열라 빨리 뛸 수 있지");
			player.setState(new RunState(player));
		}

		@Override
		public String getDescription() {
			return "걷는 중";
		}
	}

	static class RunState extends State {
		public RunState(Player player) {
			super(player);
		}

		@Override
		public void standUP() {
			player.talk("뛰다가 갑자기 서면 무릎 연골 나가요.");
			player.setSpeed(0);
			player.setState(new StandUpState(player));
		}

		@Override
		public void sitDown() {
			player.talk("뛰다가 앉으라고? ㅁㅊㄴ");
			player.setSpeed(0);
			player.setState(new StandUpState(player));
		}

		@Override
		public void walk() {
			player.talk("속도를 줄일 께요");
			player.setSpeed(8);
			player.setState(new WalkState(player));
		}

		@Override
		public void run() {
			player.talk("더 빨리 뛰라는 얘기지?");
			player.setSpeed(player.getSpeed() + 2);
		}

		@Override
		public String getDescription() {
			return "뛰는 중";
		}
	}
}
