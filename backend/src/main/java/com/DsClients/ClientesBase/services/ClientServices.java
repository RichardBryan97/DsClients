package com.DsClients.ClientesBase.services;

import com.DsClients.ClientesBase.dto.ClientDTO;
import com.DsClients.ClientesBase.entities.Client;
import com.DsClients.ClientesBase.repositories.ClientRepository;
import com.DsClients.ClientesBase.services.exceptions.DatabaseException;
import com.DsClients.ClientesBase.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class ClientServices {

    @Autowired
    private ClientRepository clientRepository;

    @Transactional
    public Page<ClientDTO> findAllPaged(PageRequest pageRequest) {

        Page<Client> list = clientRepository.findAll(pageRequest);

        return list.map(x -> new ClientDTO(x));
    }

    @Transactional
    public ClientDTO findById(Long id) {
        Optional<Client> obj = clientRepository.findById(id);

        Client entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity Not Found"));

        return new ClientDTO(entity);
    }

    @Transactional
    public ClientDTO insert(ClientDTO dto) {
        Client entity = new Client();
        CopyDtoToEntity(dto,entity);
        entity = clientRepository.save(entity);

        return new ClientDTO(entity);
    }

    @Transactional
    public ClientDTO update(Long id, ClientDTO dto) {
        try {
            Client entity = clientRepository.getOne(id);
            CopyDtoToEntity(dto, entity);
            entity = clientRepository.save(entity);
            return new ClientDTO(entity);
        }
        catch(EntityNotFoundException e){
            throw new ResourceNotFoundException("Id Not Found" + id);
        }
    }

    public void delete(Long id) {
        try{
            clientRepository.deleteById(id);
        }
        catch (EmptyResultDataAccessException e){
            throw new ResourceNotFoundException("Id Not Found" + id);
        }
        catch (DataIntegrityViolationException e){
            throw new DatabaseException("Integrity Violation");
        }

    }

    private void CopyDtoToEntity(ClientDTO dto, Client entity) {
        entity.setName(dto.getName());
        entity.setBirthDate(dto.getBirthDate());
        entity.setChildren(dto.getChildren());
        entity.setIncome(dto.getIncome());
        entity.setCpf(dto.getCpf());

    }
}
