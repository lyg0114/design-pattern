package com.designpatternstudy.behavioral.memento;

import java.util.ArrayList;
import java.util.Random;

/**
 * @author : iyeong-gyo
 * @package : com.designpatternstudy.behavioral.memento
 * @since : 21.06.24
 *
 * <p> 개요
 * - 객체의 상태를 저장하고 필요할 때 복원하여 사용하는 패턴
 * - Memento 패턴을 사용하여 Walker의 상태를 저장하고 복원함으로써 목표 위치에 더 빠르게 도달.
 *
 * <p> 프로그램의 동작 설명
 * - Memento 패턴을 사용하여 Walker 객체가 시작위치(0, 0) 에서 목표 위치(10, 10)에 도달할 때까지 무작위로 이동.
 *
 * <p> 동작 순서
 * - Walker 객체 생성 						: 초기 위치 (0, 0)에서 목표 위치 (10, 10)로 이동하기 위해 Walker 객체를 생성.
 * - 행동 정의 								: 가능한 네 가지 행동 ("UP", "DOWN", "LEFT", "RIGHT")을 배열로 정의.
 * - 무한 루프 								: Walker가 목표 위치에 도달할 때까지 무한 루프를 실행.
 * - 무작위 행동 선택 및 수행 				: 각 반복에서 무작위로 행동을 선택하고 Walker가 해당 행동을 수행. 새로운 위치에서 목표 위치까지의 거리를 계산.
 * - 거리 비교 및 상태 저장					: 현재 거리가 최소 거리보다 작으면, 최소 거리를 업데이트하고 상태를 저장, 그렇지 않으면, 이전 상태로 복원
 * - 목표 위치에 도달한 경우 루프를 종료.
 * - 결과 출력: Walker가 이동한 경로를 출력.
 *
 * <p> Memento 패턴 사용 예시
 * - 텍스트 편집기: 실행취소 기능
 * - 그래픽 소프트웨어: "실행 취소"와 "재실행(Redo)" 기능
 * - 게임 개발: 게임 중간에 저장된 상태로 돌아가기 기능
 * - 설정 관리: 이전 설정 상태를 저장하여 사용자가 변경 후 문제가 발생하면 이전 설정으로 되돌리는 기능
 * - 상태 기반의 워크플로우: 복잡한 비즈니스 프로세스 관리 시스템에서, 각 단계의 상태를 저장하여 필요할 때 이전 단계로 돌아가거나 복원할 수 있는 기능
 */

public class MementoPattern {

	public static void main(String[] args) {
		// Walker 객체를 (0, 0) 위치에서 (10, 10) 위치로 이동시키기 위해 생성
		Walker walker = new Walker(0, 0, 10, 10);

		// 가능한 행동들을 정의
		String[] actions = {"UP", "DOWN", "LEFT", "RIGHT"};
		Random random = new Random();  // 랜덤 행동을 선택하기 위해 Random 객체 생성
		double minDistance = Double.MAX_VALUE;  // 최소 거리를 초기화
		Walker.Memento memento = null;  // Walker의 상태를 저장할 Memento 객체

		while (true) {
			// 무작위로 행동을 선택
			String action = actions[random.nextInt(4)];
			double distance = walker.walk(action);  // 선택한 행동을 수행하고 새로운 위치에서의 거리를 계산
			System.out.println(action + " " + distance);  // 행동과 현재 거리를 출력

			if (distance == 0.0) {
				break;  // 목표 위치에 도달하면 루프를 종료
			}

			if (minDistance > distance) {
				// 현재 거리가 최소 거리보다 작으면, 최소 거리를 업데이트하고 상태를 저장
				minDistance = distance;
				memento = walker.createMemento();
			} else {
				// 그렇지 않으면, 이전 상태로 복원
				if (memento != null) {
					walker.restoreMemento(memento);
				}
			}
		}

		System.out.println("walker's path : " + walker);  // Walker가 이동한 경로를 출력
	}

	static class Walker {
		private int currentX, currentY;  // 현재 위치
		private int targetX, targetY;  // 목표 위치
		private ArrayList<String> actionList = new ArrayList<>();  // 수행한 행동을 저장하는 리스트

		public Walker(int currentX, int currentY, int targetX, int targetY) {
			this.currentX = currentX;
			this.currentY = currentY;
			this.targetX = targetX;
			this.targetY = targetY;
		}

		// 주어진 행동을 수행하고 새로운 위치에서 목표 위치까지의 거리를 반환
		public double walk(String action) {
			actionList.add(action);
			if (action.equals("UP")) {
				currentY += 1;
			} else if (action.equals("RIGHT")) {
				currentX += 1;
			} else if (action.equals("DOWN")) {
				currentY -= 1;
			} else if (action.equals("LEFT")) {
				currentX -= 1;
			}

			// 두 점 사이의 직선 거리 계산
			return Math.sqrt(Math.pow(currentX - targetX, 2) + Math.pow(currentY - targetY, 2));
		}

		// Walker의 상태를 저장하는 Memento 클래스
		public static class Memento {
			private int x, y;  // 저장된 위치
			private ArrayList<String> actionList = new ArrayList<>();  // 저장된 행동 리스트

			private Memento() {
			}
		}

		// 현재 상태를 Memento 객체로 저장
		public Memento createMemento() {
			Memento memento = new Memento();
			memento.x = this.currentX;
			memento.y = this.currentY;
			memento.actionList = (ArrayList<String>)this.actionList.clone();
			return memento;
		}

		// Memento 객체로부터 상태를 복원
		public void restoreMemento(Memento memento) {
			this.currentX = memento.x;
			this.currentY = memento.y;
			this.actionList = (ArrayList<String>)memento.actionList.clone();
		}

		@Override
		public String toString() {
			return actionList.toString();
		}
	}
}
