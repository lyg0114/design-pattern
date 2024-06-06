package com.designpatternstudy.behavioral.mediator;

import java.util.Scanner;

/**
 * @author : iyeong-gyo
 * @package : com.designpatternstudy.behavioral.mediator
 * @since : 06.06.24
 */
/*
 [Mediator 패턴]
 - 복잡한 객체간의 관계를 중재자를 두어 복잡한 관계를 단순화 시키는 패턴
 */
public class MediatorPattern {

  public static void main(String[] args) {
    SmartHome home = new SmartHome();
    try (Scanner scanner = new Scanner(System.in)) {
      do {
        home.report();
        System.out.println("[1] 문 열기");
        System.out.println("[2] 문 닫기");
        System.out.println("[3] 창문 열기");
        System.out.println("[4] 창문 닫기");
        System.out.println("[5] 에어컨 켜기");
        System.out.println("[6] 에어컨 끄기");
        System.out.println("[7] 보일러 켜기");
        System.out.println("[8] 보일러 끄기");
        System.out.println("[9] 종료");

        System.out.println("명령 : ");
        int i = scanner.nextInt();
        if(i == 1) home.door.open();
        else if (i == 2) home.door.close();
        else if (i == 3) home.window.open();
        else if (i == 4) home.window.close();
        else if (i == 5) home.aircon.on();
        else if (i == 6) home.aircon.off();
        else if (i == 7) home.bolier.on();
        else if (i == 8) home.bolier.off();
        else if (i == 9) return;

      } while (true);
    }
  }

  interface Mediator {

    void participatnChanged(Participant participant);
  }

  static class SmartHome implements Mediator {

    public Door door = new Door(this);
    public Window window = new Window(this);
    public CoolAircon aircon = new CoolAircon(this);
    public HeatBolier bolier = new HeatBolier(this);

    /*
     - 참여객체들간의 관계가 모두 하나의 participatnChanged 메서드에 집중되어 있음.
     - 참여객체들간의 관계는 개발중 자주 변경이 이루어지는 부분이다.
     - 자주 변경되는 지점을 한곳으로 집중 -> 시스템 간결 -> 유지보수성이 좋다.
     */
    @Override
    public void participatnChanged(Participant participant) {
      if (participant == door && !door.isClosed()) {
        aircon.off();
        bolier.off();
      }

      if (participant == window && !window.isClosed()) {
        aircon.off();
        bolier.off();
      }

      if (participant == aircon && aircon.isRunning()) {
        bolier.off();
        window.close();
        door.close();
      }

      if (participant == bolier && bolier.isRunning()) {
        aircon.off();
        window.close();
        door.close();
      }
    }

    public void report() {
      System.out.println("\033[H\033[2J[집안 상태]");
      System.out.println(door);
      System.out.println(window);
      System.out.println(aircon);
      System.out.println(bolier);
      System.out.println();
    }
  }

  static abstract class Participant {

    protected Mediator mediator;

    public Participant(Mediator mediator) {
      this.mediator = mediator;
    }
  }

  static class Door extends Participant {

    private boolean bClosed = true;

    public Door(Mediator mediator) {
      super(mediator);
    }

    public void open() {
      if (!bClosed) {
        return;
      }

      bClosed = false;

      mediator.participatnChanged(this);
    }

    public void close() {
      if (bClosed) {
        return;
      }

      bClosed = true;

      mediator.participatnChanged(this);
    }

    public boolean isClosed() {
      return bClosed;
    }

    @Override
    public String toString() {
      if (bClosed) {
        return "# 문 : 닫힘";
      } else {
        return "# 문 : 열림";
      }
    }
  }

  static class Window extends Participant {

    private boolean bClosed = true;

    public Window(Mediator mediator) {
      super(mediator);
    }

    public void open() {
      if (!bClosed) {
        return;
      }

      bClosed = false;
      mediator.participatnChanged(this);
    }

    public void close() {
      if (bClosed) {
        return;
      }

      bClosed = true;
      mediator.participatnChanged(this);
    }

    public boolean isClosed() {
      return bClosed;
    }

    @Override
    public String toString() {
      if (bClosed) {
        return "# 창 : 닫힘";
      } else {
        return "# 창 : 열림";
      }
    }
  }

  static class HeatBolier extends Participant {

    private boolean bOff = true;

    public HeatBolier(Mediator mediator) {
      super(mediator);
    }

    public void on() {
      if (!bOff) {
        return;
      }

      bOff = false;
      mediator.participatnChanged(this);
    }

    public void off() {
      if (bOff) {
        return;
      }

      bOff = true;
      mediator.participatnChanged(this);
    }

    public boolean isRunning() {
      return !bOff;
    }

    @Override
    public String toString() {
      if (bOff) {
        return "# 보일러 : 꺼짐";
      } else {
        return "# 보일러 : 켜짐";
      }
    }
  }

  static class CoolAircon extends Participant {

    private boolean bOff = true;

    public CoolAircon(Mediator mediator) {
      super(mediator);
    }

    public void on() {
      if (!bOff) {
        return;
      }

      bOff = false;
      mediator.participatnChanged(this);
    }

    public void off() {
      if (bOff) {
        return;
      }

      bOff = true;
      mediator.participatnChanged(this);
    }

    public boolean isRunning() {
      return !bOff;
    }

    @Override
    public String toString() {
      if (bOff) {
        return "# 에어컨 : 꺼짐";
      } else {
        return "# 에어컨 : 켜짐";
      }
    }
  }


}
