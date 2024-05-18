package com.designpatternstudy.strategy;

/**
 * @author : iyeong-gyo
 * @package : com.designpatternstudy.strategy
 * @since : 18.05.24
 */

//  - 어떤 하나의 기능을 구성하는 특정 부분을 실행중 다른것으로 효과적으로 변경하는 전략
//  - 인터페이스를 활용해 유연한 구조 생성 - 필요할 경우 전략을 변경할 수 있다.
public class StrategyPattern {

  public static void main(String[] args) {
    SumPrinter sumPrinter = new SumPrinter();
    sumPrinter.print(new SimpleSumStrategy(), 3);
    System.out.println("####################################");
    sumPrinter.print(new GaussSumStrategy(), 3);
  }

  static class SumPrinter {
    public void print(SumStrategy strategy, int N) {
      System.out.println(strategy.get(N));
    }
  }

  interface SumStrategy {
    int get(int n);
  }

  static class SimpleSumStrategy implements SumStrategy {
    @Override
    public int get(int n) {
      int sum = n;
      for (int i = 0; i < n; i++) {
        sum += i;
      }
      return sum;
    }
  }

  static class GaussSumStrategy implements SumStrategy {
    @Override
    public int get(int n) {
      return ((n + 1) * n) / 2;
    }
  }
}
