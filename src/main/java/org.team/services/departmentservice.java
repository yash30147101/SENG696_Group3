pack org.team.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.team.models.department;
import org.team.repositories.departmentRepository;
import java.util.List;

@Service
public class departmentService {

    private final departmentRepository departmentRepository;

    @Autowired
    public departmentService(departmentRepository departmentRepository){
        this.departmentRepository = departmentRepository;
    }


    public List<department> findAllSpecialties(){ return departmentRepository.findAll(); }

    public department saveSpec(department department){ return departmentRepository.save(department); }

    public department findSpecBySpecName(String specName){
        return departmentRepository.findByName(specName);
    }



}
