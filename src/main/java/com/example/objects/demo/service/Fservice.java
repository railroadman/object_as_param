package com.example.objects.demo.service;

import com.example.objects.demo.dto.UserDto;
import com.example.objects.demo.filter.Filter;
import com.example.objects.demo.repository.DataRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class Fservice {
    @Autowired
    private DataRepo repo;
    public boolean getSomethingWithFilter(Filter filter) {
        List<UserDto> userDtos = repo.getData(filter);
        List<UserDto> result = userDtos.stream()
                .filter(e -> filter.getFirstName() == null ? true : e.getFirstName().equals(filter.getFirstName()))
                .filter(e -> filter.getEmail() == null ? true : e.getEmail().equals(filter.getEmail()))
                .filter(e -> filter.getLogin() == null ? true : e.getLogin().equals(filter.getLogin()))
                .filter(e -> filter.getLastName() == null ? true : e.getLastName().equals(filter.getLastName()))
                .filter(e -> filter.getAge() == null ? true : e.getAge().equals(filter.getAge()))
                .collect(Collectors.toList());


        if (result.size()>0){
            return true;
        }
        else return false;
    }
}
