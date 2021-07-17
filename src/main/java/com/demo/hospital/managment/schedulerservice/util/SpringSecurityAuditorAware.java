package com.demo.hospital.managment.schedulerservice.util;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;

public class SpringSecurityAuditorAware implements AuditorAware<String> {

	@Override
	public Optional<String> getCurrentAuditor() {

		
		return Optional.ofNullable("admin").filter(s -> !s.isEmpty());
	}

}
