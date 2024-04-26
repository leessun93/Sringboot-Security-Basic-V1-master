package com.cos.securityex01.config.auth;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.cos.securityex01.model.User;

import lombok.Data;

//시큐리티가 login 주소 요청이 오면 낚아채서 로그인을 진행시킨다
//로그인을 진행을 완료하면 시큐리티가 session을 만들어준다.(Security ContextHolder)
//오브젝트 => Authentication 타입 객체
//Authentication 안에 User정보가 있어야함
//User오브젝트타입 =>UserDetail타입 객채

//Security Session영역의 -> Authentication -> UserDetail(PrincipalDetails) 타입 객체가 있어서 나중에 쓸 때도 이 타입으로 꺼내 쓴다


// Authentication 객체에 저장할 수 있는 유일한 타입
@Data
public class PrincipalDetails implements UserDetails{

	private User user;

	public PrincipalDetails(User user) {
		super();
		this.user = user;
	}
	
	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	//해당 유저의 권한을 return 하는곳
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> collet = new ArrayList<GrantedAuthority>();
		collet.add(()->{ return user.getRole();});
		return collet;
	}


	
}
