package com.xunterr.stream.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class StreamRequest{
	@NotNull
	Long uid;

	@NotBlank(message = "Title must not be null")
	String title;

	@NotBlank(message = "Description must not be null")
	String description;

	@NotBlank(message = "Stream key must not be null")
	String streamKey;
}
