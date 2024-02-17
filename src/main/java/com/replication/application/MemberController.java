package com.replication.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/members")
    public String getMembers(Model model) {
        List<Member> findMembers = memberService.findAll();

        model.addAttribute("members", findMembers);

        return "member";
    }

    @GetMapping("/insert")
    public String insert() {
        return "insert_member";
    }

    @PostMapping("/insert")
    public String insertMember(@ModelAttribute("member") MemberDto dto) {
        Member member = new Member(dto.getName(), dto.getAge(), dto.getBalance());
        memberService.save(member);

        return "redirect:/members";
    }
}
