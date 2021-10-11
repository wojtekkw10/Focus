package com.focus.backend.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class DataGridResponse<T> {
    private List<T> data;
    private long totalCount;

    public DataGridResponse(List<T> data) {
        this.data = data;
        this.totalCount = data.size();
    }


}
