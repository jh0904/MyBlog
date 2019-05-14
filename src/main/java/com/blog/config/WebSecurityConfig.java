package com.blog.config;

import com.blog.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

/**
 * @author: i_jianghao
 * @Date: 2018/6/5 18:45
 * Describe: SpringSecurity配置
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	//@Bean
	//UserDetailsService customUserService() {
	//	return new CustomUserServiceImpl ();
	//}

	@Autowired
	private DataSource dataSource;

	@Autowired
	private UserDetailsService userDetailsService;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService (userDetailsService)
				//启动MD5加密
				.passwordEncoder (new PasswordEncoder () {
					MD5Util md5Util = new MD5Util ();

					@Override
					public String encode(CharSequence rawPassword) {
						return md5Util.encode ((String) rawPassword);
					}

					@Override
					public boolean matches(CharSequence rawPassword, String encodedPassword) {
						return encodedPassword.equals (md5Util.encode ((String) rawPassword));
					}
				});
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
				.authorizeRequests ()
				.antMatchers ("/", "/index", "/aboutme", "/archives", "/categories", "/friendlylink", "/tags", "/update")
				.permitAll ()
				.antMatchers ("/editor", "/user").hasAnyRole ("USER")
				.antMatchers ("/ali").hasAnyRole ("ADMIN")
				.antMatchers ("/superadmin").hasAnyRole ("SUPERADMIN")
				.and ()
				.formLogin ().usernameParameter ("username").passwordParameter ("password").loginPage ("/login").failureUrl ("/login?error").defaultSuccessUrl ("/")
				.and ()
				.logout ().logoutUrl ("/logout").logoutSuccessUrl ("/").invalidateHttpSession (true)
				.and ()
				.rememberMe ().rememberMeParameter ("remember-me")
				.tokenRepository (persistentTokenRepository ())  // 配置数据库源
				.tokenValiditySeconds (60 * 60 * 3)
				.userDetailsService (userDetailsService)
				.rememberMeCookieName ("magic_jh")
				.and ().csrf ().disable ();

	}

	@Bean
	public PersistentTokenRepository persistentTokenRepository() {
		JdbcTokenRepositoryImpl persistentTokenRepository = new JdbcTokenRepositoryImpl ();
		// 将 DataSource 设置到 PersistentTokenRepository
		persistentTokenRepository.setDataSource (dataSource);
		// 第一次启动的时候自动建表（可以不用这句话，自己手动建表，源码中有语句的）
		// persistentTokenRepository.setCreateTableOnStartup(true);
		return persistentTokenRepository;
	}
}
