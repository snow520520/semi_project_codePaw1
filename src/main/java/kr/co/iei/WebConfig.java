package kr.co.iei;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		// 썸머노트 이미지 전용 → C:/Temp/upload/image/ 경로에서 파일 찾음
		registry.addResourceHandler("/editorImage/**").addResourceLocations("file:///C:/Temp/upload/image/");

		// 기존 static 이미지
		registry.addResourceHandler("/image/**").addResourceLocations("classpath:/static/image/");
	}
}
