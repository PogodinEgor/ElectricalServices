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
import pogodin_egor.CRUD_TP.models.MainPowerStation;
import pogodin_egor.CRUD_TP.services.MainPowerStationService;
import pogodin_egor.CRUD_TP.services.TransformatorSubstationService;
import pogodin_egor.CRUD_TP.validator.MainPowerStationValidator;


@Controller
@RequestMapping("/powerstation")
@AllArgsConstructor
public class MainPowerStationController {

    @Autowired
    private MainPowerStationService mainPowerStationService;

    @Autowired
    private MainPowerStationValidator mainPowerStationValidator;

    @Autowired
    private TransformatorSubstationService transformatorSubstationService;


    @GetMapping
    public String index(Model model) {
        model.addAttribute("mainPowerStations", mainPowerStationService.findAll());
        return "powerstation/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") long id, Model model) {
        model.addAttribute("mainPowerStation", mainPowerStationService.findOne(id));
        model.addAttribute("transformatorSubstations", mainPowerStationService.findOne(id).getTransformatorSubstationList());
        return "powerstation/show";
    }

    @GetMapping("/new")
    public String newPowerStation(@ModelAttribute("mainPowerStation") MainPowerStation mainPowerStation) {
        return "powerstation/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("mainPowerStation") @Valid MainPowerStation mainPowerStation, BindingResult bindingResult) {
        mainPowerStationValidator.validate(mainPowerStation, bindingResult);

        if (bindingResult.hasErrors()) {
            return "powerstation/new";
        }

        mainPowerStationService.save(mainPowerStation);
        return "redirect:/powerstation";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") long id, Model model) {
        model.addAttribute("mainPowerStation", mainPowerStationService.findOne(id));
        return "powerstation/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("mainPowerStation") @Valid MainPowerStation mainPowerStation, BindingResult bindingResult,
                         @PathVariable("id") long id) {
        if (bindingResult.hasErrors()) {
            return "powerstation/edit";
        }

        mainPowerStationService.update(id, mainPowerStation);
        return "redirect:/powerstation";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") long id) {
        mainPowerStationService.delete(id);
        return "redirect:/powerstation";
    }


}
