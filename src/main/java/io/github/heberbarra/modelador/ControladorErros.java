package io.github.heberbarra.modelador;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ControladorErros implements ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, ModelMap modelMap) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        Object errorMessage = request.getAttribute(RequestDispatcher.ERROR_MESSAGE);
        ControladorWeb.injetarPaleta(modelMap);
        modelMap.addAttribute("status", status);
        modelMap.addAttribute("error", errorMessage);

        return "error";
    }
}
