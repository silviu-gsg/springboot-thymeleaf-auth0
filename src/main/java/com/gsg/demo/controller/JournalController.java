package com.gsg.demo.controller;

import com.gsg.demo.dto.JournalEntryDto;
import com.gsg.demo.service.JournalEntryService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@AllArgsConstructor
@Controller
@RequestMapping("/journal")
public class JournalController {

    private final JournalEntryService journalEntryService;

    @GetMapping
    public String showJournal(Model model, @AuthenticationPrincipal OidcUser oidcUser) {
        model.addAttribute("profile", oidcUser.getClaims());

        List<JournalEntryDto> journalEntries = journalEntryService.findEntriesForUserId(oidcUser.getSubject());
        model.addAttribute("journalEntries", journalEntries);
        return "journal";
    }

    @GetMapping("/entry/create")
    public String showJournalEntryCreateForm(JournalEntryDto journalEntry, Model model) {
        model.addAttribute("journalEntry", journalEntry);
        return "add-journal-entry";
    }

    @PostMapping("/entry/create")
    public String createJournalEntry(@Valid JournalEntryDto journalEntry, BindingResult result, Model model,
                                     @AuthenticationPrincipal OidcUser oidcUser) {
        if (result.hasErrors()) {
            return "add-journal-entry";
        }

        journalEntry.setUserId(oidcUser.getSubject());
        journalEntryService.create(journalEntry);
        return "redirect:/journal";
    }

    @GetMapping("/entry/update/{id}")
    public String showJournalEntryUpdateForm(@PathVariable("id") Long id, Model model) {
        JournalEntryDto journalEntry = journalEntryService.findById(id);
        model.addAttribute("journalEntry", journalEntry);
        return "update-journal-entry";
    }

    @PostMapping("/entry/update/{id}")
    public String updateJournalEntry(@PathVariable("id") Long id, @Valid JournalEntryDto journalEntry, BindingResult result,
                                     Model model, @AuthenticationPrincipal OidcUser oidcUser) {
        if (result.hasErrors()) {
            return "add-journal-entry";
        }

        journalEntryService.update(id, journalEntry);
        return "redirect:/journal";
    }

    @PostMapping("/entry/delete/{id}")
    public String deleteJournalEntry(@PathVariable("id") Long id, Model model) {
        journalEntryService.delete(id);
        return "redirect:/journal";
    }

}
