pack org.team.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.team.exceptions.departmentNotFoundException;
import org.team.models.department;
import org.team.repositories.departmentRepository;
import org.team.services.departmentService;

import java.util.List;

@RestController
public class departmentController {

    private final departmentRepository departmentRepository;
    private final departmentService departmentService;

    @Autowired
    public departmentController(departmentRepository departmentRepository, departmentService departmentService) {
        this.departmentRepository = departmentRepository;
        this.departmentService = departmentService;
    }

    @GetMapping("/department/all")
    public List<department> getdepartment() {
        return departmentService.findAllSpecialties();
    }

    @GetMapping("/department/{name}")
    public department getdepartment(@PathVariable String specName) {
        return departmentService.findSpecBySpecName(specName);
    }

    @PostMapping("/department")
    public department newdepartment(@RequestBody department department) {
        return departmentService.saveSpec(department);
    }

    @PutMapping("/department/{id}")
    public department updatedepartment(@PathVariable Long id, @RequestBody department updatedepartment) {
        return departmentRepository.findById(id)
                .map(department -> {
                    department.setName(updatedepartment.getName());
                    return departmentRepository.save(department);
                })
                .orElseThrow(() -> new departmentNotFoundException(id));
    }

    @DeleteMapping("department/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deletedepartment(@PathVariable String name) {
        getdepartment(name);
        departmentRepository.deleteByName(name);
    }
}
