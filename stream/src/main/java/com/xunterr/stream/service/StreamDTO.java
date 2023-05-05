package com.xunterr.stream.service;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class StreamDTO implements Serializable {
	UUID id;

	@NotNull
	UUID userId;

	@NotBlank(message = "Title must not be null")
	String title;

	@NotBlank(message = "Description must not be null")
	String description;

	@NotNull
	boolean autoDelete;
}
