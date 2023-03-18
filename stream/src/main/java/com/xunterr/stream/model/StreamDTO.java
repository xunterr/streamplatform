package com.xunterr.stream.model;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


import java.util.UUID;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class StreamDTO {
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