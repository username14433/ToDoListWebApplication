package org.rockend.to_do_list_web_application.controller;

import org.rockend.to_do_list_web_application.entity.Record;
import org.rockend.to_do_list_web_application.entity.RecordStatus;
import org.rockend.to_do_list_web_application.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class CommonController {
    private final RecordService recordService;

    @Autowired
    public CommonController(RecordService recordService) {
        this.recordService = recordService;
    }

    @GetMapping("/")
    public String redirectTOHomePage() {
        return "redirect:/home";
    }

    @GetMapping("/home")
    public String getMainPage(Model model){
        List<Record> records = recordService.findAllRecords();
        int numberOfDoneRecords = (int) records.stream().
                filter(record -> record.getStatus() == RecordStatus.DONE).count();
        int numberOfActiveRecords = (int) records.stream().
                filter(record -> record.getStatus() == RecordStatus.ACTIVE).count();
        recordService.findAllRecords();


        model.addAttribute("numberOfDoneRecords", numberOfDoneRecords);
        model.addAttribute("numberOfActiveRecords", numberOfActiveRecords);
        model.addAttribute("records", records);
        return "main-page";
    }

    @PostMapping("/add-record")
    public String addRecord(@RequestParam String title) {
        recordService.saveRecord(title);
        return "redirect:/home";
    }

    @PostMapping("/make-record-done")
    public String makeRecordDone(@RequestParam int id){
        recordService.updateRecordStatus(id, RecordStatus.DONE);
        return "redirect:/home";
    }

    @PostMapping("/delete-record")
    public String deleteRecord(@RequestParam int id) {
        recordService.deleteRecord(id);
        return "redirect:/home";
    }
}
