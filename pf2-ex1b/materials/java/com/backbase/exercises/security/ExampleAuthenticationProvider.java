package com.backbase.exercises.security;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.backbase.portal.foundation.business.service.GroupBusinessService;
import com.backbase.portal.foundation.business.service.UserBusinessService;
import com.backbase.portal.foundation.commons.exceptions.FoundationDataException;
import com.backbase.portal.foundation.commons.exceptions.FoundationRuntimeException;
import com.backbase.portal.foundation.commons.exceptions.ItemAlreadyExistsException;
import com.backbase.portal.foundation.commons.exceptions.ItemNotFoundException;
import com.backbase.portal.foundation.domain.model.Group;
import com.backbase.portal.foundation.domain.model.Role;
import com.backbase.portal.foundation.domain.model.User;

/**
 * User: bartv
 * Date: 27-09-13
 * Time: 19:58
 *
 * This is an example Authentication Provider used for training purposes only.
 * To configure portal server to use this Authentication Provider you need to add it to a
 * existing Authentication Manager.
 *
 * When you create a portal from the blank archetype, the dependency security-portalserver specifies the
 * backbase-portal-business-security.xml
 * To add this authentication provider to the Authentication Manager, you'll need to get the
 * backbase-portal-business-security.xml from the target directory and add it in /src/main/resources/META-INF/spring
 */
public class ExampleAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    UserBusinessService userBusinessService;

    @Autowired
    GroupBusinessService groupBusinessService;

    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String username = String.valueOf(authentication.getPrincipal());
        String password = String.valueOf(authentication.getCredentials());

        // If the HtPassword Map does not contain the user name, throw a BadCredentialsException
        if (getUsersAndPasswords().containsKey(username) == false) {
            // Never tell the user that the username is incorrect. This will confirm that the user exists in the system
            throw new BadCredentialsException("Username or password does not match");
        }

        // Encode the password using MD5
        String encodedPassword =  encodePassword(password);
        String usersPassword = getUsersAndPasswords().get(username);
        // Check if the entered password equals the password from htPassword
        if (usersPassword.equals(encodedPassword) == false) {
            // Password do not match. throw a bad credentials exception
            throw new BadCredentialsException("Username or password does not match");
        }

        // User name and password match! Now we can lookup the user in the portal database
        User portalUser = null;

        try {
            portalUser = userBusinessService.getUser(username);
        } catch (ItemNotFoundException e) {
            // If the user does not exist in the portal database, the portal business service throws an ItemNotFoundException.
            // Now we can create a user and add it to the default group
            portalUser = new User();
            portalUser.setUsername(username);
            portalUser.setPassword(password);
            portalUser.setEnabled(true);
            // Add the right groups
            portalUser.setGroups(getDefaultGroups());
            try {
                userBusinessService.createUser(portalUser);
            } catch (FoundationDataException e1) {
                throw new AuthenticationServiceException("Cannot create user");
            } catch (ItemNotFoundException e1) {
                throw new AuthenticationServiceException("Something cannot be found");
            } catch (ItemAlreadyExistsException e1) {
                throw new AuthenticationServiceException("User already exists even though we just checked for that...");
            }

        }

        // The Portal User class implements the UserDetails interface so we can use it as our Security Principal.
        UserDetails userDetailsPrincipal = portalUser;
        // Ensure we return the original credentials the user supplied,
        // so subsequent attempts are successful even with encoded passwords.
        Object credentials = authentication.getCredentials();
        // Also ensure we return the original getDetails(), so that future
        // authentication events after cache expiry contain the details
        Object authenticationDetails = authentication.getDetails();
        // A collection of all Granted Authorities for the User. The Granted Authorities are composed from
        // the Groups the user is member of and their roles.
        Collection<GrantedAuthority> grantedAuthorities = portalUser.getAuthorities();

        // return a new UsernameAuthenticationToken with the granted authorities
        UsernamePasswordAuthenticationToken newToken =
                new UsernamePasswordAuthenticationToken(userDetailsPrincipal, credentials,
                        grantedAuthorities);
        newToken.setDetails(authenticationDetails);
        return newToken;
    }

    private List<Group> getDefaultGroups() {
        List<Group> groups = new ArrayList<Group>();
        groups.add(retrieveOrCreateGroup("user"));
        groups.add(retrieveOrCreateGroup("myCustomGroup"));
        return groups;
    }

    public Group retrieveOrCreateGroup(String groupName) throws AuthenticationServiceException {
        Group group = null;
        try {
            group = groupBusinessService.getGroup(groupName);
        } catch (ItemNotFoundException e) {
            group = new Group();
            group.setDescription("Group created by Authentication Provider");
            group.setName(groupName);
            group.setRole(Role.USER);
            try {
                groupBusinessService.createGroup(group);
            } catch (ItemAlreadyExistsException e1) {
                throw new FoundationRuntimeException(e1);
            } catch (FoundationDataException e1) {
                throw new FoundationRuntimeException(e1);
            }
        }
        return group;
    }

    /**
     * Encodes the plain password into a md5 hash string
     *
     * @param password
     * @return
     * @throws java.security.NoSuchAlgorithmException
     */
    private String encodePassword(String password) {
        return DigestUtils.md5Hex(password);
    }

    public Map<String, String> getUsersAndPasswords() {
        Map<String, String> htPassword = new HashMap<String, String>();
        htPassword.put("admin", "21232f297a57a5a743894a0e4a801fc3"); // admin
        htPassword.put("bart","5f4dcc3b5aa765d61d8327deb882cf99"); // password

        return htPassword;
    }

    /**
     * This method returns the supported login types. Since we login using a username and password, this authentication
     * provider only supports the UsernamePasswordAuthenticationToken
     *
     * @param aClass
     * @return
     */
    public boolean supports(Class<?> aClass) {
        return aClass.equals(UsernamePasswordAuthenticationToken.class);
    }
}
