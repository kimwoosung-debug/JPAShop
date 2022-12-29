package jpabook.jpashop.Controller.Form;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
public class MemberForm {
    @NotEmpty(message = "회원 이름은 필수 입니다.")

    private String name;
    private String city;
    private String street;
    @Size(min = 4, max = 5)
    private String zipcode;
}
