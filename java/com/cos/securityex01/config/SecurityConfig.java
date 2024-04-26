package com.cos.securityex01.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration // IoC 빈(bean)을 등록
@EnableWebSecurity // 필터 체인 관리 시작 어노테이션
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true) // 특정 주소 접근시 권한 및 인증을 위한 어노테이션 활성화
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	// 해당 메서드의 리턴이 되는 ioc 매서드를 리턴을해준다
	@Bean	//
	public BCryptPasswordEncoder encodePwd() {
		return new BCryptPasswordEncoder();
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.csrf().disable();
		http.authorizeRequests()
			.antMatchers("/user/**").authenticated()//권한설정
			//.antMatchers("/admin/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
			//.antMatchers("/admin/**").access("hasRole('ROLE_ADMIN') and hasRole('ROLE_USER')") //admin이면
			.antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")	//어드민권한설정 //이건 문자열이고 db에 이렇게 저장되어있다.
			.anyRequest().permitAll() //나머지는 누구나 들어갈 수 있다.
		.and()
			.formLogin()	//여기서부터는 manager admin user 쳐도 로긴패이지로 가도록 설정하는거
			.loginPage("/login")
			.loginProcessingUrl("/loginProc") //login 주소 가 호출이되면 시큐리티가 낚아채서 로그인을 대신 진행해줌
			.defaultSuccessUrl("/");//로그인이 완료되면 메인페이지로 이동하게함
	}
}





