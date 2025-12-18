package org.yearup.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.yearup.data.ProductDao;
import org.yearup.data.ProfileDao;
import org.yearup.data.ShoppingCartDao;
import org.yearup.data.UserDao;
import org.yearup.models.Profile;
import org.yearup.models.User;

import java.security.Principal;

@RestController
@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
@RequestMapping("/profile")
@CrossOrigin
public class ProfileController {
    // i am instatiating these objections just incase i might need them when creating the profile controller
    private UserDao userDao;
    private ProfileDao profileDao;
    private Profile profile;

    //using auto wire so that Spring boot will be able to use it
    @Autowired
    public ProfileController(UserDao userDao, ProfileDao profileDao, Profile profile) {
        this.userDao = userDao;
        this.profileDao = profileDao;
        this.profile = profile;
    }

    @GetMapping("")
    public Profile createProfile(Principal principal, Profile profile){

        try
        {

            String userName = principal.getName();

            User user = userDao.getByUserName(userName);

            int userId = user.getId();

            return profileDao.getByUserId(userId);
        }
        catch(Exception e)
        {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Oops... our bad.");
        }
    }

    @PutMapping("{id}")
    public Profile getByUserId(Principal principal,@PathVariable int id){
        try
        {
            String userName = principal.getName();

            User user = userDao.getByUserName(userName);

            int userId = user.getId();
            return profileDao.getByUserId(userId);
        }
        catch(Exception e)
        {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Oops... our bad.");
        }
    }

}
