package io.karmanov.watermark.service;

import io.karmanov.watermark.converter.ConverterService;
import io.karmanov.watermark.domain.Document;
import io.karmanov.watermark.dto.DocumentCreateRequestDTO;
import io.karmanov.watermark.dto.DocumentDTO;
import io.karmanov.watermark.dto.PageDTO;
import io.karmanov.watermark.exception.DocumentNotFoundException;
import io.karmanov.watermark.repository.DocumentRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * Provides interface for all operation under {@link Document} entity.
 * Every interaction with {@link Document} entity should be done via this class
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DocumentService {

    private final DocumentRepository documentRepository;

    private final ConverterService converterService;

    public List<DocumentDTO> getDocuments() {
        log.debug("Fetching all documents");
        return documentRepository.findAll().stream()
                                 .map(this::convert)
                                 .collect(toList());
    }

    public PageDTO<DocumentDTO> getDocumentsPageable(Pageable pageable) {
        log.debug("Fetching documents by pages with pageSize {}, page number {} and sort {}", pageable.getPageSize(),
                pageable.getPageNumber(), pageable.getSort());
        Page<Document> documentsPage = documentRepository.findAll(pageable);
        List<DocumentDTO> documents = documentsPage.getContent().stream()
                                                   .map(this::convert)
                                                   .collect(toList());
        return convert(documentsPage, documents);
    }

    public DocumentDTO create(DocumentCreateRequestDTO requestDTO) {
        log.info("Creating document from request [{}]", requestDTO);
        Document document = convert(requestDTO);
        return convert(documentRepository.saveAndFlush(document));
    }

    public DocumentDTO update(Long documentId, DocumentCreateRequestDTO source) {
        log.info("Updating document with id {} with values {}", documentId, source);
        Document destination = findById(documentId);
        BeanUtils.copyProperties(source, destination);
        return convert(documentRepository.saveAndFlush(destination));
    }

    public DocumentDTO update(final DocumentDTO source) {
        log.info("Updating document with id {} with values {}", source.getId(), source);
        Document target = findById(source.getId());
        BeanUtils.copyProperties(source, target);
        return convert(documentRepository.saveAndFlush(target));
    }

    public DocumentDTO getDocumentById(Long documentId) {
        return convert(findById(documentId));
    }

    public void deleteDocument(Long documentId) {
        log.info("Deleting document by id {}", documentId);
        Document document = findById(documentId);
        documentRepository.delete(document);
    }

    private Document findById(Long documentId) {
        log.debug("Fetching document by id: {}", documentId);
        return documentRepository.findById(documentId)
                                 .orElseThrow(() -> new DocumentNotFoundException(documentId.toString()));
    }

    private DocumentDTO convert(Document document) {
        return converterService.convert(document, DocumentDTO.class);
    }

    private Document convert(DocumentCreateRequestDTO requestDTO) {
        return converterService.convert(requestDTO, Document.class);
    }

    private PageDTO<DocumentDTO> convert(Page<Document> page, List<DocumentDTO> documents) {
        PageDTO pageDTO = converterService.convert(page, PageDTO.class);
        pageDTO.setEntries(documents);
        return pageDTO;
    }
}
