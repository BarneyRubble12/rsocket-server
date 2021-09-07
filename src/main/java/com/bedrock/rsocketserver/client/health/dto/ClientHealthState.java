package com.bedrock.rsocketserver.client.health.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientHealthState {
  private boolean healthy;
}
