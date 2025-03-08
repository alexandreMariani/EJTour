package app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import app.entity.Role;
import app.service.RoleService;

@RestController
@RequestMapping(value = "/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping(value = "/{id}")
    public Role findById(@PathVariable Long id){
        return roleService.findById(id);
    }

    @PostMapping
    public Role save(@RequestBody Role department){

        return roleService.postMapping(department);
    }

}
