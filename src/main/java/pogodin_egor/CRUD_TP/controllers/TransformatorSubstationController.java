package pogodin_egor.CRUD_TP.controllers;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pogodin_egor.CRUD_TP.exception.DublicateNameException;
import pogodin_egor.CRUD_TP.models.MainPowerStation;
import pogodin_egor.CRUD_TP.models.TransformatorSubstation;
import pogodin_egor.CRUD_TP.services.MainPowerStationService;
import pogodin_egor.CRUD_TP.services.TransformatorSubstationService;
import pogodin_egor.CRUD_TP.validator.TransformatorSubstationValidator;


import java.util.List;


@Controller
@RequestMapping("/substation")
@AllArgsConstructor
public class TransformatorSubstationController {

    @Autowired
    private TransformatorSubstationService transformatorSubstationService;

    @Autowired
    private TransformatorSubstationValidator transformatorSubstationValidator;

    @Autowired
    private MainPowerStationService mainPowerStationService;

    @GetMapping
    public String index(Model model,
                        @RequestParam(value = "page", defaultValue = "0") int page,
                        @RequestParam(value = "size", defaultValue = "20") int size) {
        Page<TransformatorSubstation> substationsPage = transformatorSubstationService.findAll(page, size);
        model.addAttribute("transformatorSubstations", substationsPage);
        return "substation/index"; // Имя вашего шаблона Thymeleaf
    }

    @GetMapping("/search")
    public String searchByName(@RequestParam("name") String name, Model model) {
        model.addAttribute("transformatorSubstations", transformatorSubstationService.findByNameTransformatorSubstation(name));
        return "substation/index";
    }


    @ModelAttribute("allMainPowerStations")
    public List<MainPowerStation> populateMainPowerStations() {
        return mainPowerStationService.findAll();
    }


    @GetMapping("/{id}")
    public String show(@PathVariable("id") long id, Model model) {
        model.addAttribute("transformatorSubstation", transformatorSubstationService.findOne(id));
        model.addAttribute("powerLines", transformatorSubstationService.findOne(id).getPowerLineList());
        return "substation/show";
    }

    @GetMapping("/new")
    public String newSubstation(@ModelAttribute("transformatorSubstation") TransformatorSubstation transformatorSubstation) {
        return "substation/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("transformatorSubstation") @Valid TransformatorSubstation transformatorSubstation, BindingResult bindingResult,Model model) {
        transformatorSubstationValidator.validate(transformatorSubstation, bindingResult);
        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult);
            return "substation/new";
        }
        if (transformatorSubstation.getPowerStation() != null && transformatorSubstation.getPowerStation().getId() != null) {
            MainPowerStation mainPowerStation = mainPowerStationService.findOne(transformatorSubstation.getPowerStation().getId());
            transformatorSubstation.setPowerStation(mainPowerStation);
        }

        try {
            transformatorSubstationService.save(transformatorSubstation);
            return "redirect:/substation";
        } catch (DublicateNameException e) {
            bindingResult.rejectValue("nameTransformatorSubstation", "", e.getMessage());
            model.addAttribute("errors", bindingResult);
            return "substation/new";
        }

    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") long id, Model model) {
        model.addAttribute("transformatorSubstation", transformatorSubstationService.findOne(id));
        return "substation/edit";
    }

    @PostMapping("/{id}")
    public String update(@ModelAttribute("transformatorSubstation") @Valid TransformatorSubstation transformatorSubstation, BindingResult bindingResult,
                         @PathVariable("id") long id ) {
        if (bindingResult.hasErrors()) {
            return "substation/edit";
        }

        if (transformatorSubstation.getPowerStation() != null && transformatorSubstation.getPowerStation().getId() != null) {
            MainPowerStation mainPowerStation = mainPowerStationService.findOne(transformatorSubstation.getPowerStation().getId());
            transformatorSubstation.setPowerStation(mainPowerStation);
        }


        transformatorSubstationService.update(id, transformatorSubstation);
        return "redirect:/substation";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") long id) {
        transformatorSubstationService.delete(id);
        return "redirect:/substation";
    }
}
