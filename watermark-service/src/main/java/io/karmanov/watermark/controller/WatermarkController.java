package io.karmanov.watermark.controller;

import io.karmanov.watermark.dto.TicketDTO;
import io.karmanov.watermark.dto.WatermarkRequestDTO;
import io.karmanov.watermark.service.WatermarkService;
import io.karmanov.watermark.validation.WatermarkRequestValidator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

/**
 * Provides operations for watermarking documents and check the results
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/watermark-service")
@Api(description = "Provides operations for watermarking documents and check the results")
public class WatermarkController {

    private final WatermarkService watermarkService;

    private final WatermarkRequestValidator requestValidator;

    @InitBinder("watermarkRequestDTO")
    private void initBinder(WebDataBinder binder) {
        binder.setValidator(requestValidator);
    }

    @PostMapping(value = "/watermark", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation("Watermarking the document")
    public TicketDTO watermarkDocument(@Validated @RequestBody WatermarkRequestDTO request) {
        return watermarkService.watermark(request);
    }
}
