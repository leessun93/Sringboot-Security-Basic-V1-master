package com.cos.securityex01.controller;

import java.util.Collection;
import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cos.securityex01.config.auth.PrincipalDetails;
import com.cos.securityex01.model.User;
import com.cos.securityex01.repository.UserRepository;

@Controller
public class IndexController {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	//이선흠 config 에서 설정한 패스워드 암소화 오버라이딩 매서드 한 내용 불러옴 암호화 설정 파일인듯 join에서 씀

	@GetMapping({ "", "/" })
	public @ResponseBody String index() {
		return "인덱스 페이지입니다.";
	}

	@GetMapping("/user")
	public @ResponseBody String user(@AuthenticationPrincipal PrincipalDetails principal) {
		System.out.println("Principal : " + principal);
		// iterator 순차 출력 해보기
		Iterator<? extends GrantedAuthority> iter = principal.getAuthorities().iterator();
		while (iter.hasNext()) {
			GrantedAuthority auth = iter.next();
			System.out.println(auth.getAuthority());
		}

		return "유저 페이지입니다.";
	}

	@GetMapping("/admin")
	public @ResponseBody String admin() {
		return "어드민 페이지입니다.";
	}
	
	//@PostAuthorize("hasRole('ROLE_MANAGER')")
	//@PreAuthorize("hasRole('ROLE_MANAGER')")
	@Secured("ROLE_MANAGER")	//시큐어드 어노테이션을 달면 session에 해당 권한이 있는 사용자만 접근 가능
	//@PreAuthrize("hasRole('ROLE_MANAGER') or hasRole()") 이건 매서드 실행 전에 실행되는 어노테이션임 권한 여러개를 설정할 때 씀
	@GetMapping("/manager")
	public @ResponseBody String manager() {
		return "매니저 페이지입니다.";
	}

	@GetMapping("/login")
	public String login() {
		return "login";
	}

	@GetMapping("/join")
	public String join() {
		return "join";
	}

	@PostMapping("/joinProc")
	public String joinProc(User user) {
		System.out.println("회원가입 진행 : " + user);
		String rawPassword = user.getPassword(); //유저가 쓴 패스워드를 보존
		String encPassword = bCryptPasswordEncoder.encode(rawPassword);//유저의 패스워드를 처음에 선언한 클래스의 매서드함수로 암호화 작업
		user.setPassword(encPassword);	//암호화된 패스워드 Dto에 입력
		user.setRole("ROLE_USER");
		userRepository.save(user);
		return "redirect:/";
	}
}
