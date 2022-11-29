pack org.team.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.team.models.faculty;
import org.team.repositories.facultyRepository;

import java.util.List;

@Service
public class facultyService {

    private final facultyRepository facultyRepository;

    @Autowired
    public facultyService(facultyRepository facultyRepository){
        this.facultyRepository = facultyRepository;
    }

    public faculty findfacultyByAmka(String id){
        return facultyRepository.findById(id).get();
    }

    public List<faculty> getfacultysWithdepartment(String specName){
        return facultyRepository.findfacultysBydepartmentNameEquals(specName);
    }
}
