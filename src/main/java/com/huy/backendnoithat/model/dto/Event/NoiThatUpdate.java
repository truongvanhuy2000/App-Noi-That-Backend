package com.huy.backendnoithat.model.dto.Event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NoiThatUpdate {
    private String dbName;
    private String username;
}
