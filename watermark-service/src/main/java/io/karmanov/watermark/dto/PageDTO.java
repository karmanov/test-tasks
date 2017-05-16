package io.karmanov.watermark.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * Represents paged response
 *
 * @param <T> type of the collection
 */
@Data
@ApiModel(description = "Represents paged response")
public class PageDTO<T> {

    @ApiModelProperty("Collection of the elements")
    private List<T> entries;

    @ApiModelProperty("Total number of elements")
    private long totalElements;

    @ApiModelProperty("Total number of pages")
    private int totalPages;

}
