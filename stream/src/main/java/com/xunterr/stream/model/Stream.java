package com.xunterr.stream.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "stream")
public class Stream {
	@Id
	@GeneratedValue
	private UUID id;

	@Column(nullable = false, unique = true)
	private UUID userID;

	@CreatedDate
	private Instant createdDate;

	@LastModifiedDate
	private Instant lastModifiedDate;

	private String title;
	private String description;
}