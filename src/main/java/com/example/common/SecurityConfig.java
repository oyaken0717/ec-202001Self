package com.example.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * ログイン認証用設定.
 * 
 * @author kenji.oyamada
 *
 */
//■上の@:設定用のクラス
//■下の@:Spring Securityのウェブ用の機能を利用する
@Configuration
@EnableWebSecurity 
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsService memberDetailsService;

//■「セキュリティ設定」を無視する設定
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/css/**", 
									"/img/**", 
									"/js/**", 
									"/fonts/**", 
									"/img_noodle/**");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
//■ーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーー
		http.authorizeRequests()
			//■ログインフィルターの無視。
			//■①-1全部のパスがフィルター無視。(開発中に使える。)
			.antMatchers("/**").permitAll()
			//■①-2本来は v の書き方でフィルターを無視できるパスを制限する。
//			.antMatchers("/login-user/to-login","/register-user/toRegister").permitAll()
			//■② /user/から始まるパスはUSER権限でログインしている場合のみアクセス可(権限設定時の「ROLE_」を除いた文字列を指定)
			.antMatchers("/user/**").hasRole("USER")
			//■それ以外のパスは認証が必要
			.anyRequest().authenticated(); 

//■ーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーー
		http.formLogin() 
			//■ログインに関する設定(「ログイン認証メソッドだけ」をこっちに移動させたイメージ！！！！！！！！！！！！！！！！！！！！！)
			//■③ログイン画面に遷移させるパス
			// > ログインされていないとこのパスに遷移される。
			.loginPage("/to-login") 
			//■④「?error=true」がURLに表示 > メソッドでキャッチしてエラー文を出す。
			.failureUrl("/to-login/?error=true") 
			//■⑤ログインボタンを押した際に遷移させるパス(ここに遷移させれば自動的にログインが行われる！！！！！！！！！！！！！！！！！)
			.loginProcessingUrl("/login") 
			//■⑥ログイン失敗に遷移させるパス
			// > 第1引数:ログイン成功時に遷移させるパス
			// > 第2引数: true :認証後 > 第1引数のパスに遷移
			// > 第2引数: false:認証されてない > 一度ログイン画面に飛ばされる > ログインしたら「本来遷移するはずだったURL」に遷移
			.defaultSuccessUrl("/", false)
			//■⑦認証時に使用するリクエストパラメータ名(今回はメールアドレスを使用)
			.usernameParameter("email") 
			//■⑧認証時に使用するパスワードのリクエストパラメータ名
			.passwordParameter("password"); 

//■ーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーー
		http.logout()
			//■ログアウトさせる際に遷移させるパス
			.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
			//■ログアウト後に遷移させるパス(ここでは商品一覧画面を設定)
			.logoutSuccessUrl("/") 
			//■ログアウト後、Cookieに保存されているセッションIDを削除
			.deleteCookies("JSESSIONID") 
			//■ true:ログアウト後、セッションを無効にする 
			//> false:セッションを無効にしない
			.invalidateHttpSession(true);
	}

	//■bcryptアルゴリズムでパスワードをハッシュ化したオブジェクトを返します.
	//@Autowired
	//private PasswordEncoder passwordEncoder;
	//と記載する > DIされる。
    @Bean
    public PasswordEncoder passwordEncoder() {
    	return new BCryptPasswordEncoder();
    }
	 
	//■認証ユーザを取得する「UserDetailsService」の設定
	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(memberDetailsService).passwordEncoder(new BCryptPasswordEncoder());
	}
}