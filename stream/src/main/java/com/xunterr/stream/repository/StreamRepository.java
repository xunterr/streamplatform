package com.xunterr.stream.repository;

import com.xunterr.stream.model.Stream;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StreamRepository extends JpaRepository<Stream, Long>{
	Optional<Stream> findByStreamKey(String streamKey);
}
