package org.gedata.producer.service;

import lombok.AllArgsConstructor;
import org.gedata.producer.model.producer.GenericData;
import org.gedata.producer.model.user.UserData;
import org.gedata.producer.repository.GenericDataRepository;
import org.gedata.producer.repository.UserDataRepository;
import org.gedata.producer.utils.UserDuplicatedDataException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@AllArgsConstructor
@Service
public class UserDataService {

    private final UserDataRepository userDataRepository;
    private final GenericDataRepository genericDataRepository;


    public List<UserData> getAllUserData(){
        return userDataRepository.findAll();
    }

    public UserData getUserById(Long id){
        return userDataRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException(String.format("User with %s does not exist.", id)));
    }

    public UserData createUserData(UserData userData){
        List<UserData> userDataDuplicated = userDataRepository.findByEmailAddressOrNickname(userData.getEmailAddress(),userData.getNickname());
        if(!userDataDuplicated.isEmpty()){
           userDataDuplicated.forEach(userData1 -> {
               if(userData1.getNickname().equals(userData.getNickname())){
                   throw new UserDuplicatedDataException(
                           String.format("Please use the different nickname, nick: %s already exist.",userData.getNickname()));
               }else{
                   throw new UserDuplicatedDataException(
                           String.format("Please use the different email. %s already exist.",userData.getEmailAddress()));
               }
           });
        }
        return userDataRepository.save(userData);
    }

    public UserData updateUserData(Long id, UserData userData){
           return userDataRepository.findById(id).map(user->{
               user.setFirstName(userData.getFirstName());
               user.setLastName(userData.getLastName());
               user.setEmailAddress(userData.getEmailAddress());
               user.setNickname(userData.getNickname());
               userDataRepository.save(user);
               return user;
           }).orElseThrow(()->new NoSuchElementException(
                   String.format("Operation update can not be performed, user with id %s does not exist.",id)));
    }

    public List<GenericData> getGenericDataByUserId(Long id){
        UserData user = getUserById(id);
        return genericDataRepository.findGenericDataByUserData(user);
    }

    public void deleteUser(Long id){
        userDataRepository.delete(getUserById(id));
    }

}
