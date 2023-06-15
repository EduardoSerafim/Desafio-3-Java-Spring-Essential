package com.devsuperior.desafio3.services;

import com.devsuperior.desafio3.dto.ClientDTO;
import com.devsuperior.desafio3.entities.Client;
import com.devsuperior.desafio3.repositories.ClientRepository;
import com.devsuperior.desafio3.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Transactional(readOnly = true)
    public Page<ClientDTO> findAll(Pageable pageable){
        return clientRepository.findAll(pageable).map(ClientDTO::new);
    }

    @Transactional(readOnly = true)
    public ClientDTO findById(Long id){
        var entity = clientRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado"));
        return new ClientDTO(entity);
    }

    @Transactional
    public ClientDTO Insert(ClientDTO dto){
        Client client = new Client();
        copyDtoToEntity(dto, client);
        client = clientRepository.save(client);
        return new ClientDTO(client);
    }

    @Transactional
    public ClientDTO update(Long id, ClientDTO dto){
        try {
            var entity = clientRepository.getReferenceById(id);
            copyDtoToEntity(dto, entity);
            entity = clientRepository.save(entity);
            return new ClientDTO(entity);
        }catch (EntityNotFoundException e){
            throw new ResourceNotFoundException("Cliente não encontrado");
        }

    }

    @Transactional
    public void delete(Long id){
        if(!clientRepository.existsById(id)){
            throw new ResourceNotFoundException("Cliente não encontrado");
        }
        clientRepository.deleteById(id);

    }



    private void copyDtoToEntity(ClientDTO dto, Client entity) {
        entity.setName(dto.getName());
        entity.setChildren(dto.getChildren());
        entity.setCpf(dto.getCpf());
        entity.setIncome(dto.getIncome());
        entity.setBirthDate(dto.getBirthDate());
    }



}
