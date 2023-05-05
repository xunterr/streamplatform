package com.xunterr.stream.model;

import com.xunterr.stream.key.StreamKeyGenerator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Data
@NoArgsConstructor
@Entity
@Table(name = "stream")
public class Stream {
	@Id
	@GeneratedValue
	private UUID id;

	@Column(nullable = false)
	private UUID userID;

	@CreatedDate
	private Instant createdDate;

	@LastModifiedDate
	private Instant lastModifiedDate;

	private String title;
	private String description;
	private String streamKey;

	private boolean isLive;
	private boolean autoDelete;

	public Stream(UUID userID, String title, String description, boolean autoDelete, StreamKeyGenerator keyGenerator) {
		this.userID = userID;
		this.title = title;
		this.description = description;
		this.isLive = false;
		this.autoDelete = autoDelete;
		this.streamKey = keyGenerator.generateKey();
	}
}