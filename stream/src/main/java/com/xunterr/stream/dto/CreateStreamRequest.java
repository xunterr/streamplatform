package com.xunterr.stream.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CreateStreamRequest {
	@NotNull
	UUID userID;

	@NotBlank(message = "Title must not be null")
	String title;

	@NotBlank(message = "Description must not be null")
	String description;

	@NotBlank
	boolean autoDelete;
}
