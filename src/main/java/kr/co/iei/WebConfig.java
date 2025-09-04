package kr.co.iei;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import kr.co.iei.util.AdminInterceptor;
import kr.co.iei.util.LoginInterceptor;
import kr.co.iei.util.ReviewInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer {
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		// 썸머노트 이미지 전용 → C:/Temp/upload/image/ 경로에서 파일 찾음
		registry.addResourceHandler("/editorImage/**").addResourceLocations("file:///C:/Temp/upload/image/");

		// 공지사항 파일 전용
		registry.addResourceHandler("/file/**").addResourceLocations("file:///C:Temp/upload/image/notice/");

		// 기존 static 이미지
		registry.addResourceHandler("/image/**").addResourceLocations("classpath:/static/image/");
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new LoginInterceptor())
				.addPathPatterns(
						"/member/logout",
						"/member/myPage",
						"/member/updateInfo",
						"/member/deleteInfo",
						"/admission/**",
						"/adoption/**",
						"/notice/**",
						"/review/reviewWriteFrm",
						"/review/reviewUpdateFrm",
						"/review/delete",
						"/review/likepush",
						"/admin/**"
						).excludePathPatterns(
								"/admission/list",
								"/admission/searchTitle",
								"/adoption/list",
								"/adoption/searchTitle",
								"/notice/list",
								"/notice/searchTitle",
								"/notice/view",
								"/notice/filedown",
								"/adoption/view"
								);
		registry.addInterceptor(new AdminInterceptor())
		.addPathPatterns(
					"/admin/**",
					"/notice/insertFrm",
					"/notice/delete",
					"/notice/updateFrm"
				);
		registry.addInterceptor(new ReviewInterceptor())
		.addPathPatterns(
				"/review/reviewWriteFrm",
				"/review/reviewUpdateFrm",
				"/review/delete"
				);
			
		
		
	}
	
}
