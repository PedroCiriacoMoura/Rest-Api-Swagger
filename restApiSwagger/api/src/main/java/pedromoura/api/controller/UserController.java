package pedromoura.api.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pedromoura.api.model.UserModel;
import pedromoura.api.repository.UserRepository;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value="/user")
@Api(value="API REST")
public class UserController {

    @Autowired
    private UserRepository repository;

    @ApiOperation(value="List all users")
    @GetMapping(path = "/list")
    public List<UserModel> list () {
        return repository.findAll();
    }

    @ApiOperation(value="Return user")
    @GetMapping (path = "/{id}")
    public ResponseEntity consult (@PathVariable ("id") Long id)
    {
        return repository.findById(id)
                .map(record -> ResponseEntity.ok().body(record))
                .orElse(ResponseEntity.notFound().build());
    }

    @ApiOperation(value="Add user")
    @PostMapping(path = "/add")
    @ResponseStatus(HttpStatus.CREATED)
    public UserModel add (@RequestBody UserModel user) {
        return repository.save(user);
    }


    @ApiOperation(value="Delete user")
    @DeleteMapping(path = "/delete/{id}")
    public void delete (@PathVariable("id") Long id){
        if (repository.existsById(id)){
            repository.deleteById(id);
        }
    }
}
