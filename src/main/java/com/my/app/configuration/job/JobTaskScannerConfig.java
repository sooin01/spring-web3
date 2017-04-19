package com.my.app.configuration.job;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.AnnotationBeanNameGenerator;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.filter.AnnotationTypeFilter;

import com.my.app.api.common.annotation.Task;

public class JobTaskScannerConfig implements ImportBeanDefinitionRegistrar {

	@Override
	public void registerBeanDefinitions(AnnotationMetadata metadata, BeanDefinitionRegistry registry) {
		// Don't pull default filters (@Component, etc.):
		ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(false);
		provider.addIncludeFilter(new AnnotationTypeFilter(Task.class));

		for (BeanDefinition beanDef : provider.findCandidateComponents("com.my.app")) {
			printMetadata(beanDef);

			String beanName = new AnnotationBeanNameGenerator().generateBeanName(beanDef, registry);
			registry.registerBeanDefinition(beanName, beanDef);
		}
	}

	private void printMetadata(BeanDefinition beanDef) {
		try {
			Class<?> cl = Class.forName(beanDef.getBeanClassName());
			Task task = cl.getAnnotation(Task.class);
			System.out.printf("Found class: %s, with meta name: %s%n", cl.getSimpleName(), task.name());
		} catch (Exception e) {
			System.err.println("Got exception: " + e.getMessage());
		}
	}

}
