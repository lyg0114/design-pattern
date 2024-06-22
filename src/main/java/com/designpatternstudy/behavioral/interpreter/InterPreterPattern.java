package com.designpatternstudy.behavioral.interpreter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

/**
 * @author : iyeong-gyo
 * @package : com.designpatternstudy.behavioral.interpreter
 * @since : 22.06.24
 */
public class InterPreterPattern {

	public static void main(String[] args) {
		String script = "BEGIN FRONT LOOP 2 BACK RIGHT END BACK END";
		// String script = "BEGIN FRONT LOOP 3 LOOP 2 RIGHT FRONT END LOOP 3 LEFT END BACK RIGHT END BACK END";
		Context context = new Context(script);
		Expression expression = new BeginExpression();

		if (expression.parse(context)) {
			System.out.println(expression);
			expression.run();
		}

		// while (true) {
		// 	String keyword = context.getCurrentKeyword();
		// 	if (keyword == null) {
		// 		break;
		// 	}
		// 	System.out.println(keyword);
		// 	context.readNextKeyword();
		// }
	}

	static class Context {
		private StringTokenizer tokenizer;
		private String currentKeyword;

		public Context(String script) {
			tokenizer = new StringTokenizer(script);
			readNextKeyword();
		}

		private String readNextKeyword() {
			if (tokenizer.hasMoreTokens()) {
				currentKeyword = tokenizer.nextToken();
			} else {
				currentKeyword = null;
			}
			return currentKeyword;
		}

		public String getCurrentKeyword() {
			return currentKeyword;
		}
	}

	interface Expression {
		boolean parse(Context context);

		boolean run();
	}

	static class BeginExpression implements Expression {
		private CommandListExptession exptession;

		@Override
		public boolean parse(Context context) {
			if (checkValidKeyword(context.getCurrentKeyword())) {
				context.readNextKeyword();
				exptession = new CommandListExptession();
				return exptession.parse(context);
			} else {
				return false;
			}
		}

		private boolean checkValidKeyword(String keyowrd) {
			return keyowrd.equals("BEGIN");
		}

		@Override
		public boolean run() {
			return exptession.run();
		}

		@Override
		public String toString() {
			return "BEGIN " + exptession;
		}
	}

	static class CommandListExptession implements Expression {
		private List<CommandExptession> commands = new ArrayList<>();

		@Override
		public boolean parse(Context context) {
			while (true) {
				String currentKeyword = context.getCurrentKeyword();
				if (currentKeyword == null) {
					return false;
				} else if (currentKeyword.equals("END")) {
					context.readNextKeyword();
					break;
				} else {
					CommandExptession command = null;
					if (LoopCommandExpression.checkValidKeyword(currentKeyword)) {
					} else if (ActionCommandExpression.checkValidKeyword(currentKeyword)) {
						command = new ActionCommandExpression(currentKeyword);
					}

					if (command != null) {
						if (command.parse(context)) {
							commands.add(command);
						} else {
							return false;
						}
					} else {
						return false;
					}
				}
			}
			return true;
		}

		@Override
		public boolean run() {
			Iterator<CommandExptession> itr = commands.iterator();
			while (itr.hasNext()) {
				boolean bOk = itr.next().run();
				if (!bOk) {
					return false;
				}
			}
			return true;
		}

		@Override
		public String toString() {
			return commands.toString();
		}
	}

	static abstract class CommandExptession implements Expression {
		protected String keyword;

		public CommandExptession(String keyword) {
			this.keyword = keyword;
		}
	}

	static class ActionCommandExpression extends CommandExptession {
		public ActionCommandExpression(String keyword) {
			super(keyword);
		}

		@Override
		public boolean parse(Context context) {
			if (!checkValidKeyword(keyword)) {
				return false;
			}
			if (context.readNextKeyword() == null) {
				return false;
			}

			return true;
		}

		private static boolean checkValidKeyword(String keyword) {
			return keyword.equals("FRONT") ||
				keyword.equals("BACK") ||
				keyword.equals("LEFT")
				|| keyword.equals("RIGHT");
		}

		@Override
		public boolean run() {
			System.out.println("cmd: " + keyword);
			return true;
		}

		@Override
		public String toString() {
			return keyword;
		}
	}

	static class LoopCommandExpression extends CommandExptession {

		private int count;
		private CommandListExptession exptession;

		public LoopCommandExpression(String keyword) {
			super(keyword);
		}

		@Override
		public boolean parse(Context context) {
			if (!checkValidKeyword(keyword)) {
				return false;
			}

			String countKeyword = context.readNextKeyword();
			if (countKeyword == null) {
				return false;
			}
			try {
				count = Integer.parseInt(countKeyword);
				CommandListExptession exptession = new CommandListExptession();
				if (context.readNextKeyword() == null) {
					return false;
				}

				return exptession.parse(context);
			} catch (NumberFormatException ex) {
				return false;
			}
		}

		private static boolean checkValidKeyword(String keyword) {
			return keyword.equals("LOOP");
		}

		@Override
		public boolean run() {
			for (int i = 0; i < count; i++) {
				if (!exptession.run()) {
					return false;
				}
			}
			return true;
		}

		@Override
		public String toString() {
			return "LOOP (" + count + ") " + exptession;
		}
	}
}



















