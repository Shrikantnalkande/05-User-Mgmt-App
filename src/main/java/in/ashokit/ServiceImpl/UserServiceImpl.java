package in.ashokit.ServiceImpl;

import in.ashokit.dto.*;
import in.ashokit.entity.CityEntity;
import in.ashokit.entity.CountryEntity;
import in.ashokit.entity.StateEntity;
import in.ashokit.entity.UserEntity;
import in.ashokit.repository.CityRepository;
import in.ashokit.repository.CountryRepository;
import in.ashokit.repository.StateRepository;
import in.ashokit.repository.UserRepository;
import in.ashokit.service.UserService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Random;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private CountryRepository countryRepository;

    private StateRepository stateRepository;

    private CityRepository cityRepository;

    private UserRepository userRepository;

    private ModelMapper mapper;

    private EmailServiceImpl emailService;

    @Override
    public List<CountryDto> getCountries() {
        List<CountryEntity> countries = countryRepository.findAll();
        return countries.stream().map(country->mapper.map(country,CountryDto.class)).toList();
    }

    @Override
    public List<StateDto> getStates(Integer countryId) {
        List<StateEntity> states = stateRepository.findByCountryCountryId(countryId);
        return states.stream().map(state->mapper.map(state,StateDto.class)).toList();
    }

    @Override
    public List<CityDto> getCities(Integer stateId) {
        List<CityEntity> cities = cityRepository.findByStateStateId(stateId);
        return cities.stream().map(city->mapper.map(city,CityDto.class)).toList();
    }

    @Override
    public boolean isEmailUnique(String email) {
        return !userRepository.existsByEmail(email);
    }

    @Override
    public UserDto login(String email, String pwd) {
        UserEntity userEntity = userRepository.findByEmailAndPwd(email,pwd);
        if(userEntity!=null){
            return mapper.map(userEntity,UserDto.class);
        }
        return null;
    }

    @Override
    public boolean register(UserDto userDto) {
        UserEntity userEntity = mapper.map(userDto,UserEntity.class);

        userEntity.setPwd(generateRandomPwd(5));
        userEntity.setPwdUpdated("NO");

        CountryEntity country = countryRepository.findById(userDto.getCountryId()).orElseThrow();
        StateEntity state = stateRepository.findById(userDto.getStateId()).orElseThrow();
        CityEntity city = cityRepository.findById(userDto.getCityId()).orElseThrow();
        userEntity.setCountry(country);
        userEntity.setState(state);
        userEntity.setCity(city);
        UserEntity user = userRepository.save(userEntity);
        if(user.getUserId()!=null){
            String subject = "ASHOK-IT Your Account Created";
            String body = "<h2> your temporary pwd:"+user.getPwd();
            return emailService.sendEmail(subject,body,userDto.getEmail());
        }
        return false;
    }

    private String generateRandomPwd(int pwdLength) {
        Random random = new Random();
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ123456789";
        StringBuilder builder = new StringBuilder(pwdLength);
        for(int i=0;i<pwdLength;i++) {
            int randomIndex = random.nextInt(chars.length());
            char ch = chars.charAt(randomIndex);
            builder.append(ch);
        }
        return builder.toString();
    }

    @Override
    public boolean resetPwd(ResetPwdDto resetPwdDto) {
        UserEntity user = userRepository.findByEmail(resetPwdDto.getEmail());
        if(user!=null && user.getPwd().equals( resetPwdDto.getOldPwd()) && resetPwdDto.getNewPwd().equals( resetPwdDto.getConfirmPwd())){
            user.setPwd(resetPwdDto.getNewPwd());
            user.setPwdUpdated("YES");
            userRepository.save(user);
            return true;
        }
        return false;
    }

    @Override
    public QuoteApiResponseDto getQuote() {
        String apiUrl = "https://dummyjson.com/quotes/random";
        RestTemplate rt = new RestTemplate();
        return rt.getForEntity(apiUrl,QuoteApiResponseDto.class).getBody();
    }
}
