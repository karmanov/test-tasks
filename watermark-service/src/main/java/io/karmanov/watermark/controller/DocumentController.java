package io.karmanov.watermark.controller;

import io.karmanov.watermark.dto.DocumentCreateRequestDTO;
import io.karmanov.watermark.dto.DocumentDTO;
import io.karmanov.watermark.dto.PageDTO;
import io.karmanov.watermark.service.DocumentService;
import io.karmanov.watermark.validation.DocumentCreateValidator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * Provides CRUD operations for documents(books and journals)
 */
@RestController
@RequestMapping(value = "/watermark-service")
@RequiredArgsConstructor
@Api(description = "Provides CRUD operations for documents(books and journals)")
public class DocumentController {

    private final DocumentService documentService;

    private final DocumentCreateValidator documentValidator;

    @InitBinder("documentCreateRequestDTO")
    private void initBinder(WebDataBinder binder) {
        binder.setValidator(documentValidator);
    }

    @GetMapping(value = "/documents", produces = APPLICATION_JSON_VALUE)
    @ApiOperation("Finds all documents")
    List<DocumentDTO> getDocuments() {
        return documentService.getDocuments();
    }

    @GetMapping(value = "/documents", params = {"page", "size"}, produces = APPLICATION_JSON_VALUE)
    @ApiOperation("Finds all documents paginated")
    PageDTO<DocumentDTO> getDocumentsPageable(Pageable pageable) {
        return documentService.getDocumentsPageable(pageable);
    }

    @PostMapping(value = "/documents", consumes = APPLICATION_JSON_VALUE,
            produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation("Creates new document")
    public DocumentDTO create(@Validated @RequestBody DocumentCreateRequestDTO requestDTO) {
        return documentService.create(requestDTO);
    }

    @PutMapping(value = "/documents/{document_id}", produces = APPLICATION_JSON_VALUE,
            consumes = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    @ApiOperation("Updates existing entity with given value")
    public DocumentDTO update(@PathVariable("document_id") Long documentId, @Validated @RequestBody DocumentCreateRequestDTO updateDTO) {
        return documentService.update(documentId, updateDTO);
    }

    @GetMapping(value = "/documents/{document_id}", produces = APPLICATION_JSON_VALUE)
    @ApiOperation("Finds document by given id")
    public DocumentDTO getDocumentById(@PathVariable("document_id") Long documentId) {
        return documentService.getDocumentById(documentId);
    }

    @DeleteMapping(value = "/documents/{document_id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation("Deletes document by id")
    public void deleteDocument(@PathVariable("document_id") Long documentId) {
        documentService.deleteDocument(documentId);
    }
}
