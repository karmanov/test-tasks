package io.karmanov.watermark.dto;

import io.karmanov.watermark.domain.DocumentTopic;
import io.karmanov.watermark.domain.DocumentType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Document create request payload
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "Document create request payload")
public class DocumentCreateRequestDTO {

    @ApiModelProperty("Document's title")
    private String title;

    @ApiModelProperty("Document's author")
    private String author;

    @ApiModelProperty("Document's watermark")
    private String watermark;

    @ApiModelProperty("Document's topic. Null for documents with type 2(JOURNAL). Possible values: " +
            "1 = BUSINESS, 2 = SCIENCE")
    private DocumentTopic topic;

    @ApiModelProperty("Document's type. Possible values: 1 = BOOK, 2 = JOURNAL")
    private DocumentType type;
}
