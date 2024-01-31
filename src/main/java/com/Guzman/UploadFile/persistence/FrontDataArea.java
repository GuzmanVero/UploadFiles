package com.Guzman.UploadFile.persistence;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FrontDataArea {
    private String area;
    private Integer capacity;
    private LocalTime hour;

}
