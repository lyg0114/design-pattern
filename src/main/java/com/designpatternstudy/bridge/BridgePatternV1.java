package com.designpatternstudy.bridge;

import java.util.List;

/**
 * @author : iyeong-gyo
 * @package : com.designpatternstudy.bridge
 * @since : 24.05.24
 */
/*
 [브릿지 패턴]
 - 긱능 계층과 구현 계층의 분리로 시스템의 확장성과 유지보수성을 높이는 패턴
  - 신규요구사항이 요청되기 전의 코드
 */
public class BridgePatternV1 {

  public static void main(String[] args) {
    var title = "복원된 지구";
    var author = "홍길동";
    var content = List.of(
        "내용1내용1내용1내용1내용1",
        "내용2내용2내용2내용2내용2",
        "내용3내용3내용3내용3내용3"
    );
    Draft draft = new Draft(title, author, content);
    Display display = null;

    display = new SimpleDisplay();
    draft.print(display);

    System.out.println();
    System.out.println();
    System.out.println();

    display = new CaptionDisplay();
    draft.print(display);
  }

  static class Draft {

    private String title;
    private String author;
    private List<String> content;

    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public List<String> getContent() { return content; }

    public Draft(String title, String author, List<String> content) {
      this.title = title;
      this.author = author;
      this.content = content;
    }

    public void print(Display display) {
      display.title(this);
      display.author(this);
      display.content(this);
    }
  }

  interface Display {
    void title(Draft draft);
    void author(Draft draft);
    void content(Draft draft);
  }

  static class SimpleDisplay implements Display {
    @Override public void title(Draft draft) { System.out.println("draft.getTitle() = " + draft.getTitle()); }
    @Override public void author(Draft draft) { System.out.println("draft.getAuthor() = " + draft.getAuthor()); }
    @Override public void content(Draft draft) { System.out.println("draft.getContent() = " + draft.getContent()); }
  }

  static class CaptionDisplay implements Display {
    @Override public void title(Draft draft) { System.out.println("제목 : " + draft.getTitle()); }
    @Override public void author(Draft draft) { System.out.println("저자 : " + draft.getAuthor()); }
    @Override
    public void content(Draft draft) {
      System.out.println(" ======== 내용 ========");
      List<String> content = draft.getContent();
      for (String str : content) {
        System.out.println("      " + str);
      }
    }
  }
}
