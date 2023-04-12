package My_Project.integration.AlgorithmTest;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class AlgorithmeTest {

    @Test
    public void Test1() {
        List<Integer> bestPostCommentsList = new ArrayList<>(3);

        int x = 9;

        bestPostCommentsList.add(0,3);
        bestPostCommentsList.add(1,6);
        bestPostCommentsList.add(2,9);

        if (x > bestPostCommentsList.get(2)) {
            int temp = bestPostCommentsList.get(1);

            bestPostCommentsList.set(1,bestPostCommentsList.get(2));
            bestPostCommentsList.set(0,temp);
            bestPostCommentsList.set(2,x);
        }

        else if (x > bestPostCommentsList.get(1) && x != bestPostCommentsList.get(2)) {
            bestPostCommentsList.set(0, bestPostCommentsList.get(1));
            bestPostCommentsList.set(1,x);
        }

        else if (x > bestPostCommentsList.get(0) && x != bestPostCommentsList.get(1) && x != bestPostCommentsList.get(2)) {
            bestPostCommentsList.set(0,x);
        }

        for(Integer integer : bestPostCommentsList) {
            System.out.println(integer);
        }
    }
}
