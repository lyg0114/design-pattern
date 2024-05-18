package com.designpatternstudy.proxy;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : iyeong-gyo
 * @package : com.designpatternstudy.proxy
 * @since : 18.05.24
 */
// - Proxy : 대리인
// - 어떤 작업의 실행을 대리인을 통해 실행하도록 하는 패턴
public class ProxyPattern {

  public static void main(String[] args) {
    Display display = new BufferDisplay(5);
    display.print("string print - 1");
    display.print("string print - 2");
    display.print("string print - 3");
    display.print("string print - 4");
    display.print("string print - 5");
    display.print("string print - 6");
    display.print("string print - 7");
  }

  interface Display {
    void print(String content);
  }

  // Proxy
  // 실제 ScreenDisplay 를 호출해서 print 하기전,
  // buffer에 데이터를 모으는 전처리를 하는 클래스.
  static class BufferDisplay implements Display {
    private final List<String> buffer = new ArrayList<>();
    private ScreenDisplay screen;
    int bufferSize;

    public BufferDisplay(int bufferSize) {
      this.bufferSize = bufferSize;
    }

    @Override
    public void print(String content) {
      buffer.add(content);
      if(buffer.size() == bufferSize){
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
      try {
        // content를 화면에 출력하기 위해 상당한 시간이 걸린다고 가정.
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      System.out.println("content = " + content);
    }
  }
}
