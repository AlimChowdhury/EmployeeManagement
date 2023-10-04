package com.alim.EmployeeManagement.controller;


import com.alim.EmployeeManagement.model.User;
import com.alim.EmployeeManagement.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class CrudController {


    private final IUserService iUserService;

    @Autowired
    public CrudController(IUserService iUserService) {
        this.iUserService = iUserService;
    }

    //display list
    @GetMapping("/index")
    public String viewUserPg(Model model){
        model.addAttribute("listUsers",iUserService.getAllUsers());
        return "index";
    }

    @GetMapping("/showFormForUpdate/{id}")
    public String showFormForUpdate(@PathVariable(value = "id") long id, Model model){
        //get user from the service
        User user= iUserService.getUserById(id);

        // set employee as a model attribute to pre-populate the form
        model.addAttribute("user",user);
        return "update_user";
    }

    @GetMapping("/deleteUser/{id}")
    public String deleteUser(@PathVariable(value = "id") long id)
    {
        this.iUserService.deleteUserById(id);
        return "redirect:/index";
    }



}
