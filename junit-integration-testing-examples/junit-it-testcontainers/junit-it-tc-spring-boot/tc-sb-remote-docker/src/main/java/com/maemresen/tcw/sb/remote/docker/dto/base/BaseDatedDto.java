package com.maemresen.tcw.sb.remote.docker.dto.base;


import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class BaseDatedDto extends BaseDto {
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
}
