package com.simpleindex.scannerservice.controller;

import com.simpleindex.scannerservice.model.Document;
import com.simpleindex.scannerservice.model.DocumentBean;
import com.simpleindex.scannerservice.service.DocumentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller()
public class SimpleIndexController {
    private static final Logger LOG = LoggerFactory.getLogger(SimpleIndexController.class);

    @Autowired
    private DocumentService documentService;

    @GetMapping("/")
    public String showForm(Model model) {
        LocalDate date = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String startDate = date.format(formatter);
        String endDate = date.plusDays(1).format(formatter);
        List<Document> documents = documentService.getDocumentsFromDate(startDate, endDate);
        model.addAttribute("gridVisiblity",documents.size()==0?false:true);
        model.addAttribute("documents", documents);
        return "document";

    }

    @PostMapping("/report")
    public String submitForm(Model model, @ModelAttribute("user") DocumentBean document) {
        System.out.println("document == " + document.toString());
        LOG.info("Retrieving all the records from Documents for toDate{}=, fromDate{}=", document.getToDate(), document.getFromDate());
        model.addAttribute("documents", documentService.getDocumentsFromDate(document.getToDate(), document.getFromDate()));
        return "document";
    }

    @GetMapping(value = "/filter")
    public String filterGrid(Model model, @RequestParam String startDate, @RequestParam String endDate){
        LOG.info("Startdate is "+startDate+" EndDate is "+ endDate);
        List<Document> documents = documentService.getDocumentsFromDate(startDate, endDate);
        boolean val = documents.size()==0?false:true;
        LOG.info("alue is" + val);
        model.addAttribute("gridVisiblity",val);
        model.addAttribute("documents", documents);
        return "document :: table";
    }
   /* @GetMapping("/document/to/{toDate}/from/{fromDate}")
    public String document(Model model, @PathVariable("toDate") final String toDate, @PathVariable("fromDate") final String fromDate) {
        LOG.info("Retrieving all the records from Documents for toDate{}=, fromDate{}=", toDate, fromDate);
        model.addAttribute("documents", documentService.getDocumentsFromDate(toDate, fromDate));
        return "document";
    }*/

   /* @GetMapping("/document")
    public ModelAndView document(Model model) {
        LOG.info("Retrieving all the records from Documents for ");
        model.addAttribute("documents", documentService.getDocuments());
        return new ModelAndView("document");
    }*/
}
