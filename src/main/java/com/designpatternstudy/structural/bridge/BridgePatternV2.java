package com.designpatternstudy.structural.bridge;

import java.util.List;

/**
 * @author : iyeong-gyo
 * @package : com.designpatternstudy.bridge
 * @since : 24.05.24
 */
/*
 [브릿지 패턴]
 - 긱능 계층과 구현 계층의 분리로 시스템의 확장성과 유지보수성을 높이는 패턴
    - 기존 클래스의 어떠한 변경도 없이 새로운 기능을 추가할 수 있다.
 */
public class BridgePatternV2 {

	/*
	 - 신규 요구사항 :
		  - Draft가 출판될 경우 출판사와 가격정보를 함께 출력해 줘야 한다.
		  - 단 기존의 Draft 기능은 모두 유지
	 */
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

		System.out.println();
		System.out.println();
		System.out.println();

		var publisher = "한국출판";
		var coast = 100;
		draft = new Publication(title, author, content, publisher, coast);
		draft.print(display);
	}

	/*
	  - [구현 계층]
		-  하나의 데이터(Draft)를 다양한 형태로 표현
	 */
	interface Display {
		void title(Draft draft);

		void author(Draft draft);

		void content(Draft draft);
	}

	/*
	  - [기능 계층]
		- 새로운 요구사항이 발생했을때 기존의 작성된 코드를 변경하지 않고도 새로운 기능을 추가할 수 있다.
	 */
	static class Draft {

		private String title;
		private String author;
		private List<String> content;

		public Draft(String title, String author, List<String> content) {
			this.title = title;
			this.author = author;
			this.content = content;
		}

		public String getTitle() {
			return title;
		}

		public String getAuthor() {
			return author;
		}

		public List<String> getContent() {
			return content;
		}

		public void print(Display display) {
			display.title(this);
			display.author(this);
			display.content(this);
		}
	}

	/*
	  - 고객의 요구사항으로 신규로 추가된 클래스
	 */
	static class Publication extends Draft {
		private String publisher;
		private int coast;

		public Publication(
			String title, String author, List<String> content, String publisher, int coast
		) {
			super(title, author, content);
			this.publisher = publisher;
			this.coast = coast;
		}

		@Override
		public void print(Display display) {
			super.print(display);
			printPublicationInf();
		}

		private void printPublicationInf() {
			System.out.println("#" + publisher + " $" + coast);
		}
	}

	static class SimpleDisplay implements Display {
		@Override
		public void title(Draft draft) {
			System.out.println("draft.getTitle() = " + draft.getTitle());
		}

		@Override
		public void author(Draft draft) {
			System.out.println("draft.getAuthor() = " + draft.getAuthor());
		}

		@Override
		public void content(Draft draft) {
			System.out.println("draft.getContent() = " + draft.getContent());
		}
	}

	static class CaptionDisplay implements Display {
		@Override
		public void title(Draft draft) {
			System.out.println("제목 : " + draft.getTitle());
		}

		@Override
		public void author(Draft draft) {
			System.out.println("저자 : " + draft.getAuthor());
		}

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
