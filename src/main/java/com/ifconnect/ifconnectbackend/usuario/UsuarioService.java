package com.ifconnect.ifconnectbackend.usuario;

import com.ifconnect.ifconnectbackend.mailcode.Code;
import com.ifconnect.ifconnectbackend.mailcode.CodeService;
import com.ifconnect.ifconnectbackend.models.Usuario;
import com.ifconnect.ifconnectbackend.models.modelvo.SearchFilter;
import com.ifconnect.ifconnectbackend.requestmodels.ChangePasswordRequest;
import jakarta.persistence.NoResultException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final PasswordEncoder passwordEncoder;
    private final UsuarioRepository repository;
    private final CodeService codeService;

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
    
    public void changePassword(ChangePasswordRequest request) {
        Usuario user = repository.findByEmail(request.getUserEmail()).orElseThrow(() ->
                new NoResultException("Ops! Esse usuário não foi encontrado! :(")
        );

        Code code = codeService.find(request.getCode(), user);

        if(code.isExpired()){
            throw new IllegalStateException("Ops! Esse código já expirou");
        }
        // check if the two new passwords are the same
        if (!request.getNewPassword().equals(request.getConfirmationPassword())) {
            throw new IllegalStateException("Senhas não são iguais");
        }
        // update the password
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        // save the new password
        repository.save(user);

        code.setExpired(true);
        codeService.save(code);
    }

    public void changePasswordCode(String email){
        Usuario user = repository.findByEmail(email).orElseThrow(() ->
                new NoResultException("Ops! Esse usuário não foi encontrado! :(")
        );
        codeService.generateAndSendCode(user);
    }

    public void changeProfileImage(String fotoPerfilBase64, Integer usuarioId){
        Usuario user = repository.findById(usuarioId).orElseThrow(() ->
                new NoResultException("Ops! Usuário não foi encontrado! :(")
        );

        user.setFotoPerfilBase64(fotoPerfilBase64);
        repository.save(user);
    }
}
