package com.beerhouse.application.config.handlers.error;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseErrorVO {
  private String message;
  private String path;
}
