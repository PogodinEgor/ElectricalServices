package pogodin_egor.CRUD_TP.controllers;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pogodin_egor.CRUD_TP.models.Person;
import pogodin_egor.CRUD_TP.services.PersonService;

@Controller
@RequestMapping("/admin")
@AllArgsConstructor
public class AdminController {
    @Autowired
    private PersonService personService;

    @GetMapping
    public String index(Model model) {
        model.addAttribute("people", personService.findAll());
        return "admin/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") long id, Model model) {
        model.addAttribute("person", personService.findOne(id));
        return "admin/show";
    }


    @GetMapping("/new")
    public String newAdmin(@ModelAttribute("person")Person person) {
        return "admin/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult) {


        if (bindingResult.hasErrors()) {
            return "admin/new";
        }

        personService.save(person);
        return "redirect:/admin";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") long id, Model model) {
        model.addAttribute("person", personService.findOne(id));
        return "admin/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult,
                         @PathVariable("id") long id) {
        if (bindingResult.hasErrors()) {
            return "admin/edit";
        }

        personService.update(id, person);
        return "redirect:/admin";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") long id) {
        personService.delete(id);
        return "redirect:/admin";
    }

}
