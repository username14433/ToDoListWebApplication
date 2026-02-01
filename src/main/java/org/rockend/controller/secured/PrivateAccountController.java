package org.rockend.controller.secured;

import org.rockend.entity.RecordStatus;
import org.rockend.entity.User;
import org.rockend.entity.dto.RecordsContainerDTO;
import org.rockend.service.RecordServiceImpl;
import org.rockend.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/account")
public class PrivateAccountController {
    private final UserServiceImpl userServiceImpl;
    private final RecordServiceImpl recordServiceImpl;


    @Autowired
    public PrivateAccountController(UserServiceImpl userServiceImpl, RecordServiceImpl recordServiceImpl) {
        this.userServiceImpl = userServiceImpl;
        this.recordServiceImpl = recordServiceImpl;
    }


    @GetMapping
    public String getMainPage(Model model, @RequestParam(name="filter",  required = false) String filterMode) {
        RecordsContainerDTO container = recordServiceImpl.findAllRecords(filterMode);

        model.addAttribute("userName", container.getUserName());
        model.addAttribute("numberOfDoneRecords", container.getNumberOfDoneRecords());
        model.addAttribute("numberOfActiveRecords", container.getNumberOfActiveRecords());
        model.addAttribute("records", container.getRecords());

        return "private/account-page";
    }

    @PostMapping("/add-record")
    public String addRecord(@RequestParam String title) {
        recordServiceImpl.saveRecord(title);
        return "redirect:/account";
    }

    @PostMapping("/make-record-done")
    public String makeRecordDone(@RequestParam int id,
                                 @RequestParam(name = "filter", required = false) String filterMode){
        recordServiceImpl.updateRecordStatus(id, RecordStatus.DONE);
        return "redirect:/account" + (filterMode != null && !filterMode.isBlank() ? "?filter=" + filterMode : "");
    }

    @PostMapping("/delete-record")
    public String deleteRecord(@RequestParam int id,
                               @RequestParam(name = "filter", required = false) String filterMode) {
        recordServiceImpl.deleteRecord(id);
        return "redirect:/account" + (filterMode != null && !filterMode.isBlank() ? "?filter=" + filterMode : "");
    }
}
