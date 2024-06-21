package com.designpatternstudy.behavioral.chainofresponsibility;

/**
 * @author : iyeong-gyo
 * @package : com.designpatternstudy.behavioral.chainofresponsibility
 * @since : 21.06.24
 * - 책임 : 무언가를 처리하는 기능, 구현은 클래스 단위로 이루어짐
 * - 책임을 연결 : 여러개의 책임을 동적으로 연결하여 순차적으로 실행
 * - 기능을 클래스별로 분리하여 기능의 독립성 보장, 최적화된 코드를 작성할 수 있다.
 */
public class ChainOfResponsibilityPattern {

	public static void main(String[] args) {
		Handler handler1 = new ProtocolHandler();
		Handler handler2 = new PortHandler();
		Handler handler3 = new DomainHandler();

		handler1
			.setNextHandler(handler2)
			.setNextHandler(handler3)
		;

		String url = "http://www.youtube.com:1007";
		System.out.println("INPUT : " + url);
		handler1.run(url);
	}

	static abstract class Handler {
		protected Handler nextHandler;

		public Handler setNextHandler(Handler handler) {
			this.nextHandler = handler;
			return handler;
		}

		protected abstract void process(String url);

		public void run(String url) {
			process(url);
			if (nextHandler != null) {
				nextHandler.run(url);
			}
		}
	}

	static class ProtocolHandler extends Handler {
		@Override
		protected void process(String url) {
			int index = url.indexOf("://");
			if (index != -1) {
				System.out.println("PROTOCOL: " + url.substring(0, index));
			} else {
				System.out.println("NO PROTOCOL: ");
			}
		}
	}

	static class DomainHandler extends Handler {
		@Override
		protected void process(String url) {
			int startIndex = url.indexOf("://");
			int lastIndex = url.lastIndexOf(":");

			System.out.print("DOMAIN: ");
			if (startIndex == -1) {
				if (lastIndex == -1) {
					System.out.println(url);
				} else {
					System.out.println(url.substring(0, lastIndex));
				}
			} else if (startIndex != lastIndex) {
				System.out.println(url.substring(startIndex + 3, lastIndex));
			} else if (startIndex == lastIndex) {
				System.out.println(url.substring(startIndex + 3));
			} else {
				System.out.println("NONE");
			}
		}
	}

	static class PortHandler extends Handler {
		@Override
		protected void process(String url) {
			int index = url.lastIndexOf(":");
			if (index != -1) {
				String strPort = url.substring(index + 1);
				try {
					int port = Integer.parseInt(strPort);
					System.out.println("PORT: " + port);
					return;
				} catch (NumberFormatException ex) {
					ex.printStackTrace();
				}
			}
			System.out.println("NO PORT");
		}
	}
}




