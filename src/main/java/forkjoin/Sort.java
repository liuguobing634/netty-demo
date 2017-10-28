package forkjoin;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

/**
 * Created by 刘国兵 on 2017/10/17.
 */
public class Sort extends RecursiveTask<List<Integer>> {

    private List<Integer> initList;

    public Sort(List<Integer> initList) {
        this.initList = initList;
    }

    @Override
    protected List<Integer> compute() {
        int n = initList.size();
        if (n < 2) {
            return initList;
        } else {
            Sort left = new Sort(initList.subList(0,n / 2));
            Sort right = new Sort(initList.subList(n / 2, n));
            left.fork();
            right.fork();
            List<Integer> leftJoin = left.join();
            List<Integer> rightJoin = right.join();
            List<Integer> result = new ArrayList<>();
            int i = 0;
            int j = 0;
            while (i < leftJoin.size() && j < rightJoin.size()) {
                Integer leftI = leftJoin.get(i);
                Integer rightI = rightJoin.get(j);
                if (leftI < rightI) {
                    result.add(leftI);
                    i ++;
                } else {
                    result.add(rightI);
                    j++;
                }
            }
            if (i < leftJoin.size()) {
                result.addAll(leftJoin.subList(i, leftJoin.size()));
            }
            if (j < rightJoin.size()) {
                result.addAll(rightJoin.subList(j,rightJoin.size()));
            }
            return result;
        }
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ForkJoinPool pool = new ForkJoinPool();
        List<Integer> numbers = new ArrayList<>();
        for (int i = 0;i < 100;i++) {
            numbers.add((int)(Math.random() * 100));
        }
        System.out.println(numbers);
        System.out.println("------------------------");
        Sort s= new Sort(numbers);
        List<Integer> integers = pool.submit(s).get();
        System.out.println(integers);
        System.out.println("------------------------");
        System.out.println(numbers);
        System.out.println("------------------------");
        Collections.sort(numbers);
        System.out.println(numbers);
    }

}
