package com.example.id.manager;

import com.example.id.dto.UserRequestDTO;
import com.example.id.dto.UserResponseDTO;
import com.example.id.entity.UserEntity;
import com.example.id.exception.ForbiddenException;
import com.example.id.exception.UserNotFoundException;
import com.example.id.repository.UserRepository;
import com.example.id.security.Authentication;
import com.example.id.security.Roles;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@Transactional
@RequiredArgsConstructor
public class UserManager {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final Function<UserEntity, UserResponseDTO> userEntityToUserResponseDTO = userEntity -> new UserResponseDTO(
            userEntity.getId(),
            userEntity.getLogin(),
            userEntity.getRoles()
    );

    public List<UserResponseDTO> getAll(final Authentication authentication) {
        if (!authentication.hasRole(Roles.ROLE_ADMIN)) {
            throw new ForbiddenException();
        }

        final List<UserEntity> all = userRepository.findAll();
        return all.stream()
                .map(userEntityToUserResponseDTO)
                .collect(Collectors.toList())
                ;
    }

    public UserResponseDTO getById(final Authentication authentication, final long id) {
        if (!authentication.hasRole(Roles.ROLE_ADMIN)) {
            throw new ForbiddenException();
        }

        return userRepository.findById(id)
                .map(userEntityToUserResponseDTO)
                .orElseThrow(UserNotFoundException::new)
                ;
    }

    public UserResponseDTO create(final Authentication authentication, final UserRequestDTO requestDTO) {
        if (!authentication.hasRole(Roles.ROLE_ADMIN)) {
            throw new ForbiddenException();
        }

        final UserEntity userEntity = new UserEntity(
                0,
                requestDTO.getName(),
                requestDTO.getLogin(),
                passwordEncoder.encode(requestDTO.getPassword()),
                requestDTO.getRoles()
        );
        final UserEntity savedEntity = userRepository.save(userEntity);
        return userEntityToUserResponseDTO.apply(savedEntity);
    }

    public UserResponseDTO update(final Authentication authentication, final UserRequestDTO requestDTO) {
        if (!authentication.hasRole(Roles.ROLE_ADMIN)) {
            throw new ForbiddenException();
        }

        final UserEntity userEntity = userRepository.getReferenceById(requestDTO.getId());
        userEntity.setName(requestDTO.getName());
        userEntity.setLogin(requestDTO.getLogin());
        userEntity.setPassword(passwordEncoder.encode(requestDTO.getPassword()));
        userEntity.setRoles(requestDTO.getRoles());

        return userEntityToUserResponseDTO.apply(userEntity);
    }

    public void deleteById(final Authentication authentication, final long id) {
        if (!authentication.hasRole(Roles.ROLE_ADMIN)) {
            throw new ForbiddenException();
        }

        userRepository.deleteById(id);
    }
}
