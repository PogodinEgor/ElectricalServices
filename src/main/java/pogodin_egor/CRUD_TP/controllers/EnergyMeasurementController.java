package pogodin_egor.CRUD_TP.controllers;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pogodin_egor.CRUD_TP.models.EnergyCounter;
import pogodin_egor.CRUD_TP.models.EnergyMeasurement;
import pogodin_egor.CRUD_TP.models.TransformatorSubstation;
import pogodin_egor.CRUD_TP.services.EnergyCounterService;
import pogodin_egor.CRUD_TP.services.EnergyMeasurementService;

import java.util.List;

@Controller
@RequestMapping("/measurement")
@AllArgsConstructor
public class EnergyMeasurementController {

    @Autowired
    private EnergyMeasurementService energyMeasurementService;

    @Autowired
    private EnergyCounterService energyCounterService;


    @ModelAttribute("allEnergyMeasurements")
    public List<EnergyCounter> populateEnergyMeasurements() {
        return energyCounterService.findAll();
    }

    @GetMapping
    public String index(Model model,
                        @RequestParam(value = "page", defaultValue = "0") int page,
                        @RequestParam(value = "size", defaultValue = "20") int size) {
        Page<EnergyMeasurement> substationsPage = energyMeasurementService.findAll(page, size);
        model.addAttribute("energyMeasurements", substationsPage);
        return "measurement/index"; // Имя вашего шаблона Thymeleaf
    }


    @GetMapping("/{id}")
    public String show(@PathVariable("id") long id, Model model) {
        model.addAttribute("energyMeasurement", energyMeasurementService.findOne(id));
        return "measurement/show";
    }

    @GetMapping("/new")
    public String newMeasurement(@ModelAttribute("energyMeasurement") EnergyMeasurement energyMeasurement) {
        return "measurement/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("energyMeasurement") @Valid EnergyMeasurement energyMeasurement, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "measurement/new";
        }

        energyMeasurementService.save(energyMeasurement);

        return "redirect:/measurement";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") long id, Model model) {
        model.addAttribute("energyMeasurement", energyMeasurementService.findOne(id));
        return "measurement/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("energyMeasurement") @Valid EnergyMeasurement energyMeasurement, BindingResult bindingResult,
                         @PathVariable("id") long id) {
        if (bindingResult.hasErrors()) {
            return "measurement/edit";
        }


        energyMeasurementService.update(id, energyMeasurement);
        return "redirect:/measurement";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") long id) {
        energyMeasurementService.delete(id);
        return "redirect:/measurement";
    }
}
