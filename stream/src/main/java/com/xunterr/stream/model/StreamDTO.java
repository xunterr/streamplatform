package com.xunterr.stream.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class StreamDTO {
	@NotNull
	UUID id;

	@NotNull
	UUID userId;

	@NotBlank(message = "Title must not be null")
	String title;

	@NotBlank(message = "Description must not be null")
	String description;

	public StreamDTO(Stream stream) {
		this(stream.getId(), stream.getUserID(), stream.getTitle(), stream.getDescription());
	}
}