package in.ashokit.service;

import in.ashokit.dto.*;

import java.util.List;

public interface UserService {

    public List<CountryDto> getCountries();

    public List<StateDto> getStates(Integer countryId);

    public List<CityDto> getCities(Integer stateId);

    public boolean isEmailUnique(String email);

    public UserDto login(String email,String pwd);

    public boolean register(UserDto userDto);

    public boolean resetPwd(ResetPwdDto resetPwdDto);

    public QuoteApiResponseDto getQuote();

}
