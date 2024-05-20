package com.example.agency.service;

import com.example.agency.exception.RoleAlreadyExistException;
import com.example.agency.exception.UserAlreadyExistsException;
import com.example.agency.model.Role;
import com.example.agency.model.User;
import com.example.agency.repository.RoleRepo;
import com.example.agency.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService{
    private final RoleRepo roleRepo;
    private final UserRepo userRepo;

    @Override
    public List<Role> getRoles() {
        return roleRepo.findAll();
    }

    @Override
    public Role createRole(Role theRole) {
        String roleName = "ROLE_"+theRole.getName().toUpperCase();
        Role role = new Role(roleName);
        if (roleRepo.existsByName(roleName)){
            throw new RoleAlreadyExistException(theRole.getName()+" уже существует");
        }
        return roleRepo.save(role);
    }

    @Override
    public void deleteRole(Long roleId) {
        this.removeAllUsersFromRole(roleId);
        roleRepo.deleteById(roleId);
    }

    @Override
    public Role findByName(String name) {

        return roleRepo.findByName(name).get();
    }

    @Override
    public User removeUserFromRole(Long userId, Long roleId) {
        Optional<User> user = userRepo.findById(userId);
        Optional<Role> role = roleRepo.findById(userId);
        if (role.isPresent() && role.get().getUsers().contains(user.get())){
            role.get().removeUserFromRole(user.get());
            roleRepo.save(role.get());
            return user.get();
        }
        throw new UsernameNotFoundException("Пользователь не найден");
    }

    @Override
    public User assignRoleToUser(Long userId, Long roleId) {
        Optional<User> user = userRepo.findById(userId);
        Optional<Role> role = roleRepo.findById(userId);
        if (user.isPresent() && user.get().getRoles().contains(role.get())){
            throw new UserAlreadyExistsException(
                    user.get().getFirstName()+ " уже привязан к роли " + role.get().getName());
        }
        if (role.isPresent()){
            role.get().assignRoleToUser(user.get());
            roleRepo.save(role.get());
        }
        return user.get();
    }

    @Override
    public Role removeAllUsersFromRole(Long roleId) {
        Optional<Role> role = roleRepo.findById(roleId);
        role.ifPresent(Role::removeAllUsersFromRole);
        return roleRepo.save(role.get());
    }
}
