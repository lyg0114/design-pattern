package com.designpatternstudy.behavioral.template;

import java.util.List;

/**
 * @author : iyeong-gyo
 * @package : com.designpatternstudy.template
 * @since : 21.05.24
 */
/*
 [템플릿패턴]
 - 알고리즘의 구조를 템플릿으로 만들고 서브클래스가 특정 단계를 재정의할 수 있도록 하는 패턴
 - 알고리즘의 뼈대는 그대로 유지하면서, 세부적인 구현은 서브클래스에 위임
    - ex) 트랜잭션 처리
 - 이 패턴은 상속을 통해 구현, 코드의 재사용성과 확장성을 높여줍니다.
 */
public class TemplatePattern {

	public static void main(String[] args) {
		String title = "디자인 패턴";
		List<String> content = List.of(
			"컨텐츠 내용 1",
			"컨텐츠 내용 2",
			"컨텐츠 내용 3",
			"컨텐츠 내용 4",
			"컨텐츠 내용 4"
		);
		String footer = "디자인 패턴 footer";

		Article article = new Article(title, content, footer);
		DisplayArticleTemplate simpleTemplate = new SimpleDisplayArticle(article);
		System.out.println("[CASE-1]");
		simpleTemplate.display();

		System.out.println();

		DisplayArticleTemplate captionTemplate = new CaptionDisplayArticle(article);
		System.out.println("[CASE-2]");
		captionTemplate.display();
	}

	static class Article {
		private String title;
		private List<String> content;
		private String footer;

		public Article(String title, List<String> content, String footer) {
			this.title = title;
			this.content = content;
			this.footer = footer;
		}

		public String getTitle() {
			return title;
		}

		public List<String> getContent() {
			return content;
		}

		public String getFooter() {
			return footer;
		}
	}

	static abstract class DisplayArticleTemplate {
		protected Article article;

		public DisplayArticleTemplate(Article article) {
			this.article = article;
		}

		// 템플릿으로 처리할 부분
		public final void display() {
			System.out.println("##############");   // 공통적으로 사용되는 코드
			title();                                // 하위 클래스에서 구체적인 구현 처리
			content();                              // 하위 클래스에서 구체적인 구현 처리
			footer();                               // 하위 클래스에서 구체적인 구현 처리
			System.out.println("##############");   // 공통적으로 사용되는 코드
		}

		protected abstract void title();

		protected abstract void content();

		protected abstract void footer();
	}

	static class SimpleDisplayArticle extends DisplayArticleTemplate {

		public SimpleDisplayArticle(Article article) {
			super(article);
		}

		@Override
		protected void title() {
			System.out.println("article.getTitle() = " + article.getTitle());
		}

		@Override
		protected void content() {
			List<String> content = article.getContent();
			for (String s : content) {
				System.out.println("s = " + s);
			}
		}

		@Override
		protected void footer() {
			System.out.println("article.getFooter() = " + article.getFooter());
		}
	}

	static class CaptionDisplayArticle extends DisplayArticleTemplate {

		public CaptionDisplayArticle(Article article) {
			super(article);
		}

		@Override
		protected void title() {
			System.out.println("TITLE");
			System.out.println("article.getTitle() = " + article.getTitle());
		}

		@Override
		protected void content() {
			System.out.println("CONTENT");
			List<String> content = article.getContent();
			for (String s : content) {
				System.out.println("s = " + s);
			}
		}

		@Override
		protected void footer() {
			System.out.println("FOOTER");
			System.out.println("article.getFooter() = " + article.getFooter());
		}
	}
}
