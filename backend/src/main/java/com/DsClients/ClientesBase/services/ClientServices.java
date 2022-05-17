package com.DsClients.ClientesBase.services;

import com.DsClients.ClientesBase.dto.ClientDTO;
import com.DsClients.ClientesBase.entities.Client;
import com.DsClients.ClientesBase.repositories.ClientRepository;
import com.DsClients.ClientesBase.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

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
}
