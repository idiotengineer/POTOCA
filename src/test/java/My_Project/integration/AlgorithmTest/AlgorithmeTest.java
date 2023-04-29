package My_Project.integration.AlgorithmTest;

import net.bytebuddy.asm.Advice;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.sql.Time;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AlgorithmeTest {

    @Test
    public void Test1() {
        List<Integer> bestPostCommentsList = new ArrayList<>(3);

        int x = 9;

        bestPostCommentsList.add(0,1);
        bestPostCommentsList.add(1,2);
        bestPostCommentsList.add(2,3);

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

    @Test
    public void Test2() {
        LocalDate currentDate = LocalDate.now();
        LocalDateTime currentDateTime = LocalDateTime.now();

        Assertions.assertThat(currentDateTime.toLocalDate()).isEqualTo(currentDate);
    }

    @Test
    public void Test3() {
        LocalDateTime currentTime = LocalDateTime.now();
        LocalDateTime after24Hours = currentTime.plusSeconds(260);

        System.out.println(currentTime);
        System.out.println(after24Hours);

        Duration duration = Duration.between(currentTime ,after24Hours);
        Duration.between(currentTime,currentTime);

        long year = duration.getSeconds() / 31556926;
        long month = duration.getSeconds() / 2629800;
        long day = duration.getSeconds() / 86400;
        long hour = duration.getSeconds() / 3600;
        long minute = duration.getSeconds() / 60;
        long second = duration.getSeconds();

        if (year > 0) { //
            System.out.println("약 " + year + "년 후 종료!");
        } else if (month > 0) {
            System.out.println("약 " + month + "달 후 종료!");
        } else if (day > 0) {
            System.out.println("약 " + day + "일 후 종료!");
        } else if (hour > 0) {
            System.out.println("약 " + hour + "시간 후 종료!");
        } else if (minute > 0) {
            System.out.println(minute + "분 후 종료!");
        } else if (second > 1){
            System.out.println(second + "초 후 종료!");
        } else {
            System.out.println("마감된 게시글입니다");
        }
    }
}
