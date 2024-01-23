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
import pogodin_egor.CRUD_TP.exception.DublicateNameException;
import pogodin_egor.CRUD_TP.models.EnergyCounter;
import pogodin_egor.CRUD_TP.models.PowerLine;
import pogodin_egor.CRUD_TP.models.TransformatorSubstation;
import pogodin_egor.CRUD_TP.services.EnergyCounterService;
import pogodin_egor.CRUD_TP.services.PowerLineService;
import pogodin_egor.CRUD_TP.services.TransformatorSubstationService;
import pogodin_egor.CRUD_TP.validator.PowerLineValidator;

import java.awt.print.Pageable;
import java.util.List;

@Controller
@RequestMapping("/powerline")
@AllArgsConstructor
public class PowerLineController {

    @Autowired
    private PowerLineService powerLineService;

    @Autowired
    private TransformatorSubstationService transformatorSubstationService;

    @Autowired
    private EnergyCounterService energyCounterService;

    @Autowired
    PowerLineValidator powerLineValidator;

    @ModelAttribute("allTransformatorSubstations")
    public List<TransformatorSubstation> populateTransformatorSubstations() {
        return transformatorSubstationService.findAll();
    }

    @GetMapping
    public String index(Model model,
                        @RequestParam(value = "page", defaultValue = "0") int page,
                        @RequestParam(value = "size", defaultValue = "20") int size) {
        Page<PowerLine> substationsPage = powerLineService.findAll(page, size);
        model.addAttribute("powerLines", substationsPage);
        return "powerline/index"; // Имя вашего шаблона Thymeleaf
    }


    @GetMapping("/{id}")
    public String show(@PathVariable("id") long id, Model model) {
        model.addAttribute("powerLine", powerLineService.findOne(id));
        model.addAttribute("energyCounters", powerLineService.findOne(id).getEnergyCounterList());
        return "powerline/show";
    }

    @GetMapping("/new")
    public String newPowerLine(@ModelAttribute("powerLine") PowerLine powerLine) {
        return "powerline/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("powerLine") @Valid PowerLine powerLine, BindingResult bindingResult, Model model) {
        powerLineValidator.validate(powerLine,bindingResult);

        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult);
            return "powerline/new";
        }

        try {
            powerLineService.save(powerLine);
            return "redirect:/powerline";
        } catch (DublicateNameException e) {
            bindingResult.rejectValue("namePowerLine", "", e.getMessage());
            model.addAttribute("errors", bindingResult);
            return "powerline/new";
        }
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") long id, Model model) {
        model.addAttribute("powerLine", powerLineService.findOne(id));
        return "powerline/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("powerLine") @Valid PowerLine powerLine, BindingResult bindingResult,
                         @PathVariable("id") long id,Model model) {
        powerLineValidator.validate(powerLine,bindingResult);
        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult);
            return "powerline/edit";
        }

        try{
        powerLineService.update(id, powerLine);
        return "redirect:/powerline";}
        catch (DublicateNameException e){
            bindingResult.rejectValue("namePowerLine", "", e.getMessage());
            model.addAttribute("errors", bindingResult);
            return "powerline/edit";
        }
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") long id) {
        powerLineService.delete(id);
        return "redirect:/powerline";
    }
}
