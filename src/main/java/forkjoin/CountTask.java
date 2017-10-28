package forkjoin;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveTask;

/**
 * Created by 刘国兵 on 2017/10/17.
 */
public class CountTask extends RecursiveTask<Integer> {

    private static final int THRESHOLD = 2;
    private int start;
    private int end;

    public CountTask(int start, int end) {
        this.start = start;
        this.end = end;
    }

    @Override
    protected Integer compute() {
        System.out.println(Thread.currentThread());
        int sum = 0;
        if (end - start  <= THRESHOLD) {
            for (int i = start;i <= end; i++) {
                sum += i;
            }
            return sum;
        } else {
            // 拆分子任务
            int middle = (start + end) / 2;
            CountTask left = new CountTask(start,middle);
            CountTask right = new CountTask(middle + 1, end);
            // 子任务自行
            left.fork();
            right.fork();
            //子任务合并
            int leftResult = left.join();
            int rightResult = right.join();
            return leftResult + rightResult;
        }
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ForkJoinPool pool = new ForkJoinPool();
        CountTask task = new CountTask(1,100);
        Future<Integer> result = pool.submit(task);
        System.out.println(result.get());
    }
}
