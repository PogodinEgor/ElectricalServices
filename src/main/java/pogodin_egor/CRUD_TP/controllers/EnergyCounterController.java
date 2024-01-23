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
import pogodin_egor.CRUD_TP.models.*;
import pogodin_egor.CRUD_TP.services.EnergyConsumerService;
import pogodin_egor.CRUD_TP.services.EnergyCounterService;
import pogodin_egor.CRUD_TP.services.PowerLineService;
import pogodin_egor.CRUD_TP.services.TypeCounterService;
import pogodin_egor.CRUD_TP.validator.EnergyCounterValidator;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/counter")
@AllArgsConstructor
public class EnergyCounterController {
    @Autowired
    private EnergyCounterService energyCounterService;

    @Autowired
    private PowerLineService powerLineService;

    @Autowired
    private EnergyConsumerService energyConsumerService;

    @Autowired
    private TypeCounterService typeCounterService;

    @Autowired
    private EnergyCounterValidator energyCounterValidator;


    @GetMapping
    public String index(Model model,
                        @RequestParam(value = "page", defaultValue = "0") int page,
                        @RequestParam(value = "size", defaultValue = "20") int size) {
        Page<EnergyCounter> substationsPage = energyCounterService.findAll(page, size);
        model.addAttribute("energyCounters", substationsPage);
        return "counter/index"; // Имя вашего шаблона Thymeleaf
    }

    @ModelAttribute("allTypeCounters")
    public List<TypeCounter> populateTypeCounters() {return typeCounterService.findAll();}

    @ModelAttribute("allPowerLines")
    public List<PowerLine> populatePowerLines() {
        return powerLineService.findAll();
    }

    @ModelAttribute("allEnergyConsumers")
    public List<EnergyConsumer> populateEnergyConsumers() {
        return energyConsumerService.findAll();
    }

    @GetMapping("/search")
    public String searchByName(@RequestParam("serialNumber") String serialNumber, Model model,
                               @RequestParam(value = "page", defaultValue = "0") int page,
                               @RequestParam(value = "size", defaultValue = "20") int size) {
        Page<EnergyCounter> energyPage =  energyCounterService.findBySerialNumber(serialNumber, PageRequest.of(page,size));
        model.addAttribute("energyCounters", energyPage);
        return "counter/index";
    }


    @GetMapping("/{id}")
    public String show(@PathVariable("id") long id, Model model) {
        model.addAttribute("energyCounter", energyCounterService.findOne(id));
        model.addAttribute("energyMeasurements", energyCounterService.findOne(id).getEnergyMeasurements());
        return "counter/show";
    }
//    @GetMapping("{id}")
//    public String show(@PathVariable("id") long id, Model model,
//                       @RequestParam(value = "page", defaultValue = "0") int page,
//                       @RequestParam(value = "size", defaultValue = "5") int size) {
//
//        model.addAttribute("energyCounter", energyCounterService.findOne(id));
//        Page<EnergyCounter> countersPage = energyCounterService.findByEnergyMeasurements(id, PageRequest.of(page, size));
//        model.addAttribute("energyMeasurements", countersPage);
//        return "consumer/show";
//    }

    @GetMapping("/new")
    public String newCounter(@ModelAttribute("energyCounter") EnergyCounter energyCounter) {
        return "counter/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("energyCounter") @Valid EnergyCounter energyCounter, BindingResult bindingResult) {
        energyCounterValidator.validate(energyCounter, bindingResult);

        if (bindingResult.hasErrors()) {
            return "counter/new";
        }

        energyCounterService.save(energyCounter);
        return "redirect:/counter";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") long id, Model model) {
        model.addAttribute("powerLine", powerLineService.findOne(id));
        model.addAttribute("energyCounter", energyCounterService.findOne(id));
        return "counter/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("energyCounter") @Valid EnergyCounter energyCounter, BindingResult bindingResult,
                         @PathVariable("id") long id) {
        energyCounterValidator.validate(energyCounter, bindingResult);
        if (bindingResult.hasErrors()) {
            return "counter/edit";
        }

        energyCounterService.update(id, energyCounter);
        return "redirect:/counter";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") long id) {
        energyCounterService.delete(id);
        return "redirect:/counter";
    }

}
