package com.designpatternstudy.behavioral.visitor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author : iyeong-gyo
 * @package : com.designpatternstudy.behavioral.visitor
 * @since : 22.06.24
 */
public class VisitorPattern {

	public static void main(String[] args) {
		ItemList list1 = new ItemList();
		list1.add(new Item(10));
		list1.add(new Item(20));
		list1.add(new Item(40));

		ItemList list2 = new ItemList();
		list2.add(new Item(30));
		list2.add(new Item(40));
		list1.add(list2);

		ItemList list3 = new ItemList();
		list3.add(new Item(25));
		list2.add(list3);

		Visitor sum = new SumVisitor();
		list1.accept(sum);
		System.out.println("합계 : " + sum.getValue());

		Visitor avg = new AvgVisitor();
		list1.accept(avg);
		System.out.println("평균 : " + avg.getValue());
	}

	interface Unit {
		void accept(Visitor visitor);
	}

	// Item 도 Unit으로 취급
	static class Item implements Unit {

		private int value;

		public Item(int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}

		@Override
		public void accept(Visitor visitor) {
			visitor.visit(this);
		}
	}

	// ItemList 도 Unit으로 취급
	static class ItemList implements Unit {
		private List<Unit> units = new ArrayList<>();

		public void add(Unit unit) {
			units.add(unit);
		}

		@Override
		public void accept(Visitor visitor) {
			Iterator<Unit> itr = units.iterator();
			while (itr.hasNext()) {
				Unit unit = itr.next();
				visitor.visit(unit);
			}
		}
	}

	interface Visitor {
		int getValue();
		void visit(Unit unit);
	}

	static class SumVisitor implements Visitor {
		int sum = 0;

		@Override
		public int getValue() {
			return sum;
		}

		@Override
		public void visit(Unit unit) {
			// Unit이 Item인 경우
			if (unit instanceof Item) {
				sum += ((Item)unit).getValue();
			} else {
				// Unit이 ItemList인 경우
				unit.accept(this);
			}
		}
	}

	static class AvgVisitor implements Visitor {
		int sum = 0;
		int count = 0;

		@Override
		public int getValue() {
			return sum / count;
		}

		@Override
		public void visit(Unit unit) {
			if (unit instanceof Item) {
				sum += ((Item)unit).getValue();
				count++;
			} else {
				unit.accept(this);
			}
		}
	}
}
