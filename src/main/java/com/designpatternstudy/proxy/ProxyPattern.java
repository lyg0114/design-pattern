package com.designpatternstudy.proxy;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : iyeong-gyo
 * @package : com.designpatternstudy.proxy
 * @since : 18.05.24
 */
/*
 - Proxy : 대리인
 - 어떤 작업의 실행을 대리인을 통해 실행하도록 하는 패턴
 - 프록시 패턴은 객체에 대한 [접근 제어]가 주 목적
 -  프록시는 실제 객체(Real Subject)를 대리하여 클라이언트가 실제 객체에 직접 접근하지 못하게 하고,
    대신 프록시 객체를 통해 접근하도록 한다.
 */
public class ProxyPattern {

  public static void main(String[] args) {
    Display display = new BufferDisplay(5);
    display.print("string print - 1");
    display.print("string print - 2");
    display.print("string print - 3");
    display.print("string print - 4");
    display.print("string print - 5");
  }

  interface Display {
    void print(String content);
  }

  // Proxy
  // 실제 ScreenDisplay 를 호출해서 print 하기전,
  // buffer에 데이터를 모으는 전처리를 하는 클래스.
  static class BufferDisplay implements Display {

    private final List<String> buffer = new ArrayList<>();
    private ScreenDisplay screen; // Real Subject
    int maxBufferSize;

    public BufferDisplay(int maxBufferSize) {
      this.maxBufferSize = maxBufferSize;
    }

    @Override
    public void print(String content) {
      buffer.add(content);
      if(buffer.size() == maxBufferSize){
       flush();
      }
    }

    private void flush() {
      if(screen == null){
        screen = new ScreenDisplay();
      }
      String lines = String.join("\n", buffer);
      screen.print(lines);
      buffer.clear();
    }
  }

  static class ScreenDisplay implements Display {
    @Override
    public void print(String content) {
      // content를 화면에 출력하는것은 resource를 많이 사용하는 무거운 작업이라 가정
      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      System.out.println("content = " + content);
    }
  }
}
