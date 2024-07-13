package com.luv2code.springboot.thymeleafdemo.controller;

import com.luv2code.springboot.thymeleafdemo.entity.Employee;
import com.luv2code.springboot.thymeleafdemo.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.model.IModel;

import java.util.List;

@Controller
@RequestMapping("/employees")
public class EmployeeController {

    private EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    //    Add mapping for listing employees
    @GetMapping("list")
    public String listEmployees(Model model) {

//        get the employees from the database
        List<Employee> employees = employeeService.findAll();

//        add to the spring model
        model.addAttribute("employees", employees);

        return "employees/listEmployees";

    }

    @GetMapping("showFormForAdd")
    public String showForm(Model model) {
        Employee employee = new Employee();
        model.addAttribute("employee", employee);
        return "employees/employeeForm";
    }

    @PostMapping("/save")
    public String addEmployee(@ModelAttribute("employee") Employee employee) {
//        Save the employee
        employeeService.save(employee);
//        Use a redirect to prevent duplicate submissions
        return "redirect:/employees/list";
    }

    @GetMapping("/showFormForUpdate")
    public String showFormForUpdate(@RequestParam("employeeId") int theId, Model model) {


//        Get the employee from the service
        Employee employee = employeeService.findById(theId);

//        set employee in the model to populate the form
        model.addAttribute("employee", employee);
//        send over to our form
        return "employees/employeeForm";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam("employeeId") int theId) {
//        delete the employee
        employeeService.deleteById(theId);
//    redirect to the home page
        return "redirect:/employees/list";
    }

}
