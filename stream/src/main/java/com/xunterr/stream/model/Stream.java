package com.xunterr.stream.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.Instant;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "stream")
public class Stream {
	@Id
	@SequenceGenerator(
			name = "stream_id_sequence",
			sequenceName = "stream_id_sequence",
			initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "stream_id_sequence")
	@Column(name = "id", nullable = false)
	private Long id;

	@Column(name = "stream_key", nullable = false, unique = true)
	private String streamKey;

	@Column(nullable = false)
	private Long uid;

	@CreatedDate
	private Instant createdDate;

	@LastModifiedDate
	private Instant lastModifiedDate;

	private String title;
	private String description;
}
