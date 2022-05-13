package com.ssaffron.business.api.controller;

import com.ssaffron.business.api.dto.ApplyDto;
import com.ssaffron.business.api.dto.LaundryPlanDto;
import com.ssaffron.business.api.dto.RequestApplyDto;
import com.ssaffron.business.api.dto.MonthPlanDto;
import com.ssaffron.business.api.exception.*;
import com.ssaffron.business.api.service.MemberService;
import com.ssaffron.business.api.service.PlanService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/v1/api/plan")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Slf4j
public class PlanController {

    private final PlanService planService;
    private final MemberService memberService;

    // tt Laundry Plan 요금제 전체 조회
    @GetMapping("/laundry/list")
    public ResponseEntity<List<LaundryPlanDto>> allListLaundry() {
        List<LaundryPlanDto> laundryPlanDtos = planService.findAllLaundryPlan();

        return new ResponseEntity<>(laundryPlanDtos, HttpStatus.OK);
    }

    // tt Laundry Plan 요금제 단일 조회
    @GetMapping("/laundry/{laundry-plan-Id}")
    public ResponseEntity<LaundryPlanDto> getListLaundry(@PathVariable("laundry-plan-Id") int laundryplanId) {
        LaundryPlanDto laundryPlanDto = planService.findOneLaundryPlan(laundryplanId);

        return new ResponseEntity<>(laundryPlanDto, HttpStatus.OK);
    }

    // tt 요금제 신청 header로 불러오는 법 ->
    @PostMapping("")
    public ResponseEntity createApplyFor(@RequestBody RequestApplyDto requestApplyDto) throws DuplicatedApplyException{
        String memberEmail = memberService.decodeJWT();
        int monthPlanId = requestApplyDto.getMonthPlanId();
        planService.insertApply(monthPlanId, memberEmail);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    // tt 요금제 수정
    @PutMapping("")
    public ResponseEntity updateApplyFor(@RequestBody RequestApplyDto requestApplyDto) throws NotFoundApplyException{
        String memberEmail = memberService.decodeJWT();
        int monthPlanId = requestApplyDto.getMonthPlanId();
        planService.updateApply(monthPlanId, memberEmail);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    // tt 요금제 삭제
    @DeleteMapping("")
    public ResponseEntity deleteApplyFor() throws DeleteApplyException{
        String memberEmail = memberService.decodeJWT();
        planService.deleteApply(memberEmail);

        return new ResponseEntity<>(HttpStatus.OK);

    }

    // dd 한달 요금제 리스트 조회
    @GetMapping("/month/list")
    public ResponseEntity<List<MonthPlanDto>> allListMonth() {
        List<MonthPlanDto> monthlist = planService.getMonthPlanList();

        return new ResponseEntity<>(monthlist, HttpStatus.OK);
    }
    // dd 한달요금제 1개조회
    @GetMapping("/month/{month_plan_Id}")
    public ResponseEntity<MonthPlanDto> getListMonth(@PathVariable int month_plan_Id) {
        MonthPlanDto monthone = planService.getMonthPlan(month_plan_Id);

        return new ResponseEntity<>(monthone, HttpStatus.OK);
    }

    //dd 사용중인 요금제 list 조회
    @GetMapping("/admin/using-plan")
    public ResponseEntity<List<ApplyDto>> allListApplyFor() {
        List<ApplyDto> applylist = planService.getApplyList();

        return new ResponseEntity<>(applylist, HttpStatus.OK);
    }

    //dd 사용중인 요금제 조회
    @GetMapping("")
    public ResponseEntity<ApplyDto> getApplyFor() throws NotFoundApplyException{
        String memberEmail = memberService.decodeJWT();

        ApplyDto applyone = planService.getApply(memberEmail);

        return new ResponseEntity(applyone, HttpStatus.OK);

    }

    // dd Test : 다음달 요금제 변경------------ 쓸거임?
    @GetMapping("/change/{memberId}")
    public ResponseEntity nextMonthPlan(@PathVariable("memberId") int memberId, @RequestBody MonthPlanDto dto) { // 수정

        return new ResponseEntity(HttpStatus.OK);
    }

}
