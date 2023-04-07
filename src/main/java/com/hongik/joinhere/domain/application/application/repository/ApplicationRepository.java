package com.hongik.joinhere.domain.application.application.repository;

import com.hongik.joinhere.domain.application.application.entity.Application;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationRepository extends JpaRepository<Application, Long> {
}
