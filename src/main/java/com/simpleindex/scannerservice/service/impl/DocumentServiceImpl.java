package com.simpleindex.scannerservice.service.impl;

import com.simpleindex.scannerservice.dao.DocumentDao;
import com.simpleindex.scannerservice.model.Document;
import com.simpleindex.scannerservice.service.DocumentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service("documentsService")
public class DocumentServiceImpl implements DocumentService {
    private static final Logger LOG = LoggerFactory.getLogger(DocumentServiceImpl.class);
    @Autowired
    private DocumentDao documentDao;

    @Override
    public List<Document> getDocuments() {
        return documentDao.getDocuments();
    }

    @Override
    public List<Document> getDocumentsFromDate(String toDate, String fromDate) {
        List<Document> docListNew = new ArrayList<Document>();
        List<Document> docList = documentDao.getDocumentsFromDate(toDate, fromDate);
        int prev = 0;
        int next = 0;
        for (Document doc : docList) {
            if (next == 0 && prev == 0) {
                next = Integer.parseInt(doc.getInvoiceNumber());
                prev = next;
                docListNew.add(doc);
                continue;
            }
            next = Integer.parseInt(doc.getInvoiceNumber());
            if (next == prev) {
                docListNew.add(doc);
                prev = next;
                continue;
            }
            if (next != prev + 1) {
                LOG.info("Data not found for custId={}", prev);
                String msg = null;
                String from = Integer.toString(prev + 1);
                addMissingId(docListNew, prev, next, from);
                prev = next;
            } else {
                prev = next;
            }
            docListNew.add(doc);
        }
        LOG.info("old list size =" + docList.size() + " and new list size = " + docListNew.size());
       /* LOG.info("old list first val= " + docList.get(0) + "old list last val = " + docList.get(docList.size() - 1));
        LOG.info("new list first val= " + docListNew.get(0) + "new list last val = " + docListNew.get(docListNew.size() - 1));*/
        return docListNew;
    }

    private void addMissingId(List<Document> missingDocList, int prev, int next, String fromDate) {
        String msg;
        String missingMsg = " missing !!";
        Document docNotFound = null;
        if (next - prev == 2) {
            msg = fromDate + missingMsg;
            docNotFound = new Document();
            docNotFound.setMessage(msg);
            missingDocList.add(docNotFound);
        } else {
            String toDate = Integer.toString(next - 1);
            msg = fromDate + " to " + toDate + missingMsg;
            docNotFound = new Document();
            docNotFound.setMessage(msg);
            missingDocList.add(docNotFound);
        }
    }
}