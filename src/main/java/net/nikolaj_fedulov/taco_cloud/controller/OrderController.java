package net.nikolaj_fedulov.taco_cloud.controller;

import lombok.extern.slf4j.Slf4j;
import net.nikolaj_fedulov.taco_cloud.model.TacoOrder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

@Slf4j
@Controller
@RequestMapping("/orders")
@SessionAttributes("tacoOrder")
public class OrderController {

    @GetMapping("/current")
    public String orderForm() {
        return "orderForm";
    }

    @PostMapping
    public String processOder(TacoOrder tacoOrder, SessionStatus sessionStatus) {
        log.info("Oder submitted: {}", tacoOrder);
        sessionStatus.setComplete();
        return "redirect:/";
    }
}
