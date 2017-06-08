package com.challenge.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PageAnalysisReportDTO {

    private String url;

    private String htmlVersion;

    private String title;

    private Map<String, Long> headingsMap;

    private Map<Integer, List<LinkResourceDTO>> linksMap;

    private boolean containsLoginForm;

    private int headingCount;

    private int externalLinksCount;

    private int internalLinksCount;
}
