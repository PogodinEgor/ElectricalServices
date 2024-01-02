package pogodin_egor.CRUD_TP.controllers;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pogodin_egor.CRUD_TP.models.TransformatorSubstation;
import pogodin_egor.CRUD_TP.models.TypeCounter;
import pogodin_egor.CRUD_TP.services.TypeCounterService;
import pogodin_egor.CRUD_TP.validator.TypeCounterValidator;


@Controller
@RequestMapping("/type")
@AllArgsConstructor
public class TypeCounterController {
    @Autowired
    private TypeCounterService typeCounterService;

    @Autowired
    private TypeCounterValidator typeCounterValidator;


    @GetMapping
    public String index(Model model,
                        @RequestParam(value = "page", defaultValue = "0") int page,
                        @RequestParam(value = "size", defaultValue = "20") int size) {
        Page<TypeCounter> substationsPage = typeCounterService.findAll(page, size);
        model.addAttribute("typeCounters", substationsPage);
        return "type/index"; // Имя вашего шаблона Thymeleaf
    }
//    @GetMapping
//    public String index(Model model) {
//        model.addAttribute("typeCounters", typeCounterService.findAll());
//        return "type/index";
//    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") long id, Model model) {
        model.addAttribute("typeCounter", typeCounterService.findOne(id));
        return "type/show";
    }

    @GetMapping("/new")
    public String newTypeCounter(@ModelAttribute("typeCounter")TypeCounter typeCounter) {
        return "type/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("typeCounter") @Valid TypeCounter typeCounter, BindingResult bindingResult) {
        typeCounterValidator.validate(typeCounter,bindingResult);

        if (bindingResult.hasErrors()) {
            return "type/new";
        }

        typeCounterService.save(typeCounter);
        return "redirect:/type";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") long id, Model model) {
        model.addAttribute("typeCounter", typeCounterService.findOne(id));
        return "type/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("typeCounter") @Valid TypeCounter typeCounter, BindingResult bindingResult,
                         @PathVariable("id") long id) {
        typeCounterValidator.validate(typeCounter,bindingResult);
        if (bindingResult.hasErrors()) {
            return "type/edit";
        }

        typeCounterService.update(id, typeCounter);
        return "redirect:/type";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") long id) {
        typeCounterService.delete(id);
        return "redirect:/type";
    }
}
