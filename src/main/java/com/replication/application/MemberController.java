package com.replication.application;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
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
    private final MemberRepository memberRepository;
    private final CacheRepository cacheRepository;

    @GetMapping("/members")
    public String getMembers(Model model, HttpServletRequest request) {
        List<Member> findMembers = memberService.findAll();
        Member findMember = new Member("test", 0, 0);

        Cookie[] cookies = request.getCookies();
        Cookie cookie = cookies[0];
        String name = cookie.getAttribute("name");

        if (name != null) {
            findMember = cacheRepository.findMemberAtCache(name);
        }

        HttpSession session = request.getSession();
        session.setAttribute("name", findMember.getName());

        model.addAttribute("members", findMember);

        return "member";
    }

    @GetMapping("/insert")
    public String insert(HttpSession session, Model model) {
        String name = (String) session.getAttribute("name");

        model.addAttribute("name", name);

        return "insert_member";
    }

    @PostMapping("/insert")
    public String insertMember(@ModelAttribute("member") MemberDto dto) {
        Member member = new Member(dto.getName(), dto.getAge(), dto.getBalance());
        memberService.save(member);

        return "redirect:/members";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/login")
    public String loginPost(@ModelAttribute("login") LoginDto dto, HttpSession session, HttpServletResponse response, Model model) {
        Member findMember = memberRepository.findByName(dto.getName());
        session.setAttribute("member", findMember.getName());
        response.addCookie(new Cookie("name", findMember.getName()));
        model.addAttribute("members", findMember);

        return "redirect:/members";
    }
}
