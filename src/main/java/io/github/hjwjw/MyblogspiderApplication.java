package io.github.hjwjw;

import io.github.hjwjw.entity.Article;
import io.github.hjwjw.service.IArticleService;
import io.github.hjwjw.service.ILoginService;
import io.github.hjwjw.service.IMdService;
import io.github.hjwjw.utils.LoginUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@SpringBootApplication
public class MyblogspiderApplication implements CommandLineRunner{

	@Resource
	private ILoginService loginService;
	@Resource
	private IArticleService articleService;
	@Resource
	private IMdService mdService;
	@Value("${csdn.login.url}")
	private String loginUrl;
	@Value("${csdn.login.username}")
	private String username;
	@Value("${csdn.login.password}")
	private String password;
	@Value("${csdn.article.postlist.url}")
	private String postListUrl;
	@Value("${csdn.article.articleUrl}")
	private String articleUrl;
	@Value("${saveUrl}")
	private String saveUrl;

	public static void main(String[] args) {
		SpringApplication.run(MyblogspiderApplication.class, args);
	}

	@Override
	public void run(String... strings) throws Exception {
		String postCookie;
		Boolean success = false;
		Map<String, Object> map = loginService.getLoginParam(loginUrl);
		Map<String,Object> paramMap = LoginUtil.getLoginParamMap(map);
		paramMap.put("username",username);
		paramMap.put("password",password);

		try {
			//暂停5秒，避免登陆频繁而失败
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		postCookie = loginService.LoginMyblog(map.get("action").toString(),paramMap);
		if (postCookie!=null && !"".equals(postCookie)){
			Map<String, String> postMap = articleService.getPostList(postListUrl,postCookie);
			List<Article> articleList = articleService.toArticle(articleUrl, postCookie, postMap);
			success = mdService.writerMd(saveUrl,articleList);
		}
		System.out.println("程序执行状态：" + success);
	}
}
