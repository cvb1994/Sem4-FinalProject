package com.personal.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.personal.filter.JwtAuthenticationFilter;
import com.personal.serviceImp.UserManagerService;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
	@Autowired
    UserManagerService userService;
	
	@Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    }
	
	@Bean(BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        // Get AuthenticationManager bean
        return super.authenticationManagerBean();
    }
	
	@Bean
    public PasswordEncoder passwordEncoder() {
        // Password encoder, để Spring Security sử dụng mã hóa mật khẩu người dùng
        return new BCryptPasswordEncoder();
    }
	
	@Override
    protected void configure(AuthenticationManagerBuilder auth)
            throws Exception {
        auth.userDetailsService(userService) // Cung cáp userservice cho spring security
            .passwordEncoder(passwordEncoder()); // cung cấp password encoder
    }
	
	@Override
    protected void configure(HttpSecurity http) throws Exception {
    	http = http.cors().and().csrf().disable();
    	
        http.authorizeRequests()
            .antMatchers("/**").permitAll() // Cho phép tất cả mọi người truy cập vào địa chỉ này
//            .antMatchers("/test","/api/admin/login").permitAll() // Cho phép tất cả mọi người truy cập vào địa chỉ này
//            .antMatchers("/testlogin").hasAuthority("ROLE_ADMIN")
            .anyRequest().authenticated(); // Tất cả các request khác đều cần phải xác thực mới được truy cập

        http.authorizeRequests().and().logout();
        // Thêm một lớp Filter kiểm tra jwt
        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }
}
