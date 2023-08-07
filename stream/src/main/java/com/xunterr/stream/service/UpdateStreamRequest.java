package com.xunterr.stream.service;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateStreamRequest {
	@NotBlank(message = "Title must not be null")
	public String title;

	@NotBlank(message = "Description must not be null")
	public String description;
}
