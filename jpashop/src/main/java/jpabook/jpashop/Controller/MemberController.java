package jpabook.jpashop.Controller;

import jpabook.jpashop.Controller.Form.MemberForm;
import jpabook.jpashop.Domain.Address;
import jpabook.jpashop.Domain.Member;
import jpabook.jpashop.Service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/members/new")
    public String createForm(Model model) {
        model.addAttribute("memberForm", new MemberForm());

        return "Members/createMemberForm";
    }
    @PostMapping("/members/new")
    public String join(@Valid MemberForm form, BindingResult result) {

        if(result.hasErrors()) {
            return "Members/createMemberForm";
        }

        Member member = new Member();
        member.setName(form.getName());
        member.setAddress(new Address(
                form.getCity(),
                form.getStreet(),
                form.getZipcode()));

        memberService.join(member);
        return "redirect:/";
    }

    @GetMapping("/members")
    public String memberList(Model model) {
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);
        return "Members/memberList";
    }

}
