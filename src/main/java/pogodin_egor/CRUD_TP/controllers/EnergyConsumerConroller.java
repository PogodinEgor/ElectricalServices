package pogodin_egor.CRUD_TP.controllers;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pogodin_egor.CRUD_TP.models.EnergyConsumer;
import pogodin_egor.CRUD_TP.services.EnergyConsumerService;
import pogodin_egor.CRUD_TP.validator.EnergyConsumerValidator;



@Controller
@RequestMapping("/consumer")
@AllArgsConstructor
public class EnergyConsumerConroller {

    @Autowired
    private final EnergyConsumerService energyConsumerService;

    @Autowired
    private final EnergyConsumerValidator energyConsumerValidator;


    @GetMapping
    public String index(Model model,
                        @RequestParam(value = "page", defaultValue = "0") int page,
                        @RequestParam(value = "size", defaultValue = "20") int size) {
        Page<EnergyConsumer> substationsPage = energyConsumerService.findAll(page, size);
        model.addAttribute("energyConsumers", substationsPage);
        return "consumer/index";
    }


    @GetMapping("/search")
    public String searchByName(@RequestParam("lastName") String lastName, Model model,
                               @RequestParam(value = "page", defaultValue = "0") int page,
                               @RequestParam(value = "size", defaultValue = "20") int size) {
        Page<EnergyConsumer> consumersPage = energyConsumerService.findByLastNameIgnoreCase(lastName, PageRequest.of(page, size));
        model.addAttribute("energyConsumers", consumersPage);
        return "consumer/index";
    }

    @GetMapping("{id}")
    public String show(@PathVariable("id") long id, Model model){
        model.addAttribute("energyConsumer",energyConsumerService.findOne(id));
        model.addAttribute("energyCounters",energyConsumerService.findOne(id).getCounterList());
        return "consumer/show";
    }

    @GetMapping("/new")
    public String newConsumer(@ModelAttribute("energyConsumer") EnergyConsumer energyConsumer) {
        return "consumer/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("energyConsumer") @Valid EnergyConsumer energyConsumer,
                         BindingResult bindingResult) {
       energyConsumerValidator.validate(energyConsumer, bindingResult);

        if (bindingResult.hasErrors())
            return "consumer/new";

        energyConsumerService.save(energyConsumer);
        return "redirect:/consumer";
    }

    @GetMapping("/{id}/edit")
    public String edit( @PathVariable("id") long id ,Model model) {
        model.addAttribute("energyConsumer", energyConsumerService.findOne(id));
        return "consumer/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("energyConsumer") @Valid EnergyConsumer energyConsumer, BindingResult bindingResult,
                         @PathVariable("id") long id) {
        if (bindingResult.hasErrors())
            return "consumer/edit";

        energyConsumerService.update(id, energyConsumer);
        return "redirect:/consumer";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") long id) {
        energyConsumerService.delete(id);
        return "redirect:/consumer";
    }




}
