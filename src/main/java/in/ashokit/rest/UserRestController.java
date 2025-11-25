package in.ashokit.rest;

import in.ashokit.dto.*;
import in.ashokit.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
public class UserRestController {
    @Autowired
    private UserService userService;

    @GetMapping("/countries")
    public ResponseEntity<ApiResponse<List<CountryDto>>> getCountries(){
        log.debug("Execution started");

        ApiResponse<List<CountryDto>> response = new ApiResponse<>();
        List<CountryDto>countries = userService.getCountries();
        if(countries.isEmpty()){
            response.setStatus(500);
            response.setMessage("No countries found");
            response.setData(null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        } else {
            response.setStatus(200);
            response.setMessage("Fetched countries successfully");
            response.setData(countries);
            return new ResponseEntity<>(response,HttpStatus.OK);
        }
    }

    @GetMapping("/states/{countryId}")
    public ResponseEntity<ApiResponse<List<StateDto>>> getStates(@PathVariable Integer countryId){
        ApiResponse<List<StateDto>> response = new ApiResponse<>();
        List<StateDto> states = userService.getStates(countryId);
        if(states.isEmpty()){
            response.setStatus(500);
            response.setMessage("No states found");
            response.setData(null);
            return new ResponseEntity<>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }else{
            response.setStatus(200);
            response.setMessage("States fetched successfully");
            response.setData(states);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }

    @GetMapping("/cities/{stateId}")
    public ResponseEntity<ApiResponse<List<CityDto>>> getCities(@PathVariable Integer stateId){
        ApiResponse<List<CityDto>> response = new ApiResponse<>();
        List<CityDto>cities = userService.getCities(stateId);
        if(cities.isEmpty()){
            response.setStatus(500);
            response.setMessage("No cities found");
            response.setData(null);
            return new ResponseEntity<>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }else{
            response.setStatus(200);
            response.setMessage("cities fetched successfully");
            response.setData(cities);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }

    @GetMapping("/unique/{email}")
    public ResponseEntity<ApiResponse<String>> checkEmail(@PathVariable String email){
        ApiResponse<String> response = new ApiResponse<>();
        boolean isUnique = userService.isEmailUnique(email);
        if(isUnique){
            response.setStatus(200);
            response.setMessage("No Email found");
            response.setData("UNIQUE");
            return new ResponseEntity<>(response,HttpStatus.OK);
        }else{
            response.setStatus(500);
            response.setMessage("Duplicate Email found");
            response.setData("DUPLICATE");
            return new ResponseEntity<>(response,HttpStatus.OK);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<String>> resgisterUser(@RequestBody UserDto userDto){
        ApiResponse<String> response = new ApiResponse<>();
        boolean isRegistered = userService.register(userDto);
        if(isRegistered){
            response.setStatus(200);
            response.setMessage("Resgistration successful");
            response.setData("SUCCESS");
            return new ResponseEntity<>(response,HttpStatus.OK);
        }else{
            response.setStatus(500);
            response.setMessage("Failed to Register");
            response.setData("FAILED");
            return new ResponseEntity<>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<UserDto>> login(@RequestBody UserDto userDto){
        ApiResponse<UserDto> response = new ApiResponse<>();
        UserDto user = userService.login(userDto.getEmail(),userDto.getPwd());
        if(user!=null){
            response.setStatus(200);
            response.setMessage("Login Successful");
            response.setData(user);
            return new ResponseEntity<>(response,HttpStatus.OK);
        }else{
            response.setStatus(401);
            response.setMessage("Invalid Credentials");
            response.setData(null);
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/reset-pwd")
    public ResponseEntity<ApiResponse<String>> resetPwd(@RequestBody ResetPwdDto resetPwdDto){
        ApiResponse<String> response =new ApiResponse<>();
        boolean isPwdUpdated = userService.resetPwd(resetPwdDto);
        if(isPwdUpdated){
            response.setStatus(200);
            response.setMessage("Pwd updated successfully");
            response.setData("SUCCESS");
            return new ResponseEntity<>(response,HttpStatus.OK);
        }else{
            response.setStatus(500);
            response.setMessage("Record not found");
            response.setData("FAILED");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/quote")
    public ResponseEntity<ApiResponse<QuoteApiResponseDto>> getQuotation(){
        ApiResponse<QuoteApiResponseDto> response = new ApiResponse<>();
        QuoteApiResponseDto quote = userService.getQuote();
        if(quote != null){
            response.setStatus(200);
            response.setMessage("Quote fetched successfully");
            response.setData(quote);
            return new ResponseEntity<>(response,HttpStatus.OK);
        }else{
            response.setStatus(500);
            response.setMessage("Quote Fetching failed");
            response.setData(null);
            return new ResponseEntity<>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
