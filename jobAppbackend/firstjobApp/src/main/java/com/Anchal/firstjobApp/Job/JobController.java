package com.Anchal.firstjobApp.Job;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@CrossOrigin(origins = "http://localhost:5173")
@RestController    //to tell the spring that this is out controller and it has to start executing from here only.
//@RequestMapping("/jobs")   // we can also use it at class level . it basically set url for all the method in this class at once . if we are writing this then not need to write base url for all method. only need to writing method specific url.  like agar yaha pr /job likh diye h to baki method mapping se wo hta sakte h . to har method ke pass bs /job ke baad wala url bachega jisse hmme code km likhna parega.
public class JobController {
    public Jobservice jobService;  // in java we cannot able to make object of interface directly but in spring boot if we make implementation of interface as serivce then using constructor spring boot will provide its object at run time
    public JobController(Jobservice jobService) {
        this.jobService = jobService;
    }

    @GetMapping("/jobs")   //to specify that this is a get api and /jobs is its url.
    public ResponseEntity<List<Job>> findall()
    {
        List<Job> jobs=jobService.findAll();
        //return new ResponseEntity<>(jobs, HttpStatus.OK);    // 1st way
        if(jobs.size()==0)
        {
            return new ResponseEntity<>(jobs ,  HttpStatus.NOT_FOUND);
        }
        return  ResponseEntity.ok(jobs);    //2nd way
    }

    @PostMapping("/jobs")   //to specify it is a post api and /jobs is its url
    public ResponseEntity<String> createJob(@RequestBody Job job)  //here @RequestBody means we are getting this job input as a request body from postman/swagger
    {
        Boolean newjob=jobService.createJob(job);
        if(newjob) {
            return new ResponseEntity<>("job created successfully", HttpStatus.CREATED);
        }
        else{
            return new ResponseEntity<>("Please create company before adding job to it." , HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/job/{id}")   //to specify it is a get api and /job/{id} is its url here id indicates that this api will only find that job whose id is same as given id
    public ResponseEntity<Job> getJobById(@PathVariable Long id)
    {
        Job job= jobService.getJobByid(id);
        if(job==null)
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(job , HttpStatus.FOUND);
    }

    @DeleteMapping("/job/{id}")
    public ResponseEntity<String> deleteJobById(@PathVariable Long id)
    {
        if(jobService.deleteJobById(id))
        {
            return new ResponseEntity<>("job deleted successfully" , HttpStatus.OK);
        }
        return new ResponseEntity<>("job not found" , HttpStatus.NOT_FOUND);
    }

    //@PutMapping("/job/{id}")    //instead of writing this we can also use requestmapping annotation
    @RequestMapping(value = "/job/{id}" , method = RequestMethod.PUT)   //difference between both is that @putmapping is specified for their action and also reduce some code this is not only valid for put but also valid for all type of mapping we use above
    public ResponseEntity<Job> updatejob(@RequestBody Job job,  @PathVariable Long id)
    {
        Job updatedJob = jobService.updateJob(job , id);
        if(updatedJob!=null)
        {
            return new ResponseEntity<>(updatedJob , HttpStatus.OK);
        }
        return new ResponseEntity<>(updatedJob , HttpStatus.NOT_FOUND);
    }
}
// response entity is a class in spring that represent entire http respose.it allow you to customize your response ,flexible response  and also helps you to return different status code based on success and failure of code and we can also include additional information in response if nedded.
//httpstatus is a enum object in java spring that contains all type of status code . it enables readability and maintability of api request. it also helps in error handling  , error mapping

