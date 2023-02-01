package com.xunterr.stream.repository;

import com.xunterr.stream.model.Stream;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface StreamRepository extends JpaRepository<Stream, UUID>{}