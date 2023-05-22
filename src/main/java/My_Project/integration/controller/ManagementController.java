package My_Project.integration.controller;

import My_Project.integration.entity.PointHistory;
import My_Project.integration.entity.PostInfo;
import My_Project.integration.entity.Report;
import My_Project.integration.entity.Users;
import My_Project.integration.service.PointService;
import My_Project.integration.service.PostService;
import My_Project.integration.service.ReportService;
import My_Project.integration.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class ManagementController {

    @Autowired
    PostService postService;

    @Autowired
    UserService userService;

    @Autowired
    PointService pointService;

    @Autowired
    ReportService reportService;

    @GetMapping("/management")
    public String management(
            @Qualifier("pageable1") @PageableDefault(size = 5) Pageable pageable1,
            @Qualifier("pageable2") @PageableDefault(size = 5) Pageable pageable2,
            @Qualifier("pageable3") @PageableDefault(size = 5) Pageable pageable3,
            @Qualifier("pageable4") @PageableDefault(size = 5) Pageable pageable4,

            @RequestParam(name = "pageable1.page", defaultValue = "0") int pageable1Page,
            @RequestParam(name = "pageable2.page", defaultValue = "0") int pageable2Page,
            @RequestParam(name = "pageable3.page", defaultValue = "0") int pageable3Page,
            @RequestParam(name = "pageable4.page", defaultValue = "0") int pageable4Page,

            Model model) {


//        List<PostInfo> allTodaysPost = postService.findAllTodaysPost();
//        List<PostInfo> allThisWeeksPost = postService.findAllThisWeeksPost();
//        List<PostInfo> allThisMonthPost = postService.findAllThisMonthPost();
//
//        Map<String, Integer> map = getStringIntegerMap(allTodaysPost);
//        Map<String, Integer> map2 = getStringIntegerMap(allThisWeeksPost);
//        Map<String, Integer> map3 = getStringIntegerMap(allThisMonthPost);
//
//        model.addAttribute("todaysPost", map);
//        model.addAttribute("thisWeeksPost",map2);
//        model.addAttribute("thisMonthPost",map3);
//        return "management";
//
        List<PostInfo> thisYearsPost = postService.findThisYearsPost();

        List<String> dTypes = thisYearsPost.stream()
                .map(PostInfo::getDtype) // 모든 PostInfo 객체의 dtype 필드를 추출
                .distinct() // 중복되지 않은 dtype 필드만 추출
                .collect(Collectors.toList()); // 리스트로 변환

        List<String> months = accumulatedBarGraphGetX(LocalDateTime.now(), 1);
        Map<String, List<Integer>> postInfoCountByMonth = getPostInfoCountByPeriod(thisYearsPost, months, dTypes);

        List<String> weeks = accumulatedBarGraphGetX(LocalDateTime.now(), 2);
        Map<String, List<Integer>> postInfoCountByWeeks = getPostInfoCountByPeriod(thisYearsPost, weeks, dTypes);

        List<String> days = accumulatedBarGraphGetX(LocalDateTime.now(), 3);
        Map<String, List<Integer>> postInfoCountByDays = getPostInfoCountByPeriod(thisYearsPost, days, dTypes);

        model.addAttribute("XAxisWeeks", weeks);
        model.addAttribute("YAxisMapWeeks", postInfoCountByWeeks);

        model.addAttribute("XAxisMonths", months);
        model.addAttribute("YAxisMapMonths", postInfoCountByMonth);

        model.addAttribute("XAxisDays", days);
        model.addAttribute("YAxisMapDays", postInfoCountByDays);

        //------------------------------------------------------ 누적막대그래프 메서드 ------------------------------------------------------

        if (pageable1Page > 0) {
            PageRequest of = PageRequest.of(pageable1Page, 5);
            Page<Users> allUsersWithPaging = userService.findAllUsersWithPaging(of);

        } else {
            Page<Users> allUsersWithPaging = userService.findAllUsersWithPaging(pageable1);
            model.addAttribute("allUsersWithPaging", allUsersWithPaging);
        }

        model.addAttribute("maxPage", 5);

        //------------------------------------------------------ 회원 목록 메서드 ------------------------------------------------------

        if (pageable2Page > 0) {
            PageRequest of = PageRequest.of(pageable2Page, 5);
            Page<PostInfo> postAllWithPaging = postService.findPostAllWithPaging(of);
            model.addAttribute("allPostWithPaging", postAllWithPaging);
        } else {
            Page<PostInfo> postAllWithPaging = postService.findPostAllWithPaging(pageable2);
            model.addAttribute("allPostWithPaging", postAllWithPaging);
        }
        //------------------------------------------------------ 게시글 목록 메서드 ------------------------------------------------------

        if (pageable3Page > 0) {
            PageRequest of = PageRequest.of(pageable3Page, 5);
            Page<PointHistory> allPointHistory = pointService.findAllPointHistoryWithPaging(of);
            model.addAttribute("allPointHistory", allPointHistory);
        } else {
            Page<PointHistory> allPointHistory = pointService.findAllPointHistoryWithPaging(pageable3);
            model.addAttribute("allPointHistory", allPointHistory);
        }

        //------------------------------------------------------ 포인트 사용 목록 메서드 ------------------------------------------------------
        if (pageable4Page > 0) {
            PageRequest of = PageRequest.of(pageable4Page, 5);
            Page<Report> allReportWithPaging = reportService.findAllWithPaging(of);
            model.addAttribute("allReportWithPaging", allReportWithPaging);
        } else {
            Page<Report> allReportWithPaging = reportService.findAllWithPaging(pageable4);
            model.addAttribute("allReportWithPaging", allReportWithPaging);
        }


        //------------------------------------------------------ 신고 목록 메서드 ------------------------------------------------------


        return "management";
    }

    private static List<String> getDays(LocalDateTime now) {
        List<String> thisSevenDays = IntStream.rangeClosed(-3, 3)
                .mapToObj(now::plusDays) // 현재 날짜에 일 수를 더하여 각 날짜 객체를 만듭니다.
                .map(date -> DateTimeFormatter.ofPattern("MM월 dd일").format(date)) // 날짜 객체를 MM월 dd일 형식의 문자열로 변환합니다.
                .collect(Collectors.toList());
        return thisSevenDays;
    }

    private static List<String> accumulatedBarGraphGetX(LocalDateTime now, int s) {
        switch (s) {
            case 1: // 1월부터 12월까지
                return IntStream.rangeClosed(1, 12)
                        .mapToObj(month -> DateTimeFormatter.ofPattern("MM월").format(now.withMonth(month)))
                        .collect(Collectors.toList());
            case 2: // 저 저번주부터 이번주까지
                return
                        IntStream.rangeClosed(-2, 0)
                                .mapToObj(week -> {
                                            if (weeksOverTheYear(now, week)) {
                                                String endDate = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일").format(now.plusWeeks(week));
                                                String startDate = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일").format(now.plusWeeks(week).minusDays(6));
                                                return startDate + "~" + endDate;
                                            } else {
                                                return "-";
                                            }
                                        }
                                )
                                .collect(Collectors.toList());

            case 3: // 오늘부터 7일간
                return
                        IntStream.rangeClosed(-7, 0)
                                .mapToObj(
                                        day -> {
                                            if (daysOverTheYear(now, day)) {
                                                return DateTimeFormatter.ofPattern("yyyy년 MM월 dd일").format(now.plusDays(day));
                                            } else {
                                                return "-";
                                            }
                                        })
                                .collect(Collectors.toList());
            default:
                throw new IllegalArgumentException("Invalid argument for s: " + s);
        }
    }

    private static boolean daysOverTheYear(LocalDateTime now, int day) {
        return now.plusDays(day).getYear() != now.getYear() ? false : true;
    }

    private static boolean weeksOverTheYear(LocalDateTime now, int week) {
        if (week < 0) {
            if (
                    now.minusDays(1).getYear() != now.getYear() ||
                            now.minusDays(2).getYear() != now.getYear() ||
                            now.minusDays(3).getYear() != now.getYear() ||
                            now.minusDays(4).getYear() != now.getYear() ||
                            now.minusDays(5).getYear() != now.getYear() ||
                            now.minusDays(6).getYear() != now.getYear()
            ) return false;
            else return true;
        } else {
            if (
                    now.plusDays(1).getYear() != now.getYear() ||
                            now.plusDays(2).getYear() != now.getYear() ||
                            now.plusDays(3).getYear() != now.getYear() ||
                            now.plusDays(4).getYear() != now.getYear() ||
                            now.plusDays(5).getYear() != now.getYear() ||
                            now.plusDays(6).getYear() != now.getYear()
            ) return false;
            else return true;
        }
    }

    private static Map<String, Integer> getStringIntegerMap(List<PostInfo> allTodaysPost) {
        Set<String> collect = allTodaysPost.stream()
                .map(postInfo -> postInfo.getDtype())
                .collect(Collectors.toSet());

        // 조회된 데이터 allTodaysPost의 Dtype의 종류의 개수를 구하기 위해 Set<>으로 중복제거
        Map<String, Integer> map = new HashMap<>();

        for (String s : collect) {
            map.put(s, (int) allTodaysPost.stream().filter(
                    postInfo -> postInfo.getDtype().equals(s)
            ).count());
        }
        // s : 전체 조회한 엔티티에서 dtype이 s와 같은 유형의 개수
        return map;
    }

    List<Map<String, Integer>> transform(List<PostInfo> A) {
        List<Map<String, Integer>> result = new ArrayList<>();

        // dtype을 기준으로 그룹핑하여 Map<String, Integer>으로 만들어 List에 추가합니다.
        Map<String, Integer> dtypeCountMap = A.stream()
                .collect(Collectors.groupingBy(PostInfo::getDtype, Collectors.summingInt(e -> 1)));
        result.add(dtypeCountMap);

        // uploadedTime을 기준으로 그룹핑하여 월별로 그룹핑합니다.
        Map<Integer, List<PostInfo>> monthGroupedMap = A.stream()
                .collect(Collectors.groupingBy(e -> e.getDates().getUploadedTime().getMonthValue()));

        // 각 월별로 dtype을 기준으로 그룹핑하여 Map<String, Integer>으로 만들어 List에 추가합니다.
        for (Map.Entry<Integer, List<PostInfo>> entry : monthGroupedMap.entrySet()) {
            int month = entry.getKey();
            List<PostInfo> monthGroup = entry.getValue();

            Map<String, Integer> dtypeCountMapForMonth = monthGroup.stream()
                    .collect(Collectors.groupingBy(PostInfo::getDtype, Collectors.summingInt(e -> 1)));
            dtypeCountMapForMonth.put("month", month); // 결과 Map에 월 정보를 추가합니다.
            result.add(dtypeCountMapForMonth);
        }

        return result;
    }

    public Map<String, List<Integer>> getPostInfoCountByPeriod(List<PostInfo> thisYearsPost, List<String> xAxis, List<String> dTypes) {
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM월 dd일");
        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH:mm:ss.SSS");
        Map<String, List<Integer>> yAxis = new HashMap<>();
        String s;
        for (String dType : dTypes) {
            List<Integer> list = new ArrayList<>();

            for (String period : xAxis) {
                LocalDateTime start, end;

                if (period.contains("~")) { // 기간이 일주일인 경우
                    String[] dates = period.split("~");
                    start = LocalDateTime.parse(dates[0] + " 00:00:00.000", formatter2);
                    end = LocalDateTime.parse(dates[1] + " 23:59:59.999", formatter2);
                } else {
                    try {
                        s = timeDifferenceMethod(xAxis);
                    } catch (Exception e) {
                        e.printStackTrace();
                        return null;
                    }

                    if (s == "days") { // 기간이 하루인 경우
                        start = LocalDateTime.parse(period + " 00:00:00.000", formatter2);
                        end = LocalDateTime.parse(period + " 23:59:59.999", formatter2);
                    } else {
                        // 기간이 한 달인 경우
                        String month = period.replace("월", "");
                        int year = LocalDateTime.now().getYear();
                        int monthValue = Integer.parseInt(month);
                        int maxDayOfMonth = Month.of(monthValue).maxLength();

                        // 2월의 경우, 해당 년도가 윤년인지 확인하고 예외 처리합니다.
                        if (monthValue == Month.FEBRUARY.getValue() && !Year.of(year).isLeap()) {
                            end = LocalDateTime.of(year, monthValue, 28, 23, 59, 59);
                        } else {
                            end = LocalDateTime.of(year, monthValue, maxDayOfMonth, 23, 59, 59);
                        }
                        start = LocalDateTime.of(year, monthValue, 1, 0, 0);
                    }
                }


                int count = (int) thisYearsPost.stream()
                        .filter(post -> post.getDates().getUploadedTime().isAfter(start)
                                && post.getDates().getUploadedTime().isBefore(end)
                                && post.getDtype().equals(dType))
                        .count();

                list.add(count);
            }
            yAxis.put(dType, list);
        }
        return yAxis;
    }

    private String timeDifferenceMethod(List<String> xAxis) throws Exception {
        if (xAxis.size() <= 1) {
            throw new Exception("timeDifferenceMethod의 인수가 빈 리스트입니다.");
        }

        if (xAxis.get(0).contains("일")) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH:mm:ss.SSS");
            LocalDateTime idx0 = LocalDateTime.parse(xAxis.get(0) + " 00:00:00.000", formatter);
            LocalDateTime idx1 = LocalDateTime.parse(xAxis.get(1) + " 00:00:00.000", formatter);

            Duration duration = Duration.between(idx0, idx1);
            Period period = Period.between(idx0.toLocalDate(), idx1.toLocalDate());

            if (duration.toDays() == 1) {
                return "days";
            }

            if (duration.toDays() == 7) {
                return "weeks";
            }

            throw new Exception("날짜 간격 오류");

        } else {
            return "months";
        }
    }

    @ResponseBody
    @PostMapping("deletePostList")
    public ResponseEntity<Long> deletePostList(@RequestBody List<Long> postNumberList) {
        long l = postService.deletePostList(postNumberList);
        return new ResponseEntity<Long>(l, HttpStatus.OK);
    }

    @ResponseBody
    @PostMapping("deleteUsersList")
    public ResponseEntity<Long> deleteUserList(@RequestBody List<String> userEmailList) {
        long l = userService.deleteUserList(userEmailList);
        return new ResponseEntity<Long>(l, HttpStatus.OK);
    }
}
