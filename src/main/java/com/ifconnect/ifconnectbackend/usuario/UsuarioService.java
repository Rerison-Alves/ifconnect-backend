package com.ifconnect.ifconnectbackend.usuario;

import com.ifconnect.ifconnectbackend.models.Usuario;
import com.ifconnect.ifconnectbackend.models.modelvo.SearchFilter;
import com.ifconnect.ifconnectbackend.requestmodels.ChangePasswordRequest;
import jakarta.persistence.NoResultException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final PasswordEncoder passwordEncoder;
    private final UsuarioRepository repository;

    @Transactional
    public Usuario saveOrUpdate(Usuario entity) {
        return repository.saveAndFlush(entity);
    }

    public Usuario findById(Integer id) {
        return repository.findById(id).orElseThrow(() -> {
            throw new NoResultException("Ops! Not Found entity for this id! :(");
        });
    }

    public List<Usuario> findAll() {
        return repository.findAll();
    }

    public Page<Usuario> usuarioPageable(SearchFilter searchFilter) {
        return repository.searchPageable(searchFilter.getFilter().get(),
                PageRequest.of(searchFilter.getPage(), searchFilter.getSize(), searchFilter.getDirection(), searchFilter.getOrder()));
    }

    @Transactional
    public void delete(Usuario entity) {
        repository.delete(entity);
    }
    
    public void changePassword(ChangePasswordRequest request, Principal connectedUser) {

        var user = (Usuario) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();

        // check if the current password is correct
        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            throw new IllegalStateException("Wrong password");
        }
        // check if the two new passwords are the same
        if (!request.getNewPassword().equals(request.getConfirmationPassword())) {
            throw new IllegalStateException("Password are not the same");
        }

        // update the password
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));

        // save the new password
        repository.save(user);
    }
}
