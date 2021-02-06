package org.gedata.producer.controller;

import lombok.AllArgsConstructor;
import org.gedata.producer.dtomapper.userdata.DtoUserDataMapper;
import org.gedata.producer.model.data.DeletedData;
import org.gedata.producer.model.user.dto.RequestUserData;
import org.gedata.producer.model.user.dto.UserDataDto;
import org.gedata.producer.service.UserDataService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
@RequestMapping("/v1/userdata")
public class ApiUserData {

    private final UserDataService userDataService;

    @GetMapping
    public ResponseEntity<List<UserDataDto>> getAllUsers() {
        return ResponseEntity.ok(userDataService.getAllUserData()
                .stream().map(DtoUserDataMapper::convertToUserDataDto).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDataDto> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(DtoUserDataMapper.convertToUserDataDto(userDataService.getUserById(id)));


    }

    @PostMapping
    public ResponseEntity<UserDataDto> saveUserData(@RequestBody RequestUserData requestData) {
        return ResponseEntity.ok(DtoUserDataMapper
                .convertToUserDataDto(userDataService
                        .createUserData(DtoUserDataMapper.convertToUserData(requestData))));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDataDto> updateUser(@PathVariable Long id, @RequestBody RequestUserData requestData) {
        return ResponseEntity.ok(DtoUserDataMapper
                .convertToUserDataDto(userDataService
                        .updateUserData(id, DtoUserDataMapper.convertToUserData(requestData))));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DeletedData> deleteUserData(@PathVariable Long id) {
        userDataService.deleteUser(id);
        return ResponseEntity.ok(new DeletedData(String.format("User with id %s successfuly deleted.", id)));
    }
}